package org.spring.service;

import org.spring.persistence.JobBoardMapper;
import org.spring.domain.BookmarkDTO;
import org.spring.domain.job.JobBoardDTO;
import org.spring.domain.job.JobCriteria;
import org.spring.model.JobBoardResponse;
import org.spring.utils.RestApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class JobBoardService {

    private static final String SECRETKEY = "7858665a4b776c6733345a79574947";
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/{KEY}/{TYPE}/{SERVICE}/{START_INDEX}/{END_INDEX}/";
    private static final int PAGE_SIZE = 10; // 페이지 당 공고 수

    @Autowired
    private JobBoardMapper jobBoardMapper;

    public JobBoardResponse getJobsForPage(int startIndex, int endIndex) {
        String url = BASE_URL
                .replace("{KEY}", SECRETKEY)
                .replace("{TYPE}", "xml")
                .replace("{SERVICE}", "GetJobInfo")
                .replace("{START_INDEX}", String.valueOf(startIndex))
                .replace("{END_INDEX}", String.valueOf(endIndex));

        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> headerData = new HashMap<>();
        headerData.put("Content-type", "application/xml");

        return RestApiUtil.ConnHttpGetType(url, data, headerData, JobBoardResponse.class, true);
    }

    @Transactional
    public void fetchAndSaveAllJobBoardData() {
        int startIndex = 1;
        int endIndex = PAGE_SIZE;
        JobBoardResponse initialResponse = getJobsForPage(startIndex, endIndex);

        if (initialResponse != null && initialResponse.getListTotalCount() > 0) {
            int totalCount = initialResponse.getListTotalCount();
            while (startIndex <= totalCount) {
                JobBoardResponse jobBoardResponse = getJobsForPage(startIndex, endIndex);

                if (jobBoardResponse != null && jobBoardResponse.getRows() != null) {
                    jobBoardResponse.getRows().forEach(row -> {
                    	
                    	 // 중복 체크
                        if (!jobBoardMapper.existsByJoRegistNo(row.getJoRegistNo())) {
                            JobBoardDTO jobBoardDTO = new JobBoardDTO();
                            jobBoardDTO.setJoRegistNo(row.getJoRegistNo());
                            jobBoardDTO.setCmpnyNm(row.getCmpnyNm());
                            jobBoardDTO.setBsnsSumryCn(row.getBsnsSumryCn());
                            jobBoardDTO.setMngrInsttNm(row.getMngrInsttNm());
                            jobBoardDTO.setDtyCn(row.getDtyCn());
                            jobBoardDTO.setJobcodeNm(row.getJobcodeNm());
                            jobBoardDTO.setRcritNmprCo(row.getRcritNmprCo());
                            jobBoardDTO.setAcdmcrNm(row.getAcdmcrNm());
                            jobBoardDTO.setCareerCndNm(row.getCareerCndNm());
                            jobBoardDTO.setHopeWage(row.getHopeWage());
                            jobBoardDTO.setWorkTimeNm(row.getWorkTimeNm());
                            jobBoardDTO.setHolidayNm(row.getHolidayNm());
                            jobBoardDTO.setWorkPararBassAdresCn(row.getWorkPararBassAdresCn());
                            jobBoardDTO.setJoSj(row.getJoSj());
                            jobBoardDTO.setJoRegDt(Date.valueOf(row.getJoRegDt()));
                            jobBoardDTO.setReceptClosNm(row.getReceptClosNm());
                            jobBoardDTO.setReceptMthNm(row.getReceptMthNm());
                            jobBoardDTO.setPresentnPapersNm(row.getPresentnPapersNm());
                            jobBoardDTO.setMngrNm(row.getMngrNm());
                            jobBoardDTO.setMngrPhonNo(row.getMngrPhonNo());

                            jobBoardMapper.insertJobBoardData(jobBoardDTO);
                        }
                    });
                }

                startIndex += PAGE_SIZE;
                endIndex = Math.min(startIndex + PAGE_SIZE - 1, totalCount);
            }
        }
    }

    public List<JobBoardDTO> getJobBoardData(JobCriteria cri) {
        return jobBoardMapper.getAllJobBoardData(cri);
    }

	public int getTotal(JobCriteria cri) {
		return jobBoardMapper.getTotalCount(cri);
	}

	public List<JobBoardDTO> getList(JobCriteria cri) {	
		System.out.println("getList에 들어왔니? cri :" + cri);
		List<JobBoardDTO> result = jobBoardMapper.getListWithSearch(cri);		
		System.out.println("getList에 들어왔니? result :" + result);
		return result;
	}

	public JobBoardDTO getJobDetail(String jobId) {
        return jobBoardMapper.getJobDetail(jobId);
    }

	public boolean bookmarkChk(String joRegistNo, int user_num) {
		return jobBoardMapper.bookmarkChk(joRegistNo, user_num) > 0;
	}

	public void bookmarkDel(String joRegistNo, int user_num, String cmpnyNm, String bsnsSumryCn, String receptClosNm,String hopeWage) {
		jobBoardMapper.bookmarkDel(joRegistNo, user_num, cmpnyNm, bsnsSumryCn, receptClosNm, hopeWage);
	}

	public void bookmark(String joRegistNo, int user_num, String cmpnyNm, String bsnsSumryCn, String receptClosNm, String hopeWage) {
		jobBoardMapper.bookmark(joRegistNo, user_num, cmpnyNm, bsnsSumryCn, receptClosNm, hopeWage);		
	}

	public List<BookmarkDTO> getUserBookmarks(int user_num) {
		
		 return jobBoardMapper.getUserBookmarks(user_num);
	}

	

	
	
	
}
