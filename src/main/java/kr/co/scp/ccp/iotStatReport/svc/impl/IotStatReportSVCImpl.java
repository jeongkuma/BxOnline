package kr.co.scp.ccp.iotStatReport.svc.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCust.dao.IotCustDAO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotStatReport.dao.IotStatReportDAO;
import kr.co.scp.ccp.iotStatReport.dto.TbIotComCollStatDTO;
import kr.co.scp.ccp.iotStatReport.svc.IotStatReportSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;


@Primary
@Service
public class IotStatReportSVCImpl implements IotStatReportSVC {

	@Autowired
	private IotStatReportDAO iotStatReportDAO;

	@Autowired
	private IotCustDAO iotCustDAO;

	final String DATE_PATTERN = "yyyyMMdd";

//	private static String[] titles = { "고객사", "장비 유형", "조직", "모델명", "설치대수" };
	private static String[] titles = { "고객사", "장비 유형", "모델명", "설치대수" };
	private static String[] subTitle = { "통신 성공율", "데이터 수집율" };
	private static List<Map<String, String>> rateDataNames = new ArrayList<>();

	static {
		Map<String, String> rateData = null;
		for (int i = 0; i < 7; i++) {
			rateData = new HashMap<>();
			rateData.put("commRate", "commRate" + i);
			rateData.put("collCnt", "collCnt" + i);
			rateData.put("collRate", "collRate" + i);
			rateDataNames.add(rateData);
		}
	}

