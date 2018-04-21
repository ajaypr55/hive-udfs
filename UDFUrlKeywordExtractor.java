package uri_keyword_extractor;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;

public class UDFUrlKeywordExtractor extends UDF {
	private  Text result = new Text();

	public  Text evaluate(Text url) {
		if (url == null) {
			return null;
		}
		String keywords = url_keyword_maker(url.toString());
		result.set(keywords);
		return result;
	}

	private static String url_keyword_maker(String url) {
		// TODO Auto-generated method stub
		ArrayList<String> keywordAr = new ArrayList<String>();
		char[] charAr = url.toCharArray();
		for (int i = 0; i < charAr.length; i++) {
			int current_index = i;
			// check if character is a-z or A-Z
			char ch = charAr[i];
			StringBuilder sb = new StringBuilder();
			while (current_index < charAr.length-1 && isChar(ch)) {
				sb.append(ch);
				current_index = current_index+1;
				ch = charAr[current_index];
			}
			String word = sb.toString();
			if (word.length() >= 2) {
				keywordAr.add(word);
			}
			i = current_index;
		}
		//
		StringBuilder sb = new StringBuilder();
		for(int i =0; i < keywordAr.size();i++) {
			String current = keywordAr.get(i);
			sb.append(current);
			if(i < keywordAr.size() -1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private static  boolean isChar(char ch) {
		// TODO Auto-generated method stub
		int ascii_value = (int) ch;
		// A-Z => (65,90) a-z => (97,122)
		// condition 1 : A-Z , condition 2 : a-z character check
		if (  (ascii_value >= 65 && ascii_value <= 90)  ||  (ascii_value >= 97 && ascii_value <= 122) ) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test1 = "file:///storage/emulated/0/SHAREit/videos/Dangerous_Hero_(2017)____Latest_South_Indian_Full_Hindi_Dubbed_Movie___2017_.mp4";
		String test2 = "file:///storage/emulated/0/VidMate/download/%E0%A0_-_Promo_Songs_-_Khiladi_-_Khesari_Lal_-_Bho.mp4";
		String test3 = "file:///storage/emulated/0/bluetooth/%5DChitaChola%7B%7D%D8%B9%D8%A7%D9%85%D8%B1%24%20.3gp";
		System.out.println(url_keyword_maker(test1).toString());
		System.out.println(url_keyword_maker(test2).toString());
		System.out.println(url_keyword_maker(test3).toString());
	}
}
