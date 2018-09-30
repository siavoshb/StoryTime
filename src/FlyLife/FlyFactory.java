package FlyLife;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import FlyLife.Grammar.INTERMEDIATE;

public class FlyFactory {
	
	// TODO reduce creation of mates, re-use those on queue
	/*
	 * cross agent context sensitive grammars like mt()
if large number of same generation/family born/die/eat etc at one time period -then can trigger certain replacements in their life histories -- celebrations, moarnings etc
analogous to if emotions are sad those grammars replaced with larger movements, changes their affiliations etc...
	 */
	
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
		fly.generation = 0;
		fly.mateId = 0;
		fly.parentId = 0;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.MATE_LIFE);
		fly.birthday = 0;
		return fly;
	}
	
	public Fly createMateFor(Fly mate) {
		Fly fly = new Fly();
		fly.id = globalId++;
		fly.generation = mate.generation;
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
		fly.generation = child.generation--;
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
		fly.generation = parent.generation++;
		fly.mateId = 0;
		fly.parentId = parent.id;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.START);
		fly.birthday = parent.birthday + Arrays.asList(parent.life).indexOf(Grammar.TERMINAL.MATE);
		return fly;
	}
	
	public Collection<Fly> createChildren(Fly parent)
	{
		Collection<Fly> children = new HashSet<>();
		int numChildren = ThreadLocalRandom.current().nextInt(Colony.MIN_CHILDREN_COUNT, Colony.MAX_CHILDREN_COUNT + 1);
		System.out.println(numChildren);
		for (int i=0; i<numChildren; i++) {
			children.add(createChild(parent));
		}
		return children;
	}
}
