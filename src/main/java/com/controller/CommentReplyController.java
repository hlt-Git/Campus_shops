package com.controller;


import com.entity.*;
import com.service.*;
import com.util.KeyUtil;
import com.util.StatusCode;
import com.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  评论和回复控制器
 * </p>
 *
 * @author hlt
 * @since 2019-12-21
 */
@Controller
public class CommentReplyController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private NoticesService noticesService;

    /**
     * 查询商品下的评论和回复
     * */
    @ResponseBody
    @GetMapping("/comment/query/{commid}")
    public ResultVo queryCommentReply(@PathVariable("commid") String commid){
        /**查询评论*/
        List<Comment> commentsList = commentService.queryComments(commid);
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
            System.out.println("评论者ID：" + comment.getCuserid());
            UserInfo userInfo = userInfoService.queryPartInfo(comment.getCuserid());
            /**添加评论下的回复及评论者昵称和头像信息*/
            comment.setReplyLsit(repliesList).setCusername(userInfo.getUsername()).setCuimage(userInfo.getUimage());
        }
        return new ResultVo(true, StatusCode.OK,"查询评论回复成功",commentsList);
    }

    /**
     * 评论
     * 1.前端传入：商品id（commid）、商品发布者id（spuserid）、评论内容（content）
     * 2.session获取：评论者id（cuserid）
     * 3.过滤评论内容后，插入评论
     */
    @ResponseBody
    @PostMapping("/comment/insert")
    public ResultVo insertcomment(@RequestBody Comment comment,HttpSession session) {
        String cuserid = (String) session.getAttribute("userid");
        String content = comment.getContent();

        if (StringUtils.isEmpty(cuserid)) {
            return new ResultVo(false,StatusCode.ACCESSERROR,"请登录后再评论");
        }
        content = content.replace("<", "&lt;");
        content = content.replace(">", "&gt;");
        content = content.replace("'", "\"");

        comment.setCid(KeyUtil.genUniqueKey()).setCuserid(cuserid).setContent(content);
        /**插入评论*/
        Integer i = commentService.insertComment(comment);
        if (i == 1){
            /**发出评论通知消息*/
            Commodity commodity = commodityService.LookCommodity(new Commodity().setCommid(comment.getCommid()));
            Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(comment.getSpuserid()).setTpname("评论")
                    .setWhys("您的商品 <a href=/product-detail/"+comment.getCommid()+" style=\"color:#08bf91\" target=\"_blank\" >"+commodity.getCommname()+"</a> 被评论了，快去看看吧。");
            noticesService.insertNotices(notices);
            return new ResultVo(true, StatusCode.OK,"评论成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"评论失败");
    }

    /**
     * 评论回复
     * 1.前端传入：商品id（commid）、评论id（cid）、被回复用户id（cuserid）、商品发布者id（spuserid）、评论内容（recontent）
     * 2.session获取：回复者id（ruserid）
     * 3.过滤评论回复内容后，插入评论回复
     */
    @ResponseBody
    @PostMapping("/reply/insert")
    public ResultVo insertreply(@RequestBody Reply reply,HttpSession session) {
        String ruserid = (String) session.getAttribute("userid");
        String recontent = reply.getRecontent();

        if (StringUtils.isEmpty(ruserid)) {
            return new ResultVo(false,StatusCode.ACCESSERROR,"请登录后再评论");
        }

        recontent = recontent.replace("<", "&lt;");
        recontent = recontent.replace(">", "&gt;");
        recontent = recontent.replace("'", "\"");

        reply.setRid(KeyUtil.genUniqueKey()).setRuserid(ruserid).setRecontent(recontent);
        /**插入回复*/
        Integer i = replyService.insetReply(reply);
        if (i == 1){
            /**发出评论回复通知消息*/
            Commodity commodity = commodityService.LookCommodity(new Commodity().setCommid(reply.getCommid()));
            Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(reply.getCuserid()).setTpname("评论回复")
                    .setWhys("有小伙伴在 <a href=/product-detail/"+reply.getCommid()+" style=\"color:#08bf91\" target=\"_blank\" >"+commodity.getCommname()+"</a> 下回复了您的评论，快去看看吧。");
            noticesService.insertNotices(notices);
            return new ResultVo(true, StatusCode.OK,"回复成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"回复失败");
    }

    /**
     * 删除评论
     * 1.获取session中用户id信息
     * 2.对比用户id信息和评论者id信息：是否满足评论者本人或商品发布者
     * 3.删除评论及评论对应的回复
     */
    @ResponseBody
    @PutMapping("/comment/delete/{cid}")
    public ResultVo deletecomment(@PathVariable("cid") String cid,HttpSession session){
        String cuserid = (String) session.getAttribute("userid");
        Comment comment = commentService.queryById(cid);
        /**如果是评论者本人或者商品发布者则进行删除操作*/
        if (comment.getCuserid().equals(cuserid) || comment.getSpuserid().equals(cuserid)){
            Integer i = commentService.deleteComment(cid);
            Integer j = replyService.deleteReply(new Reply().setCid(cid));
            if (i == 1 && j == 1){
                return new ResultVo(true, StatusCode.OK,"删除成功");
            }
            return new ResultVo(false, StatusCode.ERROR,"删除失败");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"禁止操作");
    }

    /**
     * 删除评论回复
     * 1.获取session中用户id信息
     * 2.对比用户id信息和回复者id信息：是否满足评论回复者本人或商品发布者
     * 3.进行删除操作
     */
    @ResponseBody
    @PutMapping("/reply/delete/{rid}")
    public ResultVo deletereply(@PathVariable("rid") String rid,HttpSession session){
        String ruserid = (String) session.getAttribute("userid");
        Reply reply = replyService.queryById(rid);
        /**如果是回复者本人或者商品发布者则进行删除操作*/
        if (reply.getRuserid().equals(ruserid) || reply.getSpuserid().equals(ruserid)){
            Integer i = replyService.deleteReply(new Reply().setRid(rid));
            if (i == 1){
                return new ResultVo(true, StatusCode.OK,"删除成功");
            }
            return new ResultVo(false, StatusCode.ERROR,"删除失败");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"禁止操作");
    }

}

