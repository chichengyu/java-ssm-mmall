package cn.xiaochi.service;

import cn.xiaochi.common.Response;
import cn.xiaochi.pojo.User;

public interface IUserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    Response<User> login(String username,String password);

    /**
     * 注册
     * @param user
     * @return
     */
    Response<User> register(User user);

    /**
     * 参数效验
     * @param str
     * @param type
     * @return
     */
    public Response<String> checkValid(String str,String type);

    /**
     * 找回密码
     * @param username
     * @return
     */
    public Response selectQuestion(String username);

    /**
     * 验证问题是否正确
     * @param username
     * @param password
     * @param answer
     * @return
     */
    public Response<String> checkAnswer(String username,String password,String answer);

    /**
     * 忘记密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    public Response<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    /**
     * 重置密码
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    Response<String> resetPassword(String passwordOld, String passwordNew,User user);

    /**
     * 更新个人资料
     * @param user
     * @return
     */
    Response<User> updateInformation(User user);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    Response<User> getInformation(Integer id);

    /**
     * 效验是否是管理员
     * @param user
     * @return
     */
    public Response checkAdminRole(User user);
}
