package com.planner.planner.Object;

public class Image {

	private String path;
	private String absolutePath;
	private String name;

	public Image() {

	}

	public Image(String path, String absolutePath, String name) {
		this.path = path;
		this.absolutePath = absolutePath;
		this.name = name;
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
	@Override
	public String toString() {
		return "Image [path=" + path + ", absolutePath=" + absolutePath + ", name=" + name + "]";
	}
}