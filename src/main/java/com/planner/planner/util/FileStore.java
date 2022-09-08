package com.planner.planner.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Image;

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
	
	public String getBaseLocation() {
		return baseLocation;
	}

	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}
	
	public File getFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			return file;
		}
		else {
			return null;
		}
	}

	public Image createFilePath(MultipartFile file, String subLocation) {
		String name = System.nanoTime() + "_" + file.getOriginalFilename();
		
		StringBuilder builder = new StringBuilder();
		builder.append(subLocation).append("\\");
		builder.append(name);
		
		//builder.append(baseLocation).append("\\");
		//builder.append(file.getOriginalFilename());
		
		Image image = new Image();
		image.setPath(builder.toString());
		image.setAbsolutePath(baseLocation + builder.toString());
		image.setName(name);
		return image;
	}
}
