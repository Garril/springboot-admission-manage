package com.admit.admit.entity;
import lombok.Data;

@Data
public class StuInfo {
    private String id;
    private String sno;
    private String name;
    private String degree;
    private String sex;
    private String dep_id;
    private String spe_id;
    private String class_id;
    private String number;
    private String year;
    private String dep_name;
    private String spe_name;
    private String class_name;
    private String campus;
    private String url;
    private String class_no;

    public StuInfo(String id, String name, String degree, String sex, String dep_id, String spe_id, String year) {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.sex = sex;
        this.dep_id = dep_id;
        this.spe_id = spe_id;
        this.year = year;
    }
}
