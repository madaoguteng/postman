package io.postman.integration.domain.model.publisher;

import io.postman.common.util.StringUtil;

import java.util.UUID;

/**
 * Created by caojun on 2017/11/7.
 */
public class PublishLogId {
    private String id;

    public PublishLogId(){
        this.id = UUID.randomUUID().toString().replaceAll("-","");
    }

    protected PublishLogId(String id){
        if (StringUtil.notEmpty(id))
            this.id = id;
        else
            this.id = UUID.randomUUID().toString().replaceAll("-","");
    }

    public String id() {
        return id;
    }
}
