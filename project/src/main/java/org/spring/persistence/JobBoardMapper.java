package org.spring.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.spring.domain.BookmarkDTO;
import org.spring.domain.job.JobBoardDTO;
import org.spring.domain.job.JobCriteria;



@Mapper
public interface JobBoardMapper {

	boolean existsByJoRegistNo(String joRegistNo);
	
    @Insert("INSERT INTO job_board (joRegistNo, cmpnyNm, bsnsSumryCn, mngrInsttNm, dtyCn, jobcodeNm, " +
            "rcritNmprCo, acdmcrNm, careerCndNm, hopeWage, workTimeNm, holidayNm, workPararBassAdresCn, " +
            "joSj, joRegDt, receptClosNm, receptMthNm, presentnPapersNm, mngrNm, mngrPhonNo) " +
            "VALUES (#{joRegistNo}, #{cmpnyNm}, #{bsnsSumryCn}, #{mngrInsttNm}, #{dtyCn}, #{jobcodeNm}, " +
            "#{rcritNmprCo}, #{acdmcrNm}, #{careerCndNm}, #{hopeWage}, #{workTimeNm}, #{holidayNm}, " +
            "#{workPararBassAdresCn}, #{joSj}, #{joRegDt}, #{receptClosNm}, #{receptMthNm}, " +
            "#{presentnPapersNm}, #{mngrNm}, #{mngrPhonNo})")
    public void insertJobBoardData(JobBoardDTO jobBoardDTO);
    
    @Select("SELECT * FROM job_board")
    public List<JobBoardDTO> getAllJobBoardData(JobCriteria cri);

	public int getTotalCount(JobCriteria cri);

	public List<JobBoardDTO> getListWithSearch(JobCriteria cri);

	@Select("SELECT * FROM job_board WHERE joRegistNo = #{jobId}")
	public JobBoardDTO getJobDetail(String jobId);

	// 북마크 추가
    public void bookmark(@Param("joRegistNo") String joRegistNo, @Param("user_num") int user_num, @Param("cmpnyNm") String cmpnyNm, @Param("bsnsSumryCn") String bsnsSumryCn, @Param("receptClosNm") String receptClosNm, @Param("hopeWage") String hopeWage);

    // 북마크 삭제
    public void bookmarkDel(@Param("joRegistNo") String joRegistNo, @Param("user_num") int user_num, @Param("cmpnyNm") String cmpnyNm, @Param("bsnsSumryCn") String bsnsSumryCn, @Param("receptClosNm") String receptClosNm ,@Param("hopeWage") String hopeWage);

    // 북마크 여부 확인
    public int bookmarkChk(@Param("joRegistNo") String joRegistNo, @Param("user_num") int user_num);
    
    List<BookmarkDTO> getUserBookmarks(@Param("user_num") int user_num);
	
}
