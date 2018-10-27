package iptrace;
public class RouterTables {
	int routers;
	public RouterTables(float matrix[][], int routingTable[][], int routers) {
		this.routers = routers;
		//routingTable = new int[routers][routers];
		for(int i = 0; i < routers; i++) {
			for(int j = 0; j < routers; j++) {
				if(matrix[i][j] != 0)
					routingTable[i][j] = 1;
				else 
					routingTable[i][j] = 0;
			}
		}
	}
}
