package com.iyihua.ispiderman.message;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.iyihua.ispiderman.entity.Repository;

public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        
        Gson gson = new Gson();
        
        Repository repository = gson.fromJson(message, Repository.class);
        LOGGER.info("object: {}", repository.toString());
        latch.countDown();
    }
}
