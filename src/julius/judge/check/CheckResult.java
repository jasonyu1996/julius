package julius.judge.check;

public class CheckResult {
	private int score;
	private String info;
	
	protected CheckResult(int score, String info){
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
