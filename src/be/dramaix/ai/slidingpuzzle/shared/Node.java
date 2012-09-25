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
package be.dramaix.ai.slidingpuzzle.shared;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Node implements IsSerializable {
	
	private State state;
	//private Move move;
	private int cost = 0;
	private int heuristic = -1;
	private Node parent = null;
	private Action nextAction;
	
	public Node() {}
	
	public Node(State state/*, Move m*/) {
		this.state = state;
		//this.move = m;
	}
	
	public State getState() {
		return state;
	}

	
	/**
	 * equality based on state
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node){
			return state.equals(((Node)obj).state);
		}
		return false;
	}
	
	/**
	 * equality based on state
	 */
	@Override
	public int hashCode() {
		return state.hashCode();
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
		cost = parent.getCost() + 1;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public int getCost() {
		return cost;
	}

	public Action getAction() {
		return nextAction;
	}
	
	public void setAction(Action next){
		this.nextAction = next;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
		
	}

}
