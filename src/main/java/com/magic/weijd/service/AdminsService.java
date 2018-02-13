package com.magic.weijd.service;

import com.magic.weijd.entity.Admins;
import com.magic.weijd.entity.PageArgs;
import com.magic.weijd.entity.PageList;
import com.magic.weijd.entity.Suggest;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.mapper.IAdminsMapper;
import com.magic.weijd.util.StatusConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lzh
 * @create 2017/9/14 22:16
 */
@Service
public class AdminsService {

    @Resource
    private IAdminsMapper adminsMapper;

    /**
     * 登录
     * @param account
     * @param pwd
     */
    public Admins login(String account , String pwd) {
        Admins admins = adminsMapper.login(account);
        if (null == admins) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"没有此管理员");
        }
        if (!admins.getPwd().equals(pwd)) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"密码错误");
        }
        return admins;
    }

    /**
     * 详情
     * @param id
     */
    public Admins info(Integer id) {
        Admins admins = adminsMapper.info(id);
        if (null == admins) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"没有此管理员");
        }
        return admins;
    }


    public void save(Admins admins) {
        if (null != adminsMapper.login(admins.getAccount())) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_NOT_EXIST,"此账号已存在");
        }
        adminsMapper.save(admins);
    }

    public void update(Admins admins) {
        adminsMapper.update(admins);
    }

    /**
     * 后台页面 分页获取管理员
     * @param pageArgs 分页属性
     * @param userName 管理员姓名
     * @return
     */
    public PageList<Admins> listForAdmin(PageArgs pageArgs , String userName,String account) {
        PageList<Admins> pageList = new PageList<Admins>();
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("userName",userName);
        map.put("account",account);
        int count = adminsMapper.listForAdminCount(map);
        if (count > 0) {
            map.put("pageArgs",pageArgs);
            pageList.setList(adminsMapper.listForAdmin(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }
}
