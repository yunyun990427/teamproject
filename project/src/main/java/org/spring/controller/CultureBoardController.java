package org.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.spring.domain.BookmarkDTO;
import org.spring.domain.UserDTO;
import org.spring.domain.culture.Criteria;
import org.spring.domain.culture.CultureBoardDTO;
import org.spring.domain.culture.PageDTO;
import org.spring.service.CultureBoardService;
import org.spring.service.CultureBoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/culture/*")
public class CultureBoardController {
	
	@Autowired
    private CultureBoardService cultureboardService;
	
	@Autowired
	private CultureBoardServiceImpl cultureboardServiceImpl;

	@GetMapping("/list")
	public void listAll(@RequestParam(value="pageNum", defaultValue="1") int pageNum,
	                    @RequestParam(value="amount", defaultValue="10") int amount,
	                    @RequestParam(value="area", defaultValue="") String culture_area,
	                    @RequestParam(value="classify", defaultValue="") String culture_classify,
	                    @RequestParam(value="type", defaultValue="") String type,
	                    @RequestParam(value="keyword", defaultValue="") String keyword,
	                    Model model) {
	    Criteria cri = new Criteria(pageNum, amount);
	    cri.setType(type);
	    cri.setKeyword(keyword);

	    int total = cultureboardService.getTotalCount(cri);
	    List<CultureBoardDTO> list = cultureboardService.listAll(cri, culture_area, culture_classify);
	    model.addAttribute("list", list);
	    model.addAttribute("pageMaker", new PageDTO(cri, total));
	    model.addAttribute("selectedArea", culture_area);
	    model.addAttribute("selectedClassify", culture_classify);
	    model.addAttribute("cri", cri);
	}
    
	@GetMapping("/get/{culture_bno}")
	public String get(@PathVariable("culture_bno") int culture_bno, Criteria cri, Model model, HttpSession session) {
	    CultureBoardDTO dto = cultureboardService.getBoard(culture_bno);
	    model.addAttribute("dto", dto);
	    model.addAttribute("cri", cri);

	    UserDTO user = (UserDTO) session.getAttribute("user_info");
	    if (user != null) {
	        int user_num = user.getUser_num();
	        boolean bookmarked = cultureboardService.bookmarkChk(culture_bno, user_num);
	        model.addAttribute("bookmarked", bookmarked);
	        model.addAttribute("user_num", user_num);
	    } else {
	        model.addAttribute("bookmarked", false);
	        model.addAttribute("user_num", null);
	    }
	    return "culture/get";
	}
    
    @PostMapping("/ajaxList")
    @ResponseBody
    public Map<String, Object> ajaxList(@RequestParam(value="pageNum") int pageNum,
                                        @RequestParam(value="amount") int amount,
                                        @RequestParam(value="area") String culture_area,
                                        @RequestParam(value="classify") String culture_classify,
                                        Criteria cri) {
    	System.out.println("ajax >> " + "pageNum: " + pageNum + ", amount: " + amount);
        cri.setPageNum(pageNum);
    	cri.setAmount(amount);
    	cri.setArea(culture_area);
        cri.setClassify(culture_classify);
        
        int total = cultureboardService.getTotalCount(cri);
        System.out.println("total: "+total);
        List<CultureBoardDTO> list = cultureboardService.listPage(cri, culture_area, culture_classify);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        PageDTO pageDTO = new PageDTO(cri, total);
        result.put("pageMaker", pageDTO);
        return result;
    }
    
    @ResponseBody
    @PostMapping("/bookmark")
    public Map<String, Object> cultureBookmark(HttpSession session, @RequestParam("culture_bno") int culture_bno,
                                                               @RequestParam("culture_classify") String culture_classify,
                                                               @RequestParam("culture_title") String culture_title,
                                                               @RequestParam("culture_place") String culture_place) throws Exception {
        Map<String, Object> result = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user_info");
        if(user == null) {
            result.put("loggedIn", false);
            return result;
        }
        int user_num = user.getUser_num();
        boolean bookmark = cultureboardService.bookmarkChk(culture_bno, user_num);
        if (!bookmark) {
        	cultureboardService.bookmark(culture_bno, user_num, culture_classify, culture_title, culture_place);
        } else {
        	cultureboardService.bookmarkDel(culture_bno, user_num, culture_classify, culture_title);
        }
        result.put("loggedIn", true);
        result.put("bookmarked", !bookmark);
        return result;
    }
    
    @GetMapping("/bookmarks")public String showCultureBookmarks(Model model, HttpSession session) {
        // Log entry into the method
        System.out.println("showCultureBookmarks");

        UserDTO user = (UserDTO) session.getAttribute("user_info");
        System.out.println("User session info: " + user);

        if (user != null) {
            int user_num = user.getUser_num();
           

            List<BookmarkDTO> bookmarks = cultureboardService.getBookmarkedPosts(user_num);
            System.out.println("bookmarks: " + bookmarks);

            model.addAttribute("cultureBookmarks", bookmarks);
        } else {
            model.addAttribute("cultureBookmarks", new ArrayList<>());
        }

        System.out.println("Model after setting attributes: " + model);
        return "culture/bookmarks";  
    }
}