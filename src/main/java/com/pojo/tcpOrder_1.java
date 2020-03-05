package com.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class tcpOrder {


    private Long id;
    private  String address;
    private  String text;
    private  String direction;
    private Date createTime;


}
