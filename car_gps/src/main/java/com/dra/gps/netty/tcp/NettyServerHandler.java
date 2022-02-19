package com.dra.gps.netty.tcp;


import com.dra.gps.redis.RedisUtil;
import com.dra.gps.service.CarService;
import com.dra.gps.service.GPSLogService;
import com.dra.gps.service.GPSService;
import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.CarState;
import com.dra.pojo.gps.GpsLog;
import com.dra.utils.FinalValueSet;
import com.dra.utils.GPSMessage;
import com.dra.utils.HeaderStringCheck;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Deprecated
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private CarService carService;

    @Autowired
    private GPSService gpsService;

    @Autowired
    private GPSLogService gpsLogService;

    @Resource
    private RedisUtil redisUtil;

    boolean isRegister = true;

    private Car car;
    //缓存 储存连接信息
    private static final Map<SocketAddress, Map<String,Object>> cars = new HashMap<>();

    private void hasRegister(ChannelHandlerContext ctx){
        Collections.singletonList(cars).forEach(car -> {
            this.isRegister = car.get(ctx.channel().remoteAddress()) == null;
        });
    }

    //心跳处理
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        hasRegister(ctx);
        if (evt instanceof IdleStateEvent&&!this.isRegister){
            //客户端长时间未发生消息给服务端 提醒
            if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
                String var1 = ((Car) cars.get(ctx.channel().remoteAddress()).get(FinalValueSet.CAR)).getCarId();
                redisUtil.setList(FinalValueSet.CAR_STATE,
                        var1, new CarState(var1, FinalValueSet.CAR_STATE_EXCEPTING));
                log.warn("服务空闲-" + ctx.channel().remoteAddress());
            }

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("已连接" + ctx.channel().hashCode());
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("connected".getBytes()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("开始读取");
        String msg_string = msg.toString();
        //检测是否已注册 car
        hasRegister(ctx);
        //消息注册
        System.out.println(msg_string);

        if (this.isRegister) {
            //查询不到相关数据 进入注册通道
            if (HeaderStringCheck.isHead(msg_string)) {
                //获取gps型号
                String gpsId = HeaderStringCheck.getHead(msg_string).get(FinalValueSet.GPS_ID);
                //读取数据库 查询 对应车辆
                car = gpsService.searchCarByGpsId(gpsId);
                if (car==null){
                    refuseAccess(ctx);
                    return;
                }
                //注册
                HashMap<String, Object> map = new HashMap<>();
                map.put(FinalValueSet.GPS_ID, gpsId);
                map.put(FinalValueSet.CAR,car);
                cars.put(ctx.channel().remoteAddress(), map);
                log.info("已注册消息组");
                redisUtil.setList(FinalValueSet.CAR_STATE,car.getCarId(),new CarState(car.getCarId(),FinalValueSet.CAR_STATE_ACTIVE));
            } else {
                refuseAccess(ctx);
            }
        } else {
            log.info("检测完毕");
            //信息解析
            GpsLog gpsLog = GPSMessage.analysisMessage(msg_string).
                    analysisPositionMessage(((Car)cars.get(ctx.channel().remoteAddress()).get(FinalValueSet.CAR)).getCarId()
                            ,(String) cars.get(ctx.channel().remoteAddress()).get(FinalValueSet.GPS_ID));
            //数据处理
            GpsLog gpsLog1 = GPSMessage.dataWash(gpsLog);
            //信息注入redis和mysql数据库 发起任务
            //=============redis==================
            ctx.channel().eventLoop().execute(() -> redisUtil.setList(FinalValueSet.GPS,gpsLog1.getCarId(),gpsLog1));
            //=============mysql==================
            ctx.channel().eventLoop().execute(() -> gpsLogService.add(gpsLog1));
            redisUtil.setList(FinalValueSet.CAR_STATE,car.getCarId(),new CarState(car.getCarId(),FinalValueSet.CAR_STATE_ACTIVE));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("读取完毕");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        String var1 = ((Car) cars.get(ctx.channel().remoteAddress()).get(FinalValueSet.CAR)).getCarId();
        log.error("发生异常:"+cause.getMessage());
        redisUtil.setList(FinalValueSet.CAR_STATE,var1,new CarState(var1,FinalValueSet.CAR_STATE_ACTIVE));
    }
    //断开连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String var1 = ((Car) cars.get(ctx.channel().remoteAddress()).get(FinalValueSet.CAR)).getCarId();
        log.info(ctx.channel().remoteAddress()+"已下线");
        ctx.close();
        //移除注册信息
        cars.remove(ctx.channel().remoteAddress());
        //下线
        redisUtil.setList(FinalValueSet.CAR_STATE,var1,new CarState(var1,FinalValueSet.CAR_STATE_INACTIVE));
    }

    public void refuseAccess(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.copiedBuffer("access refuse".getBytes()));
        log.error("匹配错误,关闭Socket");
        log.info(ctx.channel().remoteAddress()+"已拒绝");
        ctx.close();
    }
}
