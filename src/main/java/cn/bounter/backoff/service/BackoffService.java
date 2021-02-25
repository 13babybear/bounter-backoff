package cn.bounter.backoff.service;

import cn.bounter.backoff.entity.BackoffRecord;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BackoffService extends IService<BackoffRecord> {

    void backoff(Long id);
}
