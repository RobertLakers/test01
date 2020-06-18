package cn.tedu.service;

public interface UserService {
    /**
     * 查询用户积分
     * @param userId 要查询的用户
     * @return 查找到的用户积分
     */
    int point(String userId);
}
