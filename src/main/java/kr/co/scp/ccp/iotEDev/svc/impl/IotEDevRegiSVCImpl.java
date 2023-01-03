package kr.co.scp.ccp.iotEDev.svc.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.StringUtils;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.auiot.common.util.WebCommUtil;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevMSVC;
import kr.co.scp.ccp.iagEvent.svc.IagSvcDevSVC;
import kr.co.scp.ccp.iotEDev.dao.IotEDevRegiDAO;
import kr.co.scp.ccp.iotEDev.dto.*;
import kr.co.scp.ccp.iotEDev.svc.IotEDevRegiSVC;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Primary
@Slf4j
@Service
public class IotEDevRegiSVCImpl implements IotEDevRegiSVC {

    static ObjectMapper oMapper = new ObjectMapper();

    @Autowired
    private FileMessageSourceConfig fileMessageSourceConfig;

    @Autowired
    private IotEDevRegiDAO iotEDevRegiDAO;

    @Autowired
    private IagEntrDevMSVC iagEntrDevMSVC;
    @Autowired
    private IagSvcDevSVC iagSvcDevSVC;

    @Autowired
    FileServiceUtil fileService;

    @Autowired
    private BrdFileListDAO brdFileListDAO;


    @Value("${file.upload-dir}")
    private String FILE_UPLOAD_PATH;

    @Value("${file.upload-dir-devImg}")
    private String IMG_FILE_UPLOAD_PATH;

    @Value("${file.get-devImg}")
    private String IMG_FILE_GET_PATH;

    @Value("${file.max-size}")
    private int FILE_MAX_SIZE;

    @Override
    public int retrieveCtnDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        ComInfoDto temp = null;
        int cnt = 0;
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveCtnDup");
        try {
            cnt = iotEDevRegiDAO.retrieveCtnDup(tbIotEDevRegiDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }
        return cnt;
    }

    @Override
    public int retrieveManfSerialNoDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        ComInfoDto temp = null;
        tbIotEDevRegiDTO.setManfSerialNo(StringUtils.leftPad(tbIotEDevRegiDTO.getManfSerialNo(), 20, '0'));
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveManfSerialNoDup");
        int cnt = 0;
        try {
            cnt = iotEDevRegiDAO.retrieveManfSerialNoDup(tbIotEDevRegiDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }
        return cnt;
    }

