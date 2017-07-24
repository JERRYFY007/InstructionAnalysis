package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class CompareForAll {

	public static void main(String[] args) throws Exception {
		BufferedReader br1 = null;
		BufferedReader br2=null;
		
		BufferedWriter bw=null;
		BufferedWriter bw2=null;
		br1 = new BufferedReader(new FileReader("./File/alljson.txt"));//全部测试集的解析文本，顺序为multi、tv、pro、func(open、close...)
		br2 = new BufferedReader(new FileReader("./File/AnalysisResult.txt"));//全部解析结果
		bw = new BufferedWriter(new FileWriter("./File/Wrong.txt"));//详细错误分析
		bw2 = new BufferedWriter(new FileWriter("./File/WrongList.txt"));//错误解析测试集列表
	

		String line1 = null;
		String line2 = null;
		String line3 = null;
		int totalnumber=0;
		int rightnumber=0;
		int wrongnumber=0;
		
	//	int multinumber=64;
		int multiright=0;
		int multiwrong=0;
		

	//	int firsttv=65;
		int tvright=0;
		int tvwrong=0;
		
		
	//	int firstpro=21681;
		int proright=0;
		int prowrong=0;
		
		
	//	int firstfunc=43297;
		int funcright=0;
		int funcwrong=0;
		
		
	//	int firstopen=43297;
		int openright=0;
		int openwrong=0;
		
	//	int firstclose=43373;
		int closeright=0;
		int closewrong=0;
		
    //  int firsttrunup=43525;
		int turnupright=0;
		int turnupwrong=0;
		
	//	int firsttrundown=44365;
		int turndownright=0;
		int turndownwrong=0;
		
	//	int firstswitchup=45205;
		int switchupright=0;
		int switchupwrong=0;
		
	//	int firstswitchdown=45242;
		int switchdownright=0;
		int switchdownwrong=0;
		
	//	int firstpausestart=45279;
		int pausestartright=0;
		int pausestartwrong=0;
		while (((line1 = br1.readLine()) != null)&&((line2 = br2.readLine()) != null)) {
			totalnumber++;
        //解析结果和测试结果匹配上
			if(line1.equals(line2)){
				rightnumber++;
				if(totalnumber<65){
					multiright++;
				}
				else if(totalnumber<21681){
					tvright++;
				}
				else if(totalnumber<43297){
					proright++;
				}
				else{
					funcright++;
					if(totalnumber<43373){
						openright++;
					}
					else if(totalnumber<43525){
						closeright++;
					}
					else if(totalnumber<44365){
						turnupright++;
					}
					else if(totalnumber<45205){
						turndownright++;
					}
					else if(totalnumber<45242){
						switchupright++;
					}
					else if(totalnumber<45279){
						switchdownright++;
					}
					else{
						pausestartright++;
					}
						
							
				}
				
			}
			//解析结果和测试集不匹配
			else
			{   int j=1;
			    BufferedReader br3=null;
			    br3 = new BufferedReader(new FileReader("./File/all.txt"));//全部测试集的输入文本
				while ((line3 = br3.readLine()) != null) {
				     
					
					if(j==totalnumber){
						System.out.println("出错："+totalnumber);
						System.out.println(line3);
						bw2.write(line3+"\n");
						break;
					}
					
					j++;
					
				}
				if(totalnumber<65){
					multiwrong++;
				}
				else if(totalnumber<21681){
					tvwrong++;
				}
				else if(totalnumber<43297){
					prowrong++;
				}
				else{
					funcwrong++;
					if(totalnumber<43373){
						openwrong++;
					}
					else if(totalnumber<43525){
						closewrong++;
					}
					else if(totalnumber<44365){
						turnupwrong++;
					}
					else if(totalnumber<45205){
						turndownwrong++;
					}
					else if(totalnumber<45242){
						switchupwrong++;
					}
					else if(totalnumber<45279){
						switchdownwrong++;
					}
					else{
						pausestartwrong++;
					}
						
							
				}
			
			br3.close();
			System.out.println("标准："+line1);
			System.out.println("解析："+line2);
			System.out.println();
			bw.write("出错： "+totalnumber+"\n");
			bw.write(line3+"\n");
			bw.write("标准："+line1+"\n");
			bw.write("解析："+line2+"\n");
			bw.write("\n");
		   wrongnumber++;
				
			}
			
		      
			 }
	
		System.out.println("---------------------------------------------------------------");
		System.out.println("混合指令数目："+64);
		System.out.println("解析错误数目："+multiwrong);
		float num1= (float)multiright/64;  
		DecimalFormat df1 = new DecimalFormat("0.000000");//格式化小数  
		String s1 = df1.format(num1);//返回的是String类型  
		System.out.println("准确率为:"+s1);
		
		System.out.println("---------------------------------------------------------------");
		
		System.out.println("频道指令数目："+21616);
		System.out.println("解析错误数目："+tvwrong);
		float num2= (float)tvright/21616;  
		DecimalFormat df2 = new DecimalFormat("0.000000");//格式化小数  
		String s2 = df2.format(num2);//返回的是String类型  
		System.out.println("准确率为:"+s2);
		
		System.out.println("---------------------------------------------------------------");
		
		System.out.println("节目指令数目："+21616);
		System.out.println("解析错误数目："+prowrong);
		float num3= (float)proright/21616;  
		DecimalFormat df3 = new DecimalFormat("0.000000");//格式化小数  
		String s3 = df3.format(num3);//返回的是String类型  
		System.out.println("准确率为:"+s3);
		
		System.out.println("---------------------------------------------------------------");
		
		System.out.println("功能指令数目："+1987);
		System.out.println("解析错误数目："+funcwrong);
		
		float num4= (float)funcright/1987;  
		DecimalFormat df4 = new DecimalFormat("0.000000");//格式化小数  
		String s4 = df4.format(num4);//返回的是String类型  
		System.out.println("准确率为:"+s4);
		
		float num41= (float)openright/76;  
		DecimalFormat df41 = new DecimalFormat("0.000000");//格式化小数  
		String s41 = df41.format(num41);//返回的是String类型  
		
		float num42= (float)closeright/152;  
		DecimalFormat df42 = new DecimalFormat("0.000000");//格式化小数  
		String s42 = df42.format(num42);//返回的是String类型  
		
		float num43= (float)turnupright/840;  
		DecimalFormat df43 = new DecimalFormat("0.000000");//格式化小数  
		String s43 = df43.format(num43);//返回的是String类型  
		
		float num44= (float)turndownright/840;  
		DecimalFormat df44 = new DecimalFormat("0.000000");//格式化小数  
		String s44 = df44.format(num44);//返回的是String类型  
		
		float num45= (float)switchupright/37;  
		DecimalFormat df45 = new DecimalFormat("0.000000");//格式化小数  
		String s45 = df45.format(num45);//返回的是String类型  
		
		float num46= (float)switchdownright/37;  
		DecimalFormat df46 = new DecimalFormat("0.000000");//格式化小数  
		String s46 = df46.format(num46);//返回的是String类型  

		
		float num47= (float)pausestartright/5;  
		DecimalFormat df47 = new DecimalFormat("0.000000");//格式化小数  
		String s47 = df47.format(num47);//返回的是String类型  
		System.out.println("其中：");
		System.out.println("   打开命令有76条，解析错误数目"+openwrong+"正确率为"+s41);
		System.out.println("   关闭命令有152条，解析错误数目"+closewrong+"正确率为"+s42);
		System.out.println("   调大命令有840条，解析错误数目"+turnupwrong+"正确率为"+s43);
		System.out.println("   调小命令有840条，解析错误数目"+turndownwrong+"正确率为"+s44);
		System.out.println("   向上切换有37条，解析错误数目"+switchupwrong+"正确率为"+s45);
		System.out.println("   向下切换有37条，解析错误数目"+switchdownwrong+"正确率为"+s46);
		System.out.println("   暂停开始有5条，解析错误数目"+pausestartwrong+"正确率为"+s47);
		
		System.out.println("---------------------------------------------------------------");
		
		System.out.println("总解析指令数目："+totalnumber);
		System.out.println("解析错误数目："+wrongnumber);
		float num5= (float)rightnumber/totalnumber;  
		DecimalFormat df5 = new DecimalFormat("0.000000");//格式化小数  
		String s5 = df5.format(num5);//返回的是String类型  
		System.out.println("准确率为:"+s5);
		
		
		
		
		br1.close();
		br2.close();
		bw.close();
		bw2.close();
		
	}
}
