package io.qepl.impetus;

import java.util.ArrayList;

public class MultiVariateTimeSeries {

    ArrayList<Long> domain = new ArrayList<>();

    ArrayList<float[]> values = new ArrayList<>();


    void update(long t, float[] X) {

        domain.add(t);
        values.add(X);

    }

}
