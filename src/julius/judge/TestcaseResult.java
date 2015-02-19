package julius.judge;

import julius.judge.check.CheckResult;
import julius.judge.runtime.RuntimeResult;
import julius.judge.runtime.RuntimeStatus;

/**
 * <p>A representation for the judge result of a single testcase.</p>
 * <p>Such a result consists of runtime result which gives the basic information associated with
 * the execution process in the testcase, and check result from checker which examines the output produced in the process.</p>
 * <p>When the runtime status is not NORMAL, the check result may be null.</p>
 * 
 * @author jason_yu
 * @since Julius1.0
 */
public class TestcaseResult {
	private RuntimeResult runtimeResult;
	private CheckResult checkResult;
	
	/**
	 * <p>Returns the runtime result, or the information collected during the execution process.</p>
	 * 
	 * @return the runtime result
	 * @since Julius1.0
	 */
	public RuntimeResult getRuntimeResult() {
		return runtimeResult;
	}

	/**
	 * <p>Returns the check result, or the information produced by Checker, which examines the output produced during the execution process.</p>
	 * 
	 * @return the check result
	 * @since Julius1.0
	 */
	public CheckResult getCheckResult() {
		return checkResult;
	}

	/**
	 * <p>Creates an instance with runtime result and check result given.</p>
	 * 
	 * @param runtimeResult the runtime result
	 * @param checkResult the check result
	 * @since Julius1.0
	 */
	public TestcaseResult(RuntimeResult runtimeResult, CheckResult checkResult){
		this.runtimeResult = runtimeResult;
		this.checkResult = checkResult;
	}
	
	/**
	 * <p>Calculates the score gained in this testcase.</p>
	 * <p>If the runtime status is not NORMAL, the score would be 0. Otherwise it follows the score of the check result.</p>
	 * 
	 * @return the score in this testcase
	 * @since 1.0
	 */
	public int getScore(){
		if(runtimeResult.getStatus() != RuntimeStatus.NORMAL)
			return 0;
		return checkResult.getScore();
	}
}
