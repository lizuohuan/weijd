package com.magic.weijd.service;

import com.magic.weijd.entity.RepaymentMethod;
import com.magic.weijd.mapper.IRepaymentMethodMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 还款方式
 * @author lzh
 * @create 2017/9/13 9:39
 */
@Service
public class RepaymentMethodService {

    @Resource
    private IRepaymentMethodMapper repaymentMethodMapper;

    public void save(RepaymentMethod interest) {
        repaymentMethodMapper.save(interest);
    }

    public void update(RepaymentMethod interest) {
        repaymentMethodMapper.update(interest);
    }

    public List<RepaymentMethod> list() {
        return repaymentMethodMapper.list();
    }
}
