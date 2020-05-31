package org.wdzl.netty;

import java.io.Serializable;

/**
 * @Author: 王文
 * @Date: 2020/5/31 8:41
 * @Version: 1.0
 * @Description:
 */

public class DataContent implements Serializable {
    private Integer action;//动作类型
    private ChatMsg chatMsg;//用户的聊天内容
    private String extand;//扩展字段
}
