package cn.bounter.backoff.controller;

import cn.bounter.backoff.entity.ResponseData;
import cn.bounter.backoff.entity.User;
import cn.bounter.backoff.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/print")
    public ResponseData userPrint(User user) {
        try {
            userService.print(user);
            return ResponseData.success();
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("/ping")
    public ResponseData ping() {
        try {
            return userService.pingPrintServer();
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }
}
