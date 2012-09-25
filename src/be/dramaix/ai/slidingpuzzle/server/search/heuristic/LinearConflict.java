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
public class LinearConflict extends ManhattanDistance{

	@Override
	protected int calculate(State s) {
		
		int heuristic = super.calculate(s);
		
		heuristic += linearVerticalConflict(s);
		heuristic += linearHorizontalConflict(s);
		
		return heuristic;

	}

   private int linearVerticalConflict(State s) {
		int dimension = s.getDimension();
		int linearConflict = 0;
		
		for (int row = 0; row < dimension; row++){
			byte max = -1;
			for (int column = 0;  column < dimension; column++){
				byte cellValue = s.getCellValue(row,column);
				//is tile in its goal row ?
				if (cellValue != 0 && (cellValue - 1) / dimension == row){
					if (cellValue > max){
						max = cellValue;
					}else {
						//linear conflict, one tile must move up or down to allow the other to pass by and then back up
						//add two moves to the manhattan distance
						linearConflict += 2;
					}
				}
				
			}
			
		}
		return linearConflict;
	}

   private int linearHorizontalConflict(State s) {
		
		int dimension = s.getDimension();
		int linearConflict = 0;
		
		for (int column = 0; column < dimension; column++){
			byte max = -1;
			for (int row = 0;  row < dimension; row++){
				byte cellValue = s.getCellValue(row,column);
				//is tile in its goal row ?
				if (cellValue != 0 && cellValue % dimension == column + 1){
					if (cellValue > max){
						max = cellValue;
					}else {
						//linear conflict, one tile must move left or right to allow the other to pass by and then back up
						//add two moves to the manhattan distance
						linearConflict += 2;
					}
				}
				
			}
			
		}
		return linearConflict;
	}
	
	

}
