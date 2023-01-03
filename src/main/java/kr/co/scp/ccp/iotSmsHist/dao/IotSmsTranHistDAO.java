package kr.co.scp.ccp.iotSmsHist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistDTO;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsConditionDTO;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsConditiontReqDTO;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsTranHistDTO;

@Mapper
public interface IotSmsTranHistDAO {

	List<TbIotSmsTranHistDTO> retrieveIotAlarmHistList(TbIotSmsTranHistDTO tbIotSmsTranHistDTO);

	Integer retrieveIotAlarmCount(TbIotSmsTranHistDTO tbIotSmsTranHistDTO);

	List<TbIotSmsConditionDTO> retrieveTrEtc1List(TbIotSmsConditiontReqDTO tbIotSmsConditiontReqDTO);

	List<TbIotSmsConditionDTO> retrieveTrEtc2List(TbIotSmsConditiontReqDTO tbIotSmsConditiontReqDTO);

	List<TbIotSmsTranHistDTO> retrieveIotSmsTranHistNotPage(TbIotSmsTranHistDTO tbIotSmsTranHistDTO);

	// sms 발송현황
	List<TbIotSmsTranHistDTO> retrieveIotSmsReportList(TbIotSmsTranHistDTO tbIotSmsTranHistDTO);
	Integer retrieveIotSmsReportCount(TbIotSmsTranHistDTO tbIotSmsTranHistDTO);
}
