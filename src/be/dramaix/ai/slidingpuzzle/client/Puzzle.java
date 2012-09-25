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
package be.dramaix.ai.slidingpuzzle.client;

import static be.dramaix.ai.slidingpuzzle.client.MySelectors.SELECTOR;
import static com.google.gwt.query.client.GQuery.$;
import be.dramaix.ai.slidingpuzzle.shared.Action;
import be.dramaix.ai.slidingpuzzle.shared.Path;
import be.dramaix.ai.slidingpuzzle.shared.State;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Element;

/**
 * Class managing the puzzle.
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 *
 */
public class Puzzle {

	private static final String SLIDE_HELPER_HTML = "<div class='empty helper' style='position: absolute'></div><div id='slideHelper' class='tile helper'></div>";
	private static final int TILE_DIMENSION = 83;
	
	private int dimension;
	private State startState;
	private Path result;

	public Puzzle() {
		this.dimension = 4;
		this.startState = State.getGoalState(dimension);
		bind();
	}

	public int getDimension() {
		return dimension;
	}

	public State getState() {
		return startState;
	}

	public void loadState(State s) {

		GQuery puzzle = SELECTOR.getPuzzleBoard();
		puzzle.empty();
		
		byte[] board = s.getAllCells();

		for (int i = 0; i < board.length; i++) {
			byte value = board[i];
			GQuery cell = $("<div class='tile'></div>");
			if (value == 0) {
				cell.addClass("empty");
			} else {
				cell.html("" + value);
			}
			puzzle.append(cell);

		}

		//
		int puzzleDimension = TILE_DIMENSION * dimension;
		puzzle.height(puzzleDimension).width(puzzleDimension);
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public void setResult(Path result) {
		this.result = result;
	}

	private void bind() {
		// randomize the puzzle
		SELECTOR.getRandomButton().click(new Function() {
			public void f() {
				SELECTOR.getInformationMessages().hide();
				
				startState = State.createRandom(dimension);
				loadState(startState);
				
				SELECTOR.getPlayButton().hide();
				SELECTOR.getSolveButton().show();
			}
		});

		// play the result
		SELECTOR.getPlayButton().click(new Function() {
			public void f() {
				loadState(startState);
				play();
			}
		});

		//dimension of the puzzle change ?
		SELECTOR.getPuzzleTypeRadios().click(new Function() {
			public void f(Element e) {
				int newDimension = Integer.parseInt($(e).val());
				onDimensionChange(newDimension);
			}
		});
	}

	private void onDimensionChange(int newDimension) {
		dimension = newDimension;
		startState = State.getGoalState(dimension);
		loadState(startState);
	}

	/**
	 * Play the result previously returned by the algorithm
	 */
	private void play() {
		if (result == null) {
			return;
		}

		slide(0, 250);

	}

	private void slide(final int currentIndex, final int time) {

		if (currentIndex == result.length() - 1) {
			return;
		}

		State start = result.getNodes()[currentIndex].getState();
		final State end = result.getNodes()[currentIndex + 1].getState();
		Action a = result.getNodes()[currentIndex + 1].getAction();

		int xSlide = TILE_DIMENSION * a.getMove().getHorizontalMove();
		int ySlide = TILE_DIMENSION * a.getMove().getVerticalMove();

		int topInitial = TILE_DIMENSION * a.getCellLocation().getRowIndex();
		int leftInitial = TILE_DIMENSION * a.getCellLocation().getColumnIndex();

		int topFinal = topInitial + ySlide;
		int leftFinal = leftInitial + xSlide;

		int value = start.getCellValue(a.getCellLocation());

		GQuery cell = $(SLIDE_HELPER_HTML).css("top", "" + topInitial + "px")
				.css("left", "" + leftInitial + "px");

		SELECTOR.getPuzzleBoard().append(cell);

		String animation = "left: '" + leftFinal + "px'; top: '" + topFinal
				+ "px';";

		$("#slideHelper").text("" + value).animate(animation, time)
				.delay(50, new Function() {
					@Override
					public void f() {
						loadState(end);
						slide(currentIndex + 1, time);
					}
				});

	}
}
