package cx.study.auction.controller;

import cx.study.auction.bean.AuctionType;
import cx.study.auction.bean.HttpResult;
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
@RestController
public class AuctionTypeController {

    @Resource
    private AuctionTypeService auctionTypeService;

    @GetMapping("/type-list")
    @ResponseBody
    public Map<String,Object> list(int start,int length,@RequestParam("search[value]") String query){
        Map<String,Object> map = new HashMap<>();
        Page<AuctionType> page = auctionTypeService.findAll(start, length,query);
        map.put("data",page.getContent());
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return map;
    }

    @PostMapping("/type-add")
    public void add(String typeName){
        auctionTypeService.save(typeName);
    }

    @PostMapping("/type-update")
    public void update(Integer id,String typeName){
        auctionTypeService.update(id,typeName);
    }

    @PostMapping("/type-delete-batch")
    public HttpResult<String> deleteBatch(String ids){
        ids = ids.substring(1, ids.length());
        String[] array = ids.split(",");
        for (String id : array) {
            auctionTypeService.delete(id);
        }
        return new HttpResult<>(0,"删除成功",null);
    }

    @PostMapping("/type-delete")
    public HttpResult<String> delete(String id){
        auctionTypeService.delete(id);
        return new HttpResult<>(0,"删除成功",null);
    }
}
