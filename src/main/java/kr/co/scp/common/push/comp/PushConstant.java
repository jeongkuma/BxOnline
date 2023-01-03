package kr.co.scp.common.push.comp;

public class PushConstant {

	public enum TMPL_GUBUN {

		/**
		 * 주기를 나타내는 tmplGubun 특수키워드
		 */
		INTERVAL("_interval_");

		private String value;

		private TMPL_GUBUN(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
