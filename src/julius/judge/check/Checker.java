package julius.judge.check;

import java.io.File;

public abstract class Checker {
	protected abstract CheckResult checkIt(File input, File answer, File output, int full) throws Exception;
	public final CheckResult check(File input, File answer, File output, int full){
		if(!answer.exists())
			return new CheckResult(0, "Output file not found!");
		if(!output.exists())
			return new CheckResult(0, "Standard output file not found!");
		try{
			return checkIt(input, answer, output, full);
		} catch(Exception e){
			e.printStackTrace();
			return new CheckResult(0, "Check error!");
		}
	}
}
