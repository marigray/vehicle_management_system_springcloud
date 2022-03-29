package com.dra.gps.netty.websockt;

import com.alibaba.fastjson.JSON;
import com.dra.gps.redis.RedisUtil;
import com.dra.pojo.gps.GpsLog;
import com.dra.utils.FinalValueSet;
import com.dra.utils.GPSMessage;
import com.dra.utils.HeaderStringCheck;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 平台数据注册格式： *carId*
 * 连接时需发生对应格式信息进行指定数据获取
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class NettyCarPositionWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    private RedisUtil redisUtil;
    //检测通过 开始任务 1秒钟回写一次数据 从redis中拿到
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {
        log.info("开始读取" + channelHandlerContext.channel().remoteAddress());
        String var1 = msg.text();
        log.info("msg=" + var1);
        if (HeaderStringCheck.isWSHead(var1)) {
            GpsLog var2 = redisUtil.getGpsLog(FinalValueSet.GPS, HeaderStringCheck.getWSHead(var1));
            if (var2 != null) {
                log.info("开始推送->"+channelHandlerContext.channel().remoteAddress());
                channelHandlerContext.channel().eventLoop().scheduleAtFixedRate(() -> {
                            try {
                                channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(GPSMessage.dataWash(redisUtil.getGpsLog(FinalValueSet.GPS, HeaderStringCheck.getWSHead(var1))))));
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }

                        }
                        , 0, 1, TimeUnit.SECONDS);
            }else {
                channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(FinalValueSet.ERROR_DATA_NOT_FIND));
            }
        } else {
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(FinalValueSet.ERROR_FORMAT));
        }
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("已连接" + ctx.channel().remoteAddress());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(cause.getMessage() + "[关闭通道]");
        ctx.channel();
    }

}
