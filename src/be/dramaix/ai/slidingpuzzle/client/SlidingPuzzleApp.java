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
import be.dramaix.ai.slidingpuzzle.client.rpc.ResolveServiceAsync;
import be.dramaix.ai.slidingpuzzle.shared.AlgorithmType;
import be.dramaix.ai.slidingpuzzle.shared.HeuristicType;
import be.dramaix.ai.slidingpuzzle.shared.PuzzleSolution;
import be.dramaix.ai.slidingpuzzle.shared.State;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * Entry point of the application.
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 * 
 */
public class SlidingPuzzleApp implements EntryPoint {

	
	private LoadingDialog loading;
	private ConfigPanel config;
	private Puzzle puzzle;
	private ResultPanel result;

	/**
	 * Entry point method of the application
	 */
	@Override
	public void onModuleLoad() {
		puzzle = new Puzzle();
		config = new ConfigPanel();
		result = new ResultPanel();
		loading = new LoadingDialog();
			
		bind();
		initButtons();
	}

	private void initButtons() {
		SELECTOR.getSolveButton().hide();
		SELECTOR.getPlayButton().hide();
		SELECTOR.getActionsBar().show();
		
	}
	
	/**
	 * Bind a click handler on the solve button.
	 */
	private void bind() {

		$("#solve").click(new Function() {
			public void f() {
				SELECTOR.getInformationMessages().hide();
				loading.show();
				result.reset();
				solve();
			}
		});

	}


	/**
	 * Call the search algorithm
	 */
	private void solve() {
		State start = puzzle.getState();
		int dimension = puzzle.getDimension();
		AlgorithmType algorithmType = config.getAlgorithmType();
		HeuristicType heuristicType = config.getHeuristicType();
		
		ResolveServiceAsync.INSTANCE.resolve(start,
				State.getGoalState(dimension), algorithmType, heuristicType,
				new AsyncCallback<PuzzleSolution>() {

					@Override
					public void onSuccess(PuzzleSolution solution) {
						if (!solution.isTimeoutOccured()){
							result.loadSolution(solution);
							puzzle.setResult(solution.getPath());
							SELECTOR.getPlayButton().show();
						}else{
							SELECTOR.getTimeoutMsg().show();
						}
						
						loading.hide();

					}

					@Override
					public void onFailure(Throwable caught) {
						SELECTOR.getErrorMsg().show();
						loading.hide();
					}
				});
	}


}
