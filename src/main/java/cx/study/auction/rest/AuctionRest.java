package cx.study.auction.rest;

import com.google.gson.JsonObject;
import cx.study.auction.bean.Auction;
import cx.study.auction.bean.AuctionType;
import cx.study.auction.bean.BidRecord;
import cx.study.auction.bean.HttpResult;
import cx.study.auction.constants.Constants;
import cx.study.auction.constants.Constants.AuctionStatus;
import cx.study.auction.service.AuctionService;
import cx.study.auction.service.AuctionTypeService;
import cx.study.auction.service.BidRecordService;
import cx.study.auction.service.DepositService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@RestController
@RequestMapping("/CommodityRest")
public class AuctionRest {

    @Resource
    private AuctionService auctionService;
    @Resource
    private AuctionTypeService auctionTypeService;
    @Resource
    private DepositService depositService;
    @Resource
    private BidRecordService bidRecordService;
    @RequestMapping("/findCommodityById")
    public HttpResult<Auction> findById(@RequestBody()JsonObject json){
        int id = json.get("commodityId").getAsInt();
        Auction auction = auctionService.findById(id);
        return new HttpResult<>(0,"请求成功",auction);
    }

    @RequestMapping("/commodityType")
    public HttpResult<List<AuctionType>> getAuctionType(){
        List<AuctionType> types = auctionTypeService.findAll();
        return new HttpResult<>(0,"请求成功",types);
    }

    @RequestMapping("/commodities")
    public HttpResult<List<Auction>> getAuctions(@RequestBody()JsonObject json) {
        int id = json.get("id").getAsInt();
        List<Auction> auctions = new ArrayList<>();
        if (id < 0) {
            switch (id) {
                case -1:
                    auctions.addAll(auctionService.findByStatus(AuctionStatus.AUCTION));
                    auctions.addAll(auctionService.findByStatus(AuctionStatus.WAIT_AUCTION));
                    break;
                case -2:
                    auctions.addAll(auctionService.findByStatus(AuctionStatus.AUCTION));
                    break;
                case -3:
                    auctions.addAll(auctionService.findByStatus(AuctionStatus.WAIT_AUCTION));
                    break;
            }
        } else {
            auctions.addAll(auctionService.findByStatusAndType(id, AuctionStatus.AUCTION));
            auctions.addAll(auctionService.findByStatusAndType(id, AuctionStatus.WAIT_AUCTION));
        }
        return new HttpResult<>(0, "", auctions);
    }

    @RequestMapping("/bid")
    public HttpResult<BidRecord> bid(@RequestBody()JsonObject json) throws Exception {
        int commodityId = json.get("commodityId").getAsInt();
        int userId = json.get("userId").getAsInt();
        double price = json.get("price").getAsDouble();
        BidRecord bid = bidRecordService.bid(commodityId, userId, price);
        if (bid != null){
            return new HttpResult<>(0, "", bid);
        }
        return new HttpResult<>(1, "", null);
    }

    @RequestMapping("/bidRecords")
    public HttpResult<List<BidRecord>> getBidRecord(@RequestBody()JsonObject json) throws Exception {
        int commodityId = json.get("commodityId").getAsInt();
        List<BidRecord> records = bidRecordService.find(commodityId);
        return new HttpResult<>(0, "", records);
    }

    @RequestMapping("/auction")
    public HttpResult<List<Auction>> auction(@RequestBody()JsonObject json) throws Exception {
        int userId = json.get("userId").getAsInt();
        List<Auction> auctions = depositService.findByUserId(userId);
        return new HttpResult<>(0, "", auctions);
    }
}
