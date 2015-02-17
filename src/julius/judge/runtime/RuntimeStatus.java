package julius.judge.runtime;

public enum RuntimeStatus {
	TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, RUNTIME_ERROR, NORMAL;
	
	protected static RuntimeStatus getRuntimeStatus(int id){
		if(id == 3)
			return TIME_LIMIT_EXCEEDED;
		else if(id == 4)
			return MEMORY_LIMIT_EXCEEDED;
		else if(id == 0)
			return NORMAL;
		else
			return RUNTIME_ERROR;
	}
	
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

