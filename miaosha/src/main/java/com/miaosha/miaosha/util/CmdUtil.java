package com.miaosha.miaosha.util;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/7 10:09
 * @Desc:
 */
public class CmdUtil {
    /**
     * * cmd /c dir：是执行完dir命令后关闭命令窗口；
     * * cmd /k dir：是执行完dir命令后不关闭命令窗口。
     * * cmd /c start dir：会打开一个新窗口后执行dir指令，原窗口会关闭；
     * * cmd /k start dir：会打开一个新窗口后执行dir指令，原窗口不会关闭。
     * * ---------------------
     * * 原文：https://blog.csdn.net/w405722907/article/details/78610503
     *
     * @param filePath
     * @param fileName
     * @param originSuffix
     * @param textArea
     * @return
     */
    public static boolean mergeCSVByCommand(String filePath, String fileName, String originSuffix, JTextArea textArea) {
        File file = new File(filePath);
        Process process;
        try {
            //在指定的目录下  执行cmd命令
            process = Runtime.getRuntime().exec("cmd /c copy *." + originSuffix + " " + fileName, null, file);
            //因此处等待cmd执行，因此该段代码要放入java线程类的run函数里面
            if (process != null) {
                process.getOutputStream().close();
                InputStream in = process.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"));
                String temp;
                while ((temp = br.readLine()) != null) {
                    System.out.println(temp);
                }
            }
            File[] files = file.listFiles();
            assert files != null;
            for (File file1 : files) {
                if (file1.getName().contains(fileName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            textArea.insert(e.getMessage() + "\n", 0);
            e.printStackTrace();
        }
        return false;
    }
}
