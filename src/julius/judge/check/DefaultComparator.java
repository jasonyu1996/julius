package julius.judge.check;

public class DefaultComparator implements Comparator {

	@Override
	public boolean compare(String read, String expected) throws Exception {
		return read.equals(expected);
	}
}
