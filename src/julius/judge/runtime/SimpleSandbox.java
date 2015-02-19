package julius.judge.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleSandbox implements Sandbox{
	private String sandboxCmd;
	private boolean ready;
	private File path;
	
	public SimpleSandbox(String sandboxCmd, File path){
		this.sandboxCmd = sandboxCmd;
		this.path = path;
		ready = false;
	}
	
	@Override
	public boolean init() {
		if(ready || path.exists() || path.mkdir())
			ready = true;
		return ready;
	}

	@Override
	public File getPath() throws SandboxNotReadyException{
		if(!ready)
			throw new SandboxNotReadyException("SimpleSandbox not ready yet when getPath() was invoked!");
		return path;
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	private int getNextInt(BufferedReader res) throws IOException{
		String s = res.readLine();
		if(s == null)
			return 0;
		return Integer.valueOf(s);
	}
	
	private RuntimeStatus getRuntimeStatus(int id){
		if(id == 3)
			return RuntimeStatus.TIME_LIMIT_EXCEEDED;
		if(id == 4)
			return RuntimeStatus.MEMORY_LIMIT_EXCEEDED;
		if(id == 0)
			return RuntimeStatus.NORMAL;
		return RuntimeStatus.RUNTIME_ERROR;
	}
	
	@Override
	public RuntimeResult run(String command, int timeLimit, int memoryLimit) throws SandboxNotReadyException{
		if(!ready)
			throw new SandboxNotReadyException("SimpleSandbox not ready yet when run() was invoked!");
		try{
			Process p = Runtime.getRuntime().
					exec(new String[]{sandboxCmd, path.toString(), command, 
							String.valueOf(timeLimit), String.valueOf(memoryLimit)});
			int re = p.waitFor();
			RuntimeResult result;
			BufferedReader res = new BufferedReader(new InputStreamReader(p.getInputStream()));
			result = new RuntimeResult(getNextInt(res), getNextInt(res), getRuntimeStatus(re));
			res.close();
			
			return result;
		} catch(Exception e){
			e.printStackTrace();
			return new RuntimeResult(0, 0, RuntimeStatus.RUNTIME_ERROR);
		}
	}
	

	@Override
	public RuntimeResult run(String command, int timeLimit, int memoryLimit,
			String in, String out) throws SandboxNotReadyException{
		if(!ready)
			throw new SandboxNotReadyException("SimpleSandbox not ready yet when run() was invoked!");
		try{
			Process p = Runtime.getRuntime().
					exec(new String[]{sandboxCmd, path.toString(), command, 
							String.valueOf(timeLimit), String.valueOf(memoryLimit), in, out});
			int re = p.waitFor();
			RuntimeResult result;
			BufferedReader res = new BufferedReader(new InputStreamReader(p.getInputStream()));
			result = new RuntimeResult(getNextInt(res), getNextInt(res), getRuntimeStatus(re));
			res.close();
			
			return result;
		} catch(Exception e){
			e.printStackTrace();
			return new RuntimeResult(0, 0, RuntimeStatus.RUNTIME_ERROR);
		}
	}
	
	private boolean deletePath(File file) throws IOException{
		if(!file.exists())
			return true;
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i ++)
				if(!deletePath(files[i]))
					return false;
		}
		return file.delete();
	}

	@Override
	public boolean cleanUp() {
		if(!ready)
			return true;
		try{
			if(deletePath(path))
				ready = false;
		} catch(Exception e){
			return false;
		}
		return !ready;
	}

}
