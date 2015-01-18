package julius.judge;

import julius.judge.compile.CompileResult;

public class JudgeResult {
	private int totalScore;
	private TestcaseResult[] testcaseResults;
	private CompileResult compileResult;
	
	protected JudgeResult(CompileResult compileResult, TestcaseResult[] testcaseResults){
		this.compileResult = compileResult;	
		totalScore = 0;
		this.testcaseResults = testcaseResults.clone();
		if(compileResult.isCompileOk())
			for(int i = 0; i < testcaseResults.length; i ++)
				totalScore += testcaseResults[i].getScore();
	}

	public int getTotalScore() {
		return totalScore;
	}

	public CompileResult getCompileResult() {
		return compileResult;
	}
	
	public TestcaseResult[] getTestcaseResults(){
		return testcaseResults.clone();
	}
}
