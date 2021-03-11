package com.service;

import com.entity.Commodity;
import com.mapper.CommodityMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
public class CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;

    /**插入商品*/
    @Async
    public Integer InsertCommodity(Commodity commodity){
        return commodityMapper.InsertCommodity(commodity);
    }
    /**查询商品详情*/
    public Commodity LookCommodity(Commodity commodity){
        return commodityMapper.LookCommodity(commodity);
    }
    /**修改商品*/
    public Integer ChangeCommodity(Commodity commodity){
        return commodityMapper.ChangeCommodity(commodity);
    }
    /**修改商品状态*/
    public Integer ChangeCommstatus(String commid,Integer commstatus){
        return commodityMapper.ChangeCommstatus(commid,commstatus);
    }
    /**通过商品名分页模糊查询*/
    public List<Commodity> queryCommodityByName(Integer page,Integer count,String commname){
        return commodityMapper.queryCommodityByName(page,count,"%"+commname+"%");
    }
    /**模糊查询商品总数*/
    public Integer queryCommodityByNameCount(String commname){
        return commodityMapper.queryCommodityByNameCount("%"+commname+"%");
    }
    /**分页展示各类状态的商品信息*/
    public List<Commodity> queryAllCommodity(Integer page,Integer count,String userid,Integer commstatus){
        return commodityMapper.queryAllCommodity(page,count,userid,commstatus);
    }
    /**查询商品各类状态的总数*/
    public Integer queryCommodityCount(String userid,Integer commstatus){
        return commodityMapper.queryCommodityCount(userid,commstatus);
    }
    /**首页分类展示8条商品*/
    public List<Commodity> queryCommodityByCategory(String category){
        return commodityMapper.queryCommodityByCategory(category);
    }
    /**产品清单分类分页展示商品*/
    public List<Commodity> queryAllCommodityByCategory(Integer page,Integer count,String area,String school,String category,BigDecimal minmoney,BigDecimal maxmoney){
        return commodityMapper.queryAllCommodityByCategory(page,count,area,school,category,minmoney,maxmoney);
    }
    /**查询产品清单分类分页展示商品的总数*/
    public Integer queryAllCommodityByCategoryCount(String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney){
        return commodityMapper.queryAllCommodityByCategoryCount(area,school,category,minmoney,maxmoney);
    }
}
