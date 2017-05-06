package cx.study.auction.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@Entity
public class BidRecord {
    @Id
    @GeneratedValue @Expose
    private Integer id;
    @ManyToOne @Expose
    private User user;
    @ManyToOne @Expose
    private Auction auction;
    @Expose
    private double price;
    @Expose
    private long bidTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getBidTime() {
        return bidTime;
    }

    public void setBidTime(long bidTime) {
        this.bidTime = bidTime;
    }
}
