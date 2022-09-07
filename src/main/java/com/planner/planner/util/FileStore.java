package com.planner.planner.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStore {
	public enum FileLocation {
		USER("user");
		
		private String location;
		FileLocation(String location) {
			this.location = location;
		}
		
		public String locationName() {
			return location;
		}
	}
	
	private String baseLocation;
	
	public FileStore(String baseLocation) {
		this.baseLocation = baseLocation;
	}

	public String createFilePath(MultipartFile file, String subLocation){
		StringBuilder builder = new StringBuilder();
		builder.append(baseLocation).append("\\");
		builder.append(subLocation).append("\\");
		//builder.append(UUID.randomUUID().toString()).append("\\");
		builder.append(file.getOriginalFilename());
		return builder.toString();
	}
}
