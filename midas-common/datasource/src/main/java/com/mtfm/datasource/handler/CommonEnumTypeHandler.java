package com.mtfm.datasource.handler;

import com.mtfm.tools.enums.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
@MappedTypes(value = {BaseEnum.class})
public class CommonEnumTypeHandler extends BaseTypeHandler<BaseEnum> {

    private final Class<BaseEnum> type;

    public CommonEnumTypeHandler(Class<BaseEnum> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getInt(columnIndex));
    }

    private BaseEnum convert(int code) {
        BaseEnum[] objs = type.getEnumConstants();
        for(BaseEnum em: objs){
            if(em.getCode() == code) {
                return  em;
            }
        }
        return null;
    }
}
