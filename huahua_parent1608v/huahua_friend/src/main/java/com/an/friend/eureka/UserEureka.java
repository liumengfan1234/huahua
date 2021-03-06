package com.an.friend.eureka;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("huahua-user")
public interface UserEureka {

    /**
     * 增加关注数(huahua-friend)
     */
    @PostMapping(value = "/user/incfollow/{userid}/{num}")
    public void incfollowCount(@PathVariable("userid")String userid, @PathVariable("num") Integer num);


    /**
     * 增加关注数(huahua-friend)
     */
    @PostMapping(value = "/user/incfans/{userid}/{num}")
    public void incfansCount(@PathVariable("userid")String userid,@PathVariable("num") Integer num);


}