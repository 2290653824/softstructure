package com.zhengjian.controller;

import com.zhengjian.OOP.Alphabetizer;
import com.zhengjian.OOP.Input;
import com.zhengjian.OOP.Output;
import com.zhengjian.OOP.Shift;
import com.zhengjian.common.R;
import com.zhengjian.enventSystem.KWICSubject;
import com.zhengjian.mainProgramSsubprogram.demo1;
import com.zhengjian.pipline.Pipe;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class WebController {

    private Map<String,String> modelInfo;
    private Map<String,String> codeInfo;


    @RequestMapping("startKWIC")
    public R getKWIC(@RequestParam("model") int model,@RequestParam("path")String path) throws IOException {
        String outputPath="";
        String[] split = path.split("//");
        for(int i=0;i<split.length-1;i++){
            outputPath+=split[i]+"//";
        }
        outputPath+="output.txt";
        if(model==1){
            demo1 kwic = new demo1();
            kwic.input(path);
            kwic.shift();
            kwic.alphabetizer();
            kwic.output(outputPath);
        }else if(model==2){
            Input input = new Input();
            input.input(path);
            Shift shift = new Shift(input.getLineTxt());
            shift.shift();
            Alphabetizer alphabetizer = new Alphabetizer(shift.getKwicList());
            alphabetizer.sort();
            Output output = new Output(alphabetizer.getKwicList());
            output.output(outputPath);
        }else if(model == 3){
            //创建主题
            KWICSubject kwicSubject = new KWICSubject();
            //创建观察者
            com.zhengjian.enventSystem.Input input = new com.zhengjian.enventSystem.Input(path);
            com.zhengjian.enventSystem.Shift shift = new com.zhengjian.enventSystem.Shift(input.getLineTxt());
            com.zhengjian.enventSystem.Alphabetizer alphabetizer = new com.zhengjian.enventSystem.Alphabetizer(shift.getKwicList());
            com.zhengjian.enventSystem.Output output = new com.zhengjian.enventSystem.Output(alphabetizer.getKwicList(), outputPath);

            // 将观察者加入主题
            kwicSubject.addObserver(input);
            kwicSubject.addObserver(shift);
            kwicSubject.addObserver(alphabetizer);
            kwicSubject.addObserver(output);
            // 逐步调用各个观察者
            kwicSubject.startKWIC();
        }else if(model==4){
            File inFile = new File(path);
            File outFile = new File(outputPath);
            Pipe pipe1 = new Pipe();
            Pipe pipe2 = new Pipe();
            Pipe pipe3 = new Pipe();
            com.zhengjian.pipline.Input input = new com.zhengjian.pipline.Input(inFile, pipe1);
            com.zhengjian.pipline.Shift shift = new com.zhengjian.pipline.Shift(pipe1, pipe2);
            com.zhengjian.pipline.Alphabetizer alphabetizer  = new com.zhengjian.pipline.Alphabetizer(pipe2, pipe3);
            com.zhengjian.pipline.Output output = new com.zhengjian.pipline.Output(outFile,pipe3);
            input.transform();
            shift.transform();
            alphabetizer.transform();
            output.transform();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("path",outputPath);
        R r=new R();
        r.setData(map);
        return r;
    }

    @PostConstruct
    public void inti(){
        modelInfo=new HashMap<String,String>();
        modelInfo.put("1","主程序—子程序：软件体系结构中主程序—子程序体系结构是较简单的结构，其组件是主程序和子程序，" +
                "连接件是调用返回机制。其实就是\\n 主程序调用子程序");
        modelInfo.put("2","面向对象：面向对象(Object Oriented)是软件开发方法，一种编程范式。" +
                "面向对象的概念和应用已超越了程序设计和软件开发，扩展到如数据库系统、交互" +
                "式界面、应用结构、应用平台、分布式系统、网络管理结构、CAD技术、人工智能" +
                "等领域。面向对象是一种对现实世界理解和抽象的方法，是计算机编程技术" +
                "发展到一定阶段后的产物。");
        modelInfo.put("3","事件系统：是指通过事件进行通信的一种软件架构，是最常用的架构范式的一" +
                "种；该架构关注的是事件的产生、识别、处理和响应的情况。");
        modelInfo.put("4","管道-过滤：管道-过滤器模式的体系结构是面向数据流的软件体系结构。它最典" +
                "型的应用是在编译系统。一个普通的编译系统包括词法分析器,语法分析器,语义分析" +
                "与中间代码生成器,优化器,目标代码生成器等一系列对源程序进行处理的过程。人" +
                "们可以将编译系统看作一系列过滤器的连接体,按照管道-过滤器的体系结构进行设计");

        codeInfo=new HashMap<String,String>();
        codeInfo.put("1","demo1 kwic = new demo1();\n" +
                "        kwic.input(\"E:\\\\input.txt\");\n" +
                "        kwic.shift();\n" +
                "        kwic.alphabetizer();\n" +
                "        kwic.output(\"E:\\\\output.txt\");");

        codeInfo.put("2","Input input = new Input();\n" +
                "        input.input(\"D:\\\\schoolTest\\\\input.txt\");\n" +
                "        Shift shift = new Shift(input.getLineTxt());\n" +
                "        shift.shift();\n" +
                "        Alphabetizer alphabetizer = new Alphabetizer(shift.getKwicList());\n" +
                "        alphabetizer.sort();\n" +
                "        Output output = new Output(alphabetizer.getKwicList());\n" +
                "        output.output(\"D:\\\\schoolTest\\\\output.txt\");");

        codeInfo.put("3","//创建主题\n" +
                "        KWICSubject kwicSubject = new KWICSubject();\n" +
                "        //创建观察者\n" +
                "        Input input = new Input(\"E:\\\\input.txt\");\n" +
                "        Shift shift = new Shift(input.getLineTxt());\n" +
                "        Alphabetizer alphabetizer = new Alphabetizer(shift.getKwicList());\n" +
                "        Output output = new Output(alphabetizer.getKwicList(), \"E:\\\\output.txt\");\n" +
                "\n" +
                "        // 将观察者加入主题\n" +
                "        kwicSubject.addObserver(input);\n" +
                "        kwicSubject.addObserver(shift);\n" +
                "        kwicSubject.addObserver(alphabetizer);\n" +
                "        kwicSubject.addObserver(output);\n" +
                "        // 逐步调用各个观察者\n" +
                "        kwicSubject.startKWIC();");
        codeInfo.put("4","File inFile = new File(\"D:\\\\schoolTest\\\\input.txt\");\n" +
                "        File outFile = new File(\"D:\\\\schoolTest\\\\output.txt\");\n" +
                "        Pipe pipe1 = new Pipe();\n" +
                "        Pipe pipe2 = new Pipe();\n" +
                "        Pipe pipe3 = new Pipe();\n" +
                "        Input input = new Input(inFile, pipe1);\n" +
                "        Shift shift = new Shift(pipe1, pipe2);\n" +
                "        Alphabetizer alphabetizer  = new Alphabetizer(pipe2, pipe3);\n" +
                "        Output output = new Output(outFile,pipe3);\n" +
                "        input.transform();\n" +
                "        shift.transform();\n" +
                "        alphabetizer.transform();\n" +
                "        output.transform();");
    }

    @RequestMapping("getModelInfo")
    public R getModelInfo(@RequestParam("model") String model){
        R r=new R();
        HashMap<String, Object> map = new HashMap<>();
        map.put("modelInfo",modelInfo.get(model));
        map.put("codeInfo",codeInfo.get(model));
        r.setData(map);
        return r;
    }
}
