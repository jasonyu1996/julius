package julius.judge;

import julius.judge.compile.CompileResult;

/**
 * A representation of the result of a judge routine.
 * 
 * <p>It consists of compile results and testcase results.</p>
 * <p>When the compilation is not successful, the testcase results may be null.</p>
 * @author jason_yu
 * @since Julius1.0
 * */
public class JudgeResult {
	private int totalScore;
	private TestcaseResult[] testcaseResults;
	private CompileResult compileResult;
	
	/**
	 * <p>Creates a JudgeResult instance with given compile result and testcase results.</p>
	 * 
	 * @param compileResult the compile result
	 * @param testcaseResults the results for testcases
	 * @since Julius1.0
	 * */
	public JudgeResult(CompileResult compileResult, TestcaseResult[] testcaseResults){
		this.compileResult = compileResult;	
		totalScore = 0;
		if(testcaseResults == null)
			this.testcaseResults = null;
		else
			this.testcaseResults = testcaseResults.clone();
		if(compileResult.isCompileOk())
			for(int i = 0; i < testcaseResults.length; i ++)
				totalScore += testcaseResults[i].getScore();
	}

	/**
	 * <p>Calculates the total score of the judge result.</p>
	 * @return the total score. If the compilation is not successful, it would be 0. Otherwise, it would be the sum of score gained in each testcase.
	 * @since Julius1.0
	 * */
	public int getTotalScore() {
		return totalScore;
	}

	/**
	 * <p>Returns the compile result in the judge result.</p>
	 * 
	 * @return the compile result
	 * @since Julius1.0
	 */
	public CompileResult getCompileResult() {
		return compileResult;
	}
	
	/**
	 * <p>Returns the testcase results in the judge result.</p>
	 * 
	 * @return an array containing the results of testcases
	 * @since Julius1.0
	 */
	public TestcaseResult[] getTestcaseResults(){
		if(testcaseResults == null)
			return null;
		return testcaseResults.clone();
	}
}
