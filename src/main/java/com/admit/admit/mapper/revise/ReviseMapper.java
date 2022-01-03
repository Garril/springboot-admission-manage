package com.admit.admit.mapper.revise;



import com.admit.admit.entity.ClassInfo;
import com.admit.admit.entity.StuInfo;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

public interface ReviseMapper {
    //4.1修改学号
    @Update("UPDATE matriculated SET sno = #{id} WHERE sno = #{sno};")
    @Transactional
    void reviseSno(StuInfo stuInfo);
    //4.2修改班级代号名称
    @Update("UPDATE class SET class_name = #{class_name} WHERE id = #{id};")
    @Transactional
    void reviseClassName(ClassInfo classInfo);
}
