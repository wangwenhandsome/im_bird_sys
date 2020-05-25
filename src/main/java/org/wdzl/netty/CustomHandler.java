package org.wdzl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Author: 王文
 * @Date: 2020/5/24 21:50
 * @Version: 1.0
 * @Description:自定义
 */

public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        Channel channel = ctx.channel();
        //在控制台打印远程地址
        System.out.println(channel.remoteAddress());
        //定义向客户端发送的数据内容
        ByteBuf content= Unpooled.copiedBuffer("hello netty~", CharsetUtil.UTF_8);
        //构造http response
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK,content);
        //为响应增加数据类型和长度
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
        //吧响应渲染到html客户端页面
        ctx.writeAndFlush(response);
    }
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 注册");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 移除");
    }
}
