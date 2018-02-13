package com.magic.weijd.mapper;

import com.magic.weijd.entity.Admins;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 后台管理
 * @author lzh
 * @create 2017/9/14 18:03
 */
@Mapper
public interface IAdminsMapper {


    Admins login(@Param("account") String account);

    void update(Admins admins);

    void save(Admins admins);

    Admins info(@Param("id") Integer id);

    List<Admins> listForAdmin(Map<String, Object> map);

    int listForAdminCount(Map<String, Object> map);



}
