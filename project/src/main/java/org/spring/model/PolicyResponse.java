package org.spring.model;

import java.util.List;

public class PolicyResponse {

	private int currentCount;
	private List<ServiceInfo> data;
	private int matchCount;
	private int page;
	private int perPage;
	private int totalCount;

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public List<ServiceInfo> getData() {
		return data;
	}

	public void setData(List<ServiceInfo> data) {
		this.data = data;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public static class ServiceInfo {
		private String 부서명;
		private String 사용자구분;
		private String 상세조회URL;
		private String 서비스ID;
		private String 서비스명;
		private String 서비스목적요약;
		private String 서비스분야;
		private String 선정기준;
		private String 소관기관명;
		private String 소관기관유형;
		private String 소관기관코드;
		private String 신청기한;
		private String 신청방법;
		private String 전화문의;
		private String 접수기관;
		private int 조회수;
		private String 지원내용;
		private String 지원대상;
		private String 지원유형;

		public String get부서명() {
			return 부서명;
		}

		public void set부서명(String 부서명) {
			this.부서명 = 부서명;
		}

		public String get사용자구분() {
			return 사용자구분;
		}

		public void set사용자구분(String 사용자구분) {
			this.사용자구분 = 사용자구분;
		}

		public String get상세조회URL() {
			return 상세조회URL;
		}

		public void set상세조회URL(String 상세조회url) {
			상세조회URL = 상세조회url;
		}

		public String get서비스ID() {
			return 서비스ID;
		}

		public void set서비스ID(String 서비스id) {
			서비스ID = 서비스id;
		}

		public String get서비스명() {
			return 서비스명;
		}

		public void set서비스명(String 서비스명) {
			this.서비스명 = 서비스명;
		}

		public String get서비스목적요약() {
			return 서비스목적요약;
		}

		public void set서비스목적요약(String 서비스목적요약) {
			this.서비스목적요약 = 서비스목적요약;
		}

		public String get서비스분야() {
			return 서비스분야;
		}

		public void set서비스분야(String 서비스분야) {
			this.서비스분야 = 서비스분야;
		}

		public String get선정기준() {
			return 선정기준;
		}

		public void set선정기준(String 선정기준) {
			this.선정기준 = 선정기준;
		}

		public String get소관기관명() {
			return 소관기관명;
		}

		public void set소관기관명(String 소관기관명) {
			this.소관기관명 = 소관기관명;
		}

		public String get소관기관유형() {
			return 소관기관유형;
		}

		public void set소관기관유형(String 소관기관유형) {
			this.소관기관유형 = 소관기관유형;
		}

		public String get소관기관코드() {
			return 소관기관코드;
		}

		public void set소관기관코드(String 소관기관코드) {
			this.소관기관코드 = 소관기관코드;
		}

		public String get신청기한() {
			return 신청기한;
		}

		public void set신청기한(String 신청기한) {
			this.신청기한 = 신청기한;
		}

		public String get신청방법() {
			return 신청방법;
		}

		public void set신청방법(String 신청방법) {
			this.신청방법 = 신청방법;
		}

		public String get전화문의() {
			return 전화문의;
		}

		public void set전화문의(String 전화문의) {
			this.전화문의 = 전화문의;
		}

		public String get접수기관() {
			return 접수기관;
		}

		public void set접수기관(String 접수기관) {
			this.접수기관 = 접수기관;
		}

		public int get조회수() {
			return 조회수;
		}

		public void set조회수(int 조회수) {
			this.조회수 = 조회수;
		}

		public String get지원내용() {
			return 지원내용;
		}

		public void set지원내용(String 지원내용) {
			this.지원내용 = 지원내용;
		}

		public String get지원대상() {
			return 지원대상;
		}

		public void set지원대상(String 지원대상) {
			this.지원대상 = 지원대상;
		}

		public String get지원유형() {
			return 지원유형;
		}

		public void set지원유형(String 지원유형) {
			this.지원유형 = 지원유형;
		}

		@Override
		public String toString() {
			return "ServiceInfo [부서명=" + 부서명 + ", 사용자구분=" + 사용자구분 + ", 상세조회URL=" + 상세조회URL + ", 서비스ID=" + 서비스ID
					+ ", 서비스명=" + 서비스명 + ", 서비스목적요약=" + 서비스목적요약 + ", 서비스분야=" + 서비스분야 + ", 선정기준=" + 선정기준 + ", 소관기관명="
					+ 소관기관명 + ", 소관기관유형=" + 소관기관유형 + ", 소관기관코드=" + 소관기관코드 + ", 신청기한=" + 신청기한 + ", 신청방법=" + 신청방법
					+ ", 전화문의=" + 전화문의 + ", 접수기관=" + 접수기관 + ", 조회수=" + 조회수 + ", 지원내용=" + 지원내용 + ", 지원대상=" + 지원대상
					+ ", 지원유형=" + 지원유형 + "]";
		}

	}
	@Override
	public String toString() {
		return "PolicyResponse [currentCount=" + currentCount + ", data=" + data + ", matchCount=" + matchCount
				+ ", page=" + page + ", perPage=" + perPage + ", totalCount=" + totalCount + "]";
	}
}
