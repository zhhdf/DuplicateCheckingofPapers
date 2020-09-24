import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class CheckText {

    static BufferedReader bdr1 = null;
    static BufferedReader bdr2 = null;

    final static double threshold = 0.6;  	    //重复率阈值
    final static int volume = 400;				//句子容量大小

    public static void main(String[] args) {

        double rate = 0;
        double sum = 0;
        int[][] Word_vector;
        int count = 0;
        double ra;

        try {

            int search = 0;

            bdr1 = new BufferedReader(new InputStreamReader(new FileInputStream(args[1]), StandardCharsets.UTF_8));	//原文本
            bdr2 = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));	//查重文本
            char[] text1 = new char[volume];							//词集合
            char[] text2;
            char[] textall;

            int[] mean1 = {1};									//分词方式
            int[] mean2 = {1};

            int pp = 0;

            while(true){

                if(search==0){
                    text1 = SingleSentence(bdr1,mean1);			//查重文本下一句单句分词处理
//                    ra = 0;
                    rate = 0;
                    bdr2.close();
                    bdr2 = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
                    pp++;

//					for(String s : text1){
//						if(s!=null)
//							System.out.print(s+" ");
//						else{
//							System.out.print("\n");
//							break;
//						}
//					}

                }

                if(text1[0]=='\u0000')			//查重文章所有句子都已检查
                    break;
                if(text1[0]=='。'){
                    pp--;
                    continue;
                }

                text2 = SingleSentence(bdr2,mean2);				//原本文本下一句单句分词处理

                if(text2[0]=='\u0000' && search==0){
                    count++;
                    continue;
                }

                if(text2[0]=='\u0000'){				//已检查原文全文

                    sum+=rate;
//					if(rate!=0){
//						String s = String.format("%.2f",rate);
//						System.out.println("rate= "+s+" 第"+pp+"句");
//					}
                    search = 0;
                    count++;
                    continue;
                }

/////////////////////////////////////////////////////////////////////////////处理字数组

                textall = new char[volume*2];
                int i1 = 0;
                for(char str : text1){
                    if(str == '\u0000')
                        break;
                    textall[i1] = str;
                    i1++;
                }

                for(int i = 0;text2[i]!='\u0000';i++){
                    for(int j = 0;j!=i1+1;j++){
                        if(textall[j]!='\u0000' && text2[i]!='\u0000')
                            if(textall[j]==text2[i])
                                break;
                        if(j==i1){
                            textall[i1] = text2[i];
                            i1++;
                            break;
                        }
                    }
                }

//                System.out.print("text1: ");
//				for(char s : text1){
//					if(s!='\u0000')
//						System.out.print(s+" ");
//					else{
//						System.out.print("\n");
//						break;
//					}
//				}
//
//                System.out.print("text2: ");
//				for(char s : text2){
//					if(s!='\u0000')
//						System.out.print(s+" ");
//					else{
//						System.out.print("\n");
//						break;
//					}
//				}
//
//                System.out.print("textall: ");
//				for(char s : textall){
//					if(s!='\u0000')
//						System.out.print(s+" ");
//					else{
//						System.out.print("\n");
//						break;
//					}
//				}

///////////////////////////////////////////////////////////////////////////

                Word_vector = new int[2][volume];

                ConstructingWordVector(Word_vector,text1,textall,0);		//构建词向量

                ConstructingWordVector(Word_vector,text2,textall,1);


/////////////////////////////////////////////////////////////////////////////词向量余弦算法
                double upnum = 0, downuml = 0, downumr = 0;

//				for(int i : Word_vector[0])
//					System.out.print(i+" ");
//				System.out.println("");
//
//				for(int i : Word_vector[1])
//					System.out.print(i+" ");
//				System.out.println("");

                for(int i = 0;i<=i1;i++){
                    upnum+=(Word_vector[0][i]*Word_vector[1][i]);
                }
                for(int i = 0;i<=i1;i++){
                    downuml+=(Word_vector[0][i]*Word_vector[0][i]);
                }
                for(int i = 0;i<=i1;i++){
                    downumr+=(Word_vector[1][i]*Word_vector[1][i]);
                }

                ra = upnum / Math.sqrt(downuml * downumr);

                if(ra<threshold){			//不符合重复率
                    search = 1;
                }
                else{						//符合重复率
                    search = 1;
                    if(rate<ra){            //一直取最大重复率
                        rate = ra;
//						//============================
//						for(String s : text1){
//							if(s!=null)
//								System.out.print(s+" ");
//							else{
//								System.out.print("\n");
//								break;
//							}
//						}
//
//						for(String s : text2){
//							if(s!=null)
//								System.out.print(s+" ");
//							else{
//								System.out.print("\n");
//								break;
//							}
//						}
//						//===============================
                    }
                }


            }
            System.out.println("已完成检查");
            System.out.println("重复率为："+String.format("%.2f",sum/count));
            System.out.println("句子总数为："+ (pp-1));

            OutPut(args[2],sum/count);

        } catch (FileNotFoundException e) {
            System.out.println("找不到文件，请确认文件目录");
            e.printStackTrace();
        } catch (java.io.IOException e1) {
            System.out.println("文件载入出错");
            e1.printStackTrace();
        }

    }

    //文本单句分词处理
    public static char[] SingleSentence(BufferedReader bdr,int[] mean){

        char[] str = new char[volume];
        int i = 0;
        int count = 0;
        char chr;

        try {

            while(true){

                int t = bdr.read();

                if(t==-1)       //若到达文章末尾
                    break;

                chr = (char)t;      //转换成char类型

                if(chr=='\n' || chr=='，' || chr==',' || chr=='\u0020' || chr=='？'       //省略标点符号
                        || chr=='、' || chr=='：' || chr=='；' || chr=='\u3000' || chr=='\r'){
                    continue;
                }
                if(chr=='.' || chr=='。'){   //已到达句子末尾
                    if(str[0]==0)
                        str[0] = '。';
                    break;
                }
                if(str[i]==0)          //以下代码为上一版本未修改的代码
                    str[i] = chr;
//                else str[i]+=chr;
                count++;
                if(count == mean[0]){
                    i++;
                    count = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    //构建词向量
    public static void ConstructingWordVector(int[][] Word_vector,char[] text,char[] textall,int p){

        for(int i = 0;textall[i]!='\u0000';i++){
            for(int j = 0;text[j]!='\u0000';j++){
                if(textall[i]==text[j]){
//                    System.out.println("textall= "+textall[i]+" text[j]= "+text[j]);
//                    System.out.println("(int)textall= "+(int)textall[i]+" (int)text[j]= "+(int)text[j]);
                    Word_vector[p][i]++;
                }
            }
        }
    }

    public static void OutPut(String log,double rate){

        BufferedWriter bdw;
        try {
            bdw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(log,false)));
            bdw.write("");
            bdw.flush();
            bdw.write("重复率："+String.format("%.2f",rate));
            bdw.flush();
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件，请确认目录");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("无法输出文件");
            e.printStackTrace();
        }
        System.out.println("已成功输出文件 res.txt");
    }

}
