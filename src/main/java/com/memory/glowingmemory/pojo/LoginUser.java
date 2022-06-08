package com.memory.glowingmemory.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录系统的User
 *
 * @author zc
 */
@Data
public class LoginUser {

    @TableId(type = IdType.AUTO)
    Integer id;
    String name = "匿名用户";
    String phone = "11111111111";
    String email = "111@qq.com";
    String image = "image";

    @NotNull
    String loginName;
    @NotNull
    String passWord;
    Integer status = 1;
}
