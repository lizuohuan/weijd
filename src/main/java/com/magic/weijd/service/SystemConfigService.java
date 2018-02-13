package com.magic.weijd.service;

import com.magic.weijd.entity.SystemConfig;
import com.magic.weijd.mapper.ISystemConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统配置
 * @author lzh
 * @create 2017/12/25 19:06
 */
@Service
public class SystemConfigService {

    @Resource
    private ISystemConfigMapper systemConfigMapper;


    public SystemConfig info() {
        return systemConfigMapper.info();
    }

    public void update(SystemConfig systemConfig) {
        systemConfigMapper.update(systemConfig);
    }
}
