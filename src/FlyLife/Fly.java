package FlyLife;

import java.util.Arrays;

import FlyLife.Grammar.Event;
import FlyLife.Grammar.TERMINAL;

public class Fly {
	
	public int id;
	public int birthday;
	public int parent1Id, parent2Id;
	public boolean sex;
	public int mateId;
	public Event[] life;

	public Fly() {}
	
	public boolean hasMate() {
		return Arrays.asList(life).contains(TERMINAL.MATE);
	}
	
	
		
	public void print() {
		System.out.print("id " + id);
		for (Event e : life) {
			if (e instanceof TERMINAL) {
				System.out.print("\t" + e.toString());
			}
		}
		System.out.print("\n");
	}
}