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
    private static ChannelGroup users=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端所传输的消息
        String content = msg.text();
        //1.获取客户端的发来的消息
        //2.判断消息的类型,根据不同的类型处理不同的业务
        /**
         * 2.1当websocket第一次open,初始化channel，把用的channel和userid关联
         * 2.2聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态【未签收】
         * 2.3签收消息类型，针对具体的消息进行签收，修改数据库中对应的消息的签身状态【已签收】
         */
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常后关闭连接，同时从channelgroup里面进
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
