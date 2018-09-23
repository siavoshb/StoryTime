package FlyLife;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Colony {
	private final Random seed;
	private final FlyFactory flyFactory;
	private final Set<Fly> population;
	private final Queue<Fly> queue;
	
	public static final int MAX_POPULATION = 2000000;
	public static final int MAX_LIFE_SPAN = 50;
	public static final int MAX_CHILDREN_COUNT = 1;

	public Colony(Random seed) {
		this.seed = seed;
		this.flyFactory = new FlyFactory(seed);
		this.population = new HashSet<>();
		this.queue = new LinkedList<>();
	}

	public void genesis(DirectDrawDemo panel) {
		
		queue.add(flyFactory.createGenesisFly());

		while (!queue.isEmpty()) {
			Fly f = queue.remove();
			if (f.needsMate()) {
				Fly mate = flyFactory.createMateFor(f);
				f.mateId = mate.id;
				queue.add(mate);
				queue.addAll(flyFactory.createChildren(f));
			}
			if (f.parentId == 0) {
				Fly parent = flyFactory.createParentFor(f);
				f.parentId = parent.id;
				queue.add(parent);
			}
			population.add(f);
			panel.drawFly(f, seed);

			if (population.size() > MAX_POPULATION) { break; }
		}

		System.out.println("remaining " + queue.size());
		int max = 0;
		while (!queue.isEmpty()) {
			Fly f = queue.remove();
			if (f.birthday > max) {
				max = f.birthday;
			}
		}
		System.out.println("max birthday: " + max);
		//print();
	}

	private void print() {
		for (Fly f : population) {
			f.print();
		}
	}

}
