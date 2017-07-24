package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class Compare {

	public static void main(String[] args) throws Exception {
		BufferedReader br1 = null;
		BufferedReader br2=null;
		
		BufferedWriter bw=null;
		BufferedWriter bw2=null;
		br1 = new BufferedReader(new FileReader("./File/alljson.txt"));
		br2 = new BufferedReader(new FileReader("./File/AnalysisResult.txt"));
		bw = new BufferedWriter(new FileWriter("./File/Wrong.txt"));
		bw2 = new BufferedWriter(new FileWriter("./File/WrongList.txt"));
	

		String line1 = null;
		String line2 = null;
		String line3 = null;
		int totalnumber=0;
		int rightnumber=0;
		int wrongnumber=0;
		int errornumber=0;
		
		int multinumber=64;
		int multiright=0;
		int multiwrong=0;
		int multierror=0;

		int firsttv=65;
		int tvright=0;
		int tvwrong=0;
		int tverror=0;
		
		int firstpro=21681;
		int proright=0;
		int prowrong=0;
		int proerror=0;
		while (((line1 = br1.readLine()) != null)&&((line2 = br2.readLine()) != null)) {
			totalnumber++;
			if(line2.equals("error")){
				errornumber++;
				if(totalnumber<65){
					multierror++;
				}
				else if(totalnumber<21681){
					tverror++;
				}
				else{
					proerror++;
					
				}
			}
				
			
			
			if(line1.equals(line2)){
				rightnumber++;
				if(totalnumber<65){
					multiright++;
				}
				else if(totalnumber<21681){
					tvright++;
				}
				else{
					proright++;
					
				}
				
			}
			else
			{   int j=1;
			    BufferedReader br3=null;
			    br3 = new BufferedReader(new FileReader("./File/all.txt"));
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
				else{
					prowrong++;
					
				}
			
			
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
		System.out.println("总解析指令数目："+totalnumber);
		System.out.println("完全正确数目："+rightnumber+"，其中返回error的指令有"+errornumber+"条");
		System.out.println("解析错误数目："+wrongnumber);
		float num= (float)rightnumber/totalnumber;  
		DecimalFormat df = new DecimalFormat("0.000000");//格式化小数  
		String s = df.format(num);//返回的是String类型  
		System.out.println("准确率为:"+s);
		
		System.out.println("----------------------------");
		
		System.out.println("混合指令数目："+64);
		System.out.println("完全正确数目："+multiright+"，其中返回error的指令有"+multierror+"条");
		System.out.println("解析错误数目："+multiwrong);
		float num1= (float)multiright/64;  
		DecimalFormat df1 = new DecimalFormat("0.000000");//格式化小数  
		String s1 = df1.format(num1);//返回的是String类型  
		System.out.println("准确率为:"+s1);
		
		System.out.println("----------------------------");
		
		System.out.println("频道指令数目："+21616);
		System.out.println("完全正确数目："+tvright+"，其中返回error的指令有"+tverror+"条");
		System.out.println("解析错误数目："+tvwrong);
		float num2= (float)tvright/21616;  
		DecimalFormat df2 = new DecimalFormat("0.000000");//格式化小数  
		String s2 = df1.format(num2);//返回的是String类型  
		System.out.println("准确率为:"+s2);
		
		System.out.println("----------------------------");
		
		System.out.println("节目指令数目："+21616);
		System.out.println("完全正确数目："+proright+"，其中返回error的指令有"+proerror+"条");
		System.out.println("解析错误数目："+prowrong);
		float num3= (float)proright/21616;  
		DecimalFormat df3 = new DecimalFormat("0.000000");//格式化小数  
		String s3 = df3.format(num3);//返回的是String类型  
		System.out.println("准确率为:"+s3);
		
		
		br1.close();
		br2.close();
		bw.close();
		bw2.close();
		
	}
}
