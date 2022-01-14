package com.dong.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 爬虫一些教程 需要存放的路径信息
 * @TableName tutorials_node
 */
@TableName(value ="tutorials_node")
@Data
public class TutorialsNode implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 对应的访问路径
     */
    private String url;

    /**
     * 父节点对应的id
     */
    private Integer parentId;

    /**
     * 教程状态 0 初始重新爬虫 1 已获得书签 
     */
    private Integer tutorialsStatus;

    /**
     * 项目id
     */
    private Integer crawleId;

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
        TutorialsNode other = (TutorialsNode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getTutorialsStatus() == null ? other.getTutorialsStatus() == null : this.getTutorialsStatus().equals(other.getTutorialsStatus()))
            && (this.getCrawleId() == null ? other.getCrawleId() == null : this.getCrawleId().equals(other.getCrawleId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getTutorialsStatus() == null) ? 0 : getTutorialsStatus().hashCode());
        result = prime * result + ((getCrawleId() == null) ? 0 : getCrawleId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
        sb.append(", parentId=").append(parentId);
        sb.append(", tutorialsStatus=").append(tutorialsStatus);
        sb.append(", crawleId=").append(crawleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}