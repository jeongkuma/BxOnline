package kr.co.abacus.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class RedisHelper {

	public final static long NOT_EXPIRE = -1; // 만료 시간 설정 안 함

	@Autowired
	private RedisTemplate redisTemplate;

	public Object get(final String key) {
		try {
			DataType type = redisTemplate.type(key);
			if (DataType.NONE == type) {
				return null;
			} else if (DataType.STRING == type) {
				return redisTemplate.opsForValue().get(key);
			} else if (DataType.LIST == type) {
				return redisTemplate.opsForList().range(key, 0, NOT_EXPIRE);
			} else if (DataType.ZSET == type) {
				return redisTemplate.opsForZSet().range(key, 0, NOT_EXPIRE);
			} else if (DataType.HASH == type) {
				return redisTemplate.opsForHash().entries(key);
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("from redis get error = {} ", e.getMessage());
			return null;
		}
	}

	public <T> T get(String key, Class<T> clazz, long expire) {
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		String value = (String) operations.get(key);
		if (expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
		return value == null ? null : JsonUtils.fromJson(value, clazz);
	}

	public <T> T get(String key, Class<T> clazz) {
		return get(key, clazz, NOT_EXPIRE);
	}

	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, JsonUtils.objectToString(value));
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean setExpire(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, JsonUtils.objectToString(value));
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean setExpireAt(final String key, Object value, Date expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, JsonUtils.objectToString(value));
			redisTemplate.expireAt(key, expireTime);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean setList(final String key, Object value) {
		boolean result = false;
		try {
			ListOperations listOperations = redisTemplate.opsForList();
			listOperations.leftPushAll(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean leftPush(final String key, Object value) {
		try {
			ListOperations listOperations = redisTemplate.opsForList();
			listOperations.leftPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Object rightPop(final String key) {
		try {
			ListOperations listOperations = redisTemplate.opsForList();
			return listOperations.rightPop(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Long remQueue(final String key, Object value) {
		try {
			ListOperations listOperations = redisTemplate.opsForList();
			return listOperations.remove(key, 0, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	public Long queueCount(final String key) {
		try {
			ListOperations listOperations = redisTemplate.opsForList();
			return listOperations.size(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0) {
			redisTemplate.delete(keys);
		}
	}

	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

}
