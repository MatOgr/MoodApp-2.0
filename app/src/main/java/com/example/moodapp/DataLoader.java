package com.example.moodapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DataLoader {
    private Map<String, String> moodData;

    // TODO: 18.11.2020 look at the API version reqs
    public String loadString() throws MalformedURLException {
        URL urlAddr = new URL("https://moodup.team/test/info.php");

        ArrayList<String> tablice = new ArrayList<>();
        String unformattedString = null;

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(urlAddr.openStream()))){
            unformattedString = in.lines()
                    .collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  inputLine contains String with all data, now splitting
        //for (cell in inputLine.split(","))
        unformattedString = unformattedString.substring(1, unformattedString.length()-1)
                .trim()
                .replaceAll(" \\[    ", "\\[").replaceAll("    ", "");
        System.out.println(unformattedString + '\n');

        // getting the arrays out
        int i = 0;
        while(unformattedString.indexOf("[") != -1) {
            tablice.add(unformattedString.substring(unformattedString.indexOf("["), unformattedString.indexOf("]")+1));
            unformattedString = unformattedString.replace(tablice.get(i), String.format("##tablica_%d##\"", i));
            tablice.set(i, tablice.get(i)
                    .substring(2, tablice.get(i).length()-1)
                    .replaceAll("\",\"", "=")
                    .replaceAll("\"", ""));
            System.out.println((i) + " tablica: " + tablice.get(i));
            i+=1;
        }
        unformattedString = unformattedString.replaceAll(":", "::");
        String result = "";
        System.out.println("Liczba tablic: " + tablice.size() + "\n\nA oto nasza tablica do slownika:\n");
        for (String x : unformattedString.split("\",\"")) {
            result += '#' + x.replaceAll("\"", "");
        }

        for (i = 0; i < tablice.size(); i++) {
            result = result.replaceFirst(String.format("##tablica_%d##", i), tablice.get(i));
        }
        System.out.println(result.substring(1));

        return result.substring(1);
    }


    public Map<String, String> getMoodData() {
        return moodData;
    }

    public void setMoodData(Map<String, String> moodData) {
        this.moodData = moodData;
    }

    public Map<String, String> makeMap(String data) {
        Map<String, String> dataMap = new HashMap<>();
        for (String x : data.split("#")) {
            System.out.println("Linia: " + x);
            String[] a = x.split("::");
            dataMap.put(a[0], a[1]);
        }
        return dataMap;
    }
}
