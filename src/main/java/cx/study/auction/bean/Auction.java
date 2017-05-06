package cx.study.auction.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
@Entity
public class Auction {

    @Id
    @GeneratedValue @Expose
    private Integer id;
    @Expose
    private String name;
    @ManyToOne @Expose
    private AuctionType type;
    @Transient
    private Integer typeId;
    @ManyToOne @Expose
    private Customer customer;
    @Transient
    private Integer customerId;
    @Column() @Expose
    private double startPrice; //起拍价
    @Expose
    private double appraisedPrice; //估价
    @Expose
    private double bidIncrements; //竞价增幅
    @Expose
    private double hammerPrice; //成交价
    @Expose
    private double deposit;      //保证金
    @Expose
    private int status;
    @Column(length = 500) @Expose
    private String description;
    @OneToMany(cascade = {CascadeType.REMOVE},mappedBy = "auction") @Expose
    private List<ImageUrl> imageUrls;
    @Expose
    private Long startTime;
    @Expose
    private Long endTime;
    @Expose
    private long createTime;
    @Expose
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

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
