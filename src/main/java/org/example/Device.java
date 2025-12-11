package org.example;

import java.util.ArrayList;
import java.util.List;

public class Device {
    String deviceName;
    List<String> outputs = new ArrayList<>();

    public Device(String deviceName, List<String> outputs) {
        this.deviceName = deviceName;
        this.outputs = outputs;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<String> outputs) {
        this.outputs = outputs;
    }
}
