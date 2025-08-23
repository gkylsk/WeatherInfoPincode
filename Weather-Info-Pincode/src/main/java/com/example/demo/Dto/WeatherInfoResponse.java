package com.example.demo.Dto;

import java.util.List;

public record WeatherInfoResponse(String name, Sys sys, List<Weather> weather, Main main, Wind wind) {

}
