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

import be.dramaix.ai.slidingpuzzle.server.search.heuristic.HeuristicFunction;
import be.dramaix.ai.slidingpuzzle.server.search.heuristic.UseHeuristic;
import be.dramaix.ai.slidingpuzzle.shared.Node;

public class AStar extends GraphSearch implements UseHeuristic {

	public static class HeuristicComparator implements Comparator<Node>{

		private HeuristicFunction heuristic;
		
		public HeuristicComparator(HeuristicFunction heuristic) {
			this.heuristic = heuristic;
		}

		@Override
		public int compare(Node o1, Node o2) {
			
			int result = (o1.getCost() + heuristic.h(o1)) - (o2.getCost() + heuristic.h(o1));	
			
			if (result == 0){
				//Ties among minimal f values are resolved in favor of the deepest node in the search tree
				//i.e. the closest node to the goal
				result =  o2.getCost() - o1.getCost();			
				
			}
			
			return result;
			
		}
	}

	private HeuristicFunction heuristic;
	
	@Override
	protected PriorityQueue<Node> createFrontier() {
		return new PriorityQueue<Node>(10000, new HeuristicComparator(heuristic));
	}

	@Override
	public void setHeuristic(HeuristicFunction h) {
		this.heuristic = h;
		
	}

	
}
