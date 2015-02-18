package julius.judge.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IsolateSandbox implements Sandbox{
	private String sandboxCmd;
	private static File path = null;
	private File metaFile;
	public IsolateSandbox(String sandboxCmd, File metaFile){
		this.sandboxCmd = sandboxCmd;
		this.metaFile = metaFile;
	};
	
	@Override
	public File getPath() throws SandboxNotReadyException{
		if(!isReady())
			throw new SandboxNotReadyException("IsolateSandbox not ready yet when getPath() was invoked!");
		return path;
	}
	
	@Override
	public boolean isReady(){
		return path != null && path.exists();
	}
	
	@Override
	public boolean init(){
		if(isReady())
			return true;
		try{
			Process p = Runtime.getRuntime().exec(new String[]{sandboxCmd, "--init"});
			int re = p.waitFor();
			if(re != 0)
				return false;
			BufferedReader res = new BufferedReader(new InputStreamReader(p.getInputStream()));
			path = new File(res.readLine(), "box/");
			if(!path.exists())
				return false;
		} catch(Exception e){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean cleanUp(){
		if(!isReady())
			return true;
		try{
			Process p = Runtime.getRuntime().exec(new String[]{sandboxCmd, "--cleanup"});
			int re = p.waitFor();
			if(re != 0)
				return false;
		} catch(Exception e){
			return false;
		}
		return true;
	}
	
	private Process getProcess(String command, int timeLimit, int memoryLimit) throws IOException{
		return Runtime.getRuntime().exec(new String[]{sandboxCmd, "--time=" + (timeLimit / 1000.0), "--extra-time=1", 
				"--wall-time=" + (timeLimit / 1000.0 + 1.0),
				"--mem=" + memoryLimit, "--run -- " + command});
	}
	
	private Process getProcess(String command, int timeLimit,
			int memoryLimit, String in, String out) throws IOException{
		return Runtime.getRuntime().exec(new String[]{sandboxCmd, "--time=" + (timeLimit / 1000.0), "--extra-time=1", 
				"--wall-time=" + (timeLimit / 1000.0 + 1.0),
				"--mem=" + memoryLimit, "--stdin=" + in, "--stdout=" + out, "--run -- " + command});
	}
	
	@Override
	public RuntimeResult run(String command, int timeLimit,
			int memoryLimit) throws SandboxNotReadyException{
		if(!isReady())
			throw new SandboxNotReadyException("RuntimeSandbox not ready yet when run() was invoked!");
		try{
			Process p = getProcess(command, timeLimit, memoryLimit);
			return getResult(p);
		} catch(Exception e){
			return new RuntimeResult(0, 0, RuntimeStatus.RUNTIME_ERROR);
		}
	}
	
	@Override
	public RuntimeResult run(String command, int timeLimit,
			int memoryLimit, String in, String out) throws SandboxNotReadyException{
		if(!isReady())
			throw new SandboxNotReadyException("RuntimeSandbox not ready yet when run() was invoked!");
		try{
			Process p = getProcess(command, timeLimit, memoryLimit, in, out);
			return getResult(p);
		} catch(Exception e){
			return new RuntimeResult(0, 0, RuntimeStatus.RUNTIME_ERROR);
		}
	}

	private RuntimeResult getResult(Process p) throws Exception{
		int re = p.waitFor();
		if(re != 0 && re != 1)
			return new RuntimeResult(0, 0, RuntimeStatus.RUNTIME_ERROR);
		int ti = 0, me = 0;
		RuntimeStatus status = RuntimeStatus.NORMAL;
		BufferedReader metaReader = new BufferedReader(new FileReader(metaFile));
		String s;
		String[] info;
		while(true){
			s = metaReader.readLine();
			if(s == null)
				break;
			info = s.split(":", 2);
			if(info[0].equals("status")){
				if(info[1].equals("TO"))
					status = RuntimeStatus.TIME_LIMIT_EXCEEDED;
				else if(info[1].equals("ML"))
					status = RuntimeStatus.MEMORY_LIMIT_EXCEEDED;
				else
					status = RuntimeStatus.RUNTIME_ERROR;
			} else if(info[0].equals("time-wall"))
				ti = (int)(Double.valueOf(info[1]) * 1000.0);
			else if(info[0].equals("max-rss"))
				me = Integer.valueOf(info[1]);
		}
		metaReader.close();
		metaFile.delete();
		return new RuntimeResult(ti, me, status);
	}
}
