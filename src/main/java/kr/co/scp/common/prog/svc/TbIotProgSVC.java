package kr.co.scp.common.prog.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.prog.dto.TbIotAuthProgDTO;
import kr.co.scp.common.prog.dto.TbIotProgDTO;

import java.util.HashMap;
import java.util.List;

public interface TbIotProgSVC {
	public HashMap<String, Object> retrieveTbIotProgList(TbIotProgDTO tbIotProgDto) throws BizException;

	public void deleteTbIotProg(TbIotProgDTO tbIotProgDTO) throws BizException;

	public void updateTbIotProg(TbIotProgDTO tbIotProgDTO) throws BizException;

	public void insertTbIotProg(TbIotProgDTO tbIotProgDTO) throws BizException;

	public TbIotProgDTO searchTbIotProgData(TbIotProgDTO tbIotProgDto) throws BizException;

	public int duplicationCheck(TbIotProgDTO tbIotProgDto) throws BizException;

	public List<TbIotAuthProgDTO> retrieveAuthProgList(TbIotAuthProgDTO tbIotAuthProgDTO) throws BizException;
	
	public HashMap<String, Object> autoIotProgId(TbIotProgDTO tbIotProgDto) throws BizException;
}
