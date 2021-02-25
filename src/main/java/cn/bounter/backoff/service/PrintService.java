package cn.bounter.backoff.service;

import cn.bounter.backoff.entity.ResponseData;
import cn.bounter.backoff.entity.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 模拟打印服务
 */
@Slf4j
@Service
public class PrintService {

    public ResponseData printUser(User user) {
//        log.info("打印用户信息：{}", JSON.toJSONString(user));
//        return ResponseData.success();

        throw new RuntimeException("打印用户信息异常！");
    }

    public ResponseData ping() {
        log.info("收到连通性检查请求");
        return ResponseData.success();

//        throw new RuntimeException("网络异常！");
    }
}
