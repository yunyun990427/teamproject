package org.spring.controller;

import java.util.List;

import org.spring.domain.InquiryDTO;
import org.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/inquiries")
    public String manageInquiries(Model model) {
        List<InquiryDTO> inquiries = userService.getAllInquiries();
        model.addAttribute("inquiries", inquiries);
        return "admin/inquiries";
    }

    @PostMapping("/updateInquiryStatus")
    public String updateInquiryStatus(@RequestParam int inquiryNum, @RequestParam String inquiryProgress, RedirectAttributes redirectAttributes) {
        try {
            userService.updateInquiryStatus(inquiryNum, inquiryProgress);
            redirectAttributes.addFlashAttribute("message", "문의 상태가 업데이트되었습니다.");
            return "redirect:/admin/inquiries";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "문의 상태 업데이트에 실패했습니다.");
            return "redirect:/admin/inquiries";
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
    }
}
