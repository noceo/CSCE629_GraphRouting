# CSCE629 - GraphRouting

The project aims to give a better understanding on how both Dijkstra’s and Kruskal’s algorithm work
in practice. Also by measuring the time one can see under which circumstances which algorithm
performs best. Two different versions of Dijkstra’s algorithm and one version of Kruskal’s algorithm
have been adapted to solve the Max-Bandwidth-Path problem. The problem is stated as follows:
"Given a weighted and undirected graph G and two vertices s and t, ﬁnd a path P from s to t that
has the maximum bandwidth". The bandwidth of the path is therefore determined by the smallest
weight in P . The naive implementation of Dijkstra’s algorithm is compared with a version that uses a
heap for storing vertices and Kruskal’s algorithm that ﬁrst ﬁnds the maximum spanning tree and then
performs the heap version of Dijkstra on that maximum spanning tree. Both algorithms used in this
project are described in the following two sections.