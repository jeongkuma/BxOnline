package kr.co.scp.ccp.iotDev.svc.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.validation.ValidationComponent;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.auiot.common.util.WebCommUtil;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.iotDev.dao.IotDevCtlDAO;
import kr.co.scp.ccp.iotDev.dao.IotDevDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevCtlDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevExcelDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevCtlSVC;
import kr.co.scp.ccp.iotDev.svc.IotDevSVC;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

@Primary
@Slf4j
@Service
public class IotDevSVCImpl implements IotDevSVC {

    @Autowired
    private ValidationComponent validationComponent;

    static ObjectMapper oMapper = new ObjectMapper();

    @Autowired
    private IotDevDAO iotDevDAO;

    @Autowired
    private IotDevCtlDAO iotDevCtlDAO;

    @Autowired
    private BrdFileListDAO brdFileListDAO;

    private TbIotDevCtlDTO tbIotDevCtlDTO;

    @Autowired
    private IotDevCtlSVC iotDevCtlSVC;

    @Autowired
    FileServiceUtil fileService;

    @Value("${file.upload-dir}")
    private String FILE_UPLOAD_PATH;

    @Value("${file.max-size}")
    private int FILE_MAX_SIZE;

    private TbIotDevExcelDTO tbIotDevExcelDTO;

    private int retrieveIotDup;

