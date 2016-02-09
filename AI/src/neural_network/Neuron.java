package neural_network;

public class Neuron
{
	float NET;
	char chr;
	int num;
	
	public Neuron(char chr, int num) {
		this.chr = chr;
		this.num = num;
	}
	
	@Override
	public boolean equals(Object n) {
		if(this == n) return true;
		else return false;
	}
	
	@Override
	public String toString() {
		return Character.toString(chr) + num;
	}
}
