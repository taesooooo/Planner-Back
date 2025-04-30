package com.planner.planner.Common;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coordinate {
	private double latitude;
	private double longitude;
	
	public String toWKT() {
		return "POINT(" + longitude + " " + latitude + ")";
	}
}
