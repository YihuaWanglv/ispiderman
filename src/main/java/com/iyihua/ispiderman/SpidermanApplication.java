package com.iyihua.ispiderman;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.gson.Gson;
import com.iyihua.ispiderman.entity.Repository;

@SpringBootApplication
@EnableScheduling
public class SpidermanApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpidermanApplication.class);
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpidermanApplication.class);
		
//		ApplicationContext ctx = SpringApplication.run(SpidermanApplication.class, args);
//		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//		CountDownLatch latch = ctx.getBean(CountDownLatch.class);
//
//		LOGGER.info("Sending message...");
//		Repository r = Repository.builder().content("ggg").title("title3").build();
//		template.convertAndSend("post-gzaic", new Gson().toJson(r));
//
//		latch.await();
//
//		System.exit(0);
	}
}