package julius.judge.re;

public class TestcaseResult {
	private RuntimeResult runtimeResult;
	private CheckResult checkResult;
	
	public TestcaseResult(RuntimeResult runtimeResult, CheckResult checkResult){
		this.runtimeResult = runtimeResult;
		this.checkResult = checkResult;
	}
	
	public int getScore(){
		if(runtimeResult.getStatus() != RuntimeStatus.NORMAL)
			return 0;
		return checkResult.getScore();
	}
}
