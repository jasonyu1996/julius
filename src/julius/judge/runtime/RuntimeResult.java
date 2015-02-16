package julius.judge.runtime;

public class RuntimeResult {
	private int timeUsed, memoryUsed;
	private RuntimeStatus status;
	
	public RuntimeResult(int timeUsed, int memoryUsed, RuntimeStatus status){
		this.timeUsed = timeUsed;
		this.memoryUsed = memoryUsed;
		this.status = status;
	}
	
	public int getTimeUsed() {
		return timeUsed;
	}
	public int getMemoryUsed() {
		return memoryUsed;
	}
	public RuntimeStatus getStatus() {
		return status;
	}
}
