package com.admit.admit.mapper.delete;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;


public interface deleteStu {
    @Update("UPDATE matriculated SET number =number-1,sno =sno-1 \n" +
            "WHERE number >#{number} AND class_id =#{class_id} ")
    @Transactional
    void updateNumber(String sno,String number,String class_id);

    @Delete("delete from matriculated where sno = #{sno}")
    void deleteBySno(String sno);
}

