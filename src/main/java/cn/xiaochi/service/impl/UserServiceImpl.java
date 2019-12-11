package cn.xiaochi.service.impl;

import cn.xiaochi.common.Const;
import cn.xiaochi.common.Response;
import cn.xiaochi.common.TokenCache;
import cn.xiaochi.dao.UserDao;
import cn.xiaochi.pojo.User;
import cn.xiaochi.service.IUserService;
import cn.xiaochi.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    /**
     * 效验是否是管理员
     * @param user
     * @return
     */
    public Response checkAdminRole(User user){
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return Response.success();
        }
        return Response.error();
    }

    // ====================================

    /**
     * 验证用户是否存在
     * @param username
     * @return
     */
    @Override
    public Response<User> login(String username, String password) {
        int resultCount = userDao.checkUsername(username);
        if (resultCount == 0){
            return Response.error("用户不存在");
        }
        User user = userDao.selectLogin(username, MD5Util.MD5EncodeUtf8(password));
        if (user == null){
            return Response.error("密码错误");
        }
        // 把密码设置为空
        user.setPassword(StringUtils.EMPTY);
        return Response.success("登录成功",user);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public Response<User> register(User user) {
        Response validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        user.setRole(Const.Role.ROLE_CUSTOMER);
        int resultCount = userDao.insertSelective(user);
        if (resultCount == 0){
            return Response.error("注册失败");
        }
        return Response.success("注册成功");
    }

    /**
     * 参数效验
     * @param str 字符
     * @param type 字符类型
     * @return
     */
    public Response<String> checkValid(String str,String type){
        if (StringUtils.isNotBlank(str)){
            // 开始校验
            if (Const.USERNAME.equals(type)){
                int resultCount = userDao.checkUsername(str);
                if (resultCount > 0){
                    return Response.error("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)){
                int resultCount = userDao.checkEmail(str);
                if (resultCount > 0){
                    return Response.error("邮箱已存在");
                }
            }
        }else {
            return Response.error("参数错误");
        }
        return Response.success("效验通过");
    }

    /**
     * 找回密码
     * @param username
     * @return
     */
    public Response selectQuestion(String username){
        Response validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()){
            // 验证通过 ， 用户不存在
            return Response.error("用户不存在");
        }
        String question = userDao.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)){
            return Response.success(question);
        }
        return Response.error("找回密码的问题是空的");
    }

    /**
     * 验证问题是否正确
     * @param username
     * @param password
     * @param answer
     * @return
     */
    public Response<String> checkAnswer(String username,String password,String answer){
        int resultCount = userDao.checkAnswer(username, password, answer);
        if (resultCount > 0){
            // 说明问题及问题答案是这个用户的,并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return Response.success(forgetToken);
        }
        return Response.error("问题的答案错误");
    }

    /**
     * 忘记密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    public Response<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        if (StringUtils.isBlank(forgetToken)){
            return Response.error("参数错误,token需要传递");
        }
        Response validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()){
            // 用户不存在
            return Response.error("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)){
            return Response.error("token无效或者过期");
        }
        if (StringUtils.equals(token,forgetToken)){
            int rowCount = userDao.updatePasswordByUsername(username,MD5Util.MD5EncodeUtf8(passwordNew));
            if (rowCount > 0){
                return Response.success("修改密码成功");
            }
        }else {
            return Response.error("token错误,请重新获取重置密码的token");
        }
        return Response.error("修改密码失败");
    }

    /**
     * 重置密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    @Override
    public Response<String> resetPassword(String passwordOld, String passwordNew, User user) {
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int resultCount = userDao.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0){
            return Response.error("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userDao.updateByPrimaryKeySelective(user);
        if (updateCount > 0){
            return Response.success("密码更新成功");
        }
        return Response.error("密码更新失败");
    }

    /**
     * 更新个人资料
     * @param user
     * @return
     */
    @Override
    public Response<User> updateInformation(User user) {
        //username是不能被更新的
        //email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
        int resultCount = userDao.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0){
            return Response.error("email已存在,请更换email再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userDao.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0){
            return Response.success("更新个人信息成功",updateUser);
        }
        return Response.error("更新个人信息失败");
    }

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @Override
    public Response<User> getInformation(Integer id){
        User user = userDao.selectByPrimaryKey(id);
        if (user == null){
            return Response.error("找不到当前用户");
        }
        // 密码置空
        user.setPassword(StringUtils.EMPTY);
        return Response.success(user);
    }
}
