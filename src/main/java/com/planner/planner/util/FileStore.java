package com.planner.planner.util;

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
	
	//private static final String location = "image";
	public FileStore() {
		
	}
	
	public String createFilePath(FileLocation location, MultipartFile file, String subLocation) {
		StringBuilder builder = new StringBuilder();
		builder.append(location).append("\\");
		builder.append(subLocation).append("\\");
		builder.append(UUID.randomUUID().toString()).append("\\");
		builder.append(file.getOriginalFilename());
		return builder.toString();
	}
}
