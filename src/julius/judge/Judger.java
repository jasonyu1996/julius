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

/**
 * <p>The Judger class provides a static method to carry out the judge routine.</p>
 * 
 * @author jason_yu
 * @since Julius1.0
 * */
public final class Judger {
	private Judger(){}
	
	/**
	 * <p>Carries out a judge routine.</p>
	 * 
	 * @param source the source file
	 * @param testcases the testcases in this judge task
	 * @param compiler the compiler used to compile the source
	 * @param sandbox the sandbox used to execute the code
	 * @param command the command used to execute the code
	 * @param in the input file name
	 * @param out the output file name
	 * @param redirect whether the standard input and output will be redirected
	 * @return the judge result. The testcase results would be arranged corresponding to testcases.
	 * @throws IOException if any I/O problem occurs
	 * @throws SandboxNotReadyException if in the judge process the sandbox could not be initialised to be usable 
	 * @since Julius1.0
	 * */
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
