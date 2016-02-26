package search;

public class SearchListNode
{
	private Searchable parent;
	private Searchable val;
	private int gVal;
	private float hVal;
	
	public SearchListNode(int gVal) {
		this.gVal = gVal;
	}

	public int g() {
		return gVal;
	}
	/*public float h() {
		return val.h();
	}*/
	public float f() {
		return g(); //+ h();
	}
}
