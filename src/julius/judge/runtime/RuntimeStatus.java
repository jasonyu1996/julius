package julius.judge.runtime;

public enum RuntimeStatus {
	TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, RUNTIME_ERROR, NORMAL;
	
	@Override
	public String toString(){
		if(this == TIME_LIMIT_EXCEEDED)
			return "Time Limit Exceeded";
		else if(this == MEMORY_LIMIT_EXCEEDED)
			return "Memory Limit Exceeded";
		else if(this == RUNTIME_ERROR)
			return "Runtime Error";
		else
			return "Normal";
	}
}

