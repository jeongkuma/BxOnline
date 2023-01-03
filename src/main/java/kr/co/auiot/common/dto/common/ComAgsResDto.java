package kr.co.auiot.common.dto.common;

import com.fasterxml.jackson.databind.JsonNode;
import kr.co.abacus.common.dto.common.ResultDescDto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ComAgsResDto<T> {
	private JsonNode account = null;
	private JsonNode home = null;
	private JsonNode device = null;
	private JsonNode report = null;
	private JsonNode reason = null;
	private ResultDescDto result = null;
	private T agsResult = null;

	public JsonNode getAccount() {
		return account;
	}

	public void setAccount(JsonNode account) {
		this.account = account;
	}

	public JsonNode getHome() {
		return home;
	}

	public void setHome(JsonNode home) {
		this.home = home;
	}

	public JsonNode getDevice() {
		return device;
	}

	public void setDevice(JsonNode device) {
		this.device = device;
	}

	public JsonNode getReport() {
		return report;
	}

	public void setReport(JsonNode report) {
		this.report = report;
	}

	public JsonNode getReason() {
		return reason;
	}

	public void setReason(JsonNode reason) {
		this.reason = reason;
	}

	public ResultDescDto getResult() {
		return result;
	}

	public void setResult(ResultDescDto result) {
		this.result = result;
	}

	public T getAgsResult() {
		return agsResult;
	}

	public void setAgsResult(T agsResult) {
		this.agsResult = agsResult;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
