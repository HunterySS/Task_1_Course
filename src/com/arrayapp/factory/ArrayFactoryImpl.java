package com.arrayapp.factory;

import com.arrayapp.entity.NumericArray;

public class ArrayFactoryImpl implements ArrayFactory {

    @Override
    public NumericArray createArray(double[] values) {
        return new NumericArrayBuilder()
                .setValues(values)
                .build();
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
