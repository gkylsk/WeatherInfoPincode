package com.example.demo.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.WeatherInfo;
import com.example.demo.Service.WeatherService;

@RestController
@RequestMapping("api/weather")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;
	
	WeatherInfo info = null;
	
	@GetMapping
	public ResponseEntity<WeatherInfo> getWeatherInfo(@RequestParam Integer pincode, @RequestParam @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate date){
		try {
			info = weatherService.getWeatherInfo(pincode, date);
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(info);
	}
}
