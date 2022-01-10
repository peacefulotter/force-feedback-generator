package com.peacefulotter.ffserver;

import com.peacefulotter.ffserver.control.FFControl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class FFServer
{
	public static void main(String[] args) {
		SpringApplication.run( FFServer.class, args);
	}

	@PostMapping(value = "/params", consumes = "application/json", produces = "application/json")
	@ResponseBody FFParams postParams(@RequestBody FFParams params) {
		System.out.println("params");
		System.out.println(params);
		params.incr();
		FFControl.launchFF();
		return params;
	}
}
