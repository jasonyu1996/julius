package julius.org;

public class Pattern {
	private String pattern;
	public Pattern(String pattern){
		this.pattern = pattern;
	}
	
	public static String getPrefixFileName(String fname){
		return fname.substring(0, Math.max(0, fname.lastIndexOf('.')));
	}
	
	public String parse(String fname){
		return pattern.replace("%<", getPrefixFileName(fname)).replace("%", fname);
	}
}
