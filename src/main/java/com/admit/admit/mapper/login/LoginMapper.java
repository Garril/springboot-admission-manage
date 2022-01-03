package com.admit.admit.mapper.login;

import org.apache.ibatis.annotations.Select;

public interface LoginMapper {

    @Select("select * from manager where id =#{id} and pw=#{pw}")
    String Login(String id, String pw);
}
