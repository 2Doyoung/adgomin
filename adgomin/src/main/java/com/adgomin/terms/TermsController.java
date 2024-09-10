package com.adgomin.terms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;
@Controller(value = "com.adgomin.terms.controller.TermsController")
public class TermsController {
    @GetMapping(value = "/terms")
    public ModelAndView terms() {
        ModelAndView modelAndView = new ModelAndView("terms/terms");
        return  modelAndView;
    }

    @GetMapping(value = "/privacy")
    public ModelAndView privacy() {
        ModelAndView modelAndView = new ModelAndView("terms/privacy");
        return  modelAndView;
    }
}
