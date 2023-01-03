package kr.co.scp.ccp.iotOrg.svc.impl;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeDataDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeLiAttrDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeStatusDTO;
import kr.co.scp.ccp.iotOrg.dao.IotOrgDAO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgOptDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgRDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgUDTO;
import kr.co.scp.ccp.iotOrg.svc.IotOrgSvc;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Primary
@Service
public class IotOrgSvcImpl implements IotOrgSvc {

	@Autowired
	IotOrgDAO iotOrgDAO;

	@Override
	public List<TbIotOrgOptDTO> retrieveIotOrgByUsr() throws BizException {
		TbIotOrgDTO tbIotOrgDTO = new TbIotOrgDTO();
		List<TbIotOrgOptDTO> iotOrgOptDTOs = new ArrayList<TbIotOrgOptDTO>();
		String custSeq = AuthUtils.getUser().getCustSeq();
		tbIotOrgDTO.setCustSeq(custSeq);

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotOrgDAO.retrieveIotOrgByUsr");
		try {
			iotOrgOptDTOs = iotOrgDAO.retrieveIotOrgByUsr(tbIotOrgDTO);
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

		return iotOrgOptDTOs;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<TbIotTreeDTO> retrieveIotOrg(TbIotOrgDTO tbIotOrgDTO) throws BizException {

		String custSeq = AuthUtils.getUser().getCustSeq();

		String orgSeq = "";
		if(null != tbIotOrgDTO.getOrgSeq()) {
			orgSeq = tbIotOrgDTO.getOrgSeq();
		}

		List<TbIotOrgDTO> orgList = new ArrayList<TbIotOrgDTO>();

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotOrgDAO.retrieveIotOrg");
		try {
			orgList = iotOrgDAO.retrieveIotOrg(custSeq);
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

		List<TbIotTreeDTO> returnTreeData = new ArrayList<TbIotTreeDTO>();
		int listSize = orgList.size();

		// 관리화면 최상위 데이터 생성
		TbIotOrgDTO firstOrg = orgList.get(0);
		TbIotTreeDTO firstData = new TbIotTreeDTO();
		TbIotTreeDataDTO tmpTreeData = new TbIotTreeDataDTO();
		TbIotTreeStatusDTO tmpFirstTreeStatusDto = new TbIotTreeStatusDTO();
		tmpTreeData.setParent("#");
		tmpTreeData.setSelfSeq("0");
		firstData.setId(firstOrg.getOrgSeq());
		firstData.setParent("#");
		firstData.setText(firstOrg.getOrgNm() + " (" +firstOrg.getUseYn() +")");
		firstData.setLi_attr(new TbIotTreeLiAttrDTO());
		firstData.setData(tmpTreeData);
		tmpFirstTreeStatusDto.setOpened(true);
		if(!StringUtils.isEmpty(orgSeq) && firstOrg.getOrgSeq().equals(orgSeq)) {
			tmpFirstTreeStatusDto.setSelected(true);
		}
		firstData.setState(tmpFirstTreeStatusDto);
		returnTreeData.add(firstData);

		for (int i = 1; i < listSize; i++) {
			TbIotOrgDTO tmpOrgDTO = (TbIotOrgDTO) orgList.get(i);
			TbIotTreeDTO tmpTreeDto = new TbIotTreeDTO();
			TbIotTreeLiAttrDTO tmpTreeLiAttrDto = new TbIotTreeLiAttrDTO();
			TbIotTreeDataDTO tmpTreeDataDto = new TbIotTreeDataDTO();
			TbIotTreeStatusDTO tmpTreeStatusDto = new TbIotTreeStatusDTO();
			if (tmpOrgDTO.getOrgLvl().equals("1")) {
				tmpOrgDTO.setUpOrgSeq("0");
			}
			tmpTreeStatusDto.setOpened(true);
			tmpTreeStatusDto.setUseYn(tmpOrgDTO.getUseYn());
			if(!StringUtils.isEmpty(orgSeq) && tmpOrgDTO.getOrgSeq().equals(orgSeq)) {
				tmpTreeStatusDto.setSelected(true);
			}
			tmpTreeDataDto.setSelfSeq(tmpOrgDTO.getOrgSeq());
			tmpTreeDataDto.setParent(tmpOrgDTO.getUpOrgSeq());

			tmpTreeDto.setId(tmpOrgDTO.getOrgSeq());
			tmpTreeDto.setParent(tmpOrgDTO.getUpOrgSeq());
			tmpTreeDto.setLi_attr(tmpTreeLiAttrDto);
			tmpTreeDto.setText(tmpOrgDTO.getOrgNm() + " (" +tmpOrgDTO.getUseYn() +")");
			tmpTreeDto.setData(tmpTreeDataDto);
			tmpTreeDto.setState(tmpTreeStatusDto);
			returnTreeData.add(tmpTreeDto);
		}
		return returnTreeData;
	}

	@Override
	public TbIotOrgRDTO retrieveIotOrgBySeq(TbIotOrgDTO tbIotOrgDTO) throws BizException {
		TbIotOrgRDTO iotOrgRDTO = new TbIotOrgRDTO();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotOrgDAO.retrieveIotOrgBySeq");
		try {
			iotOrgRDTO = iotOrgDAO.retrieveIotOrgBySeq(tbIotOrgDTO);
			if(iotOrgRDTO.getUpOrgNm().equals("0")) iotOrgRDTO.setUpOrgNm("");
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
		return iotOrgRDTO;
	}

	@Override
	public String retrieveIotOrgNmChk(TbIotOrgDTO tbIotOrgDTO) throws BizException {
		String chk = "";
		String custSeq = AuthUtils.getUser().getCustSeq();
		tbIotOrgDTO.setCustSeq(custSeq);
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotOrgDAO.retrieveIotOrgNmChk");
		try {
			chk = iotOrgDAO.retrieveIotOrgNmChk(tbIotOrgDTO);
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

		return chk;
	}

	@Override
	public void createIotOrg(TbIotOrgDTO tbIotOrgDTO) throws BizException {
		userAuthCheck();
		String userId = AuthUtils.getUser().getUserId();
		if (null == tbIotOrgDTO.getCustSeq()) {
			tbIotOrgDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}
		tbIotOrgDTO.setRegUsrId(userId);
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotOrgDAO.createIotOrg");
		try {
			iotOrgDAO.createIotOrg(tbIotOrgDTO);
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
	public String retrieveIotOrgNm(TbIotOrgDTO tbIotOrgDTO) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotOrgDAO.retrieveIotOrgNm");
		tbIotOrgDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		String orgSeq;
		try {
			orgSeq = iotOrgDAO.retrieveIotOrgNm(tbIotOrgDTO);
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
		return orgSeq;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void updateIotOrg(TbIotOrgUDTO tbIotOrgUDTO) throws BizException {
		userAuthCheck();
		ComInfoDto temp = null;
		String custSeq = AuthUtils.getUser().getCustSeq();
		String modUsrId = AuthUtils.getUser().getUserId();
		tbIotOrgUDTO.setCustSeq(custSeq);

		// 조직명 변경 체크
		boolean isOrgNmChanged = tbIotOrgUDTO.getOrgPath().contains(tbIotOrgUDTO.getOrgNm());

		// 순번 변동이 있을 때
		if (null != tbIotOrgUDTO.getOriginOrder()) {
			int originOrder = Integer.valueOf(tbIotOrgUDTO.getOriginOrder());
			int changeOrder = Integer.valueOf(tbIotOrgUDTO.getOrgOrder());

			if (originOrder != changeOrder) {

				TbIotOrgDTO inputDto = new TbIotOrgDTO();
				inputDto.setCustSeq(custSeq);
				inputDto.setOrgLvl(tbIotOrgUDTO.getOrgLvl());
				inputDto.setOrgOrder(tbIotOrgUDTO.getOrgOrder());
				List<TbIotOrgDTO> returnDto = new ArrayList<TbIotOrgDTO>();
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotOrgDAO.retrieveIotOrgByOrder");
				try {
					returnDto = iotOrgDAO.retrieveIotOrgByOrder(inputDto);
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

				if (returnDto.size() > 0) {
					for (Iterator iterator = returnDto.iterator(); iterator.hasNext();) {
						TbIotOrgDTO tbIotOrgDTO = (TbIotOrgDTO) iterator.next();
						int tmpOrgOrder = Integer.valueOf(tbIotOrgDTO.getOrgOrder());
						// 큰 수는 그대로 insert
						if (tmpOrgOrder < changeOrder) {
							// 순서를 바꿀 때 동일한 순번을 만났을 때 -> 원래의 숫자와 비교해서
						} else if (tmpOrgOrder == changeOrder) {
							// 비교대상이 원래 번호보다 orgOrder가 클때 ( 순번의 우선순위가 낮을 때 )
							if (tmpOrgOrder > originOrder) {
								tbIotOrgDTO.setOrgOrder(String.valueOf(tmpOrgOrder - 1));
								// 비교대상이 원래 번호보다 orgOrder가 작을때 ( 순번의 우선순위가 높을 때 )
							} else if (tmpOrgOrder < originOrder) {
								tbIotOrgDTO.setOrgOrder(String.valueOf(tmpOrgOrder + 1));
							}
							tbIotOrgDTO.setModUsrId(modUsrId);
							temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
									"iotOrgDAO.updateIotOrg");
							try {
								iotOrgDAO.updateIotOrg(tbIotOrgDTO);
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

							if (tbIotOrgDTO.getUseYn().equals("n")) {
								this.checkChildren(tbIotOrgDTO);
							}
						} else if (tmpOrgOrder > changeOrder) {

						}
					}
					TbIotOrgDTO tmpDto = this.convertOrgDto(tbIotOrgUDTO, modUsrId);
					List<TbIotOrgDTO> updateDtoList = null;
					if(!isOrgNmChanged) {
						updateDtoList = changeOrgPath(tmpDto, tbIotOrgUDTO.getOrgNm());
					} else {
						updateDtoList = new ArrayList<TbIotOrgDTO>();
						updateDtoList.add(tmpDto);
					}

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
							"iotOrgDAO.updateIotOrg");
					try {
						for (Iterator iterator = updateDtoList.iterator(); iterator.hasNext();) {
							TbIotOrgDTO tbIotOrgDTO = (TbIotOrgDTO) iterator.next();
							iotOrgDAO.updateIotOrg(tbIotOrgDTO);
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

					if (tmpDto.getUseYn().equals("N")) {
						this.checkChildren(tmpDto);
					}
				}
			}

		} else {
			TbIotOrgDTO tmpDto = this.convertOrgDto(tbIotOrgUDTO, modUsrId);
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotOrgDAO.updateIotOrg");

			List<TbIotOrgDTO> updateDtoList = null;
			if(!isOrgNmChanged) {
				updateDtoList = changeOrgPath(tmpDto, tbIotOrgUDTO.getOrgNm());
			} else {
				updateDtoList = new ArrayList<TbIotOrgDTO>();
				updateDtoList.add(tmpDto);
			}

			try {
				for (Iterator iterator = updateDtoList.iterator(); iterator.hasNext();) {
					TbIotOrgDTO tbIotOrgDTO = (TbIotOrgDTO) iterator.next();
					iotOrgDAO.updateIotOrg(tbIotOrgDTO);
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
			if (tmpDto.getUseYn().equals("N")) {
				this.checkChildren(tmpDto);
			}
		}

	}

	private TbIotOrgDTO convertOrgDto(TbIotOrgUDTO tbIotOrgUDTO, String modUsrId) {
		TbIotOrgDTO changedDto = new TbIotOrgDTO();
		changedDto.setCustSeq(tbIotOrgUDTO.getCustSeq());
		changedDto.setUseYn(tbIotOrgUDTO.getUseYn());
		changedDto.setOrgSeq(tbIotOrgUDTO.getOrgSeq());
		changedDto.setOrgNm(tbIotOrgUDTO.getOrgNm());
		changedDto.setOrgLvl(tbIotOrgUDTO.getOrgLvl());
		changedDto.setOrgOrder(tbIotOrgUDTO.getOrgOrder());
		changedDto.setModUsrId(modUsrId);
		changedDto.setOrgPath(tbIotOrgUDTO.getOrgPath());
		return changedDto;
	}

	private void checkChildren(TbIotOrgDTO dto) {

		List<TbIotOrgDTO> childrenArr = this.retrieveChildrenOrg(dto);

		while (true) {
			if (childrenArr.size() > 0) {
				for (Iterator iterator2 = childrenArr.iterator(); iterator2.hasNext();) {
					TbIotOrgDTO tbIotOrgDTO2 = (TbIotOrgDTO) iterator2.next();
					ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
							"iotOrgDAO.deleteIotOrg");
					try {
						iotOrgDAO.deleteIotOrg(tbIotOrgDTO2);
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
			} else {
				break;
			}
		}
	}

	public List<TbIotOrgDTO> retrieveChildrenOrg(TbIotOrgDTO dto) {
		List<TbIotOrgDTO> childrenArr;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotOrgDAO.retrieveOrgChildren");
		try {
			childrenArr = iotOrgDAO.retrieveOrgChildren(dto);
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
		return childrenArr;
	}

	public List<TbIotOrgDTO> changeOrgPath(TbIotOrgDTO tmpDto, String orgNm) {
		List<TbIotOrgDTO> updateDtoList = new ArrayList<TbIotOrgDTO>();
		int lvl = Integer.parseInt(tmpDto.getOrgLvl());
		StringBuilder newPath = new StringBuilder();
		newPath.append("/");
		String[] pathList = tmpDto.getOrgPath().split("/");
		for (int i = 1; i < pathList.length; i++) {
			if( (i== lvl) ) {
				newPath.append(orgNm);
			} else {
				newPath.append(pathList[i]);
			}
			if( (i+1) < pathList.length) {
				newPath.append("/");
			}
		}
		tmpDto.setOrgPath(newPath.toString());
		updateDtoList.add(tmpDto);

		List<TbIotOrgDTO> childrenArr = this.retrieveChildrenOrg(tmpDto);
		for (Iterator iterator = childrenArr.iterator(); iterator.hasNext();) {
			TbIotOrgDTO tbIotOrgDTO2 = (TbIotOrgDTO) iterator.next();
			StringBuilder sb = new StringBuilder();
			sb.append("/");
			String[] pathArr = tbIotOrgDTO2.getOrgPath().split("/");
			for (int i = 1; i < pathArr.length; i++) {
				if(i== lvl ) {
					sb.append(orgNm);
				} else {
					sb.append(pathArr[i]);
				}
				if( (i + 1) < pathArr.length) {
					sb.append("/");
				}
			}
			tbIotOrgDTO2.setOrgPath(sb.toString());
			updateDtoList.add(tbIotOrgDTO2);
		}
		return updateDtoList;
	}

	public void userAuthCheck() {
		String[] adminRoleArr = {  "GN00000035", "GN00000036", "GN00000033", "GN00000034" };

		if (!ArrayUtils.contains(adminRoleArr, AuthUtils.getUser().getRoleCdId())) {
			throw new BizException("userNotFound");
		}
	}

	@Override
	public HashMap<String, Object> retrieveIotCustOrg() throws BizException {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		TbIotOrgDTO tbIotOrgDTO = new TbIotOrgDTO();
		List<TbIotOrgOptDTO> iotOrgOptDTOs = new ArrayList<TbIotOrgOptDTO>();
		String custSeq = AuthUtils.getUser().getCustSeq();
		tbIotOrgDTO.setCustSeq(custSeq);
		tbIotOrgDTO.setOrgNm(AuthUtils.getUser().getOrgNm());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotOrgDAO.retrieveIotCustOrg");
		try {
			iotOrgOptDTOs = iotOrgDAO.retrieveIotCustOrg(tbIotOrgDTO);
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

		List<TbIotOrgDTO> custOrgList = null;
		tbIotOrgDTO.setOrgLvl("1");
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotOrgDAO.retrieveIotOrgByOrder");
		try {
			custOrgList = iotOrgDAO.retrieveIotOrgByOrder(tbIotOrgDTO);
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

		String custOrgSeq = custOrgList.get(0).getOrgSeq();

		returnMap.put("orgList", iotOrgOptDTOs);
		returnMap.put("custOrgSeq", custOrgSeq);
		return returnMap;
	}
}
