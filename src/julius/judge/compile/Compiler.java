package julius.judge.compile;

import java.io.File;

public interface Compiler {
	public CompileResult compile(File path, String fname);
}
