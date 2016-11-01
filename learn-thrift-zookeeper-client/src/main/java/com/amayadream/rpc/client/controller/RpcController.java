package com.amayadream.rpc.client.controller;

import com.alibaba.fastjson.JSON;
import com.amayadream.rpc.client.HelloServiceClient;
import com.amayadream.rpc.client.util.WatchSayHello;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 17:41
 */
@Controller
@RequestMapping(value = "/hello")
public class RpcController {

    @RequestMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String list() {
        return JSON.toJSONString(WatchSayHello.sayHelloList);
    }

    @RequestMapping(value = "/call", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String call(String ip, String port, String params, HttpServletRequest request, HttpServletResponse response) {
        HelloServiceClient client = new HelloServiceClient();
        client.sayHello(ip, Integer.valueOf(port), params);
        return "success";
    }

}
