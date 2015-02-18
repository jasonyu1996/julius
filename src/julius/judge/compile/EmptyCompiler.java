package julius.judge.compile;

import java.io.File;

public class EmptyCompiler implements Compiler{

	@Override
	public CompileResult compile(File path, String fname) {
		return new CompileResult(true, "");
	}
}
