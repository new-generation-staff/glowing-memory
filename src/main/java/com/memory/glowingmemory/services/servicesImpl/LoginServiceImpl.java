package com.memory.glowingmemory.services.servicesImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.memory.glowingmemory.mapper.LoginMapper;
import com.memory.glowingmemory.pojo.LoginUser;
import com.memory.glowingmemory.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zc
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public int register(LoginUser user) {
        int insert = loginMapper.insert(user);
        return insert;
    }

    @Override
    public LoginUser getUser(LoginUser user) {
        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();

        /**
         * wrapper 查询条件
         * select：查询指定列
         * ge：>=
         * gt：>
         * le：<=
         * lt：<
         * eq：=
         * ne：！=
         * between：范围之间 ， 需要三个参数 ， 第一个是表字段名 ， 第二个是起始 ， 第三个是结束 ， 区间是既包含首也包含尾。
         * like：模糊查询
         * orderByDesc：降序排列
         * orderByAsc：升序
         * last：拼接语句
         */

        wrapper.eq("login_name", user.getLoginName())
                .eq("pass_word", user.getPassWord())
                .eq("status", 1);
        return loginMapper.selectOne(wrapper);
    }

    @Override
    public int countByUsername(LoginUser user) {
        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name", user.getLoginName())
                .eq("status", 1);
        return loginMapper.selectCount(wrapper);

    }
}
