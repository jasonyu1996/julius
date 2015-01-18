package julius.judge.runtime;

public enum RuntimeStatus {
	TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, RUNTIME_ERROR, NORMAL;
	
	protected RuntimeStatus getRuntimeStatus(int id){
		if(id == 1)
			return TIME_LIMIT_EXCEEDED;
		else if(id == 2)
			return MEMORY_LIMIT_EXCEEDED;
		else if(id == 0)
			return NORMAL;
		else
			return RUNTIME_ERROR;
	}
}

