package com.iyihua.ispiderman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class MessageService {

	@Autowired
	StringRedisTemplate template;
	
	public void send(String topic, Object obj) {
		template.convertAndSend(topic, new Gson().toJson(obj));
	}
}
