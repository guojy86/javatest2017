package solution.question1;

/**
 * Class to represent a Quote object
 * @author Jiayuan
 *
 */
public class Quote {
	
	private int value;

	public Quote(int v) {
		this.value = v;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quote other = (Quote) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
	

}
