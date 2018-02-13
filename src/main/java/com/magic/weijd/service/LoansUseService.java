package com.magic.weijd.service;

import com.magic.weijd.entity.LoansUse;
import com.magic.weijd.mapper.ILoansUseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 借款用途
 * @author lzh
 * @create 2017/9/13 9:50
 */
@Service
public class LoansUseService {

    @Resource
    private ILoansUseMapper loansUseMapper;

    public void save(LoansUse loansUse) {
        loansUseMapper.save(loansUse);
    }

    public void update(LoansUse loansUse) {
        loansUseMapper.update(loansUse);
    }

    public List<LoansUse> list() {
        return loansUseMapper.list();
    }

}
