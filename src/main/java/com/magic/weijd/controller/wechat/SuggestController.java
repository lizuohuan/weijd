package com.magic.weijd.controller.wechat;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.PageArgs;
import com.magic.weijd.entity.PageList;
import com.magic.weijd.entity.Suggest;
import com.magic.weijd.entity.User;
import com.magic.weijd.service.SuggestService;
import com.magic.weijd.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 意见反馈
 * @author lzh
 * @create 2017/8/3 10:17
 */
@RestController("WechatSuggestController")
@RequestMapping("/wechat/suggest")
public class SuggestController extends BaseController {


    @Resource
    private SuggestService suggestService;

    /**
     * 提交意见反馈
     * @param suggest
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ViewData save(Suggest suggest){
        if(CommonUtil.isEmpty(suggest.getPhone()) || CommonUtil.isEmpty(suggest.getContent()) ||
                CommonUtil.isEmpty(suggest.getUserName())){
            return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
        }
        try {
            suggestService.addSuggest(suggest);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return buildFailureJson(StatusConstant.Fail_CODE,"提交失败");
        }
        return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"提交成功");
    }



}
