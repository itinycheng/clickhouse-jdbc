package ru.yandex.clickhouse;

import ru.yandex.clickhouse.domain.ClickHouseDataType;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author Dmitry Andreev <a href="mailto:AndreevDm@yandex-team.ru"></a>
 */
public class ClickHouseArray implements Array {

    private ClickHouseDataType elementType;
    private Object array;

    public ClickHouseArray(ClickHouseDataType elementType, Object array) {
        if (array == null) {
            throw new IllegalArgumentException("array cannot be null");
        }
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("not array");
        }
        this.elementType = elementType;
        this.array = array;
    }

    @Override
    public String getBaseTypeName() throws SQLException {
        return elementType.name();
    }

    @Override
    public int getBaseType() throws SQLException {
        return elementType.getSqlType();
    }

    @Override
    public Object getArray() throws SQLException {
        if (array == null) {
            throw new SQLException("Call after free");
        }
        return array;
    }

    @Override
    public Object getArray(Map<String, Class<?>> map) throws SQLException {
        return getArray();
    }

    @Override
    public Object getArray(long index, int count) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet(long index, int count) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void free() throws SQLException {
        array = null;
    }

    @Override
    public String toString() {
        if (!array.getClass().isArray()) {
            return Objects.toString(array);
        }

        int arrayLen = java.lang.reflect.Array.getLength(array);
        Object[] newObjArray = new Object[arrayLen];
        for (int i = 0; i < arrayLen; i++) {
            newObjArray[i] = java.lang.reflect.Array.get(array, i);
        }
        return Arrays.toString(newObjArray);
    }
}
