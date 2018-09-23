package FlyLife;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import FlyLife.Grammar.INTERMEDIATE;

public class FlyFactory {
	
	// TODO reduce creation of mates, re-use those on queue
	
	private Random rand;
	private Grammar grammar;
	private volatile int globalId = 1;
	
	public FlyFactory(Random seed) {
		this.rand = seed;
		grammar = new Grammar(this.rand);
	}
	
	public Fly createGenesisFly() {
		Fly fly = new Fly();
		fly.id = globalId++;
		fly.mateId = 0;
		fly.parentId = 0;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.MATE_LIFE);
		fly.birthday = 0;
		return fly;
	}
	
	public Fly createMateFor(Fly mate) {
		Fly fly = new Fly();
		fly.id = globalId++;
		fly.mateId = mate.id;
		fly.parentId = 0;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.MATE_LIFE);
		int absoluteMateDay = mate.birthday + Arrays.asList(mate.life).indexOf(Grammar.TERMINAL.MATE);
		fly.birthday = absoluteMateDay - Arrays.asList(fly.life).indexOf(Grammar.TERMINAL.MATE);		
		return fly;
	}
	
	public Fly createParentFor(Fly child) {
		Fly fly = new Fly();
		fly.id = globalId++;
		fly.mateId = 0;
		fly.parentId = 0;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.MATE_LIFE);
		int relativeMateDay = Arrays.asList(fly.life).indexOf(Grammar.TERMINAL.MATE);
		relativeMateDay = child.birthday  - relativeMateDay;
		fly.birthday = relativeMateDay;
		return fly;
	}
	
	public Fly createChild(Fly parent) {
		Fly fly = new Fly();
		fly.id = globalId++;
		fly.mateId = 0;
		fly.parentId = parent.id;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.START);
		fly.birthday = parent.birthday + Arrays.asList(parent.life).indexOf(Grammar.TERMINAL.MATE);
		return fly;
	}
	
	public Collection<Fly> createChildren(Fly parent)
	{
		Collection<Fly> children = new HashSet<>();
		int numChildren = 1; //(int)Math.ceil(rand.nextDouble() * Colony.MAX_CHILDREN_COUNT);
		for (int i=0; i<numChildren; i++) {
			children.add(createChild(parent));
		}
		return children;
	}
}
