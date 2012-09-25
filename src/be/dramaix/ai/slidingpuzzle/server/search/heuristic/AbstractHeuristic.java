/*
 * Copyright 2011 Julien Dramaix.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package be.dramaix.ai.slidingpuzzle.server.search.heuristic;

import java.util.PriorityQueue;

import be.dramaix.ai.slidingpuzzle.shared.Node;
import be.dramaix.ai.slidingpuzzle.shared.State;

/**
 * Abstract class for heuristic function. This class implement a caching system
 * at the Node level for performance purpose. Indeed, some algorithm like A* use
 * {@link PriorityQueue} to manage its open nodes list that implies to calcute
 * often the heuristic value of a Node.
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 * 
 */
public abstract class AbstractHeuristic implements HeuristicFunction {

	@Override
	public int h(Node n) {
		int cachedValue = n.getHeuristic();
		
		if (cachedValue == -1){
			cachedValue = calculate(n.getState());
			
			n.setHeuristic(cachedValue);
		}
		
		
		
		return cachedValue;
	}

	protected abstract int calculate(State state);

}
