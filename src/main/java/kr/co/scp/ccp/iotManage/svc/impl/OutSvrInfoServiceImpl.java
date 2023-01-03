package kr.co.scp.ccp.iotManage.svc.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotManage.dao.OutSvrInfoDAO;
import kr.co.scp.ccp.iotManage.dto.TbIotOutSvrEDTO;
import kr.co.scp.ccp.iotManage.svc.OutSvrInfoService;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Service
public class OutSvrInfoServiceImpl implements OutSvrInfoService {

    @Autowired
    private OutSvrInfoDAO outSvrInfoDAO;


    public HashMap<String, Object> retrieveIotOutSvrInfoMList(TbIotOutSvrEDTO tbIotOutSvrEDTO) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        PageDTO pageDTO = new PageDTO();
        Integer count = 0;
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "exSvrInfoDAO.retrieveIotExSvrInfoMCount");
        try {
            count = outSvrInfoDAO.retrieveIotOutSvrInfoMCount(tbIotOutSvrEDTO);
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

        pageDTO.pageCalculate(count, tbIotOutSvrEDTO.getDisplayRowCount(), tbIotOutSvrEDTO.getCurrentPage());
        tbIotOutSvrEDTO.setStartPage(pageDTO.getRowStart());
        List<TbIotOutSvrEDTO> retrieveTbIotSvrInfoList = new ArrayList<TbIotOutSvrEDTO>();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExOutSvrInfoMList");
        try {
            retrieveTbIotSvrInfoList = outSvrInfoDAO.retrieveOutSvrInfoMList(tbIotOutSvrEDTO);
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
        resultMap.put("outSvrInfoMList", retrieveTbIotSvrInfoList);
        return resultMap;
    }
   
    public TbIotOutSvrEDTO retrieveIotOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO) {
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExOutSvrM");

        TbIotOutSvrEDTO result = new TbIotOutSvrEDTO();
        try {
            result = outSvrInfoDAO.retrieveOutSvrM(tbIotOutSvrEDTO);
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
    public TbIotOutSvrEDTO retrieveIotOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO) {
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExOutSvrInfo");
    	
    	TbIotOutSvrEDTO result = new TbIotOutSvrEDTO();
    	try {
    		result = outSvrInfoDAO.retrieveOutSvrInfo(tbIotOutSvrEDTO);
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

    public HashMap<String, Object> retrieveIotOutSvrInfoList(TbIotOutSvrEDTO tbIotOutSvrEDTO) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        PageDTO pageDTO = new PageDTO();
        Integer count = 0;
     
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "exSvrInfoDAO.retrieveIotExSvrInfoCount");
        try {
            count = outSvrInfoDAO.retrieveIotOutSvrInfoCount(tbIotOutSvrEDTO);
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
      

        pageDTO.pageCalculate(count, tbIotOutSvrEDTO.getDisplayRowCount(), tbIotOutSvrEDTO.getCurrentPage());
        tbIotOutSvrEDTO.setStartPage(pageDTO.getRowStart());
        List<TbIotOutSvrEDTO> retrieveTbIotSvrInfoList = new ArrayList<TbIotOutSvrEDTO>();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.retrieveExOutSvrInfoList");
        try {
            retrieveTbIotSvrInfoList = outSvrInfoDAO.retrieveOutSvrInfoList(tbIotOutSvrEDTO);
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
        resultMap.put("outSvrInfoList", retrieveTbIotSvrInfoList);
        return resultMap;
    }

    public void insertTbIoTOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO) {
    	String loginId = AuthUtils.getUser().getUserId();
    	tbIotOutSvrEDTO.setRegUsrId(loginId);

        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.registerExSvrM");
        try {
            outSvrInfoDAO.registerOutSvrM(tbIotOutSvrEDTO);
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

    public void insertTbIoTOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO) {
    	String loginId = AuthUtils.getUser().getUserId();
    	tbIotOutSvrEDTO.setRegUsrId(loginId);
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.registerExSvrInfo");
        try {
            outSvrInfoDAO.registerOutSvrInfo(tbIotOutSvrEDTO);
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

    public void updateTbIoTOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO) {
    	String loginId = AuthUtils.getUser().getUserId();
    	tbIotOutSvrEDTO.setRegUsrId(loginId);
        
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.updateTbIotOutSvrM");
        try {
            outSvrInfoDAO.updateTbIotOutSvrM(tbIotOutSvrEDTO);
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
    public void updateTbIoTOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO) {
    	String loginId = AuthUtils.getUser().getUserId();
    	tbIotOutSvrEDTO.setRegUsrId(loginId);
    	
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "exSvrInfoDAO.updateTbIotOutSvrInfo");
    	try {
    		outSvrInfoDAO.updateTbIotOutSvrInfo(tbIotOutSvrEDTO);
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
	public int retrieveDuplicatedSvrNm(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"outSvrInfoDAO.retrieveDuplicatedSvrNm");
		try {
			chkCnt = outSvrInfoDAO.retrieveDuplicatedSvrNm(tbIotOutSvrEDTO);
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
    @Override
    public int retrieveDuplicatedCust(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
    	int chkCnt = 0;
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
    			"outSvrInfoDAO.retrieveDuplicatedCust");
    	try {
    		chkCnt = outSvrInfoDAO.retrieveDuplicatedCust(tbIotOutSvrEDTO);
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
    @Override
    public int retrieveMakeToken(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
    	int chkCnt = 0;
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
    			"outSvrInfoDAO.retrieveMakeToken");
    	try {
    		chkCnt = outSvrInfoDAO.retrieveMakeToken(tbIotOutSvrEDTO);
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

	@Override
	public HashMap<String, Object> retrieveIotCustBySeq(TbIotCustUDTO tbIotCustDTO) throws BizException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		TbIotCustUDTO returnDto = null;
		ComInfoDto temp = null;
		tbIotCustDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outSvrInfoDAO.retrieveIotCustBySeq");
		try {
			returnDto = outSvrInfoDAO.retrieveIotCustBySeq(tbIotCustDTO);
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
		map.put("data", returnDto);
		
		return map;
	}

   
}
