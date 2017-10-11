package org.powergrid.pjc.ps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ThingState {
	ENABLE,
	DISABLE,
	KO;	
	
	private static final List<ThingState> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	  public static ThingState randomState()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	  }
}
