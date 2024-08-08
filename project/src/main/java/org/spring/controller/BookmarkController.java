package org.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.spring.domain.BookmarkDTO;
import org.spring.domain.UserDTO;
import org.spring.domain.policy.PolicyBookmarkDTO;
import org.spring.service.CultureBoardService;
import org.spring.service.JobBoardService;
import org.spring.service.PolicyBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/user/*")
public class BookmarkController {

    @Autowired
    private JobBoardService jobBoardService;

    @Autowired
    private PolicyBoardService policyBoardService;
    @Autowired
    private CultureBoardService cultureBoardService;

    @ResponseBody
    @PostMapping("/bookmark")
    public Map<String, Object> bookmark(HttpSession session,
                                        @RequestParam("type") String type,
                                        @RequestParam("id") String id,
                                        @RequestParam("name") String name,
                                        @RequestParam("summary") String summary) {
        Map<String, Object> result = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user_info");
        if (user == null) {
            result.put("loggedIn", false);
            return result;
        }
        int user_num = user.getUser_num();
        boolean bookmark =false;
//        if ("job".equals(type)) {
//            bookmark = jobBoardService.bookmarkChk(id, user_num);
//            if (!bookmark) {
//                jobBoardService.bookmark(id, user_num, name, summary);
//            } else {
//                jobBoardService.bookmarkDel(id, user_num, summary, summary);
//            }
//        } 
//        else if ("policy".equals(type)) {
//           bookmark = policyBoardService.bookmarkChk(id, user_num);
//            if (!bookmark) {
//                policyBoardService.bookmark(id, user_num, name, summary);
//            } else {
//                policyBoardService.bookmarkDel(id, user_num);
//            }
//        } 
//        else {
//            throw new IllegalArgumentException("Invalid bookmark type");
//        }
        result.put("loggedIn", true);
        result.put("bookmarked", !bookmark);
        return result;
    }

    @GetMapping
    public String getUserBookmarks(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user_info");
        if (user == null) {
            return "redirect:/login"; // or wherever your login page is
        }
        int user_num = user.getUser_num();
        List<BookmarkDTO> jobBookmarks = jobBoardService.getUserBookmarks(user_num);
        List<BookmarkDTO> policyBookmarks = policyBoardService.getUserBookmarks(user_num);
        List<BookmarkDTO> cultureBookmarks = cultureBoardService.getUserBookmarks(user_num);
        model.addAttribute("jobBookmarks", jobBookmarks);
        model.addAttribute("policyBookmarks", policyBookmarks);
        model.addAttribute("cultureBookmarks", cultureBookmarks);
        return "bookmarks"; // JSP page name
    }
}
