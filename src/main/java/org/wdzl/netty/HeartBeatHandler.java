package org.wdzl.netty;

/**
 * @Author: 王文
 * @Date: 2020/6/21 22:31
 * @Version: 1.0
 * @Description:
 */

import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 用于检测channel 的心跳handler
 * 继承ChannelInboundHandlerAdapter，目的是不需要实现ChannelRead0 这个方法
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

}
