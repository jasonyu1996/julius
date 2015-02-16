package julius.judge.check;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DiffChecker extends Checker {
	
	private FileReader answerReader, outputReader;
	private BufferedReader answerBReader, outputBReader;
	private Comparator comparator;

	private void closeAll() throws Exception{
		answerBReader.close();
		outputBReader.close();
		answerReader.close();
		outputReader.close();
	}
	
	public DiffChecker(Comparator comparator){
		this.comparator = comparator;
	}
	
	@Override
	protected CheckResult checkIt(File input, File answer, File output, int full) throws Exception{
		answerReader = new FileReader(answer);
		outputReader = new FileReader(output);
		answerBReader = new BufferedReader(answerReader);
		outputBReader = new BufferedReader(outputReader);
		
		int lineNumber = 0;
		String a, b;
		while(true){
			++ lineNumber;
			a = answerBReader.readLine();
			b = outputBReader.readLine();
			if(a == null && b != null){
				closeAll();
				return new DiffCheckResult(0, lineNumber, "redundant tailing output");
			}
			if(a != null && b == null){
				closeAll();
				return new DiffCheckResult(0, lineNumber, "output unexpectedly ends");
			}
			if(a != null && b != null){
				if(!comparator.compare(b, a)){
					return new DiffCheckResult(0, lineNumber, b, a);
				}
			} else
				break;
		}
		
		closeAll();
		
		return new DiffCheckResult(full);
	}

}