    @Override
    public int retrieveInstNoDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        ComInfoDto temp = null;
        int cnt = 0;
        if (StringUtils.isNotEmpty(tbIotEDevRegiDTO.getInstNo())) {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveInstNoDup");
            try {
                cnt = iotEDevRegiDAO.retrieveInstNoDup(tbIotEDevRegiDTO);
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
        return cnt;
    }

    @Override
    public int retrieveUuidDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        ComInfoDto temp = null;
        int cnt = 0;
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveUuidDup");
        try {
            cnt = iotEDevRegiDAO.retrieveUuidDup(tbIotEDevRegiDTO);
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
        return cnt;
    }

    @Override
    public String createUuid(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        return null;
    }
//
//	@Override
//	public String createUuid(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
//		List<TbIotIagSvcDevDTO> tbIotIagSvcDevDTOList = iagSvcDevSVC.retrieveTbIotIagSvcDev(tbIotEDevRegiDTO.getDevMdlCd());
//		if(tbIotIagSvcDevDTOList.size() <= 0)
//			return null;
//		tbIotEDevRegiDTO.setManfSerialNo(StringUtils.leftPad(tbIotEDevRegiDTO.getManfSerialNo(), 20, '0'));
//		return oneM2MUtil.deviceEntityIdByMac(NODE_TYPE.ASN, tbIotEDevRegiDTO.getCtn(), tbIotEDevRegiDTO.getManfSerialNo(), tbIotIagSvcDevDTOList.get(0).getOm2mSvcCd(), true);
//	}

    @Override
    public int retrieveUnameDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        ComInfoDto temp = null;
        int cnt = 0;
        int result = 0;
        if (StringUtils.isNotEmpty(tbIotEDevRegiDTO.getDevUname())) {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveUnameDup");
            try {
                cnt = iotEDevRegiDAO.retrieveUnameDup(tbIotEDevRegiDTO);
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
        return cnt;
    }

    @Override
    public void createEntrDev(HttpServletRequest request, TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        if (StringUtils.isEmpty(tbIotEDevRegiDTO.getHldrCustNo())) {
            tbIotEDevRegiDTO.setHldrCustNo(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "defaultHldrCustNo"));
        }
        // 가입번호
        if (StringUtils.isEmpty(tbIotEDevRegiDTO.getEntrNo())) {
            tbIotEDevRegiDTO.setEntrNo(iotEDevRegiDAO.retrieveMaxEntrNo());
        }
        // 가입번호
        if (StringUtils.isEmpty(tbIotEDevRegiDTO.getAceno())) {
            tbIotEDevRegiDTO.setAceno(iotEDevRegiDAO.retrieveMaxAceNo());
        }

        if (null == tbIotEDevRegiDTO.getOrgSeq() || "".equals(tbIotEDevRegiDTO.getOrgSeq())) {
            tbIotEDevRegiDTO.setOrgSeq(AuthUtils.getUser().getOrgSeq());
        }

        // 가입장비 마스터 등록
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.createEntrDev");
        if (AuthUtils.getUser() != null) {
            tbIotEDevRegiDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
            tbIotEDevRegiDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
            tbIotEDevRegiDTO.setRegUserId(AuthUtils.getUser().getUserId());
        }

        if (AuthUtils.getUser() != null) {
            tbIotEDevRegiDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
            tbIotEDevRegiDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
            tbIotEDevRegiDTO.setOrgSeq(AuthUtils.getUser().getOrgSeq());
            tbIotEDevRegiDTO.setRegUserId(AuthUtils.getUser().getUserId());
        }

        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.createEntrDev");
        try {
            iotEDevRegiDAO.createEntrDev(tbIotEDevRegiDTO);
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

        // 가입장비 위치 등록
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "iotEDevRegiDAO.createEntrDevInsLoc");
        try {
            iotEDevRegiDAO.createEntrDevInsLoc(tbIotEDevRegiDTO);
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

        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "iotEDevRegiDAO.createEntrSDevAttbCurVal");
        try {
            iotEDevRegiDAO.createEntrSDevAttbCurVal(tbIotEDevRegiDTO);
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

        // TB_iOT_COL_VAL 등록
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "iotEDevRegiDAO.createEntrDevColVal");
        try {
            iotEDevRegiDAO.createEntrDevColVal(tbIotEDevRegiDTO);
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
    public String retrieveMaxHldrNo() throws BizException {
        String result = "";
        ComInfoDto temp = null;
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveMaxHldrNo");
        try {
            result = iotEDevRegiDAO.retrieveMaxHldrNo();
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
    public String retrieveMaxEntrNo() throws BizException {
        String result = "";
        ComInfoDto temp = null;
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveMaxEntrNo");
        try {
            result = iotEDevRegiDAO.retrieveMaxEntrNo();
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
    public String retrieveMaxAceNo() throws BizException {
        String result = "";
        ComInfoDto temp = null;
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveMaxAceNo");
        try {
            result = iotEDevRegiDAO.retrieveMaxAceNo();
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
    public TbIotEDevRegiDTO retrieveEntrDevInfo(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        ComInfoDto temp = null;
        List<EDevCurValDTO> eDevCurValDTORn = new ArrayList<EDevCurValDTO>();
        tbIotEDevRegiDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveEntrDev");
        try {
            tbIotEDevRegiDTO = iotEDevRegiDAO.retrieveEntrDev(tbIotEDevRegiDTO);
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

        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveEntrDevCurVal");
        try {
            eDevCurValDTORn = iotEDevRegiDAO.retrieveEntrDevCurVal(tbIotEDevRegiDTO);
            if (eDevCurValDTORn != null) {
                tbIotEDevRegiDTO.setEntrDevCurValues(eDevCurValDTORn);
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

        return tbIotEDevRegiDTO;
    }

    @Override
    public void updateEntrDev(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        ComInfoDto temp = null;

        // 기본정보 세팅(LG)
        if (StringUtils.isEmpty(tbIotEDevRegiDTO.getHldrCustNo())) {
//			tbIotEDevRegiDTO.setHldrCustNo(iotEDevRegiDAO.retrieveMaxHldrNo());
            tbIotEDevRegiDTO.setHldrCustNo(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "defaultHldrCustNo"));
        }
        // 가입번호
        if (StringUtils.isEmpty(tbIotEDevRegiDTO.getEntrNo())) {
            tbIotEDevRegiDTO.setEntrNo(iotEDevRegiDAO.retrieveMaxEntrNo());
        }
        // 가입번호
        if (StringUtils.isEmpty(tbIotEDevRegiDTO.getAceno())) {
            tbIotEDevRegiDTO.setAceno(iotEDevRegiDAO.retrieveMaxAceNo());
        }

        if (AuthUtils.getUser() != null) {
            tbIotEDevRegiDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
            tbIotEDevRegiDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
            tbIotEDevRegiDTO.setRegUserId(AuthUtils.getUser().getUserId());
            tbIotEDevRegiDTO.setModUserId(AuthUtils.getUser().getUserId());
        }



        if("".equals(tbIotEDevRegiDTO.getDevRegiDt()) || "Invalid date".equals(tbIotEDevRegiDTO.getDevRegiDt())) {
            tbIotEDevRegiDTO.setDevRegiDt(null);
        }

        if("".equals(tbIotEDevRegiDTO.getInstDttm()) || "Invalid date".equals(tbIotEDevRegiDTO.getInstDttm())) {
            tbIotEDevRegiDTO.setInstDttm(null);
        }

        if (null != tbIotEDevRegiDTO.getStatusCd() && (tbIotEDevRegiDTO.getStatusCd().equals("C") || tbIotEDevRegiDTO.getStatusCd().equals("R"))) {
            tbIotEDevRegiDTO.setCustSeq(null);
            tbIotEDevRegiDTO.setOrgSeq(null);
        }

        // 가입장비 마스터 정보 UPDATE
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.updateEntrDev");
        try {
            iotEDevRegiDAO.updateEntrDev(tbIotEDevRegiDTO);
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

        // 가입장비 위치테이블 확인
        int istCnt = 0;
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.chkEntrInstLoc");
        try {
            istCnt = iotEDevRegiDAO.chkEntrInstLoc(tbIotEDevRegiDTO);
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

        // 가입장비 위치 update
        if (istCnt > 0) {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotEDevRegiDAO.updateEntrDevInsLoc");
            try {
                iotEDevRegiDAO.updateEntrDevInsLoc(tbIotEDevRegiDTO);
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
        } else {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotEDevRegiDAO.createEntrDevInsLoc");
            try {
                iotEDevRegiDAO.createEntrDevInsLoc(tbIotEDevRegiDTO);
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


        // 가입장비 가입속성 등록
        if (tbIotEDevRegiDTO.getEntrDevCurValues() != null && !tbIotEDevRegiDTO.getEntrDevCurValues().isEmpty() && tbIotEDevRegiDTO.getEntrDevCurValues().size() > 0) {
            for (EDevCurValDTO eDevCurValDTO : tbIotEDevRegiDTO.getEntrDevCurValues()) {
                eDevCurValDTO.setEntrDevSeq(tbIotEDevRegiDTO.getEntrDevSeq());
                eDevCurValDTO.setDevClsCd(tbIotEDevRegiDTO.getDevClsCd());
                eDevCurValDTO.setDevClsCdNm(tbIotEDevRegiDTO.getDevClsCdNm());
                eDevCurValDTO.setDevMdlCd(tbIotEDevRegiDTO.getDevMdlCd());
                eDevCurValDTO.setDevMdlNm(tbIotEDevRegiDTO.getDevMdlNm());
                eDevCurValDTO.setRegUserId(AuthUtils.getUser().getUserId());
                int chkCurVal = 0;
                // 가입장비 속성 등록 여부 확인
                temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.chkEntrDevCurVal");
                try {
                    chkCurVal = iotEDevRegiDAO.chkEntrDevCurVal(eDevCurValDTO);
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

                if(chkCurVal > 0) {
                    temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.updateEntrDevCurVal");
                    try {
                        iotEDevRegiDAO.updateEntrDevCurVal(eDevCurValDTO);
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
                }else {
                    temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.createEntrDevCurVal");
                    try {
                        iotEDevRegiDAO.createEntrDevCurVal(eDevCurValDTO);
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
            }
        }

        // 서비스 장비 속성 등록
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.createEntrSDevAttbCurVal");
        try {
            iotEDevRegiDAO.createEntrSDevAttbCurVal(tbIotEDevRegiDTO);
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

        int chkColVal = 0;
        // 가입장비 속성 등록 여부 확인
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.chkEntrDevColVal");
        try {
            chkColVal = iotEDevRegiDAO.chkEntrDevColVal(tbIotEDevRegiDTO);
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

        if(chkColVal < 1 ) {
            // TB_iOT_COL_VAL 등록
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotEDevRegiDAO.createEntrDevColVal");
            try {
                iotEDevRegiDAO.createEntrDevColVal(tbIotEDevRegiDTO);
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


    }

    @Override
    public XSSFWorkbook downloadEntrDevList(TbIotEDevDTO tbIotEDevDTO) throws BizException {
        ComInfoDto temp = null;
        List<DownEntrDevDTO> entrDevList = new ArrayList<DownEntrDevDTO>();
        Map<String, String> headerList = new LinkedHashMap<String, String>();
        XSSFWorkbook wb = new XSSFWorkbook();
        headerList = getExcelDownHeaders();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.getEntrDevDownLoadList");

        try {
            tbIotEDevDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
            entrDevList = iotEDevRegiDAO.getEntrDevDownLoadList(tbIotEDevDTO);
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

        ExcelUtils.createExcelFile(wb, entrDevList, headerList);
        return wb;
    }

    @Override
    public XSSFWorkbook downSmplEDevElsx(TbIotEDevDTO tbIotEDevDTO) throws BizException {
        // ComInfoDto temp = null;
        List<DownEntrDevDTO> entrDevList = new ArrayList<DownEntrDevDTO>();
        Map<String, String> headerList = getExcelExample();
        XSSFWorkbook wb = new XSSFWorkbook();
        DownEntrDevDTO dto = new DownEntrDevDTO();
        entrDevList.add(dto);

        ExcelUtils.createExcelFile(wb, entrDevList, headerList);
        return wb;
    }

    public boolean isFlot(String param) {
        String regExp = "^[.0-9]*$";
        return param.matches(regExp);
    }

    public int maxSizeCheck(String param, Double num) {
        // 같으면 0, 우측이 크면 -1, 좌측이 크면 1
        return Double.compare(new Double(param), num);
    }

    public boolean isNullCheck(String param) {
        return StringUtils.isEmpty(param);
    }

    public boolean isContainWhiteSpace(String param) {
        return param.indexOf(" ") > 0 ? true : false;
    }

    public boolean isNumber(String param) {
        String regExp = "^[0-9]*$";
        return param.matches(regExp);
    }

    public boolean lengthChk(String param, int num) {
        return param.length() > num ? false : true;
    }

    public boolean maxSizeChk(String param, int num) {
        return Integer.parseInt(param) >= num ? false : true;
    }

    public boolean datelengthChk(String param, int num) {
        return param.length() == num ? true : false;
    }

    public boolean validationDate(String checkDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(checkDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private TbIotEDevRegiDTO convertDto(TbIotEDevUploadDTO tbIotEDevRegiDTO) {
        TbIotEDevRegiDTO returnDto = new TbIotEDevRegiDTO();
        returnDto.setCtn(tbIotEDevRegiDTO.getCtn());
        returnDto.setDevUname(tbIotEDevRegiDTO.getDevUname());
        returnDto.setStatusCd(tbIotEDevRegiDTO.getStatusCd());
        returnDto.setHldrCustNo(tbIotEDevRegiDTO.getHldrCustNo());
        returnDto.setUsingNo(tbIotEDevRegiDTO.getUsingNo());
        returnDto.setInstAddr(tbIotEDevRegiDTO.getInstAddr());
        returnDto.setInstAddrDetail(tbIotEDevRegiDTO.getInstAddrDetail());
        returnDto.setInstLat(tbIotEDevRegiDTO.getInstLat());
        returnDto.setInstLon(tbIotEDevRegiDTO.getInstLon());
        returnDto.setDevClsCd(tbIotEDevRegiDTO.getDevClsCd());
        returnDto.setDevClsCdNm(tbIotEDevRegiDTO.getDevClsCdNm());
        returnDto.setDevMdlCd(tbIotEDevRegiDTO.getDevMdlCd());
        returnDto.setDevMdlNm(tbIotEDevRegiDTO.getDevMdlNm());
        returnDto.setDevUuid(tbIotEDevRegiDTO.getDevUuid());
        returnDto.setInstNo(tbIotEDevRegiDTO.getInstNo());
        returnDto.setMachineNo(tbIotEDevRegiDTO.getMachineNo());
        returnDto.setDevRegiDt(tbIotEDevRegiDTO.getDevRegiDt());
        returnDto.setInstDttm(tbIotEDevRegiDTO.getInstDttm());
        returnDto.setRegUserId(tbIotEDevRegiDTO.getRegUserId());
        returnDto.setSvcCd(tbIotEDevRegiDTO.getSvcCd());
        returnDto.setDevBuildingNm(tbIotEDevRegiDTO.getDevBuildingNm());
        return returnDto;
    }

    @Override
    public HashMap<String, Object> searchDevImg(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        HashMap<String, Object> rnMap = new HashMap<String, Object>();
        TbIotEDevRegiDTO imgList = null;
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.searchDevImg");
        try {
            imgList = iotEDevRegiDAO.searchDevImg(tbIotEDevRegiDTO);
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
        return rnMap;
    }

    @Override
    public TbIotEDevRegiDTO retrieveDevMdlNm(TbIotEDevUploadDTO tbIotEDevUploadDTO) throws BizException {
        ComInfoDto temp = null;
        TbIotEDevRegiDTO outDto = new TbIotEDevRegiDTO();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveDevMdlNm");
        try {
            outDto = iotEDevRegiDAO.retrieveDevMdlNm(tbIotEDevUploadDTO);
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
        return outDto;
    }

    @Override
    public TbIotEDevRegiDTO retrieveDevClsCdNm(TbIotEDevUploadDTO tbIotEDevUploadDTO) throws BizException {
        ComInfoDto temp = null;
        List<TbIotEDevRegiDTO> resultList;
        TbIotEDevRegiDTO outDto = new TbIotEDevRegiDTO();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.retrieveDevClsCdNm");
        try {
            resultList = iotEDevRegiDAO.retrieveDevClsCdNm(tbIotEDevUploadDTO);
            if (resultList.size() > 0) {
//                outDto = resultList.get(0);
                outDto.setDevClsCd(resultList.get(0).getDevClsCd());
                outDto.setDevClsCdNm(resultList.get(0).getDevClsCdNm());
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
        return outDto;
    }

    @Override
    public TbIotEDevRegiDTO searchCtnInfo(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
        ComInfoDto temp = null;
        TbIotEDevRegiDTO outDto = new TbIotEDevRegiDTO();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevRegiDAO.searchCtnInfo");
        try {
            outDto = iotEDevRegiDAO.searchCtnInfo(tbIotEDevRegiDTO);
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
        return outDto;
    }

    public TbIotEDevUploadDTO objectCheck(TbIotEDevUploadDTO dto) {
        Map<String, String> propsMap = oMapper.convertValue(dto, Map.class);
        String value;
        boolean objChk = true;
        for (String key : propsMap.keySet()) {
            try {
                value = propsMap.get(key).trim();
                if (StringUtils.isEmpty(value)) {
                    objChk = false;
                } else {
                    objChk = true;
                    break;
                }
            } catch (NullPointerException e) {
                objChk = false;
            }
        }
        if (objChk) {
            return dto;
        } else {
            return null;
        }
    }


    // 삼천리 온라인 장비 목록 다운로드 시 사용하는 헤더
    public Map<String, String> getExcelDownHeaders() {
        Map<String, String> headerList = new LinkedHashMap<String, String>();
        headerList.put("장비 유형", "devClsCd");
        headerList.put("장비 모델", "devMdlCd");
        headerList.put("식별번호(CTN)", "ctn");
        headerList.put("Entity Id", "devUuid");
        headerList.put("시설번호", "instNo");
        headerList.put("식별명", "devUname");
        headerList.put("설치주소", "instAddr");
        headerList.put("장비상태", "statusNm");
        headerList.put("장비설치일자", "instDttm");

        return headerList;
    }

    // 삼천리 온라인 장비 엑셀 업로드 헤더 목록
    public Map<String, String> getExcelExample() {
        Map<String, String> headerList = new LinkedHashMap<String, String>();
        headerList.put("장비관리번호", "nodeId");
        headerList.put("*식별번호(CTN)", "ctn");
        headerList.put("Entity Id", "devUuid");
        headerList.put("시설유형명", "equiTypeNm");
        headerList.put("시설유형코드", "equiType");
        headerList.put("시설번호", "instNo");
        headerList.put("시설명", "devBuildingNm");
        headerList.put("*경도", "instLon");
        headerList.put("*위도", "instLat");
        headerList.put("장비유형명", "devClsCdNm");
        headerList.put("*장비유형코드", "devClsCd");
        headerList.put("장비모델명", "devMdlNm");
        headerList.put("*장비모델코드", "devMdlCd");
        headerList.put("설치일자", "instDttm");
        headerList.put("설치차수", "sequenceNum");
        headerList.put("모듈일련번호", "devUname");

        return headerList;
    }

    // 삼천리 온라인 장비 엑셀 업로드 시 파싱 key 목록
    public List<String> getExcelColumnList() {
        List<String> columnList = new ArrayList<String>();
        columnList.add("nodeId");
        columnList.add("ctn");
        columnList.add("devUuid");
        columnList.add("equiTypeNm");
        columnList.add("equiType");
        columnList.add("instNo");
        columnList.add("devBuildingNm");
        columnList.add("instLon");
        columnList.add("instLat");
        columnList.add("devClsCdNm");
        columnList.add("devClsCd");
        columnList.add("devMdlNm");
        columnList.add("devMdlCd");
        columnList.add("instDttm");
        columnList.add("sequenceNum");
        columnList.add("devUname");

        return columnList;
    }

//    @SuppressWarnings("unchecked")
//    @Transactional
    @Override
    public void uploadBatchEntrDev(HttpServletRequest request) throws BizException {
        ComInfoDto temp = null;
        String seq = iotEDevRegiDAO.retrieveDevSeq();
        fileService.saveFiles(request, FileBoardTypeDTO.DEVSTATUS, seq);
        // Board Type 설정
        TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
        tbIoTBrdFileDTO.setContentSeq(seq);
        tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.DEVSTATUS);

        // 엑셀에서 속성으로 뽑아낼 항목들
        List<String> excelPropList = this.getExcelColumnList();

        // 저장된 파일 리스트 조회
        List<TbIoTBrdFileDTO> savedFileList;
        savedFileList = brdFileListDAO.retrieveTbIoTBrdFileList(tbIoTBrdFileDTO);

        int uploadNum = 0;

        if (savedFileList != null && !savedFileList.isEmpty() && savedFileList.size() > 0) {
            List<TbIotEDevUploadDTO > uploadList = null;

            for (Iterator<TbIoTBrdFileDTO> iterator = savedFileList.iterator(); iterator.hasNext();) {
                StringBuilder filePath = new StringBuilder();
                filePath.append(FILE_UPLOAD_PATH);
                TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
                filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
                InputStream xslInputStream = null;
                try {
                    String tempFilePath = filePath.toString().replaceAll("\\\\", "/");
                    String filepathTemp = WebCommUtil.cleanString(tempFilePath);
                    xslInputStream = new FileInputStream(filepathTemp);
                    try {
                        uploadList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, TbIotEDevUploadDTO.class);
                    } catch (Exception e) {
                        throw new BizException(e, "excelUploadException");
                    }
                    if(uploadList.size() > 1000) {
                        throw new BizException("excelUploadLimit");
                    }

                    Integer rowCnt = 2;
                    for (Iterator<TbIotEDevUploadDTO> iterator2 = uploadList.iterator(); iterator2.hasNext();) {
                        TbIotEDevUploadDTO tbIotEDevRegiDTO = objectCheck((TbIotEDevUploadDTO) iterator2.next());
                        if (null != tbIotEDevRegiDTO) {
                            localValidation(tbIotEDevRegiDTO);

                            // 기본정보 세팅(LG)
                            if (StringUtils.isEmpty(tbIotEDevRegiDTO.getHldrCustNo())) {
//								tbIotEDevRegiDTO.setHldrCustNo(iotEDevRegiDAO.retrieveMaxHldrNo());
                                tbIotEDevRegiDTO.setHldrCustNo(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "defaultHldrCustNo"));
                            }
                            if (StringUtils.isEmpty(tbIotEDevRegiDTO.getEntrNo())) {
                                tbIotEDevRegiDTO.setEntrNo(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "defaultEntrNo"));
                            }
                            if (StringUtils.isEmpty(tbIotEDevRegiDTO.getAceno())) {
                                tbIotEDevRegiDTO.setAceno(iotEDevRegiDAO.retrieveMaxAceNo());
                            }


                            TbIotEDevRegiDTO tmpDto = new TbIotEDevRegiDTO();
                            List<String> testList = new ArrayList<String>();
                            testList.add(String.valueOf(rowCnt));

                            int resultNum;

                            // CTN 중복확인
                            tmpDto.setCtn(tbIotEDevRegiDTO.getCtn());
                            resultNum = iotEDevRegiDAO.retrieveCtnDup(tmpDto);
                            if(resultNum > 0) {
                                throw new BizException("duplicationCtnExcel", testList);
                            }

                            // UUID 중복확인
                            tmpDto.setDevUuid(tbIotEDevRegiDTO.getDevUuid());
                            resultNum = this.retrieveUuidDup(tmpDto);
                            if(resultNum > 0) {
                                throw new BizException("duplicationUuIdExcel", testList);
                            }

                            // 식별명 중복확인
//                            tmpDto.setDevUname(tbIotEDevRegiDTO.getDevUname());
//                            resultNum = this.retrieveUnameDup(tmpDto);
//                            if(resultNum > 0) {
//                                throw new BizException("duplicationUnameExcel", testList);
//                            }


                            tbIotEDevRegiDTO.setSvcCd(AuthUtils.getUser().getSvcCd());

                            // 장비유형코드 조회
                            TbIotEDevRegiDTO resultCldCheck = this.retrieveDevClsCdNm(tbIotEDevRegiDTO);
                            if ( null == resultCldCheck.getDevClsCd()) {
                                throw new BizException("noDevClsCdNm", testList);
                            } else {
                                tbIotEDevRegiDTO.setDevClsCd(resultCldCheck.getDevClsCd());
                            }

                            // 장비모델명 조회
                            TbIotEDevRegiDTO resultMdlCheck = this.retrieveDevMdlNm(tbIotEDevRegiDTO);
                            if (null == resultMdlCheck.getDevMdlCd()) {
                                throw new BizException("noDevMdlNm", testList);
                            } else {
                                tbIotEDevRegiDTO.setDevMdlCd(resultMdlCheck.getDevMdlCd());
                            }

                            tbIotEDevRegiDTO.setRegUserId(AuthUtils.getUser().getUserId());
                            tbIotEDevRegiDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
                            TbIotEDevRegiDTO inputDto = convertDto(tbIotEDevRegiDTO);
                            inputDto.setCustSeq(AuthUtils.getUser().getCustSeq());
                            inputDto.setStatusCd("A");
//							inputDto.setOrgSeq(AuthUtils.getUser().getOrgSeq());
                            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                                    "iotEDevRegiDAO.createEntrDev");
                            try {
                                iotEDevRegiDAO.createEntrDev(inputDto);
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

//							double chgInstLat = Math.floor(Float.parseFloat(inputDto.getInstLat()) * 10000000);
//							double chgInstLon = Math.floor(Float.parseFloat(inputDto.getInstLon()) * 10000000);

                            if ("".equals(StringUtils.ifNullToEmpty(inputDto.getInstLat()))) {
                            	inputDto.setInstLat("0");
                            	
                            }
                            if ("".equals(StringUtils.ifNullToEmpty(inputDto.getInstLon()))) {
                            	inputDto.setInstLon("0");
                            }
                            
                            double chgInstLat = Double.parseDouble(inputDto.getInstLat()) * 10000000;
                            double chgInstLon = Double.parseDouble(inputDto.getInstLon()) * 10000000;
                            BigDecimal op1 = new BigDecimal(Math.floor(chgInstLat));
                            BigDecimal op2 = new BigDecimal(Math.floor(chgInstLon));
                            inputDto.setInstLat(op1.toString());
                            inputDto.setInstLon(op2.toString());

                            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                                    "iotEDevRegiDAO.createEntrDevInsLoc");
                            try {
                                iotEDevRegiDAO.createEntrDevInsLoc(inputDto);
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


                            // 서비스 장비 속성 등록
                            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                                    "iotEDevRegiDAO.createEntrSDevAttbCurVal");
                            try {
                                iotEDevRegiDAO.createEntrSDevAttbCurVal(inputDto);
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

                            // TB_iOT_COL_VAL 등록
                            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                                    "iotEDevRegiDAO.createEntrDevColVal");
                            try {
                                iotEDevRegiDAO.createEntrDevColVal(inputDto);
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
                            uploadNum++;
                            if(!iterator2.hasNext()) {
                                log.info("total upload number : " + uploadNum);
                            }
                        }
                        // 행 체크를 위한 변수 증가
                        rowCnt ++ ;
                    }
                    ReqContextComponent.getComInfoDto().setMsg("Device Upload Number : " + uploadNum);
                } catch (FileNotFoundException e) {
                    throw new BizException("FileNotFoundException");
                } finally {
                    if (xslInputStream != null) {
                        try {
                            xslInputStream.close();
                        } catch (IOException e) {}
                    }
                    TbIoTBrdFileListDTO dto = new TbIoTBrdFileListDTO();
                    dto.setContentSeq(seq);
                    dto.setContentType(FileBoardTypeDTO.DEVSTATUS);
                    TbIoTBrdFileDTO dto2 = new TbIoTBrdFileDTO();
                    dto2.setContentSeq(seq);
                    dto2.setContentType(FileBoardTypeDTO.DEVSTATUS);
                    List<TbIoTBrdFileDTO> dto3 = fileService.selectFileList(dto2);
                    dto.setFileListSeq(dto3.get(0).getFileListSeq());

                    temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                            "brdFileListDAO.deleteTbIoTBrdFileList");
                    try {
                        brdFileListDAO.deleteTbIoTBrdFileList(dto);
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
//					savedFileList.get(0).getFilePath();
//					savedFileList.get(0).getFileName();
                    FileUtils.deleteFile(request, FILE_UPLOAD_PATH+savedFileList.get(0).getFilePath()+"/", savedFileList.get(0).getFileName());
                }
            }
        }

    }

    private void localValidation(TbIotEDevUploadDTO tbIotEDevRegiDTO) {
        String[] notNullGrp = { "ctn", "devClsCd", "devMdlCd" };
        String[] noWhiteSpaceGrp = { "ctn", "instNo", "instLat", "instLon" };
        String[] numberGrp = { "ctn", "instLat", "instLon" };
        String[] lengthChkGrp = { "ctn", "instNo", "instDttm", "devUuid", "devUname" };
        String[] dateChkGrp = { "instDttm"};
        Map<String, String> propsMap = oMapper.convertValue(tbIotEDevRegiDTO, Map.class);
        List<String> exceptionList = new ArrayList<String>();
        String len = null;
        String value = null;
        String message = null;
        for (String key : propsMap.keySet()) {
            message = fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, key);
            if ( null != propsMap.get(key)) {
                value = propsMap.get(key).trim();
            }

            for (int i = 0; i < notNullGrp.length; i++) {
                if (notNullGrp[i].equals(key)) {
                    if (StringUtils.isEmpty(value)) {
                        exceptionList.add(message);
                        throw new BizException("excel_empty_value", exceptionList);
                    }
                }
            }
            for (int i = 0; i < noWhiteSpaceGrp.length; i++) {
                if (noWhiteSpaceGrp[i].equals(key)) {
                    if (isContainWhiteSpace(value)) {
                        exceptionList.add(message);
                        throw new BizException("white_space", exceptionList);
                    }
                }
            }
            for (int i = 0; i < numberGrp.length; i++) {
                if (numberGrp[i].equals(key)) {
                    if(key.equals("instLat") || key.equals("instLon") ) {
                        if(!isFlot(value)) {
                            exceptionList.add(message);
                            throw new BizException("not_input_number", exceptionList);
                        }else {
                            double maxSize = 0;
                            if(key.equals("instLat")) {
                                maxSize = 90.2;
                            } else {
                                maxSize = 1000;
                            }
                            // 소수점 비교로 변경
                            int rsNum = maxSizeCheck(value, maxSize);
                            if(rsNum != -1) {
//								if(!maxSizeChk(value, maxSize)) {
                                exceptionList.add(message);
                                exceptionList.add(maxSize+"");
                                throw new BizException("numberUnderLimit", exceptionList);
                            }
                        }
                    }else {
                        if (!isNumber(value)) {
                            exceptionList.add(message);
                            throw new BizException("not_input_number", exceptionList);
                        }
                    }
                }
            }
            for (int i = 0; i < lengthChkGrp.length; i++) {
                if (lengthChkGrp[i].equals(key)) {
                    boolean status = false;
                    if (key.equals("devRegiDt") || key.equals("instDttm")) {
                        status = datelengthChk(value,10);
                        len = "10";
                    } else if (key.equals("ctn")) {
                        status = lengthChk(value,11);
                        len = "11";
                    } else if (key.equals("devUuid")) {
                        status = lengthChk(value,50);
                        len = "50";
                    } else if (key.equals("devUname")) {
                        status = lengthChk(value,50);
                        len = "50";
                    } else if (key.equals("firmwareVer")) {
                        status = lengthChk(value,50);
                        len = "50";
                    } else {
                        status = lengthChk(value,20);
                        len = "20";
                    }
                    if (!status) {
                        exceptionList.add(message);
                        exceptionList.add(len);
                        throw new BizException("wrong_length", exceptionList);
                    }
                }
            }
            for (int i = 0; i < dateChkGrp.length; i++) {
                if (dateChkGrp[i].equals(key)) {
                    if (!validationDate(value)) {
                        exceptionList.add(message);
                        throw new BizException("invalid_data", exceptionList);
                    }
                }
            }
        }
    }


}
