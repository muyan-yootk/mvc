package com.yootk.test;

import org.junit.Test;

import java.io.*;
import java.nio.Buffer;
import java.util.Scanner;

public class TestImport {
    @Test
    public void get() throws Exception {
        File inFile = new File("H:\\京东区域数据\\town.sql") ;
        File outFile = new File("H:\\京东区域数据\\area.sql") ;
        BufferedReader scanner = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), "UTF-8")) ;
        PrintStream out = new PrintStream(new FileOutputStream(outFile, true)) ;
        String temp = null ;
        while((temp = scanner.readLine()) != null) {
            out.print(temp + " ; \n");
        }
    }
}
