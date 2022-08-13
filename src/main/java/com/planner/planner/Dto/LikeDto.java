package com.planner.planner.Dto;

import java.time.LocalDate;

import com.planner.planner.Entity.Like;
import com.planner.planner.Entity.Like.Builder;

public class LikeDto {
	private int likeId;
	private LocalDate likeDate;
	private int id;
	private int accountId;
	
	public static class Builder {
		private int likeId;
		private LocalDate likeDate;
		private int id;
		private int accountId;
		
		public Builder setLikeId(int likeId) {
			this.likeId = likeId;
			return this;
		}
		
		public Builder setLikeDate(LocalDate likeDate) {
			this.likeDate = likeDate;
			return this;
		}
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}
		
		public Builder setAccountId(int accountId) {
			this.accountId = accountId;
			return this;
		}
		
		public LikeDto build() {
			return new LikeDto(this);
		}
	}

	public LikeDto() {
	}

	public LikeDto(Builder builder) {
		this.likeId = builder.likeId;
		this.likeDate = builder.likeDate;
		this.id = builder.id;
		this.accountId = builder.accountId;
	}

	public int getLikeId() {
		return likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}

	public LocalDate getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(LocalDate likeDate) {
		this.likeDate = likeDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public Like toEntity() {
		return new Like.Builder().setLikeId(likeId).setLikeDate(likeDate).setId(accountId).setAccountId(accountId).build();
	}

	@Override
	public String toString() {
		return "LikeDto [likeId=" + likeId + ", likeDate=" + likeDate + ", id=" + id + ", accountId=" + accountId + "]";
	}
	
}
