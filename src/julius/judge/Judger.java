package julius.judge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import julius.org.Pattern;
import julius.org.Testcase;
import julius.judge.compile.CompileResult;
import julius.judge.compile.Compiler;
import julius.judge.runtime.RuntimeResult;
import julius.judge.runtime.RuntimeStatus;
import julius.judge.runtime.Sandbox;
import julius.judge.runtime.SandboxNotReadyException;

public class Judger {
	public static JudgeResult judge(File source, Testcase[] testcases, Compiler compiler, Sandbox sandbox, Pattern command, String in, String out, boolean redirect) throws IOException, SandboxNotReadyException{
		sandbox.init();
		File targetPath = sandbox.getPath();
		Files.copy(source.toPath(), targetPath.toPath().resolve(source.getName()));
		CompileResult cRes = compiler.compile(targetPath, source.getName());
		if(!cRes.isCompileOk())
			return new JudgeResult(cRes, null);
		String realCommand = command.parse(source.getName());
		TestcaseResult[] tRes = new TestcaseResult[testcases.length];
		File outFile = new File(targetPath, out);
		for(int i = 0; i < testcases.length; i ++){
			Files.copy(testcases[i].getInFile().toPath(), targetPath.toPath().resolve(in), StandardCopyOption.REPLACE_EXISTING);
			RuntimeResult runRes;
			outFile.createNewFile();
			if(redirect){
				runRes = sandbox.run(realCommand, testcases[i].getTimeLimit(), testcases[i].getMemoryLimit(), in, out);
			} else{
				runRes = sandbox.run(realCommand, testcases[i].getTimeLimit(), testcases[i].getMemoryLimit());
			}
			if(runRes.getStatus() == RuntimeStatus.NORMAL){
				tRes[i] = new TestcaseResult(runRes, testcases[i].getChecker().
						check(testcases[i].getInFile(), testcases[i].getAnsFile(), outFile,
								testcases[i].getFull()));
			} else
				tRes[i] = new TestcaseResult(runRes, null);
		}
		sandbox.cleanUp();
		return new JudgeResult(cRes, tRes);
	}
}
