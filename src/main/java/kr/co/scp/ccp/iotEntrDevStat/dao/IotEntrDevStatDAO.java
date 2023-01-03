package kr.co.scp.ccp.iotEntrDevStat.dao;

import kr.co.scp.ccp.iotEntrDevStat.dto.TbIotEntrDevStatDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IotEntrDevStatDAO {
	public List<TbIotEntrDevStatDTO> retrieveHourStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveDayStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveWeekStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveMonthStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public Integer retrieveHourStatListCount(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public Integer retrieveDayStatListCount(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public Integer retrieveWeekStatListCount(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public Integer retrieveMonthStatListCount(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveHourStatAvgList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveDayStatAvgList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveWeekStatAvgList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveMonthStatAvgList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public Integer retrieveHourStatAvgListCount(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public Integer retrieveDayStatAvgListCount(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public Integer retrieveWeekStatAvgListCount(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public Integer retrieveMonthStatAvgListCount(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveHourStatListExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveDayStatListExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveWeekStatListExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveMonthStatListExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveHourStatAvgListExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveDayStatAvgListExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveWeekStatAvgListExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
	public List<TbIotEntrDevStatDTO> retrieveMonthStatAvgListExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO);
}
