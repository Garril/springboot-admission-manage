package com.admit.admit.controller.delete;


import com.admit.admit.entity.ClassInfo;
import com.admit.admit.entity.StuInfo;
import com.admit.admit.mapper.delete.deleteStu;
import com.admit.admit.mapper.find.FindMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/delete")
public class DeleteStuController {//接收从前端获取的参数，设置接口相应的动作
    @Resource
    deleteStu deleteStu;

    @Resource
    FindMapper findMapper;

    @DeleteMapping("/stu")
    public String deleteBySno(@RequestBody StuInfo stuinfo) {
        StuInfo stuBySno = findMapper.findStuBySno(stuinfo.getSno());
        deleteStu.deleteBySno(stuinfo.getSno());
        deleteStu.updateNumber(stuBySno.getSno(),stuBySno.getNumber(),stuBySno.getClass_id());
        return "success";
    }
}
