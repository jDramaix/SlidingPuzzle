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

import be.dramaix.ai.slidingpuzzle.shared.State.CellLocation;

public enum Move {

	UP(0, -1), DOWN(0, 1), RIGHT(1, 0), LEFT(-1, 0);

	private int horizontalMove;
	private int verticalMove;

	private Move(int horizontal, int vertical) {
		this.horizontalMove = horizontal;
		this.verticalMove = vertical;
	}

	public int getHorizontalMove() {
		return horizontalMove;
	}

	public int getVerticalMove() {
		return verticalMove;
	}

	public CellLocation getNextCellLocation(CellLocation currentLocation) {
		return new CellLocation(getNextRow(currentLocation.getRowIndex()),
				getNextColumn(currentLocation.getColumnIndex()));
	}

	private int getNextRow(int currentRow) {
		return currentRow + verticalMove;
	}

	private int getNextColumn(int currentColumn) {
		return currentColumn + horizontalMove;
	}

	public Move getInverse() {
		switch (this) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;

		}
		return null;
	}
}
