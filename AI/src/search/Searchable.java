package search;

import rubiks.Move;

public interface Searchable {
	public Searchable[] genChildren();
	public boolean isSolved();
	public boolean equals(Searchable obj);
	public Searchable getParent();
	public Move getMoveAppliedToParent();
}
