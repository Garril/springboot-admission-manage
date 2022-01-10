package com.admit.admit.controller.importInfo;

import com.admit.admit.entity.*;
import com.admit.admit.mapper.find.FindMapper;
import com.admit.admit.mapper.importInfo.ImportMapper;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/import")
public class ImportController {

    @Resource
    ImportMapper importMapper;

    @Resource
    FindMapper findMapper;

    // 2.1 导入录取信息
    @PostMapping("/stu")
    public String findStu(@RequestBody StuInfo stuInfo) {
        String dep_id = stuInfo.getDep_id();
        String spe_id = stuInfo.getSpe_id();
        String degree = stuInfo.getDegree();
        double maxRate=0.0;
        double rate;
        double boy;
        double girl;
        ClassInfo rightClass = null;


        // 拿到要分的班级列表
        List<ClassInfo> classInfos = importMapper.ForCreateSno(dep_id, spe_id, degree);
        for(ClassInfo classInfo: classInfos) {

            if(classInfo.getBoy()==null) {
                classInfo.setBoy("0");
                boy = 0;
            } else {
                boy = Double.parseDouble(classInfo.getBoy());
            }
            if(classInfo.getGirl()==null) {
                classInfo.setGirl("0");
                girl = 0;
            } else {
                girl = Double.parseDouble(classInfo.getGirl());
            }

            if(Integer.parseInt(stuInfo.getSex())==2) { // 是女孩
                if(stuInfo.getUrl()==null) {
                    stuInfo.setUrl("D:\\Front end\\DB_Project\\view\\admit\\src\\assets\\avatar\\2.png");
                }
                if(boy==0&&girl==0) {
                    rate = 0;
                } else {
                    rate = boy/girl; // 男女比例, 最大代表男孩子相对女孩子更多
                }
            } else { // 男孩
                if(stuInfo.getUrl()==null) {
                    stuInfo.setUrl("D:\\Front end\\DB_Project\\view\\admit\\src\\assets\\avatar\\1.png");
                }
                if(boy==0&&girl==0) {
                    rate = 0;
                } else {
                    rate = girl/boy; // 男女比例，最大代表女孩子相对男孩子更多
                }
            }
            if(maxRate <= rate) { // 求出最大的男女比例
                maxRate = rate;
                rightClass = classInfo; // 拿到合适的班级信息
            }
        }
        if(rightClass==null) {
            return "导入失败,不存在班级";
        }
        int count = Integer.parseInt(rightClass.getGirl())+Integer.parseInt(rightClass.getBoy())+1;
        String sno;
        if(count<=9) {
            sno = stuInfo.getDegree()+stuInfo.getSex()+ stuInfo.getYear().substring(2,4) +
                    stuInfo.getDep_id() + stuInfo.getSpe_id() + rightClass.getClass_no() + "0"+count;
        } else {
            sno = stuInfo.getDegree()+stuInfo.getSex()+ stuInfo.getYear().substring(2,4) +
                    stuInfo.getDep_id() + stuInfo.getSpe_id() + rightClass.getClass_no() + count;
        }
        try {
            importMapper.UpdateSno(stuInfo.getId(),sno,stuInfo.getName(),stuInfo.getDegree(),stuInfo.getSex(),stuInfo.getDep_id(),
                    stuInfo.getSpe_id(),rightClass.getId(),count,stuInfo.getYear(),stuInfo.getUrl());
        } catch (Exception e) {
            return "学生已经存在，导入学生信息失败";
        }
        return "导入学生信息成功";
    }

    @PostMapping("/class")
    public String findClass(@RequestBody ClassInfo classInfo) {
        try {
            importMapper.InsertNewClass(classInfo.getDegree(),classInfo.getYear(),classInfo.getDep_id(),classInfo.getSpe_id(),classInfo.getClass_no(),classInfo.getClass_name());
        } catch (Exception e) {
          return "班级已经存在，导入班级信息失败";
        }
        return "success";
    }

    // excel 导入学生
    @PostMapping("/addStudent")
    public String addStudent(MultipartFile file){
        try{
            List<Spe> allSpe = findMapper.findAllSpe();
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, Student.class,new com.admit.admit.listener.StudentListener(findMapper,new HashMap<String,String>(),new HashMap<String,Integer>(),allSpe,importMapper)).sheet().doRead();
        }catch (Exception e) {
            return e.getMessage();
        }
        return "导入学生成功";
    }

    // excel 导入班级
    @PostMapping("/addClass")
    public String addClass(MultipartFile file){
        try{
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ClassExcel.class,new com.admit.admit.listener.ClassListener(importMapper)).sheet().doRead();
        }catch (Exception e) {
            return e.getMessage();
        }
        return "导入班级成功";
    }


    @PostMapping("/avatar")
    public String regist(MultipartFile file) throws IOException {
        String url = "";
        if(file !=null) {
            String name = file.getOriginalFilename();
            InputStream in = file.getInputStream();
            File mkdir = new File("D:\\Front end\\DB_Project\\view\\admit\\src\\assets\\avatar");
            if(!mkdir.exists()) {
                mkdir.mkdirs();
            }
            //定义输出流，将文件写入硬盘
            url = (mkdir.getPath()+"\\"+name);
            FileOutputStream os = new FileOutputStream(url);
            int len =0;
            while( (len = in.read()) != -1) {
                os.write(len);
            }
            os.flush(); //关闭流
            in.close();
            os.close();
        }
        return url;
    }
}
