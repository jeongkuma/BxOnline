package kr.co.scp.common.label.svc.impl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.label.dao.TbIotLabelDAO;
import kr.co.scp.common.label.dto.TbIotLabelDTO;
import kr.co.scp.common.label.dto.TbIotLabelViewDTO;
import kr.co.scp.common.label.svc.TbIotLabelSVC;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class TbIotLabelSVCImpl implements TbIotLabelSVC {

	@Autowired
	private TbIotLabelDAO tbIotLabelDao;

	@Override
	public HashMap<String, Object> retrievetbIotLabelList(TbIotLabelDTO tbIotLabelDto) throws BizException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 언어셋 설정
		String langSet = ReqContextComponent.getComInfoDto().getXlang();
		if (langSet == null) {
			langSet = ComConstant.DEFAULT_CHAR_SET;
		}

		// 조회 : session에 있는 언어 셋을 넣어 준다.
		tbIotLabelDto.setLangSet(langSet);
		List<TbIotLabelDTO> retrieveFaqBoardList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotLabelDao.retrieveTbIotLabelList");
		try {
			retrieveFaqBoardList = tbIotLabelDao.retrieveTbIotLabelList(tbIotLabelDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		resultMap.put(langSet, retrieveFaqBoardList);

		return resultMap;
	}

	@Override
	@Transactional
	public void insertTbIotLabel(TbIotLabelDTO tbIotLabelDto) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotLabelDao.insertTbIotLabelDTO");
		try {
			tbIotLabelDao.insertTbIotLabelDTO(tbIotLabelDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
	}

	@Override
	@Transactional
	public void updateTbIotLabel(TbIotLabelDTO tbIotLabelDto) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotLabelDao.updateTbIotLabelDTO");
		try {
			tbIotLabelDao.updateTbIotLabelDTO(tbIotLabelDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
	}

	@Override
	@Transactional
	public void deletetbIotLabel(TbIotLabelDTO tbIotLabelDto) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotLabelDao.deleteTbIotLabel");
		try {
			tbIotLabelDao.deleteTbIotLabel(tbIotLabelDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
	}

	@Override
	public HashMap<String, Object> retrievetbIotLabelView(TbIotLabelDTO tbIotLabelDto) throws BizException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 언어셋 설정
		String langSet = ReqContextComponent.getComInfoDto().getXlang();
		if (langSet == null) {
			langSet = ComConstant.DEFAULT_CHAR_SET;
		}

		// 조회 : session에 있는 언어 셋을 넣어 준다.
		tbIotLabelDto.setLangSet(langSet);
		List<TbIotLabelViewDTO> retrievetbIotLabelView = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotLabelDao.retrieveTbIotLabelView");
		try {
			retrievetbIotLabelView = tbIotLabelDao.retrieveTbIotLabelView(tbIotLabelDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		resultMap.put(langSet, retrievetbIotLabelView);

		return resultMap;
	}

}
