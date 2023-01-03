package kr.co.scp.ccp.iotEDev.svc.impl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.iotEDev.dao.IotEDevDetSmsDAO;
import kr.co.scp.ccp.iotEDev.dto.*;
import kr.co.scp.ccp.iotEDev.svc.IotEDevDetSmsSVC;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Primary
@Service
public class IotEDevDetSmsSVCImpl implements IotEDevDetSmsSVC {

	@Autowired
	private IotEDevDetSmsDAO iotEDevDetSmsDAO;

	@Value("${file.upload-dir}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@Override
	public HashMap<String, Object> retrieveEDevDetSmsList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ComInfoDto db = null;
		PageDTO pageDTO = new PageDTO();

		if (AuthUtils.getUser() != null) {
			tbIotEdevDetSmsDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}
		tbIotEdevDetSmsDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		tbIotEdevDetSmsDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
		Integer count = 0;
		db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEDevDetSmsDAO.retrieveEDevDetSmsListCount");
		try {
			tbIotEdevDetSmsDTO.setCustLoginId(AuthUtils.getUser().getCustId());
			count = iotEDevDetSmsDAO.retrieveEDevDetSmsListCount(tbIotEdevDetSmsDTO);

		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db);
		}

		// 페이징
		pageDTO.pageCalculate(count, tbIotEdevDetSmsDTO.getDisplayRowCount(), tbIotEdevDetSmsDTO.getCurrentPage());
		tbIotEdevDetSmsDTO.setStartPage(pageDTO.getRowStart());

