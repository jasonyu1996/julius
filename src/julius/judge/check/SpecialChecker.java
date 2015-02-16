package julius.judge.check;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class SpecialChecker extends Checker {
	private String cmd;
	private BufferedReader bReader;
	
	public SpecialChecker(String cmd){
		this.cmd = cmd;
	}
	
	private void closeAll() throws Exception{
		bReader.close();
	}
	
	@Override
	protected CheckResult checkIt(File input, File answer, File output, int full)
			throws Exception {
		Process pro = Runtime.getRuntime().exec(cmd, new String[]{input.getAbsolutePath(), 
				answer.getAbsolutePath(), output.getAbsolutePath(), Integer.toString(full)});
		int exitVal = pro.waitFor();
		if(exitVal != 0)
			return new CheckResult(0, "Special checker error!");
		int score;
		StringBuilder info = new StringBuilder();
		bReader = new BufferedReader(new BufferedReader(new InputStreamReader(pro.getInputStream())));
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
