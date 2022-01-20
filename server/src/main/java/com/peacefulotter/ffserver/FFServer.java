package com.peacefulotter.ffserver;

import com.peacefulotter.ffserver.args.FFAngle;
import com.peacefulotter.ffserver.args.FFParams;
import com.peacefulotter.ffserver.args.FFStatus;
import com.peacefulotter.ffserver.control.FFControl;
import com.peacefulotter.ffserver.control.FFRecorder;
import com.peacefulotter.ffserver.file.FileHandler;
import com.peacefulotter.ffserver.websocket.FFPoll;
import com.peacefulotter.ffserver.websocket.WebSocketApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;


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
	@ResponseBody FFStatus getStatus() {
		return FFControl.getStatus();
	}

	@PostMapping(value = "/params", consumes = "application/json", produces = "application/json")
	@ResponseBody FFParams postParams( @RequestBody FFParams params ) {
		FFControl.launchFF(params);
		return params;
	}

	@PostMapping(value = "/control", consumes = "application/json", produces = "application/json")
	@ResponseBody FFPoll controlWheel( @RequestBody FFAngle angle ) {
		FFControl.moveAngle(angle);
		return FFControl.getPoll();
	}

	@PostMapping(value = "/start", consumes = "application/json", produces = "application/json")
	void startRecording() {
		FFRecorder.startRecording();
	}

	@PostMapping(value = "/stop", consumes = "application/json", produces = "application/json")
	void stopRecording() {
		Queue<Float> angles = FFRecorder.stopRecording();
			FileHandler.writeToFile( angles, "rec.txt" );
	}
}
