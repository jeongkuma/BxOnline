package kr.co.abacus.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CaseInsensitiveMap<K, V> extends java.util.LinkedHashMap<K, V> {
	
	private static final long serialVersionUID = 1L;
	
	//convertedKey, realKey
	private Map<Object,Object> keyMap = new HashMap<>();
	
	@Override
	public V put(K key, V value) {
		keyMap.put(convertKey(key),key );
		return super.put(key, value);
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Set<? extends K> keySet = m.keySet();
		for(Object key : keySet) {
			keyMap.put(convertKey(key),key);
		}
		super.putAll(m);
	}
	
	@Override
	public V get(Object key) {
		 Object realKey = keyMap.get(convertKey(key));
		if(realKey == null) {
			return null;
		}
		return super.get(realKey);
	}
	
    protected Object convertKey(final Object key) {
        if (key != null) {
            final char[] chars = key.toString().toCharArray();
            for (int i = chars.length - 1; i >= 0; i--) {
                chars[i] = Character.toLowerCase(Character.toUpperCase(chars[i]));
            }
            return new String(chars);
        }
        return null;
    }
    
}
