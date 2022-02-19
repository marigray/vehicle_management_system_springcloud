package com.dra.gps.netty.websockt;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Slf4j
public class NettyCarPositionWebSocketInitChannel extends ChannelInitializer<SocketChannel> {

    @Resource
    private NettyCarPositionWebSocketHandler nettyCarPositionWebSocketHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new HttpServerCodec());
        socketChannel.pipeline().addLast(new ChunkedWriteHandler());
        socketChannel.pipeline().addLast(new HttpObjectAggregator(8912));
        socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/car"));
        socketChannel.pipeline().addLast(nettyCarPositionWebSocketHandler);

    }
}
