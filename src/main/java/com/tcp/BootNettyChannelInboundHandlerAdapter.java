package com.tcp;


import com.pojo.tcp;
import com.service.TcpTypeService;
import com.service.tcpOrderService;
import com.service.tcpService;
import com.utils.SpringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 * I/O数据读写处理类
 */
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {


    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private tcpService tcpService1 = applicationContext.getBean(tcpService.class);
    private tcpOrderService tcpOrderService = applicationContext.getBean(tcpOrderService.class);
    private TcpTypeService tcpTypeService = applicationContext.getBean(TcpTypeService.class);


    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        System.out.println(msg);
        if ((msg + "").contains("dapeng")) {
//            心跳
            tcp savebyip = tcpService1.savebyip(msg + "");
            InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String clientIp = insocket.getAddress().getHostAddress();
            int port = insocket.getPort();
//
            if (tcpService1.savebyip(msg + "") == null) {
//                                没有
//            添加map管理，添加在线设备
                NettyServer.map.put(msg + "", ctx);
                tcp tcp = new tcp();
                tcp.setAddress(msg + "");
                tcp.setIp(clientIp);
                tcp.setDuankou(port + "");
                tcpService1.save(tcp);
            } else {
                    //                添加心跳时间
                    tcp tcp = new tcp();
                    tcp.setHeartbeatTime(new Date());
                    tcp.setAddress(msg + "");
                    tcpService1.updatebyaddressTime(tcp);
                }
        } else {
//            消息返回状态.
            System.out.println(msg                );
        }
    }

    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
        System.out.println("接受成功");
        ctx.flush();
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
        System.out.println("客户端异常断开连接:" + clientIp);
        for (String key : NettyServer.map.keySet()) {
            if (NettyServer.map.get(key) != null && NettyServer.map.get(key).equals(ctx)) {
                NettyServer.map.remove(key);
                tcpTypeService.close(key);
            }
        }
        int port = insocket.getPort();
        tcpService1.close(clientIp, port + "");
        ctx.close();//抛出异常，断开与客户端的连接
    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {


    }

    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
        System.out.println("客户端断开连接:" + clientIp);
        for (String key : NettyServer.map.keySet()) {
            if (NettyServer.map.get(key) != null && NettyServer.map.get(key).equals(ctx)) {
                NettyServer.map.remove(key);
                tcpTypeService.close(key);
            }
        }
        int port = insocket.getPort();
        tcpService1.close(clientIp, port + "");
    }

    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception, IOException {
        super.userEventTriggered(ctx, evt);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close();//超时时断开连接
        System.out.println("userEventTriggered:" + clientIp);
    }
}
