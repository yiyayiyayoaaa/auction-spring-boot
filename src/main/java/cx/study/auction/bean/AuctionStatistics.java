package cx.study.auction.bean;

/**
 *
 * Created by chengxiao on 2017/5/10.
 */
public class AuctionStatistics {
    private long total;
    private long auction;
    private long waitAuction;
    private long success;
    private long unsold;
    private long other;

    public AuctionStatistics(long total, long auction, long waitAuction, long success, long unsold, long other) {
        this.total = total;
        this.auction = auction;
        this.waitAuction = waitAuction;
        this.success = success;
        this.unsold = unsold;
        this.other = other;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getAuction() {
        return auction;
    }

    public void setAuction(long auction) {
        this.auction = auction;
    }

    public long getWaitAuction() {
        return waitAuction;
    }

    public void setWaitAuction(long waitAuction) {
        this.waitAuction = waitAuction;
    }

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public long getUnsold() {
        return unsold;
    }

    public void setUnsold(long unsold) {
        this.unsold = unsold;
    }

    public long getOther() {
        return other;
    }

    public void setOther(long other) {
        this.other = other;
    }
}
