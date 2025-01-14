package com.zcy.nls.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.zcy.nls.business.domain.Demo;
import com.zcy.nls.business.domain.DemoExample;
import com.zcy.nls.business.exception.BusinessException;
import com.zcy.nls.business.exception.BusinessExceptionEnum;
import com.zcy.nls.business.mapper.DemoMapper;
import com.zcy.nls.business.mapper.cust.DemoMapperCust;
import com.zcy.nls.business.req.DemoQueryReq;
import com.zcy.nls.business.resp.DemoQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Resource
    private DemoMapperCust demoMapperCust;

    @Resource
    private DemoMapper demoMapper;

    public int count() {
//        return demoMapperCust.count();
        return Math.toIntExact(demoMapper.countByExample(null));
    }

    public List<DemoQueryResp> query(DemoQueryReq req) {
        String mobile = req.getMobile();
        DemoExample demoExample = new DemoExample();
        demoExample.setOrderByClause("id desc");
        DemoExample.Criteria criteria = demoExample.createCriteria();
//        if (mobile != null) {
//            criteria.andMobileEqualTo(mobile);
//        }
        if (StrUtil.isBlank(mobile)) {
            throw new BusinessException(BusinessExceptionEnum.DEMO_MOBILE_NOT_NULL);
        }
        criteria.andMobileEqualTo(mobile);
        List<Demo> demoList = demoMapper.selectByExample(demoExample);
        return BeanUtil.copyToList(demoList, DemoQueryResp.class);

    }
}
