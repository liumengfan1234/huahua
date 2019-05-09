package com.an.friend.controller;

import com.an.friend.service.FriendService;
import huahua.common.Result;
import huahua.common.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FridendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 功能描述：添加关注或者取消关注
     * @param friendid 被关注的用户id
     * @param type 1 喜欢 2 不喜欢
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable("friendid") String friendid,@PathVariable("type") String type){
         //判断值是否存在
        Claims claims = (Claims) request.getAttribute("user_claims");
          if (null==claims){
              return new Result(false, StatusCode.AUTOROLES,"无权访问");
          }
        //判断type 类型是喜欢还是不喜欢
          //如果喜欢
        if (StringUtils.equals("1",type)){
             if (
                     friendService.addFriend(claims.getId(),friendid)==0){
                 return new Result(false,StatusCode.ERROR,"已关注用户");
             }
        }else{
            //取消喜欢
             friendService.notLikeFriend(claims.getId(),friendid);
        }
        //返回状态
        return new Result(false,StatusCode.ERROR,"操作成功");

    }

        /**
         * 不喜欢（拉黑）
         */
       @DeleteMapping("/{friendid}")
     public Result delete(@PathVariable("friendid") String friendid){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (null==claims){
            return new Result(false, StatusCode.AUTOROLES,"无权访问");
        }
        friendService.delete(claims.getId(),friendid);
        return new Result(false,StatusCode.ERROR,"操作成功");
     }
}