package com.controller;


import com.pojo.tcpOrder;
import com.service.tcpOrderService;
import com.tcp.NettyServer;
import com.utils.Crc16Util;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/tcpOrder")
public class tcpOrderController {


    @Autowired
    private tcpOrderService tcpOrederService1;


    @RequestMapping("/query")
    public Long query(tcpOrder tcpOrder) {
        String[] split = tcpOrder.getText().split(",");
        List<String> strings = new ArrayList<>();
        for (String s : split) {
            int i = Integer.parseInt(s);
//            十六进制转成十进制
            String tmp =  StringUtils.leftPad(Integer.toHexString(i).toUpperCase(), 2, '0');
            strings.add(tmp);
        }
        List<String> strings1 = new ArrayList<>();
       String[] split1 = strings.toArray(new String[strings.size()]);
        byte[] bytes = to_byte(split1);
        for (byte aByte : bytes) {
            strings1.add(aByte+"");
        }
        String[] toBeStored = strings1.toArray(new String[strings1.size()]);
        byte[] data = Crc16Util.getData(toBeStored);
//        String str = Crc16Util.byteTo16String(data).toUpperCase();
        Map<String, ChannelHandlerContext> map = NettyServer.map;
        ChannelHandlerContext no1_1 = map.get(tcpOrder.getAddress());
        Channel channel = no1_1.channel();
        channel.write(Unpooled.copiedBuffer(data));
        channel.flush();
        return 0l;
    }

    @RequestMapping("/list")
    public List<tcpOrder> exportExcel() {

        List<tcpOrder> tcps = tcpOrederService1.tcpList();
        return tcps;
}

    public static void main(String[] args) {
        String s5="1,1,51,0,0,10";
        String[] split = s5.split(",");
        List<String> strings = new ArrayList<>();
        for (String s : split) {
            int i = Integer.parseInt(s);
//      十六进制转成十进制
            String tmp =  StringUtils.leftPad(Integer.toHexString(i).toUpperCase(), 2, '0');
            strings.add(tmp);
        }


        List<String> strings1 = new ArrayList<>();
        String[] split1 = strings.toArray(new String[strings.size()]);
        byte[] bytes = to_byte(split1);
        for (byte aByte : bytes) {
            strings1.add(aByte+"");
        }
        String[] toBeStored = strings1.toArray(new String[strings1.size()]);
        byte[] data = Crc16Util.getData(toBeStored);
        String str = Crc16Util.byteTo16String(data).toUpperCase();
        System.out.println(str);
    }

    public static byte[] to_byte(String[] strs) {
        byte[] bytes=new byte[strs.length];
        for (int i=0; i<strs.length; i++) {
            bytes[i]=Byte.parseByte(strs[i]);
        }
        return bytes;
    }




}
