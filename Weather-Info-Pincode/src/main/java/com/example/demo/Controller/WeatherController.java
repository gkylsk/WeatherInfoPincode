package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.WeatherInfo;

@RestController
@RequestMapping("api/weather")
public class WeatherController {

	@GetMapping
	public ResponseEntity<?> getWeatherInfo(){
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
