package com.example.demo.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.Entity.WeatherInfo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class WeatherInfoRepositoryTest {

	@Autowired
	private WeatherInfoRepository mockWeatherInfoRepository;
	
	private WeatherInfo mockWeatherinfo;
	private Integer test_pincode = 123456;
	private LocalDate test_date = LocalDate.of(2020, 10, 15);
	
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
		mockWeatherInfoRepository.save(mockWeatherinfo);
	}
	
	@AfterEach()
	void teardown() throws Exception{
		mockWeatherInfoRepository.deleteAll();
	}
	
	@Test
	void findByPincodeAndDate_Success() throws Exception{
		Optional<WeatherInfo>  result = mockWeatherInfoRepository.findByPincodeAndDate(test_pincode, test_date);
		
		assertThat(result).isPresent();
		assertThat(result.get().getPincode()).isEqualTo(test_pincode);
		assertThat(result.get().getDate()).isEqualTo(test_date);
		assertThat(result.get().getTemperature()).isEqualTo(27);
	}
	
	@Test
	void findByPincodeAndDate_NotFound() throws Exception{
		Optional<WeatherInfo> result = mockWeatherInfoRepository.findByPincodeAndDate(411012, test_date);
		assertThat(result).isNotPresent();
	}
}
