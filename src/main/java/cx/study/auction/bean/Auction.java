package cx.study.auction.bean;

import javax.persistence.*;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
@Entity
public class Auction {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ManyToOne
    private AuctionType type;

    @ManyToOne
    private Customer customer;
    @Column()
    private double startPrice; //起拍价
    private double appraisedPrice; //估价
    private double bidIncrements; //竞价增幅
    private double hammerPrice; //成交价
    private double deposit;      //保证金
    private int status;
    @Column(length = 500)
    private String description;
    @OneToMany(mappedBy = "auction")
    private List<ImageUrl> imageUrls;
    private long startTime;
    private long endTime;
    private long createTime;
    private long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuctionType getType() {
        return type;
    }

    public void setType(AuctionType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getAppraisedPrice() {
        return appraisedPrice;
    }

    public void setAppraisedPrice(double appraisedPrice) {
        this.appraisedPrice = appraisedPrice;
    }

    public double getBidIncrements() {
        return bidIncrements;
    }

    public void setBidIncrements(double bidIncrements) {
        this.bidIncrements = bidIncrements;
    }

    public double getHammerPrice() {
        return hammerPrice;
    }

    public void setHammerPrice(double hammerPrice) {
        this.hammerPrice = hammerPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<ImageUrl> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageUrl> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}
