package com.admit.admit.controller.login;

import com.admit.admit.mapper.login.LoginMapper;
import com.admit.admit.util.ApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    LoginMapper loginMapper;

    @PostMapping
    public ApiResult login(@RequestBody com.admit.admit.entity.Login login) {
        String id = login.getId();
        String pw = login.getPw();
        if(loginMapper.Login(id,pw) != null) {
            return ApiResult.buildApiResult(200, "请求成功", "success");
        }
        return ApiResult.buildApiResult(400, "请求失败", null);
    }

}
