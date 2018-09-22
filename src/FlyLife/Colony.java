package FlyLife;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Colony {
	private final Random seed;
	private final FlyFactory flyFactory;
	private final Set<Fly> population;
	private final Queue<Fly> pendingMates;

	public Colony(Random seed) {
		this.seed = seed;
		this.flyFactory = new FlyFactory(seed);
		this.population = new HashSet<>();
		this.pendingMates = new LinkedList<>();
	}
	
	private void initializePopulation() {
		Fly f1 = flyFactory.createGenesisFly();
		Fly f2 = flyFactory.createGenesisFly();
		pendingMates.add(f1);
		pendingMates.add(f2);
		population.add(f1);
		population.add(f2);
	}

	public void genesis() {
		initializePopulation();
		
		while (pendingMates.size() > 1) {
			Fly f1 = pendingMates.remove(), f2 = pendingMates.remove();
			Collection<Fly> children = flyFactory.createChildren(f1, f2);
			for (Fly c : children) {
				population.add(c);
				if (c.hasMate()) {
					pendingMates.add(c);
				}
			}
			
			if (population.size() > 100) { break; }
		}
		
		print();
	}
	
	private void print() {
		for (Fly f : population) {
			f.print();
		}
	}

}
