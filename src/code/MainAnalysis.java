package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAnalysis {
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
        
		BufferedReader br = null;
		BufferedWriter bw=null;
		BufferedWriter bw1=null;
		br = new BufferedReader(new FileReader("./File/all.txt"));
		bw = new BufferedWriter(new FileWriter("./File/AnalysisResult.txt"));
		bw.write("");
		bw.close();
		bw1 = new BufferedWriter(new FileWriter("./File/AnalysisResult.txt",true));
		

		String line = null;

		while ((line = br.readLine()) != null) {
			line=line.replace(" ","");//去空格
			
			//对最新给的5个暂停开始命令直接给出结果
			if(line.equals("开始")|line.equals("暂停")|line.equals("开始播放")|line.equals("重新开始")|line.equals("重新播放")){
				
				System.out.println("{\"动作指令\":{\"动作\":\"暂停开始命令\",\"类型\":\"功能控制\"},\"对象指令\":{\"对象\":\"" + line+"\",\"类型\":\"功能对象\"}}" + "\n");
				bw1.write("{\"动作指令\":{\"动作\":\"暂停开始命令\",\"类型\":\"功能控制\"},\"对象指令\":{\"对象\":\"" +line +"\",\"类型\":\"功能对象\"}}" + "\n");
			}
			//把所有命令按照分为打开(播放)命令、关闭命令、其他命令。
			//前面的打开(播放)和关闭命令不使用分词和词性标注，只进行匹配和使用一些规则。
			//而其他命令中则使用了分词和词性标注，基于词性和添加的一些规则进行指令的识别。
			else{
			Pattern expression1 = Pattern.compile("换到|打开|切换到|播放|进入|进到");
			Matcher matcher1 = expression1.matcher(line);
			
			Pattern expressionsee = Pattern.compile("看|开");//优先前面的打开播放关键词，而次之选择“看”和“开”，是因为前面的关键词更有可能是打开播放关键词
			Matcher matchersee = expressionsee.matcher(line);  
			
			Pattern expression2 = Pattern.compile("切换");
			Matcher matcher2 = expression2.matcher(line);

			Pattern expression3 = Pattern.compile("关掉|关闭|关了|退出|结束");
			Matcher matcher3 = expression3.matcher(line);

			Pattern expression4 = Pattern.compile("关");
			Matcher matcher4 = expression4.matcher(line);

			if (matcher1.find()) {
				// 删除打开或点播关键词，其余部分进行点播或打开解析
				System.out.println("打开命令：  " + line);
				line = matcher1.replaceAll("");
				
				OnDemandAndOpen oda = new OnDemandAndOpen();
				oda.ondemandandopen(line);
			} 
			else if (matchersee.find()) {
				System.out.println("打开命令：  " + line);
				CutWordsByParam cutword = new CutWordsByParam();
				String cutre = cutword.cutwordsbyparam(line,"./File/Dic.txt");
				Pattern expressionseev = Pattern.compile("看/v[\\s$]");//动词“看”，后面是空格或者是结尾符

				Matcher matcherseev = expressionseev.matcher(cutre);
				
				Pattern expressionopen = Pattern.compile("开/v[\\s$]");

				Matcher matcheropen = expressionopen.matcher(cutre);
				
		
				
			    if (matcherseev.find()) {
			    	    cutre = matcherseev.replaceFirst("");
			            Pattern expressionother = Pattern.compile("/.+?[\\s$]");//去词性标注
						Matcher matcherother = expressionother.matcher(cutre);
						while (matcherother.find()){
							line = matcherother.replaceAll("");
							line=line.replace(" ", "");
						}
						OnDemandAndOpen oda = new OnDemandAndOpen();
						oda.ondemandandopen(line);
			    }
			    else if(matcheropen.find()){
			    	cutre = matcheropen.replaceFirst("");
		            Pattern expressionother = Pattern.compile("/.+?[\\s$]");
					Matcher matcherother = expressionother.matcher(cutre);
					while (matcherother.find()){
						line = matcherother.replaceAll("");
						line=line.replace(" ", "");
					}
					OnDemandAndOpen oda = new OnDemandAndOpen();
					oda.ondemandandopen(line);
			    	
			    }
			    else{
					line = matchersee.replaceFirst("");
					OnDemandAndOpen oda = new OnDemandAndOpen();
					oda.ondemandandopen(line);

			    }
				

			} 

			else if (matcher2.find()) {
				System.out.println("打开命令：  " + line);
				line = matcher2.replaceAll("");
				
				OnDemandAndOpen oda = new OnDemandAndOpen();
				oda.ondemandandopen(line);

			} 
			
			else if (matcher3.find()) {
				// 删除关闭的关键词，其余部分进行解析

				System.out.println("关闭命令：  " + line);
				line = matcher3.replaceAll("");
				
				ShutDemand sd = new ShutDemand();
				sd.shutdemand(line);
			} 
			else if (matcher4.find()) {
				System.out.println("关闭命令：  " + line);
				line = matcher4.replaceAll("");
				
				ShutDemand sd = new ShutDemand();
				sd.shutdemand(line);

			} 
			else {
				// 对除了打开、点播、关闭之外的命令进行解析
				System.out.println("其余命令：  " + line);
				
				OtherDemand od = new OtherDemand();
				od.otherdemand(line);
			}
			}
		}
		
		
		
		br.close();
		bw1.close();
		long end = System.currentTimeMillis();
		float total=(float) (1.0*(end-start)/1000);
        System.out.println("运行时间：" + total + "秒");

	}
}
