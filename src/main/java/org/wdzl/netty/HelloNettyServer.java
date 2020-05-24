package org.wdzl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: 王文
 * @Date: 2020/5/24 16:03
 * @Version: 1.0
 * @Description:实现客户端发送请求，服务器给响应
 */

public class HelloNettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建一组线程池组
        //主线程池：用于接受客户端的请求连接，不做任何处理
        EventLoopGroup group1 = new NioEventLoopGroup();
        //从线程池：主线程池会把任务交给它
        EventLoopGroup group2= new NioEventLoopGroup();
        try{
            //创建服务器启动类
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(group1,group2)//设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置nio双向通道
                    .childHandler(new HelloNettyServerInitializer());//添加子处理器，处理从线程池的任务
            //启动服务，并且设置端口号，启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            //监听关闭的channel,设置为同步方式
            channelFuture.channel().closeFuture().sync();
        }finally {
            group1.shutdownGracefully();
            group2.shutdownGracefully();

        }

    }
}
