package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class CutWordsByParam {

	// 定义接口CLibrary，继承自com.sun.jna.Library
	public interface CLibrary extends Library {
		// 定义并初始化接口的静态变量
		CLibrary Instance = (CLibrary) Native.loadLibrary("./source/NLPIR",CLibrary.class);

		public int NLPIR_Init(String sDataPath, int encoding,String sLicenceCode);

		public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

		public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,boolean bWeightOut);

		public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,boolean bWeightOut);

		public int NLPIR_AddUserWord(String sWord);// add by qp 2008.11.10

		public int NLPIR_DelUsrWord(String sWord);// add by qp 2008.11.10

		public String NLPIR_GetLastErrorMsg();

		public void NLPIR_Exit();
	}

	public static String transString(String aidString, String ori_encoding,String new_encoding) {
		try {
			return new String(aidString.getBytes(ori_encoding), new_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String cutwordsbyparam(String sentence, String dic) throws Exception {
		String argu = "./";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "UTF-8";
		int charset_type = 1;

		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;

		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！fail reason is " + nativeBytes);
			return nativeBytes;
		}

		BufferedReader brdic = null;
		brdic = new BufferedReader(new FileReader(dic));
		String dicline = null;
		while (true) {
			dicline = brdic.readLine(); // BufferedReader 的readline 方法，直接读取一行数据
			if (dicline == null) {
				break;
			} else {
				CLibrary.Instance.NLPIR_AddUserWord(dicline);
			}
		}
		brdic.close();

		try {
			nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(sentence, 1);
			System.out.println("分词结果为： " + nativeBytes);
			CLibrary.Instance.NLPIR_Exit();

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return nativeBytes;

	}

	public static void main(String[] args) throws Exception {
		CutWordsByParam cut = new CutWordsByParam();
		String a = cut.cutwordsbyparam("变压器是什么", "./File/Dic.txt");
//		String b = cut.cutwordsbyparam("开开封电视台经济生活频道", "./File/Dic.txt");
//		String c = cut.cutwordsbyparam("看中国教育空中课堂", "./File/Dic.txt");
//		String d = cut.cutwordsbyparam("屏幕调艳点", "./File/Dic.txt");
//		String e = cut.cutwordsbyparam("屏幕调亮点", "./File/Dic.txt");
//		String f = cut.cutwordsbyparam("屏幕调亮一点", "./File/Dic.txt");
//		String g = cut.cutwordsbyparam("开心超人看", "./File/Dic.txt");

	}
}
