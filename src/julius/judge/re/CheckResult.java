package julius.judge.re;

public class CheckResult {
	private int score;
	private String info;
	
	public CheckResult(int score, String info){
		this.score = score;
		this.info = info;
	}

	public int getScore() {
		return score;
	}

	public String getInfo() {
		return info;
	}
}
