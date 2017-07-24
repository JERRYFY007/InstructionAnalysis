package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replace {
	   
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
        
		BufferedReader br = null;
		BufferedWriter bw = null;
	

		br = new BufferedReader(new FileReader("./File/funcjson.txt"));
		bw = new BufferedWriter(new FileWriter("./File/funcjson2.txt"));


		String line = null;
		int num=0;
        int i=0;
		while ((line = br.readLine()) != null) {
			line=line.replace(" ","");//06/03修改，去空格
			// Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Pattern expression1 = Pattern.compile("\"对象指令.+?}");
			Matcher matcher1 = expression1.matcher(line);
			
			Pattern expression2 = Pattern.compile("\"量词指令.+?\"}");
			Matcher matcher2 = expression2.matcher(line);
//             String re="";
//			// "类型":"倍数量词",
//		   Pattern expression3 = Pattern.compile("\"类型.+?量词\",");
//			Matcher matcher3 = expression3.matcher(line);
             String m1="";
            
  

			if ((matcher1.find())&&(matcher2.find())) {
				// 删除打开或点播部分，其余部分进行点播或打开解析
				
			    m1=matcher1.group(0);
				line = matcher1.replaceAll("");

                line=line.replace("}}","},"+m1+"}");
                line=line.replace(",,",",");
				
			}  
	
			    line=line.replace("\"类型\":\"功能对象\",","");
                line=line.replace("}}",","+"\"类型\":\"功能对象\""+"}}");
//             if (matcher3.find()) {
//    				// 删除打开或点播部分，其余部分进行点播或打开解析
//    				
//    			    m3=matcher3.group(0);
//    			    System.out.println(m3);
//    				line = matcher3.replaceAll("");
//                    line=line.replace("},\"对象指令",m3+"},\"对象指令");
//                    line=line.replace(",,",",");
//    				
//    			}  
				
			
	    
			bw.write(line+"\n");
		
		i++;
		}
		System.out.println(i);
		System.out.println("num："+num);
		br.close();
		bw.close();
		BufferedReader br1 = null;
		BufferedWriter bw1 = null;
		br1 = new BufferedReader(new FileReader("./File/funcjson2.txt"));
		bw1 = new BufferedWriter(new FileWriter("./File/funcjson3.txt"));


		String line2 = null;
	
		while ((line2 = br1.readLine()) != null) {
			line2=line2.replace(" ","");//06/03修改，去空格
			// Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			 String m3="";
		   Pattern expression3 = Pattern.compile("\"类型.{4,8}量词\"");
			Matcher matcher3 = expression3.matcher(line2);
  
			Pattern expression4 = Pattern.compile("秒|分钟|小时");
		    Matcher matcher4 = expression4.matcher(line2);

		
             if (matcher3.find()) {
    				// 删除打开或点播部分，其余部分进行点播或打开解析
    				
    			    m3=matcher3.group(0);
    			    System.out.println(m3);
    				line2 = matcher3.replaceAll("");
                    line2=line2.replace("},\"对象指令",","+m3+"},\"对象指令");
                    // {,"量词
                    line2=line2.replace("{,\"量词","{\"量词");
    				
    			}  
             if (matcher4.find()) {
 				// 删除打开或点播部分，其余部分进行点播或打开解析
 				
 			
                 line2=line2.replace("单位量词","时间量词");
             
 				
 			}  
				
			
	    
			bw1.write(line2+"\n");
		
		}
	
		br1.close();
		bw1.close();
		long end = System.currentTimeMillis();
		float total=(float) (1.0*(end-start)/1000);
        System.out.println("运行时间：" + total + "秒");

	}
}
