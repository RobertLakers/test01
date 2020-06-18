package cn.tedu.user.pojo;

import javax.persistence.*;

@Table(name = "easydb..t_user")
public class TUser {
    /**
     * 用户id uuid 主键
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户登陆名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码 md5
     */
    @Column(name = "user_password")
    private String userPassword;

    /**
     * 用户昵称
     */
    @Column(name = "user_nickname")
    private String userNickname;

    /**
     * 用户邮箱
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * 用户状态 0(普通用户),1(管理员),2(超级管理员)
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * 获取用户id uuid 主键
     *
     * @return user_id - 用户id uuid 主键
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id uuid 主键
     *
     * @param userId 用户id uuid 主键
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户登陆名称
     *
     * @return user_name - 用户登陆名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户登陆名称
     *
     * @param userName 用户登陆名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户密码 md5
     *
     * @return user_password - 用户密码 md5
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 设置用户密码 md5
     *
     * @param userPassword 用户密码 md5
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 获取用户昵称
     *
     * @return user_nickname - 用户昵称
     */
    public String getUserNickname() {
        return userNickname;
    }

    /**
     * 设置用户昵称
     *
     * @param userNickname 用户昵称
     */
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    /**
     * 获取用户邮箱
     *
     * @return user_email - 用户邮箱
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 设置用户邮箱
     *
     * @param userEmail 用户邮箱
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * 获取用户状态 0(普通用户),1(管理员),2(超级管理员)
     *
     * @return user_type - 用户状态 0(普通用户),1(管理员),2(超级管理员)
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置用户状态 0(普通用户),1(管理员),2(超级管理员)
     *
     * @param userType 用户状态 0(普通用户),1(管理员),2(超级管理员)
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}