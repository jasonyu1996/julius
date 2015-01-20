package julius.judge.check;

public interface Comparator {
	boolean compare(String read, String expected) throws Exception;
}
