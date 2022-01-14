package com.dong.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName roles
 */
@TableName(value ="roles")
@Data
public class Roles implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer rId;

    /**
     * 账号
     */
    private String rUser;

    /**
     * 密码
     */
    private String rPassword;

    /**
     * 成员等级
     */
    private Integer rGrade;

    /**
     * 昵称
     */
    private String rUsername;

    /**
     * 金币
     */
    private Long rGold;

    /**
     * 工会id
     */
    private Integer rUaId;

    /**
     * 头像图片路径地址
     */
    private String rImg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Roles other = (Roles) that;
        return (this.getRId() == null ? other.getRId() == null : this.getRId().equals(other.getRId()))
            && (this.getRUser() == null ? other.getRUser() == null : this.getRUser().equals(other.getRUser()))
            && (this.getRPassword() == null ? other.getRPassword() == null : this.getRPassword().equals(other.getRPassword()))
            && (this.getRGrade() == null ? other.getRGrade() == null : this.getRGrade().equals(other.getRGrade()))
            && (this.getRUsername() == null ? other.getRUsername() == null : this.getRUsername().equals(other.getRUsername()))
            && (this.getRGold() == null ? other.getRGold() == null : this.getRGold().equals(other.getRGold()))
            && (this.getRUaId() == null ? other.getRUaId() == null : this.getRUaId().equals(other.getRUaId()))
            && (this.getRImg() == null ? other.getRImg() == null : this.getRImg().equals(other.getRImg()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRId() == null) ? 0 : getRId().hashCode());
        result = prime * result + ((getRUser() == null) ? 0 : getRUser().hashCode());
        result = prime * result + ((getRPassword() == null) ? 0 : getRPassword().hashCode());
        result = prime * result + ((getRGrade() == null) ? 0 : getRGrade().hashCode());
        result = prime * result + ((getRUsername() == null) ? 0 : getRUsername().hashCode());
        result = prime * result + ((getRGold() == null) ? 0 : getRGold().hashCode());
        result = prime * result + ((getRUaId() == null) ? 0 : getRUaId().hashCode());
        result = prime * result + ((getRImg() == null) ? 0 : getRImg().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rId=").append(rId);
        sb.append(", rUser=").append(rUser);
        sb.append(", rPassword=").append(rPassword);
        sb.append(", rGrade=").append(rGrade);
        sb.append(", rUsername=").append(rUsername);
        sb.append(", rGold=").append(rGold);
        sb.append(", rUaId=").append(rUaId);
        sb.append(", rImg=").append(rImg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}