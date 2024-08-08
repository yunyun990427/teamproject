package org.spring.domain.policy;

import lombok.Data;

@Data
public class PolicyBookmarkDTO {

	private String serviceID; 
    private int userNum;
    private String serviceName;
    private String serviceContent;
    private String serviceDeadline; 
}
