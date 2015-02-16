package julius.judge.check;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SpecialChecker extends Checker {
	private String cmd;
	private File resultFile;
	private FileReader reader;
	private BufferedReader bReader;
	
	public SpecialChecker(String cmd, File resultFile){
		this.cmd = cmd;
		this.resultFile = resultFile;
	}
	
	private void closeAll() throws Exception{
		bReader.close();
		reader.close();
	}
	
	@Override
	protected CheckResult checkIt(File input, File answer, File output, int full)
			throws Exception {
		Process pro = Runtime.getRuntime().exec(cmd, new String[]{input.getAbsolutePath(), 
				answer.getAbsolutePath(), output.getAbsolutePath(), Integer.toString(full), resultFile.toString()});
		int exitVal = pro.waitFor();
		if(exitVal != 0)
			return new CheckResult(0, "Special checker error!");
		int score;
		StringBuilder info = new StringBuilder();
		reader = new FileReader(resultFile);
		bReader = new BufferedReader(reader);
		try{
			String s = bReader.readLine();
			score = Integer.parseInt(s);
			boolean isFirstLine = true;
			while(true){
				s = bReader.readLine();
				if(s == null)
					break;
				if(isFirstLine)
					info.append(s);
				else
					info.append("\n" + s);
				isFirstLine = false;
			}
		} catch(Exception e){
			e.printStackTrace();
			closeAll();
			return new CheckResult(0, "Special checker error!");
		}
		closeAll();
		
		return new CheckResult(score, info.toString());
	}

}
