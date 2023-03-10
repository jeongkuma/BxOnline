package kr.co.scp.ccp.iotCtrl.svc.impl;

import java.net.URI;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import kr.co.scp.ccp.iotCtrl.parser.RequestCollection;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdPDTO;
import org.apache.commons.compress.utils.Lists;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCtrl.dao.IotCtrlDAO;
import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.scp.ccp.iotCtrl.svc.IotCtrlSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;


@Primary
@Slf4j
@Service
public class IotCtrlVCImpl implements IotCtrlSVC {

	@Autowired
	private IotCtrlDAO iotCtrlDAO;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${ctrl.imm-api-url}")
	private String CTRL_IMM_API_URL;

	private Gson GSON = new Gson();

	@Override
	public HashMap<String, Object> retrieveIotCtrlList(TbIotCtrlDTO tbIotCtrlDTO) throws BizException {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();
		tbIotCtrlDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
			// ??? ?????? ??????
			int totalCount = 0;
			if (AuthUtils.getUser() != null) {
				tbIotCtrlDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
				tbIotCtrlDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			}
			tbIotCtrlDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotCtrlDAO.retrieveTbIotCtrlCount");
			try {
				totalCount = iotCtrlDAO.retrieveTbIotCtrlCount(tbIotCtrlDTO);
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
			pageDto.pageCalculate(totalCount, tbIotCtrlDTO.getDisplayRowCount(), tbIotCtrlDTO.getCurrentPage());
			tbIotCtrlDTO.setStartPage(pageDto.getRowStart());

			// ??????
			List<TbIotCtrlDTO> retrieveTbIotCtrlList = new ArrayList<TbIotCtrlDTO>();
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotCtrlDAO.retrieveTbIotCtrlList");
			try {
				tbIotCtrlDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
				retrieveTbIotCtrlList = iotCtrlDAO.retrieveTbIotCtrlList(tbIotCtrlDTO);
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

			returnMap.put("dataList", retrieveTbIotCtrlList);
			returnMap.put("page", pageDto);


		return returnMap;
	}

	@Override
	public HashMap<String, Object> retrieveTbIotCtrlRsvList(TbIotCtrlDTO tbIotCtrlDTO) throws BizException {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();
		tbIotCtrlDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
		tbIotCtrlDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		tbIotCtrlDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());

			// ??????
			List<TbIotCtrlDTO> retrieveTbIotCtrlRsvList = new ArrayList<TbIotCtrlDTO>();
			ComInfoDto temp  = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotCtrlDAO.retrieveTbIotCtrlRsvList");
			try {
				retrieveTbIotCtrlRsvList = iotCtrlDAO.retrieveTbIotCtrlRsvList(tbIotCtrlDTO);
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

			returnMap.put("dataList", retrieveTbIotCtrlRsvList);
			returnMap.put("page", pageDto);

		return returnMap;
	}

	@Override
	public HashMap<String, Object> retrieveTbIotCtrlAttbList(TbIotCtrlDTO tbIotCtrlDTO) throws BizException {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();


		// ??????
		List<TbIotCtrlDTO> retrieveTbIotCtrlAttbList = new ArrayList<TbIotCtrlDTO>();
		ComInfoDto temp  = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCtrlDAO.retrieveTbIotCtrlAttbList");
		try {
			retrieveTbIotCtrlAttbList = iotCtrlDAO.retrieveTbIotCtrlAttbList(tbIotCtrlDTO);
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

		returnMap.put("dataList", retrieveTbIotCtrlAttbList);
		returnMap.put("page", pageDto);

		return returnMap;
	}

	@Override
	public HashMap<String, Object> deleteIotCtrlList(TbIotCtrlDTO tbIotCtrlDTOList) throws BizException {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> msg = new ArrayList<HashMap<String, Object>>();

		ComInfoDto temp = null;
		TbIotCtrlDTO tbIotCtrlDTO = null;
		TbIotCtrlDTO tbIotCtrlDTOPrcCd = null;

		//?????? ?????? ?????? ?????? ?????????
		HashMap<String, Object> msgMap = new HashMap<String, Object>();


		// ????????????????????? ??????
		for (int i = 0; i < tbIotCtrlDTOList.getEntrDevSeqAr().length; i++) {

			// ???????????? ??????
			for (int j = 0; j < tbIotCtrlDTOList.getCtrList().size(); j++) {
				tbIotCtrlDTO = tbIotCtrlDTOList.getCtrList().get(j);
				tbIotCtrlDTO.setEntrDevSeq(tbIotCtrlDTOList.getEntrDevSeqAr()[i]); // ????????????????????? ??????
				tbIotCtrlDTO.setPrcCd("11");

				// ???????????? ??????
				if (AuthUtils.getUser() != null) {
					tbIotCtrlDTO.setModUsrId(AuthUtils.getUser().getUserId());
				}

				// ???????????? ?????? ?????? ??????
				tbIotCtrlDTOPrcCd = iotCtrlDAO.retrieveTbIotCtlMprcCd(tbIotCtrlDTO);

				/** ?????? ????????? ??????
				 *  ?????? ????????? ?????? ??? ????????? ?????? ???????????? Skip
				 * */
				if(tbIotCtrlDTOPrcCd != null && "00".equals(tbIotCtrlDTOPrcCd.getPrcCd())) {
					tbIotCtrlDTO.setCtlSeq(tbIotCtrlDTOPrcCd.getCtlSeq()); //ctl_seq ??????
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCtrlDAO.deleteIotCtlM");
					try {
						iotCtrlDAO.deleteIotCtlM(tbIotCtrlDTO); // ???????????? ????????????
					} catch (DataIntegrityViolationException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (UncategorizedSQLException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (MyBatisSystemException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (BadSqlGrammarException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} finally {
						OmsUtils.endInnerOms(temp);
					}

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCtrlDAO.deleteIotCtlHist");
					try {
						iotCtrlDAO.deleteIotCtlHist(tbIotCtrlDTO); // ???????????? ????????????
					} catch (DataIntegrityViolationException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (UncategorizedSQLException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (MyBatisSystemException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (BadSqlGrammarException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} finally {
						OmsUtils.endInnerOms(temp);
					}
				} else {
					// ?????? ?????????
					// ???????????? ???????????? ????????????.
					// ?????? ?????? ??????
					msgMap.put("msg"+String.valueOf(i), "?????????????????? : ?????? -" + tbIotCtrlDTO.getDevMdlNm() +"("+tbIotCtrlDTO.getDevUuid()+") "+ tbIotCtrlDTO.getDevAttbCdNm() );
					msg.add(msgMap);
				}
			}
		}
		returnMap.put("msgList", msg);
		return returnMap;
	}

	@SuppressWarnings("unused")
	@Override
	public HashMap<String, Object> insertIoTCtrlRsv(HttpServletRequest request, TbIotCtrlDTO tbIotCtrlDTOList) throws BizException {
		// TODO Auto-generated method stub
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> msg = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> immMsg = new HashMap<String, Object>();

		ComInfoDto temp = null;
		TbIotCtrlDTO copyCtlDateDTO = null;
		TbIotCtrlDTO ctrlDtoPrcCd = null;

		int chkCnt = 0;

		//?????? ?????? ?????? ?????? ?????????
		HashMap<String, Object> msgMap = new HashMap<String, Object>();

		List<TbIotCtrlDTO> instantList = new ArrayList<TbIotCtrlDTO>();

		// ????????????????????? ??????
		for (int i = 0; i < tbIotCtrlDTOList.getEntrDevSeqAr().length; i++) {

			tbIotCtrlDTOList.setEntrDevSeq(tbIotCtrlDTOList.getEntrDevSeqAr()[i]);


			// ???????????? ??????
			boolean isInstance = false;
			for (int j = 0; j < tbIotCtrlDTOList.getCtrList().size(); j++) {
				TbIotCtrlDTO ctrlDto = new TbIotCtrlDTO();


				ctrlDto = tbIotCtrlDTOList.getCtrList().get(j);
				ctrlDto.setEntrDevSeq(tbIotCtrlDTOList.getEntrDevSeqAr()[i]); // ????????????????????? ??????
				ctrlDto.setCtn(tbIotCtrlDTOList.getEntrDevList().get(i).getCtn()); // ????????????????????????.????????????
				ctrlDto.setEntrNo(tbIotCtrlDTOList.getEntrDevList().get(i).getEntrNo()); // ????????????????????????.????????????
				ctrlDto.setEntityId(tbIotCtrlDTOList.getEntrDevList().get(i).getEntityId()); // ????????????????????????.?????????ID
				ctrlDto.setPrcCd("00");
				ctrlDto.setDevTime(null);


				// ???????????? ??????
				if (AuthUtils.getUser() != null) {
					ctrlDto.setRegUsrId(AuthUtils.getUser().getUserId());
					ctrlDto.setModUsrId(AuthUtils.getUser().getUserId());
				}

				// ???????????? ?????? ?????? ??????
				ctrlDtoPrcCd = iotCtrlDAO.retrieveTbIotCtlMprcCd(ctrlDto);
				if (ctrlDtoPrcCd != null) {
					ctrlDto.setCtlSeq(ctrlDtoPrcCd.getCtlSeq()); //ctl_seq ??????
				}
				/** ?????? ????????? ??????
				 *  '?????????' ??? Skip - ?????? ????????? ??????
				 */
				if (ctrlDtoPrcCd != null && !"10".equals(ctrlDtoPrcCd.getPrcCd())) {

					copyCtlDateDTO = (TbIotCtrlDTO) ctrlDto.clone();
					/*if("00".equals(CtrlDtoPrcCd.getPrcCd())) copyCtlDateDTO.setCtlDate(""); //????????? ?????? ??????????????? ???????????????.*/

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ctrlDao.updateIotCtlM");
					try {
						iotCtrlDAO.updateIotCtlM(copyCtlDateDTO); // ????????????
					} catch (DataIntegrityViolationException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (UncategorizedSQLException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (MyBatisSystemException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (BadSqlGrammarException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} finally {
						OmsUtils.endInnerOms(temp);
					}

					//????????????????????? ?????? ???????????? ?????????
				} else if (ctrlDtoPrcCd == null) {

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ctrlDao.insertIotCtlM");
					try {
						iotCtrlDAO.insertIotCtlM(ctrlDto); // ????????????
					} catch (DataIntegrityViolationException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (UncategorizedSQLException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (MyBatisSystemException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (BadSqlGrammarException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} finally {
						OmsUtils.endInnerOms(temp);
					}

				} else {
					msgMap.put("msg" + String.valueOf(i), "?????????????????? : ?????? -" + ctrlDto.getDevMdlNm() + "(" + ctrlDto.getEntityId() + ") " + ctrlDto.getDevAttbCdNm());
					msg.add(msgMap);
				}


				/**?????? History ??????
				 * 10 '?????????' ??? ?????? Skip
				 * ???????????? '??????'
				 */
				if (ctrlDtoPrcCd != null && "10".equals(ctrlDtoPrcCd.getPrcCd())) {

				} else if (ctrlDtoPrcCd != null && "00".equals(ctrlDtoPrcCd.getPrcCd())) {
					/**
					 * ???????????? '????????????' ????????????
					 */
					copyCtlDateDTO = (TbIotCtrlDTO) ctrlDto.clone();
					copyCtlDateDTO.setCtlDate("");

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ctrlDao.updateIotCtlHist");
					try {
						iotCtrlDAO.updateIotCtlHist(copyCtlDateDTO); // ??????????????????
					} catch (DataIntegrityViolationException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (UncategorizedSQLException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (MyBatisSystemException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (BadSqlGrammarException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} finally {
						OmsUtils.endInnerOms(temp);
					}

				} else { //???????????? ???????????? ????????? or ??????(01),????????????(98),????????????(99) ??????
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ctrlDao.insertIotCtlHist");
					try {
						iotCtrlDAO.insertIotCtlHist(ctrlDto); // ??????????????????
					} catch (DataIntegrityViolationException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
					} catch (UncategorizedSQLException e) {
						OmsUtils.expInnerOms(temp, e);
						throw e;
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

				// ???????????????
				if (null == ctrlDtoPrcCd) {
					// ??????????????? ???
					if (null != ctrlDto.getCtlType() && ctrlDto.getCtlType().equals("GN00000113")) {
						isInstance = true;
//						immMsg = callImmApi(request, ctrlDto);
					}

				} else if (null != ctrlDtoPrcCd && !"10".equals(ctrlDtoPrcCd.getPrcCd())) {
					// ?????? ???????????????
					if (null != ctrlDto.getCtlType() && ctrlDto.getCtlType().equals("GN00000113")) {
						isInstance = true;
//						immMsg=callImmApi(request, ctrlDto);
					}
				}

				if (isInstance) {
					TbIotCtrlDTO news = this.avoidDupl(ctrlDto);
					instantList.add(news);
				}
			}


		}
		returnMap.put("msgList", msg);
		returnMap.put("instantList", instantList);
//		returnMap.put("immMsg", immMsg);
		return returnMap;

	}

	public TbIotCtrlDTO avoidDupl(TbIotCtrlDTO param) {
		TbIotCtrlDTO news = new TbIotCtrlDTO();
		news.setEntityId(param.getEntityId());
		news.setCommand(param.getCommand());
		news.setDevMdlCd(param.getDevMdlCd());
		news.setPamKey(param.getPamKey());
		news.setDevVal(param.getDevVal());
		news.setCtlSeq(param.getCtlSeq());

		return news;
	}


	public HashMap<String, Object> callImmApi(HttpServletRequest request, TbIotCtrlDTO tbIotCtrlDTO, List<TbIotCtrlDTO> instantList) throws BizException {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();

		String deviceEntityId = null;
		String model = null;
		String command = "";

		List<Map<String, String>> conData = Lists.newArrayList();
//		for(TbIotCtrlDTO dto : instantList) {
//			Map<String, String> conMap = Maps.newHashMap();
//			deviceEntityId = dto.getEntityId();
//			model = dto.getDevMdlCd();
//
//			conMap.put(dto.getPamKey(), dto.getDevVal());
//			conMap.put("ctlSeq", dto.getCtlSeq());
//			conData.add(conMap);
//			// conMap ?????? ????????? ??????
//			command = dto.getCommand();
//		}
		Map<String, String> conMap = Maps.newHashMap();
		deviceEntityId = tbIotCtrlDTO.getEntityId();
		model = tbIotCtrlDTO.getDevMdlCd();
		conMap.put(tbIotCtrlDTO.getPamKey(), tbIotCtrlDTO.getDevVal());
		conMap.put("ctlSeq", tbIotCtrlDTO.getCtlSeq());
		conData.add(conMap);
		command = tbIotCtrlDTO.getCommand();

		HashMap<String, Object> parsingMap = new HashMap<String, Object>();

		String gsonStr = "";
		RequestCollection reqData = new RequestCollection();
		ArrayList<RequestCollection.ColData> datas = new ArrayList<RequestCollection.ColData>();
		RequestCollection.ColData data = new RequestCollection.ColData();

		try {
			data.setEntityid(deviceEntityId);
			data.setModel(model);
			data.setCon(conData);

			datas.add(data);

			reqData.setCommand(command);
			reqData.setData(datas);

			gsonStr = GSON.toJson(reqData);
			// Map ?????????
			parsingMap = GSON.fromJson(gsonStr, parsingMap.getClass());

		} catch (Exception e) {
			log.error("ERROR!! DataCollectionRequestParser - parameters : {}, msg : {}", parsingMap.toString(), e.getMessage());
		}

		HttpHeaders headers = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);

		ResponseEntity<JsonNode> resp = null;

		try {
			HttpMethod method = HttpMethod.POST;
			URI uri = new URI(CTRL_IMM_API_URL);

			JsonNode requestJsonBody = new ObjectMapper().readTree(new Gson().toJson(parsingMap));
			RequestEntity<JsonNode> requestEntity = new RequestEntity<JsonNode>(requestJsonBody, headers, method, uri);
			resp = restTemplate.exchange(requestEntity, JsonNode.class);

			if (resp.getStatusCode().is2xxSuccessful()) {
				log.debug("SUCCESS  - resp : {}, requestEntity : {}", resp, requestEntity);
				returnMap.put("msg","??????????????????");
			} else {
				log.debug("FAIL  - resp : {}, requestEntity : {}", resp, requestEntity);
				throw new BizException("ctrlImmFalse");
			}
		} catch (Exception e) {
			throw new BizException("ctrlImmFalse");
		}
		return returnMap;
	}

}
