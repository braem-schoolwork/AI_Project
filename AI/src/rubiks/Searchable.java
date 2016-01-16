package rubiks;

public interface Searchable {
	public Searchable[] genChildren();
	public boolean isSolved();
}
