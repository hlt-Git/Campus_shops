package com.service;

import com.entity.Soldrecord;
import com.mapper.SoldrecordMapper;
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
public class SoldrecordService {
    @Autowired
    private SoldrecordMapper soldrecordMapper;

    /**插入售出记录*/
    public Integer insertSold(Soldrecord soldrecord){
        return soldrecordMapper.insertSold(soldrecord);
    }
    /**删除售出记录*/
    public Integer deleteSold(String id){
        return soldrecordMapper.deleteSold(id);
    }
    /**分页展示个人的售出记录*/
    public List<Soldrecord> queryAllSoldrecord(Integer page, Integer count, String userid){
        return soldrecordMapper.queryAllSoldrecord(page,count,userid);
    }
    /**查看售出记录总数*/
    public Integer querySoldCount(String userid){
        return soldrecordMapper.querySoldCount(userid);
    }
}
