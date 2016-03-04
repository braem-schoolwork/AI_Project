package search;

public class SearchListNode implements Comparable<SearchListNode>
{
	private SearchListNode parent;
	private Searchable searchableObj;
	private int gVal;
	
	public SearchListNode(SearchListNode parent, Searchable searchableObj, int gVal) {
		this.parent = parent;
		this.searchableObj = searchableObj;
		this.gVal = gVal;
	}

	public SearchListNode getParent() { return parent; }
	public Searchable getSearchableObj() { return searchableObj; }
	public int getGVal() { return gVal; }
	public float f() {
		return gVal + searchableObj.h();
	}

	@Override
	public int compareTo(SearchListNode sln) {
		if(this.f() > sln.f())
			return 10;
		else if(this.f() < sln.f())
			return -10;
		else
			return 0;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SearchListNode)) return false;
		return this.searchableObj.equals(((SearchListNode)obj).getSearchableObj());
	}
	@Override
	public int hashCode() {
		return searchableObj.hashCode();
	}
}
