package com.an.qa.eureka;

import huahua.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("huahua-base")  //调用的是那一个微服务
public interface LabelEureke {

    //value要写全路径
    @RequestMapping(method = RequestMethod.GET,value = "/label/{labelId}")
    public Result queryById(@PathVariable("labelId") String labelId);

}