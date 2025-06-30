package ambient_intelligence.boundary;

public class NameBoundary {
	private String first;
	private String last;

	public static boolean validateName(NameBoundary name)
	{
		if (name.getFirst() == null  || name.getFirst().trim().isEmpty()) 
			return false;
		if (name.getLast() == null  || name.getLast().trim().isEmpty()) 
			return false;
		return true;
	}
	public NameBoundary() {
	}

	public NameBoundary(String first, String last) {
		if (first == null  || first.trim().isEmpty()) 
			throw new IllegalArgumentException("First name is required");
		    

		if (last == null  || last.trim().isEmpty()) 
			throw new IllegalArgumentException("Last name is required");
		    
		
		
		this.first = first;
		this.last = last;
	}
	
	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "{first=" + first + ", last=" + last + "}";
	}

}
