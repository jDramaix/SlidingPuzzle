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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import be.dramaix.ai.slidingpuzzle.shared.State;

/**
 * @author jDramaix
 * 
 */
public class PatternDatabase extends AbstractHeuristic {

	private static final String FILE_PREFIX = "/db/";
	private static final String FILE_SUFFIX = ".db";
	
	

	public enum PatternDatabaseType {
		FIFTEEN_3_6_6_1(new byte[]{2,3,4}), 
		FIFTEEN_3_6_6_2(new byte[] {1,5,6,9,10,13}), 
		FIFTEEN_3_6_6_3(new byte[]{7,8,11,12,14,15});
		


		private byte[] costTable;
		private byte[] tilesInPattern;

		private PatternDatabaseType(byte[] tilesInPattern ) {
			this.tilesInPattern = tilesInPattern;
			costTable = new byte[(int)Math.pow(2, tilesInPattern.length * 4)];
			loadTableCost();
		}

		private void loadTableCost() {
			
			System.err.println("Load database for " + name());
			long startTime = System.currentTimeMillis();
		
			StringBuilder builder = new StringBuilder(FILE_PREFIX);
			builder.append(name()).append(FILE_SUFFIX);

			try {
				loadFile(builder.toString());
			} catch (IOException e) {
				throw new RuntimeException(
						"Impossible to load databasepattern", e);
			}
			
			System.err.println("Database loaded in "
					+ (System.currentTimeMillis() - startTime));

		}

		private void loadFile(String fileName) throws IOException {
			InputStream is = PatternDatabaseType.class.getResourceAsStream(fileName);
			DataInputStream inputStream = new DataInputStream(
					new BufferedInputStream(is));
			try {
				int i = 0;
				while (true) {
					costTable[i++] = inputStream.readByte();
				}
			} catch (EOFException e) {

			}

			inputStream.close();

		}
		public byte getCost(int index) {
			return costTable[index];
		}

		public int getPatternPosition(byte value) {
			for (int i = 0; i < tilesInPattern.length; i++){
				byte tile = tilesInPattern[i];
				if (tile == value){
					return i;
				}
			}
			
			return -1; //not found
		}
	}


	@Override
	protected int calculate(State s) {

		int index_1 = 0;
		int index_2 = 0;
		int index_3 = 0;
		
		byte[] allCells =  s.getAllCells();

		for (int i = 0; i < allCells.length; i++){
			byte value = allCells[i];
			int position = -1;
			if ((position = PatternDatabaseType.FIFTEEN_3_6_6_1.getPatternPosition(value)) != -1){
				index_1 |= i << (position << 2) ;
			}else if ((position = PatternDatabaseType.FIFTEEN_3_6_6_2.getPatternPosition(value)) != -1){
				index_2 |= i << (position << 2) ;
			}else if ((position = PatternDatabaseType.FIFTEEN_3_6_6_3.getPatternPosition(value)) != -1){
				index_3 |= i << (position << 2) ;
			}
		}
		return PatternDatabaseType.FIFTEEN_3_6_6_1.getCost(index_1)
				+ PatternDatabaseType.FIFTEEN_3_6_6_2.getCost(index_2)
				+ PatternDatabaseType.FIFTEEN_3_6_6_3.getCost(index_3);

	}

}
