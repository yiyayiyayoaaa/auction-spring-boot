package cx.study.auction.rest;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.HomeItem;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.constants.Constants;
import cx.study.auction.service.AuctionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@RestController
@RequestMapping("/homeRest")
public class HomPageRest {

    @Resource
    private AuctionService auctionService;

    @RequestMapping("/homeInfo")
    public HttpResult<List<HomeItem>> homeInfo(){
        Page<Auction> page1 = auctionService.findAll(0,6, Constants.AuctionStatus.AUCTION);
        Page<Auction> page2 = auctionService.findAll(0,6, Constants.AuctionStatus.WAIT_AUCTION);
        List<HomeItem> homeItems = new ArrayList<>();
        homeItems.add(new HomeItem<>(HomeItem.TITLE,"正在拍卖"));
        for (Auction auction : page1.getContent()){
            homeItems.add(new HomeItem<>(HomeItem.CONTENT,auction));
        }
        homeItems.add(new HomeItem<>(HomeItem.TITLE,"即将开始"));
        for (Auction auction : page2.getContent()){
            homeItems.add(new HomeItem<>(HomeItem.CONTENT,auction));
        }
        return new HttpResult<>(0,"请求成功",homeItems);
    }
    @RequestMapping("/random-view")
    public HttpResult<List<Auction>> randomView(){
        List<Auction> auctions = auctionService.random();
        return new HttpResult<>(0, "", auctions);
    }
}
