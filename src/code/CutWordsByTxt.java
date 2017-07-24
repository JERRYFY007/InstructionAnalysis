package code;
//完成分词和词性标注，采用用户自定义词典。

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class CutWordsByTxt {

	// 定义接口CLibrary，继承自com.sun.jna.Library
	public interface CLibrary extends Library {
		// 定义并初始化接口的静态变量
		CLibrary Instance = (CLibrary) Native.loadLibrary(
				"./source/NLPIR", CLibrary.class);
		
		public int NLPIR_Init(String sDataPath, int encoding,
				String sLicenceCode);
				
		public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

		public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,
				boolean bWeightOut);
		public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,
				boolean bWeightOut);
		public int NLPIR_AddUserWord(String sWord);//add by qp 2008.11.10
		public int NLPIR_DelUsrWord(String sWord);//add by qp 2008.11.10
		public String NLPIR_GetLastErrorMsg();
		public void NLPIR_Exit();

		public void NLPIR_FileProcess(String txt_input, String txt_output, int i);
	}

	public static String transString(String aidString, String ori_encoding,
			String new_encoding) {
		try {
			return new String(aidString.getBytes(ori_encoding), new_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
   public void  cutwords(String input,String output, String dic){
	   String argu = "./";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "UTF-8";
		int charset_type = 1;
		
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;

		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！fail reason is "+nativeBytes);
			return;
		}

		
       BufferedReader br = null;
       BufferedReader brdic = null;
       BufferedWriter bw = null;
       try {
           br = new BufferedReader(new FileReader(input));
           brdic = new BufferedReader(new FileReader(dic));
           bw = new BufferedWriter(new FileWriter(output));
           String dicline = null;
           while (true) {       
               dicline = brdic.readLine(); // BufferedReader 的readline 方法，直接读取一行数据
               if (dicline == null) {
                   break;
               } else {
               	CLibrary.Instance.NLPIR_AddUserWord(dicline);
               }
           }
           String txt_input = input;   // 读入文本  
           String txt_output = output;    // 输出文本  
           try {  
               //  对读入文本分词，并导出结果为另一个文本，要注意读入文本的编码格式（这里为UTF-8)  
               CLibrary.Instance.NLPIR_FileProcess(txt_input,txt_output,1);  
               CLibrary.Instance.NLPIR_Exit();  
               System.out.println("已完成分词和词性标注！");
           } catch (Exception ex) {  
               // TODO Auto-generated catch block  
               ex.printStackTrace();  
           }  

          } catch (Exception e) {
                System.out.println(e);
                 } 
         finally {
         try {
             bw.close();
             br.close();
         } catch (IOException e) {
             System.out.println(e);
         }
     }

   }
   public static void main(String[] args) {
	   CutWordsByTxt cw=new CutWordsByTxt();
		String input="./File/TXT.txt";
		String output="./File/RE.txt";
		String dic="./File/Dic.txt";
		cw.cutwords(input, output, dic);
}
	
}
