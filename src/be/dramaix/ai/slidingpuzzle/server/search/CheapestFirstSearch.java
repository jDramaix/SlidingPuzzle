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
package be.dramaix.ai.slidingpuzzle.server.search;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import be.dramaix.ai.slidingpuzzle.shared.Node;

/**
 * CFS implementation. Expand the node with shortest path
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 *
 */
public class CheapestFirstSearch extends GraphSearch {

	/**
	 * Comparator used in CFS. CFS expands always the node having the minimum path cost.
	 * 
	 */
	private class MinimumCostComparator implements Comparator<Node>{

		@Override
		public int compare(Node o1, Node o2) {
			return o1.getCost() - o2.getCost();
		}
		
	}
	
	@Override
	protected Queue<Node> createFrontier() {
		return new PriorityQueue<Node>(1000,new MinimumCostComparator());
	}

}
