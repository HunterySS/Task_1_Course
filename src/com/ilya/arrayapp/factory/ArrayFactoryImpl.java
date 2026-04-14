package com.ilya.arrayapp.factory;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.factory.impl.ArrayFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayFactoryImpl implements ArrayFactory {
    private static final Logger LOGGER = LogManager.getLogger(ArrayFactoryImpl.class);

    @Override
    public NumericArray createArray(double[] values) {
        LOGGER.debug("Creating array from values: {}", java.util.Arrays.toString(values));
        NumericArray array = new NumericArrayBuilder()
                .setValues(values)
                .build();
        LOGGER.info("Array created: {}", array);
        return array;
    }

    private static class NumericArrayBuilder {
        private double[] values;

        public NumericArrayBuilder setValues(double[] values) {
            this.values = values;
            return this;
        }

        public NumericArray build() {
            if (values == null) {
                return new NumericArray(new double[0]);
            }
            return new NumericArray(values);
        }
    }
}