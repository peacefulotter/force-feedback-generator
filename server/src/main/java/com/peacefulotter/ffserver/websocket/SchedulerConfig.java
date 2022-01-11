package com.peacefulotter.ffserver.websocket;

import com.peacefulotter.ffserver.FFStatus;
import com.peacefulotter.ffserver.control.FFControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;

@EnableScheduling
@Configuration
public class SchedulerConfig
{
    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 3000)
    public void send()
    {
        System.out.println("Sending to Client " + FFControl.getStatus() );
        // FFControl.getStatus()
        // content-type: "text/plain;charset=UTF-8"
        template.convertAndSend(
                "/topic/greetings",
                FFControl.getStatus(),
                Collections.singletonMap( MessageHeaders.CONTENT_TYPE, "application/json") );
    }
}
