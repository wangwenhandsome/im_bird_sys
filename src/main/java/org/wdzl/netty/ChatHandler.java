package org.wdzl.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @Author: 王文
 * @Date: 2020/5/26 9:55
 * @Version: 1.0
 * @Description:用来处理消息的handler
 */

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于记录和管理所有客户端的channel
    private static ChannelGroup clients=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端所传输的消息
        String content = msg.text();
        System.out.println("接受到的数据："+content);

        //将数据刷新到客户端上
        clients.writeAndFlush(
                new TextWebSocketFrame(
                        "[服务器在：]"+ LocalDateTime.now()
                        +"接受到的消息，消息内容为："+content));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //clients.remove(ctx.channel());
        System.out.println("客户端断开，channel对应的长Id为:"+ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短Id为:"+ctx.channel().id().asShortText());
    }
}
