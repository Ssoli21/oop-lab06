package main.java.it.unibo.generics.graph.impl;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import it.unibo.generics.graph.api.Graph;

public class GraphImpl<N> implements Graph<N>{
    private final Map<N, Set<N>> graph = new LinkedHashMap<>();

    public void addNode(N node){
        graph.putIfAbsent(node, new LinkedHashSet<N>());
    }

    public void addEdge(N source, N target){
        if (!graph.containsKey(source) || !graph.containsKey(target)){
            throw new IllegalArgumentException("source or target not found in the key set");
        }
        graph.get(source).add(target);
    }

    public Set<N> nodeSet(){
        return Set.copyOf(graph.keySet());
    }

    public Set<N> linkedNodes(N node){
        return Set.copyOf(graph.get(node));
    }

    public List<N> getPath(N source, N target){
        if (!graph.containsKey(source) || !graph.containsKey(target)){
            throw new IllegalArgumentException("source or target not found in the key set");
        }
        Queue<N> queue = new LinkedList<>();
        Set<N> visited = new LinkedHashSet<>();
        Map<N,N> previous = new LinkedHashMap<>();
        visited.add(source);
        queue.add(source);
        previous.put(source, null);

        while(!queue.isEmpty()){
            N u = queue.poll();
            Set<N> adj = graph.get(u);
            if (u == target){
                List<N> path = new LinkedList<>();
                while (u != null) {
                    path.add(0,u);
                    u = previous.get(u);
                }
                return path;
            }
            for (N n : adj) {
                if (!visited.contains(n)){
                    visited.add(n);
                    queue.add(n);
                    previous.put(n, u);
                }
            }
        }
        return new LinkedList<N>();
    }
}
