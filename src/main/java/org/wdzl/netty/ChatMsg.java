package org.wdzl.netty;

import java.io.Serializable;

/**
 * @Author: 王文
 * @Date: 2020/5/31 8:43
 * @Version: 1.0
 * @Description:
 */

public class ChatMsg implements Serializable {
    private String senderId;//发送者id
    private String receiverId;//接收者id
    private String msg;//聊天内容
    private String msgId;//消息签收的id

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
