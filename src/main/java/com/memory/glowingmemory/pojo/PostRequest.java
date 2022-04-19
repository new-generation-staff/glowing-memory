package com.memory.glowingmemory.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.util.List;
import java.util.Map;

/**
 * @author zc
 */
@Data
public class PostRequest implements Cloneable {

    private String requestId;

    @NotNull
    @Positive
    private Integer tenantId;

    private String campaignUuid;

    private List<Map<String, Object>> data;

    //深拷贝对象
    @Override
    public PostRequest clone() throws CloneNotSupportedException {
        PostRequest clone = (PostRequest) super.clone();
        //不拷贝原来的data字段
        clone.setData(null);
        return clone;
    }
}
