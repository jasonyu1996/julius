package julius.judge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import julius.org.Testcase;
import julius.judge.compile.CompileResult;
import julius.judge.compile.Compiler;
import julius.judge.runtime.Launcher;
import julius.judge.runtime.RuntimeResult;
import julius.judge.runtime.RuntimeStatus;

public class Judger {
	public static JudgeResult judge(String monitor, File dir, String source, Testcase[] testcases, Compiler compiler, Launcher launcher, String in, String out, boolean redirect) throws IOException{
		CompileResult cRes = compiler.compile(dir, source);
		if(!cRes.isCompileOk())
			return new JudgeResult(cRes, null);
		TestcaseResult[] tRes = new TestcaseResult[testcases.length];
		Path pathDir = dir.toPath();
		File outFile = new File(dir, out);
		for(int i = 0; i < testcases.length; i ++){
			Files.copy(testcases[i].getInFile().toPath(), pathDir.resolve(in), StandardCopyOption.REPLACE_EXISTING);
			RuntimeResult runRes;
			outFile.createNewFile();
			if(redirect){
				runRes = launcher.launch(monitor, dir, source, testcases[i].getTimeLimit(), testcases[i].getMemoryLimit(), in, out);
			} else{
				runRes = launcher.launch(monitor, dir, source, testcases[i].getTimeLimit(), testcases[i].getMemoryLimit());
			}
			if(runRes.getStatus() == RuntimeStatus.NORMAL){
				tRes[i] = new TestcaseResult(runRes, testcases[i].getChecker().
						check(testcases[i].getInFile(), testcases[i].getAnsFile(), outFile,
								testcases[i].getFull()));
			} else
				tRes[i] = new TestcaseResult(runRes, null);
		}
		return new JudgeResult(cRes, tRes);
	}
}
