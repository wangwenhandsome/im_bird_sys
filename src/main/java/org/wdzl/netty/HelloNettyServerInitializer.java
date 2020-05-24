package org.wdzl.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: 王文
 * @Date: 2020/5/24 20:57
 * @Version: 1.0
 * @Description:初始化
 */
public class HelloNettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //通过SocketChanner去获取对应的管道
        ChannelPipeline pipeline=channel.pipeline();
        /**
         * 通过管道添加Hander
         */
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("CustomHandler",new CustomHandler());
    }
}
