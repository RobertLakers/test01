package cn.tedu.user.mapper;

import com.jt.common.pojo.User;

public interface UserMapper {
    public int selectCountByUserName(String userName);
    public void insertUser(User user);
    public User selectUserByNameAndPw(User user);
}
