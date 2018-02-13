package com.magic.weijd.util;

/**
 * 计算利息
 * @author lzh
 * @create 2017/9/19 18:56
 */
public class ComputeAccrual {

    /**
     * 计算日利息+本金
     * @param price 本金
     * @param interestRate 年利率
     * @param yearDays 借款那年的天数
     * @param day 借的天数
     * @return
     */
    public static Double computeAccrualDay(Double price,Double interestRate ,Integer yearDays , Integer day) {
        price = price + (price * interestRate / yearDays);
        day = day - 1;
        if (day > 0) {
            price = computeAccrualDay(price,interestRate,yearDays,day);
        }
        return price;
    }

    /**
     * 计算日利息
     * @param price 本金
     * @param interestRate 年利率
     * @param yearDays 借款那年的天数
     * @param day 借的天数
     * @return
     */
    public static Double computeAccrualDayInterest(Double price,Double interestRate ,Integer yearDays , Integer day ,Double interest) {
        price = price + (price * interestRate / yearDays);
        interest = interest + (price * interestRate / yearDays);
        day = day - 1 ;
        if (day > 0) {
            interest = computeAccrualDayInterest(price,interestRate,yearDays,day,interest);
        }
        return interest;
    }


}
