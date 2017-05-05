package cx.study.auction.controller;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.service.AuctionService;
import cx.study.auction.service.ImageUrlService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by cheng.xiao on 2017/5/4.
 */
@RestController
public class AuctionController {
    @Resource
    private AuctionService auctionService;
    @Resource
    private ImageUrlService imageUrlService;
    @GetMapping("/auction-list")
    public Map<String,Object> list(int start, int length, @RequestParam("search[value]") String query){
        Map<String,Object> map = new HashMap<>();
        Page<Auction> page = auctionService.findAll(start, length,query);
        map.put("data",page.getContent());
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return map;
    }

    @PostMapping("/add-auction")
    public HttpResult<String> add(Auction auction, String editorValue,String urls){
        auction.setDescription(editorValue);
        Auction add = auctionService.add(auction, urls);
        if (add != null){
            return new HttpResult<>(0,"添加成功",null);
        }
        return new HttpResult<>(1,"添加失败",null);
    }

    @PostMapping("/update-auction")
    public HttpResult<String> update(Auction auction,String editorValue,String urls){
        auction.setDescription(editorValue);
        Auction update = auctionService.update(auction, urls);
        if (update != null){
            return new HttpResult<>(0,"修改成功",null);
        }
        return new HttpResult<>(1,"修改失败",null);
    }

    @PostMapping("/delete-auction")
    public HttpResult<String> delete(String id){
        auctionService.delete(id);
        return new HttpResult<>(0,"删除成功",null);
    }

    @PostMapping("/delete-batch-auction")
    public HttpResult<String> deleteBatch(String ids){
        ids = ids.substring(1, ids.length());
        String[] array = ids.split(",");
        for (String id : array) {
            auctionService.delete(id);
        }
        return new HttpResult<>(0,"删除成功",null);
    }

    @PostMapping("/delete-batch-image")
    public HttpResult<String> deleteImage(String ids){
        ids = ids.substring(1, ids.length());
        String[] array = ids.split(",");
        for (String id : array) {
            imageUrlService.delete(id);
        }
        return new HttpResult<>(0,"删除成功",null);
    }

}
