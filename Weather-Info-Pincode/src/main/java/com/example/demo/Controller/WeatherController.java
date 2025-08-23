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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("api/weather")
@Tag(name = "Weather Info API", description = "Endpoint for fetching weather information")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;
	
	WeatherInfo info = null;
	
	@GetMapping
	@Operation(summary = "Get Weather info from pincode and date")
	public ResponseEntity<WeatherInfo> getWeatherInfo(
			@Parameter(description = "Pincode of location", example = "411014") @RequestParam Integer pincode, 
			@Parameter(description = "Date in yyyy-mm-dd format", example = "2020-10-15") @RequestParam @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate for_date){
		try {
			info = weatherService.getWeatherInfo(pincode, for_date);
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(info);
	}
}