		db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEDevDetSmsDAO.retrieveEDevDetSmsList");
		try {

			List<TbIotEdevDetSmsDTO> retrieveEDevDetSmsList = iotEDevDetSmsDAO
					.retrieveEDevDetSmsList(tbIotEdevDetSmsDTO);
			resultMap.put("boardList", retrieveEDevDetSmsList);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db);
		}


		resultMap.put("page", pageDTO);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> retrieveEDevDetDevAttbList(TbIotEdevDetAttbDTO tbIotEdevDetAttbDTO)
			throws BizException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<TbIotEdevDetAttbDTO> EDevList = null;
		List<TbIotEdevDetAttbDTO> SDevList = null;
		List<TbIotEdevDetAttbDTO> attbList = new ArrayList<TbIotEdevDetAttbDTO>();
		tbIotEdevDetAttbDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		tbIotEdevDetAttbDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
		tbIotEdevDetAttbDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		ComInfoDto db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEDevDetSmsDAO.retrieveEDevDetDevAttbList_EDev");
		try {
			EDevList = iotEDevDetSmsDAO.retrieveEDevDetDevAttbList_EDev(tbIotEdevDetAttbDTO);

		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (Exception e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db);
		}

		db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEDevDetSmsDAO.retrieveEDevDetDevAttbList_SDev");
		try {
			SDevList = iotEDevDetSmsDAO.retrieveEDevDetDevAttbList_SDev(tbIotEdevDetAttbDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (Exception e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db);
		}

		String devClsCd = "";
		String devMdlCd = "";
		String devAttbCdId = "";
		String devDetSetCdId = "";
		String devAttbCdNm = "";
		String devDetSetCdNm = "";
		String detSetCond = "";
		String sendMsg = "";
		String sendMsgTmpl = "";
		String sendGubun = "";
		String detSetSeq = "";
		String entrDevSeq = "";
		String recvTelNo = "";
		String curValSeq = "";
		String detApplyYn = "";


		boolean chkFlag = false;
		 for (int i = 0; i < SDevList.size(); i++) {

			String sDevClsCd = SDevList.get(i).getDevClsCd();
			String sDevMdlCd = SDevList.get(i).getDevMdlCd();
			String sDevAttbCdId = SDevList.get(i).getDevAttbCdId();
			String sDeDetSetCdId = SDevList.get(i).getDevDetSetCdId();
			String sDevAttbCdNm = SDevList.get(i).getDevAttbCdNm();
			String sDevDetSetCdNm = SDevList.get(i).getDevDetSetCdNm();
			String sDetSetCond = SDevList.get(i).getDetSetCond();
			String sSendMsg = SDevList.get(i).getSendMsg();
			String sSendMsgTmpl = SDevList.get(i).getSendMsgTmpl();
			String sSendGubun = SDevList.get(i).getSendGubun();
			String sDetSetSeq = SDevList.get(i).getDetSetSeq();
			String sDevDetSetCdId = SDevList.get(i).getDevDetSetCdId();
			String sEntrDevSeq = SDevList.get(i).getEntrDevSeq();
			String sRecvTelNo = SDevList.get(i).getRecvTelNo();
			String sCurValSeq = SDevList.get(i).getCurValSeq();
			String sDetApplyYn = SDevList.get(i).getDetApplyYn();

			for (int j = 0; j < EDevList.size(); j++) {

				String eDevClsCd = EDevList.get(j).getDevClsCd();
				String eDevMdlCd = EDevList.get(j).getDevMdlCd();
				String eDevAttbCdId = EDevList.get(j).getDevAttbCdId();
				String eDeDetSetCdId = EDevList.get(j).getDevDetSetCdId();
				String eDevAttbCdNm = EDevList.get(j).getDevAttbCdNm();
				String eDevDetSetCdNm = EDevList.get(j).getDevDetSetCdNm();
				String eDetSetCond = EDevList.get(j).getDetSetCond();
				String eSendMsg = EDevList.get(j).getSendMsg();
				String eSendMsgTmpl = EDevList.get(j).getSendMsgTmpl();
				String eSendGubun = EDevList.get(j).getSendGubun();
				String eDetSetSeq = EDevList.get(j).getDetSetSeq();
				String eDevDetSetCdId = EDevList.get(j).getDevDetSetCdId();
				String eEntrDevSeq = EDevList.get(j).getEntrDevSeq();
				String eRecvTelNo = EDevList.get(j).getRecvTelNo();
				String eCurValSeq = EDevList.get(j).getCurValSeq();
				String eDetApplyYn = EDevList.get(j).getDetApplyYn();

				if( sDevClsCd.equals(eDevClsCd) && sDevMdlCd.equals(eDevMdlCd) &&
					sDevAttbCdId.equals(eDevAttbCdId) && sDeDetSetCdId.equals(eDeDetSetCdId) ) {

					devClsCd = eDevClsCd;
					devMdlCd = eDevMdlCd;
					devAttbCdId = eDevAttbCdId;
					devAttbCdNm = eDevAttbCdNm;
					devDetSetCdNm = eDevDetSetCdNm;
					detSetCond = eDetSetCond;
					sendMsg = eSendMsg;
					sendMsgTmpl = eSendMsgTmpl;
					sendGubun = eSendGubun;
					detSetSeq = eDetSetSeq;
					devDetSetCdId = eDevDetSetCdId;
					entrDevSeq = eEntrDevSeq;
					recvTelNo = eRecvTelNo;
					curValSeq = eCurValSeq;
					detApplyYn = eDetApplyYn;

					chkFlag = true;
					break;

				}
			 }

			 TbIotEdevDetAttbDTO attb = new TbIotEdevDetAttbDTO();
			 if(chkFlag) {
				 attb.setDevClsCd(devClsCd);
				 attb.setDevMdlCd(devMdlCd);
				 attb.setDevAttbCdId(devAttbCdId);
				 attb.setDevDetSetCdId(devDetSetCdId);
				 attb.setDevAttbCdNm(devAttbCdNm);
				 attb.setDevDetSetCdNm(devDetSetCdNm);
				 attb.setDetSetCond(detSetCond);
				 attb.setSendMsg(sendMsg);
				 attb.setSendMsgTmpl(sendMsgTmpl);
				 attb.setSendGubun(sendGubun);
				 attb.setDetSetSeq(detSetSeq);
				 attb.setDevDetSetCdId(devDetSetCdId);
				 attb.setEntrDevSeq(entrDevSeq);
				 attb.setRecvTelNo(recvTelNo);
				 attb.setCurValSeq(curValSeq);
				 attb.setDetApplyYn(detApplyYn);
			 }else {

				 attb.setDevClsCd(sDevClsCd);
				 attb.setDevMdlCd(sDevMdlCd);
				 attb.setDevAttbCdId(sDevAttbCdId);
				 attb.setDevDetSetCdId(sDevDetSetCdId);
				 attb.setDevAttbCdNm(sDevAttbCdNm);
				 attb.setDevDetSetCdNm(sDevDetSetCdNm);
				 attb.setDetSetCond(sDetSetCond);
				 attb.setSendMsg(sSendMsg);
				 attb.setSendMsgTmpl(sSendMsgTmpl);
				 attb.setSendGubun(sSendGubun);
				 attb.setDetSetSeq(sDetSetSeq);
				 attb.setDevDetSetCdId(sDevDetSetCdId);
				 attb.setEntrDevSeq(sEntrDevSeq);
				 attb.setRecvTelNo(sRecvTelNo);
				 attb.setCurValSeq(sCurValSeq);
				 attb.setDetApplyYn(sDetApplyYn);

			 }
			 attbList.add(attb);
			 chkFlag = false;
		 }

		resultMap.put("attbList", attbList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> retrieveEDevDetUserList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) throws BizException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		tbIotEdevDetSmsDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		tbIotEdevDetSmsDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
		ComInfoDto db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEDevDetSmsDAO.retrieveEDevDetUserList");
		try {
			List<TbIotEdevDetSmsDTO> retrieveEDevDetUserList = iotEDevDetSmsDAO
					.retrieveEDevDetUserList(tbIotEdevDetSmsDTO);
			resultMap.put("userList", retrieveEDevDetUserList);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db);
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> retrieveEDevDetUserListByDetSetSeq(TbIotEdevDetRcvUsrDTO tbIotEdevDetRcvUsrDTO)
			throws BizException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ComInfoDto db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEDevDetSmsDAO.retrieveEDevDetUserListByDetSetSeq");
		try {
			List<TbIotEdevDetRcvUsrDTO> retrieveEDevDetUserList = iotEDevDetSmsDAO
					.retrieveEDevDetUserListByDetSetSeq(tbIotEdevDetRcvUsrDTO);
			resultMap.put("userList", retrieveEDevDetUserList);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db);
		}
		return resultMap;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void createEDevDetSms(TbIotEdevDetSmsDTO detSmsDTO) throws BizException {

		String loginId = "";

		if (AuthUtils.getUser() != null) {
			loginId = AuthUtils.getUser().getUserId();
		}

		ComInfoDto db = null;
		try {

			String devClsCd = detSmsDTO.getDevClsCd();
			String devClsCdNm = detSmsDTO.getDevClsCdNm();
			String devMdlCd = detSmsDTO.getDevMdlCd();
			String devMdlNm = detSmsDTO.getDevMdlNm();
			String sendGubun = detSmsDTO.getSendGubun();
			String sendMsg = detSmsDTO.getSendMsg();
			String sendMsgTmpl = detSmsDTO.getSendMsgTmpl();
			String recvTelNo = detSmsDTO.getRecvTelNo();

			List<TbIotEdevDetSmsUsrDTO> usrDTOList = new ArrayList();
			List<TbIotEdevDetSmsUsrDTO> usrDelDTOList = new ArrayList();

			for (int k = 0; k < detSmsDTO.getEntrList().size(); k++) {

				String jsonStrEntr = detSmsDTO.getEntrList().get(k).toString();
				JSONParser jsonParserEntr = new JSONParser();
				JSONObject jsonObjEntr = (JSONObject) jsonParserEntr.parse(jsonStrEntr);
				String entrDevSeq = jsonObjEntr.get("entrDevSeq").toString();

				for (int i = 0; i < detSmsDTO.getAttbList().size(); i++) {

					String jsonStr = detSmsDTO.getAttbList().get(i).toString();
					JSONParser jsonParser = new JSONParser();
					JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonStr);

					TbIotEdevDetSetDTO detSetDTO = new TbIotEdevDetSetDTO();

					detSetDTO.setEntrDevSeq(entrDevSeq);
//					detSetDTO.setCurValSeq(jsonObj.get("curValSeq").toString());
					detSetDTO.setDevDetSetCdId(jsonObj.get("devDetSetCdId").toString());
					detSetDTO.setDevDetSetCdNm(jsonObj.get("devDetSetCdNm").toString());
					detSetDTO.setDetSetCond(jsonObj.get("detSetCond").toString());
					detSetDTO.setDetApplyYn(jsonObj.get("detApplyYn").toString());
					detSetDTO.setDevClsCd(devClsCd);
					detSetDTO.setDevClsCdNm(devClsCdNm);
					detSetDTO.setDevMdlCd(devMdlCd);
					detSetDTO.setDevMdlNm(devMdlNm);
					detSetDTO.setSendGubun(sendGubun);
					detSetDTO.setSendMsg(sendMsg);
					detSetDTO.setSendMsgTmpl(sendMsgTmpl);
					detSetDTO.setRecvTelNo(recvTelNo);
					detSetDTO.setRegUsrId(loginId);

					TbIotEdevDetAttbDTO curValSeqDTO = new TbIotEdevDetAttbDTO();
					curValSeqDTO.setDevClsCd(devClsCd);
					curValSeqDTO.setDevMdlCd(devMdlCd);
					curValSeqDTO.setEntrDevSeq(entrDevSeq);
					curValSeqDTO.setDevAttbCdId(jsonObj.get("devAttbCdId").toString());

					db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
							"iotEDevDetSmsDAO.retrieveEDevDetDevCurValSeq");
					TbIotEdevDetAttbDTO retCurValSeqDTO = iotEDevDetSmsDAO.retrieveEDevDetDevCurValSeq(curValSeqDTO);
					if(retCurValSeqDTO != null) {
						detSetDTO.setCurValSeq(retCurValSeqDTO.getCurValSeq());

						if("N".equals(detSetDTO.getDetApplyYn())) {
							db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
									"iotEDevDetSmsDAO.updateEDevCurVal");
							iotEDevDetSmsDAO.updateEDevCurVal(detSetDTO);
						}
					}

					db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
							"iotEDevDetSmsDAO.retrieveEDevDetSetChk");
					TbIotEdevDetSetDTO retDetSetDTO = iotEDevDetSmsDAO.retrieveEDevDetSetChk(detSetDTO);

					if (retDetSetDTO != null) {
						String eDevDetSetSeq = retDetSetDTO.getEDevDetSetSeq();
						detSetDTO.setEDevDetSetSeq(eDevDetSetSeq);
						db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
								"iotEDevDetSmsDAO.updateEDevDetSet");
						iotEDevDetSmsDAO.updateEDevDetSet(detSetDTO);

						String detSetSeq = eDevDetSetSeq;

						TbIotEdevDetSmsUsrDTO usrDelDTO = new TbIotEdevDetSmsUsrDTO();
						usrDelDTO.setEDevDetSetSeq(detSetSeq);
