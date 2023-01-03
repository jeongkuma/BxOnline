package kr.co.abacus.common.component;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.CallHistoryInfoDto;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.common.ComSqlParameterDto;
import kr.co.abacus.common.util.DateUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.List;
import java.util.StringTokenizer;

public class MybatisQueryInterceptor extends XMLLanguageDriver {
	@Override
	public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
		String sqlId = mappedStatement.getId();
		addDebuggingComment(sqlId, boundSql, parameterObject, mappedStatement);
		return super.createParameterHandler(mappedStatement, parameterObject, boundSql);
	}

	@Override
	public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
		return super.createSqlSource(configuration, script, parameterType);
	}

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
		return super.createSqlSource(configuration, script, parameterType);
	}

	protected String removeBreakingWhitespace(String original) {
		StringTokenizer whitespaceStripper = new StringTokenizer(original);
		StringBuilder builder = new StringBuilder();
		while (whitespaceStripper.hasMoreTokens()) {
			builder.append(whitespaceStripper.nextToken());
			builder.append(" ");
		}
		return builder.toString();
	}

	private void addDebuggingComment(String sqlId, BoundSql boundSql, Object parameterObject, MappedStatement mappedStatement) {

		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		Configuration configuration = mappedStatement.getConfiguration();
		String sql = boundSql.getSql();

		sql = removeBreakingWhitespace(sql);

		List<ComSqlParameterDto> comSqlParameterDtoList = this.appendRequestContextComment(sqlId, sql);

		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				ComSqlParameterDto comSqlParameterDto = new ComSqlParameterDto();
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						MetaObject metaObject = configuration.newMetaObject(parameterObject);
						value = metaObject.getValue(propertyName);
					}
					comSqlParameterDto.setPropertyName(propertyName);
					comSqlParameterDto.setValue(value);
					comSqlParameterDto.setJavaType(parameterMapping.getJavaType().getName());

					if (comSqlParameterDtoList != null) {
						comSqlParameterDtoList.add(comSqlParameterDto);
					}
				}
			}
		}
	}

	private List<ComSqlParameterDto> appendRequestContextComment(String sqlId, String sql) {
		ComInfoDto comInfoDto;
		try {
			comInfoDto = ReqContextComponent.getComInfoDto();
		} catch (IllegalStateException e) {
			return null;
		}
		List<CallHistoryInfoDto> callHistoryInfoDtoList = comInfoDto.getCallHistoryInfoList();
		CallHistoryInfoDto history = new CallHistoryInfoDto(ComConstant.DB_ACCESS_HISTORY_TYPE);
		String reqDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);

		history.setReqDatetime(reqDatetime);
		callHistoryInfoDtoList.add(history);

		List<ComSqlParameterDto> comSqlParameterDtoList = history.getComSqlParameterDtoList();
		history.setComSqlParameterDtoList(comSqlParameterDtoList);

		history.setSql(sql);
		history.setSqlId(sqlId);

		return comSqlParameterDtoList;
	}

}
