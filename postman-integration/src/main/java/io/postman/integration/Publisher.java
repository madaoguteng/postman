package io.postman.integration;

/**
 * Created by caojun on 2017/11/7.
 */
public interface Publisher {
    /**
     * 非阻塞方式发送消息
     * @param msgName 消息名称
     * @param routingKey 路由key
     * @param value 消息内容
     * @param callback
     */
    <K,V> void sendNIO(String msgName, K routingKey, V value, Callback callback);

    /**
     * 阻塞方式发送消息
     * @param msgName 消息名称
     * @param routingKey 路由key
     * @param value 消息内容
     */
    <K,V> void sendBIO(String msgName, K routingKey, V value);
}
