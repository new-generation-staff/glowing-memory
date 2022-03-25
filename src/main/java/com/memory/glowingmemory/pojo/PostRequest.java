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
public class PostRequest {

    private String requestId;

    @NotNull
    @Positive
    private Integer tenantId;

    private String campaignUuid;

    @NotNull
    @Positive
    private Long templateId;

    private List<Map<String, Object>> data;

}
