package com.dong.demo.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将Element属性映射为varchar
 *
 * @author Dong_Jia_Qi on 2021/12/23
 */
@MappedTypes(Element.class)//转换出来的类型
@MappedJdbcTypes(JdbcType.VARCHAR)//数据库用的类型
public class ElementTypeHandler extends BaseTypeHandler<Element> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Element element, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, element.toString());
    }

    @Override
    public Element getNullableResult(ResultSet rs, String s) throws SQLException {
        String elementStr = rs.getString(s);
        return getElement(elementStr);
    }

    @Override
    public Element getNullableResult(ResultSet rs, int i) throws SQLException {
        String elementStr = rs.getString(i);
        return getElement(elementStr);
    }

    @Override
    public Element getNullableResult(CallableStatement cs, int i) throws SQLException {
        String elementStr = cs.getString(i);
        return getElement(elementStr);
    }

    /**
     * 将拿到的String转为 Element
     * @param elementStr 数据库查到的 element的字符串形式
     * @return  通过 Jsoup 解析后的 Element
     */
    private Element getElement(String elementStr) {
        if (elementStr == null) {
            return null;
        }
        Document parse = Jsoup.parse(elementStr);
        Element element = parse.body();
        return element;
    }
}
