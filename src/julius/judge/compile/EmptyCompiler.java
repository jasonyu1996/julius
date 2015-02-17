package julius.judge.compile;

import java.io.File;

public class EmptyCompiler implements Compiler{

	@Override
	public CompileResult compile(File dir, String source) {
		return new CompileResult(true, "");
	}
}
