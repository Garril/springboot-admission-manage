package com.admit.admit.mapper.find;


import com.admit.admit.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface FindMapper {

    // 3.1.1 sno查信息
    @Select("SELECT m.*,class.class_no,class.class_name,s.dep_name,s.spe_name,s.campus\n" +
            "FROM\n" +
            "(SELECT * FROM matriculated WHERE matriculated.sno = #{sno} ) AS m\n" +
            "LEFT JOIN class ON m.class_id = class.id\n" +
            "LEFT JOIN spe_dep s ON m.spe_id = s.spe_id AND m.dep_id = s.dep_id")
    StuInfo findStuBySno(String sno);

    // 3.1.2 id查信息
    @Select("SELECT m.*,class.class_no,class.class_name,s.dep_name,s.spe_name,s.campus\n" +
            "FROM\n" +
            "(SELECT * FROM matriculated WHERE matriculated.id = #{id} ) AS m\n" +
            "LEFT JOIN class ON m.class_id = class.id\n" +
            "LEFT JOIN spe_dep s ON m.spe_id = s.spe_id AND m.dep_id = s.dep_id\n")
    StuInfo findStuById(String id);


    // 3.2.2 按学院查询分班
    @Select("SELECT c.id,c.class_no,c.degree,c.class_name,c.year,s.dep_name,s.spe_name,s.spe_id,s.dep_id,\n" +
            "  COUNT(m.sex  <= 1 OR NULL) boy,\n" +
            "  COUNT(m.sex >= 2 OR NULL) girl\n" +
            "FROM (class AS c,matriculated AS m)\n" +
            "LEFT JOIN spe_dep s ON c.dep_id = s.dep_id AND c.spe_id = s.spe_id\n" +
            "WHERE c.degree = #{degree}\n" +
            "AND c.dep_id = #{dep_id}\n" +
            "AND c.id = m.class_id\n" +
            "GROUP BY c.id")
    @Transactional
    List<ClassInfo> findByDep(String dep_id, String degree);

    // 3.3.1 拿到所有的专业和对应的学院id和名字
    @Select("SELECT\n" +
            "  s.dep_id,d.name AS dep_name,\n" +
            "  GROUP_CONCAT(s.name ORDER BY s.spe_id) AS spe_names\n" +
            "FROM\n" +
            "  speciality s,department d\n" +
            "WHERE d.id = s.dep_id\n" +
            "GROUP BY d.id\n")
    List<Spe> findAllSpe();

    // 3.3.2 按专业查询分班
    @Select("SELECT c.id,c.class_no,c.degree,c.class_name,c.year,s.dep_name,s.spe_name,s.spe_id,s.dep_id,\n" +
            "  COUNT(m.sex  <= 1 OR NULL) boy,\n" +
            "  COUNT(m.sex >= 2 OR NULL) girl\n" +
            "FROM (class AS c,matriculated AS m)\n" +
            "LEFT JOIN spe_dep s ON c.dep_id = s.dep_id AND c.spe_id = s.spe_id\n" +
            "WHERE c.degree = #{degree}\n" +
            "AND c.dep_id = #{dep_id}\n" +
            "AND c.spe_id = #{spe_id}\n" +
            "AND c.id = m.class_id\n" +
            "GROUP BY c.id")
    @Transactional
    List<ClassInfo> findBySpe(String dep_id, String degree, String spe_id);

    // 3.4 学号查询分班信息
    @Select("  SELECT t.*,s.dep_name,s.spe_name\n" +
            "  FROM (SELECT class.*,\n" +
            "\tCOUNT(m.sex  <= 1 OR NULL) boy,\n" +
            "\tCOUNT(m.sex >= 2 OR NULL) girl\n" +
            "\tFROM class,matriculated m\n" +
            "\tWHERE class.id IN (SELECT class_id FROM matriculated WHERE sno = #{sno})\n" +
            "\tAND m.class_id = class.id\n" +
            "\tGROUP BY class.id) AS t,spe_dep AS s\n" +
            "  WHERE t.dep_id = s.dep_id\n" +
            "  AND t.spe_id = s.spe_id")
    @Transactional
    List<ClassInfo> findBySno(String sno);


    //    找到对应班级的所有学生，联级选项用到
    @Select("SELECT t.*,tt.spe_name,tt.dep_name,tt.campus\n" +
            "FROM (\n" +
            "\tSELECT m.*,class.class_no,class.class_name\n" +
            "\tFROM matriculated AS m,class\n" +
            "\tWHERE class.id = m.class_id\n" +
            "\tAND class.class_no = #{class_no}\n" +
            "\tAND m.degree = #{degree}\n" +
            "\tAND m.dep_id = #{dep_id}\n" +
            "\tAND m.spe_id = #{spe_id}\n" +
            "\t) AS t\n" +
            "LEFT JOIN spe_dep AS tt\n" +
            "ON tt.dep_id = t.dep_id AND tt.spe_id = t.spe_id")
    List<StuInfo> findClassAllStu(String degree, String dep_id, String spe_id, String class_no);


    // 这两个是计算人数的，统计分班情况用
//    @Select("SELECT degree,dep_id,spe_id,class_id,\n" +
//            "  COUNT(m.sex  <= 1 OR NULL) boy,\n" +
//            "  COUNT(m.sex >= 2 OR NULL) girl\n" +
//            "FROM matriculated m\n" +
//            "WHERE m.degree = #{degree}\n" +
//            "AND dep_id =#{dep_id}")
//    @Transactional
//    ClassInfo findNumberDep(String degree,String dep_id);
//
//    @Select("SELECT degree,dep_id,spe_id,class_id,\n" +
//            "  COUNT(m.sex  <= 1 OR NULL) boy,\n" +
//            "  COUNT(m.sex >= 2 OR NULL) girl\n" +
//            "FROM matriculated m\n" +
//            "WHERE m.degree = #{degree}\n" +
//            "AND dep_id =#{dep_id}\n" +
//            "AND spe_id = #{spe_id}")
//    @Transactional
//    ClassInfo findNumberSep(String degree,String dep_id,String spe_id);

    // 找到所有的班级，查询分班情况，页面一开始加载时候用
    @Select("SELECT t.*,spe_dep.dep_name,spe_dep.spe_name\n" +
            "    FROM (SELECT class.*,\n" +
            "      COUNT(m.sex  <= 1 OR NULL) boy,\n" +
            "      COUNT(m.sex >= 2 OR NULL) girl\n" +
            "    FROM class,matriculated m\n" +
            "    WHERE m.class_id = class.id\n" +
            "    GROUP BY class.id) AS t,spe_dep\n" +
            "    WHERE t.dep_id = spe_dep.dep_id\n" +
            "    AND spe_dep.spe_id = t.spe_id")
    List<ClassInfo> getAllClass();

    // 找到所有学生。界面初始显示
    @Select("SELECT m.*,class.class_no,class.class_name,s.dep_name,s.spe_name,s.campus\n" +
            "    FROM matriculated AS m,class,spe_dep AS s\n" +
            "    WHERE m.class_id = class.id\n" +
            "    AND m.dep_id = s.dep_id\n" +
            "    AND m.spe_id = s.spe_id \n")
    List<StuInfo> findAllStu();
}
