package kr.co.scp.common.api.svc;

import java.util.HashMap;
import java.util.List;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.api.dto.TbIotApiDTO;
import kr.co.scp.common.api.dto.TbIotParamDTO;

public interface TbIotApiSVC {
	public HashMap<String, Object> retrieveTbIotApiList(TbIotApiDTO tbIotApiDto) throws BizException ;

	public void insertTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException ;

	public void updateTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException ;

	public void deleteTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException ;

	public int duplicationCheckApiNm(TbIotApiDTO tbIotApiDto) throws BizException ;

	public int duplicationCheckApiUri(TbIotApiDTO tbIotApiDto) throws BizException ;

	public TbIotApiDTO searchTbIotApiParam(TbIotApiDTO tbIotApiDto) throws BizException ;

	public HashMap<String, Object> retrieveTbIotDashApiList(TbIotApiDTO tbIotApiDto) throws BizException ;

	public List<TbIotParamDTO> retrieveTbIotParamList(TbIotParamDTO tbIotParamDto) throws BizException ;

	public List<HashMap<String, Object>> retrieveTbIotParamCd(TbIotParamDTO tbIotParamDto) throws BizException ;

}
