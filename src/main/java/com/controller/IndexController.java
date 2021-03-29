package com.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class IndexController {
    /**
     * 网站首页
     * */
    @GetMapping("/")
    public String index(){
        return "/index";
    }

    /**
     * 联系我们
     * */
    @GetMapping("/contacts")
    public String contacts(){
        return "/common/contacts";
    }

    /**
     * 关于我们
     * */
    @GetMapping("/about")
    public String about(){
        return "/common/about";
    }

    /**
     * 后台管理首页
     * */
    @GetMapping("/admin/index")
    public String adminindex(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String admin = (String) session.getAttribute("admin");
        /**拦截器：如果不是管理员，则进行重定向*/
        if (StringUtils.isEmpty(admin)){
            response.sendRedirect(request.getContextPath() + "/");//重定向
        }
        return "/admin/index";
    }

    /**
     * 用户登录注册
     * */
    @GetMapping("/login")
    public String login(){
        return "/user/logreg";
    }

    /**
     * 用户忘记密码
     * */
    @GetMapping("/forget")
    public String forget(){
        return "user/forget";
    }

    /**
     * 个人中心
     * */
    @GetMapping("/user/center")
    public String usercenter(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userid = (String) session.getAttribute("userid");
        /**拦截器：如果不是用户角色登录，则进行重定向*/
        if (StringUtils.isEmpty(userid)){
            response.sendRedirect(request.getContextPath() + "/");//重定向
        }
        return "/user/user-center";
    }

    /**
     * 用户修改密码
     * */
    @RequiresPermissions("user:userinfo")
    @GetMapping("/user/pass")
    public String userinfo(){
        return "/user/updatepass";
    }

    /**
     * 用户更换手机号
     * */
    @RequiresPermissions("user:userinfo")
    @GetMapping("/user/phone")
    public String userphone(){
        return "/user/updatephone";
    }

    /**
     * 用户商品列表
     * */
    @GetMapping("/user/product")
    public String userproduct(){
        return "/user/product/productlist";
    }

    /**
     * 通知消息
     * */
    @GetMapping("/user/message")
    public String commonmessage(){
        return "/user/message/message";
    }
    /**
     * 弹出式通知消息
     * */
    @GetMapping("/user/alertmessage")
    public String alertmessage(){
        return "/user/message/alertmessage";
    }
    /**
     * 跳转到产品清单界面
     * */
    @GetMapping("/product-listing")
    public String toproductlisting() {
        return "/common/product-listing";
    }

    /**
     * 跳转到产品清单搜索界面
     * */
    @GetMapping("/product-search")
    public String toProductSearchs(String keys, ModelMap modelMap) {
        if(keys==null){
            return "/error/404";
        }
        modelMap.put("keys",keys);
        return "/common/product-search";
    }

    /**用户个人中心默认展示图*/
    @GetMapping("/home/console")
    public String homeconsole(){
        return "/admin/home/console";
    }

    /**
     * 管理员首页默认展示图
     * */
    @GetMapping("/echars/console")
    public String echars(){
        return "/admin/echars/console";
    }


    @GetMapping("/app/message/index")
    public String appmessageindex(){
        return "/admin/app/message/index";
    }

    /**
     * 用户收藏列表
     * */
    @GetMapping("/user/collect")
    public String usercollect(){
        return "/user/collect/collectlist";
    }

    /**
     * 用户售出记录
     * */
    @GetMapping("/user/sold")
    public String sold(){
        return "/user/sold/soldrecord";
    }

    /**
     * 销量列表
     * */
    @GetMapping("/admin/sold")
    public String adminSold(){
        return "/admin/sold/soldrecord";
    }

    /**
     * 首页公告清单
     * */
    @GetMapping("/user/newslist")
    public String userNews(){
        return "/common/listnews";
    }

    /**
     * 管理员公告列表
     * */
    @GetMapping("/admin/newslist")
    public String adminNews(){
        return "/admin/news/newslist";
    }
}
