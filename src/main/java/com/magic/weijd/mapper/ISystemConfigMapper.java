package com.magic.weijd.mapper;

import com.magic.weijd.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置
 * @author lzh
 * @create 2017/12/25 19:01
 */
@Mapper
public interface ISystemConfigMapper {


    /**
     * 更新
     * @param systemConfig
     */
    void update(SystemConfig systemConfig);

    /**
     * 配置详情
     * @return
     */
    SystemConfig info();
}
