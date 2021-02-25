package cn.bounter.backoff.service;

import cn.bounter.backoff.annotation.Backoffable;
import cn.bounter.backoff.entity.User;
import cn.bounter.backoff.entity.ResponseData;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * 模拟用户服务
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private PrintService printService;


    /**
     * 打印用户信息
     * @param user
     */
    @Retryable
    @Backoffable(User.class)
    public void print(User user) {
        log.info("请求打印服务开始，请求参数：{}", JSON.toJSONString(user));
        ResponseData responseData = printService.printUser(user);
        log.info("请求打印服务结束，返回结果：{}", JSON.toJSONString(responseData));
    }

    /**
     * 检查打印服务连通性
     */
    @Retryable
    @Backoffable
    public ResponseData pingPrintServer() {
        log.info("检查打印服务连通性开始");
        ResponseData responseData = printService.ping();
        log.info("检查打印服务连通性结束，返回结果：{}", JSON.toJSONString(responseData));
        return responseData;
    }
}
