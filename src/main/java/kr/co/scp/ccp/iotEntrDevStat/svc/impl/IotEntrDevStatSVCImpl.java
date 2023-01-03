package kr.co.scp.ccp.iotEntrDevStat.svc.impl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.iotEntrDevStat.dao.IotEntrDevStatDAO;
import kr.co.scp.ccp.iotEntrDevStat.dto.TbIotEntrDevStatDTO;
import kr.co.scp.ccp.iotEntrDevStat.svc.IotEntrDevStatSVC;
import kr.co.scp.common.tmpl.dao.TbIotTmplHdrJqgridDAO;
import kr.co.scp.common.tmpl.dto.TbIotJqDataResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class IotEntrDevStatSVCImpl implements IotEntrDevStatSVC {

	@Autowired
	private IotEntrDevStatDAO iotEntrDevStatDAO;

	@Autowired
	private TbIotTmplHdrJqgridDAO tbIotTmplHdrJqgridDao;


	@Override
	public HashMap<String, Object> retrieveHourStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException {

		Integer count = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveHourStatListCount");
		try {
			if (tbIotEntrDevStatDTO.getSearchEndDttm() != null) {
				String chngDttm = tbIotEntrDevStatDTO.getSearchEndDttm().substring(0, 10) + "5959";
				tbIotEntrDevStatDTO.setSearchEndDttm(chngDttm);
			}
			
			if (tbIotEntrDevStatDTO.getStatType() != null) {
				if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
					count = iotEntrDevStatDAO.retrieveHourStatListCount(tbIotEntrDevStatDTO);
				}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
					count = iotEntrDevStatDAO.retrieveHourStatAvgListCount(tbIotEntrDevStatDTO);
				}
			}
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

		PageDTO pageDTO = new PageDTO();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		pageDTO.pageCalculate(count, tbIotEntrDevStatDTO.getDisplayRowCount(), tbIotEntrDevStatDTO.getCurrentPage());
		tbIotEntrDevStatDTO.setStartPage(pageDTO.getRowStart());

		List<TbIotEntrDevStatDTO> retrieveHourStatList = new ArrayList<TbIotEntrDevStatDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveHourStatList");
		try {
			if (tbIotEntrDevStatDTO.getStatType() != null) {
				if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
					retrieveHourStatList = iotEntrDevStatDAO.retrieveHourStatList(tbIotEntrDevStatDTO);
				}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
					retrieveHourStatList = iotEntrDevStatDAO.retrieveHourStatAvgList(tbIotEntrDevStatDTO);
				}
			}	
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

		resultMap.put("page", pageDTO);
		resultMap.put("retrieveHourStatList", retrieveHourStatList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> retrieveDayStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) {
		PageDTO pageDTO = new PageDTO();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Integer count = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveDayStatListCount");
		try {
			if (tbIotEntrDevStatDTO.getSearchEndDttm() != null) {
				String chngDttm = tbIotEntrDevStatDTO.getSearchEndDttm().substring(0, 8) + "235959";
				tbIotEntrDevStatDTO.setSearchEndDttm(chngDttm);
			}
			if (tbIotEntrDevStatDTO.getStatType() != null) {
				if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
					count = iotEntrDevStatDAO.retrieveDayStatListCount(tbIotEntrDevStatDTO);
				}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
					count = iotEntrDevStatDAO.retrieveDayStatAvgListCount(tbIotEntrDevStatDTO);
				}
			}
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

		pageDTO.pageCalculate(count, tbIotEntrDevStatDTO.getDisplayRowCount(), tbIotEntrDevStatDTO.getCurrentPage());
		tbIotEntrDevStatDTO.setStartPage(pageDTO.getRowStart());

		List<TbIotEntrDevStatDTO> retrieveDayStatList = new ArrayList<TbIotEntrDevStatDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveDayStatList");
		try {
			if (tbIotEntrDevStatDTO.getStatType() != null) {
				if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
					retrieveDayStatList = iotEntrDevStatDAO.retrieveDayStatList(tbIotEntrDevStatDTO);
				}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
					retrieveDayStatList = iotEntrDevStatDAO.retrieveDayStatAvgList(tbIotEntrDevStatDTO);
				}
			}
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

		resultMap.put("page", pageDTO);
		resultMap.put("retrieveDayStatList", retrieveDayStatList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> retrieveWeekStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException {
		PageDTO pageDTO = new PageDTO();
		Integer count = 0;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveWeekStatListCount");
		try {
			if (tbIotEntrDevStatDTO.getSearchEndDttm() != null) {
				String chngDttm = tbIotEntrDevStatDTO.getSearchEndDttm().substring(0, 8) + "235959";
				tbIotEntrDevStatDTO.setSearchEndDttm(chngDttm);
			}
			if (tbIotEntrDevStatDTO.getStatType() != null) {
				if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
					count = iotEntrDevStatDAO.retrieveWeekStatListCount(tbIotEntrDevStatDTO);
				}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
					count = iotEntrDevStatDAO.retrieveWeekStatAvgListCount(tbIotEntrDevStatDTO);
				}
			}
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

		pageDTO.pageCalculate(count, tbIotEntrDevStatDTO.getDisplayRowCount(), tbIotEntrDevStatDTO.getCurrentPage());
		tbIotEntrDevStatDTO.setStartPage(pageDTO.getRowStart());

		List<TbIotEntrDevStatDTO> retrieveWeekStatList = new ArrayList<TbIotEntrDevStatDTO>();

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveWeekStatList");
		try {
			if (tbIotEntrDevStatDTO.getStatType() != null) {
				if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
					retrieveWeekStatList = iotEntrDevStatDAO.retrieveWeekStatList(tbIotEntrDevStatDTO);
				}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
					retrieveWeekStatList = iotEntrDevStatDAO.retrieveWeekStatAvgList(tbIotEntrDevStatDTO);
				}
			}
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

		resultMap.put("page", pageDTO);
		resultMap.put("retrieveWeekStatList", retrieveWeekStatList);

		return resultMap;

	}

	@Override
	public HashMap<String, Object> retrieveMonthStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException {
		PageDTO pageDTO = new PageDTO();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Integer count = 0;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveMonthStatListCount");
		try {
			if (tbIotEntrDevStatDTO.getSearchEndDttm() != null) {
				String chngDttm = tbIotEntrDevStatDTO.getSearchEndDttm().substring(0, 6) + "31235959";
				tbIotEntrDevStatDTO.setSearchEndDttm(chngDttm);
			}
			if (tbIotEntrDevStatDTO.getStatType() != null) {
				if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
					count = iotEntrDevStatDAO.retrieveMonthStatListCount(tbIotEntrDevStatDTO);
				}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
					count = iotEntrDevStatDAO.retrieveMonthStatAvgListCount(tbIotEntrDevStatDTO);
				}
			}
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

		pageDTO.pageCalculate(count, tbIotEntrDevStatDTO.getDisplayRowCount(), tbIotEntrDevStatDTO.getCurrentPage());
		tbIotEntrDevStatDTO.setStartPage(pageDTO.getRowStart());

		List<TbIotEntrDevStatDTO> retrieveMonthStatList = new ArrayList<TbIotEntrDevStatDTO>();

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveMonthStatList");
		try {
			if (tbIotEntrDevStatDTO.getStatType() != null) {
				if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
					retrieveMonthStatList = iotEntrDevStatDAO.retrieveMonthStatList(tbIotEntrDevStatDTO);
				}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
					retrieveMonthStatList = iotEntrDevStatDAO.retrieveMonthStatAvgList(tbIotEntrDevStatDTO);
				}
			}
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

		resultMap.put("page", pageDTO);
		resultMap.put("retrieveMonthStatList", retrieveMonthStatList);

		return resultMap;

	}

	@Override
	public XSSFWorkbook retrieveStatExcelDownload(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException {
		List<TbIotEntrDevStatDTO> retrieveExcelList = new ArrayList<TbIotEntrDevStatDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevStatDAO.retrieveStatExcelDownload");
		try {
			if (tbIotEntrDevStatDTO.getTimeGubun() != null) {
				if (tbIotEntrDevStatDTO.getTimeGubun().equals("hour")) {
					if (tbIotEntrDevStatDTO.getStatType() != null) {
						if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
							retrieveExcelList = iotEntrDevStatDAO.retrieveHourStatListExcel(tbIotEntrDevStatDTO);
						}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
							retrieveExcelList = iotEntrDevStatDAO.retrieveHourStatAvgListExcel(tbIotEntrDevStatDTO);
						}
					}
				} else if (tbIotEntrDevStatDTO.getTimeGubun().equals("day")) {
					if (tbIotEntrDevStatDTO.getStatType() != null) {
						if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
							retrieveExcelList = iotEntrDevStatDAO.retrieveDayStatListExcel(tbIotEntrDevStatDTO);
						}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
							retrieveExcelList = iotEntrDevStatDAO.retrieveDayStatAvgListExcel(tbIotEntrDevStatDTO);
						}
					}
				} else if (tbIotEntrDevStatDTO.getTimeGubun().equals("week")) {
					if (tbIotEntrDevStatDTO.getStatType() != null) {
						if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
							retrieveExcelList = iotEntrDevStatDAO.retrieveWeekStatListExcel(tbIotEntrDevStatDTO);
						}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
							retrieveExcelList = iotEntrDevStatDAO.retrieveWeekStatAvgListExcel(tbIotEntrDevStatDTO);
						}
					}
				} else if (tbIotEntrDevStatDTO.getTimeGubun().equals("month")) {
					if (tbIotEntrDevStatDTO.getStatType() != null) {
						if(tbIotEntrDevStatDTO.getStatType().equals("sum")) {
							retrieveExcelList = iotEntrDevStatDAO.retrieveMonthStatListExcel(tbIotEntrDevStatDTO);
						}else if(tbIotEntrDevStatDTO.getStatType().equals("avg")) {
							retrieveExcelList = iotEntrDevStatDAO.retrieveMonthStatAvgListExcel(tbIotEntrDevStatDTO);
						}
					}
				}
			}
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

		List<TbIotJqDataResponseDTO> dataList = new ArrayList<TbIotJqDataResponseDTO>();

		XSSFWorkbook wb = new XSSFWorkbook();
		try {
			ComInfoDto c = ReqContextComponent.getComInfoDto();
			String newLang = c.getXlang();
			tbIotEntrDevStatDTO.setLangSet(newLang);

			// 동적 템플릿 가져오기

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotTmplHdrJqgridDao.retrieveEntrJqDataExcel");
			try {
				dataList = tbIotTmplHdrJqgridDao.retrieveEntrJqDataExcel(tbIotEntrDevStatDTO);
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

			// 동적 헤더명
			String colName[] = dataList.get(0).getColNameData().split(", ");

			// 엑셀 다운로드
			Map<String, String> title = new LinkedHashMap<String, String>();

			for (int i = 0; i < dataList.size(); i++) {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParser.parse(dataList.get(i).getColModelData());

				// map에 값 담기
				title.put(colName[i], (String) jsonObj.get("name"));
			}

			ExcelUtils.createExcelFile(wb, retrieveExcelList, title);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}
}
