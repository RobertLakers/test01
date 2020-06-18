package cn.tedu.user.controller;

import cn.tedu.user.service.UserService;
import com.jt.common.pojo.User;
import com.jt.common.utils.CookieUtils;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user/manage")
public class UserController {
    //注入一个userService
    @Autowired
    private UserService us;
    //用户名是否存在的检查
    @RequestMapping("checkUserName")
    public SysResult checkUserName(String userName){
        return us.checkUserName(userName);
    }
    //注册用户信息的表单提交，新增
    @RequestMapping("save")
    public SysResult saveUser(User user){
        //判断新增的成功与失败
        try{
            us.saveUser(user);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"注册失败，咋回事",null);
        }
    }

    //接受登陆请求，判断合法和存储redis
    @RequestMapping("login")
    public SysResult doLogin(User user, HttpServletRequest req, HttpServletResponse res){
        //判断 业务层返回数据，是否生成了存储在redis中的key值 ticket
       String ticket= us.doLogin(user);
       if("".equals(ticket)||ticket==null){
           //说明业务层么有存储redis，说明用户名密码不正确
           //登陆失败
           return SysResult.build(201,"登陆失败",null);
       }else{
           //正确存储到redis 登陆成功，控制层使用cookie技术，将返回ticket值
           //带会给浏览器，浏览器客户端才能在登陆之后，有了一张类似票的概念的数据

           //后续访问系统只要判断这张票是否合法 是否超时。
           //调用common-resources cookieUtils
           //其中set方法需要request和response对象 在controller里可以从springmvc拿到
           CookieUtils.setCookie(req,res,"EM_TICKET",ticket);
           //返回数据，告诉ajax登陆成功的
           return SysResult.ok();
       }
    }
    //获取用户登陆后存储在redis中的数据
    @RequestMapping("query/{ticket}")
    public SysResult queryUserData(@PathVariable String ticket){
        //调用业务层，执行redis链接获取数据逻辑
        String userJson=us.queryUserData(ticket);
        //有ticket就一定去得到数据吗？不一定，设置了登陆状态超时。
        if(userJson==null){
            //有ticket，但是redis没有userJson说明确实登陆过，但是已经超时了
            //返回201，不携带任何数据 data
            return SysResult.build(201,"登录可能超时",null);
        }else{
            //ticket也有登陆过，userJson也有表示没超时，登录状态是正常使用的
            return SysResult.build(200,"登录可用",userJson);
        }
    }
    //登出
    @RequestMapping("logout")
    public SysResult doLogout(HttpServletResponse res,HttpServletRequest req) {
        //删除cookie。
        CookieUtils.deleteCookie(req,res,"EM_TICKET");
        return SysResult.ok();
        //删除redis中存储的数据。可以省略代码不删除数据，
        //redis的数据具有超时配置的.但是只删除cookie不删除redis数据，有可能就会
        //造成redis短时间内的数据猛增。
    }
}











