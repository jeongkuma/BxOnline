package kr.co.abacus.common.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @FileName : MultiLangManager.java
 * @Project : acfw
 * @Date : 2019. 2. 04.
 * @Author : ASUS
 * @History : 날자 작성자 내용 ---------- ----------- -------------------------------
 *          2019.02.04 애버커스 최초작성 2019.02.23 애버커스 다국어 처리를 위한 추가호 변경
 * @Description :
 */
@Component
public class MultiLangManager {

	/**
	 * @Desctiption : 다국어 처리를 위한 메세지 빈 주입
	 */
	@Autowired
	private MultiLangConfig multiLangConfig;

	/**
	 * @Method Name : getMessage
	 * @Date : 2019. 2. 23.
	 * @Author : ASUS
	 * @History : 날자 작성자 내용 ---------- ----------- -------------------------------
	 *          2019.02.23 애버커스 주석 정리
	 * @Description : 다국어 메세지를 읽어 온다. lang : multiLang.yml에 정의 된 level 갑을 의미함 key :
	 *              메게지 코드
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		return multiLangConfig.getMessage(key);
	}

}
