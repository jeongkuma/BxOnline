package kr.co.scp.ccp.login.svc.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DateUtils;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.dto.common.User;
import kr.co.auiot.common.spring.SecurityWebConfig;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.EncryptUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.common.smsTran.dto.TbIotSmsTranDTO;
import kr.co.scp.ccp.common.smsTran.svc.SmsTranSVC;
import kr.co.scp.ccp.iotTerms.dao.TbIotTermsDAO;
import kr.co.scp.ccp.iotTerms.dto.TbIotTermsDTO;
import kr.co.scp.ccp.login.dao.LoginDAO;
import kr.co.scp.ccp.login.dao.LoginHistDAO;
import kr.co.scp.ccp.login.dto.LoginDTO;
import kr.co.scp.ccp.login.dto.LoginUserPersonalInfoDTO;
import kr.co.scp.ccp.login.dto.LogoDTO;
import kr.co.scp.ccp.login.dto.TbIoTLoginHistDTO;
import kr.co.scp.ccp.login.svc.LoginSVC;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uap_clj.java.api.Browser;
import uap_clj.java.api.OS;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Base64.Decoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Slf4j
@Primary
@Service
public class LoginSVCImpl implements LoginSVC {

	@Autowired
	private Environment env = null;

	@Autowired
	private LoginDAO loginDAO;

	@Autowired
	private LoginHistDAO loginHistDAO;

//	@Autowired
//	private TbIotTermsDAO termsDAO;

	@Autowired
	private SmsTranSVC smsSvc;

	@Autowired
	SecurityWebConfig securityWebConfig;

	private String usrOs = "";
	private String usrBrowser = "";

	@Value("${file.get-logo-img}")
	private String GET_LOGO_IMG;

