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

	public static final int MAX_POPULATION = 40;
	//public static final int MAX_LIFE_SPAN = 50;
	public static final int MIN_CHILDREN_COUNT = 2;
	public static final int MAX_CHILDREN_COUNT = 15;

	public Colony(Random seed) {
		this.seed = seed;
		this.flyFactory = new FlyFactory(seed);
		this.population = new HashSet<>();
		this.queue = new LinkedList<>();
	}

	public Set<Fly> getPopulation() {
		return population;
	}

	public Fly getMate(Queue<Fly> q, Fly elig) {
		java.util.Iterator<Fly> iterator = q.iterator(); 
		while (iterator.hasNext()) {
			Fly f = iterator.next();
			if (f.needsMate()) {
				if (f.getAbsoluteMateDay() == elig.getAbsoluteMateDay())
					iterator.remove();
				return f;
			}
		}
		return null;
	}

	public void genesis(DirectDrawDemo panel) {

		queue.add(flyFactory.createGenesisFly());

		boolean nomore = false;
		
		while (!queue.isEmpty()) { 
			Fly f = queue.remove();
			if (f.needsMate() && population.size() < MAX_POPULATION) {
				// try to take mate from queue
				Fly mate = getMate(queue, f);
				if (mate == null) {
					mate = flyFactory.createMateFor(f);
				}
				f.mateId = mate.id;
				queue.add(mate);
				queue.addAll(flyFactory.createChildren(f));
			}
			/*if (f.parentId == 0) {
				Fly parent = flyFactory.createParentFor(f);
				f.parentId = parent.id;
				queue.add(parent);
			}*/
			population.add(f);
			if (panel!=null) {
				panel.drawFly(f, seed);
			}

			if (nomore) { break; }
		}
		
		

		System.out.println("population " + population.size());
		System.out.println("remaining " + queue.size());
		int max = 0, maxg = 0;

		while (!queue.isEmpty()) {
			Fly f = queue.remove();
			if (f.birthday > max) {
				max = f.birthday;
			}
			if (f.generation > maxg) {
				maxg = f.generation;
			}
		}
		System.out.println("max birthday: " + max);
		System.out.println("max gen: " + maxg);
		//print();
	}

	private void print() {
		for (Fly f : population) {
			f.print();
		}
	}

}
