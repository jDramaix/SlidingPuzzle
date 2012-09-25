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


public class Path implements IsSerializable {
	
	private Node[] nodes;
	
	public static Path fromEndNode(Node endNode){
		if (endNode == null){
			return null;
		}
		
		Path p = new Path(endNode.getCost()+1);
		
		Node[] nodes = p.getNodes();
		nodes[endNode.getCost()] = endNode;
		
		Node parent = endNode.getParent();
		Node current = null;
		while(parent != null){
			current = parent;
			nodes[current.getCost()] = current;
			parent = current.getParent();
		}
		
		return p;
	}
	
	public Path() {}
	
	public Path(int lenght){
		nodes = new Node[lenght];
	}
	
	public int length(){
		return nodes.length;
	}
	
	public Node[] getNodes() {
		return nodes;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < nodes.length; i++){
			Node n = nodes[i];
			builder.append(n.getAction());
			if (i < nodes.length - 1){
				builder.append(", ");
			}
			
		}
		
		return builder.toString();
	}
}
