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
			// 총 개수 조회
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

			// 조회
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

			// 조회
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


		// 조회
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

		//다중 선택 처리 실패 메세지
		HashMap<String, Object> msgMap = new HashMap<String, Object>();


		// 가입별장비순번 루프
		for (int i = 0; i < tbIotCtrlDTOList.getEntrDevSeqAr().length; i++) {

			// 제어항목 루프
			for (int j = 0; j < tbIotCtrlDTOList.getCtrList().size(); j++) {
				tbIotCtrlDTO = tbIotCtrlDTOList.getCtrList().get(j);
				tbIotCtrlDTO.setEntrDevSeq(tbIotCtrlDTOList.getEntrDevSeqAr()[i]); // 가입별장비순번 셋팅
				tbIotCtrlDTO.setPrcCd("11");

				// 계정정보 셋팅
				if (AuthUtils.getUser() != null) {
					tbIotCtrlDTO.setModUsrId(AuthUtils.getUser().getUserId());
				}

				// 예약여부 확인 로직 필요
				tbIotCtrlDTOPrcCd = iotCtrlDAO.retrieveTbIotCtlMprcCd(tbIotCtrlDTO);

				/** 제어 마스터 취소
				 *  예약 취소는 예약 일 경우만 가능 그외에는 Skip
				 * */
				if(tbIotCtrlDTOPrcCd != null && "00".equals(tbIotCtrlDTOPrcCd.getPrcCd())) {
					tbIotCtrlDTO.setCtlSeq(tbIotCtrlDTOPrcCd.getCtlSeq()); //ctl_seq 셋팅
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCtrlDAO.deleteIotCtlM");
					try {
						iotCtrlDAO.deleteIotCtlM(tbIotCtrlDTO); // 제어정보 예약취소
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
						iotCtrlDAO.deleteIotCtlHist(tbIotCtrlDTO); // 제어이력 예약취소
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
					// 처리 완료후
					// 실패항목 리스트를 보여준다.
					// 상태 변경 불가
					msgMap.put("msg"+String.valueOf(i), "예약취소불가 : 장비 -" + tbIotCtrlDTO.getDevMdlNm() +"("+tbIotCtrlDTO.getDevUuid()+") "+ tbIotCtrlDTO.getDevAttbCdNm() );
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

		//다중 선택 처리 실패 메세지
		HashMap<String, Object> msgMap = new HashMap<String, Object>();

		List<TbIotCtrlDTO> instantList = new ArrayList<TbIotCtrlDTO>();

		// 가입별장비순번 루프
		for (int i = 0; i < tbIotCtrlDTOList.getEntrDevSeqAr().length; i++) {

			tbIotCtrlDTOList.setEntrDevSeq(tbIotCtrlDTOList.getEntrDevSeqAr()[i]);


			// 제어항목 루프
			boolean isInstance = false;
			for (int j = 0; j < tbIotCtrlDTOList.getCtrList().size(); j++) {
				TbIotCtrlDTO ctrlDto = new TbIotCtrlDTO();


				ctrlDto = tbIotCtrlDTOList.getCtrList().get(j);
				ctrlDto.setEntrDevSeq(tbIotCtrlDTOList.getEntrDevSeqAr()[i]); // 가입별장비순번 셋팅
				ctrlDto.setCtn(tbIotCtrlDTOList.getEntrDevList().get(i).getCtn()); // 가입별장비마스터.식별번호
				ctrlDto.setEntrNo(tbIotCtrlDTOList.getEntrDevList().get(i).getEntrNo()); // 가입별장비마스터.가입번호
				ctrlDto.setEntityId(tbIotCtrlDTOList.getEntrDevList().get(i).getEntityId()); // 가입별장비마스터.엔티티ID
				ctrlDto.setPrcCd("00");
				ctrlDto.setDevTime(null);


				// 계정정보 셋팅
				if (AuthUtils.getUser() != null) {
					ctrlDto.setRegUsrId(AuthUtils.getUser().getUserId());
					ctrlDto.setModUsrId(AuthUtils.getUser().getUserId());
				}

				// 예약여부 확인 로직 필요
				ctrlDtoPrcCd = iotCtrlDAO.retrieveTbIotCtlMprcCd(ctrlDto);
				if (ctrlDtoPrcCd != null) {
					ctrlDto.setCtlSeq(ctrlDtoPrcCd.getCtlSeq()); //ctl_seq 셋팅
				}
				/** 제어 마스터 처리
				 *  '처리중' 은 Skip - 실패 메세지 생성
				 */
				if (ctrlDtoPrcCd != null && !"10".equals(ctrlDtoPrcCd.getPrcCd())) {

					copyCtlDateDTO = (TbIotCtrlDTO) ctrlDto.clone();
					/*if("00".equals(CtrlDtoPrcCd.getPrcCd())) copyCtlDateDTO.setCtlDate(""); //예약일 경우 제어일자를 넣지않는다.*/

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ctrlDao.updateIotCtlM");
					try {
						iotCtrlDAO.updateIotCtlM(copyCtlDateDTO); // 제어정보
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

					//마스터테이블에 값이 존재하지 않을시
				} else if (ctrlDtoPrcCd == null) {

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ctrlDao.insertIotCtlM");
					try {
						iotCtrlDAO.insertIotCtlM(ctrlDto); // 제어정보
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
					msgMap.put("msg" + String.valueOf(i), "제어변경불가 : 장비 -" + ctrlDto.getDevMdlNm() + "(" + ctrlDto.getEntityId() + ") " + ctrlDto.getDevAttbCdNm());
					msg.add(msgMap);
				}


				/**제어 History 처리
				 * 10 '처리중' 일 경우 Skip
				 * 상태값이 '예약'
				 */
				if (ctrlDtoPrcCd != null && "10".equals(ctrlDtoPrcCd.getPrcCd())) {

				} else if (ctrlDtoPrcCd != null && "00".equals(ctrlDtoPrcCd.getPrcCd())) {
					/**
					 * 상태값이 '예약일때' 업데이트
					 */
					copyCtlDateDTO = (TbIotCtrlDTO) ctrlDto.clone();
					copyCtlDateDTO.setCtlDate("");

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ctrlDao.updateIotCtlHist");
					try {
						iotCtrlDAO.updateIotCtlHist(copyCtlDateDTO); // 제어이력정보
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

				} else { //데이터가 존재하지 않을시 or 완료(01),응답실패(98),전송실패(99) 경우
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ctrlDao.insertIotCtlHist");
					try {
						iotCtrlDAO.insertIotCtlHist(ctrlDto); // 제어이력정보
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

				// 예약등록시
				if (null == ctrlDtoPrcCd) {
					// 즉시제어일 때
					if (null != ctrlDto.getCtlType() && ctrlDto.getCtlType().equals("GN00000113")) {
						isInstance = true;
//						immMsg = callImmApi(request, ctrlDto);
					}

				} else if (null != ctrlDtoPrcCd && !"10".equals(ctrlDtoPrcCd.getPrcCd())) {
					// 예약 업데이트시
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
//			// conMap 상세 테스트 필요
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
			// Map 캐스팅
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
				returnMap.put("msg","즉시제어성공");
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
