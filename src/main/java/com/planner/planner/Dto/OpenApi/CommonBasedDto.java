package com.planner.planner.Dto.OpenApi;

public class CommonBasedDto {
	private String readCount; // 조회수
	private String sigunguCode; // 시군구코드
	private String tel; // 전화번호
	private String title; // 제목
	private String addr1; // 주소
	private String addr2; // 상세주소
	private String areaCode; // 지역코드
	private String bookTour; // 교과서속여행지 여부
	private String cat1; // 대분류
	private String cat2; // 중분류
	private String cat3; // 소분류
	private String contentid; // 콘텐츠ID
	private String contenttypeid; // 콘텐츠타입ID
	private String createdtime; // 등록일
	private String firstimage; // 대표이미지(원본)
	private String firstimage2; // 대표이미지(썸네일)
	private String mapx; // GPS X좌표
	private String mapy; // GPS Y좌표
	private String mlevel; // Map Level
	private String modifiedtime; // 수정일
	private String zipcode; // 우편번호
	
	public static class Builder {
		private String readCount; // 조회수
		private String sigunguCode; // 시군구코드
		private String tel; // 전화번호
		private String title; // 제목
		private String addr1; // 주소
		private String addr2; // 상세주소
		private String areaCode; // 지역코드
		private String bookTour; // 교과서속여행지 여부
		private String cat1; // 대분류
		private String cat2; // 중분류
		private String cat3; // 소분류
		private String contentid; // 콘텐츠ID
		private String contenttypeid; // 콘텐츠타입ID
		private String createdtime; // 등록일
		private String firstimage; // 대표이미지(원본)
		private String firstimage2; // 대표이미지(썸네일)
		private String mapx; // GPS X좌표
		private String mapy; // GPS Y좌표
		private String mlevel; // Map Level
		private String modifiedtime; // 수정일
		private String zipcode; // 우편번호
		
		public Builder setReadCount(String readCount) {
			this.readCount = readCount;
			return this;
		}
		public Builder setSigunguCode(String sigunguCode) {
			this.sigunguCode = sigunguCode;
			return this;
		}
		public Builder setTel(String tel) {
			this.tel = tel;
			return this;
		}
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		public Builder setAddr1(String addr1) {
			this.addr1 = addr1;
			return this;
		}
		public Builder setAddr2(String addr2) {
			this.addr2 = addr2;
			return this;
		}
		public Builder setAreaCode(String areaCode) {
			this.areaCode = areaCode;
			return this;
		}
		public Builder setBookTour(String bookTour) {
			this.bookTour = bookTour;
			return this;
		}
		public Builder setCat1(String cat1) {
			this.cat1 = cat1;
			return this;
		}
		public Builder setCat2(String cat2) {
			this.cat2 = cat2;
			return this;
		}
		public Builder setCat3(String cat3) {
			this.cat3 = cat3;
			return this;
		}
		public Builder setContentid(String contentid) {
			this.contentid = contentid;
			return this;
		}
		public Builder setContenttypeid(String contenttypeid) {
			this.contenttypeid = contenttypeid;
			return this;
		}
		public Builder setCreatedtime(String createdtime) {
			this.createdtime = createdtime;
			return this;
		}
		public Builder setFirstimage(String firstimage) {
			this.firstimage = firstimage;
			return this;
		}
		public Builder setFirstimage2(String firstimage2) {
			this.firstimage2 = firstimage2;
			return this;
		}
		public Builder setMapx(String mapx) {
			this.mapx = mapx;
			return this;
		}
		public Builder setMapy(String mapy) {
			this.mapy = mapy;
			return this;
		}
		public Builder setMlevel(String mlevel) {
			this.mlevel = mlevel;
			return this;
		}
		public Builder setModifiedtime(String modifiedtime) {
			this.modifiedtime = modifiedtime;
			return this;
		}

		public Builder setZipcode(String zipcode) {
			this.zipcode = zipcode;
			return this;
		}
	
		public CommonBasedDto build() {
			return new CommonBasedDto(this);
		}
	}
	
	public CommonBasedDto(Builder builder) {
		this.readCount = builder.readCount;
		this.sigunguCode = builder.sigunguCode;
		this.tel = builder.tel;
		this.title = builder.title;
		this.addr1 = builder.addr1;
		this.addr2 = builder.addr2;
		this.areaCode = builder.areaCode;
		this.bookTour = builder.bookTour;
		this.cat1 = builder.cat1;
		this.cat2 = builder.cat2;
		this.cat3 = builder.cat3;
		this.contentid = builder.contentid;
		this.contenttypeid = builder.contenttypeid;
		this.createdtime = builder.createdtime;
		this.firstimage = builder.firstimage;
		this.firstimage2 = builder.firstimage2;
		this.mapx = builder.mapx;
		this.mapy = builder.mapy;
		this.mlevel = builder.mlevel;
		this.modifiedtime = builder.modifiedtime;
		this.zipcode = builder.zipcode;
	}

	public String getReadCount() {
		return readCount;
	}

	public String getSigunguCode() {
		return sigunguCode;
	}

	public String getTel() {
		return tel;
	}

	public String getTitle() {
		return title;
	}

	public String getAddr1() {
		return addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getBookTour() {
		return bookTour;
	}

	public String getCat1() {
		return cat1;
	}

	public String getCat2() {
		return cat2;
	}

	public String getCat3() {
		return cat3;
	}

	public String getContentid() {
		return contentid;
	}

	public String getContenttypeid() {
		return contenttypeid;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public String getFirstimage() {
		return firstimage;
	}

	public String getFirstimage2() {
		return firstimage2;
	}

	public String getMapx() {
		return mapx;
	}

	public String getMapy() {
		return mapy;
	}

	public String getMlevel() {
		return mlevel;
	}

	public String getModifiedtime() {
		return modifiedtime;
	}

	public String getZipcode() {
		return zipcode;
	}
	
}
