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
package be.dramaix.ai.slidingpuzzle.client.rpc;

import be.dramaix.ai.slidingpuzzle.shared.AlgorithmType;
import be.dramaix.ai.slidingpuzzle.shared.HeuristicType;
import be.dramaix.ai.slidingpuzzle.shared.PuzzleSolution;
import be.dramaix.ai.slidingpuzzle.shared.State;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 *
 */
public interface ResolveServiceAsync {

	public ResolveServiceAsync INSTANCE = GWT.create(ResolveService.class);

	void resolve(State start, State goal, AlgorithmType algorithmType,
			HeuristicType heuristicType, AsyncCallback<PuzzleSolution> callback);

}
