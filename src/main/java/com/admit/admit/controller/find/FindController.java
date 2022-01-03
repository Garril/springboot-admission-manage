package com.admit.admit.controller.find;

import com.admit.admit.entity.*;


import com.admit.admit.mapper.find.FindMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;


@RestController
@RequestMapping("/find")
public class FindController {

    @Resource
    FindMapper findMapper;

    // 3.1 获取sno学号/或者录取号为id的 学生的 所有信息，要求查询matriculated的所有内容（除了class_id）,以及class_no，class_name
    @GetMapping("/stu")
    public StuInfo findStu(@RequestParam String sno, @RequestParam boolean type) {
        // true表示用sno找，false表示用id找
        if(!type) {
            return findMapper.findStuById(sno);
        }
        return findMapper.findStuBySno(sno);
    }

    @GetMapping("/allStu")
    public List<StuInfo> findAllStu() {
        List<StuInfo> allStu = findMapper.findAllStu();
        for(StuInfo stuInfo:allStu) {
            if(stuInfo.getSex().equals("1")){
                stuInfo.setSex("男");
            } else {
                stuInfo.setSex("女");
            }
        }
        return allStu;
    }


    // 3.2.2 根据学院号和年级找到班级
    @GetMapping("/classByDep")
    public List<ClassInfo> findByD(@RequestParam String dep_id, @RequestParam String degree){
        if((dep_id.equals(" "))&&(degree.equals(" "))) {
            return findMapper.getAllClass();
        }
        return findMapper.findByDep(dep_id,degree);
    }

    // 3.3.1 拿到所有的专业和对应的学院id和名字
    @GetMapping("/allSpe")
    public List<Spe> findAllSpe() {
        List<Spe> allSpe = findMapper.findAllSpe();
        for(Spe spe : allSpe) {
            String[] split = spe.getSpe_names().split(",");
            spe.setSpe_arr(split);
        }
        return allSpe;
    }

    // 3.3.2 根据学院号和年级还有专业号找到班级
    @GetMapping("/classBySpe")
    public List<ClassInfo> findByS(@RequestParam String dep_id, @RequestParam String degree, @RequestParam String spe_id) {
        return findMapper.findBySpe(dep_id,degree,spe_id);
    }

    // 3.4 根据学号找到班级
    @GetMapping("/classBySno")
    public List<ClassInfo> findBySn(@RequestParam String sno){
        return findMapper.findBySno(sno);
    }


    // 统计分班信息改
    @GetMapping("/countByDepSpe")
    public ClassInfo findNumBySpe(@RequestParam String degree,@RequestParam String dep_id,@RequestParam String spe_id){
        if(spe_id.equals("@")) {
            return findMapper.findNumberDep(degree,dep_id);
        }
        return findMapper.findNumberSep(degree,dep_id,spe_id);
    }


    // 找到对应班级所有的学生，在查询分班信息里面用
    @GetMapping("/classAllStu")
    public List<StuInfo> findClassAllStu(@RequestParam String degree,
                                         @RequestParam String dep_id,
                                         @RequestParam String spe_id,
                                         @RequestParam String class_no) {
        return findMapper.findClassAllStu(degree,dep_id,spe_id,class_no);
    }

}
