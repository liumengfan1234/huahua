package com.an.friend.dao;

import com.an.friend.pojo.Friend;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String>, JpaSpecificationExecutor<Friend> {

    /**
     * 更新为互相喜欢
     */
    @Modifying
    @Query(nativeQuery = true,value = "update tb_friend set islike=?3 where userid=?1 and friendid=?2 ")
   public void updateLike(String userid,String friedid,String islike);

    /**
     * 效验用户是否
     */
    @Query(nativeQuery = true,value = "select count(1) from tb_friend where friendid=?2 and userid=?1")
    public int selectByUserCount(String userid,String friendid);

    @Modifying
    @Query(nativeQuery = true,value = "delete from tb_friend where userid=:userid and friendid=:friendid")
    void deleteByuseridAndfriendid(@Param("userid") String userid, @Param("friendid")String friendid);
}
