package cn.bounter.backoff.service.impl;

import cn.bounter.backoff.dao.BackoffMapper;
import cn.bounter.backoff.entity.BackoffRecord;
import cn.bounter.backoff.enums.BackoffStatusEnum;
import cn.bounter.backoff.service.BackoffService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 补偿服务
 */
@Slf4j
@Service
public class BackoffServiceImpl extends ServiceImpl<BackoffMapper, BackoffRecord> implements BackoffService {

    @Autowired
    private ApplicationContext applicationContext;


    /**
     * 补偿方法
     * @param id
     */
    @Override
    public void backoff(Long id) {
        //查询补偿记录
        BackoffRecord backoffRecord = getById(id);
        if (backoffRecord != null && backoffRecord.getStatus() == BackoffStatusEnum.N.getValue()) {
            //请求重放
            try {
                Class clazz = Class.forName(backoffRecord.getClassName());
                Object bean = applicationContext.getBean(clazz);
                if (backoffRecord.getParam() == null) {
                    Method method = clazz.getDeclaredMethod(backoffRecord.getMethodName());
                    method.invoke(bean);
                } else {
                    Class paramType = Class.forName(backoffRecord.getParamType());
                    Method method = clazz.getDeclaredMethod(backoffRecord.getMethodName(), paramType);
                    method.invoke(bean, JSON.parseObject(backoffRecord.getParam(), paramType));
                }
            } catch (Exception e) {
                log.warn("接口[{}#{}]执行补偿失败：", backoffRecord.getClassName(), backoffRecord.getMethodName(), e);
                throw new RuntimeException("执行补偿失败！");
            }

            //更新补偿状态为'补偿成功'
            updateById(backoffRecord.setStatus(BackoffStatusEnum.Y.getValue()).setUpdateTime(new Date()));
            log.info("接口[{}#{}]补偿成功", backoffRecord.getClassName() ,backoffRecord.getMethodName());
        }
    }
}
