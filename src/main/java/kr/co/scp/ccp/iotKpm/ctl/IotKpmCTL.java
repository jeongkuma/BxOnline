package kr.co.scp.ccp.iotKpm.ctl;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotKpm.svc.IotKpmSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotkpm")
public class IotKpmCTL {

    @Autowired
    private ResponseComUtil responseComUtil;

    @Autowired
    private IotKpmSVC kpmSvc;

    /**
     * KPM 전문 파싱
     * @param request
     * @param param
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/processKpmParse", method = RequestMethod.POST)
    public ComResponseDto<?> processKpmParse(HttpServletRequest request, @RequestBody Map<String, Object> param) throws BizException {

        HashMap<String, Object> result = kpmSvc.processKpmParse(param);

        return responseComUtil.setResponse200ok(result);
    }
}
