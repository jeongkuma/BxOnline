package kr.co.scp.common.prog.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.prog.dto.TbIotAuthProgDTO;
import kr.co.scp.common.prog.dto.TbIotProgDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TbIotProgDAO {
	public List<TbIotProgDTO> retrieveTbIotProgList(TbIotProgDTO tbIotProgDto) throws BizException;

	public void deleteTbIotProg(TbIotProgDTO tbIotProgDto) throws BizException;

	public void updateTbIotProg(TbIotProgDTO tbIotProgDto) throws BizException;

	public void insertTbIotProg(TbIotProgDTO tbIotProgDto) throws BizException;

	public int retrieveTbIotPorgCount(TbIotProgDTO tbIotProgDto) throws BizException;

	public TbIotProgDTO searchTbIotProgData(TbIotProgDTO tbIotProgDto) throws BizException;

	public int duplicationCheck(TbIotProgDTO tbIotProgDto) throws BizException;
	
	public HashMap<String, Object> autoIotProgId(TbIotProgDTO tbIotProgDto) throws BizException;

	public List<TbIotAuthProgDTO> retrieveAuthProgList(TbIotAuthProgDTO tbIotAuthProgDTO) throws BizException;
}
