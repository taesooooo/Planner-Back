package com.planner.planner.Common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class H2Function {
	public static long dateDifference(Timestamp dateTime1, Timestamp dateTime2) {
		return dateTime1.toLocalDateTime().toLocalDate().toEpochDay() - dateTime2.toLocalDateTime().toLocalDate().toEpochDay();
	}
}
