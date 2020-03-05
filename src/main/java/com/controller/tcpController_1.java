package com.controller;

import com.pojo.tcp;
import com.service.tcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tcpClient")
public class tcpController {


    @Autowired
    private tcpService tcpService1;

    @RequestMapping("/accept")
    public List<tcp> exportExcel() throws IOException {

        List<tcp> tcps = tcpService1.tcpList();
        return tcps;
    }

   /* @RequestMapping("/tcpByid")
    public boolean tcpByid(Long id, String text) throws IOException {

        tcp tcps = tcpService1.tcpByid(id);
        //boolean out = tcpClient1.out(tcps.getIp(), tcps.getDuankou(), text);
        return out;
    }*/

    @RequestMapping("/index")
    public String ftlIndex() {
        return "index";
    }



}
