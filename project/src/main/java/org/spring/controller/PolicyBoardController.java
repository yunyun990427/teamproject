package org.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.spring.controller.PolicyBoardController;
import org.spring.domain.UserDTO;
import org.spring.domain.policy.*;
import org.spring.model.PolicyResponse;
import org.spring.model.PolicyResponse2;
import org.spring.model.PolicyResponse3;
import org.spring.service.PolicyBoardService;

@Controller
@RequestMapping("/policy/*")
@RequiredArgsConstructor
@Log4j
public class PolicyBoardController {

	@Autowired
	private final PolicyBoardService policyService;
	
	@GetMapping("/list")
	public String listAll(Model model, Criteria cri, HttpSession session) {
		PolicyResponse response = policyService.listAll(cri);
		model.addAttribute("policyapi", response);
		
		int total = response.getMatchCount();
		PageDTO pageResult = new PageDTO(cri, total);
		model.addAttribute("pageMaker", pageResult);
		return "/policy/list";
	}
    
	@ResponseBody
    @PostMapping("/ajaxList")
    public Map<String, Object> ajaxList(@RequestBody Criteria cri) {
        PolicyResponse r = policyService.getList(cri);
        int total = r.getMatchCount();
		PageDTO pageResult = new PageDTO(cri, total);
		System.out.println("검색 결과 수: " + r.getMatchCount());

	    Map<String, Object> result = new HashMap<>();
	    result.put("policyapi", r);
	    result.put("pageMaker", pageResult);
	    
        return result;
    }
	
	@GetMapping("/get")
    public String get(@RequestParam("serviceID") String serviceID, Criteria cri , Model model, HttpSession session) {
        PolicyResponse2 response = policyService.get(serviceID);
        PolicyResponse3 engResponse = convertToEngResponse(response);
        model.addAttribute("cri", cri);
        if (engResponse.getData() != null && !engResponse.getData().isEmpty()) {
            model.addAttribute("policyDetail", engResponse.getData().get(0));
            UserDTO user = (UserDTO) session.getAttribute("user_info");
            if (user != null) {
                int user_num = user.getUser_num();
                boolean bookmarked = policyService.bookmarkChk(serviceID, user_num);
                model.addAttribute("bookmarked", bookmarked);
                model.addAttribute("user_num", user_num);
            } else {
                model.addAttribute("bookmarked", false);
                model.addAttribute("user_num", null);
            }
        }
        return "/policy/get";
    }
	
	@ResponseBody
	@PostMapping("/bookmark")
	public Map<String, Object> policyBookmark(HttpSession session, @RequestBody PolicyBookmarkDTO dto) throws Exception {
		Map<String, Object> result = new HashMap<>();
		UserDTO user = (UserDTO) session.getAttribute("user_info");
		if(user==null) {
			result.put("loggedIn", false);
	        return result;
		}
		int user_num = user.getUser_num();
	    //dto.setUser_num(user_num);
	    dto.setUserNum(user_num);
	    boolean bookmark = policyService.bookmarkChk(dto.getServiceID(), user_num);
	    
	    if (!bookmark) {
	        policyService.bookmark(dto);
	    } else {
	        policyService.bookmarkDel(dto);
	    }
	    result.put("loggedIn", true);
	    result.put("bookmarked", !bookmark);
	    return result;
	}
	
	private PolicyResponse3 convertToEngResponse(PolicyResponse2 response2) {
        PolicyResponse3 response3 = new PolicyResponse3();
        response3.setCurrentCount(response2.getCurrentCount());
        response3.setMatchCount(response2.getMatchCount());
        response3.setPage(response2.getPage());
        response3.setPerPage(response2.getPerPage());
        response3.setTotalCount(response2.getTotalCount());
        
        // 각 ServiceInfo를 변환
        List<PolicyResponse3.ServiceInfo> engData = new ArrayList<>();
        for (PolicyResponse2.ServiceInfo info2 : response2.getData()) {
            PolicyResponse3.ServiceInfo info3 = new PolicyResponse3.ServiceInfo();
            info3.setRequiredDocuments(info2.get구비서류() != null ? info2.get구비서류().trim() : "");
            info3.setContactPoint(info2.get문의처() != null ? info2.get문의처().trim() : "");
            info3.setLegislation(info2.get법령() != null ? info2.get법령().trim() : "");
            info3.setServiceID(info2.get서비스ID() != null ? info2.get서비스ID().trim() : "");
            info3.setServiceName(info2.get서비스명());
            info3.setServicePurpose(info2.get서비스목적() != null ? info2.get서비스목적().trim() : "");
            info3.setSelectionCriteria(info2.get선정기준() != null ? info2.get선정기준().trim() : "");
            info3.setAgencyName(info2.get소관기관명() != null ? info2.get소관기관명().trim() : "");
            info3.setModifiedDateTime(info2.get수정일시() != null ? info2.get수정일시().trim() : "");
            info3.setApplicationDeadline(info2.get신청기한() != null ? info2.get신청기한().trim() : "");
            info3.setApplicationMethod(info2.get신청방법() != null ? info2.get신청방법().trim() : "");
            info3.setOnlineApplicationSiteURL(info2.get온라인신청사이트URL() != null ? info2.get온라인신청사이트URL().trim() : "");
            info3.setLocalOrdinance(info2.get자치법규() != null ? info2.get자치법규().trim() : "");
            info3.setReceivingAgencyName(info2.get접수기관명() != null ? info2.get접수기관명().trim() : "");
            info3.setSupportContent(info2.get지원내용() != null ? info2.get지원내용().trim() : "");
            info3.setSupportTarget(info2.get지원대상() != null ? info2.get지원대상().trim() : "");
            info3.setSupportType(info2.get지원유형() != null ? info2.get지원유형().trim() : "");
            info3.setAdministrativeRule(info2.get행정규칙() != null ? info2.get행정규칙().trim() : "");
            engData.add(info3);
        }
        response3.setData(engData);
        
        return response3;
    }
}