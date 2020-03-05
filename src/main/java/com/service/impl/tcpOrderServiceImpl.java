package com.service.impl;

import com.dao.tcpDao;
import com.dao.tcpOrderDao;
import com.pojo.tcp;
import com.pojo.tcpOrder;
import com.service.tcpOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class tcpOrderServiceImpl implements tcpOrderService {


    @Autowired
    private tcpOrderDao tcpOrderDao1;
    @Autowired
    private tcpDao tcpDao1;


    @Override
    public Long save(tcpOrder tcporder1) {
            tcpOrderDao1.save(tcporder1);
        Long id = tcporder1.getId();
        return id;
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

    @Override
    public String selectByResults(String id) {
        long l = Long.parseLong(id);//返回Long包装类型
        tcpOrder  tcpOrder= tcpOrderDao1.selectByResults(l);
        if (tcpOrder!=null){
            if (tcpOrder.getResults()!=null&&!"".equals(tcpOrder.getResults())){
                return "ok";
            }else {
                return "no";
            }
            }
        else {
            return "no";
        }
    }

    @Override
    public tcpOrder selectBycodeOnly(String codeOnly) {
       return tcpOrderDao1.selectBycodeOnly(codeOnly);
    }

    @Override
    public void Resultssave(tcpOrder tcpOrder) {
        tcpOrderDao1.Resultssave(tcpOrder);
    }

    @Override
    public void Recentlysave(tcpOrder tcpOrder) {
        tcpOrderDao1.Recentlysave(tcpOrder);
    }
}
