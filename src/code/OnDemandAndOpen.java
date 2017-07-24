package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnDemandAndOpen {
	public void ondemandandopen(String OnDemandAndOpenInstruction)throws Exception {
		int state = 0;
		BufferedReader br = null;
		BufferedWriter bwOnDemandAndOpen = null;

		br = new BufferedReader(new FileReader("./File/function.txt"));
		// 此处添加true参数，变为追加，不添加则是覆盖
		bwOnDemandAndOpen = new BufferedWriter(new FileWriter("./File/AnalysisResult.txt", true));
		String line = null;
		String Object = null;
		String Control=null;

		// 功能对象匹配
		
	    // 先对“切换到第一个频道”，这个很容易歧义到点播命令的指令进行解析。
		Pattern expressiona = Pattern.compile("台|频道|节目");
		Matcher matchera = expressiona.matcher(OnDemandAndOpenInstruction);
		if (matchera.find()) {
			Pattern expressionb = Pattern.compile("(上/v|第)");
			Matcher matcherb = expressionb.matcher(OnDemandAndOpenInstruction);
			if (matcherb.find()) {			
				CutWordsByParam cutword = new CutWordsByParam();
				String re = cutword.cutwordsbyparam(OnDemandAndOpenInstruction,"./File/Dic.txt");
				re = re.replace("一个/mq", "一/m 个/q");
				Pattern expressionQuantity = Pattern.compile("/q");
				Matcher matcherQuan = expressionQuantity.matcher(re);
				if(matcherQuan.find()){
			       state = 1;
				   Pattern expressionnum = Pattern.compile("/m.*");
				   Matcher matchernum = expressionnum.matcher(re);
				   String Num = matchernum.replaceAll("");
				   Pattern expressionQ = Pattern.compile("\\s.{0,}/q");
				   Matcher matcherQ = expressionQ.matcher(re);
				   String Quantity = null;
				   while (matcherQ.find())
				       {  

					    Quantity = matcherQ.group(0).replace(" ", "");//删除”个“前面的空格
					    Quantity = Quantity.replace("/q", "");

				       }
				  Pattern expressionChannel = Pattern.compile("/q.+?/n");
				  Matcher matcherchannel = expressionChannel.matcher(re);
				  String Channel = null;
				  while (matcherchannel.find()) {
					    Channel = matcherchannel.group(0).replace("/q ", "");
					    Channel = Channel.replace("/n", "");

				  }
				  if (Channel.equals("频道")) {
					Control="频道";
					Object = "频道";
				  }else if (Channel.equals("台")) {
					Control="频道";
					Object = "台";
				  }
				  else if (Channel.equals("节目")) {
					Control="节目";
					Object = "节目";
				  }
				  Pattern expressionNumType = Pattern.compile("第");
				  Matcher matcherNumType = expressionNumType.matcher(re);
				  Pattern expressionNumType2 = Pattern.compile("一点|一些|少许");
				  Matcher matcherNumType2 = expressionNumType2.matcher(re);
				  String NumType = null;

				  if (matcherNumType.find()) {
					NumType = "序数词";

				  } else if (matcherNumType2.find()) {
					NumType = "约数词";
				  } else {
					NumType = "数目词";
				  }
				  Pattern expressionQType = Pattern.compile("个|格|级|档");
				  Matcher matcherQType = expressionQType.matcher(re);
				  Pattern expressionQType2 = Pattern.compile("倍|半");
				  Matcher matcherQType2 = expressionQType2.matcher(re);
				  Pattern expressionQType3 = Pattern.compile("秒|分钟|小时");
				  Matcher matcherQType3 = expressionQType3.matcher(re);
				  String QType = null;
				  if (matcherQType.find()) {
					  QType = "单位量词";

				  } else if (matcherQType2.find()) {
					  QType = "倍数量词";
				  } else if (matcherQType3.find())
				  {
					  QType = "时间量词";
				  } else {
					  QType = null;
				  }
				if(Object==null){
					System.out.println("error!");
					bwOnDemandAndOpen.write("error!" + "\n");
				}
				else{
				System.out.println("匹配到功能对象: " + Object);
				System.out.println("{\"动作指令\":{\"动作\":\"打开命令\",\"类型\":\"" +Control+ "控制\"},\"数词指令\":{\"数词\":\"" + Num + "\",\"类型\":\"" + NumType+ "\"},\"量词指令\":{\"量词\":\"" + Quantity + "\",\"类型\":\"" + QType+ "\"},\"对象指令\":{\"对象\":\"" + Object + "\",\"类型\":\"功能对象\"}}" + "\n");
				bwOnDemandAndOpen.write("{\"动作指令\":{\"动作\":\"打开命令\",\"类型\":\"" + Control+ "控制\"},\"数词指令\":{\"数词\":\"" + Num + "\",\"类型\":\"" + NumType+ "\"},\"量词指令\":{\"量词\":\"" + Quantity + "\",\"类型\":\"" + QType+ "\"},\"对象指令\":{\"对象\":\"" + Object + "\",\"类型\":\"功能对象\"}}" + "\n");
				}
				}
			}

		}if(state==0){
			//功能对象匹配，解析打开指令
			while ((line = br.readLine()) != null) {
				if (OnDemandAndOpenInstruction.equals(line)) {

					Pattern expressionObj = Pattern.compile("机");
					Matcher matcherObj = expressionObj.matcher(OnDemandAndOpenInstruction);

					if (matcherObj.find()) {
						OnDemandAndOpenInstruction = "电视机";

					}
					System.out.println("匹配到功能对象: " + line);
					//"{\"动作指令\":{\"动作\":\"打开命令\",\"类型\":\"" + Object+ "控制\"},\"数词指令\":{\"数词\":\"" + Num + "\",\"类型\":\"" + NumType+ "\"},\"量词指令\":{\"量词\":\"" + Quantity + "\",\"类型\":\"" + QType+ "\"},\"对象指令\":{\"对象\":\"" + Object + "\",\"类型\":\"功能对象\"}}" + "\n"
					System.out.println("{\"动作指令\":{\"动作\":\"打开命令\",\"类型\":\"功能控制\"},\"对象指令\":{\"对象\":\"" +OnDemandAndOpenInstruction+ "\",\"类型\":\"功能对象\"}}" + "\n");
					bwOnDemandAndOpen.write("{\"动作指令\":{\"动作\":\"打开命令\",\"类型\":\"功能控制\"},\"对象指令\":{\"对象\":\"" +OnDemandAndOpenInstruction+ "\",\"类型\":\"功能对象\"}}" + "\n");
					state = 1;
					break;
				}

			}

		}

		// 频道对象匹配
		if (state == 0) {
			Pattern expression1 = Pattern.compile("台|卫视|套|频道|TV|tv|高清|卡通|Channel|CHC");
			Matcher matcher1 = expression1.matcher(OnDemandAndOpenInstruction);

			if (matcher1.find()) {
				System.out.println("匹配到频道对象: " + OnDemandAndOpenInstruction);
				//"{\"动作指令\":{\"动作\":\"打开命令\",\"类型\":\"" + Object+ "控制\"},\"数词指令\":{\"数词\":\"" + Num + "\",\"类型\":\"" + NumType+ "\"},\"量词指令\":{\"量词\":\"" + Quantity + "\",\"类型\":\"" + QType+ "\"},\"对象指令\":{\"对象\":\"" + Object + "\",\"类型\":\"功能对象\"}}" + "\n"
				System.out.println("{\"动作指令\":{\"动作\":\"点播命令\",\"类型\":\"频道控制\"},\"对象指令\":{\"对象\":\"" + OnDemandAndOpenInstruction +"\",\"类型\":\"频道对象\"}}" + "\n");
				bwOnDemandAndOpen.write("{\"动作指令\":{\"动作\":\"点播命令\",\"类型\":\"频道控制\"},\"对象指令\":{\"对象\":\"" + OnDemandAndOpenInstruction +"\",\"类型\":\"频道对象\"}}" + "\n");
			}

			// 节目对象匹配,前面的功能和频道对象匹配不成功之后，只要不为空，默认是节目对象，否则返回error
			else if (!(OnDemandAndOpenInstruction.equals("") | OnDemandAndOpenInstruction == null)) {
				System.out.println("匹配到节目对象: " + OnDemandAndOpenInstruction);
				System.out.println("{\"动作指令\":{\"动作\":\"点播命令\",\"类型\":\"节目控制\"},\"对象指令\":{\"对象\":\"" + OnDemandAndOpenInstruction + "\",\"类型\":\"节目对象\"}}" + "\n");
				bwOnDemandAndOpen.write("{\"动作指令\":{\"动作\":\"点播命令\",\"类型\":\"节目控制\"},\"对象指令\":{\"对象\":\"" + OnDemandAndOpenInstruction + "\",\"类型\":\"节目对象\"}}" + "\n");
			} else {

				System.out.println("error!" + OnDemandAndOpenInstruction);
				bwOnDemandAndOpen.write("error!" + "\n");

			}
		}
		bwOnDemandAndOpen.close();
		br.close();

	}

	public static void main(String[] args) throws Exception {
		OnDemandAndOpen oda = new OnDemandAndOpen();
//		oda.ondemandandopen("人民的名义");
//		oda.ondemandandopen("向往的生活");
//		oda.ondemandandopen("新疆卫视");
//		oda.ondemandandopen("芒果台");
//		oda.ondemandandopen("中央五套");
//		oda.ondemandandopen("央视综合频道");
//		oda.ondemandandopen("cctv12");
		oda.ondemandandopen("第一个台");
//		oda.ondemandandopen("第10个节目");
//		oda.ondemandandopen("合肥电视台第一剧场频道");

	}
}
