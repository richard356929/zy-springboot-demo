package com.zcy.nls.business.controller;

import com.zcy.nls.business.req.DemoQueryReq;
import com.zcy.nls.business.resp.CommonResp;
import com.zcy.nls.business.resp.DemoQueryResp;
import com.zcy.nls.business.service.DemoService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Resource
    private DemoService demoService;

    @GetMapping("/hello")
    public CommonResp<String> hello() {
        return new CommonResp<>("hello");
    }

    @GetMapping("/count")
    public CommonResp<Integer> count(){
        return new CommonResp<>(demoService.count());
    }

    @GetMapping("/query")
    public CommonResp<List<DemoQueryResp>> query(@Valid DemoQueryReq req){
        List<DemoQueryResp> demoList = demoService.query(req);
        return new CommonResp<>(demoList);

    }

}
