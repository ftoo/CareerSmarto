package com.example.shiro.careersmart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ericsson6 on 10/22/2016.
 */
public class ResultClass {
    Map<String, Integer> results = new HashMap<String, Integer>();
    private static ResultClass ourInstance = new ResultClass();

    public static ResultClass getInstance() {
        return ourInstance;
    }

    private ResultClass() {
    }
}
