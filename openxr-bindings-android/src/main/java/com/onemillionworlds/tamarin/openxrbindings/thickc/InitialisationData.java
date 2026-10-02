package com.onemillionworlds.tamarin.openxrbindings.thickc;

public class InitialisationData {
    public final long javaVm;
    public final long activityContext;

    public InitialisationData(long javaVm, long activityContext) {
        this.javaVm = javaVm;
        this.activityContext = activityContext;
    }
}
