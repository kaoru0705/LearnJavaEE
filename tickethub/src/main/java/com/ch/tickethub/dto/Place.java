package com.ch.tickethub.dto;

import lombok.Data;

@Data
public class Place {
	private String place_name;
	private String address;
	private double latitude;
	private double longitude;
}
