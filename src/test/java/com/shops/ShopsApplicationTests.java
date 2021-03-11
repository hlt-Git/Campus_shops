package com.shops;

import com.entity.Comment;
import com.entity.Commodity;
import com.entity.Reply;
import com.entity.UserInfo;
import com.service.*;
import com.util.PageLength;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopsApplicationTests {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CommimagesService commimagesService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询商品下的评论和回复
     */
    @Test
    @Transactional
    public void queryCommentReply() {
        /**查询评论*/
        List<Comment> commentsList = commentService.queryComments("1577792919764240135");
        for (Comment comment : commentsList) {
            /**查询对应评论下的回复*/
            List<Reply> repliesList = replyService.queryReply(comment.getCid());
            for (Reply reply : repliesList) {
                /**查询回复者的昵称和头像信息*/
                UserInfo ruser = userInfoService.queryPartInfo(reply.getRuserid());
                /**查询被回复者的昵称信息*/
                UserInfo cuser = userInfoService.queryPartInfo(reply.getCuserid());
                /**添加回复中涉及到的用户昵称及头像信息*/
                reply.setRusername(ruser.getUsername()).setRuimage(ruser.getUimage()).setCusername(cuser.getUsername());
            }
            /**查询评论者的昵称和头像信息*/
            UserInfo userInfo = userInfoService.queryPartInfo(comment.getCuserid());
            /**添加评论下的回复及评论者昵称和头像信息*/
            comment.setReplyLsit(repliesList).setCusername(userInfo.getUsername()).setCuimage(userInfo.getUimage());
        }
        System.out.println(commentsList);
    }


    /**
     * 首页分类展示商品 --> 按照分类查询商品
     * 首页展示最新发布商品 --> 查询最新发布的商品
     * 前端传入商品类别（category）
     */
    @Test
    @Transactional
    public void indexCommodity() {
        String category = "3C数码";
        List<Commodity> commodityList = commodityService.queryCommodityByCategory(category);
        for (Commodity commodity : commodityList) {
            /**查询商品对应的其它图片*/
            List<String> imagesList = commimagesService.LookGoodImages(commodity.getCommid());
            commodity.setOtherimg(imagesList);
        }
        System.out.println(commodityList);
    }

    /**
     * 产品清单界面
     * 前端传入商品类别（category）、当前页码（nowPaging）、区域（area）
     * 最低价（minmoney）、最高价（maxmoney）、价格升序降序（sequencetype：1.升序 2.降序）
     * 后端根据session查出个人本校信息（school）
     */
    @Test
    @GetMapping()
    public void productlisting() {
        String category = "全部";
        String area = "全部";
        String userid = "1582184795951594874";
        BigDecimal minmoney = new BigDecimal(1.00);
        BigDecimal maxmoney = new BigDecimal(1000.00);
        Integer page = 1;
        Integer price = 0;


        UserInfo userInfo = userInfoService.LookUserinfo(userid);
        String school = userInfo.getSchool();
        List<Commodity> commodityList = commodityService.queryAllCommodityByCategory((page - 1) * 10, 10, area, school, category, minmoney, maxmoney);
        Integer dataNumber = commodityService.queryAllCommodityByCategoryCount(area, school, category, minmoney, maxmoney);
        Integer pages = PageLength.getPages(dataNumber, 10);

        System.out.println("排序前的商品为：");
        for (Commodity commodity : commodityList) {
            System.out.println(commodity);
        }

        Collections.sort(commodityList, new Comparator<Commodity>() {//此处创建了一个匿名内部类
            int i;
//            /**升序*/
//            @Override
//            public int compare(Commodity o1, Commodity o2) {
//                if (o1.getThinkmoney().compareTo(o2.getThinkmoney()) > -1) {
//                    System.out.println("===o1大于等于o2===");
//                    i = 1;
//                } else if (o1.getThinkmoney().compareTo(o2.getThinkmoney()) < 1) {
//                    i = -1;
//                    System.out.println("===o1小于等于o2===");
//                }
//                return i;
//            }
            /**降序*/
            @Override
            public int compare(Commodity o1, Commodity o2) {
                if (o1.getThinkmoney().compareTo(o2.getThinkmoney()) > -1) {
                    System.out.println("===o1大于等于o2===");
                    i = -1;
                } else if (o1.getThinkmoney().compareTo(o2.getThinkmoney()) < 1) {
                    System.out.println("===o1小于等于o2===");
                    i = 1;
                }
                return i;
            }
        });

        System.out.println("排序后的商品为：");
        for (Commodity commodity : commodityList) {
            System.out.println(commodity);
        }

        System.out.println("查询的学校为：" + school);
        System.out.println("查询的商品分页为：" + pages);
        System.out.println("查询的商品数量为：" + dataNumber);
    }

}
