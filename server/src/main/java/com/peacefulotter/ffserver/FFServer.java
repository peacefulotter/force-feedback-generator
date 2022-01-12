package com.peacefulotter.ffserver;

import com.peacefulotter.ffserver.control.FFControl;
import com.peacefulotter.ffserver.websocket.WebSocketApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
public class FFServer
{
	public static void main(String[] args)
	{
		// Initialize the Joystick (steering wheel)
		FFControl.initControls();
		// Launch the web server
		SpringApplication.run( FFServer.class, args);
		// Launch the websocket server
		WebSocketApp.main( args );
	}

	@GetMapping(value = "/status", consumes = "application/json", produces = "application/json")
	@ResponseBody
	FFStatus getStatus() {
		System.out.println("Received GET request");
		return FFControl.getStatus();
	}

	@PostMapping(value = "/params", consumes = "application/json", produces = "application/json")
	@ResponseBody FFParams postParams(@RequestBody FFParams params) {
		System.out.println("Received POST request");
		System.out.println(params);
		boolean launched = FFControl.launchFF(params);
		System.out.println("Launched " + launched);
		return params;
	}
}
