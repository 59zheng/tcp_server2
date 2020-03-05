package com.service.impl;


import com.dao.tcpDao;
import com.pojo.tcp;
import com.service.tcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class tcpServiceImpl  implements tcpService {

    @Autowired
    private tcpDao tcpDao1;

    @Override
    public List<tcp> tcpList() {
        List<tcp> tcpDaos = tcpDao1.tcpList();
        return tcpDaos;

    }

    @Override
    public tcp tcpByid(Long id) {
        return tcpDao1.tcpByid(id);
    }

    @Override
    public void save(tcp tcp) {
       tcpDao1.save(tcp);
    }

    @Override
    public boolean savebyip(String clientIp,String address,String duankou) {

        tcp selectbyaddress = tcpDao1.selectbyaddress(address, clientIp, duankou);
        if (selectbyaddress!=null){
            return true;
        }
        return false;

    }

    @Override
    public void close(String duankou, String clientIp) {
        tcpDao1.updatebyip(duankou,clientIp);
    }

   /* @Override
    public void savetext(String msg1,String clientIp) {
        List<tcp>tcpList= tcpDao1.savebyip(clientIp);
        tcpList.forEach(item->{
            item.setText(msg1);
            tcpDao1.save(item);
        });
    }*/
}

