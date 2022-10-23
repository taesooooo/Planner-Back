package com.planner.planner.Object;

public class SpotItem {
	private String item;
	private int likeCount;

	public static class Builder {
		private String item;
		private int likeCount;
		
		public void setItem(String item) {
			this.item = item;
		}
		public void setLikeCount(int likeCount) {
			this.likeCount = likeCount;
		}
	}

	public SpotItem(Builder builder) {
		this.item = builder.item;
		this.likeCount = builder.likeCount;
	}

	public String getItem() {
		return item;
	}

	public int getLikeCount() {
		return likeCount;
	}

}
