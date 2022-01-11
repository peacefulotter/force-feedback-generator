package com.peacefulotter.ffserver.websocket;

import com.peacefulotter.ffserver.FFStatus;
import com.peacefulotter.ffserver.control.FFControl;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class StatusController
{
    @CrossOrigin(origins = "http://localhost:3000")
    @MessageMapping("/greetings")
    @SendTo("/topic/greetings")
    public FFStatus getStatus()
    {
        System.out.println("In the ws controller");
        return FFControl.getStatus();
    }
}
