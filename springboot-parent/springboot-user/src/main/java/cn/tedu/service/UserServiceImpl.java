package cn.tedu.service;

import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper = null;

    @Override
    public int point(String userId) {
        return userMapper.queryPoint(userId);
    }
    //从业务层调用持久层实现更新用户积分逻辑
    @Override
    public void pointUpdate(Integer money) {
        //实现一个2倍积分
        int point=money*2;
        userMapper.addPoint("1",point);
    }
}
