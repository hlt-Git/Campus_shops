package com.controller.admin;


import com.entity.News;
import com.service.NewsService;
import com.util.KeyUtil;
import com.util.StatusCode;
import com.vo.LayuiPageVo;
import com.vo.PageVo;
import com.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  公告控制器
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 发布公告
     * 1.传入公告标题（newstitle），公告简介（newsdesc）、公告内容（newscontent），简介图（image）
     * 2.填写session获取的发布者
     * */
    @ResponseBody
    @PostMapping("/news/insert")
    public ResultVo insertNews(@RequestBody News news, HttpSession session){
        String username=(String) session.getAttribute("username");
        news.setId(KeyUtil.genUniqueKey()).setUsername(username);
        Integer i = newsService.insertNews(news);
        if (i == 1){
            return new ResultVo(true, StatusCode.OK,"公告发布成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"公告发布失败，请重新发布");
    }

    /**
     * 删除公告
     * 1.前端传入需删除公告的id
     * 2.判断是否是本人或超级管理员
     * */
    @ResponseBody
    @PutMapping("/news/delect/{id}")
    public ResultVo delectNews (@PathVariable("id") String id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        News news = newsService.queryNewsById(id);
        if (StringUtils.isEmpty(news)){
            return new ResultVo(false,StatusCode.FINDERROR,"找不到要删除的公告");
        }else {
            /**判断是否是本人或超级管理员*/
            if (news.getUsername().equals(username) || username.equals("admin")){
                Integer i = newsService.delectNews(id);
                if (i == 1){
                    return new ResultVo(true,StatusCode.OK,"删除成功");
                }
                return new ResultVo(false,StatusCode.ERROR,"删除失败");
            }else {
                return new ResultVo(false,StatusCode.ACCESSERROR,"权限不足，无法删除");
            }
        }
    }

    /**
     *查看公告详情
     * **/
    @GetMapping("/news/detail/{id}")
    public String queryNewsById (@PathVariable("id") String id,ModelMap modelMap){
        //浏览量+1
        newsService.addNewsRednumber(id);
        News news = newsService.queryNewsById(id);
        if (StringUtils.isEmpty(news)){
            return "/error/404";
        }
        modelMap.put("news",news);
        return "/common/newsdetail";
    }
    /**
     *跳转到发布公告
     * **/
    @GetMapping("/news/torelnews")
    public String torelnews (){
        return "/admin/news/relnews";
    }

    /**
     *跳转到修改公告
     * **/
    @GetMapping("/news/toupdate/{id}")
    public String toupdate (@PathVariable("id") String id, ModelMap modelMap, HttpSession session){
        String username=(String) session.getAttribute("username");
        News news = newsService.queryNewsById(id);
        /**如果是本人则可以跳转修改*/
        if (news.getUsername().equals(username)){
            modelMap.put("qx",1);
            modelMap.put("news",news);
            return "/admin/news/updatenews";
        }
        modelMap.put("news",news);
        modelMap.put("qx",0);
        return "/admin/news/updatenews";
    }

    /**
     *修改公告
     * **/
    @ResponseBody
    @PutMapping("/news/update")
    public ResultVo updateNews (@RequestBody News news){
        Integer i = newsService.updateNews(news);
        if (i == 1){
            return new ResultVo(true,StatusCode.OK,"修改成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"修改失败");
    }

    /**
     *查询前三条公告
     * **/
    @ResponseBody
    @GetMapping("/news/all")
    public ResultVo queryNews (){
        List<News> newslist = newsService.queryNews();
        return new ResultVo(true,StatusCode.OK,"查询成功",newslist);
    }

    /**
     * 后台分页查看公告列表
     * 1.前端传入页码、分页数量
     * 2.查询分页数据
     */
    @ResponseBody
    @GetMapping("/news/queryall")
    public LayuiPageVo queryAllNews(int limit, int page) {
        List<News> newsList = newsService.queryAllNews((page - 1) * limit, limit);
        Integer dataNumber = newsService.LookNewsCount();
        return new LayuiPageVo("",0,dataNumber,newsList);
    }

    /**
     * 首页公告分页数据
     * */
    @GetMapping("/news/index/number")
    @ResponseBody
    public PageVo newsNumber(){
        Integer dataNumber = newsService.LookNewsCount();
        return new PageVo(StatusCode.OK,"查询成功",dataNumber);
    }

    /**
     * 首页网站公告
     * 1.前端传入页码、分页数量
     * 2.查询分页数据
     * */
    @GetMapping("/news/index/{page}")
    @ResponseBody
    public ResultVo newsIndex(@PathVariable("page") Integer page){
        List<News> newsList = newsService.queryAllNews((page - 1) * 9, 9);
        return new ResultVo(true,StatusCode.OK,"查询成功",newsList);
    }

}