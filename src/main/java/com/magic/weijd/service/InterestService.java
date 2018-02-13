package com.magic.weijd.service;

import com.magic.weijd.entity.Interest;
import com.magic.weijd.mapper.IInterestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 利率
 * @author lzh
 * @create 2017/9/12 20:36
 */
@Service
public class InterestService {

    @Resource
    private IInterestMapper interestMapper;

    public void save(Interest interest) {
        interestMapper.save(interest);
    }

    public void update(Interest interest) {
        interestMapper.update(interest);
    }

    public List<Interest> list() {
        return interestMapper.list();
    }
}
