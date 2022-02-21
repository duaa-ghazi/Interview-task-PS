package com.progressoft.tools;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoringSummaryImpl implements ScoringSummary {

    private List <BigDecimal> values;

    public ScoringSummaryImpl(List<BigDecimal> values) {
        this.values = values;

    }

    @Override
    public BigDecimal mean() {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (BigDecimal value : values) {
            sum = sum.add(value);
        }
        BigDecimal average = sum.divide(BigDecimal.valueOf(values.size()),BigDecimal.ROUND_HALF_EVEN);
        return average.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public BigDecimal standardDeviation() {

        double standard = Math.sqrt(variance().doubleValue());
        BigDecimal standardDeviation = new BigDecimal(standard);
        standardDeviation = standardDeviation.setScale(2, RoundingMode.HALF_UP);

        return standardDeviation;
    }

    @Override
    public BigDecimal variance() {
        List<BigDecimal> squares = new ArrayList<BigDecimal>();
        for (BigDecimal value : values) {
            BigDecimal XminMean = value.subtract(mean());
            squares.add(XminMean.pow(2));
        }
        BigDecimal sum = sum(squares);
        BigDecimal variance=sum.divide(BigDecimal.valueOf(values.size()),BigDecimal.ROUND_HALF_EVEN).setScale(0, RoundingMode.HALF_UP);
        return variance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public BigDecimal median() {
        BigDecimal median =BigDecimal.ZERO;
        Collections.sort(values);
        if (values.size() % 2 == 0)
            median = (BigDecimal) values.get(values.size() / 2).add( (BigDecimal) values.get(values.size() / 2 - 1)) .divide(BigDecimal.valueOf(2));
        else
            median = (BigDecimal) values.get(values.size() / 2);
        return median.setScale(2, BigDecimal.ROUND_HALF_EVEN);

    }

    @Override
    public BigDecimal min() {
        return values.stream()
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public BigDecimal max() {
        return values.stream()
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public  BigDecimal sum(List <BigDecimal> values) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal bigDecimal : values) {
            sum = sum.add(bigDecimal);
        }
        return sum;
    }


}
