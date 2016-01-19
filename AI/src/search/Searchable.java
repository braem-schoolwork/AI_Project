package search;

public interface Searchable {
	public Searchable[] genChildren();
	public boolean isSolved();
	public boolean equals(Searchable obj);
	public Searchable getParent();
	public int getCost();
}
