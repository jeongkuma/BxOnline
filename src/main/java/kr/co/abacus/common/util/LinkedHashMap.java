package kr.co.abacus.common.util;

public class LinkedHashMap<K, V> extends java.util.LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (K key : this.keySet()) {
			if (count != 0) {
				sb.append("|").append(key).append("=").append(this.get(key));
			} else {
				sb.append(key).append("=").append(this.get(key));
			}
			count++;
		}
		return sb.toString();
	}
}
