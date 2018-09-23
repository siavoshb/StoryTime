package FlyLife;

import java.util.Arrays;

import FlyLife.Grammar.Event;
import FlyLife.Grammar.TERMINAL;

public class Fly {
	
	public int id;
	public int birthday;
	public int parentId;
	public int mateId;
	public Event[] life;

	public Fly() {}
	
	public boolean needsMate() {
		return (mateId==0) && Arrays.asList(life).contains(TERMINAL.MATE);
	}
	
	public void print() {
		System.out.println(id + ") birthday " + birthday + "; parent " + parentId + "; mate " + mateId);
		for (Event e : life) {
				System.out.print("\t" + e.toString());
		}
		
		System.out.print("\n");
	}

}