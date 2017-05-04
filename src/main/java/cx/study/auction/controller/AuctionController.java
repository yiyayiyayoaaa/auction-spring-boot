package cx.study.auction.controller;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.service.AuctionService;
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
    public HttpResult<String> add(Auction auction, String editorValue, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate,String urls){
        auction.setDescription(editorValue);
        auction.setStartTime(startDate.getTime());
        auction.setEndTime(endDate.getTime());
        auctionService.add(auction,urls);
        return null;
    }



}
