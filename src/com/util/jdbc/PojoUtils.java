package com.util.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PojoUtils {

    /**
     * ResultSet结果集遍历到List<Map>中
     */
    private static List<Map> convertList(ResultSet resultSet) throws Exception{
        List<Map> list = new ArrayList();
        //获取键名
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        //获取列的数量
        int columnCount = resultSetMetaData.getColumnCount();
        while(resultSet.next()){
            Map rowData = new HashMap();
            for(int i = 1;i <= columnCount;i++){
                //获取键名及值
                rowData.put(resultSetMetaData.getColumnName(i),resultSet.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    /**
     * ResultSet结果集遍历到List<String>中
     */
    private static List<String> convertListString(ResultSet resultSet) throws Exception{
        List<String> list = new ArrayList();
        //获取键名
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        //获取列的数量
        int columnCount = resultSetMetaData.getColumnCount();
        StringBuilder sb = new StringBuilder();
        while(resultSet.next()){
            sb = new StringBuilder();
            for(int i = 1;i <= columnCount;i++){
                //获取键名及值
                sb.append(resultSetMetaData.getColumnName(i)).append(":")
                  .append(resultSet.getObject(i));
            }
            list.add(sb.toString());
        }
        return list;
    }

}
