package cx.study.auction.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String phone;
    @Expose
    private String remarks;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
