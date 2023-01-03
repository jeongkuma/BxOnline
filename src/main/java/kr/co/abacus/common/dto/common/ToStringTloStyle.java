package kr.co.abacus.common.dto.common;

import org.apache.commons.lang3.builder.ToStringStyle;

public class ToStringTloStyle extends ToStringStyle {
	public static final ToStringStyle TLO_STYLE = new ToStringTloStyle();
	private static final long serialVersionUID = 1L;

	private ToStringTloStyle() {
		super();
		this.setUseClassName(false);
		this.setUseIdentityHashCode(false);
		this.setContentStart("");
		this.setFieldNameValueSeparator("=");
		this.setNullText("");
		this.setFieldSeparator("|");
		this.setContentEnd("");
	}

	@Override
	public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
		if (value != null) {
			appendFieldStart(buffer, fieldName);
			appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
			appendFieldEnd(buffer, fieldName);
		}
	}
}
