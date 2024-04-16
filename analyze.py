import os
import numpy as np
import matplotlib.pyplot as plt

results = []
with open('results.txt') as f:
  for line in f:
      results.append([int(x) for x in line.split(':')[1].strip()[1:-1].split(', ')])

results = np.array(results)
results = results.astype(float) * 0.001

dijkstra_naive_mean_sparse = np.mean(results[0])
dijkstra_heap_mean_sparse = np.mean(results[1])
kruskal_mean_sparse = np.mean(results[2])

dijkstra_naive_mean_dense = np.mean(results[3])
dijkstra_heap_mean_dense = np.mean(results[4])
kruskal_mean_dense = np.mean(results[5])

print('-----------SPARSE GRAPH-----------')
print('-----DijkstraNaive-----')
print('Mean: ', dijkstra_naive_mean_sparse, '\n')

print('-----DijkstraHeap-----')
print('Mean: ', dijkstra_heap_mean_sparse, '\n')

print('-----Kruskal-----')
print('Mean: ', kruskal_mean_sparse, '\n')

print('-----------DENSE GRAPH-----------')
print('-----DijkstraNaive-----')
print('Mean: ', dijkstra_naive_mean_dense)
print('Standard Deviation: ', dijkstra_naive_mean_dense, '\n')

print('-----DijkstraHeap-----')
print('Mean: ', dijkstra_heap_mean_dense, '\n')

print('-----Kruskal-----')
print('Mean: ', kruskal_mean_dense, '\n')

os.makedirs('./testruns', exist_ok=True)
plt.xlabel('Execution')
plt.ylabel('Milliseconds')
plt.plot(results[0], 'r.-', label='Dijkstra Naive')
plt.plot(results[1], 'b.-', label='Dijkstra Heap')
plt.plot(results[2], 'g.-', label='Kruskal')
plt.legend(loc='upper right', borderaxespad=0.)
plt.savefig('./testruns/sparse.png')


plt.figure()
plt.xlabel('Execution')
plt.ylabel('Milliseconds')
plt.plot(results[3], 'r.-', label='Dijkstra Naive')
plt.plot(results[4], 'b.-', label='Dijkstra Heap')
plt.plot(results[5], 'g.-', label='Kruskal')
plt.legend(loc='upper right', borderaxespad=0.)
plt.savefig('./testruns/dense.png')