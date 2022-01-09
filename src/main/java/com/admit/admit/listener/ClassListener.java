package com.admit.admit.listener;

import com.admit.admit.entity.*;
import com.admit.admit.mapper.find.FindMapper;
import com.admit.admit.mapper.importInfo.ImportMapper;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


//监听，在这里插入学生

public class ClassListener extends AnalysisEventListener<ClassExcel> {


    private ImportMapper importMapper;


    public ClassListener(ImportMapper importMapper) {
        this.importMapper = importMapper;
    }


    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }


    @Override
    public void invoke(ClassExcel classExcel, AnalysisContext analysisContext) {
        try {
            this.importMapper.insertClass(classExcel.getSpe_id(),classExcel.getClass_no(),classExcel.getDegree(),classExcel.getClass_name(),classExcel.getDep_id(),classExcel.getYear());
        } catch(Exception e) {
//            System.out.println(e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
