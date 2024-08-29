package limchaeyoung.daily_sprout.common.redis.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {

	private final RedisTemplate<String, Object> redisTemplate;

	public void saveAsValue(String key, Object val, Long time, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, val, time, timeUnit);
	}

	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}
}
