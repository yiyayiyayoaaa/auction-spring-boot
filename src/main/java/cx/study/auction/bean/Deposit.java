package cx.study.auction.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * Created by chengxiao on 2017/5/6.
 */
@Entity
public class Deposit {

    @Id
    @GeneratedValue @Expose
    private Integer id;
    @ManyToOne @Expose
    private User user;
    @ManyToOne @Expose
    private Auction auction;
    @Expose
    private long time;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
