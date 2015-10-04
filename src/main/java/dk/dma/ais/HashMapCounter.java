package dk.dma.ais;

import java.util.HashMap;

public class HashMapCounter<K> extends HashMap<K, Integer> {

	private static final long serialVersionUID = 1L;

	public void add(K object) {
		if (containsKey(object)) {
			put(object, get(object) + 1);
		} else {
			this.put(object, 1);
		}

	}

}
