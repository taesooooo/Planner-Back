package com.planner.planner.Entity;

import java.time.LocalDate;

import com.planner.planner.Dto.LikeDto;

public class Like {
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
		
		public Like build() {
			return new Like(this);
		}
	}

	public Like() {
		
	}

	public Like(Builder builder) {
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
	
	public LikeDto toDto() {
		return new LikeDto.Builder().setLikeId(likeId).setLikeDate(likeDate).setId(accountId).setAccountId(accountId).build();
	}

	@Override
	public String toString() {
		return "Like [likeId=" + likeId + ", likeDate=" + likeDate + ", id=" + id + ", accountId=" + accountId + "]";
	}
	
}
