package julius.judge;

import julius.judge.check.CheckResult;
import julius.judge.runtime.RuntimeResult;
import julius.judge.runtime.RuntimeStatus;

public class TestcaseResult {
	private RuntimeResult runtimeResult;
	private CheckResult checkResult;
	
	public RuntimeResult getRuntimeResult() {
		return runtimeResult;
	}

	public CheckResult getCheckResult() {
		return checkResult;
	}

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
