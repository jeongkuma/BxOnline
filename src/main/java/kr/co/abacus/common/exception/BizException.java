package kr.co.abacus.common.exception;

import kr.co.abacus.common.dto.common.ResultDescDto;

import java.util.List;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String ymlKey;
	private List<String> arrayReplace;
	private Throwable throwable = null;
	private ResultDescDto rst;

	public BizException(ResultDescDto rst) {
		this.rst = rst;
	}

	public BizException(Throwable throwable, ResultDescDto rst) {
		this.throwable = throwable;
		this.rst = rst;
	}

	public BizException(String ymlKey) {
		this.ymlKey = ymlKey;
	}

	public BizException(Throwable throwable, String ymlKey) {
		this.throwable = throwable;
		this.ymlKey = ymlKey;
	}

	public BizException(String ymlKey, List<String> arrayReplace) {
		this.ymlKey = ymlKey;
		this.arrayReplace = arrayReplace;
	}

	public BizException(Throwable throwable, String ymlKey, List<String> arrayReplace) {
		this.throwable = throwable;
		this.ymlKey = ymlKey;
		this.arrayReplace = arrayReplace;
	}

	public BizException(String code, String desc, String type, String status) {
		this.rst = new ResultDescDto();
		rst.setCode(code);
		rst.setDesc(desc);
		rst.setType(type);
		rst.setStatus(status);
	}

	public BizException(Throwable throwable, String code, String desc, String type, String status) {
		this.throwable = throwable;
		this.rst = new ResultDescDto();
		rst.setCode(code);
		rst.setDesc(desc);
		rst.setType(type);
		rst.setStatus(status);
	}

	public String getYmlKey() {
		return ymlKey;
	}

	public void setYmlKey(String ymlKey) {
		this.ymlKey = ymlKey;
	}

	public List<String> getArrayReplace() {
		return arrayReplace;
	}

	public void setArrayReplace(List<String> arrayReplace) {
		this.arrayReplace = arrayReplace;
	}

	public ResultDescDto getRst() {
		return rst;
	}

	public void setRst(ResultDescDto rst) {
		this.rst = rst;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
