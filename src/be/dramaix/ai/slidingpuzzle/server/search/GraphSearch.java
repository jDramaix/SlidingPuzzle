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

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import be.dramaix.ai.slidingpuzzle.shared.Action;
import be.dramaix.ai.slidingpuzzle.shared.Node;
import be.dramaix.ai.slidingpuzzle.shared.Path;
import be.dramaix.ai.slidingpuzzle.shared.PuzzleSolution;
import be.dramaix.ai.slidingpuzzle.shared.State;

/**
 * Abstract class for graph search algorithm using an open and closed nodes lists.
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 *
 */
public abstract class GraphSearch implements SearchAlgorithm {

	private Set<State> explored;
	private Set<Node> frontierSet;
	private Queue<Node> frontier;
	private State goalState;

	@Override
	public PuzzleSolution resolve(State start, State goal) {

		init(start, goal);
		
		long startTime = System.nanoTime();
		
		Node finalNode = run();
		
		long ellapsedTime =  System.nanoTime() - startTime;

		PuzzleSolution solution = new PuzzleSolution();
		solution.setPath(Path.fromEndNode(finalNode));
		solution.setTime(ellapsedTime);
		solution.setExploredNode(explored.size());
		
		return solution;
		
	}

	protected abstract Queue<Node> createFrontier();

	protected void init(State start, State goal) {
		explored = new HashSet<State>();
		frontier = createFrontier();
		frontierSet = new HashSet<Node>();
		this.goalState = goal;

		addToFrontier(new Node(start));

	}
	
	public void addToFrontier(Node n){
		frontier.add(n);
		frontierSet.add(n);
	}

	protected Node removeChoice() {
		Node n = frontier.poll();
		frontierSet.remove(n);
		return n;
	}
	
	protected List<Action> getPossibleAction(State s) {	
		return s.getPossibleActions();
	}

	
	protected Node run() {


		while (!frontier.isEmpty()) {
			
			Node n = removeChoice();
			
			if (n == null){
				continue;
			}

			State s = n.getState();

			//is it the goal ?
			if (isGoalState(s)) {
				System.out.println("Solution found");
				return n;
			}

			explored.add(s);

			List<Action> possibleActions = getPossibleAction(s);

			for (Action action : possibleActions) {
				State newState = action.applyTo(s);
				if (!explored.contains(newState)) {
					Node newNode = new Node(newState);
					newNode.setParent(n);
					newNode.setAction(action);

					if (!frontierSet.contains(newNode)) {
						addToFrontier(newNode);
					}
				}
			}

		}

		// no solution found
		return null;
	}
	

	//can be overridden
	protected boolean isGoalState(State s){
		return s.equals(goalState);
	}

}
