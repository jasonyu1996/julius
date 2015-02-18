package julius.judge.runtime;

import java.io.File;

public interface Sandbox {
	public boolean init();
	public File getPath() throws SandboxNotReadyException;
	public boolean isReady();
	public RuntimeResult run(String command, int timeLimit, int memoryLimit) throws SandboxNotReadyException;
	public RuntimeResult run(String command, int timeLimit, int memoryLimit, String in, String out) throws SandboxNotReadyException;
	public boolean cleanUp();
}
