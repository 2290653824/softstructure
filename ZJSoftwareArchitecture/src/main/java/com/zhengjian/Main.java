package com.zhengjian;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.*;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("============请输入你的选择：============");
            System.out.println("1. 四大体系结构知识点");
            System.out.println("2. 运行实现KWIC");
            System.out.println("3. 结束程序");
            int i = scanner.nextInt();
            if(i==1){
                System.out.println("=====================================");
                System.out.println("请输入你想查询的体系结构：");
                System.out.println("1. 主程序-子程序");
                System.out.println("2. 面向对象");
                System.out.println("3. 事件系统");
                System.out.println("4. 管道-过滤");
                int j = scanner.nextInt();


                String s = HttpUtil.get("http://localhost:8080/getModelInfo?model="+j);
                JSONObject map = JSONUtil.parseObj(s);
                System.out.println("======================================相关概念介绍========================================");
                System.out.println(JSONUtil.parseObj(map.get("data").toString()).get("modelInfo"));
                System.out.println("=========================================核心代码========================================");
                System.out.println(JSONUtil.parseObj(map.get("data").toString()).get("codeInfo"));

            }else if(i==2){
                System.out.println("请输入你需要采取哪种方式进性程序运行?");
                System.out.println("1. 主程序-子程序");
                System.out.println("2. 面向前端");
                System.out.println("3. 事件系统");
                System.out.println("4. 管道-过滤");

                int z = scanner.nextInt();

                System.out.println("请输入你的文件所在路径(D://schoolTest//input.txt):");

                scanner.nextLine();
                String s=scanner.nextLine();


                String res = HttpUtil.get("http://localhost:8080/startKWIC?model=" + z + "&path=" + s);
                //获得output.txt文件存放位置
                String path=JSONUtil.parseObj(JSONUtil.parseObj(res).get("data").toString()).get("path").toString();
//                使用输出流读取文件
                readFile(path);
                System.out.println("=================================");


            }else{
                break;
            }
        }
    }
    public static void readFile(String path) throws IOException {
        //读取文件(字节流)
        InputStream in = new FileInputStream(path);
//写入相应的文件
//        OutputStream out = new FileOutputStream("d:\\2.txt");
//读取数据
//一次性取多少字节
        byte[] bytes = new byte[2048];
//接受读取的内容(n就代表的相关数据，只不过是数字的形式)
        int n = -1;
//循环取出数据
        while ((n = in.read(bytes,0,bytes.length)) != -1) {
            //转换成字符串
            String str = new String(bytes,0,n,"UTF-8");
            System.out.println(str);
            //写入相关文件
//            out.write(bytes, 0, n);
        }
//关闭流
        in.close();
//        out.close();
    }

    public static void readFile1(String path){
        try {
            FileInputStream in = new FileInputStream(path);
// 创建文件输入流对象
            int n = 512; // 设定读取的字节数
            byte buffer[] = new byte[n];
// 读取输入流
//读取 n 个字节，放置到以下标 0 开始字节数组 buffer 中，返回值为实际读取的字节的数量
            while ((in.read(buffer, 0, n) != -1) && (n > 0)) {
                System.out.print(new String(buffer));
            }
            System.out.println();
            in.close();
// 关闭输入流
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (Exception e) {
            System.out.println(e);
        }

    }


}
