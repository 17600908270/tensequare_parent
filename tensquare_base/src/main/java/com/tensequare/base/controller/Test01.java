package com.tensequare.base.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Test01 {
    public static void main(String[] args) {
        find();


    }

    private static void find() {

        try {
            int i=0;
            int m=55;
            System.out.println(m/i);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("dd"+e.getMessage());


            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            try {
                e.printStackTrace(pw);

                System.out.println(sw.toString());
            }
            finally
            { pw.close(); }


        }
    }
}
