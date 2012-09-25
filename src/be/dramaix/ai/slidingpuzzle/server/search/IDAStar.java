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

import java.util.List;

import be.dramaix.ai.slidingpuzzle.server.search.heuristic.HeuristicFunction;
import be.dramaix.ai.slidingpuzzle.server.search.heuristic.UseHeuristic;
import be.dramaix.ai.slidingpuzzle.shared.Action;
import be.dramaix.ai.slidingpuzzle.shared.Node;
import be.dramaix.ai.slidingpuzzle.shared.Path;
import be.dramaix.ai.slidingpuzzle.shared.PuzzleSolution;
import be.dramaix.ai.slidingpuzzle.shared.State;

/**
 * Iterative deepening A*
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 *
 */
public class IDAStar implements SearchAlgorithm, UseHeuristic {

	private long startTime;
	private long exploredNodes;
	private int nextCostBound;
	private HeuristicFunction heuristic;

	@Override
	public PuzzleSolution resolve(State start, State goal) {

		Node startNode = new Node(start);
		nextCostBound = heuristic.h(startNode);
		Node solution = null;
		startTime = System.nanoTime();
		exploredNodes = 0;


		while (solution == null) {

			int currentCostBound = nextCostBound;

			solution = depthFirstSearch(startNode, currentCostBound, goal);
			
			nextCostBound+=2;

		}

		long ellapsedTime = System.nanoTime() - startTime;

		PuzzleSolution puzzleSolution = new PuzzleSolution();
		puzzleSolution.setPath(Path.fromEndNode(solution));
		puzzleSolution.setTime(ellapsedTime);
		puzzleSolution.setExploredNode(exploredNodes);

		return puzzleSolution;
	}

	
	private Node depthFirstSearch(Node current, int currentCostBound, State goal) {

		if (current.getState().equals(goal)) {
			return current;
		}
		
		exploredNodes++;
		
		if (exploredNodes % 10000 == 0) {
			//System.err.println("explored nodes for this treshold "+currentCostBound+ " : "+exploredNodes);
		}
		
		Node[] children = getChildren(current);

		for (Node next : children){

			int value = next.getCost() + heuristic.h(next);

			if (value <= currentCostBound) {
				Node result = depthFirstSearch(next, currentCostBound, goal);	
				if (result != null){
					return result;
				}
			} 

		}
		return null;

	}

	private Node[] getChildren(Node node) {

		State s = node.getState();
		List<Action> possibleActions = s.getPossibleActions();
		Action oldAction = node.getAction();
		
		//don't count inverse move
		int childrenSize = oldAction == null ? possibleActions.size() : possibleActions.size() - 1;
		Node[] children = new Node[childrenSize];
		
		int i = 0;
		for (Action a : possibleActions) {
			if (!a.isInverse(oldAction)) {
				
				State childState = a.applyTo(s);
				Node child = new Node(childState);
				child.setParent(node);
				child.setAction(a);
				children[i++] = child;
			}

		}

		return children;
	}


	@Override
	public void setHeuristic(HeuristicFunction h) {
		this.heuristic = h;
		
	}

}
