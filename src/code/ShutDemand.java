package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ShutDemand {
	public void shutdemand(String ShutDemand) throws Exception {

		BufferedReader br = null;
		BufferedWriter bwshutdemand = null;

		br = new BufferedReader(new FileReader("./File/function.txt"));
		// 此处添加true参数，变为追加，不添加则是覆盖
		bwshutdemand = new BufferedWriter(new FileWriter("./File/AnalysisResult.txt", true));
		System.out.println("ShutDemand:"+ShutDemand);
		String line = null;
		int state=0;

		// 功能对象匹配

		while ((line = br.readLine()) != null) {
			if (ShutDemand.equals(line)) {
				System.out.println("匹配到功能对象: " + line);
				System.out.println("{\"动作指令\":{\"动作\":\"关闭命令\",\"类型\":\"功能控制\"},\"对象指令\":{\"对象\":\"" + ShutDemand +"\",\"类型\":\"功能对象\"}}" + "\n");
				bwshutdemand.write("{\"动作指令\":{\"动作\":\"关闭命令\",\"类型\":\"功能控制\"},\"对象指令\":{\"对象\":\"" + ShutDemand +"\",\"类型\":\"功能对象\"}}" + "\n");
				state=1;
				break;
				
			}
		}
		if(state==0){
			System.out.println("关闭命令error!");
			
			bwshutdemand.write("error!" + "\n");
		}
		bwshutdemand.close();
		br.close();

	}

	public static void main(String[] args) throws Exception {
		ShutDemand sd = new ShutDemand();
		sd.shutdemand("电视机");

	}

}
