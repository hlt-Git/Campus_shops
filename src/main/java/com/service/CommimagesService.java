package com.service;

import com.entity.Commimages;
import com.mapper.CommimagesMapper;
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
public class CommimagesService {
    @Autowired
    private CommimagesMapper commimagesMapper;

    /**插入商品的其他图*/
    public void InsertGoodImages(List<Commimages> list){
        commimagesMapper.InsertGoodImages(list);
    }
    /**查询某个商品得其他图*/
    public List<String> LookGoodImages(String commid){
        return commimagesMapper.LookGoodImages(commid);
    }
    /**删除某个商品得其他图*/
    public void DelGoodImages(String commid){
        commimagesMapper.DelGoodImages(commid);
    }
}
