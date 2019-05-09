package com.an.friend.service;

import com.an.friend.dao.FriendDao;
import com.an.friend.dao.NoFriendDao;
import com.an.friend.eureka.UserEureka;
import com.an.friend.pojo.Friend;
import com.an.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserEureka userEureka;

    /**
     * 关注喜欢的用户
     * @param userid
     * @param friendid
     * @return
     */

    public int addFriend(String userid,String friendid){
        //判断用户已经添加了这个好友 则不进行任何操作 并且返回0
       if(friendDao.selectByUserCount(userid,friendid)>0){
           return  0;
       }

        //如果没有添加 向喜欢表中添加记录
        Friend friend=new Friend();
         friend.setUserid(userid);
         friend.setFriendid(friendid);
         friend.setIslike("0");
         friendDao.save(friend);
        //判断对方是否也喜欢你 如果喜欢 则双方isliske 设置为1
         if(friendDao.selectByUserCount(friendid,userid)>0){//如果大于0则friendID用户也关注了userID
             friendDao.updateLike(userid,friendid,"1");
             friendDao.updateLike(friendid,userid,"1");
         }
        //调用springcloud 微服务 修改用户表的关注数 粉丝数
          //首先是关注数+1 和粉丝数+1
         //user 修改关注数
          userEureka.incfollowCount(userid,1);
        //friendID 修改粉丝数
         userEureka.incfansCount(friendid,1);

        return 1;
    }


    /**
     * 取消喜欢
     * @param userid
     * @param friendid
     */
    public void notLikeFriend(String userid,String friendid){
        //删除用户数据
        friendDao.deleteByuseridAndfriendid(userid,friendid);
        //判断是否互相关注
        //如果互相互粉 则修改friendid用户中islike
        if (friendDao.selectByUserCount(friendid,userid)>0){
            friendDao.updateLike(friendid,userid,"0");
        }
         //调用springcloud 微服务 修改用户表的关注数 粉丝数
        //user 修改关注数
        userEureka.incfollowCount(userid,-1);
        //friendID 修改粉丝数
        userEureka.incfansCount(friendid,-1);

    }


    public void delete(String userid,String friendid) {
        //删除我关注的用户的那条记录
         friendDao.deleteByuseridAndfriendid(userid,friendid);
        //如果是互粉的情况下 拉黑的话 friendID islike改为0
        if (
            friendDao.selectByUserCount(friendid,userid)>0){
            friendDao.updateLike(friendid,userid,"0");
        }
        //tb_nofriend中插入一条记录
         NoFriend noFriend=new NoFriend();
           noFriend.setFriendid(friendid);
           noFriend.setUserid(userid);
           noFriendDao.save(noFriend);
        //调用springcloud 微服务 修改用户表的关注数 粉丝数
            userEureka.incfollowCount(userid,-1);
            userEureka.incfansCount(friendid,-1);
    }

}