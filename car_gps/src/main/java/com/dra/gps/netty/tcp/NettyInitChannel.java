package com.dra.gps.netty.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
@Deprecated
@Component
@Slf4j
public class NettyInitChannel extends ChannelInitializer<SocketChannel> {

    @Resource
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        //解码器
        socketChannel.pipeline().addLast("decoder",new StringDecoder(StandardCharsets.UTF_8));
        //编码器
        socketChannel.pipeline().addLast("encoder",new StringEncoder(StandardCharsets.UTF_8));
        //心跳检测 配置
        socketChannel.pipeline().addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
        //自定义心跳处理
        socketChannel.pipeline().addLast(nettyServerHandler);

    }
}
