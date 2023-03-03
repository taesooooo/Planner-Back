package com.planner.planner.Common;

import org.springframework.web.multipart.MultipartFile;

public class Image {

	private String path;
	private String absolutePath;
	private String name;
	private MultipartFile image;

	public Image() {

	}

	public Image(String path, String absolutePath, String name, MultipartFile image) {
		this.path = path;
		this.absolutePath = absolutePath;
		this.name = name;
		this.image = image;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Image [path=" + path + ", absolutePath=" + absolutePath + ", name=" + name + ", image=" + image + "]";
	}
}
