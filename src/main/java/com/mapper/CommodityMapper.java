package com.mapper;

import com.entity.Commodity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
public interface CommodityMapper {
    /**插入商品*/
    Integer InsertCommodity(Commodity commodity);
    /**查询商品详情*/
    Commodity LookCommodity(Commodity commodity);
    /**修改商品*/
    Integer ChangeCommodity(Commodity commodity);
    /**修改商品状态*/
    Integer ChangeCommstatus(@Param("commid") String commid, @Param("commstatus") Integer commstatus);
    /**通过商品名分页模糊查询*/
    List<Commodity> queryCommodityByName(@Param("page") Integer page, @Param("count") Integer count, @Param("commname") String commname);
    /**模糊查询商品总数*/
    Integer queryCommodityByNameCount(@Param("commname") String commname);
    /**分页展示各类状态的商品信息*/
    List<Commodity> queryAllCommodity(@Param("page") Integer page, @Param("count") Integer count, @Param("userid") String userid, @Param("commstatus") Integer commstatus);
    /**查询商品各类状态的总数*/
    Integer queryCommodityCount(@Param("userid") String userid, @Param("commstatus") Integer commstatus);
    /**首页分类展示8条商品*/
    List<Commodity> queryCommodityByCategory(@Param("category") String category);
    /**产品清单分类分页展示商品*/
    List<Commodity> queryAllCommodityByCategory(@Param("page") Integer page, @Param("count") Integer count, @Param("area") String area, @Param("school") String school,
                                                @Param("category") String category, @Param("minmoney") BigDecimal minmoney, @Param("maxmoney") BigDecimal maxmoney);
    /**查询产品清单分类分页展示商品的总数*/
    Integer queryAllCommodityByCategoryCount(@Param("area") String area, @Param("school") String school, @Param("category") String category,
                                             @Param("minmoney") BigDecimal minmoney, @Param("maxmoney") BigDecimal maxmoney);
}