package cx.study.auction.service;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.AuctionStatistics;
import cx.study.auction.bean.OrderStatistics;
import cx.study.auction.bean.Statistics;
import cx.study.auction.constants.Constants;
import cx.study.auction.constants.Constants.OrderStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cx.study.auction.constants.Constants.AuctionStatus.*;

/**
 *
 * Created by chengxiao on 2017/5/10.
 */
@Service
public class StatisticsService {
    @Resource
    private AuctionService auctionService;
    @Resource
    private OrderService orderService;


    public Statistics get(){
        Statistics statistics = new Statistics();
        statistics.setAuctionStatistics(getAuction());
        statistics.setOrderStatistics(getOrder());
        return statistics;
    }

    public AuctionStatistics getAuction(){
        long total = auctionService.count();
        long waitAuction = auctionService.countByStatus(WAIT_AUCTION);
        long auction = auctionService.countByStatus(AUCTION);
        long success = auctionService.countByStatus(SUCCESS);
        long unsold = auctionService.countByStatus(UNSOLD);
        long other = total -auction -success-unsold-waitAuction;
        return new AuctionStatistics(total,auction,waitAuction,success,unsold,other);
    }

    public OrderStatistics getOrder(){
        long total = orderService.count();
        long waitPay = orderService.countByStatus(OrderStatus.WAIT_PAY);
        long waitSend = orderService.countByStatus(OrderStatus.WAIT_SEND);
        long waitReceived = orderService.countByStatus(OrderStatus.WAIT_RECEIVED);
        long finish = orderService.countByStatus(OrderStatus.FINISH);
        long cancel = orderService.countByStatus(OrderStatus.CANCEL);
        return new OrderStatistics(total,waitPay,waitSend,waitReceived,finish,cancel);
    }
}
