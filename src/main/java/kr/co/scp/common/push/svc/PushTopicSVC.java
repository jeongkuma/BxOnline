package kr.co.scp.common.push.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;

/**
 * push topic 항목을 세팅할 때 입력한 실행 클래스에 대한 인터페이스.
 * 
 * @author ss
 */
public interface PushTopicSVC {

	/**
	 * 스케쥴러에 의해 주기적으로 실행되는 메소드
	 * 필요한 처리를 수행하고, client 에게 전달할 내용을 리턴.
	 * 리턴 object 는 json 객체로 변환가능한 객체여야함.
	 * 
	 * @param pushItem
	 * @return
	 * @throws BizException
	 */
	public Object run(TbIotTopicDTO pushItem) throws BizException;

}
