package com.admit.admit.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

//录取通知书里面的列，从左到右依次是录取号，姓名，省份，科类，专业，学院，校区
@Data
public class Student {
    @ExcelProperty(index=0)
    private String number;

    @ExcelProperty(index = 1)
    private String name;

    @ExcelProperty(index = 2)
    private String province;

    @ExcelProperty(index = 3)
    private String type;

    @ExcelProperty(index = 4)
    private String spe;

    @ExcelProperty(index = 5)
    private String dep;

    @ExcelProperty(index = 6)
    private String capms;
}
