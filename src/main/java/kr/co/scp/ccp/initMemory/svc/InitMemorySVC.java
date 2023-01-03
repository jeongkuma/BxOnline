package kr.co.scp.ccp.initMemory.svc;

import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.exception.BizException;

public interface InitMemorySVC {
	public void initMemoryC(ComRequestDto comRequestDto) throws BizException;
	public void initMemoryA(ComRequestDto comRequestDto) throws BizException;
	public void initOuifSync(ComRequestDto comRequestDto) throws BizException;
}
