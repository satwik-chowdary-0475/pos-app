package com.increff.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UiController extends AbstractUiController{

    @RequestMapping(value = "")
    public ModelAndView index() {
        return mav("index.html");
    }

    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    @RequestMapping(value = "/ui/brand")
    public ModelAndView brand(){
        return mav("brand.html");
    }

    @RequestMapping(value = "/ui/product")
    public ModelAndView product(){
        return mav("product.html");
    }




}
