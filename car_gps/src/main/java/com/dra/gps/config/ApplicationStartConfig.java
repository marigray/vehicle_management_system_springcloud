package com.dra.gps.config;

import com.dra.gps.netty.websockt.NettyCarPositionWebSocketServerConfig;
import com.dra.gps.netty.websockt.NettyCarStateWebSocketServerConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@ConfigurationProperties("my.server.port")
@Data
public class ApplicationStartConfig {
    private int tcp;
    private int wsPosition;
    private int wsSate;
//    @Resource
//    private NettyGPSServerConfig nettyGPSServerConfig;
    @Resource
    private NettyCarStateWebSocketServerConfig nettyCarStateWebSocketServerConfig;
    @Resource
    private NettyCarPositionWebSocketServerConfig nettyCarPositionWebSocketServerConfig;

    public void run(){
        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(()->nettyGPSServerConfig.start((new InetSocketAddress(tcp))));
        executorService.execute(()->nettyCarStateWebSocketServerConfig.start(new InetSocketAddress(wsSate)));
        executorService.execute(()->nettyCarPositionWebSocketServerConfig.start(new InetSocketAddress(wsPosition)));
    }
}
