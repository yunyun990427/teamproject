package org.spring.model;

import java.util.List;

import lombok.Data;

@Data
public class PolicyResponse3 {
    private int currentCount;
    private List<ServiceInfo> data;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;

    @Data
    public static class ServiceInfo {
        private String requiredDocuments;
        private String contactPoint;
        private String legislation;
        private String serviceID;
        private String serviceName;
        private String servicePurpose;
        private String selectionCriteria;
        private String agencyName;
        private String modifiedDateTime;
        private String applicationDeadline;
        private String applicationMethod;
        private String onlineApplicationSiteURL;
        private String localOrdinance;
        private String receivingAgencyName;
        private String supportContent;
        private String supportTarget;
        private String supportType;
        private String administrativeRule;
    }
}