package com.dao;


import com.pojo.tcp;
import com.pojo.tcpOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface tcpOrderDao {





    @Insert({ "insert into tcporder(address, text,create_time,direction) values( #{address}," +
            "#{text},#{createTime},#{direction})"})
    void save(tcpOrder tcpOrder);


    @Select("select * from tcporder where address = #{address} and is_send=0")
    tcpOrder selectByAddress(String address);
}
