package julius.judge.compile;

public class CompileResult {
	private String compileInfo;
	private boolean compileOk;
	
	protected CompileResult(boolean compileOk, String compileInfo){
		this.compileOk = compileOk;
		this.compileInfo = compileInfo;
	}

	public String getCompileInfo() {
		return compileInfo;
	}

	public boolean isCompileOk() {
		return compileOk;
	}
	
	
}
