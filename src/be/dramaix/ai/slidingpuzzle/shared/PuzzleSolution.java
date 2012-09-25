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

public class PuzzleSolution implements IsSerializable {

	private Path path;
	private long time;
	private long exploredNode;
	private boolean timeout;

	public PuzzleSolution() {
	}

	/**
	 * @return the optimal path to reach to goal
	 */
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * @return the time in nanoseconds take by the algorithm to find the
	 *         solution
	 */
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	/**
	 * @return the number of nodes explored by the algorithm
	 */
	public long getExploredNode() {
		return exploredNode;
	}
	
	public void setExploredNode(long exploredNode) {
		this.exploredNode = exploredNode;
	}
	
	public boolean isTimeoutOccured(){
		return timeout;
	}
	
	public void setTimeoutOccured(boolean timeout){
		this.timeout = timeout;
	}
}