	@Override
	public Map<String, Object> retrieveIotStatReportList(TbIotComCollStatDTO tbIotComCollStatDTO) throws BizException {
		List<String> searchDateList = new ArrayList<>();
		TbIotCustUDTO tbIotCustDTO = new TbIotCustUDTO();

		if(AuthUtils.getUser() != null && AuthUtils.getUser().getSvcCd() != null && !"".equals(AuthUtils.getUser().getSvcCd()) ) {
			tbIotComCollStatDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		}
		if(AuthUtils.getUser() != null && AuthUtils.getUser().getCustSeq() != null && !"".equals(AuthUtils.getUser().getCustSeq()) ) {
			tbIotComCollStatDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			tbIotCustDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}
		tbIotCustDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		TbIotCustUDTO returnDto = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.retrieveIotCustBySeq");
		try {
			returnDto = iotCustDAO.retrieveIotCustBySeq(tbIotCustDTO);
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

		String custNm = "";
		String roleCdId = AuthUtils.getUser().getRoleCdId();
		if( returnDto != null) {
			custNm = returnDto.getCustNm();
		}

		if("".equals(tbIotComCollStatDTO.getCustNm())) {
			if ( "GN00000038".equals(roleCdId) || "GN00000033".equals(roleCdId) || "GN00000034".equals(roleCdId)) {

	        } else {
	        	tbIotComCollStatDTO.setCustNm(custNm);
	        }
		}

		String searchStartDttm = tbIotComCollStatDTO.getSearchStartDttm();
		String searchEndDttm = tbIotComCollStatDTO.getSearchEndDttm();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

		if (StringUtils.isEmpty(searchStartDttm) && StringUtils.isEmpty(searchEndDttm)) {
			Date currentTime = new Date();
			searchEndDttm = sdf.format(currentTime);
			tbIotComCollStatDTO.setSearchEndDttm(searchEndDttm);

			cal.setTime(currentTime);
			cal.add(Calendar.DAY_OF_MONTH, -2);
			searchStartDttm = sdf.format(cal.getTime());
			tbIotComCollStatDTO.setSearchStartDttm(searchStartDttm);
		}

		Date startDate = null;
		Date endDate = null;

		try {
			startDate = sdf.parse(searchStartDttm);
			endDate = sdf.parse(searchEndDttm);
		} catch (ParseException e) {
			throw new BizException("datePasingError");
		}

		if (startDate.after(endDate)) {
			throw new BizException("invalidDate");
		}

		Date currentDate = startDate;
		while (currentDate.compareTo(endDate) <= 0) {
			searchDateList.add(sdf.format(currentDate));
			cal.setTime(currentDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			currentDate = cal.getTime();
		}
		tbIotComCollStatDTO.setSearchDateList(searchDateList);
		tbIotComCollStatDTO.setOrgNm(AuthUtils.getUser().getOrgNm());

		List<TbIotComCollStatDTO> reportList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotStatReportDAO.retrieveIotStatReportList");
		try {
			reportList = iotStatReportDAO.retrieveIotStatReportListNew(tbIotComCollStatDTO);
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

		int sum = 0;
		int subSum = 0;
		for (TbIotComCollStatDTO statDTO : reportList) {
			if (statDTO.getGid() == 0) {
				sum += statDTO.getDevCount();
			} else if (statDTO.getGid() == 1) {
				subSum += sum;
				statDTO.setDevCount(sum);
				sum = 0;
			} else if (statDTO.getGid() == 3) {
				statDTO.setDevCount(subSum);
				subSum = 0;
			}
		}

		double sumCol0 = 0;
		double subSumCol0 = 0;
		double subTotSumCol0 = 0;
		double totSumCol0 = 0;
		double sumCom0 = 0;
		double subSumCom0 = 0;
		double subTotSumCom0 = 0;
		double totSumCom0 = 0;

		double sumCol1 = 0;
		double subSumCol1 = 0;
		double subTotSumCol1 = 0;
		double totSumCol1 = 0;
		double sumCom1 = 0;
		double subSumCom1 = 0;
		double subTotSumCom1 = 0;
		double totSumCom1 = 0;

		double sumCol2 = 0;
		double subSumCol2 = 0;
		double subTotSumCol2 = 0;
		double totSumCol2 = 0;
		double sumCom2 = 0;
		double subSumCom2 = 0;
		double subTotSumCom2 = 0;
		double totSumCom2 = 0;

		double sumCol3 = 0;
		double subSumCol3 = 0;
		double subTotSumCol3 = 0;
		double totSumCol3 = 0;
		double sumCom3 = 0;
		double subSumCom3 = 0;
		double subTotSumCom3 = 0;
		double totSumCom3 = 0;

		double sumCol4 = 0;
		double subSumCol4 = 0;
		double subTotSumCol4 = 0;
		double totSumCol4 = 0;
		double sumCom4 = 0;
		double subSumCom4 = 0;
		double subTotSumCom4 = 0;
		double totSumCom4 = 0;

		double sumCol5 = 0;
		double subSumCol5 = 0;
		double subTotSumCol5 = 0;
		double totSumCol5 = 0;
		double sumCom5 = 0;
		double subSumCom5 = 0;
		double subTotSumCom5 = 0;
		double totSumCom5 = 0;

		double sumCol6 = 0;
		double subSumCol6 = 0;
		double subTotSumCol6 = 0;
		double totSumCol6 = 0;
		double sumCom6 = 0;
		double subSumCom6 = 0;
		double subTotSumCom6 = 0;
		double totSumCom6 = 0;

		double cnt = 0;
		double subCnt = 0;

		for (TbIotComCollStatDTO statDTO : reportList) {
			if (statDTO.getGid() == 0) {

				///
				String collRate0 = statDTO.getCollRate0();
				String commRate0 = statDTO.getCommRate0();
				sumCol0 += Double.parseDouble(StringUtils.isEmpty(collRate0)? "0" : collRate0);
				sumCom0 += Double.parseDouble(StringUtils.isEmpty(commRate0)? "0" : commRate0);

				// Log.debug("==0=sumCol0="+sumCol0);
				// Log.debug("==0=sumCom0="+sumCom0);
				///
				String collRate1 = statDTO.getCollRate1();
				String commRate1 = statDTO.getCommRate1();
				sumCol1 += Double.parseDouble(StringUtils.isEmpty(collRate1)? "0" : collRate1);
				sumCom1 += Double.parseDouble(StringUtils.isEmpty(commRate1)? "0" : commRate1);

				// Log.debug("==0=sumCol1="+sumCol1);
				// Log.debug("==0=sumCom1="+sumCom1);

				///
				String collRate2 = statDTO.getCollRate2();
				String commRate2 = statDTO.getCommRate2();
				sumCol2 += Double.parseDouble(StringUtils.isEmpty(collRate2)? "0" : collRate2);
				sumCom2 += Double.parseDouble(StringUtils.isEmpty(commRate2)? "0" : commRate2);

				// Log.debug("==0=sumCol2="+sumCol2);
				// Log.debug("==0=sumCom2="+sumCom2);

				///
				String collRate3 = statDTO.getCollRate3();
				String commRate3 = statDTO.getCommRate3();
				sumCol3 += Double.parseDouble(StringUtils.isEmpty(collRate3)? "0" : collRate3);
				sumCom3 += Double.parseDouble(StringUtils.isEmpty(commRate3)? "0" : commRate3);

				// Log.debug("==0=sumCol3="+sumCol3);
				// Log.debug("==0=sumCom3="+sumCom3);

				String collRate4 = statDTO.getCollRate4();
				String commRate4 = statDTO.getCommRate4();
				sumCol4 += Double.parseDouble(StringUtils.isEmpty(collRate4)? "0" : collRate4);
				sumCom4 += Double.parseDouble(StringUtils.isEmpty(commRate4)? "0" : commRate4);

				// Log.debug("==0=sumCol4="+sumCol4);
				// Log.debug("==0=sumCom4="+sumCom4);

				String collRate5 = statDTO.getCollRate5();
				String commRate5 = statDTO.getCommRate5();
				sumCol5 += Double.parseDouble(StringUtils.isEmpty(collRate5)? "0" : collRate5);
				sumCom5 += Double.parseDouble(StringUtils.isEmpty(commRate5)? "0" : commRate5);

				// Log.debug("==0=sumCol5="+sumCol4);
				// Log.debug("==0=sumCom5="+sumCom4);

				String collRate6 = statDTO.getCollRate6();
				String commRate6 = statDTO.getCommRate6();
				sumCol6 += Double.parseDouble(StringUtils.isEmpty(collRate6)? "0" : collRate6);
				sumCom6 += Double.parseDouble(StringUtils.isEmpty(commRate6)? "0" : commRate6);

				// Log.debug("==0=sumCol6="+sumCol6);
				// Log.debug("==0=sumCom6="+sumCom6);
				cnt++;

			} else if (statDTO.getGid() == 1) {
				///
				subSumCol0 += sumCol0;
				subSumCom0 += sumCom0;
				double avgCol0 = subSumCol0/cnt;
				double avgCom0 = subSumCom0/cnt;

				double roundCol0 = Math.round(avgCol0*100)/100.0; //결과 : 0.00
				double roundCom0 = Math.round(avgCom0*100)/100.0; //결과 : 0.00

				if (Double.isNaN(roundCol0)) {
					roundCol0 = 0;
				}
				if (Double.isNaN(roundCom0)) {
					roundCom0 = 0;
				}

				statDTO.setCollRate0(roundCol0+"");
				statDTO.setCommRate0(roundCom0+"");

				// Log.debug("==0=roundCol0="+roundCol0+"");
				// Log.debug("==0=roundCol0="+roundCom0+"");

				sumCol0 = 0;
				sumCom0 = 0;
				subTotSumCol0 += roundCol0;
				subTotSumCom0 += roundCom0;
				subSumCol0 = 0;
				subSumCom0 = 0;
				///
				subSumCol1 += sumCol1;
				subSumCom1 += sumCom1;
				double avgCol1 = subSumCol1/cnt;
				double avgCom1 = subSumCom1/cnt;

				double roundCol1 = Math.round(avgCol1*100)/100.0; //결과 : 1.11
				double roundCom1 = Math.round(avgCom1*100)/100.0; //결과 : 1.11

				if (Double.isNaN(roundCol1)) {
					roundCol1 = 0;
				}
				if (Double.isNaN(roundCom1)) {
					roundCom1 = 0;
				}
				statDTO.setCollRate1(roundCol1+"");
				statDTO.setCommRate1(roundCom1+"");

				// Log.debug("==1=roundCol1="+roundCol1+"");
				// Log.debug("==1=roundCol1="+roundCom1+"");

				sumCol1 = 0;
				sumCom1 = 0;
				subTotSumCol1 += roundCol1;
				subTotSumCom1 += roundCom1;
				subSumCol1 = 0;
				subSumCom1 = 0;

				///
				subSumCol2 += sumCol2;
				subSumCom2 += sumCom2;
				double avgCol2 = subSumCol2/cnt;
				double avgCom2 = subSumCom2/cnt;

				double roundCol2 = Math.round(avgCol2*100)/100.0; //결과 : 2.12
				double roundCom2 = Math.round(avgCom2*100)/100.0; //결과 : 2.12

				if (Double.isNaN(roundCol2)) {
					roundCol2 = 0;
				}
				if (Double.isNaN(roundCom2)) {
					roundCom2 = 0;
				}
				statDTO.setCollRate2(roundCol2+"");
				statDTO.setCommRate2(roundCom2+"");

				// Log.debug("==1=roundCol2="+roundCol2+"");
				// Log.debug("==1=roundCol2="+roundCom2+"");

				sumCol2 = 0;
				sumCom2 = 0;
				subTotSumCol2 += roundCol2;
				subTotSumCom2 += roundCom2;
				subSumCol2 = 0;
				subSumCom2 = 0;

				///
				subSumCol3 += sumCol3;
				subSumCom3 += sumCom3;
				double avgCol3 = subSumCol3/cnt;
				double avgCom3 = subSumCom3/cnt;

				double roundCol3 = Math.round(avgCol3*100)/100.0; //결과 : 3.13
				double roundCom3 = Math.round(avgCom3*100)/100.0; //결과 : 3.13

				if (Double.isNaN(roundCol3)) {
					roundCol3 = 0;
				}
				if (Double.isNaN(roundCom3)) {
					roundCom3 = 0;
				}
				statDTO.setCollRate3(roundCol3+"");
				statDTO.setCommRate3(roundCom3+"");

				// Log.debug("==1=roundCol3="+roundCol3+"");
				// Log.debug("==1=roundCol3="+roundCom3+"");

				sumCol3 = 0;
				sumCom3 = 0;
				subTotSumCol3 += roundCol3;
				subTotSumCom3 += roundCom3;
				subSumCol3 = 0;
				subSumCom3 = 0;

				///
				subSumCol4 += sumCol4;
				subSumCom4 += sumCom4;
				double avgCol4 = subSumCol4/cnt;
				double avgCom4 = subSumCom4/cnt;

				double roundCol4 = Math.round(avgCol4*100)/100.0; //결과 : 3.14
				double roundCom4 = Math.round(avgCom4*100)/100.0; //결과 : 3.14

				if (Double.isNaN(roundCol4)) {
					roundCol4 = 0;
				}
				if (Double.isNaN(roundCom4)) {
					roundCom4 = 0;
				}
				statDTO.setCollRate4(roundCol4+"");
				statDTO.setCommRate4(roundCom4+"");

				// Log.debug("==1=roundCol4="+roundCol4+"");
				// Log.debug("==1=roundCol4="+roundCom4+"");

				sumCol4 = 0;
				sumCom4 = 0;
				subTotSumCol4 += roundCol4;
				subTotSumCom4 += roundCom4;
				subSumCol4 = 0;
				subSumCom4 = 0;

				////
				subSumCol5 += sumCol5;
				subSumCom5 += sumCom5;
				double avgCol5 = subSumCol5/cnt;
				double avgCom5 = subSumCom5/cnt;

				double roundCol5 = Math.round(avgCol5*100)/100.0; //결과 : 3.14
				double roundCom5 = Math.round(avgCom5*100)/100.0; //결과 : 3.14

				if (Double.isNaN(roundCol5)) {
					roundCol5 = 0;
				}
				if (Double.isNaN(roundCom5)) {
					roundCom5 = 0;
				}
				statDTO.setCollRate5(roundCol5+"");
				statDTO.setCommRate5(roundCom5+"");

				// Log.debug("==1=roundCol5="+roundCol5+"");
				// Log.debug("==1=roundCol5="+roundCom5+"");

				sumCol5 = 0;
				sumCom5 = 0;
				subTotSumCol5 += roundCol5;
				subTotSumCom5 += roundCom5;
				subSumCol5 = 0;
				subSumCom5 = 0;

				////
				subSumCol6 += sumCol6;
				subSumCom6 += sumCom6;
				double avgCol6 = subSumCol6/cnt;
				double avgCom6 = subSumCom6/cnt;

				double roundCol6 = Math.round(avgCol6*100)/100.0; //결과 : 3.14
				double roundCom6 = Math.round(avgCom6*100)/100.0; //결과 : 3.14

				if (Double.isNaN(roundCol6)) {
					roundCol6 = 0;
				}
				if (Double.isNaN(roundCom6)) {
					roundCom6 = 0;
				}
				statDTO.setCollRate6(roundCol6+"");
				statDTO.setCommRate6(roundCom6+"");

				// Log.debug("==1=roundCol6="+roundCol6+"");
				// Log.debug("==1=roundCol6="+roundCom6+"");

				sumCol6 = 0;
				sumCom6 = 0;
				subTotSumCol6 += roundCol6;
				subTotSumCom6 += roundCom6;
				subSumCol6 = 0;
				subSumCom6 = 0;

				subCnt++;
				cnt = 0;

			} else if (statDTO.getGid() == 3) {

				///
				totSumCol0 += subTotSumCol0;
				totSumCom0 += subTotSumCom0;
				double avgCol0 = totSumCol0/subCnt;
				double avgCom0 = totSumCom0/subCnt;

				double roundCol0 = Math.round(avgCol0*100)/100.0; //결과 : 0.00
				double roundCom0 = Math.round(avgCom0*100)/100.0; //결과 : 0.00

				if (Double.isNaN(roundCol0)) {
					roundCol0 = 0;
				}
				if (Double.isNaN(roundCom0)) {
					roundCom0 = 0;
				}
				statDTO.setCollRate0(roundCol0+"");
				statDTO.setCommRate0(roundCom0+"");

				// Log.debug("==0=roundCol0="+roundCol0+"");
				// Log.debug("==0=roundCol0="+roundCom0+"");

				sumCol0 = 0;
				sumCom0 = 0;
				subTotSumCol0 = 0;
				subTotSumCom0 = 0;
				totSumCol0 = 0;
				totSumCom0 = 0;

				///
				totSumCol1 += subTotSumCol1;
				totSumCom1 += subTotSumCom1;
				double avgCol1 = totSumCol1/subCnt;
				double avgCom1 = totSumCom1/subCnt;

				double roundCol1 = Math.round(avgCol1*100)/100.0; //결과 : 1.11
				double roundCom1 = Math.round(avgCom1*100)/100.0; //결과 : 1.11

				if (Double.isNaN(roundCol1)) {
					roundCol1 = 0;
				}
				if (Double.isNaN(roundCom1)) {
					roundCom1 = 0;
				}
				statDTO.setCollRate1(roundCol1+"");
				statDTO.setCommRate1(roundCom1+"");

				// Log.debug("==1=roundCol1="+roundCol1+"");
				// Log.debug("==1=roundCol1="+roundCom1+"");

				sumCol1 = 0;
				sumCom1 = 0;
				subTotSumCol1 = 0;
				subTotSumCom1 = 0;
				totSumCol1 = 0;
				totSumCom1 = 0;
				///
				totSumCol2 += subTotSumCol2;
				totSumCom2 += subTotSumCom2;
				double avgCol2 = totSumCol2/subCnt;
				double avgCom2 = totSumCom2/subCnt;

				double roundCol2 = Math.round(avgCol2*100)/100.0; //결과 : 2.12
				double roundCom2 = Math.round(avgCom2*100)/100.0; //결과 : 2.12

				if (Double.isNaN(roundCol2)) {
					roundCol2 = 0;
				}
				if (Double.isNaN(roundCom2)) {
					roundCom2 = 0;
				}
				statDTO.setCollRate2(roundCol2+"");
				statDTO.setCommRate2(roundCom2+"");

				// Log.debug("==2=roundCol2="+roundCol2+"");
				// Log.debug("==2=roundCol2="+roundCom2+"");

				sumCol2 = 0;
				sumCom2 = 0;
				subTotSumCol2 = 0;
				subTotSumCom2 = 0;
				totSumCol2 = 0;
				totSumCom2 = 0;

				///
				totSumCol3 += subTotSumCol3;
				totSumCom3 += subTotSumCom3;
				double avgCol3 = totSumCol3/subCnt;
				double avgCom3 = totSumCom3/subCnt;

				double roundCol3 = Math.round(avgCol3*100)/100.0; //결과 : 3.13
				double roundCom3 = Math.round(avgCom3*100)/100.0; //결과 : 3.13

				if (Double.isNaN(roundCol3)) {
					roundCol3 = 0;
				}
				if (Double.isNaN(roundCom3)) {
					roundCom3 = 0;
				}
				statDTO.setCollRate3(roundCol3+"");
				statDTO.setCommRate3(roundCom3+"");

				// Log.debug("==3=roundCol3="+roundCol3+"");
				// Log.debug("==3=roundCol3="+roundCom3+"");

				sumCol3 = 0;
				sumCom3 = 0;
				subTotSumCol3 = 0;
				subTotSumCom3 = 0;
				totSumCol3 = 0;
				totSumCom3 = 0;
				///
				totSumCol4 += subTotSumCol4;
				totSumCom4 += subTotSumCom4;
				double avgCol4 = totSumCol4/subCnt;
				double avgCom4 = totSumCom4/subCnt;

				double roundCol4 = Math.round(avgCol4*100)/100.0; //결과 : 3.14
				double roundCom4 = Math.round(avgCom4*100)/100.0; //결과 : 3.14

				if (Double.isNaN(roundCol4)) {
					roundCol4 = 0;
				}
				if (Double.isNaN(roundCom4)) {
					roundCom4 = 0;
				}
				statDTO.setCollRate4(roundCol4+"");
				statDTO.setCommRate4(roundCom4+"");

				// Log.debug("==3=roundCol4="+roundCol4+"");
				// Log.debug("==3=roundCol4="+roundCom4+"");

				sumCol4 = 0;
				sumCom4 = 0;
				subTotSumCol4 = 0;
				subTotSumCom4 = 0;
				totSumCol4 = 0;
				totSumCom4 = 0;
				///
				totSumCol5 += subTotSumCol5;
				totSumCom5 += subTotSumCom5;
				double avgCol5 = totSumCol5/subCnt;
				double avgCom5 = totSumCom5/subCnt;

				double roundCol5 = Math.round(avgCol5*100)/100.0; //결과 : 3.14
				double roundCom5 = Math.round(avgCom5*100)/100.0; //결과 : 3.14

				if (Double.isNaN(roundCol5)) {
					roundCol5 = 0;
				}
				if (Double.isNaN(roundCom5)) {
					roundCom5 = 0;
				}

				statDTO.setCollRate5(roundCol5+"");
				statDTO.setCommRate5(roundCom5+"");

				// Log.debug("==3=roundCol5="+roundCol5+"");
				// Log.debug("==3=roundCol5="+roundCom5+"");

				sumCol5 = 0;
				sumCom5 = 0;
				subTotSumCol5 = 0;
				subTotSumCom5 = 0;
				totSumCol5 = 0;
				totSumCom5 = 0;

				///
				totSumCol6 += subTotSumCol6;
				totSumCom6 += subTotSumCom6;
				double avgCol6 = totSumCol6/subCnt;
				double avgCom6 = totSumCom6/subCnt;

				double roundCol6 = Math.round(avgCol6*100)/100.0; //결과 : 3.14
				double roundCom6 = Math.round(avgCom6*100)/100.0; //결과 : 3.14

				if (Double.isNaN(roundCol6)) {
					roundCol6 = 0;
				}
				if (Double.isNaN(roundCom6)) {
					roundCom6 = 0;
				}

				statDTO.setCollRate6(roundCol6+"");
				statDTO.setCommRate6(roundCom6+"");

				// Log.debug("==3=roundCol6="+roundCol6+"");
				// Log.debug("==3=roundCol6="+roundCom6+"");


				sumCol6 = 0;
				sumCom6 = 0;
				subTotSumCol6 = 0;
				subTotSumCom6 = 0;
				totSumCol6 = 0;
				totSumCom6 = 0;

				subCnt = 0;
			}
		}

		Map<String, Object> returnData = new HashMap<String, Object>();

		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		List<String> viewSearchDateList = new ArrayList<>();
		try {
			for (String searchDate : searchDateList) {
				date = sdf.parse(searchDate);
				viewSearchDateList.add(f.format(date));
			}
		} catch (ParseException e) {
			throw new BizException("datePasingError");
		}



		returnData.put("roleCdId", roleCdId );
		returnData.put("custNm", custNm);
		returnData.put("loginId", AuthUtils.getUser().getUserId());
		returnData.put("searchDateList", viewSearchDateList);
		returnData.put("reportList", reportList);

		return returnData;
	}

	@Override
	public Map<String, Object> retrieveIotStatReportListNew(TbIotComCollStatDTO tbIotComCollStatDTO)
			throws BizException {
		List<String> searchDateList = new ArrayList<>();
		TbIotCustUDTO tbIotCustDTO = new TbIotCustUDTO();

		if(AuthUtils.getUser() != null && AuthUtils.getUser().getSvcCd() != null && !"".equals(AuthUtils.getUser().getSvcCd()) ) {
			tbIotComCollStatDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		}
		if(AuthUtils.getUser() != null && AuthUtils.getUser().getCustSeq() != null && !"".equals(AuthUtils.getUser().getCustSeq()) ) {
			tbIotComCollStatDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			tbIotCustDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}
		tbIotCustDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		TbIotCustUDTO returnDto = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.retrieveIotCustBySeq");
		try {
			returnDto = iotCustDAO.retrieveIotCustBySeq(tbIotCustDTO);
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

		String custNm = "";
		String roleCdId = AuthUtils.getUser().getRoleCdId();
		if( returnDto != null) {
			custNm = returnDto.getCustNm();
		}

		if("".equals(tbIotComCollStatDTO.getCustNm())) {
			if ( "GN00000038".equals(roleCdId) || "GN00000033".equals(roleCdId) || "GN00000034".equals(roleCdId)) {

	        } else {
	        	tbIotComCollStatDTO.setCustNm(custNm);
	        }
		}

		String searchStartDttm = tbIotComCollStatDTO.getSearchStartDttm();
		String searchEndDttm = tbIotComCollStatDTO.getSearchEndDttm();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

		if (StringUtils.isEmpty(searchStartDttm) && StringUtils.isEmpty(searchEndDttm)) {
			Date currentTime = new Date();
			searchEndDttm = sdf.format(currentTime);
			tbIotComCollStatDTO.setSearchEndDttm(searchEndDttm);

			cal.setTime(currentTime);
			cal.add(Calendar.DAY_OF_MONTH, -2);
			searchStartDttm = sdf.format(cal.getTime());
			tbIotComCollStatDTO.setSearchStartDttm(searchStartDttm);
		}

		Date startDate = null;
		Date endDate = null;

		try {
			startDate = sdf.parse(searchStartDttm);
			endDate = sdf.parse(searchEndDttm);
		} catch (ParseException e) {
			throw new BizException("datePasingError");
		}

		if (startDate.after(endDate)) {
			throw new BizException("invalidDate");
		}

		Date currentDate = startDate;
		while (currentDate.compareTo(endDate) <= 0) {
			searchDateList.add(sdf.format(currentDate));
			cal.setTime(currentDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			currentDate = cal.getTime();
		}
		tbIotComCollStatDTO.setSearchDateList(searchDateList);
		tbIotComCollStatDTO.setOrgNm(AuthUtils.getUser().getOrgNm());

		List<TbIotComCollStatDTO> reportList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotStatReportDAO.retrieveIotStatReportList");
		try {
			reportList = iotStatReportDAO.retrieveIotStatReportListNew(tbIotComCollStatDTO);
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

		Map<String, Object> returnData = new HashMap<String, Object>();

		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		List<String> viewSearchDateList = new ArrayList<>();
		try {
			for (String searchDate : searchDateList) {
				date = sdf.parse(searchDate);
				viewSearchDateList.add(f.format(date));
			}
		} catch (ParseException e) {
			throw new BizException("datePasingError");
		}

		returnData.put("roleCdId", roleCdId );
		returnData.put("custNm", custNm);
		returnData.put("loginId", AuthUtils.getUser().getUserId());
		returnData.put("searchDateList", viewSearchDateList);
		returnData.put("reportList", reportList);

		return returnData;
	}



	@SuppressWarnings("unchecked")
	@Override
	public XSSFWorkbook downloadIotStatReportList(TbIotComCollStatDTO tbIotComCollStatDTO) throws Exception {
		Map<String, Object> reportInfo = this.retrieveIotStatReportList(tbIotComCollStatDTO); // 통신/수집 성공률 조회
		List<TbIotComCollStatDTO> reportList = (List<TbIotComCollStatDTO>) reportInfo.get("reportList");
		List<String> searchDateList = (List<String>) reportInfo.get("searchDateList");

		XSSFWorkbook wb = new XSSFWorkbook();

		XSSFSheet sheet = wb.createSheet();
		XSSFRow header = sheet.createRow(0);
		XSSFRow header2 = sheet.createRow(1);
		XSSFCell cell = null;

		// 헤더 세팅
		for (int i = 0; i < titles.length; i++) {
			cell = header.createCell((short) i);
			cell.setCellStyle(ExcelUtils.headCellStyle(wb));
			cell.setCellValue(titles[i]);

			cell = header2.createCell((short) i);
			cell.setCellStyle(ExcelUtils.headCellStyle(wb));

			sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i)); // 행시작, 행종료, 열시작, 열종료 (자바배열과 같이 0부터 시작)
		}

		// Sub 헤더 세팅
		for (int i = 0; i < searchDateList.size(); i++) {
			cell = header.createCell((short) ((i * 2) + 4));
			cell.setCellStyle(ExcelUtils.headCellStyle(wb));
			cell.setCellValue(searchDateList.get(i));

			cell = header.createCell((short) ((i * 2) + 1) + 4);
			cell.setCellStyle(ExcelUtils.headCellStyle(wb));

			sheet.addMergedRegion(new CellRangeAddress(0, 0, (i * 2) + 4, ((i * 2) + 1) + 4)); // 행시작, 행종료, 열시작, 열종료
																								// (자바배열과 같이 0부터 시작)

			cell = header2.createCell((short) (i * 2) + 4);
			cell.setCellStyle(ExcelUtils.headCellStyle(wb));
			cell.setCellValue(subTitle[0]);

			cell = header2.createCell((short) ((i * 2) + 1) + 4);
			cell.setCellStyle(ExcelUtils.headCellStyle(wb));
			cell.setCellValue(subTitle[1]);
		}

		// 리스트 세팅
		Object value = null;
		int rowNum = 1;
		int startMergeRow = 2;
		String custNmPrv ="";
		String clsCdNmPrv ="";
		for (TbIotComCollStatDTO reportDTO : reportList) {
			XSSFRow row = sheet.createRow(++rowNum);

			int gid = reportDTO.getGid();

			if (gid == 1) { //  소계
				cell = row.createCell((short) 0);
				cell.setCellValue(new XSSFRichTextString(custNmPrv));
				cell = row.createCell((short) 1);
				cell.setCellValue(new XSSFRichTextString(clsCdNmPrv));
				cell = row.createCell((short) 2);
				cell.setCellValue(new XSSFRichTextString(reportDTO.getCustNm()));

			}else if (gid == 3) { // 총합계
				cell = row.createCell((short) 0);
				cell.setCellValue(new XSSFRichTextString(custNmPrv));
				cell = row.createCell((short) 1);
				cell.setCellValue(new XSSFRichTextString(reportDTO.getCustNm()));
				cell = row.createCell((short) 2);
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 2));

			} else {
				cell = row.createCell((short) 0);
				cell.setCellValue(new XSSFRichTextString(reportDTO.getCustNm()));
				cell = row.createCell((short) 1);
				cell.setCellValue(new XSSFRichTextString(reportDTO.getDevClsCdNm()));
				cell = row.createCell((short) 2);
				cell.setCellValue(new XSSFRichTextString(reportDTO.getDevMdlNm()));

				custNmPrv = reportDTO.getCustNm();
				clsCdNmPrv = reportDTO.getDevClsCdNm();
			}

			cell = row.createCell((short) 3);
//			cell = row.createCell((short) 4);
			cell.setCellValue(new XSSFRichTextString(String.valueOf(reportDTO.getDevCount()))); // 설치대수

			for (int i = 0; i < searchDateList.size(); i++) { // 통신성공률, 데이터수집률
				value = PropertyUtils.getSimpleProperty(reportDTO, rateDataNames.get(i).get("commRate"));
				cell = row.createCell((short) (i * 2) + 4);
				cell.setCellValue(
						new XSSFRichTextString((value == null) ? "" : String.format("%s%%", value.toString())));

				value = PropertyUtils.getSimpleProperty(reportDTO, rateDataNames.get(i).get("collRate"));
				cell = row.createCell((short) (i * 2) + 5);
				cell.setCellValue(
						new XSSFRichTextString((value == null) ? "" : String.format("%s%%", value.toString())));
			}
		}

		int rowCnt = sheet.getPhysicalNumberOfRows();
		int startIdx = 2;
		int startIdx1 = 2;
		for(int i = 3 ; i < rowCnt; i++) {
			XSSFRow row = sheet.getRow(i);

 			XSSFCell cell0 = row.getCell(0);
			XSSFCell cell1 = row.getCell(1);

			XSSFRow rowPrv = sheet.getRow(i-1);
			XSSFCell cellPrv = rowPrv.getCell(0);
			XSSFCell cellPrv1 = rowPrv.getCell(1);

			String tmpVal = cell0.getStringCellValue();
			String tmpValPrv = cellPrv.getStringCellValue();
			// cell 0 고객사 병합
			if(tmpVal.equals(tmpValPrv)) {
				if(i == rowCnt-1) {  // 마지막 병합
					sheet.addMergedRegion(new CellRangeAddress(startIdx, cell0.getRowIndex() , 0, 0));
				}
			}else {
				sheet.addMergedRegion(new CellRangeAddress(startIdx, cellPrv.getRowIndex() , 0, 0));
 				startIdx = cell0.getRowIndex();
			}

			String tmpVal1 = cell1.getStringCellValue();
			String tmpValPrv1 = cellPrv1.getStringCellValue();
			// cell 1 장비유형 병합
			if(tmpVal1.equals(tmpValPrv1)) {
				if(i == rowCnt-1) {  // 마지막 병합
				 	sheet.addMergedRegion(new CellRangeAddress(startIdx1, cell1.getRowIndex() , 1, 1));
				}
			}else {
				if("총합계".equals(tmpValPrv1)) {
	 				startIdx1 = cell1.getRowIndex();
				}else {
					sheet.addMergedRegion(new CellRangeAddress(startIdx1, cellPrv1.getRowIndex() , 1, 1));
	 				startIdx1 = cell1.getRowIndex();
				}
			}
		}
		return wb;
	}

}
