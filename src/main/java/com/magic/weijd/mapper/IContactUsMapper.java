package com.magic.weijd.mapper;

import com.magic.weijd.entity.ContactUs;
import org.apache.ibatis.annotations.Mapper;

/**
 * 联系我们
 * @author lzh
 * @create 2017/9/14 17:13
 */
@Mapper
public interface IContactUsMapper {


    ContactUs info();

    void update(ContactUs contactUs);

}
