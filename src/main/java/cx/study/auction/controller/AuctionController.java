package cx.study.auction.controller;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.service.AuctionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 * Created by cheng.xiao on 2017/5/4.
 */
@RestController
public class AuctionController {
    @Resource
    private AuctionService auctionService;
    @PostMapping("/add-auction")
    public HttpResult<String> add(Auction auction, String editorValue, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate){
        auction.setDescription(editorValue);
        auction.setStartTime(startDate.getTime());
        auction.setEndTime(endDate.getTime());
        auctionService.add(auction);
        return null;
    }


}
