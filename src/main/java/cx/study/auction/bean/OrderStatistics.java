package cx.study.auction.bean;

/**
 *
 * Created by chengxiao on 2017/5/10.
 */
public class OrderStatistics {
    private long total;
    private long waitPay;
    private long waitSend;
    private long waitReceived;
    private long finish;
    private long other;

    public OrderStatistics(long total, long waitPay, long waitSend, long waitReceived, long finish, long other) {
        this.total = total;
        this.waitPay = waitPay;
        this.waitSend = waitSend;
        this.waitReceived = waitReceived;
        this.finish = finish;
        this.other = other;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getWaitPay() {
        return waitPay;
    }

    public void setWaitPay(long waitPay) {
        this.waitPay = waitPay;
    }

    public long getWaitSend() {
        return waitSend;
    }

    public void setWaitSend(long waitSend) {
        this.waitSend = waitSend;
    }

    public long getWaitReceived() {
        return waitReceived;
    }

    public void setWaitReceived(long waitReceived) {
        this.waitReceived = waitReceived;
    }

    public long getFinish() {
        return finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    public long getOther() {
        return other;
    }

    public void setOther(long other) {
        this.other = other;
    }
}
