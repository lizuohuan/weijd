package com.magic.weijd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 身份证计算年龄
 * @author lzh
 * @create 2017/9/19 11:41
 */
public class IdCardToAge {


    //根据身份证号输出年龄
    public static int idCardToAge(String idCard){
        int leh = idCard.length();
        String dates;
        if (leh == 18) {
            int se = Integer.valueOf(idCard.substring(leh - 1)) % 2;
            dates = idCard.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year) - Integer.parseInt(dates);
            return u;
        }else{
            dates = idCard.substring(6, 8);
            return Integer.parseInt(dates);
        }

    }

    public static void main(String[] args) {
        System.out.println(idCardToAge("510121199411120655")); //320621198804303

        System.out.println(new IdCardValidator.IdCardInfoExtractor("510121199411120655").getAge());
    }
}
