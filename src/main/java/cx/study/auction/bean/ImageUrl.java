package cx.study.auction.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;

/**
 *
 * Created by chengxiao on 2017/5/1.
 */
@Entity
public class ImageUrl {
    @Id
    @GeneratedValue @Expose
    private Integer id;
    @Expose
    private String url;
    @ManyToOne
    private Auction auction;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

}
