package kr.co.scp.ccp.iotDev.ctl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevSVC;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevCTL {


    @Autowired
    private ResponseComUtil responseComUtil;

    @Autowired
    private IotDevSVC iotDevSVC;

    /**
     * 장비 목록 조회
     *
     * @param request
     * @param tbIotDevDto
     * @return List<tbIotDevDto>
     * @throws BizException
     */
    @RequestMapping(value = "/retrieveIotDev", method = RequestMethod.POST)
    public ComResponseDto<?> retrieveIotDev(HttpServletRequest request, @RequestBody TbIotDevDTO tbIotDevDTO) throws BizException {

        HashMap<String, Object> devListMap = iotDevSVC.retrieveIotDev(tbIotDevDTO);

        return responseComUtil.setResponse200ok(devListMap);

    }

    /**
     * 장비 목록 조회(부모장비)
     *
     * @param request
     * @param tbIotDevDto
     * @return List<tbIotDevDto>
     * @throws BizException
     */
    @RequestMapping(value = "/retrieveIotDevPar", method = RequestMethod.POST)
    public ComResponseDto<?> retrieveIotDevPar(HttpServletRequest request,
                                               @RequestBody TbIotDevDTO tbIotDevDTO
    ) throws BizException {
        log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
        HashMap<String, Object> devListMap = iotDevSVC.retrieveIotDevPar(tbIotDevDTO);

        return responseComUtil.setResponse200ok(devListMap);
    }

    /**
     * 장비 등록
     *
     * @param request
     * @param tbIotDevDto
     * @return List<tbIotDevDto>
     * @throws BizException
     */
    @RequestMapping(value = "/insertIotDev", method = RequestMethod.POST)
    public ComResponseDto<?> insertIotDev(HttpServletRequest request, @RequestBody TbIotDevDTO tbIotDevDTO) throws BizException {

        log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
        iotDevSVC.insertIotDev(tbIotDevDTO);

        return responseComUtil.setResponse200ok(tbIotDevDTO);
    }

    /**
     * 장비 수정
     *
     * @param request
     * @param tbIotDevDto
     * @return List<tbIotDevDto>
     * @throws BizException
     */
    @RequestMapping(value = "/updateIotDev", method = RequestMethod.POST)
    public ComResponseDto<?> updateIotDev(HttpServletRequest request, @RequestBody TbIotDevDTO tbIotDevDTO) throws BizException {

        log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
        iotDevSVC.updateIotDev(tbIotDevDTO);

        return responseComUtil.setResponse200ok();
    }

    /**
     * 장비 삭제
     *
     * @param request
     * @param tbIotDevDto
     * @return List<tbIotDevDto>
     * @throws BizException
     */
    @RequestMapping(value = "/deleteIotDev", method = RequestMethod.POST)
    public ComResponseDto<?> deleteIotDev(HttpServletRequest request, @RequestBody TbIotDevDTO tbIotDevDTO) throws BizException {
        System.out.println("##########################################" + tbIotDevDTO.toString());
        log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
        iotDevSVC.deleteIotDev(tbIotDevDTO);

        return responseComUtil.setResponse200ok();
    }

    /**
     * 장비 유형 목록 조회
     *
     * @param request
     * @param tbIotDevDto
     * @return List<TbIotDevMdlDTO>
     * @throws BizException
     */
    @RequestMapping(value = "/retrieveIotDevCls", method = RequestMethod.POST)
    public ComResponseDto<?> retrieveIotDevCls(HttpServletRequest request) throws BizException {

        log.debug("Test by retrieveIotDevCls_====== " + ReqContextComponent.getComInfoDto().toString());
        List<TbIotDevDTO> tbIotDevDTORn = iotDevSVC.retrieveIotDevCls();
        return responseComUtil.setResponse200ok(tbIotDevDTORn);
    }

    /**
     * 유형에 맞는 장비 모델명 목록 조회
     *
     * @param request
     * @param TbIotDevMdlDTO
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/retrieveDevMdlList", method = RequestMethod.POST)
    public ComResponseDto<?> retrieveDevMdlList(HttpServletRequest request, @RequestBody TbIotDevDTO tbIotDevDTO) throws BizException {

        log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
        List<TbIotDevDTO> tbIotDevDTORn = iotDevSVC.retrieveDevMdlList(tbIotDevDTO);
        return responseComUtil.setResponse200ok(tbIotDevDTORn);
    }

    /**
     * 서비스 유형 목록 조회
     *
     * @param request
     * @param tbIotDevDto
     * @return List<TbIotDevMdlDTO>
     * @throws BizException
     */
    @RequestMapping(value = "/retrieveIotDevSvc", method = RequestMethod.POST)
    public ComResponseDto<?> retrieveIotDevSvc(HttpServletRequest request) throws BizException {

        log.debug("Test by retrieveIotDevCls_====== " + ReqContextComponent.getComInfoDto().toString());
        List<TbIotDevDTO> tbIotDevDTORn = iotDevSVC.retrieveIotDevSvc();
        return responseComUtil.setResponse200ok(tbIotDevDTORn);
    }

//	/**
//	 * 고객 장비 할당
//	 * @param request
//	 * @param tbIotDevDto
//	 * @return List<tbIotDevDto>
//	 * @throws BizException
//	 */
//	@RequestMapping(value = "/assignment", method = RequestMethod.POST)
//	public ComResponseDto<?> custAssignmentDev(HttpServletRequest request, @RequestBody TbIotDevDTO tbIotDevDTO) throws BizException {
//
//		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
//
//		// 고객별 장비 / 고객별 장비 속성 / 고객별 장비 속성 SET / 고객별 장비 이상징후 SET / 고객별 장비 제어 SET
//		iotDevSVC.custAssignmentDev(tbIotDevDTO);
//
//		return responseComUtil.setResponse200ok();
//	}

    /**
     * 사용자 일괄 등록
     *
     * @param request
     * @param tbIotUseDTO
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/createIotDevAll", method = RequestMethod.POST)
    public ComResponseDto<?> createIotDevAll(HttpServletRequest request) throws BizException {
        iotDevSVC.createIotDevAll(request);
//
        return responseComUtil.setResponse200ok();
    }


    @RequestMapping(value = "/createIotPasteDevAttr", method = RequestMethod.POST)
    public void downloadEntrDevList(HttpServletRequest request, HttpServletResponse response,
                                    @RequestBody TbIotDevDTO tbIotDevDTO) {

        XSSFWorkbook wb = null;
        String fileName = "DevAttr" + "_" + new Random().nextInt(1000) + ".xlsx";

        try (ServletOutputStream sos = response.getOutputStream()) {

            wb = iotDevSVC.downloadEntrDevList(tbIotDevDTO);
            String header = request.getHeader("User-Agent");
            fileName = ExcelUtils.urlDecodeFileName(header, fileName);
            ExcelUtils.addFileDownloadHeader(response, fileName);
            wb.write(sos);

        } catch (Exception e) {
            ExcelUtils.addErrorFileDownloadHeader(response);
            throw new BizException(e, "excellDownloadError");
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception ignore) {
            }
        }
    }

}

