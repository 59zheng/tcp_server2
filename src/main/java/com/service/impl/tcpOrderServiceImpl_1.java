package com.service.impl;

import com.dao.tcpDao;
import com.dao.tcpOrderDao;
import com.pojo.tcp;
import com.pojo.tcpOrder;
import com.service.tcpOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class tcpOrderServiceImpl implements tcpOrderService {


    @Autowired
    private tcpOrderDao tcpOrderDao1;
    @Autowired
    private tcpDao tcpDao1;


    @Override
    public boolean save(tcpOrder tcporder1) {

        try {
            tcpOrderDao1.save(tcporder1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public tcpOrder selectByAddress(String clientIp,String duankou) {
        List<tcp> savebyip = tcpDao1.savebyip(clientIp,duankou);
        tcpOrder tcpOrder=null;
        for (int i = 0; i < savebyip.size(); i++) {
            String address = savebyip.get(0).getAddress();
             tcpOrder = tcpOrderDao1.selectByAddress(address);
        }

        return tcpOrder;



    }

    @Override
    public List<tcpOrder> tcpList() {
        return null;
    }
}
