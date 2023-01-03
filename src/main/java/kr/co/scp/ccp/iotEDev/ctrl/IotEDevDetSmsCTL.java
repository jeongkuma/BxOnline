package kr.co.scp.ccp.iotEDev.ctrl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.WebCommUtil;
import kr.co.scp.ccp.iotEDev.dto.TbIotEdevDetAttbDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEdevDetRcvUsrDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEdevDetSmsDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEdevDetSmsUsrDTO;
import kr.co.scp.ccp.iotEDev.svc.IotEDevDetSmsSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping( value = "/online/iotedevdetsms")
public class IotEDevDetSmsCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;
	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	private IotEDevDetSmsSVC iotEDevDetSmsSVC;
	
	@Autowired
	private ObjectMapper objectMapper = null;
	
	@Value("${file.upload-dir}")
	private String FILE_UPLOAD_PATH;

	
	/**
	 * 장애sms list 조회
	 *  
	 * @param request
	 * @param tbIotEdevRegDTO
	 * @return List<TbIotEdevRegDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveEDevDetSmsList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveEDevDetSmsList(HttpServletRequest request, @RequestBody TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) {

		HashMap<String, Object> retrieveEDevDetSmsList = null;
		try {  
	
			retrieveEDevDetSmsList = iotEDevDetSmsSVC.retrieveEDevDetSmsList(tbIotEdevDetSmsDTO); 
			log.debug(this.getClass().getName() + ".retrieveEDevDetSmsList : " + retrieveEDevDetSmsList.toString());

			
		}catch (Exception e) {

			// TODO: handle exception
			e.printStackTrace();
		}
		return responseComUtil.setResponse200ok(retrieveEDevDetSmsList);
	}

	/**
	 * 장비 속성 list 조회
	 *  
	 * @param request
	 * @param tbIotEdevRegDTO
	 * @return List<TbIotEdevRegDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveEDevDetDevAttbList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveEDevDetDevAttbList(HttpServletRequest request, @RequestBody TbIotEdevDetAttbDTO tbIotEdevDetAttbDTO) throws BizException{
		HashMap<String, Object> retrieveEDevDetDevAttbList = iotEDevDetSmsSVC.retrieveEDevDetDevAttbList(tbIotEdevDetAttbDTO); 
		log.debug(this.getClass().getName() + ".retrieveEDevDetDevAttbList : " + retrieveEDevDetDevAttbList.toString());
		return responseComUtil.setResponse200ok(retrieveEDevDetDevAttbList);
		 
	}

	/**
	 * 수신자 list 조회
	 *  
	 * @param request
	 * @param tbIotEdevRegDTO
	 * @return List<TbIotEdevRegDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveEDevDetUserList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveEDevDetUserList(HttpServletRequest request, @RequestBody TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) throws BizException{
		HashMap<String, Object> retrieveEDevDetUserList = iotEDevDetSmsSVC.retrieveEDevDetUserList(tbIotEdevDetSmsDTO); 
		log.debug(this.getClass().getName() + ".retrieveEDevDetUserList : " + retrieveEDevDetUserList.toString());
		return responseComUtil.setResponse200ok(retrieveEDevDetUserList);
		 
	}


	/**
	 * 수신자 list 조회
	 *  
	 * @param request
	 * @param tbIotEdevRegDTO
	 * @return List<TbIotEdevRegDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveEDevDetUserListByDetSetSeq", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveEDevDetUserListByDetSetSeq(HttpServletRequest request, @RequestBody TbIotEdevDetRcvUsrDTO tbIotEdevDetRcvUsrDTO){
		
		HashMap<String, Object> retrieveEDevDetUserList = null;
		try {
			log.debug("retrieveEDevDetUserListByDetSetSeq=="+ tbIotEdevDetRcvUsrDTO.toString() );
			 
			retrieveEDevDetUserList = iotEDevDetSmsSVC.retrieveEDevDetUserListByDetSetSeq(tbIotEdevDetRcvUsrDTO); 
			log.debug(this.getClass().getName() + ".retrieveEDevDetUserListByDetSetSeq : " + retrieveEDevDetUserList.toString());


		}catch (Exception e) {
	
			// TODO: handle exception
			e.printStackTrace();
		}
		return responseComUtil.setResponse200ok(retrieveEDevDetUserList);
		 
	} 
	
	/**
	 * 장애sms 등록
	 * @param request
	 * @param tbIotEDevRegiDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/createEDevDetSms", method=RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> createEDevDetSms(HttpServletRequest request, @RequestParam String jsonData ) throws BizException{
		log.debug("createEDevDetSms=="+ jsonData.toString() );
		TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO;
		try {
			tbIotEdevDetSmsDTO = (TbIotEdevDetSmsDTO) WebCommUtil.stringToObject(jsonData, TbIotEdevDetSmsDTO.class);
			iotEDevDetSmsSVC.createEDevDetSms(tbIotEdevDetSmsDTO);
		}  catch (UnrecognizedPropertyException e) {
			System.out.println(e.toString());
			throw new BizException("ObjectMapperError");	
		} catch (IOException e) {			
			throw new BizException("fileUploadError");
		}
		
		return responseComUtil.setResponse200ok();
	}
 
	/**
	 * 수신자 삭제
	 * @param request
	 * @param tbIotEDevRegiDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteEDevDetRcvUsr", method = RequestMethod.POST)
	public ComResponseDto<?> deleteEDevDetRcvUsr(HttpServletRequest request, @RequestBody TbIotEdevDetSmsUsrDTO tbIotEdevDetSmsUsrDTO) throws BizException {
		iotEDevDetSmsSVC.deleteEDevDetRcvUsr(tbIotEdevDetSmsUsrDTO);
		return responseComUtil.setResponse200ok();
	}
	
	/**
	 * Message list 조회
	 *  
	 * @param request
	 * @param TbIotEdevDetSmsDTO
	 * @return  
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveEDevDetMessageList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveEDevDetMessageList(HttpServletRequest request, @RequestBody TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) throws BizException{
		HashMap<String, Object> retrieveEDevDetMessageList = iotEDevDetSmsSVC.retrieveEDevDetMessageList(tbIotEdevDetSmsDTO); 
		log.debug(this.getClass().getName() + ".retrieveEDevDetMessageList : " + retrieveEDevDetMessageList.toString());
		return responseComUtil.setResponse200ok(retrieveEDevDetMessageList);
		 
	}

}
