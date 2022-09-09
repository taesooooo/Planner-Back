package com.planner.planner.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
		
		Image image = createImage(builder.toString(), baseLocation + builder.toString(), name);
		
		return image;
	}
	
	public List<Image> createFilePaths(List<MultipartFile> files, String subLocation) {
		List<Image> images = new ArrayList<Image>();
		for(MultipartFile file : files) {
			String name = System.nanoTime() + "_" + file.getOriginalFilename();
			StringBuilder builder = new StringBuilder();
			builder.append(subLocation).append("\\");
			builder.append(name);
			
			Image image = createImage(builder.toString(), baseLocation + builder.toString(), name);
			images.add(image);
		}

		return images;
	}
	
	private Image createImage(String path, String absolutePath, String name) {
		Image image = new Image();
		image.setPath(path);
		image.setAbsolutePath(absolutePath);
		image.setName(name);
		return image;
	}
}
