package io.postman.common.exception;

import io.postman.common.util.StringUtil;

/**
 * 自定义断言
 * @author caojun
 * @date 2018/5/12.
 */
public class Assert {
    /**
     * 字符串小于指定长度
     * @param errorMsg
     * @param maxLength
     * @param message
     */
    public static void assertLessThan(String errorMsg, int maxLength, String message) {
        if (StringUtil.notEmpty(errorMsg) && errorMsg.length() > maxLength)
            throw new RepositoryException(message);
    }
}