    @Override
    public HashMap<String, Object> retrieveIotDev(TbIotDevDTO tbIotDevDTO) throws BizException {
        ComInfoDto temp = null;

        PageDTO pageDTO = new PageDTO();
        ComInfoDto c = ReqContextComponent.getComInfoDto();
        String newLang = c.getXlang();
        tbIotDevDTO.setLangset(newLang);

        HashMap<String, Object> rstMap = new HashMap<String, Object>();

        try {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotDevDAO.retrieveIotDevCount");
            Integer count = iotDevDAO.retrieveIotDevCount(tbIotDevDTO);

            // 페이징
            pageDTO.pageCalculate(count, tbIotDevDTO.getDisplayRowCount(), tbIotDevDTO.getCurrentPage());
            tbIotDevDTO.setStartPage(pageDTO.getRowStart()); //페이징 에러 처리 함 확인하세요

            tbIotDevDTO.setIsParent(tbIotDevDTO.getIsParent() == "Y" ? "1" : "0");
            tbIotDevDTO.setEntrYn(tbIotDevDTO.getEntrYn());

            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotDevDAO.retrieveIotDev");
            List<TbIotDevDTO> retrieveIotDev = iotDevDAO.retrieveIotDev(tbIotDevDTO);

            rstMap.put("page", pageDTO);
            rstMap.put("boardList", retrieveIotDev);
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


        return rstMap;
    }

    @Override
    public HashMap<String, Object> retrieveIotDevPar(TbIotDevDTO tbIotDevDTO) throws BizException {
        PageDTO pageDTO = new PageDTO();

        HashMap<String, Object> rstMap = new HashMap<String, Object>();

        // FAQ 전체 갯수
        Integer count = iotDevDAO.retrieveIotDevCount1(tbIotDevDTO);

        // 페이징
        pageDTO.pageCalculate(count, tbIotDevDTO.getDisplayRowCount(), tbIotDevDTO.getCurrentPage());
        tbIotDevDTO.setStartPage(pageDTO.getRowStart());

        List<TbIotDevDTO> retrieveIotDevPar = iotDevDAO.retrieveIotDevPar(tbIotDevDTO);

        rstMap.put("page", pageDTO);
        rstMap.put("boardList", retrieveIotDevPar);

        return rstMap;
    }

    @Override
    public List<TbIotDevDTO> retrieveIotDevCls() throws BizException {
        ComInfoDto temp = null;
        List<TbIotDevDTO> list = new ArrayList<TbIotDevDTO>();
        TbIotDevDTO devDTO = new TbIotDevDTO();
        devDTO.setLangset(ReqContextComponent.getComInfoDto().getXlang());
        try {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotDevDAO.retrieveIotDev");
            list = iotDevDAO.retrieveIotDev(devDTO);
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
        return list;
    }

    @Override
    public List<TbIotDevDTO> retrieveDevMdlList(TbIotDevDTO tbIotDevDTO) throws BizException {
        ComInfoDto temp = null;
        List<TbIotDevDTO> list = new ArrayList<TbIotDevDTO>();
        try {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotDevDAO.retrieveDevMdlList");
            list = iotDevDAO.retrieveDevMdlList(tbIotDevDTO);
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
        return list;
    }

    @Override
    public void insertIotDev(TbIotDevDTO tbIotDevDTO) throws BizException {
        ComInfoDto temp = null;
        String userId = AuthUtils.getUser().getUserId();
        tbIotDevDTO.setRegUsrId(userId);
        tbIotDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
        tbIotDevDTO.setLangset(ReqContextComponent.getComInfoDto().getXlang());
        try {

            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevDAO.insertIotDev");

            iotDevDAO.insertIotDev(tbIotDevDTO);
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
    public void updateIotDev(TbIotDevDTO tbIotDevDTO) throws BizException {
        ComInfoDto temp = null;
        String userId = AuthUtils.getUser().getUserId();
        tbIotDevDTO.setRegUsrId(userId);
        try {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevDAO.updateIotDev");
            iotDevDAO.updateIotDev(tbIotDevDTO);
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

        try {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevDAO.updateIotDevAtb");
            iotDevDAO.updateIotDevAtb(tbIotDevDTO);
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
    public void deleteIotDev(TbIotDevDTO tbIotDevDTO) throws BizException {
        ComInfoDto temp = null;
        String userId = AuthUtils.getUser().getUserId();
        tbIotDevDTO.setModUsrId(userId);

        String[] item = tbIotDevDTO.getItem();
        TbIotDevDTO dto = new TbIotDevDTO();
        if (item != null) {

            for (int i = 0; i < item.length; i++) {
                dto.setDevSeq(item[i]);
                try {

                    temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                            "iotDevDAO.deleteIotDev");
                    iotDevDAO.deleteIotDev(dto);
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

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})

    public void createIotDevAll(HttpServletRequest request) throws BizException {
        ComInfoDto temp = new ComInfoDto();
        final String FIXEDCONTSEQ = "1";
        fileService.saveFiles(request, FileBoardTypeDTO.DEV, FIXEDCONTSEQ);
        // Board Type 설정
        TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
        tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
        tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.DEV);

        // 엑셀에서 속성으로 뽑아낼 항목들
        List<String> excelPropList = this.makeExcelPropsList();

        List<TbIoTBrdFileDTO> savedFileList = null;
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "brdFileListDAO.retrieveTbIoTBrdFileList");
        try {
            savedFileList = brdFileListDAO.retrieveTbIoTBrdFileList(tbIoTBrdFileDTO);
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

        // 조회된 파일이 1개 이상일 때
        if (savedFileList.size() > 0) {
            List<TbIotDevExcelDTO> DevExcelDTOList = new ArrayList<TbIotDevExcelDTO>();
            for (Iterator iterator = savedFileList.iterator(); iterator.hasNext(); ) {
                // exception 구분을 위한 변수

                StringBuilder filePath = new StringBuilder();
                filePath.append(FILE_UPLOAD_PATH);
                TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
                filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());

                String fileDPath = null;
                String fileDNm = null;

                fileDPath = FILE_UPLOAD_PATH + tmpBrdDto.getFilePath() + File.separator;
                fileDNm = tmpBrdDto.getFileName();

                InputStream xslInputStream = null;


                try {


                    String tempFilePath = filePath.toString().replaceAll("\\\\", "/");
                    String filepathTemp = WebCommUtil.cleanString(tempFilePath);
                    xslInputStream = new FileInputStream(filepathTemp);
                    DevExcelDTOList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, TbIotDevExcelDTO.class);
                    for (Iterator iterator2 = DevExcelDTOList.iterator(); iterator2.hasNext(); ) {


                        TbIotDevExcelDTO tbIotDevExcelDTO = trimObj((TbIotDevExcelDTO) iterator2.next());

                        String userId = AuthUtils.getUser().getUserId();
                        tbIotDevExcelDTO.setRegUsrId(userId);
                        tbIotDevExcelDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());

                        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevDAO.insertIotDevAll");
                        try {


                            int rs1 = 0;
                            int rs2 = 0;
                            int rs3 = 0;
                            int rs4 = 0;

                            rs1 = iotDevDAO.retrieveIotDevAllCdId(tbIotDevExcelDTO);

                            rs2 = iotDevDAO.retrieveIotDevAllCdNm(tbIotDevExcelDTO);

                            rs3 = iotDevDAO.retrieveIotDupE(tbIotDevExcelDTO);

                            rs4 = iotDevDAO.retrieveIotMdlDupE(tbIotDevExcelDTO);

                            if (rs1 == 0) {
                                throw new BizException("uploadException");
                            }

                            if (rs2 == 0) {
                                throw new BizException("uploadException");
                            }
                            if (rs3 > 0) {
                                throw new BizException("uploadException");
                            }
                            if (rs4 > 0) {
                                throw new BizException("uploadException");
                            }


                            iotDevDAO.insertIotDevAll(trimObj(tbIotDevExcelDTO));


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
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BizException("excelUploadException");
                } finally {
                    try {
                        if (xslInputStream != null) {
                            xslInputStream.close();
                        }
                    } catch (IOException e) {
                    }
                    this.deleteFile();
                    FileUtils.deleteFile(request, fileDPath, fileDNm);
                }
            }
        }
    }

    private TbIotDevDTO convertCDTO(TbIotDevDTO dto) {
        TbIotDevDTO outputDto = new TbIotDevDTO();

        outputDto.setDevClsCd(dto.getDevClsCd());
        outputDto.setDevClsCdNm(dto.getDevClsCdNm());
        outputDto.setDevMdlCd(dto.getDevMdlCd());
        outputDto.setDevMdlNm(dto.getDevMdlNm());
        outputDto.setUseYn(dto.getUseYn());
        outputDto.setStatusCd(dto.getStatusCd());
        outputDto.setParentDevSeq(dto.getParentDevSeq());
        outputDto.setVendorNm(dto.getVendorNm());
        outputDto.setNormalIconFile(dto.getNormalIconFile());
        outputDto.setAbnormalIconFile(dto.getAbnormalIconFile());
        outputDto.setAbnormalIconFile2(dto.getAbnormalIconFile2());
        return outputDto;
    }

    private List<String> makeExcelPropsList() {
        List<String> excelPropList = new ArrayList<String>();
        excelPropList.add("devClsCd");
        excelPropList.add("devClsCdNm");
        excelPropList.add("devMdlCd");
        excelPropList.add("devMdlNm");
        excelPropList.add("useYn");
        excelPropList.add("statusCd");
        excelPropList.add("parentDevSeq");
        excelPropList.add("vendorNm");
        excelPropList.add("normalIconFile");
        excelPropList.add("abnormalIconFile");
        excelPropList.add("abnormalIconFile2");

        return excelPropList;
    }

    private void deleteFile() {
        final String FIXEDCONTSEQ = "1";
        TbIoTBrdFileListDTO dto = new TbIoTBrdFileListDTO();
        dto.setContentSeq(FIXEDCONTSEQ);
        dto.setContentType(FileBoardTypeDTO.DEV);
        brdFileListDAO.deleteTbIoTBrdFileList(dto);
    }

    private TbIotDevExcelDTO trimObj(TbIotDevExcelDTO dto) {
        dto.setDevClsCd(dto.getDevClsCd());
        dto.setDevClsCdNm(dto.getDevClsCdNm());
        dto.setDevMdlCd(dto.getDevMdlCd());
        dto.setDevMdlNm(dto.getDevMdlNm());
        dto.setUseYn(dto.getUseYn());
        dto.setStatusCd(dto.getStatusCd());
        dto.setParentDevSeq(dto.getParentDevSeq());
        dto.setVendorNm(dto.getVendorNm());
        dto.setNormalIconFile(dto.getNormalIconFile());
        dto.setAbnormalIconFile(dto.getAbnormalIconFile());
        dto.setAbnormalIconFile2(dto.getAbnormalIconFile2());
        dto.setLangSet(dto.getLangSet());

        return dto;
    }

    private JSONObject getJsonStringFromMap(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        return jsonObject;

//	@Override
//	public void custAssignmentDev(TbIotDevDTO tbIotDevDTO) throws BizException {
//		// TODO Auto-generated method stub
//		iotDevDAO.custAssignmentDev(tbIotDevDTO);
//	}

    }

    @Override
    public void insertIotDevAll(TbIotDevExcelDTO tbIotDevExcelDTO) throws BizException {
        // TODO Auto-generated method stub

    }

    @Override
    public List<TbIotDevDTO> retrieveIotDevSvc() {
        ComInfoDto temp = null;
        List<TbIotDevDTO> list = null;
        TbIotSvcDto svcDto = new TbIotSvcDto();
        svcDto.setLangSet(ReqContextComponent.getComInfoDto().getXlang());

        try {

            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotDevDAO.retrieveIotDevSvc");
            list = iotDevDAO.retrieveIotDevSvc(svcDto);
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
        return list;
    }

    @Override
    public XSSFWorkbook downloadEntrDevList(TbIotDevDTO tbIotDevDTO) throws BizException {
        ComInfoDto temp = null;
//	List<DownEntrDevDTO> entrDevList = new ArrayList<DownEntrDevDTO>();
        Map<String, String> headerList = new LinkedHashMap<String, String>();
        tbIotDevDTO.setLangset(ReqContextComponent.getComInfoDto().getXlang());
        XSSFWorkbook wb = new XSSFWorkbook();
        headerList = getExcelDownHeaders();
        List<TbIotDevDTO> entrDevList = new ArrayList<TbIotDevDTO>();
        try {
            temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                    "iotDevDAO.retrieveIotDev");
            entrDevList = iotDevDAO.retrieveIotDev(tbIotDevDTO);

            for (Iterator excelIterator = entrDevList.iterator(); excelIterator.hasNext(); ) {
                TbIotDevDTO excelDto = (TbIotDevDTO) excelIterator.next();
                try {
                    excelDto.setRegDttm(DateUtils.getDateByFormatToString(excelDto.getRegDttm(), "yyyyMMddkkmmss", "yyyy-MM-dd kk:mm:ss"));
                } catch (ParseException e) {
                    e.printStackTrace();
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

        ExcelUtils.createExcelFile(wb, entrDevList, headerList);
        return wb;
    }


    public Map<String, String> getExcelDownHeaders() {
        Map<String, String> headerList = new LinkedHashMap<String, String>();
        headerList.put("장비유형", "devClsCdNm");
        headerList.put("모델코드", "devMdlCd");
        headerList.put("모델명", "devMdlNm");
        headerList.put("사용여부", "useYn");
        headerList.put("제조사", "vendorNm");
        headerList.put("등록자", "regUsrId");
        headerList.put("등록일자", "regDttm");


        return headerList;
    }

    @Override
    public Integer retrieveIotDevAllCdId(TbIotDevDTO tbIotDevDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer retrieveIotDevAllCdNm(TbIotDevDTO tbIotDevDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int retrieveIotDupE(TbIotDevExcelDTO tbIotDevExcelDTO) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int retrieveIotMdlDupE(TbIotDevExcelDTO tbIotDevExcelDTO) {
        // TODO Auto-generated method stub
        return 0;
    }

}


