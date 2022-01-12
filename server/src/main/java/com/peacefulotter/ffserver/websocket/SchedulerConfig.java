package com.peacefulotter.ffserver.websocket;

import com.peacefulotter.ffserver.control.FFControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;


@EnableScheduling
@Configuration
@Service
public class SchedulerConfig
{
    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 100)
    public void send()
    {
        logger.info("Send polled data", Instant.now());
        template.convertAndSend( "/topic/poll", FFControl.getPoll() );
    }
}
