package FlyLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

public class Grammar {
	public static interface Event {}

	public enum INTERMEDIATE implements Event { START, ANY, NO_MATE, MATE_LIFE;}
	public enum TERMINAL implements Event { BORN, FLY, EAT, MATE, SLEEP, DIE, NOOP;}
	
	public static final Map<Event, List<Event[]>> PRODUCTIONS;
	
	static {
		PRODUCTIONS = new HashMap<>();

		List<Event[]> startRules = new ArrayList<>();
		startRules.add(new Event[]{TERMINAL.BORN, INTERMEDIATE.ANY, TERMINAL.DIE});
		PRODUCTIONS.put(INTERMEDIATE.START, startRules);
		
		List<Event[]> mateStartRules = new ArrayList<>();
		mateStartRules.add(new Event[]{TERMINAL.BORN, INTERMEDIATE.NO_MATE, TERMINAL.MATE, INTERMEDIATE.NO_MATE, TERMINAL.DIE});
		PRODUCTIONS.put(INTERMEDIATE.MATE_LIFE, mateStartRules);

		List<Event[]> anyRules = new ArrayList<>();
		anyRules.add(new Event[]{TERMINAL.FLY, INTERMEDIATE.ANY});
		anyRules.add(new Event[]{TERMINAL.EAT, INTERMEDIATE.ANY});
		anyRules.add(new Event[]{TERMINAL.MATE, INTERMEDIATE.NO_MATE});
		anyRules.add(new Event[]{TERMINAL.SLEEP, INTERMEDIATE.ANY});
		anyRules.add(new Event[]{TERMINAL.NOOP});
		PRODUCTIONS.put(INTERMEDIATE.ANY, anyRules);

		List<Event[]> noMateRules = new ArrayList<>();
		noMateRules.add(new Event[]{TERMINAL.FLY, INTERMEDIATE.NO_MATE});
		noMateRules.add(new Event[]{TERMINAL.EAT, INTERMEDIATE.NO_MATE});
		noMateRules.add(new Event[]{TERMINAL.SLEEP, INTERMEDIATE.NO_MATE});
		noMateRules.add(new Event[]{TERMINAL.NOOP});
		PRODUCTIONS.put(INTERMEDIATE.NO_MATE, noMateRules);
	}
	
	private Random rand;
	
	public Grammar(Random seed) {
		this.rand = seed;
	}
	
	public Event[] generateLifeHistory(Event start) {
		List<Event> life = new ArrayList<>();
		life.add(start);

		boolean needsFurtherExpansion;
		do {
			ListIterator<Event> listIterator = life.listIterator();
			needsFurtherExpansion = false;
			while (listIterator.hasNext()) {
				Event currentNode = listIterator.next();
				if (currentNode instanceof INTERMEDIATE) {
					listIterator.remove();
					Event[] expansion = expand(currentNode);
					for (Event n : expansion) {
						if (!n.equals(TERMINAL.NOOP)) {
							listIterator.add(n);
							if (n instanceof INTERMEDIATE) {
								needsFurtherExpansion = true;
							}
						}
					}	
				}
			}
		} while (needsFurtherExpansion); // && (life.size() < Colony.MAX_LIFE_SPAN));
		
		removeIntermediates(life);
		
		return life.stream().toArray(Event[]::new);
	}

	private Event[] expand(Event n) {
		List<Event[]> expansion1 = Grammar.PRODUCTIONS.get(n);
		Event[] expansion2 = expansion1.get(rand.nextInt(expansion1.size()));
		return expansion2;
	}
	
	private void removeIntermediates(List<Event> life) {
		ListIterator<Event> listIterator = life.listIterator();
		while (listIterator.hasNext()) {
			Event currentNode = listIterator.next();
			if (currentNode instanceof INTERMEDIATE) {
				listIterator.remove();
			}
		}
	}
}
