package com.memory.glowingmemory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zc
 */
@Data
//无参构造
@NoArgsConstructor
//有参构造
@AllArgsConstructor
public class PostRequest implements Cloneable {

    private String requestId;

    @NotNull
    @Positive
    private Integer tenantId;

    @NotNull
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

    public PostRequest copy(PostRequest request) {
        //拷贝？
        PostRequest postRequest = new PostRequest();
        BeanUtils.copyProperties(request, postRequest);
        return postRequest;
    }
}
