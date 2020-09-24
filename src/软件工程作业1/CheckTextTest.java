package mytest01;

import check.CheckText;
import org.junit.Test;

//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;

public class CheckTextTest {

//    static BufferedReader bdr;
//    char[] chr;
//    int[] mean = {1};


    @Test
    public void main() {

        String[] args1 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig_0.8_add.txt",
                "res.txt"};
        String[] args2 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig_0.8_del.txt",
                "res.txt"};
        String[] args3 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig_0.8_dis_1.txt",
                "res.txt"};
        String[] args4 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig_0.8_dis_10.txt",
                "res.txt"};
        String[] args5 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig_0.8_dis_15.txt",
                "res.txt"};
        String[] args6 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\normal.txt",
                "res.txt"};
        String[] args7 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\normal2.txt",
                "res.txt"};
        String[] args8 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\normal3.txt",
                "res.txt"};
        String[] args9 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\normal4.txt",
                "res.txt"};
        String[] args10 = {"D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\orig.txt",
                "res.txt"};

        System.out.println("原文件：orig.txt        对比文件：orig_0.8_add.txt");
        CheckText.main(args1);
        System.out.println("原文件：orig.txt        对比文件：orig_0.8_del.txt");
        CheckText.main(args2);
        System.out.println("原文件：orig.txt        对比文件：orig_0.8_dis_1.txt");
        CheckText.main(args3);
        System.out.println("原文件：orig.txt        对比文件：orig_0.8_dis_10.txt");
        CheckText.main(args4);
        System.out.println("原文件：orig.txt        对比文件：orig_0.8_dis_15.txt");
        CheckText.main(args5);
        System.out.println("原文件：orig.txt        对比文件：orig_0.8_normal.txt");
        CheckText.main(args6);
        System.out.println("原文件：orig.txt        对比文件：orig_0.8_normal2.txt");
        CheckText.main(args7);
        System.out.println("原文件：orig.txt        对比文件：orig_0.8_normal3.txt");
        CheckText.main(args8);
        System.out.println("原文件：orig.txt        对比文件：orig_0.8_normal4.txt");
        CheckText.main(args9);
        System.out.println("原文件：orig.txt        对比文件：orig.txt");
        CheckText.main(args10);















    }

//    @Test
//    public void singleSentence() {
//
//        try {
//            bdr = new BufferedReader(new InputStreamReader(new FileInputStream(
//                    "D:\\IDEA\\软工1\\out\\artifacts\\main\\test\\a1.txt"), StandardCharsets.UTF_8));
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//
//        chr = CheckText.SingleSentence(bdr, mean);
//
//        char[] text = {'爸','爸','我','是','军','人','的','女','儿','我','不','会','给','你','丢','脸','的','，','、','\u0000'};
//
//        int i = 0;
//        for(char c : chr)
//            if(c!=0)
//                if(c==text[i]){
//                    System.out.println(c+"符合"+text[i]);
//                    i++;
//                }
//
//    }
//
//    @Test
//    public void constructingWordVector() {
//
//        int[][] Word_vector = new int[2][3];
//        char[] text = {'一','定','要','\u0000'};
//        char[] textall = {'不','知','道','\u0000'};
//
//        CheckText.ConstructingWordVector(Word_vector,text,textall,1);
//
//        for(int j : Word_vector[1]){
//            if(j==0)
//                System.out.println("是0符合");
//        }
//
//
//    }
//
//    @Test
//    public void output() {
//
//        CheckText.OutPut("res.txt",0.88);
//
//    }




}