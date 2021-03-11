package com.controller;

import com.entity.Soldrecord;
import com.service.SoldrecordService;
import com.util.StatusCode;
import com.vo.LayuiPageVo;
import com.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  销售记录控制器
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
@Controller
public class SoldrecordController {
    @Autowired
    private SoldrecordService soldrecordService;

    /**
     * 删除售出记录
     * 1.前端传入需删除记录的id（id）
     * 2.判断是否是本人
     * */
    @ResponseBody
    @PutMapping("/soldrecord/delect/{id}")
    public ResultVo delectSold (@PathVariable("id") String id) {
        Integer i = soldrecordService.deleteSold(id);
        if (i == 1){
            return new ResultVo(true, StatusCode.OK,"删除记录成功");
        }
        return new ResultVo(false, StatusCode.ERROR,"删除记录失败");
    }

    /**
     * 分页查看用户所有售出记录
     * 1.前端传入页码、分页数量
     * 2.查询分页数据
     */
    @ResponseBody
    @GetMapping("/soldrecord/lookuser")
    public LayuiPageVo LookUserSold(int limit, int page, HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        //如果未登录，给一个假id
        if(StringUtils.isEmpty(userid)){
            userid = "123456";
        }
        List<Soldrecord> soldrecordList = soldrecordService.queryAllSoldrecord((page - 1) * limit, limit, userid);
        Integer dataNumber = soldrecordService.querySoldCount(userid);
        return new LayuiPageVo("",0,dataNumber,soldrecordList);
    }

    /**
     * 分页查看全部的售出记录
     * 1.前端传入页码、分页数量
     * 2.查询分页数据
     */
    @ResponseBody
    @GetMapping("/soldrecord/queryall")
    public LayuiPageVo queryAllSold(int limit, int page) {
        List<Soldrecord> soldrecordList = soldrecordService.queryAllSoldrecord((page - 1) * limit, limit, null);
        Integer dataNumber = soldrecordService.querySoldCount(null);
        return new LayuiPageVo("",0,dataNumber,soldrecordList);
    }

}

