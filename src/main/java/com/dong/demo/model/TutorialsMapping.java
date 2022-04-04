package com.dong.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 教程爬虫 映射类
 * @TableName tutorials_mapping
 */
@TableName(value ="tutorials_mapping")
@Data
public class TutorialsMapping implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 爬虫项目名称
     */
    private String name;

    /**
     * 对应的实现类
     */
    private String mappingClass;

    /**
     * 网页链接
     */
    private String url;

    /**
     * 1,ALL 全站爬虫 ,2 By where ,有条件的爬虫,
     */
    private Integer crawlertype;

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
        TutorialsMapping other = (TutorialsMapping) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMappingClass() == null ? other.getMappingClass() == null : this.getMappingClass().equals(other.getMappingClass()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getCrawlertype() == null ? other.getCrawlertype() == null : this.getCrawlertype().equals(other.getCrawlertype()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMappingClass() == null) ? 0 : getMappingClass().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getCrawlertype() == null) ? 0 : getCrawlertype().hashCode());
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
        sb.append(", mappingClass=").append(mappingClass);
        sb.append(", url=").append(url);
        sb.append(", crawlertype=").append(crawlertype);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}