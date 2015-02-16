package julius.org;

import java.io.File;

import julius.judge.check.Checker;

public class Testcase {
	private File inFile, ansFile;
	private int full;
	private Checker checker;
	private int timeLimit, memoryLimit;
	
	public Testcase(File inFile, File ansFile, int full, Checker checker, int timeLimit, int memoryLimit){
		this.inFile = inFile;
		this.ansFile = ansFile;
		this.full = full;
		this.checker = checker;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
	}

	public File getInFile() {
		return inFile;
	}

	public void setInFile(File inFile) {
		this.inFile = inFile;
	}

	public File getAnsFile() {
		return ansFile;
	}

	public void setAnsFile(File ansFile) {
		this.ansFile = ansFile;
	}

	public int getFull() {
		return full;
	}

	public void setFull(int full) {
		this.full = full;
	}

	public Checker getChecker() {
		return checker;
	}

	public void setChecker(Checker checker) {
		this.checker = checker;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}
	
	
}
