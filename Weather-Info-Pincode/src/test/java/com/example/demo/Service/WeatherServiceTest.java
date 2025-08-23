package com.example.demo.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Dto.Main;
import com.example.demo.Dto.PincodeResponse;
import com.example.demo.Dto.Sys;
import com.example.demo.Dto.Weather;
import com.example.demo.Dto.WeatherInfoResponse;
import com.example.demo.Dto.Wind;
import com.example.demo.Entity.Pincode;
import com.example.demo.Entity.WeatherInfo;
import com.example.demo.Repository.PincodeRepository;
import com.example.demo.Repository.WeatherInfoRepository;


@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private PincodeRepository pincodeRepository;
	
	@Mock
	private WeatherInfoRepository weatherInfoRepository;
	
	@InjectMocks
	private WeatherService weatherService;
	
	
	private WeatherInfo mockWeatherinfo;
	private Pincode mockPincode;
	private PincodeResponse mockPincodeResponse;
	private WeatherInfoResponse mockWeatherInfoResponse;
	
	private Integer test_pincode = 411014;
	private LocalDate test_date = LocalDate.of(2020, 10, 15);
	private double lat = 15;
	private double lon = 16;
	
	@BeforeEach
	void setup() throws Exception{
		
		mockWeatherinfo = WeatherInfo.builder()
				.pincode(test_pincode)
				.date(test_date)
				.temperature(27)
				.humidity(64)
				.pressure(1007)
				.wind(8.44)
				.place("xyz")
				.country("IN")
				.description("sunny")
				.build();
		mockPincode = Pincode.builder()
				.pincode(test_pincode)
				.latitude(lat)
				.longitude(lon)
				.build();
		mockPincodeResponse = new PincodeResponse(test_pincode, lat, lon);
		Weather weather = new Weather(0, "sunny");
		mockWeatherInfoResponse = new WeatherInfoResponse("xyz" ,new Sys("IN"), List.of(weather), new Main(27, 64, 1007), new Wind(8.44));
	}
	
	@AfterEach
	void teardown() throws Exception{
		
	}
	
	@Test
	void getWeatherInfoTest() throws Exception{
		when(weatherInfoRepository.findByPincodeAndDate(test_pincode, test_date)).thenReturn(Optional.of(mockWeatherinfo));
		
		WeatherInfo weatherInfo = weatherService.getWeatherInfo(test_pincode, test_date);
		
        assertNotNull(weatherInfo);
        assertEquals(mockWeatherinfo.getPincode(), weatherInfo.getPincode());
        assertEquals(mockWeatherinfo.getDate(), weatherInfo.getDate());
        assertEquals(mockWeatherinfo.getPlace(), weatherInfo.getPlace());
        verify(weatherInfoRepository).findByPincodeAndDate(test_pincode, test_date);
	}
	
	@Test
	void getPincodeTest() throws Exception{
		PincodeResponse mockResponse = new PincodeResponse(test_pincode, lat, lon);
        ResponseEntity<PincodeResponse> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        
        when(restTemplate.getForEntity(anyString(), eq(PincodeResponse.class))).thenReturn(mockResponseEntity);
        PincodeResponse pincodeResponse = weatherService.getPincode(test_pincode);
        
         assertEquals(mockResponse.zip(), pincodeResponse.zip());
         assertEquals(mockResponse.lat(), pincodeResponse.lat());
         assertEquals(mockResponse.lon(), pincodeResponse.lon());
	}
	
	@Test 
	void getWeatherTest() throws Exception{
		Weather weather = new Weather(0, "sunny");
		WeatherInfoResponse mockResponse = new WeatherInfoResponse("xyz" ,new Sys("IN"), List.of(weather), new Main(27, 64, 1007), new Wind(8.44));
		
		ResponseEntity<WeatherInfoResponse> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
		
		when(restTemplate.getForEntity(anyString(), eq(WeatherInfoResponse.class)))
		.thenReturn(mockResponseEntity);
		
		WeatherInfoResponse res = weatherService.getWeather(lat, lon, test_date);
		assertThat(mockResponse).isNotNull();
		assertEquals(mockResponse.name(), res.name());
		assertEquals(mockResponse.main().temp(), res.main().temp());
		assertEquals(mockResponse.sys().country(), res.sys().country());
	}
}
