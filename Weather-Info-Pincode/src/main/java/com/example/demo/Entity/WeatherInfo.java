package com.example.demo.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_weatherInfo")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WeatherInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer pincode;
	private LocalDate date;
	private double temperature;
	private int humidity;
	private int pressure;
	private double wind;
	private String place;
	private String country;
	private String description;
	
}
