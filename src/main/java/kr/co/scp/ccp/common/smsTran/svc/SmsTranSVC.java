package kr.co.scp.ccp.common.smsTran.svc;

import kr.co.scp.ccp.common.smsTran.dto.TbIotSmsTranDTO;

public interface SmsTranSVC {
	public void insertTbIotSmsTran(TbIotSmsTranDTO tbIotSmsTranDTO, boolean isManualOmsLog);
}
