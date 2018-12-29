package com.tensequare.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        String retemp="dd";
        List<HashMap<String, String>> arr = new ArrayList<HashMap<String, String>>();

        HashMap<String,String> map=new HashMap<>();
        map.put("oldCharacterValue","240E:0690:0000:10");
        map.put("characterId","IPv6_DESC");
        map.put("actType","KEEP");
        map.put("characterValue","240E:0690:0000:10");
        String  tempip="";
        arr.add(map);
        for (HashMap<String, String> e : arr) {

            System.out.println(e.get("characterId"));
            System.out.println("oldCharacterValue0" +e.get("oldCharacterValue"));
            if ("IPv6_DESC".equals(e.get("characterId"))) {
                System.out.println("888888" +e.get("oldCharacterValue"));
                tempip = e.get("oldcharacterValue");
            }
        }
        System.out.println(tempip);
       String cmd = retemp.replace("${cumIP}", tempip);
        System.out.println(cmd);
    }
}
