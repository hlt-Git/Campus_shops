package com.mapper;

import com.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
public interface NewsMapper {
    /**发布公告*/
    Integer insertNews(News news);
    /**删除公告*/
    Integer delectNews(String id);
    /**修改公告*/
    Integer updateNews(News news);
    /**查看公告详情*/
    News queryNewsById(String id);
    /**浏览量*/
    void addNewsRednumber(String id);
    /**查询前三条公告*/
    List<News> queryNews();
    /**分页展示公告信息*/
    List<News> queryAllNews(@Param("page") Integer page, @Param("count") Integer count);
    /**查找所有公告的总数*/
    Integer LookNewsCount();
}
