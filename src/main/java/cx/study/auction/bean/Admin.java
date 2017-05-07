package cx.study.auction.bean;

import com.google.gson.annotations.Expose;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
@Entity
public class Admin implements Serializable,Cloneable{
    @Id
    @GeneratedValue @Expose
    private Integer id;
    @Expose
    private String username;
    private String password;
    @Expose
    private int level;
    @Expose
    private int status;   //状态 0，使用中 ，1 停用
    @Expose
    private String ip;
    @Expose
    private long createTime;
    @Expose
    private long updateTime;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
