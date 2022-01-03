package com.admit.admit.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

// 班级excel，从左到右
@Data
public class ClassExcel {
    @ExcelProperty(index=0)
    private String spe_id;

    @ExcelProperty(index = 1)
    private String class_no;

    @ExcelProperty(index = 2)
    private String degree;

    @ExcelProperty(index = 3)
    private String class_name;

    @ExcelProperty(index = 4)
    private String dep_id;

    @ExcelProperty(index = 5)
    private String year;

}