//						db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//								"iotEDevDetSmsDAO.deleteEDevDetRcvUsr");
//						iotEDevDetSmsDAO.deleteEDevDetRcvUsr(usrDelDTO);
						usrDelDTOList.add(usrDelDTO);
						for (int j = 0; j < detSmsDTO.getRcvList().size(); j++) {

							String jsonStrRcv = detSmsDTO.getRcvList().get(j).toString();
							JSONParser jsonParserRcv = new JSONParser();
							JSONObject jsonObjRcv = (JSONObject) jsonParserRcv.parse(jsonStrRcv);

							TbIotEdevDetSmsUsrDTO usrDTO = new TbIotEdevDetSmsUsrDTO();
							usrDTO.setEDevDetSetSeq(detSetSeq);
							usrDTO.setUsrSeq(jsonObjRcv.get("usrSeq").toString());
							usrDTO.setUsrLoginId(jsonObjRcv.get("usrLoginId").toString());
							usrDTO.setRegUsrId(loginId);

//							db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//									"iotEDevDetSmsDAO.insertEDevDetSms");
//							iotEDevDetSmsDAO.insertEDevDetSms(usrDTO);
							usrDTOList.add(usrDTO);
						}
					} else {

						db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
								"iotEDevDetSmsDAO.insertEDevDetSet");
						iotEDevDetSmsDAO.insertEDevDetSet(detSetDTO);

						String detSetSeq = detSetDTO.getEDevDetSetSeq();

						TbIotEdevDetSmsUsrDTO usrDelDTO = new TbIotEdevDetSmsUsrDTO();
						usrDelDTO.setEDevDetSetSeq(detSetSeq);
