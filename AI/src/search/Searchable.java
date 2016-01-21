package search;

public interface Searchable {
	public Searchable[] genChildren();
	public boolean equals(Searchable obj);
	public Searchable getParent();
	public int g();
}
