package com.dra.gps.netty.websockt;

import com.alibaba.fastjson.JSON;
import com.dra.gps.redis.RedisUtil;
import com.dra.utils.FinalValueSet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 车辆状态推送服务
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class NettyCarStateWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    RedisUtil redisUtil;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("已连接:"+ctx.channel().remoteAddress());
        log.info("开始推送->"+ctx.channel().remoteAddress());
        ctx.channel().eventLoop().scheduleAtFixedRate(()->{
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(redisUtil.getCarState(FinalValueSet.CAR_STATE))));
        },0,1, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //信息不做处理
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("已断开:"+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
