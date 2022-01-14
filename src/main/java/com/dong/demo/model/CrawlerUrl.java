package com.dong.demo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dong.demo.typeHandler.ElementTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Element;

import java.io.Serializable;

/**
 * 爬虫url
 * @TableName crawler_url
 */
@TableName(value ="crawler_url" ,autoResultMap = true)
@Accessors(chain = true)
@Data
public class CrawlerUrl implements Serializable {
    /**
     * 爬虫项目id
     */
    private Integer crawleId;

    /**
     * 爬虫名称
     */
    private String crawleName;

    /**
     * 链接的文本内容
     */
    private String urlText;

    /**
     * url链接
     */
    private String url;

    /**
     * url节点内容
     */
    @TableField(typeHandler = ElementTypeHandler.class)
    private Element urlElement;

    /**
     * url级别,爬标签用
     */
    private Integer urlGrade;

    /**
     * 爬虫状态 0 :初始 1:完成 2:进行 3:失败 4:异常
     */
    private Integer crawleStatus;

    /**
     * 爬虫顺序
     */
    private Integer crawleOrder;

    /**
     * 错误描述
     */
    private String errDescribe;

    /**
     * 爬虫当页的内容
     */
    @TableField(typeHandler = ElementTypeHandler.class)
    private Element crawlerText;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public CrawlerUrl() {
    }

    public CrawlerUrl(String urlText, String url, Element urlElement, Integer urlGrade, Integer crawleOrder) {
        this.urlText = urlText;
        this.url = url;
        this.urlElement = urlElement;
        this.urlGrade = urlGrade;
        this.crawleOrder = crawleOrder;
    }

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
        CrawlerUrl other = (CrawlerUrl) that;
        return (this.getCrawleId() == null ? other.getCrawleId() == null : this.getCrawleId().equals(other.getCrawleId()))
            && (this.getCrawleName() == null ? other.getCrawleName() == null : this.getCrawleName().equals(other.getCrawleName()))
            && (this.getUrlText() == null ? other.getUrlText() == null : this.getUrlText().equals(other.getUrlText()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getUrlElement() == null ? other.getUrlElement() == null : this.getUrlElement().equals(other.getUrlElement()))
            && (this.getUrlGrade() == null ? other.getUrlGrade() == null : this.getUrlGrade().equals(other.getUrlGrade()))
            && (this.getCrawleStatus() == null ? other.getCrawleStatus() == null : this.getCrawleStatus().equals(other.getCrawleStatus()))
            && (this.getCrawleOrder() == null ? other.getCrawleOrder() == null : this.getCrawleOrder().equals(other.getCrawleOrder()))
            && (this.getErrDescribe() == null ? other.getErrDescribe() == null : this.getErrDescribe().equals(other.getErrDescribe()))
            && (this.getCrawlerText() == null ? other.getCrawlerText() == null : this.getCrawlerText().equals(other.getCrawlerText()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCrawleId() == null) ? 0 : getCrawleId().hashCode());
        result = prime * result + ((getCrawleName() == null) ? 0 : getCrawleName().hashCode());
        result = prime * result + ((getUrlText() == null) ? 0 : getUrlText().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getUrlElement() == null) ? 0 : getUrlElement().hashCode());
        result = prime * result + ((getUrlGrade() == null) ? 0 : getUrlGrade().hashCode());
        result = prime * result + ((getCrawleStatus() == null) ? 0 : getCrawleStatus().hashCode());
        result = prime * result + ((getCrawleOrder() == null) ? 0 : getCrawleOrder().hashCode());
        result = prime * result + ((getErrDescribe() == null) ? 0 : getErrDescribe().hashCode());
        result = prime * result + ((getCrawlerText() == null) ? 0 : getCrawlerText().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", crawleId=").append(crawleId);
        sb.append(", crawleName=").append(crawleName);
        sb.append(", urlText=").append(urlText);
        sb.append(", url=").append(url);
        sb.append(", urlElement=").append(urlElement);
        sb.append(", urlGrade=").append(urlGrade);
        sb.append(", crawleStatus=").append(crawleStatus);
        sb.append(", crawleOrder=").append(crawleOrder);
        sb.append(", errDescribe=").append(errDescribe);
        sb.append(", crawlerText=").append(crawlerText);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}