package com.admit.admit.controller.revise;

import com.admit.admit.entity.*;
import com.admit.admit.mapper.revise.ReviseMapper;
import com.admit.admit.util.ApiResult;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

@RestController
@RequestMapping("/revise")
public class ReviseController {
    @Resource
    ReviseMapper ReviseMapper;

    //4.1修改学号
    @PutMapping("/sno")
    public ApiResult reviseClassName(@RequestBody StuInfo stuInfo) {
        try {
            ReviseMapper.reviseSno(stuInfo);
        } catch (Exception e ) {
            return new ApiResult(400,"sno已经存在，修改失败，请重新修改",null);
        }
        return new ApiResult(200,"success",null);
    }

    //4.2修改班级代号名称
    @PutMapping("/class")
    public String reviseClassName(@RequestBody ClassInfo classInfo) {
        ReviseMapper.reviseClassName(classInfo);
        return "success";
    }
}
