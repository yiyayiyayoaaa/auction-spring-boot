package cx.study.auction.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
@Entity
public class User {
    @Id
    @GeneratedValue @Expose
    private Integer id;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String nickname;
    @Expose
    private String avatar;  //头像
    @Expose
    private int gender;
    @Expose
    private long birth;
    @Expose
    private double account; //账户
    @Expose
    private int status;   //状态 0，使用中 ，1 停用
    @OneToMany(mappedBy = "user")
    private List<UserAddress> addresses;
    @OneToMany(mappedBy = "user")
    private List<OrderInfo> orders;
    private long createTime;
    private long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public List<UserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddress> addresses) {
        this.addresses = addresses;
    }

    public List<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfo> orders) {
        this.orders = orders;
    }
}
