package org.spring.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "GetJobInfo")
public class JobBoardResponse {

	@JacksonXmlProperty(localName = "list_total_count")
	private int listTotalCount;
	@JacksonXmlProperty(localName = "RESULT")
	private Result result;
	@JacksonXmlProperty(localName = "row")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Row> rows;

	@Data
	public static class Result {
		@JacksonXmlProperty(localName = "CODE")
		private String code;

		@JacksonXmlProperty(localName = "MESSAGE")
		private String message;
	}

	@Data
	public static class Row {
		@JacksonXmlProperty(localName = "JO_REQST_NO")
		private String joReqstNo;

		@JacksonXmlProperty(localName = "JO_REGIST_NO")
		private String joRegistNo;

		@JacksonXmlProperty(localName = "CMPNY_NM")
		private String cmpnyNm;

		@JacksonXmlProperty(localName = "BSNS_SUMRY_CN")
		private String bsnsSumryCn;

		@JacksonXmlProperty(localName = "RCRIT_JSSFC_CMMN_CODE_SE")
		private String rcritJssfcCmmnCodeSe;

		@JacksonXmlProperty(localName = "JOBCODE_NM")
		private String jobcodeNm;

		@JacksonXmlProperty(localName = "RCRIT_NMPR_CO")
		private int rcritNmprCo;

		@JacksonXmlProperty(localName = "ACDMCR_CMMN_CODE_SE")
		private String acdmcrCmmnCodeSe;

		@JacksonXmlProperty(localName = "ACDMCR_NM")
		private String acdmcrNm;

		@JacksonXmlProperty(localName = "EMPLYM_STLE_CMMN_CODE_SE")
		private String emplymStleCmmnCodeSe;

		@JacksonXmlProperty(localName = "EMPLYM_STLE_CMMN_MM")
		private String emplymStleCmmnMm;

		@JacksonXmlProperty(localName = "WORK_PARAR_BASS_ADRES_CN")
		private String workPararBassAdresCn;

		@JacksonXmlProperty(localName = "SUBWAY_NM")
		private String subwayNm;

		@JacksonXmlProperty(localName = "DTY_CN")
		private String dtyCn;

		@JacksonXmlProperty(localName = "CAREER_CND_CMMN_CODE_SE")
		private String careerCndCmmnCodeSe;

		@JacksonXmlProperty(localName = "CAREER_CND_NM")
		private String careerCndNm;

		@JacksonXmlProperty(localName = "HOPE_WAGE")
		private String hopeWage;

		@JacksonXmlProperty(localName = "RET_GRANTS_NM")
		private String retGrantsNm;

		@JacksonXmlProperty(localName = "WORK_TIME_NM")
		private String workTimeNm;

		@JacksonXmlProperty(localName = "WORK_TM_NM")
		private String workTmNm;

		@JacksonXmlProperty(localName = "HOLIDAY_NM")
		private String holidayNm;

		@JacksonXmlProperty(localName = "WEEK_WORK_HR")
		private int weekWorkHr;

		@JacksonXmlProperty(localName = "JO_FEINSR_SBSCRB_NM")
		private String joFeinsrSbscrbNm;

		@JacksonXmlProperty(localName = "RCEPT_CLOS_NM")
		private String receptClosNm;

		@JacksonXmlProperty(localName = "RCEPT_MTH_IEM_NM")
		private String receptMthIemNm;

		@JacksonXmlProperty(localName = "MODEL_MTH_NM")
		private String modelMthNm;

		@JacksonXmlProperty(localName = "RCEPT_MTH_NM")
		private String receptMthNm;

		@JacksonXmlProperty(localName = "PRESENTN_PAPERS_NM")
		private String presentnPapersNm;

		@JacksonXmlProperty(localName = "MNGR_NM")
		private String mngrNm;

		@JacksonXmlProperty(localName = "MNGR_PHON_NO")
		private String mngrPhonNo;

		@JacksonXmlProperty(localName = "MNGR_INSTT_NM")
		private String mngrInsttNm;

		@JacksonXmlProperty(localName = "BASS_ADRES_CN")
		private String bassAdresCn;

		@JacksonXmlProperty(localName = "JO_SJ")
		private String joSj;

		@JacksonXmlProperty(localName = "JO_REG_DT")
		private String joRegDt;

		@JacksonXmlProperty(localName = "GUI_LN")
		private String guiLn;


	}
}