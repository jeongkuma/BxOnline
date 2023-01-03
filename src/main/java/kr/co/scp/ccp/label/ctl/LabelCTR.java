package kr.co.scp.ccp.label.ctl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.MultiLabalConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/online/label")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class LabelCTR {
	@Autowired
	private MultiLabalConfig multiLabalConfig;

	@Autowired
	private ResponseComUtil responseComUtil;


	@RequestMapping(value = "/getlabel", method = RequestMethod.POST)
	public ComResponseDto<?> getlabel(HttpServletRequest request) throws BizException {
//		System.out.println("label-----------------------------------");
//		System.out.println(multiLabalConfig.getLabelInfo().get("id"));
		ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();	
		
		//String langSet = comRequestDto.getComInfoDto().getXlang();
		//log.debug("=== getlabel comRequestDto.getComInfoDto().getXlang() :: langSet ::" + langSet );
		
		//if (StringUtils.isEmpty(langSet)) {
//		String langSet = (String) currentRequestAttributes.getRequest().getSession().getAttribute(ComConstant.X_LANG_SET);
//			log.debug("=== getlabel currentRequestAttributes.getSession().getXlang() :: langSet :: " + langSet );
		//}
		
//		if (StringUtils.isEmpty(langSet)) {
//			langSet = ComConstant.DEFAULT_CHAR_SET;
//		}
//		
		String langSet = "";
		langSet = request.getHeader(ComConstant.X_LANG_SET);
		if (StringUtils.isEmpty(langSet)) {
			langSet = ComConstant.DEFAULT_CHAR_SET;
		}
		log.debug("=== getlabel header last langSet ::" + request.getHeader(ComConstant.X_LANG_SET));
		
		currentRequestAttributes.getRequest().getSession().setAttribute(ComConstant.X_LANG_SET, langSet);
		return responseComUtil.setResponse200ok(multiLabalConfig.getLabelInfo());
	}
	
	
}
