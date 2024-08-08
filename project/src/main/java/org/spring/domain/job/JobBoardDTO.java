package org.spring.domain.job;

import java.sql.Date;

import lombok.Data;

@Data
public class JobBoardDTO {
    private String joRegistNo;        // 구인 등록 번호
    private String cmpnyNm;           // 회사명
    private String bsnsSumryCn;       // 사업 요약
    private String mngrInsttNm;       // 자치구 (담당 기관 이름)
    private String dtyCn;             // 직무 내용
    private String jobcodeNm;         // 직무 코드
    private int rcritNmprCo;          // 모집 인원
    private String acdmcrNm;          // 학력 조건
    private String careerCndNm;       // 경력 조건
    private String hopeWage;          // 희망 급여
    private String workTimeNm;        // 근무 시간
    private String holidayNm;         // 휴일
    private String workPararBassAdresCn; // 근무지 주소
    private String joSj;              // 구인 제목
    private Date joRegDt;             // 등록일
    private String receptClosNm;      // 마감일
    private String receptMthNm;       // 전형 방법
    private String presentnPapersNm;  // 제출 서류
    private String mngrNm;            // 담당자 이름
    private String mngrPhonNo;        // 담당자 전화번호
}
