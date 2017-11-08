package io.postman.integration;

/**
 * Created by caojun on 2017/11/7.
 */
public interface Callback {

    /**
     * 回调方法
     * @param metadata 消息队列中包含的消息摘要信息,如topic，key, offset等
     * @param exception
     */
    void onCompletion(String metadata, Exception exception);
}
