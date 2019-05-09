package com.fy.flower.sample.service;

import com.alibaba.fastjson.JSONObject;
import com.fy.flower.sample.util.R;
import com.fy.flower.sample.util.Response;
import com.ly.train.flower.common.annotation.FlowerService;
import com.ly.train.flower.common.annotation.FlowerType;
import com.ly.train.flower.common.service.Complete;
import com.ly.train.flower.common.service.container.ServiceContext;
import com.ly.train.flower.common.service.impl.AbstractService;
import com.ly.train.flower.common.service.web.Flush;
import com.ly.train.flower.common.service.web.HttpComplete;

import java.util.List;

@FlowerService(type = FlowerType.AGGREGATE)
public class EndService extends AbstractService<List<Object>, Object> implements Flush, HttpComplete, Complete {

    @Override
    public Object doProcess(List<Object> message, ServiceContext context) throws Throwable {
        Response<List<Object>> res = R.ok(message);
        String ret = JSONObject.toJSONString(res, true);
        context.getWeb().printJSON(ret);
        return null;
    }

}
