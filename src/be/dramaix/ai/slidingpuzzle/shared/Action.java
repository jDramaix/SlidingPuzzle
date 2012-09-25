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

import be.dramaix.ai.slidingpuzzle.shared.State.CellLocation;

public class Action implements IsSerializable{
	
	private Move m;
	private CellLocation cell;
	
	public Action() {}
	
	public Action(CellLocation cell, Move m) {
		this.m = m;
		this.cell = cell;
	}
	
	public CellLocation getCellLocation() {
		return cell;
	}
	
	public Move getMove() {
		return m;
	}

	
	/**
	 * Apply this action to a state and return the new state
	 */
	public State applyTo(State s){
		byte value = s.getCellValue(cell);
		
		CellLocation nextCell = m.getNextCellLocation(cell);

		State newState = new State(s.getAllCells());
		newState.setCellValue(nextCell, value);
		newState.setCellValue(cell, (byte)0);
		
		return newState;
	}
	
	@Override
	public String toString() {
		return m+"("+cell.getRowIndex()+","+cell.getColumnIndex()+")";
	}
	
	public boolean isInverse(Action a){
		return a != null && m.getInverse() == a.getMove();
	}
}
