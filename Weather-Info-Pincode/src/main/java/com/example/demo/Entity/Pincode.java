package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_pincode")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Pincode {

	@Id
	private Integer pincode;
	private double latitude;
	private double longitude;
}
