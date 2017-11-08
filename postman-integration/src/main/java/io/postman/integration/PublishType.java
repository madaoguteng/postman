package io.postman.integration;

/**
 * 发布方式枚举类
 *
 * @author caojun
 *
 */
public enum PublishType {
    NIO, // 非阻塞
    BIO // 阻塞
    ;

    public boolean equals(PublishType t){
        if (t == null)
            return false;
        else
            return this.toString().equals(t.toString());
    }
}
