package model;

import java.util.ArrayList;

public class CheckPiece {
	private boolean isCheck;
	private PieceModel isInCheckBy;
	private ArrayList<Integer> freeMovements;
	
	public CheckPiece(boolean isCheck, PieceModel isInCheckBy, ArrayList<Integer> freeMovements) {
		this.isCheck = isCheck;
		this.isInCheckBy = isInCheckBy;
		this.freeMovements = new ArrayList<Integer>();
		for (int i = 0; i < freeMovements.size(); i++)
			this.freeMovements.add(freeMovements.get(i));
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public PieceModel getIsInCheckBy() {
		return isInCheckBy;
	}

	public void setIsInCheckBy(PieceModel isInCheckBy) {
		this.isInCheckBy = isInCheckBy;
	}

	public ArrayList<Integer> getFreeMovements() {
		return freeMovements;
	}

	public void setFreeMovements(ArrayList<Integer> freeMovements) {
		this.freeMovements = freeMovements;
	}
	
	
}
