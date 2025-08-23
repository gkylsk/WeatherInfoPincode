package com.example.demo.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Dto.PincodeResponse;
import com.example.demo.Dto.WeatherInfoResponse;
import com.example.demo.Entity.Pincode;
import com.example.demo.Entity.WeatherInfo;
import com.example.demo.Repository.PincodeRepository;
import com.example.demo.Repository.WeatherInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherService {

	
	@Autowired
	private PincodeRepository pincodeRepository;
	
	@Autowired
	private WeatherInfoRepository weatherInfoRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${api.key}")
	private String apiKey;
	
	public WeatherInfo getWeatherInfo(Integer pincode, LocalDate date) {
		log.info("pincode:{} and date:{}",pincode, date);
		Optional<WeatherInfo> optionalWeatherInfo = weatherInfoRepository.findByPincodeAndDate(pincode, date);
		
		//optimized api call if the weather info is already present
		if(optionalWeatherInfo.isPresent()) {
			log.info("pincode:"+pincode);
			return optionalWeatherInfo.get();
		}	
		
		Optional<Pincode> optionalPincode = pincodeRepository.findById(pincode);
		double latitude = 0, longitude = 0;
		
		//if pincode already present set lat and long
		if(optionalPincode.isPresent()) {
			latitude = optionalPincode.get().getLatitude();
			longitude = optionalPincode.get().getLongitude();
		}
		else {
			PincodeResponse res = getPincode(pincode);
			latitude = res.lat();
			longitude = res.lon();
			
			Pincode pin = Pincode.builder()
					.pincode(res.zip())
					.latitude(latitude)
					.longitude(longitude)
					.build();
			pincodeRepository.save(pin);
			log.info("Saved pincode:"+pincode);
		}
		
		WeatherInfoResponse response = getWeather(latitude, longitude, date);
		
		WeatherInfo info = null;
		if(response != null) {
			info = WeatherInfo.builder()
			.pincode(pincode)
			.date(date)
			.temperature(response.main().temp())
			.humidity(response.main().humidity())
			.pressure(response.main().pressure())
			.wind(response.wind().speed())
			.place(response.name())
			.country(response.sys().country())
			.description(response.weather().get(0).description())
			.build();
			
			weatherInfoRepository.save(info);
			log.info("Saved weather for pincode:"+pincode);
		}
		return info;
	}
	
//	http://api.openweathermap.org/geo/1.0/zip?zip={zip code},{country code}&appid={API key}
	
	public PincodeResponse getPincode(Integer pincode) {
		//since format is {zip code},{country code} for zip code and input is integer assuming country as india
		String url = "http://api.openweathermap.org/geo/1.0/zip?zip="+pincode+",in&appid="+apiKey;
		
		
		ResponseEntity<PincodeResponse> res = restTemplate.getForEntity(url, PincodeResponse.class);
		if(res.getStatusCode().isError()) {
			throw new RuntimeException(res.getStatusCode().toString());
		}
		log.info("Pincode successfully retrived"+ pincode);
		return res.getBody();
	}
	
//	https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
	
	public WeatherInfoResponse getWeather(double latitude, double longitude, LocalDate date) {
		long timestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
		String url = "https://api.openweathermap.org/data/2.5/weather?lat="+ latitude+ "&lon=" + longitude + "&appid="+ apiKey + "&dt="+ timestamp;
		
		ResponseEntity<WeatherInfoResponse> res = restTemplate.getForEntity(url, WeatherInfoResponse.class);
		if(res.getStatusCode().isError()) {
			throw new RuntimeException(res.getStatusCode().toString());
		}
		log.info("Weather successfully retrieved"+ date);
		return res.getBody();
	}
}
