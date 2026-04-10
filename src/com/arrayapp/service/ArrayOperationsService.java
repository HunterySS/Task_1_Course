package com.arrayapp.service;

import com.arrayapp.entity.AbstractArray;
import java.util.OptionalDouble;

public interface ArrayOperationsService {

    OptionalDouble findMin(AbstractArray array);

    OptionalDouble findMax(AbstractArray array);

    OptionalDouble sum(AbstractArray array);

    OptionalDouble average(AbstractArray array);

    void bubbleSort(AbstractArray array);

    void quickSort(AbstractArray array);
}