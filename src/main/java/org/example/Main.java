package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Device> devices = new ArrayList<>();
        int lastOutIndex = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Devices&Outputs.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                found = line.contains("you");
                if(found){
                    String[] dAndo = line.split(" ");
                    String dev= dAndo[0].substring(0, dAndo[0].length() - 1);
                    List<String> outs = new ArrayList<>();
                    for(int i = 1; i < dAndo.length; i++){
                        outs.add(dAndo[i]);
                    }
                    devices.add(new Device(dev,outs));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int n = devices.size();
        for(int i = 0; i < n; i++){
            if(devices.get(i).outputs.contains("out")){
                lastOutIndex = i;
            }
        }

        devices = devices.subList(lastOutIndex + 1, devices.size());

        n = devices.size();
        //devices only contain ( you -> everything in between <- out )
        List<Device> possibleTrails = new ArrayList<>();
        int possible = 0;
        possibleTrails.add(devices.getFirst());
        List<String> lastDeviceName = new ArrayList<>();
        lastDeviceName.add("you");

        for (int i = 1; i < n; i++) {
            if(lastDeviceName.contains(devices.get(i).deviceName)){
                lastDeviceName.remove(devices.get(i).deviceName);
                for(int p = 1; p < devices.get(i).outputs.size(); p++){
                    lastDeviceName.add(devices.get(i).outputs.get(p));

                    if(devices.get(i).outputs.get(p).equals("out")){
                        possible= possible+1;
                    }
                }
            }
        }
        System.out.println("How many different paths lead from you to out?: " + possible);
    }
}

