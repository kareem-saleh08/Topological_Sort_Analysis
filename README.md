# Topological Sort Analysis

## Overview
Implementation and complexity analysis of topological sorting for directed acyclic graphs (DAGs). The project builds graph structures, applies topological sorting, and analyzes runtime performance.

## Features
- Prompts user for vertices and edges
- Builds adjacency list representation
- Executes topological sort
- Supports queue-based processing with custom data structures

## Components
- **Graph Class:** Stores vertices and adjacency lists
- **Vertex Class:** Tracks node data and indegree
- **BrowserArrayList & BrowserQueue:** Custom implementations used for queue operations

## Complexity Analysis
- **Topological Sort:** O(n)
- **Queue Operations:** O(n)
- **Edge Addition:** O(1)

## Test Cases
Covers scenarios like:
- Single-node graph
- Simple and complex DAGs
- Graphs with cycles (detects invalid topological order)
