package org.wdzl.vo;

import java.util.Date;

/**
 * @Author: 王文
 * @Date: 2020/5/15 21:49
 * @Version: 1.0
 * @Description:
 */

public class FriendsRequestVo {

    private String sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUsername() {
        return sendUsername;
    }

    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername;
    }

    public String getSendFaceImage() {
        return sendFaceImage;
    }

    public void setSendFaceImage(String sendFaceImage) {
        this.sendFaceImage = sendFaceImage;
    }

    public String getSendNickname() {
        return sendNickname;
    }

    public void setSendNickname(String sendNickname) {
        this.sendNickname = sendNickname;
    }
}
