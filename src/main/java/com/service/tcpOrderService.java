package com.service;

import com.pojo.tcp;
import com.pojo.tcpOrder;

import java.util.List;

public interface tcpOrderService {


    Long save(tcpOrder tcporder1);

    tcpOrder selectByAddress(String clientIp,String duankou);

    List<tcpOrder> tcpList();

    void Recentlysave(tcpOrder tcpOrder);

    void Resultssave(tcpOrder tcpOrder);

    tcpOrder selectBycodeOnly(String codeOnly);

    String selectByResults(String id);
}