//						db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//								"iotEDevDetSmsDAO.deleteEDevDetRcvUsr");
//						iotEDevDetSmsDAO.deleteEDevDetRcvUsr(usrDelDTO);
						usrDelDTOList.add(usrDelDTO);
						for (int j = 0; j < detSmsDTO.getRcvList().size(); j++) {

							String jsonStrRcv = detSmsDTO.getRcvList().get(j).toString();
							JSONParser jsonParserRcv = new JSONParser();
							JSONObject jsonObjRcv = (JSONObject) jsonParserRcv.parse(jsonStrRcv);

							TbIotEdevDetSmsUsrDTO usrDTO = new TbIotEdevDetSmsUsrDTO();
							usrDTO.setEDevDetSetSeq(detSetSeq);
							usrDTO.setUsrSeq(jsonObjRcv.get("usrSeq").toString());
							usrDTO.setUsrLoginId(jsonObjRcv.get("usrLoginId").toString());
							usrDTO.setRegUsrId(loginId);

//							iotEDevDetSmsDAO.insertEDevDetSms(usrDTO);
							usrDTOList.add(usrDTO);

						}
					}
				}
			}

			db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotEDevDetSmsDAO.deleteEDevDetRcvUsrAll");
			iotEDevDetSmsDAO.deleteEDevDetRcvUsrAll(usrDelDTOList);

			if(usrDTOList.size() > 0) {
				db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotEDevDetSmsDAO.insertEDevDetSms");
				iotEDevDetSmsDAO.insertEDevDetSms(usrDTOList);

			}

		} catch (ParseException e) {
			e.printStackTrace();
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new BizException("JsonParseException");
		}

	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deleteEDevDetRcvUsr(TbIotEdevDetSmsUsrDTO tbIotEdevDetSmsUsrDTO) throws BizException {

		try {

			String detSetSeq = tbIotEdevDetSmsUsrDTO.getDetSetSeq();
			for (int j = 0; j < tbIotEdevDetSmsUsrDTO.getDelList().size(); j++) {

				String jsonStrRcv = tbIotEdevDetSmsUsrDTO.getDelList().get(j).toString();
				JSONParser jsonParserRcv = new JSONParser();
				JSONObject jsonObjRcv = (JSONObject) jsonParserRcv.parse(jsonStrRcv);

				TbIotEdevDetSmsUsrDTO usrDTO = new TbIotEdevDetSmsUsrDTO();
				usrDTO.setEDevDetSetSeq(detSetSeq);
				usrDTO.setUsrSeq(jsonObjRcv.get("usrSeq").toString());
				usrDTO.setUsrLoginId(jsonObjRcv.get("usrLoginId").toString());

				iotEDevDetSmsDAO.deleteEDevDetRcvUsr(usrDTO);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizException("JsonParseException");
		}
	}

	@Override
	public HashMap<String, Object> retrieveEDevDetMessageList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ComInfoDto db = null;

		if (AuthUtils.getUser() != null) {
			tbIotEdevDetSmsDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}
		tbIotEdevDetSmsDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
//		tbIotEdevDetSmsDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());

		db = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEDevDetSmsDAO.retrieveEDevDetMessageList");
		try {

			List<TbIotEdevDetSmsDTO> retrieveEDevDetMessageList = iotEDevDetSmsDAO
					.retrieveEDevDetMessageList(tbIotEdevDetSmsDTO);
			resultMap.put("boardList", retrieveEDevDetMessageList);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db);
		}

		return resultMap;
	}

}
