package ���������ҵ1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class main {
	
	static BufferedReader bdr1 = null;
	static BufferedReader bdr2 = null;
	
//	static String a1 = "D:\\��ѧ\\������\\�������\\��ҵ1\\�����ļ�\\test\\orig_0.8_dis_15.txt";		//�����ı�
//	static String a2 = "D:\\��ѧ\\������\\�������\\��ҵ1\\�����ļ�\\test\\orig.txt";					//ԭ�ı�
	
	final static double threshold = 0.6;  	//�ظ�����ֵ
	final static int volume = 400;				//����������С
	
	public static void main(String[] args) {

		double rate = 0;
		double sum = 0;
		int[][] Word_vector = new int[2][volume];
		int count = 0;
		double ra = 0;
		
		try {
			
			int search = 0;
			
			BufferedReader bdr1 = new BufferedReader(new InputStreamReader(new FileInputStream(args[1]),"UTF-8"));	//ԭ�ı�
			BufferedReader bdr2 = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),"UTF-8"));	//�����ı�
			String[] text1 = new String[volume];							//�ʼ���
			String[] text2 = new String[volume];
			String[] textall;
			
			int[] mean1 = {1};									//�ִʷ�ʽ
			int[] mean2 = {1};
			
			int pp = 0;
			
			
			while(true){
				
				if(search==0){
					text1 = SingleSentence(bdr1,mean1);			//�����ı���һ�䵥��ִʴ���
					ra = 0;
					rate = 0;
					bdr2.close();
					bdr2 = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),"UTF-8"));
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
					

				if(text1[0]==null)			//�����������о��Ӷ��Ѽ��
					break;
				if(text1[0].equals("��")){					
					pp--;
					continue;
				}
					
				
				text2 = SingleSentence(bdr2,mean2);				//ԭ���ı���һ�䵥��ִʴ���
				
					
				
				if(text2[0]==null && search==0){
					
					count++;
					
					continue;
				}
					
				if(text2[0]==null && search==1){				//�Ѽ��ԭ��ȫ��
					
					
					sum+=rate;
//					if(rate!=0){
//						String s = String.format("%.2f",rate);
//						System.out.println("rate= "+s+" ��"+pp+"��");
//					}				
					search = 0;
					count++;
					continue;
				}
				
/////////////////////////////////////////////////////////////////////////////����ʼ���		
				
				textall = new String[volume*2];
				int i1 = 0;
				for(String str : text1){
					if(str == null)
						break;
					textall[i1] = str;
					i1++;
				}
				
				for(int i = 0;text2[i]!=null;i++){		
					for(int j = 0;j!=i1+1;j++){
						if(textall[j]!=null && text2[i]!=null)
						if(textall[j].equals(text2[i]))
							break;
						if(j==i1){
							textall[i1] = text2[i];
							i1++;
							break;
						}
					}
				}
				
//				for(String s : text1){
//					if(s!=null)
//						System.out.print(s+" ");
//					else{
//						System.out.print("\n");
//						break;
//					} 
//				}
//				
//				for(String s : text2){
//					if(s!=null)
//						System.out.print(s+" ");
//					else{
//						System.out.print("\n");
//						break;
//					} 
//				}
//				
//				for(String s : textall){
//					if(s!=null)
//						System.out.print(s+" ");
//					else{
//						System.out.print("\n");
//						break;
//					} 
//				}
				
///////////////////////////////////////////////////////////////////////////				
				
				Word_vector = new int[2][volume];
				
				ConstructingWordVector(Word_vector,text1,textall,0);		//����������
				
				ConstructingWordVector(Word_vector,text2,textall,1);
				
				
/////////////////////////////////////////////////////////////////////////////�����������㷨
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
				
				if(ra<threshold){			//�������ظ���
					search = 1;
					continue;
				}
				else{						//�����ظ���
					search = 1;		
					if(rate<ra){
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
					continue;
				}	
				

			}	
			System.out.println("����ɼ��");
			System.out.println("�ظ���Ϊ��"+String.format("%.2f",sum/count));
//			System.out.println("sumΪ��"+sum);
//			System.out.println("countΪ��"+count);
			System.out.println("��������Ϊ��"+ (pp-1));
			
			OutPut(args[2],sum/count);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (java.io.IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	//�ı�����ִʴ���
	public static String[] SingleSentence(BufferedReader bdr,int[] mean){	
		
		String[] str = new String[volume];
		int i = 0;
		int count = 0;
		char chr;
		
		try {
			
			while(true){	
				
				chr = (char)bdr.read();
				char t = chr;
				if((int)t==65535)
					break;
				if(chr=='\n' || chr=='��' || chr==',' || chr=='\u0020' || chr=='��' 
						|| chr=='��' || chr=='��' || chr=='��' || chr=='\u3000' || chr=='\r'){
					continue;
				}
				if(chr=='.' || chr=='��'){
					if(str[0]==null)
						str[0] = "��";
					break;
				}
				if(str[i]==null)
					str[i] = String.valueOf(chr);
				else str[i]+=chr;
				count++;
				if(count == mean[i%1]){
					i++;
					count = 0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	//����������
	public static void ConstructingWordVector(int[][] Word_vector,String[] text,String[] textall,int p){	
		
		for(int i = 0;textall[i]!=null;i++){
			for(int j = 0;text[j]!=null;j++){
				if(textall[i].equals(text[j])){
					Word_vector[p][i]++;
				}	
			}
		}
	}
	
	public static void OutPut(String log,double rate){
		
		BufferedWriter bdw = null;
		try {
			bdw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("res.txt",false)));		
			bdw.write("");
			bdw.flush();
			bdw.write("�ظ��ʣ�"+String.format("%.2f",rate));
			bdw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("�ѳɹ�����ļ� res.txt");
	}
	
}
