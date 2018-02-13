package com.magic.weijd.mapper;

import com.magic.weijd.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 城市 Mapper
 * Created by Eric Xie on 2017/7/12 0012.
 */
@Mapper
public interface ICityMapper {

    /**
     * 查询 省市级 下所有的 区县ID
     * @param cityId
     * @param levelType
     * @return
     */
    List<City> queryCityByLevel(@Param("cityId") Integer cityId, @Param("levelType") Integer levelType);

    /**
     *
     * @param cityName
     * @return
     */
    List<City> queryCityByItems(@Param("cityName") String cityName);

    List<City> queryAll();


    City queryCity(@Param("cityId") Integer cityId);


    /**
     * 查询城市的  市级ID  和 省级Id
     * @param id
     * @return
     */
    City queryCityId(@Param("id") Integer id);

    /**
     * 查询城市的  市级ID  和 省级Id
     * @param id
     * @return
     */
    City getThreeId(@Param("id") Integer id);


    /**
     * 通过ID 和 城市级别 查询城市
     * @param id
     * @param levelType
     * @return
     */
    List<City> queryCityByParentId(@Param("cityId") Integer cityId, @Param("levelType") Integer levelType);

    /**
     * 通过ID 和 城市级别 查询城市
     * @param cityIds
     * @param levelType
     * @return
     */
    List<City> queryCityByParentIds(@Param("cityIds") List<Integer> cityIds, @Param("levelType") Integer levelType);

}
