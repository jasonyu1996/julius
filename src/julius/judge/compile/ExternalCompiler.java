package julius.judge.compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ExternalCompiler implements Compiler{
	private String pattern;
	public ExternalCompiler(String pattern){
		this.pattern = pattern;
	}
	
	@Override
	public final CompileResult compile(File dir, String source){
		try{
		/*	Process p = Runtime.getRuntime().exec(pattern.replace("%<", new File(source.getParentFile(), source.getName().
					substring(0, Math.max(0, source.getName().lastIndexOf('.')))).toString())
					.replace("%", source.toString()));*/
			Process p = Runtime.getRuntime().exec(pattern.replace("%<", 
					source.substring(0, Math.max(0, source.lastIndexOf('.')))).
					replace("%", source), null, dir);
	//		Process p = Runtime.getRuntime().exec("ls");
			int re = p.waitFor();
			StringBuilder message = new StringBuilder();
			BufferedReader outMes = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line;
			while(true){
				line = outMes.readLine();
				if(line == null)
					break;
				message.append(line + "\n");
			}
			outMes.close();
			return new CompileResult(re == 0, message.toString());
		} catch(Exception e){
			return new CompileResult(false, e.getMessage());
		}
	}
}
