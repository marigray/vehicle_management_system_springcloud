package com.dra.gps.netty.tcp;

import com.dra.utils.FinalValueSet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * 已废弃 改为 mqtt 进行数据传输
 */
@Deprecated
@Slf4j
@Component
public class NettyGPSServerConfig {
    @Resource
    private NettyInitChannel nettyInitChannel;
    public void start(InetSocketAddress inetSocketAddress){
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            //创建服务器的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //链式编程
            serverBootstrap.group(boosGroup, workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class)    //使用NioSocketChannel 作为服务器的通道
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(nettyInitChannel);//给我们的workerGroup的 eventLoop 对应管道设置处理器
            //绑定端口生成ChannelFuture 对象
            //启动服务器
            ChannelFuture sync = serverBootstrap.bind(inetSocketAddress.getPort()).sync();
            log.info(FinalValueSet.TCP_SERVER_STARTED+":"+inetSocketAddress.getPort());
            //对关闭通道进行监听
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            log.info(FinalValueSet.TCP_SERVER_STARTED);
        }

    }
}
