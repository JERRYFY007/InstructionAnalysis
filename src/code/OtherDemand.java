package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtherDemand {
	public void otherdemand(String OtherDemand) throws Exception {

		BufferedReader br = null;
		BufferedWriter bwotherdemand = null;

		br = new BufferedReader(new FileReader("./File/function.txt"));
		// 此处添加true参数，变为追加，不添加则是覆盖
		bwotherdemand = new BufferedWriter(new FileWriter("./File/AnalysisResult.txt", true));
		Pattern expressionback = Pattern.compile("(后退.*?秒)|(后退.*?分钟)|(后退.*?小时)");
		Matcher matcherback = expressionback.matcher(OtherDemand);
		Pattern expressionback1 = Pattern.compile("进度条|进度");
		Matcher matcherback1 = expressionback1.matcher(OtherDemand);
		if (matcherback.find()&(!matcherback1.find())) {

			OtherDemand = OtherDemand.replace("后退", "进度条后退");

		}
		Pattern expressionforward = Pattern.compile("(前进.*?秒)|(前进.*?分钟)|(前进.*?小时)");
		Matcher matcherforward = expressionforward.matcher(OtherDemand);
		
		if (matcherforward.find()&(!matcherback1.find())) {

			OtherDemand = OtherDemand.replace("前进", "进度条前进");

		}
		
		CutWordsByParam cutword = new CutWordsByParam();
		String re = cutword.cutwordsbyparam(OtherDemand,"./File/Dic.txt");
		
		
		re=re.replace("淡","淡/v ");
		re=re.replace("暗","暗/v ");
		re=re.replace("色/n ","色");
		re=re.replace("彩/ng ","彩");
		re=re.replace("秒钟","秒");
		System.out.println("re:"+re);
		
		re = re.replace("一个/mq", "一/m 个/q");
		re = re.replace("一半/m", "一/m 半/q");
		re = re.replace("点/q", "点/m");
		re = re.replace("调/v 亮点/n", "调亮/v 点/m");
		Pattern expressionchange = Pattern.compile("个格|个级|个档");
		Matcher matcherchange = expressionchange.matcher(OtherDemand);
		if (matcherchange.find()) {

			re = re.replace("级/n", "级/q");
			re = re.replace("格/n", "格/q");
			re = re.replace("档/ng", "档/q");
			
			re = re.replace("/m 个/q", "个/m");

		}
		
		String[] cutresult = re.split(" ");
		Pattern expressionverb = Pattern.compile("/v");
		Pattern expressionshu = Pattern.compile("/m");
		Pattern expressionliang = Pattern.compile("/q");
		Pattern expressionnoun = Pattern.compile("/n");

		String verb = null;
		String shu = null;
		String liang = null;
		String noun = null;

		for (int i = 0; i < cutresult.length; i++) {
			Matcher matcherverb = expressionverb.matcher(cutresult[i]);
			Matcher matchershu = expressionshu.matcher(cutresult[i]);
			Matcher matcherliang = expressionliang.matcher(cutresult[i]);
			Matcher matchernoun = expressionnoun.matcher(cutresult[i]);
			if (matcherverb.find()) {
				verb = matcherverb.replaceAll("");
			}

			else if (matchershu.find()) {
				shu = matchershu.replaceAll("");

			} else if (matcherliang.find()) {
				liang = matcherliang.replaceAll("");

			} else if (matchernoun.find()) {
				noun = matchernoun.replaceAll("");

			}

		}

		// 动词分类，找出所对应的动作指令
		System.out.println("调整之后："+re);
		Pattern expressionverb1 = Pattern.compile("大/v|调亮|调艳|调大|调强|增大|亮|艳|强|高");
		Matcher matcherverb1 = expressionverb1.matcher(re);
		Pattern expressionverb2 = Pattern.compile("小/v|调暗|调淡|调小|调弱|减小|调低|暗|淡|弱|低");
		Matcher matcherverb2 = expressionverb2.matcher(re);
		Pattern expressionverb3 = Pattern.compile("上|前|前进|快进|往前");
		Matcher matcherverb3 = expressionverb3.matcher(re);
		Pattern expressionverb4 = Pattern.compile("暂停|停|停下来|停止");
		Matcher matcherverb4 = expressionverb4.matcher(re);
		Pattern expressionverb5 = Pattern.compile("下|后|后退|倒退|退");
		Matcher matcherverb5 = expressionverb5.matcher(re);
		String VerbType = null;
		String NumType = null;
		String QType = null;
		String ActionType = null;

		if (matcherverb1.find()) {
			VerbType = "调大命令";

		} else if (matcherverb2.find()) {
			VerbType = "调小命令";
		} else if (matcherverb3.find()) {
			VerbType = "向上切换命令";	
		} else if (matcherverb4.find()) {
			VerbType = "暂停命令";
		} else if (matcherverb5.find()) {
			VerbType = "向下切换命令";
		} else {

			VerbType = null;
		}

		// 数词分类，找出所对应的数词类型
		if (shu != null) {
			Pattern expressionNumType = Pattern.compile("第");
			Matcher matcherNumType = expressionNumType.matcher(re);
			Pattern expressionNumType2 = Pattern.compile("点|一点|一些|少许|些许");
			Matcher matcherNumType2 = expressionNumType2.matcher(re);
        
			if (matcherNumType.find()) {
				NumType = "序数词";

			} else if (matcherNumType2.find()) {
				NumType = "约数词";
			} else {
				NumType = "数目词";
			}
		}

		// 量词分类，找出所对应的量词类型
		if (liang != null) {
			Pattern expressionQType = Pattern.compile("个|格|级|档");
			Matcher matcherQType = expressionQType.matcher(re);
			Pattern expressionQType2 = Pattern.compile("倍|半");
			Matcher matcherQType2 = expressionQType2.matcher(re);
			Pattern expressionQType3 = Pattern.compile("秒|分钟|小时");
			Matcher matcherQType3 = expressionQType3.matcher(re);
            if(shu==null){
            	shu=liang;
            	NumType="数目词";
            	liang=null;
            
            }
            else{
			   if (matcherQType.find()) {
				   QType = "单位量词";

			    } else if (matcherQType2.find()) {
				   QType = "倍数量词";
			   } else if (matcherQType3.find()){
				QType = "时间量词";
			   } else {
				      QType = null;
			     }
            }
		}

		// 控制类型分类
		String line=null;
		int state=0;
		if (noun != null) {
			if (noun.equals("频道")|noun.equals("台")) {
				ActionType = "频道控制";
			} else if (noun.equals("节目")) {
				ActionType = "节目控制";
			} else {
				ActionType = "功能控制";
			}
			while ((line = br.readLine()) != null) {
				if (noun.equals(line)) {
					state=1;
					break;
					
				}
			}
		}
		
		
		
		if(state==0){
			
			noun=null;
		}
		if ((shu == null)&(liang==null)) {
			if(VerbType==null|ActionType==null|noun==null){
				System.out.println("error!");
				bwotherdemand.write("error!" + "\n");
			}
			else{
				//"{\"动作指令\":{\"动作\":\"打开命令\",\"类型\":\"" + Object+ "控制\"},\"数词指令\":{\"数词\":\"" + Num + "\",\"类型\":\"" + NumType+ "\"},\"量词指令\":{\"量词\":\"" + Quantity + "\",\"类型\":\"" + QType+ "\"},\"对象指令\":{\"对象\":\"" + Object + "\",\"类型\":\"功能对象\"}}" + "\n"
			System.out.println("{\"动作指令\":{\"动作\":\""+ VerbType + "\",\"类型\":\"" + ActionType + "\"},\"对象指令\":{\"对象\":\""  + noun +"\",\"类型\":\"功能对象\"}}" + "\n");
			bwotherdemand.write("{\"动作指令\":{\"动作\":\""+ VerbType + "\",\"类型\":\"" + ActionType +"\"},\"对象指令\":{\"对象\":\""  + noun +"\",\"类型\":\"功能对象\"}}" + "\n");
			}
		}
		else if ((shu == null)&(liang!=null)) {
			if(VerbType==null|ActionType==null|noun==null|QType==null){
				System.out.println("error!");
				bwotherdemand.write("error!" + "\n");
			}
			else{
				//"{\"动作指令\":{\"动作\":\"打开命令\",\"类型\":\"" + Object+ "控制\"},\"数词指令\":{\"数词\":\"" + Num + "\",\"类型\":\"" + NumType+ "\"},\"量词指令\":{\"量词\":\"" + Quantity + "\",\"类型\":\"" + QType+ "\"},\"对象指令\":{\"对象\":\"" + Object + "\",\"类型\":\"功能对象\"}}" + "\n"
			System.out.println("{\"动作指令\":{\"动作\":\""+ VerbType +  "\",\"类型\":\"" + ActionType +"\"},\"量词指令\":{\"量词\":\"" +liang+ "\",\"类型\":\""+QType+"\"},\"对象指令\":{\"对象\":\"" + noun + "\",\"类型\":\"功能对象\"}}" + "\n");
			bwotherdemand.write("{\"动作指令\":{\"动作\":\""+ VerbType +  "\",\"类型\":\"" + ActionType +"\"},\"量词指令\":{\"量词\":\"" +liang+ "\",\"类型\":\""+QType+"\"},\"对象指令\":{\"对象\":\"" + noun + "\",\"类型\":\"功能对象\"}}" + "\n");
			}
		}
		else if ((shu != null) & (liang == null)) {
			if(VerbType==null|ActionType==null|noun==null){
				System.out.println("error!");
				bwotherdemand.write("error!" + "\n");
			}
			else{
			System.out.println("{\"动作指令\":{\"动作\":\""+ VerbType +  "\",\"类型\":\"" + ActionType + "\"},\"数词指令\":{\"数词\":\"" + shu +  "\",\"类型\":\"" + NumType+"\"},\"对象指令\":{\"对象\":\""+ noun +  "\",\"类型\":\"功能对象\"}}" + "\n");
			bwotherdemand.write("{\"动作指令\":{\"动作\":\""+ VerbType +  "\",\"类型\":\"" + ActionType + "\"},\"数词指令\":{\"数词\":\"" + shu +  "\",\"类型\":\"" + NumType+"\"},\"对象指令\":{\"对象\":\""+ noun +  "\",\"类型\":\"功能对象\"}}" + "\n");
			}
		}
		else if ((shu != null) & (liang != null)) {
			if(VerbType==null|ActionType==null|noun==null|QType==null){
				System.out.println("error!");
				bwotherdemand.write("error!" + "\n");
			}
			else{
			System.out.println("{\"动作指令\":{\"动作\":\""+ VerbType +  "\",\"类型\":\"" + ActionType + "\"},\"数词指令\":{\"数词\":\""  + shu +  "\",\"类型\":\"" + NumType+ "\"},\"量词指令\":{\"量词\":\"" + liang +  "\",\"类型\":\"" + QType+ "\"},\"对象指令\":{\"对象\":\"" + noun + "\",\"类型\":\"功能对象\"}}" + "\n");
			bwotherdemand.write("{\"动作指令\":{\"动作\":\""+ VerbType +  "\",\"类型\":\"" + ActionType + "\"},\"数词指令\":{\"数词\":\""  + shu +  "\",\"类型\":\"" + NumType+ "\"},\"量词指令\":{\"量词\":\"" + liang +  "\",\"类型\":\"" + QType+ "\"},\"对象指令\":{\"对象\":\"" + noun + "\",\"类型\":\"功能对象\"}}" + "\n");
			}
		}


		bwotherdemand.close();
		br.close();

	}
	public static void main(String[] args) throws Exception {
		OtherDemand od=new OtherDemand();

		od.otherdemand("进度条快进5分钟");
	}

}
