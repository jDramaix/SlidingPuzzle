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
package be.dramaix.ai.slidingpuzzle.server;

import java.util.HashMap;
import java.util.Map;

import be.dramaix.ai.slidingpuzzle.client.rpc.ResolveService;
import be.dramaix.ai.slidingpuzzle.server.search.AStar;
import be.dramaix.ai.slidingpuzzle.server.search.BreadthFirstSearch;
import be.dramaix.ai.slidingpuzzle.server.search.IDAStar;
import be.dramaix.ai.slidingpuzzle.server.search.SearchAlgorithm;
import be.dramaix.ai.slidingpuzzle.server.search.heuristic.HeuristicFunction;
import be.dramaix.ai.slidingpuzzle.server.search.heuristic.LinearConflict;
import be.dramaix.ai.slidingpuzzle.server.search.heuristic.ManhattanDistance;
import be.dramaix.ai.slidingpuzzle.server.search.heuristic.PatternDatabase;
import be.dramaix.ai.slidingpuzzle.server.search.heuristic.UseHeuristic;
import be.dramaix.ai.slidingpuzzle.shared.AlgorithmType;
import be.dramaix.ai.slidingpuzzle.shared.HeuristicType;
import be.dramaix.ai.slidingpuzzle.shared.PuzzleSolution;
import be.dramaix.ai.slidingpuzzle.shared.State;

import com.google.apphosting.api.DeadlineExceededException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @author Julien Dramaix (https://plus.google.com/u/0/103916508880440628637)
 * 
 */
@SuppressWarnings("serial")
public class ResolveServiceImpl extends RemoteServiceServlet implements
		ResolveService {

	private static final Map<HeuristicType, Class<? extends HeuristicFunction>> heuristicMapping;
	private static final Map<AlgorithmType, Class<? extends SearchAlgorithm>> algorithmMapping;
	
	static {
		heuristicMapping = new HashMap<HeuristicType, Class<? extends HeuristicFunction>>();
		heuristicMapping.put(HeuristicType.MANHATTAN_DISTANCE, ManhattanDistance.class);
		heuristicMapping.put(HeuristicType.LINEAR_CONFLICT, LinearConflict.class);
		heuristicMapping.put(HeuristicType.PATTERN_DATABASE, PatternDatabase.class);
		
		algorithmMapping = new HashMap<AlgorithmType, Class<? extends SearchAlgorithm>>();
		algorithmMapping.put(AlgorithmType.BDF, BreadthFirstSearch.class);
		algorithmMapping.put(AlgorithmType.A_STAR, AStar.class);
		algorithmMapping.put(AlgorithmType.IDA_STAR, IDAStar.class);
		
		
	}
	
	@Override
	public PuzzleSolution resolve(State start, State goal,
			AlgorithmType algorithmType, HeuristicType heuristicType) {

		SearchAlgorithm search = initSearchAlgorithm(algorithmType, heuristicType);

		try {
			
			return search.resolve(start, goal);
		
		} catch (DeadlineExceededException e) {
			PuzzleSolution solution = new PuzzleSolution();
			solution.setTimeoutOccured(true);
			return solution;
		}
	}

	
	private <T> T getInstance(Class<T> clazz){
		
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Impossible to instantiate the class "+clazz, e);
		} 
	}

	
	private SearchAlgorithm initSearchAlgorithm(AlgorithmType algorithmType,
			HeuristicType heuristicType) {
		SearchAlgorithm search = getInstance(algorithmMapping.get(algorithmType));
		
		if (search instanceof UseHeuristic) {
			HeuristicFunction heuristic = getInstance(heuristicMapping.get(heuristicType));
			((UseHeuristic) search).setHeuristic(heuristic);
		}
		
		return search;
	}

}