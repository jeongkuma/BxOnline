package kr.co.scp.ccp.iotOutMessage.svc.impl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.iotOutMessage.dao.OutMessageSvrInfoDAO;
import kr.co.scp.ccp.iotOutMessage.dto.TbIotOutSvrMsgSetMDTO;
import kr.co.scp.ccp.iotOutMessage.svc.OutMessageSvrInfoService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class OutMessageSvrInfoServiceImpl implements OutMessageSvrInfoService {

    @Autowired
    private OutMessageSvrInfoDAO outMessageSvrInfoDAO;


    public HashMap<String, Object> retrieveIotOutSvrMsgList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        PageDTO pageDTO = new PageDTO();
        Integer count = 0;
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "exSvrMsgInfoDAO.retrieveIotExSvrMsgSetMCount");
        try {
            count = outMessageSvrInfoDAO.retrieveIotOutSvrMsgSetMCount(tbIotOutSvrMsgSetMDTO);
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

        pageDTO.pageCalculate(count, tbIotOutSvrMsgSetMDTO.getDisplayRowCount(), tbIotOutSvrMsgSetMDTO.getCurrentPage());
        tbIotOutSvrMsgSetMDTO.setStartPage(pageDTO.getRowStart());
        List<TbIotOutSvrMsgSetMDTO> retrieveTbIotSvrMsgList = new ArrayList<TbIotOutSvrMsgSetMDTO>();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExOutSvrMsgList");
        try {
            retrieveTbIotSvrMsgList = outMessageSvrInfoDAO.retrieveOutSvrMsgList(tbIotOutSvrMsgSetMDTO);
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
        resultMap.put("outSvrMsgList", retrieveTbIotSvrMsgList);
        return resultMap;
    }
    public HashMap<String, Object> retrieveIotOutSvrMsgHList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	PageDTO pageDTO = new PageDTO();
    	Integer count = 0;
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
    			"exSvrMsgInfoDAO.retrieveIotExSvrMsgSetHCount");
    	try {
    		count = outMessageSvrInfoDAO.retrieveIotOutSvrMsgSetHCount(tbIotOutSvrMsgSetMDTO);
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
    	
    	pageDTO.pageCalculate(count, tbIotOutSvrMsgSetMDTO.getDisplayRowCount(), tbIotOutSvrMsgSetMDTO.getCurrentPage());
    	tbIotOutSvrMsgSetMDTO.setStartPage(pageDTO.getRowStart());
    	List<TbIotOutSvrMsgSetMDTO> retrieveTbIotSvrMsgHList = new ArrayList<TbIotOutSvrMsgSetMDTO>();
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExOutSvrMsgList");
    	try {
    		retrieveTbIotSvrMsgHList = outMessageSvrInfoDAO.retrieveOutSvrMsgHList(tbIotOutSvrMsgSetMDTO);
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
    	resultMap.put("outSvrMsgHList", retrieveTbIotSvrMsgHList);
    	return resultMap;
    }
    @Override
    public HashMap<String, Object> retrieveGetSvcListByseqList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ComInfoDto temp = null;
		List<TbIotOutSvrMsgSetMDTO> retrieveGetSvcListByseqList = new ArrayList<TbIotOutSvrMsgSetMDTO>();
        tbIotOutSvrMsgSetMDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExGetSvcListByseqList");
		try {
			retrieveGetSvcListByseqList = outMessageSvrInfoDAO.retrieveGetSvcListByseqList(tbIotOutSvrMsgSetMDTO);
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
		map.put("data", retrieveGetSvcListByseqList);
		return map;
    }
    @Override
    public HashMap<String, Object> retrieveIotGetCustList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	ComInfoDto temp = null;
    	List<TbIotOutSvrMsgSetMDTO> retrieveIotGetCustList = new ArrayList<TbIotOutSvrMsgSetMDTO>();
    	tbIotOutSvrMsgSetMDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
    	
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExGetCustList");
    	try {
    		retrieveIotGetCustList = outMessageSvrInfoDAO.retrieveIotGetCustList(tbIotOutSvrMsgSetMDTO);
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
    	map.put("data", retrieveIotGetCustList);
    	return map;
    }
    @Override
    public HashMap<String, Object> retrieveIotGetDevClsList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	ComInfoDto temp = null;
    	List<TbIotOutSvrMsgSetMDTO> retrieveIotGetDevClsList = new ArrayList<TbIotOutSvrMsgSetMDTO>();
    	tbIotOutSvrMsgSetMDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
    	
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExGetDevClsList");
    	try {
    		retrieveIotGetDevClsList = outMessageSvrInfoDAO.retrieveIotGetDevClsList(tbIotOutSvrMsgSetMDTO);
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
    	map.put("data", retrieveIotGetDevClsList);
    	return map;
    }
    @Override
    public HashMap<String, Object> retrieveIotGetDevMdlList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	ComInfoDto temp = null;
    	List<TbIotOutSvrMsgSetMDTO> retrieveIotGetDevMdlList = new ArrayList<TbIotOutSvrMsgSetMDTO>();
    	tbIotOutSvrMsgSetMDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
    	
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExGetDevMdlList");
    	try {
    		retrieveIotGetDevMdlList = outMessageSvrInfoDAO.retrieveIotGetDevMdlList(tbIotOutSvrMsgSetMDTO);
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
    	map.put("data", retrieveIotGetDevMdlList);
    	return map;
    }
    @Override
    public HashMap<String, Object> retrieveIotOutMsgSendList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	ComInfoDto temp = null;
    	List<TbIotOutSvrMsgSetMDTO> retrieveIotOutMsgSendList = new ArrayList<TbIotOutSvrMsgSetMDTO>();
    	tbIotOutSvrMsgSetMDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
    	
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExOutMsgSendList");
    	try {
    		retrieveIotOutMsgSendList = outMessageSvrInfoDAO.retrieveIotOutMsgSendList(tbIotOutSvrMsgSetMDTO);
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
    	map.put("data", retrieveIotOutMsgSendList);
    	return map;
    }
    @Override
    public HashMap<String, Object> retrieveIotOutMsgRecieveList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	ComInfoDto temp = null;
    	List<TbIotOutSvrMsgSetMDTO> retrieveIotOutMsgRecieveList = new ArrayList<TbIotOutSvrMsgSetMDTO>();
    	tbIotOutSvrMsgSetMDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
    	
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExOutMsgRecieveList");
    	try {
    		retrieveIotOutMsgRecieveList = outMessageSvrInfoDAO.retrieveIotOutMsgRecieveList(tbIotOutSvrMsgSetMDTO);
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
    	map.put("data", retrieveIotOutMsgRecieveList);
    	return map;
    }
    
    public void insertTbIoTOutSvrMsgSet(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
    	String loginId = AuthUtils.getUser().getUserId();
    	tbIotOutSvrMsgSetMDTO.setRegUsrId(loginId);
    	
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exMessageSvrInfoDAO.registerExSvrMsgSet");
        try {
            outMessageSvrInfoDAO.registerOutSvrMsgSet(tbIotOutSvrMsgSetMDTO);
            insertTbIoTOutSvrMsgSetHis(tbIotOutSvrMsgSetMDTO);
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
    public void insertTbIoTOutSvrMsgSetHis(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
    	String loginId = AuthUtils.getUser().getUserId();
    	tbIotOutSvrMsgSetMDTO.setRegUsrId(loginId);
    	
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exMessageSvrInfoDAO.registerExSvrMsgSet");
    	try {
    		outMessageSvrInfoDAO.registerOutSvrMsgSetHis(tbIotOutSvrMsgSetMDTO);
    		
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
        public void updateTbIoTOutSvrMsgSet(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
    	String loginId = AuthUtils.getUser().getUserId();
    	tbIotOutSvrMsgSetMDTO.setRegUsrId(loginId);
    	
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exMessageSvrInfoDAO.updateExSvrMsgSet");
    	try {
    		outMessageSvrInfoDAO.updateOutSvrMsgSet(tbIotOutSvrMsgSetMDTO);
    		insertTbIoTOutSvrMsgSetHis(tbIotOutSvrMsgSetMDTO);
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
    
    public TbIotOutSvrMsgSetMDTO retrieveIotOutSvrMsg(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outExMessageSvrInfoDAO.retrieveExOutSvrMsg");

        TbIotOutSvrMsgSetMDTO result = new TbIotOutSvrMsgSetMDTO();
        try {
            result = outMessageSvrInfoDAO.retrieveOutSvrMsg(tbIotOutSvrMsgSetMDTO);
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
        return result;
    }
    public TbIotOutSvrMsgSetMDTO retrieveIotOutSvrMsgH(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outExMessageSvrInfoDAO.retrieveExOutSvrMsgH");
    	
    	TbIotOutSvrMsgSetMDTO result = new TbIotOutSvrMsgSetMDTO();
    	try {
    		result = outMessageSvrInfoDAO.retrieveOutSvrMsgH(tbIotOutSvrMsgSetMDTO);
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
    	return result;
    }
   @Override
   public XSSFWorkbook excelCreate(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"outMessageSvrInfoDAO.retrieveOutSvrMsgList");
		List<TbIotOutSvrMsgSetMDTO> retrieveOutSvrMsgList = null;
		
		try {
			retrieveOutSvrMsgList = outMessageSvrInfoDAO.retrieveOutSvrMsgList(tbIotOutSvrMsgSetMDTO);
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

		// excel
		XSSFWorkbook wb = new XSSFWorkbook();
		Map<String, String> title = new LinkedHashMap<String, String>();
		title.put("고객사명", "custNm");
		title.put("서비스명", "svcNm");
		title.put("장비 유형", "devClsCd");
		title.put("장비 모델", "devMdlCd");
		title.put("외부서버명", "serverNm");
		title.put("전문 구분", "tranCd");
		title.put("URI", "uri");
		ExcelUtils.createExcelFile(wb, retrieveOutSvrMsgList, title);
		return wb;
	}
   
   
   @Override
   @Transactional
       public boolean deleteTbIoTOutSvrMsg(HttpServletRequest request, TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
   	String loginId = AuthUtils.getUser().getUserId();
   	tbIotOutSvrMsgSetMDTO.setModUserId(loginId);
   	boolean result = false;
   	for(Long outMsgSetSeq : tbIotOutSvrMsgSetMDTO.getOutsvrmsgSeqList()) {
   		tbIotOutSvrMsgSetMDTO.setOutMsgSetSeq(outMsgSetSeq);
   		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exOutMessageSvrInfoDAO.deleteTbIotOutSvrMsg");
           try {
        	   outMessageSvrInfoDAO.deleteTbIotOutSvrMsg(tbIotOutSvrMsgSetMDTO);
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
       	result = true;
       }
   	return result;
   }
   @Override
   public int retrieveDuplicatedAdd(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
   	int chkCnt = 0;
   	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
   			"outMessageSvrInfoDAO.retrieveDuplicatedAdd");
   	try {
   		chkCnt = outMessageSvrInfoDAO.retrieveDuplicatedAdd(tbIotOutSvrMsgSetMDTO);
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
   	return chkCnt;
   }
  
   
}
