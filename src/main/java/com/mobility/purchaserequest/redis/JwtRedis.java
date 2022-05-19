package com.mobility.purchaserequest.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtRedis {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void save(String jwt) {
		redisTemplate.opsForValue().set(jwt, jwt);
	}

	public String get(String jwt) {
		return redisTemplate.opsForValue().get(jwt);
	}
}
