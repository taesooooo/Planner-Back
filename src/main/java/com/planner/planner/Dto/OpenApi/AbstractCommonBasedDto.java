package com.planner.planner.Dto.OpenApi;

public abstract class AbstractCommonBasedDto {
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
	private String contentId; // 콘텐츠ID
	private String contentTypeId; // 콘텐츠타입ID
	private String createdTime; // 등록일
	private String firstImage; // 대표이미지(원본)
	private String firstImage2; // 대표이미지(썸네일)
	private String mapx; // GPS X좌표
	private String mapy; // GPS Y좌표
	private String mlevel; // Map Level
	private String modifiedTime; // 수정일
	private String zipcode; // 우편번호
	
	public abstract static class Builder<T extends Builder<T>> {
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
		private String contentId; // 콘텐츠ID
		private String contentTypeId; // 콘텐츠타입ID
		private String createdTime; // 등록일
		private String firstImage; // 대표이미지(원본)
		private String firstImage2; // 대표이미지(썸네일)
		private String mapx; // GPS X좌표
		private String mapy; // GPS Y좌표
		private String mlevel; // Map Level
		private String modifiedTime; // 수정일
		private String zipcode; // 우편번호

		public T setSigunguCode(String sigunguCode) {
			this.sigunguCode = sigunguCode;
			return self();
		}
		public T setTel(String tel) {
			this.tel = tel;
			return self();
		}
		public T setTitle(String title) {
			this.title = title;
			return self();
		}
		public T setAddr1(String addr1) {
			this.addr1 = addr1;
			return self();
		}
		public T setAddr2(String addr2) {
			this.addr2 = addr2;
			return self();
		}
		public T setAreaCode(String areaCode) {
			this.areaCode = areaCode;
			return self();
		}
		public T setBookTour(String bookTour) {
			this.bookTour = bookTour;
			return self();
		}
		public T setCat1(String cat1) {
			this.cat1 = cat1;
			return self();
		}
		public T setCat2(String cat2) {
			this.cat2 = cat2;
			return self();
		}
		public T setCat3(String cat3) {
			this.cat3 = cat3;
			return self();
		}
		public T setContentId(String contentId) {
			this.contentId = contentId;
			return self();
		}
		public T setContentTypeId(String contentTypeId) {
			this.contentTypeId = contentTypeId;
			return self();
		}
		public T setCreatedTime(String createdTime) {
			this.createdTime = createdTime;
			return self();
		}
		public T setFirstImage(String firstImage) {
			this.firstImage = firstImage;
			return self();
		}
		public T setFirstImage2(String firstImage2) {
			this.firstImage2 = firstImage2;
			return self();
		}
		public T setMapx(String mapx) {
			this.mapx = mapx;
			return self();
		}
		public T setMapy(String mapy) {
			this.mapy = mapy;
			return self();
		}
		public T setMlevel(String mlevel) {
			this.mlevel = mlevel;
			return self();
		}
		public T setModifiedTime(String modifiedTime) {
			this.modifiedTime = modifiedTime;
			return self();
		}

		public T setZipcode(String zipcode) {
			this.zipcode = zipcode;
			return self();
		}
		
		public abstract T self();
		
		public abstract AbstractCommonBasedDto build();
	}
	
	public AbstractCommonBasedDto(Builder<?> builder) {
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
		this.contentId = builder.contentId;
		this.contentTypeId = builder.contentTypeId;
		this.createdTime = builder.createdTime;
		this.firstImage = builder.firstImage;
		this.firstImage2 = builder.firstImage2;
		this.mapx = builder.mapx;
		this.mapy = builder.mapy;
		this.mlevel = builder.mlevel;
		this.modifiedTime = builder.modifiedTime;
		this.zipcode = builder.zipcode;
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

	public String getContentId() {
		return contentId;
	}

	public String getContentTypeId() {
		return contentTypeId;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public String getFirstImage() {
		return firstImage;
	}

	public String getFirstImage2() {
		return firstImage2;
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

	public String getModifiedTime() {
		return modifiedTime;
	}

	public String getZipcode() {
		return zipcode;
	}
	
}