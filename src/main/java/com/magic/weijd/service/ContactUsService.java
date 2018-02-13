package com.magic.weijd.service;

import com.magic.weijd.entity.ContactUs;
import com.magic.weijd.mapper.IContactUsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 联系我们
 * @author lzh
 * @create 2017/9/14 17:34
 */
@Service
public class ContactUsService {

    @Resource
    private IContactUsMapper contactUsMapper;

    public void update(ContactUs contactUs){
        contactUsMapper.update(contactUs);
    }


    public ContactUs info(){
        return contactUsMapper.info();
    }
}
