package julius.util;

public class StringCompressor {
	public static String compress(String a, int len){
		if(a.length() <= len)
			return a;
		return a.substring(0, len) + "...";
	}
}
