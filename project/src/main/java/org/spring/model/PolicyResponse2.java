package org.spring.model;

import java.util.List;

import lombok.Data;

@Datapublic class PolicyResponse2 {
    private int currentCount;
    private List<ServiceInfo> data;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;

    @Data
    public static class ServiceInfo {
        private String 구비서류;
        private String 문의처;
        private String 법령;
        private String 서비스ID;
        private String 서비스명;
        private String 서비스목적;
        private String 선정기준;
        private String 소관기관명;
        private String 수정일시;
        private String 신청기한;
        private String 신청방법;
        private String 온라인신청사이트URL;
        private String 자치법규;
        private String 접수기관명;
        private String 지원내용;
        private String 지원대상;
        private String 지원유형;
        private String 행정규칙;
    }
}
