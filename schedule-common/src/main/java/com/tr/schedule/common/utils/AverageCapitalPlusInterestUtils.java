package com.tr.schedule.common.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 等额本息还款，也称定期付息，即借款人每月按相等的金额偿还贷款本息，其中每月贷款利息按月初剩余贷款本金计算并逐月结清。把按揭贷款的本金总额与利息总额相加，  
 * 然后平均分摊到还款期限的每个月中。作为还款人，每个月还给银行固定金额，但每月还款额中的本金比重逐月递增、利息比重逐月递减。  
 */

public class AverageCapitalPlusInterestUtils {

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金和利息  
     *
     * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕  
     *
     * @param invest
     *            总借款额（贷款本金）  单位：元
     * @param yearRate
     *            年利率  
     * @param totalMonth
     *            还款总月数  
     * @return 每月偿还本金和利息,不四舍五入，直接截取小数点最后两位  
     */
    public static double getPerMonthPrincipalInterest(double invest, double yearRate, int totalMonth) {
        double monthRate = yearRate / 12;
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalMonth)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalMonth) - 1), 2, BigDecimal.ROUND_UP );
        return monthIncome.doubleValue();
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还利息
     *
     * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
     * @param invest
     *            总借款额（贷款本金）  单位：元
     * @param yearRate
     *            年利率
     * @param totalMonth
     *            还款总月数
     * @return 每月偿还利息
     */
    public static Map<Integer, BigDecimal> getPerMonthInterest(double invest, double yearRate, int totalMonth) {
        BigDecimal interestCount = BigDecimal.valueOf(getPrincipalInterestCount(invest, yearRate, totalMonth)).subtract(BigDecimal.valueOf(invest));

        Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
        double monthRate = yearRate/12;
        BigDecimal monthInterest = null;

        BigDecimal interestCountWithoutLast = new BigDecimal(0);
        for (int i = 1; i < totalMonth + 1; i++) {
            if(i != totalMonth){
                BigDecimal multiply = new BigDecimal(invest).multiply(new BigDecimal(monthRate));
                BigDecimal sub  = new BigDecimal(Math.pow(1 + monthRate, totalMonth)).subtract(new BigDecimal(Math.pow(1 + monthRate, i-1)));
                monthInterest = multiply.multiply(sub).divide(new BigDecimal(Math.pow(1 + monthRate, totalMonth) - 1), 6, BigDecimal.ROUND_UP );
                monthInterest = monthInterest.setScale(2, BigDecimal.ROUND_UP );
                interestCountWithoutLast = interestCountWithoutLast.add(monthInterest);
            }else{
                monthInterest = interestCount.subtract(interestCountWithoutLast);
            }
            map.put(i, monthInterest);
        }
        return map;
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金
     *
     * @param invest
     *            总借款额（贷款本金）  单位：元
     * @param yearRate
     *            年利率
     * @param totalMonth
     *            还款总月数
     * @return 每月偿还本金
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipal(double invest, double yearRate, int totalMonth) {
        double monthRate = yearRate / 12;
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalMonth)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalMonth) - 1), 2, BigDecimal.ROUND_UP );
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalMonth);
        Map<Integer, BigDecimal> mapPrincipal = new HashMap<Integer, BigDecimal>();

        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            mapPrincipal.put(entry.getKey(), monthIncome.subtract(entry.getValue()));
        }
        return mapPrincipal;
    }

    /**
     * 等额本息计算获取还款方式为等额本息的总利息
     *
     * @param invest
     *            总借款额（贷款本金）  单位：元
     * @param yearRate
     *            年利率
     * @param totalMonth
     *            还款总月数
     * @return 总利息
     */
    public static double getInterestCount(double invest, double yearRate, int totalMonth) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalMonth);

        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count.doubleValue();
    }


    public static double getPrincipalCount(double invest, double yearRate, int totalMonth) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, BigDecimal> mapInterest = getPerMonthPrincipal(invest, yearRate, totalMonth);

        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count.doubleValue();
    }

    /**
     * 应还本金总和
     * @param invest
     *            总借款额（贷款本金）单位：元
     * @param yearRate
     *            年利率
     * @param totalMonth
     *            还款总月数
     * @return 应还本金总和
     */
    public static double getPrincipalInterestCount(double invest, double yearRate, int totalMonth) {
        double monthRate = yearRate / 12;
        BigDecimal perMonthInterest = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalMonth)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalMonth) - 1), 2, BigDecimal.ROUND_UP );
        BigDecimal count = perMonthInterest.multiply(new BigDecimal(totalMonth));
        count = count.setScale(2, BigDecimal.ROUND_UP );
        return count.doubleValue();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        double invest = 20000.0; // 本金
        int month = 12;
        double yearRate = 0.15; // 年利率  
        double perMonthPrincipalInterest = getPerMonthPrincipalInterest(invest, yearRate, month);
        System.out.println("等额本息---每月还款本息：" + perMonthPrincipalInterest);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, month);
        System.out.println("等额本息---每月还款利息：" + mapInterest);
        Map<Integer, BigDecimal> mapPrincipal = getPerMonthPrincipal(invest, yearRate, month);
        System.out.println("等额本息---每月还款本金：" + mapPrincipal);
        double count = getInterestCount(invest, yearRate, month);
        System.out.println("等额本息---总利息：" + count);
        double Principalcount = getPrincipalCount(invest, yearRate, month);
        System.out.println("等额本息---总本金：" + Principalcount);
        double principalInterestCount = getPrincipalInterestCount(invest, yearRate, month);
        System.out.println("等额本息---应还本息总和：" + principalInterestCount);
    }
}  