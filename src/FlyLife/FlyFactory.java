package FlyLife;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import FlyLife.Grammar.INTERMEDIATE;

public class FlyFactory {
	
	private static int MAX_CHILDREN_COUNT = 10;
	
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
		fly.sex = rand.nextBoolean();
		fly.parent1Id = 0;
		fly.parent2Id = 0;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.MATE_LIFE);
		fly.birthday = 0;
		
		return fly;
	}
	
	public Fly createFlyWithMate(Fly mate) {
		Fly fly = new Fly();
		fly.id = globalId++;
		fly.sex = rand.nextBoolean();
		fly.parent1Id = 0;
		fly.parent2Id = 0;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.MATE_LIFE);
		//fly.birthday = Arrays.asList(fly.life).indexOf(INTERMEDIATE.MATE_LIFE);
		
		return fly;
	}
	
	public Fly createChild(Fly parent1, Fly parent2) {
		Fly fly = new Fly();
		fly.id = globalId++;
		fly.sex = rand.nextBoolean();
		fly.parent1Id = parent1.id;
		fly.parent2Id = parent2.id;
		fly.life = grammar.generateLifeHistory(INTERMEDIATE.START);
		//fly.birthday = Arrays.asList(fly.life).indexOf(INTERMEDIATE.MATE_LIFE);
		
		return fly;
	}
	
	public Collection<Fly> createChildren(Fly parent1, Fly parent2)
	{
		Collection<Fly> children = new HashSet<>();
		int numChildren =(int)Math.ceil(rand.nextDouble() * MAX_CHILDREN_COUNT);
		for (int i=0; i<numChildren; i++) {
			children.add(createChild(parent1, parent2));
		}
			
		return children;
	}
}
