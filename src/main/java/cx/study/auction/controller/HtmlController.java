package cx.study.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
@Controller
public class HtmlController {

    @GetMapping(value = "/index")
    public ModelAndView toIndex(){
        return new ModelAndView("index");
    }

    @GetMapping(value = "/login")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    @GetMapping(value = "/welcome")
    public ModelAndView toWelcome(){
        return new ModelAndView("welcome");
    }

    @GetMapping(value = "/auction-list")
    public ModelAndView toAuctionList(){
        return new ModelAndView("auction-list");
    }
    @GetMapping(value = "/auction-category")
    public ModelAndView toAuctionCategory(){
        return new ModelAndView("auction-category");
    }
    @GetMapping(value = "/auction-category-add")
    public ModelAndView toAuctionCategoryAdd(){
        return new ModelAndView("auction-category-add");
    }
    @GetMapping(value = "/auction-category-edit/{id}/{typeName}")
    public ModelAndView toAuctionCategoryEdit(@PathVariable Integer id, @PathVariable String typeName){
        ModelAndView modelAndView = new ModelAndView("auction-category-edit");
        modelAndView.addObject("id",id);
        modelAndView.addObject("typeName",typeName);
        return modelAndView;
    }
    @GetMapping(value = "/auction-add")
    public ModelAndView toAuctionAdd(){
        return new ModelAndView("auction-add");
    }
}
