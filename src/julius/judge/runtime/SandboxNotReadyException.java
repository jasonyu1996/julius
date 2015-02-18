package julius.judge.runtime;

public class SandboxNotReadyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SandboxNotReadyException(String info){
		super(info);
	}
}
