package kr.co.scp.common.label.svc;

import java.util.HashMap;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.label.dto.TbIotLabelDTO;

public interface TbIotLabelSVC {

	
	public HashMap<String, Object> retrievetbIotLabelList(TbIotLabelDTO tbIotLabelDto) throws BizException;
	
	public void insertTbIotLabel(TbIotLabelDTO tbIotLabelDto) throws BizException;
	
	public void updateTbIotLabel(TbIotLabelDTO tbIotLabelDto) throws BizException;
	
	public void deletetbIotLabel(TbIotLabelDTO tbIotLabelDto) throws BizException;
	
	public HashMap<String, Object> retrievetbIotLabelView(TbIotLabelDTO tbIotLabelDto) throws BizException;	
	
}
