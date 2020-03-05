package com.service;

import com.pojo.tcp;
import com.pojo.tcpOrder;

import java.util.List;

public interface tcpOrderService {


    boolean save(tcpOrder tcporder1);

    tcpOrder selectByAddress(String clientIp,String duankou);

    List<tcpOrder> tcpList();
}
