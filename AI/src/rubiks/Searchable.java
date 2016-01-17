package rubiks;

public interface Searchable {
	public Searchable[] genChildren();
	public boolean isSolved();
	public boolean equals(Searchable obj);
	public RubiksCube getParent();
	public Move getMoveAppliedToParent();
}
