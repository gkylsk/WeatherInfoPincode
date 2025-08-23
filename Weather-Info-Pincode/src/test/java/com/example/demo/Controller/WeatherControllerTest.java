package com.example.demo.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; 

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Entity.WeatherInfo;
import com.example.demo.Service.WeatherService;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private WeatherService weatherService;
	
	private WeatherInfo mockWeatherInfo;
	
	@BeforeEach
	void setup() throws Exception{
		mockWeatherInfo = WeatherInfo.builder()
				.pincode(411014)
				.date(LocalDate.of(2020, 10, 15))
				.temperature(27)
				.humidity(64)
				.pressure(1007)
				.wind(8.44)
				.place("xyz")
				.country("IN")
				.description("sunny")
				.build();
	}
	
	@AfterEach
	void teardown() {
		
	}
	
	@Test
	void testGetWeatherInfo_Success() throws Exception {
		Integer pincode = 411014;
		LocalDate date = LocalDate.of(2020, 10, 15);
		
		when(weatherService.getWeatherInfo(pincode, date)).thenReturn(mockWeatherInfo);
		
		mockMvc.perform(get("/api/weather")
				.param("pincode", pincode.toString())
				.param("date", date.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.pincode").value(mockWeatherInfo.getPincode()))
		.andExpect(jsonPath("$.date").value(mockWeatherInfo.getDate().toString()))
		.andExpect(jsonPath("$.temperature").value(mockWeatherInfo.getTemperature()))
		.andExpect(jsonPath("$.humidity").value(mockWeatherInfo.getHumidity()))
		.andExpect(jsonPath("$.pressure").value(mockWeatherInfo.getPressure()))
		.andExpect(jsonPath("$.wind").value(mockWeatherInfo.getWind()))
		.andExpect(jsonPath("$.place").value(mockWeatherInfo.getPlace()))
		.andExpect(jsonPath("$.country").value(mockWeatherInfo.getCountry()))
		.andExpect(jsonPath("$.description").value(mockWeatherInfo.getDescription()));
	}
	
	@Test
	void testGetWeatherInfo_NotFound() throws Exception{
		Integer pincode = 123456;
		LocalDate date = LocalDate.of(2025, 8, 23);
		
		when(weatherService.getWeatherInfo(pincode, date))
		.thenThrow(new RuntimeException("Not Found"));
		
		mockMvc.perform(get("/api/weather")
				.param("pincode", pincode.toString())
				.param("date", date.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
}
