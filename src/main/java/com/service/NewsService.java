package com.service;

import com.entity.News;
import com.mapper.NewsMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
@Service
@Transactional
public class NewsService {
    @Autowired
    private NewsMapper newsMapper;

    /**发布公告*/
    public Integer insertNews(News news){
        return newsMapper.insertNews(news);
    }

    /**删除公告*/
    public Integer delectNews(String id){
        return newsMapper.delectNews(id);
    }

    /**修改公告*/
    public Integer updateNews(News news){
        return newsMapper.updateNews(news);
    }

    /**查看公告详情*/
    public News queryNewsById(String id){
        return newsMapper.queryNewsById(id);
    }

    /**浏览量*/
    public void addNewsRednumber(String id){
        newsMapper.addNewsRednumber(id);
    }

    /**查询前三条公告*/
    public List<News> queryNews(){
        return newsMapper.queryNews();
    }

    /**分页展示公告信息*/
    public List<News> queryAllNews(@Param("page") Integer page, @Param("count") Integer count){
        return newsMapper.queryAllNews(page,count);
    }

    /**查找所有公告的总数*/
    public Integer LookNewsCount(){
        return newsMapper.LookNewsCount();
    }

}
