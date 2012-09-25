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

import be.dramaix.ai.slidingpuzzle.shared.State;

/**
 * @author jDramaix
 * 
 */
public class ManhattanDistance extends AbstractHeuristic {

	@Override
	protected int calculate(State s) {
		int counter = 0;
		byte[] allCells = s.getAllCells();
		int dimension = s.getDimension();

		for (int i = 0; i < allCells.length; i++) {
			int value = allCells[i];
			if (value == 0) {
				continue;
			}

			int row = i / dimension;
			int column = i % dimension;
			int expectedRow = (value - 1) / dimension;
			int expectedColumn = (value - 1) % dimension;

			int difference = Math.abs(row - expectedRow)
					+ Math.abs(column - expectedColumn);
			counter += difference;

		}

		return counter;
	}

}
