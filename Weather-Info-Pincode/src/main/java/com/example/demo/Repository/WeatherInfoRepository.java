package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.WeatherInfo;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {

}
