package com.admit.admit.mapper.importInfo;

import com.admit.admit.entity.ClassInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImportMapper {

    // 2.1 导入录取信息
    // 2.1.1
    @Select("SELECT class.*,boy.count AS boy,girl.count AS girl\n" +
            "FROM class\n" +
            "\n" +
            "LEFT JOIN \n" +
            "(SELECT class_id,COUNT(*) AS `count` \n" +
            "FROM matriculated m\n" +
            "WHERE class_id IN (SELECT id FROM class WHERE class.dep_id = #{dep_id} AND class.spe_id = #{spe_id} AND degree = #{degree})\n" +
            "AND sex = 1\n" +
            "GROUP BY class_id) AS boy\n" +
            "ON boy.class_id = class.id\n" +
            "\n" +
            "LEFT JOIN\n" +
            "(SELECT class_id,COUNT(*) AS `count` \n" +
            "FROM matriculated m\n" +
            "WHERE class_id IN (SELECT id FROM class WHERE class.dep_id = #{dep_id} AND class.spe_id = #{spe_id} AND degree =#{degree})\n" +
            "AND sex = 2\n" +
            "GROUP BY class_id) AS girl\n" +
            "ON girl.class_id = class.id\n" +
            "\n" +
            "WHERE class.dep_id = #{dep_id} AND class.spe_id = #{spe_id} AND degree =#{degree}")
    List<ClassInfo> ForCreateSno(String dep_id, String spe_id, String degree);

    @Insert("INSERT INTO matriculated(id,sno,NAME,degree,sex,dep_id,spe_id,class_id,number,YEAR) " +
            "VALUES(#{id},#{sno},#{name},#{degree},#{sex},#{dep_id},#{spe_id},#{class_id},#{number},#{year})")
    @Transactional
    int UpdateSno(String id,String sno,String name,String degree,String sex,String dep_id,String spe_id,String class_id,int number,String year);

    @Insert("INSERT INTO avatar(sno,url) values(#{sno},#{url})")
    void InsertAvatar(String sno, String url);

    @Insert("INSERT INTO class(degree,YEAR,dep_id,spe_id,class_no,class_name) VALUE(#{degree},#{year},#{dep_id},#{spe_id},#{class_no},#{class_name})")
    void InsertNewClass(String degree, String year, String dep_id, String spe_id, String class_no, String class_name);

    @Insert("INSERT INTO matriculated(id,sno,NAME,degree,sex,dep_id,spe_id,class_id,number,YEAR) VALUES(#{id},#{sno},#{name},#{degree},#{sex},#{dep_id},#{spe_id},#{class_id},#{number},#{year});")
    @Transactional
    int insertst(String id,String name,String degree,String sex,String dep_id,String spe_id,String year,String sno,String class_id,int number);

    @Insert("INSERT INTO class(spe_id,class_no,degree,class_name,dep_id,YEAR) VALUES (#{spe_id},#{class_no},#{degree},#{class_name},#{dep_id},#{year});")
    @Transactional
    void insertClass(String spe_id, String class_no, String degree, String class_name, String dep_id, String year);
}
