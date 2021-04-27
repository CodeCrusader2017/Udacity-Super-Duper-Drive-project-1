package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    @GetMapping
    public String getLogoutPage(@ModelAttribute Model model, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("SuccessMessage","Sign Out Successfully");
        return "redirect:/login";
    }
}