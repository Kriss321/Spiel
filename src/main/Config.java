/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kristof
 */
public class Config {
    
    private static Map<String, String> config;
    
    public Config() {
        config = new HashMap();
        config.put("p0.move.right", "32");
        config.put("p0.move.left", "30");
        config.put("p0.move.jump", "17");
        config.put("p1.move.right", "205");
        config.put("p1.move.left", "203");
        config.put("p1.move.jump", "200");
    }
    
    public static int getInt(String key) {
        return Integer.parseInt(config.get(key));
    }
}