	private String decodePwd(String usrPwd) throws BizException {
		Decoder decoder = Base64.getDecoder();
		byte[] decode2 = decoder.decode(usrPwd);
		String decodeString = "";
		try {
			decodeString = new String(decode2,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodeString;
	}

	/**
	 * ????????? ??????
	 */
	@Override
	public HashMap<String, Object> processIotLogin(LoginDTO loginDTO) throws BizException {

		// ???????????? ?????????
		loginDTO.setUsrPwd(decodePwd(loginDTO.getUsrPwd()));
		if (loginDTO.isSecLogin()) {
			ReqContextComponent.getComInfoDto().setFuncId("FN01288");
		}
		ComInfoDto temp = null;
		HashMap<String, Object> rnMap = new HashMap<String, Object>();
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		loginDTO.setUsrPwd(EncryptUtils.encryptThisString(loginDTO.getUsrPwd()));
		loginDTO.setLangSet(langSet);
		LoginDTO loginDTOTmp = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "loginDAO.processIotLogin");
		try {
			// ????????? ?????? ??????
			loginDTOTmp = loginDAO.processIotLogin(loginDTO);
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
		if (null == loginDTOTmp) {
			//????????? ????????? ???????????? ?????? ??????
			throw new BizException("userNotFound");
		} else {
			// ????????? ????????? ????????? ??????
			if (!loginDTOTmp.getUsrPwd().equals(loginDTO.getUsrPwd())) {
				// ??????????????? ????????? ??????
				throw new BizException("userNotFound");
			} else {
				// ???????????? ????????? ??????
				insertTbIotLoginHist(loginDTOTmp, "0", null);
				String auth = getUserToken(loginDTOTmp);
				rnMap.put("access", true);
				rnMap.put("usrNm", loginDTOTmp.getUsrNm());
				rnMap.put("userId", loginDTOTmp.getUsrLoginId());
				rnMap.put("auth", auth);
			}
		}
		return rnMap;
	}

	/**
	 * ????????? ??????
	 */
	@Override
	public HashMap<String, Object> retrieveIotUserInfo(LoginDTO loginDTO) throws BizException {

		// ???????????? ?????????
		loginDTO.setUsrPwd(decodePwd(loginDTO.getUsrPwd()));
		if (loginDTO.isSecLogin()) {
			ReqContextComponent.getComInfoDto().setFuncId("FN01288");
		}
		ComInfoDto temp = null;
		HashMap<String, Object> rnMap = new HashMap<String, Object>();
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		loginDTO.setUsrPwd(EncryptUtils.encryptThisString(loginDTO.getUsrPwd()));
		loginDTO.setLangSet(langSet);
		LoginDTO loginDTOTmp = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "loginDAO.processIotLogin");
		try {
			// ????????? ?????? ??????
			loginDTOTmp = loginDAO.processIotLogin(loginDTO);
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
		if (null == loginDTOTmp) {
			//????????? ????????? ???????????? ?????? ??????
			throw new BizException("userNotFound");
		} else {
			// ????????? ????????? ????????? ??????
			if (!loginDTOTmp.getUsrPwd().equals(loginDTO.getUsrPwd()) && loginDTOTmp.getUsrLckYn() != 1) {
				throw new BizException("userNotFound");
			} else {
				// ???????????? ????????? ??????
				String auth = getUserToken(loginDTOTmp);
				rnMap.put("access", true);
				rnMap.put("usrNm", loginDTOTmp.getUsrNm());
				rnMap.put("auth", auth);
			}
		}
		return rnMap;
	}


	// User ?????? ??????, ?????? ??????
	@Override
	public String getUserToken(LoginDTO loginDTO) throws BizException {
		// Setter user
		User user = new User();
		user.setCustSeq(loginDTO.getCustSeq());
		user.setCustId(loginDTO.getCustLoginId());
		user.setSvcCd(loginDTO.getSvcCd());
		user.setUserSeq(loginDTO.getUsrSeq());
		user.setUserId(loginDTO.getUsrLoginId());
		user.setUserName(loginDTO.getUsrNm());
		user.setRoleCdId(loginDTO.getRoleCdId());
		user.setRoleCdNm(loginDTO.getRoleCdNm());
		user.setOrgSeq(loginDTO.getOrgSeq());
		user.setOrgNm(loginDTO.getOrgNm());
		String auth = securityWebConfig.getToken(user);
		return auth;
	}

	// ???????????? ??????
	@Override
	public HashMap<String, Object> updateIotUsrPwd(LoginDTO loginDTO) throws BizException {
		// ???????????? ?????????
		loginDTO.setUsrPwd(decodePwd(loginDTO.getUsrPwd()));
		ComInfoDto temp = null;
		HashMap<String, Object> rnMap = new HashMap<String, Object>();
		LoginUserPersonalInfoDTO usrInfo = new LoginUserPersonalInfoDTO();
		usrInfo.setUsrLoginId(loginDTO.getUsrLoginId());
		usrInfo.setCustLoginId(loginDTO.getCustLoginId());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "loginDAO.retrieveIotUsrPersonalInfo");
		try {
			usrInfo = loginDAO.retrieveIotUsrPersonalInfo(loginDTO);
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

		if (checkPwdVal(usrInfo, loginDTO.getUsrPwd()).equals("F")) {
			throw new BizException("notValidNewPwd");
		}
		if (!checkPwdCons(loginDTO.getUsrPwd())) {
			throw new BizException("notValidPwd");
		}
		if (!pwdReg(loginDTO.getUsrPwd())) {
			throw new BizException("notValidPwd");
		}
		loginDTO.setUsrPwd(EncryptUtils.encryptThisString(loginDTO.getUsrPwd()));
		if (!checkPwdUsed(loginDTO)) {
			throw new BizException("usedPwd");
		} else {
			rnMap.put("changePwdYn", true);
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "loginDAO.updateIotUsrPwd");
			try {
				loginDAO.updateIotUsrPwd(loginDTO);
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
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "loginDAO.updateIotUsrPwdHist");
			try {
				loginDAO.updateIotUsrPwdHist(loginDTO);
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
		return rnMap;
	}

	// ???????????? ????????? ??????
	@Override
	@Transactional
	public HashMap<String, Object> updateIotUsrPwdNxt(LoginDTO loginDTO) throws BizException {
		HashMap<String, Object> rnMap = new HashMap<String, Object>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"loginDAO.updateIotUsrPwdNxt");
		try {
			loginDAO.updateIotUsrPwdNxt(loginDTO);
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
		rnMap.put("pwdChangeNxt", true);
		return rnMap;
	}


	@Override
	public LoginUserPersonalInfoDTO retrieveIotUsrPersonalInfo(LoginDTO loginDTO) throws BizException {
		LoginUserPersonalInfoDTO retrieveIotUsrPersonalInfo = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"loginDAO.retrieveIotUsrPersonalInfo");
		try {
			retrieveIotUsrPersonalInfo = loginDAO.retrieveIotUsrPersonalInfo(loginDTO);
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
		if (null != retrieveIotUsrPersonalInfo) {
			return retrieveIotUsrPersonalInfo;
		} else {
			throw new BizException("userNotFound");
		}

	}

	/**
	 * ????????? ?????? ??????
	 *
	 * @param loginDTO
	 * @param sucessYn
	 * @param loginFailReason
	 * @throws BizException
	 */
	private void insertTbIotLoginHist(LoginDTO loginDTO, String sucessYn, String loginFailReason) throws BizException {
		TbIoTLoginHistDTO tbIoTLoginiHistDTO = new TbIoTLoginHistDTO();
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		tbIoTLoginiHistDTO.setLangSet(langSet);
		tbIoTLoginiHistDTO.setLoginSuccYn(sucessYn);
		tbIoTLoginiHistDTO.setClientIp(ReqContextComponent.getComInfoDto().getClientIp());
		tbIoTLoginiHistDTO.setUsrLoginId(loginDTO.getUsrLoginId());
		tbIoTLoginiHistDTO.setUsrSeq(loginDTO.getUsrSeq());
		tbIoTLoginiHistDTO.setLoginFailReason(loginFailReason);
		tbIoTLoginiHistDTO.setUsrOs(usrOs);
		tbIoTLoginiHistDTO.setUsrBrowser(usrBrowser);
		tbIoTLoginiHistDTO.setCustSeq(loginDTO.getCustSeq());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"loginHistDAO.insertTbIotLoginHist");
		try {
			loginHistDAO.insertTbIotLoginHist(tbIoTLoginiHistDTO);
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

	/**
	 * ????????? ????????? ?????? ??????
	 */
	@Override
	public HashMap<String, Object> retrieveIotLogo(LogoDTO logoDTO) throws BizException {
		HashMap<String, Object> rnMap = new HashMap<String, Object>();
		logoDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		logoDTO.setContentType(FileBoardTypeDTO.CUST);
		LogoDTO logo = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"loginDAO.retrieveLogo");
		try {
			logo = loginDAO.retrieveLogo(logoDTO);
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
		if(null != logo) {
			logo.setFilePath(GET_LOGO_IMG + logo.getFilePath());
			rnMap.put("hasLogo", true);
			rnMap.put("logo", logo);
		} else {
			rnMap.put("hasLogo", false);
		}
		return rnMap;
	}

	/**
	 * ????????? ?????? OS, Browser ??????
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void updateUsrOsDevice(HttpServletRequest request) throws BizException {
		String userAgentString = request.getHeader("User-Agent");
		HashMap b = Browser.lookup(userAgentString);
		HashMap o = OS.lookup(userAgentString);
		usrBrowser = (String) b.get("family");
		usrOs = (String) o.get("family");
	}

	// ------------------ ????????? ?????? ????????? -----------------------

	// ???????????? ????????? ??????????????? 3?????? ????????? ??????
	public static boolean checkPwdModDttm(LoginDTO loginDTO) {
		boolean result = false;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd");
			String curr = Integer.parseInt(new SimpleDateFormat("YYYYMMdd").format(System.currentTimeMillis())) + "";
			Date lastPwdDate = formatter.parse(loginDTO.getPwdModDttm());
			Date currDate = formatter.parse(curr);
			if (((currDate.getTime() - lastPwdDate.getTime()) / (24 * 60 * 60 * 1000) > 90)) {
				result = true;
			}
		} catch (ParseException e) {

		}
		return result;
	}

	// ??????????????? ?????? 3??? ????????? ??????????????? ?????? ??????????????? ???????????? ??????
	public boolean checkPwdUsed(LoginDTO loginDTO) {
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		List<LoginDTO> usrPwdHist = null;
		ComInfoDto temp = null;
		loginDTO.setLangSet(langSet);
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "loginDAO.processIotLogin");
		LoginDTO checkNowPwd = new LoginDTO();
		try {
			checkNowPwd = loginDAO.processIotLogin(loginDTO);
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
		if (checkNowPwd.getUsrPwd().equals(loginDTO.getUsrPwd())) {
			return false;
		}
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "loginDAO.retrieveUsrPwdHist");
		try {
			usrPwdHist = loginDAO.retrieveUsrPwdHist(loginDTO);
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
		for (int inx = 0; inx < usrPwdHist.size(); inx++) {
			if (usrPwdHist.get(inx).getUsrPwd().equals(loginDTO.getUsrPwd())) {
				return false;
			}
		}
		return true;
	}

	// ?????? ??????????????? ?????????, ??????????????? ??????????????? ??????
	public String checkPwdVal(LoginUserPersonalInfoDTO usrInfo, String pwd) {
		String result = "";
		String phone8 = "";
		String phone4 = "";
		if(usrInfo.getUsrPhoneNo().length() == 11) {
			phone8 = usrInfo.getUsrPhoneNo().substring(3);
			phone4 = usrInfo.getUsrPhoneNo().substring(7);
		} else {
			phone8 = usrInfo.getUsrPhoneNo().substring(2);
			phone4 = usrInfo.getUsrPhoneNo().substring(6);
		}
		if (pwd.contains(usrInfo.getUsrLoginId())) {
			result = "F";
		} else if (pwd.contains(usrInfo.getUsrPhoneNo())) {
			result = "F";
		} else if (pwd.contains(phone8)) {
			result = "F";
		} else if (pwd.contains(phone4)) {
			result = "F";
		}
		return result;
	}

	// ?????? ??????????????? ????????? 3?????? ????????? ????????? ???????????? ????????? 3?????? ????????? ????????? ???????????? ??????
	public boolean checkPwdCons(String newPwd) {
		ArrayList<Integer> checkNum = new ArrayList<Integer>();
		for (int i = 0; i < newPwd.length(); i++) {
			if (48 <= newPwd.charAt(i) && newPwd.charAt(i) <= 57) {
				checkNum.add((int) newPwd.charAt(i));
				if (checkNum.size() == 3) {
					if (checkNum.get(0) + 1 == checkNum.get(1) && checkNum.get(1) + 1 == checkNum.get(2)) {
						return false;
					} else {
						checkNum.remove(0);
					}
				}
			}
		}
		for (int i = 0; i < newPwd.length(); i++) {
			if (48 <= newPwd.charAt(i) && newPwd.charAt(i) <= 57) {
				checkNum.add((int) newPwd.charAt(i));
				if (checkNum.size() == 3) {
					if (checkNum.get(0) - 1 == checkNum.get(1) && checkNum.get(1) - 1 == checkNum.get(2)) {
						return false;
					} else {
						checkNum.remove(0);
					}
				}
			}
		}
		String check = "";
		for (int i = 0; i < newPwd.length() - 2; i++) {
			check = "";
			check += newPwd.charAt(i);
			if (!isStringInt(check)) {
				if (check.charAt(0) == newPwd.charAt(i + 1) && check.charAt(0) == newPwd.charAt(i + 2)) {
					return false;
				}
			}
		}
		return true;
	}

	// ???????????? ??????, ?????? ??????
	public static boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean pwdReg(String newPwd) {
		Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}|" // ??????,???,??? 3??? ?????? + 8????????????
				+ "(?=.*\\d{3,})(?=.*[$@$!%*#?&])[\\d$@$!%*#?&]{10,}|" // ???,?????? ?????? 8?????? ??????
				+ "(?=.*[A-Za-z])(?=.*[$@$!%*#?&])[A-Za-z$@$!%*#?&]{10,}|" // ???,?????? ?????? 8?????? ??????
				+ "(?=.*[A-Za-z])(?=.*\\d{3,})[A-Za-z\\d]{10,}$"); // ???,??? ?????? 8?????? ??????
		Matcher m = p.matcher(newPwd);
		if (!m.matches()) {
			return false;
		} else {
			return true;
		}
	}

}
