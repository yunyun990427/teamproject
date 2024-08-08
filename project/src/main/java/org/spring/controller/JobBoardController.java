package org.spring.controller;

import org.spring.domain.BookmarkDTO;
import org.spring.domain.UserDTO;
import org.spring.domain.job.JobBoardDTO;
import org.spring.domain.job.JobCriteria;
import org.spring.domain.job.JobPageDTO;
import org.spring.service.JobBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/job/*")
public class JobBoardController {

    @Autowired
    private JobBoardService jobBoardService;

    @GetMapping("/fetchAndSaveAll") // 데이터 업데이트
    public String fetchAndSaveAllJobBoardData() {
        jobBoardService.fetchAndSaveAllJobBoardData();
        return "/job/upload_success"; 
    }

    @GetMapping("/list")
    public String getJobBoardData(JobCriteria cri, Model model) {
        System.out.println("cri 값 : " + cri);
        int total = jobBoardService.getTotal(cri);

        JobPageDTO pageResult = new JobPageDTO(cri, total);
        model.addAttribute("pageMaker", pageResult);

        System.out.println("total : " + total);
        System.out.println("pageResult : " + pageResult);

        List<JobBoardDTO> jobBoardData = jobBoardService.getJobBoardData(cri);
        model.addAttribute("jobBoardData", jobBoardData);
        model.addAttribute("cri", cri);
        return "/job/list";
    }
    
    @ResponseBody
    @PostMapping("/getList")
    public Map<String, Object> getList(JobCriteria cri, Model model) {
        System.out.println("Ajax로 전체 게시물 조회>>>");
        int total = jobBoardService.getTotal(cri);

        JobPageDTO pageResult = new JobPageDTO(cri, total);
        model.addAttribute("pageMaker", pageResult);
        
        List<JobBoardDTO> jobBoardData = jobBoardService.getList(cri);
        
        Map<String, Object> response = new HashMap<>();
        response.put("list", jobBoardData);
        response.put("pageMaker", pageResult);

        return response;
    }

    @GetMapping("/detail")
    public String getJobDetail(@RequestParam("jobId") String jobId, JobCriteria cri, Model model, HttpSession session) {
        JobBoardDTO jobDetail = jobBoardService.getJobDetail(jobId);
        model.addAttribute("jobDetail", jobDetail);
        model.addAttribute("cri", cri);
        
        UserDTO user = (UserDTO) session.getAttribute("user_info");
        if (user != null) {
            int user_num = user.getUser_num();
            boolean bookmarked = jobBoardService.bookmarkChk(jobId, user_num);
            model.addAttribute("bookmarked", bookmarked);
            model.addAttribute("user_num", user_num);
        } else {
            model.addAttribute("bookmarked", false);
            model.addAttribute("user_num", null);
        }
        return "/job/detail";
    }
    
    @ResponseBody
	@PostMapping("/bookmark")
	public Map<String, Object> JobyBookmark(HttpSession session, @RequestParam("joRegistNo") String joRegistNo,
																   @RequestParam("cmpnyNm") String cmpnyNm,
																   @RequestParam("bsnsSumryCn") String bsnsSumryCn,
																   @RequestParam("receptClosNm") String receptClosNm,
																   @RequestParam("hopeWage") String hopeWage) throws Exception {
		Map<String, Object> result = new HashMap<>();
		UserDTO user = (UserDTO) session.getAttribute("user_info");
		if(user==null) {
			result.put("loggedIn", false);
	        return result;
		}
		int user_num = user.getUser_num();
		boolean bookmark = jobBoardService.bookmarkChk(joRegistNo, user_num);
		if (!bookmark) {
	    	jobBoardService.bookmark(joRegistNo, user_num, cmpnyNm, bsnsSumryCn, receptClosNm, hopeWage);
	    } else {
	    	jobBoardService.bookmarkDel(joRegistNo, user_num, cmpnyNm, bsnsSumryCn, receptClosNm, hopeWage);
	    }
	    result.put("loggedIn", true);
	    result.put("bookmarked", !bookmark);
	    return result;
	}
    
    @GetMapping("/bookmarks")
    public String getUserBookmarks(HttpSession session, Model model) {    
        UserDTO user = (UserDTO) session.getAttribute("user_info");
        if (user != null) {
            int user_num = user.getUser_num();
            List<BookmarkDTO> jobBookmarks = jobBoardService.getUserBookmarks(user_num);
            model.addAttribute("jobBookmarks", jobBookmarks);
        } else {
            model.addAttribute("jobBookmarks", new ArrayList<>());
        }
        return "/job/bookmark";
    }
    
}
