package cx.study.auction.bean;

import java.util.List;

/**
 *
 * Created by chengxiao on 2017/5/10.
 */
public class Statistics {
    private AuctionStatistics auctionStatistics;
    private OrderStatistics orderStatistics;

    public AuctionStatistics getAuctionStatistics() {
        return auctionStatistics;
    }

    public void setAuctionStatistics(AuctionStatistics auctionStatistics) {
        this.auctionStatistics = auctionStatistics;
    }

    public OrderStatistics getOrderStatistics() {
        return orderStatistics;
    }

    public void setOrderStatistics(OrderStatistics orderStatistics) {
        this.orderStatistics = orderStatistics;
    }
}
