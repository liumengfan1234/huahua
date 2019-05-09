package com.an.friend.dao;

import com.an.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoFriendDao extends JpaRepository<NoFriend,String>,JpaSpecificationExecutor<NoFriend> {
}