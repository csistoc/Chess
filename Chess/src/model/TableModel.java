package model;

import java.util.ArrayList;

public class TableModel {
	
	private ArrayList<PieceModel> pieces;
	private int sizeX, sizeY;
	
	public TableModel(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		pieces = new ArrayList<PieceModel>();
		for (int i = 0; i < sizeX; i++)
			for (int j = 0; j < sizeY; j++)
				if(i == 0)
					if (j == 0 || j == (sizeY - 1))
						pieces.add(new PieceModel(i, j, Player.PLAYER1, PieceType.ROOK));
					else if (j == 1 || j == (sizeY - 2)) 
						pieces.add(new PieceModel(i, j, Player.PLAYER1, PieceType.HORSE));
					else if (j == 2 || j == (sizeY - 3)) 
						pieces.add(new PieceModel(i, j, Player.PLAYER1, PieceType.BISHOP));
					else if(j == 4) 
						pieces.add(new PieceModel(i, j, Player.PLAYER1, PieceType.QUEEN));
					else 
						pieces.add(new PieceModel(i, j, Player.PLAYER1, PieceType.KING));
				else if (i == 1) 
					pieces.add(new PieceModel(i, j, Player.PLAYER1, PieceType.PAWN));
				else if (i == (sizeX - 2))
					pieces.add(new PieceModel(i, j, Player.PLAYER2, PieceType.PAWN));
				else if (i == (sizeX - 1))
					if (j == 0 || j == (sizeY - 1))
						pieces.add(new PieceModel(i, j, Player.PLAYER2, PieceType.ROOK));
					else if (j == 1 || j == (sizeY - 2))
						pieces.add(new PieceModel(i, j, Player.PLAYER2, PieceType.HORSE));
					else if (j == 2 || j == (sizeY - 3))
						pieces.add(new PieceModel(i, j, Player.PLAYER2, PieceType.BISHOP));
					else if(j == 4)
						pieces.add(new PieceModel(i, j, Player.PLAYER2, PieceType.QUEEN));
					else 
						pieces.add(new PieceModel(i, j, Player.PLAYER2, PieceType.KING));
				else 
					pieces.add(new PieceModel(i, j, Player.NEUTRAL, PieceType.BLANK));
	}
	
	public TableModel(TableModel table) {
		this.pieces = new ArrayList<PieceModel>();
		for (int i = 0; i < table.getPieces().size(); i++) {
			this.pieces.add(new PieceModel(table.getPieces().get(i).getLocation().getX(), table.getPieces().get(i).getLocation().getY(),
					table.getPieces().get(i).getOwner(), table.getPieces().get(i).getType()));
		}
		this.sizeX = table.sizeX;
		this.sizeY = table.sizeY;
	}
	
	public ArrayList<PieceModel> getPieces() {
		return pieces;
	}

	public PieceModel getPieces(int x, int y) {
		int out = -1;
		for (int i = 0; i < pieces.size(); i++)
			if (x == pieces.get(i).getLocation().getX() && y == pieces.get(i).getLocation().getY()) {
				out = i;
				break;
			}
		return pieces.get(out);
	}
	
	public int getPieceIndex(int x, int y) {
		int out = -1;
		for (int i = 0; i < pieces.size(); i++)
			if (x == pieces.get(i).getLocation().getX() && y == pieces.get(i).getLocation().getY()) {
				out = i;
				break;
			}
		return out;
	}
	
	public PieceModel getPieces(int x, int y, PieceType type) {
		int out = -1;
		for (int i = 0; i < pieces.size(); i++)
			if ((x == pieces.get(i).getLocation().getX() && y == pieces.get(i).getLocation().getY()) &&
					pieces.get(i).getType().equals(type)) {
				out = i;
				break;
			}
		return pieces.get(out);
	}
	
	public void setPieces(PieceModel in, int x, int y) {
		for (int i = 0; i < pieces.size(); i++)
			if (x == pieces.get(i).getLocation().getX() && y == pieces.get(i).getLocation().getY()) {
				this.pieces.get(i).setPiece(in);
				break;
		}
	}

	public void setPieces(ArrayList<PieceModel> pieces) {
		this.pieces = pieces;
	}
	
	public void setLocation(int x, int y, int index) {
		
	}
	
	public String toString() {
		String output = new String();
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++)
				if (getPieces(i, j).getType().name().equals("BLANK"))
					output += "  ";
				else if (getPieces(i, j).getOwner().equals(Player.PLAYER1))
						output += getPieces(i, j).getType().name().charAt(0) + " ";
				else output += Character.toLowerCase(getPieces(i, j).getType().name().charAt(0)) + " ";
			output += "\n";
		}
		output += "\n";
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++)
				if (getPieces(i, j).getOwner().name().equals("NEUTRAL"))
					output += "  ";
				else output += getPieces(i, j).getOwner().name().charAt(getPieces(i, j).getOwner().name().length() - 1) + " ";
			output += "\n";
		}
		return output;
	}
	
	public String dataToString() {
		String output = new String();
		for (int i = 0; i < pieces.size(); i++)
			output += pieces.get(i) + "\n";
		return output;
	}
}
