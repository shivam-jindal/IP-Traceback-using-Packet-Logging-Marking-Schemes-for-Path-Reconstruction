package iptrace;
class Network {
	private int visited[];
	private float distance[];
	private int i, j, k, u, routers, source;
	private float newdistance;
	public Network(float graph[][], int source, int destination, int no_routers, int parents[]) {
		routers = no_routers;
		this.source = source;
		visited = new int[routers];
		distance = new float[routers];
		for(i = 0; i < routers; i++) {
			visited[i] = 0;
			parents[i] = -1;
		}
		visited[source] = 1;
		shortestPath(graph, parents);
	}
	public void shortestPath(float graph[][], int parents[]) {
		for(i = 0; i < routers; i++) {
			if(graph[source][i] == 0) {
				distance[i] = 99999;
			}
			else {
				distance[i] = graph[source][i];
				parents[i] = source;
			}
		}
		for(k = 0; k < (routers - 1); k++) {
			u = findmin(distance, routers, visited);
			visited[u] = 1;
			for(j = 0; j < routers; j++) {
				if(visited[j] == 0) {
					if(graph[u][j] != 0)
						newdistance = distance[u] + graph[u][j];
					else
						newdistance = 99999;
					if(newdistance < distance[j]) {
						distance[j] = newdistance;
						parents[j] = u;
					}
				}
			}
		}
		for(int i = 0; i<routers; i++){
			System.out.println(" "+ parents[i]);
		}
	}
	int findmin(float distance[], int n, int visited[]) {
		int i, minpos;
		float min;
		min = distance[0];
		minpos = 0;
		for(i = 1; i < n; i++) {
			if(distance[i] < min && visited[i] == 0) {
				min = distance[i];
				minpos = i;
			}
		}
		return minpos;
	}
}

