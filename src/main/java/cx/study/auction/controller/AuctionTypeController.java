package cx.study.auction.controller;

import cx.study.auction.bean.AuctionType;
import cx.study.auction.service.AuctionTypeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by cheng.xiao on 2017/5/3.
 */
@Controller
public class AuctionTypeController {

    @Resource
    private AuctionTypeService auctionTypeService;

    @GetMapping("/type-list")
    @ResponseBody
    public Map<String,Object> list(int start,int length){
        Map<String,Object> map = new HashMap<>();
        Page<AuctionType> page = auctionTypeService.findAll(start, length);
        map.put("data",page.getContent());
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return map;
    }

    @PostMapping("/type-add")
    public String add(String typeName){
        auctionTypeService.save(typeName);
        return "auction-category";
    }
}
