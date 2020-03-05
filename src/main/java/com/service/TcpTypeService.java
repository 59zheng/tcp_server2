package com.service;

import com.pojo.TcpType;

public interface TcpTypeService {
    boolean serverByAddress(TcpType tcpType);

    void save(TcpType tcpType);

    void updateByAddress(TcpType tcpType);

    void close(String key);
}
