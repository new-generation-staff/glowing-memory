package com.memory.glowingmemory.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zc
 */
@Getter
@Setter
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
}

