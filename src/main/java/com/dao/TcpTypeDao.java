package com.dao;

import com.pojo.TcpType;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TcpTypeDao {


    @Insert("insert  into db_tcp_type(address,temperature,humidity,light)values ( #{address},#{temperature},#{humidity},#{light})")
    void save(TcpType tcpType);

    @Update("update db_tcp_type set temperature=#{temperature},humidity=#{humidity},light=#{light} where address=#{address}")
    void updateByAddress(TcpType tcpType);


    @Select("select * from db_tcp_type where address=#{address}")
    TcpType serverByAddress(String address);

    @Delete("delete from db_tcp_type where address=#{key}")
    void updateByAddressToLogin(String key);
}
