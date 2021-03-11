package com.mapper;

import com.entity.Soldrecord;
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
public interface SoldrecordMapper {
    /**插入售出记录*/
    Integer insertSold(Soldrecord soldrecord);
    /**删除售出记录*/
    Integer deleteSold(String id);
    /**分页展示售出记录*/
    List<Soldrecord> queryAllSoldrecord(@Param("page") Integer page, @Param("count") Integer count, @Param("userid") String userid);
    /**查看售出记录总数*/
    Integer querySoldCount(@Param("userid") String userid);
}
