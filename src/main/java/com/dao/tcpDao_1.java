package com.dao;


import com.pojo.tcp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface tcpDao {


    @Select("select * from tcpclient where id = #{id}")
    public tcp tcpByid(Long id);


    @Select("select * from tcpclient where islongin = 1")
    public List<tcp> tcpList();


    @Insert({ "insert into tcpclient(ip, duankou," +
            "text,islongin,address) values( #{ip}, #{duankou}, #{text}, #{islongin},#{address})"})
    void save(tcp tcp);

    @Select("select * from tcpclient where ip=#{clientIp} and islongin=1 and duankou=#{duankou}")
    List<tcp> savebyip(@Param("clientIp") String clientIp,@Param("duankou") String duankou);

    @Delete("delete from tcpclient where ip=#{clientIp} and duankou=#{duankou}")
    void updatebyip( @Param("clientIp") String ip, @Param("duankou") String duankou);
    @Update("update tcpclient set address=#{address}")
    void updatebyiptext(String msg1);

    @Select("select * from tcpclient where ip=#{clientIp} and islongin=1 and duankou=#{duankou} and address=#{address}")
    tcp selectbyaddress(@Param("clientIp") String clientIp,@Param("duankou") String duankou,@Param("address") String
            address);
}
