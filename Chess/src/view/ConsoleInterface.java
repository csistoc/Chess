package view;

public class ConsoleInterface {
	
	private char[][] table;
	private static final int n = 8, m = 8;
	
	public ConsoleInterface() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if(i == 0)
					if (j == 0 || j == (m - 1))
						this.table[i][j] = 'r';
					else if (j == 1 || j == (m - 2)) 
						this.table[i][j] = 'h';
					else if (j == 2 || j == (m - 3)) 
						this.table[i][j] = 'b';
					else if(j == 3) 
						this.table[i][j] = 'q';
					else 
						this.table[i][j] = 'k';
				else if (i == 1)
					this.table[i][j] = 'p';
				else if (i == (n - 2))
					this.table[i][j] = 'P';
				else if (i == (n - 1))
					if (j == 0 || j == (m - 1))
						this.table[i][j] = 'R';
					else if (j == 1 || j == (m - 2))
						this.table[i][j] = 'H';
					else if (j == 2 || j == (m - 3))
						this.table[i][j] = 'B';
					else if(j == 3)
						this.table[i][j] = 'Q';
					else this.table[i][j] = 'K';
				else this.table[i][j] = '-';
	}
	
	public char[][] getTable() {
		return this.table;
	}
	
	public char getTable(int x, int y) {
		return this.table[x][y];
	}
	
	public int getTableSize() {
		return n;
	}
	
	public void setTable(char piece, int x, int y) {
		this.table[x][y] = piece;
	}
}
