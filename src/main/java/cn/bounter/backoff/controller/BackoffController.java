package cn.bounter.backoff.controller;

import cn.bounter.backoff.entity.ResponseData;
import cn.bounter.backoff.service.BackoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/backoff")
@RestController
public class BackoffController {

    @Autowired
    private BackoffService backoffService;


    @PostMapping
    public ResponseData backoff(Long id) {
        try {
            backoffService.backoff(id);
            return ResponseData.success();
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }
}
