package com.ilya.arrayapp.service.impl;

import com.ilya.arrayapp.entity.NumericArray;
import java.util.OptionalDouble;

public interface StatisticsService {
    OptionalDouble findMin(NumericArray array);
    OptionalDouble findMax(NumericArray array);
    OptionalDouble sum(NumericArray array);
    OptionalDouble average(NumericArray array);
}