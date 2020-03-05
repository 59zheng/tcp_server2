package com.service;

import com.pojo.tcp;
import org.springframework.stereotype.Service;

import java.util.List;

public interface tcpService {


    List<tcp> tcpList();

    tcp tcpByid(Long id);

    void save(tcp tcp);

    boolean savebyip(String clientIp,String address,String duankou);

   void close(String duankou,String clientIp);
}
