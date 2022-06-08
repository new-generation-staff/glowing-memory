package com.memory.glowingmemory.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

/**
 * 登录系统的User
 *
 * @author zc
 */
@Data
@Table("login_user")
public class LoginUser {

    @Column
    @TableId(type = IdType.AUTO)
    Integer id;
    @Column
    String name = "匿名用户";
    @Column
    String phone = "11111111111";
    @Column
    String email = "111@qq.com";
    @Column
    String image = "image";

    @Column("login_name")
    String loginName;
    @Column("pass_word")
    String passWord;
    @Column
    Integer status = 1;
}
