package kr.co.scp.ccp.iotEDev.dao;

import kr.co.scp.ccp.iotEDev.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IotEDevDetSmsDAO {

	public List<TbIotEdevDetSmsDTO> retrieveEDevDetSmsList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO);
	
	public Integer retrieveEDevDetSmsListCount(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO);
	
	public List<TbIotEdevDetAttbDTO> retrieveEDevDetDevAttbList_EDev(TbIotEdevDetAttbDTO tbIotEdevDetAttbDTO);
	
	public List<TbIotEdevDetAttbDTO> retrieveEDevDetDevAttbList_SDev(TbIotEdevDetAttbDTO tbIotEdevDetAttbDTO);
	
	public List<TbIotEdevDetSmsDTO> retrieveEDevDetUserList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO);

	public List<TbIotEdevDetRcvUsrDTO> retrieveEDevDetUserListByDetSetSeq(TbIotEdevDetRcvUsrDTO tbIotEdevDetRcvUsrDTO);
		
	public void insertEDevDetSet(TbIotEdevDetSetDTO tbIotEdevDetSetDTO);
//	public Integer insertEDevDetSet(TbIotEdevDetSetDTO tbIotEdevDetSetDTO); 

//	public void insertEDevDetSms(TbIotEdevDetSmsUsrDTO tbIotEdevDetUsrDTO);

	public int insertEDevDetSms(List<TbIotEdevDetSmsUsrDTO> params);

	public TbIotEdevDetSmsDTO retrieveEDevDetSetSeq(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO); 

//	public TbIotEdevDetSmsUsrDTO retrieveEDevLastId(TbIotEdevDetSmsUsrDTO tbIotEdevDetSmsUsrDTO);

	public TbIotEdevDetSetDTO retrieveEDevDetSetChk(TbIotEdevDetSetDTO tbIotEdevDetSetDTO);

	public TbIotEdevDetSmsUsrDTO retrieveEDevDetSmsChk(TbIotEdevDetSmsUsrDTO tbIotEdevDetUsrDTO);
	
	public void updateEDevDetSet(TbIotEdevDetSetDTO tbIotEdevDetSetDTO);

	public void updateEDevDetSms(TbIotEdevDetSmsUsrDTO tbIotEdevDetUsrDTO); 

	public void deleteEDevDetRcvUsr(TbIotEdevDetSmsUsrDTO tbIotEdevDetSmsUsrDTO);
	
	public int deleteEDevDetRcvUsrAll(List<TbIotEdevDetSmsUsrDTO> params);
	
	public List<TbIotEdevDetCmCdDTO> retrieveEDevDetCmCd(TbIotEdevDetCmCdDTO tbIotEdevDetCmCdDTO);

	public List<TbIotEdevDetSmsDTO> retrieveEDevDetMessageList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO);

	public TbIotEdevDetAttbDTO retrieveEDevDetDevCurValSeq(TbIotEdevDetAttbDTO tbIotEdevDetAttbDTO);

	public void updateEDevCurVal(TbIotEdevDetSetDTO tbIotEdevDetSetDTO);
	
}
