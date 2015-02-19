package julius.judge.check;

public class DiffCheckResult extends CheckResult {
	private String expected, read;
	private int lineNumber;
	
	private static String compress(String a, int len){
		if(a.length() <= len)
			return a;
		return a.substring(0, len) + "...";
	}
	
	protected DiffCheckResult(int score, int lineNumber, String read, String expected){
		super(score,  "Line " + lineNumber + " differs: read: " + compress(read, 20) + " expected: " + compress(expected, 20));
		
		this.lineNumber = lineNumber;
		this.read = read;
		this.expected = expected;
	}
	
	protected DiffCheckResult(int score, int lineNumber, String info){
		super(score, "Line " + lineNumber + ": " + info);
	
		this.lineNumber = lineNumber;
	}
	
	protected DiffCheckResult(int score){
		super(score, "Ok");
		
		this.lineNumber = 0;
	}

	public String getExpected() {
		return expected;
	}

	public String getRead() {
		return read;
	}

	public int getLineNumber() {
		return lineNumber;
	}
}
