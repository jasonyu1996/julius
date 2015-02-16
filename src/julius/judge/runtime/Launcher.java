package julius.judge.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {
	private String pattern;
	
	public Launcher(String pattern){
		this.pattern = pattern;
	}
	
	private int getNextInt(BufferedReader res) throws IOException{
		String s = res.readLine();
		if(s == null)
			return 0;
		return Integer.valueOf(s);
	}
	
	private String getCommand(String sourceName){
		return pattern.replaceAll("%", sourceName).
				replaceAll("%<", sourceName.
						substring(0, Math.max(0, sourceName.lastIndexOf('.'))));
	}
	
	public RuntimeResult launch(File path, String sourceName, int timeLimit, int memoryLimit){
		try{
			Process p = Runtime.getRuntime().
					exec(new String[]{"./monitor", path.toString(), getCommand(sourceName), 
							String.valueOf(timeLimit), String.valueOf(memoryLimit)});
			int re = p.waitFor();
			RuntimeResult result;
			BufferedReader res = new BufferedReader(new InputStreamReader(p.getInputStream()));
			result = new RuntimeResult(getNextInt(res), getNextInt(res), RuntimeStatus.getRuntimeStatus(re));
			res.close();
			
			return result;
		} catch(Exception e){
			e.printStackTrace();
			return new RuntimeResult(0, 0, RuntimeStatus.RUNTIME_ERROR);
		}
	}
	
	public RuntimeResult launch(File path, String sourceName, int timeLimit, int memoryLimit, String in, String out){
		try{
			Process p = Runtime.getRuntime().
					exec(new String[]{"./monitor", path.toString(), getCommand(sourceName), 
							String.valueOf(timeLimit), String.valueOf(memoryLimit), in, out});
			int re = p.waitFor();
			RuntimeResult result;
			BufferedReader res = new BufferedReader(new InputStreamReader(p.getInputStream()));
			result = new RuntimeResult(getNextInt(res), getNextInt(res), RuntimeStatus.getRuntimeStatus(re));
			res.close();
			
			return result;
		} catch(Exception e){
			e.printStackTrace();
			return new RuntimeResult(0, 0, RuntimeStatus.RUNTIME_ERROR);
		}
	}
}
