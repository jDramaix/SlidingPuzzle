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

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Selector;
import com.google.gwt.query.client.Selectors;

/**
 * GQuery Compile Time Selectors
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 *
 */
interface MySelectors extends Selectors {

	public MySelectors SELECTOR = GWT.create(MySelectors.class);
	
	@Selector("#heuristicType")
	GQuery getHeuristicSelectElement();
	
	@Selector("#algorithmType")
	GQuery getAlgorithmSelectElement();
	
	@Selector("#puzzle")
	GQuery getPuzzleBoard();
	
	@Selector("#random")
	GQuery getRandomButton();
	
	@Selector("#play")
	GQuery getPlayButton();
	
	@Selector("#solve")
	GQuery getSolveButton();
	
	@Selector("input[name='puzzleType']")
	GQuery getPuzzleTypeRadios();
	
	@Selector("#numberOfMoves")
	GQuery getNbrOfMoveSpan();
	
	@Selector("#elapsedTime")
	GQuery getEllapsedTimeSpan();
	
	@Selector("#path")
	GQuery getPathSpan();

	@Selector("#actionsBar")
	GQuery getActionsBar();
	
	@Selector("#error")
	GQuery getErrorMsg();
	
	@Selector("#timeout")
	GQuery getTimeoutMsg();
	
	@Selector("#timeout, #error")
	GQuery getInformationMessages();
	
	
}