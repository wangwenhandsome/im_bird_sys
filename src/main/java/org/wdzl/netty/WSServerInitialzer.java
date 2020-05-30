package org.wdzl.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: 王文
 * @Date: 2020/5/25 23:09
 * @Version: 1.0
 * @Description:
 */

public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //获取管道(pipeline)
        ChannelPipeline pipeline=channel.pipeline();
        //webSocket 基与Http协议所需要的编解码器
        pipeline.addLast(new HttpServerCodec());
        //在Http上有一些数据流产生,有大有小，我们对其进行处理，我们需要使用netty对数据流读写提供支持，这个类叫：ChunkedWriteHandler
        pipeline.addLast(new ChunkedWriteHandler());
        //对HttpMessage 进行聚合处理
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        /**
         * 本handel 会帮你处理一些复杂问题
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义handel
        pipeline.addLast(new ChatHandler());

    }
}
