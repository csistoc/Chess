package controller;

import model.PieceModel;
import model.Player;
import model.TableModel;

public class PieceController {
	
	public static boolean isMoveValid(TableModel table, int startX, int startY, int finishX, int finishY) {
		if (table.getPieces(startX, startY).getOwner() == table.getPieces(finishX, finishY).getOwner()) { // same owner
			LogFileController.writeToFile("same owner check failed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
			return false;
		}
		LogFileController.writeToFile("same owner check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		int size = table.getPieces().size();
		if (finishX >= size || finishY >= size || finishX < 0 || finishY < 0) { // out of table
			LogFileController.writeToFile("out of bounds check failed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
			return false;
		}
		LogFileController.writeToFile("out of bounds check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		return true;
	}
	
	private static boolean isPawnMoveValid(TableModel table, int startX, int startY, int finishX, int finishY) {
		if (!isMoveValid(table, startX, startY, finishX, finishY))
			return false;
		PieceModel start = table.getPieces(startX, startY);
		PieceModel finish = table.getPieces(finishX, finishY);
		Player owner = table.getPieces(startX, startY).getOwner();
		switch (owner) {
			case PLAYER1: {
				if (finish.getOwner() != Player.NEUTRAL)
					if (start.getOwner() != finish.getOwner())
						if (startX + 1 == finishX && startY - 1 == finishY) { // lower left attack
							LogFileController.writeToFile("lower left attack");
							return true;
						}
						else if (startX + 1 == finishX && startY + 1 == finishY) { // lower right attack
							LogFileController.writeToFile("lower right attack");
							return true;
						}
				if (startX + 1 == finishX && startY == finishY && finish.getOwner() == Player.NEUTRAL) { // front move
					LogFileController.writeToFile("lower front move");
					return true;
				}
				if (startX == 1 && startX + 2 == finishX && startY == finishY && finish.getOwner() == Player.NEUTRAL) { // start position front leap
					LogFileController.writeToFile("lower front leap move");
					return true;
				}
				return false;
			}
			case PLAYER2: {
				if (finish.getOwner() != Player.NEUTRAL)
					if (start.getOwner() != finish.getOwner())
						if (startX - 1 == finishX && startY - 1 == finishY) { // upper left attack
							LogFileController.writeToFile("upper left attack");
							return true;
						}
						else if (startX - 1 == finishX && startY + 1 == finishY) { // upper right attack
							LogFileController.writeToFile("upper right attack");
							return true;
						}
				if (startX - 1 == finishX && startY == finishY && finish.getOwner() == Player.NEUTRAL) { // front move
					LogFileController.writeToFile("upper front move");
					return true;
				}
				if (startX == (int)(Math.sqrt(table.getPieces().size()) - 2)
						&& startX - 2 == finishX && startY == finishY && finish.getOwner() == Player.NEUTRAL) { // start position front leap
					LogFileController.writeToFile("upper front leap move");
					return true;
				}
				return false;
			}
			default:
				return false;
		}
	}
	
	private static boolean isHorseMoveValid(TableModel table, int startX, int startY, int finishX, int finishY) {
		if (!isMoveValid(table, startX, startY, finishX, finishY))
			return false;
		if (startX - 1 == finishX && startY - 2 == finishY)
			return true;
		if (startX + 1 == finishX && startY - 2 == finishY)
			return true;
		if (startX - 1 == finishX && startY + 2 == finishY)
			return true;
		if (startX + 1 == finishX && startY + 2 == finishY)
			return true;
		if (startX - 2 == finishX && startY - 1 == finishY)
			return true;
		if (startX + 2 == finishX && startY - 1 == finishY)
			return true;
		if (startX - 2 == finishX && startY + 1 == finishY)
			return true;
		if (startX + 2 == finishX && startY + 1 == finishY)
			return true;
		return false;
	}
	
	private static boolean isBishopMoveValid(TableModel table, int startX, int startY, int finishX, int finishY) {
		if (!isMoveValid(table, startX, startY, finishX, finishY))
			return false;
		if (Math.abs(finishX - startX) != Math.abs(finishY - startY)) // not diagonal
			return false;
		LogFileController.writeToFile("General check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		if (finishX - startX == finishY - startY) // upper left diagonal direction
			for (int i = startX - 1, j = startY - 1; i > finishX && j > finishY; i--, j--) {
				if (!TableController.isBlank(table, i, j)) {
					LogFileController.writeToFile("upper left diagonal direction failed at position: (" + i + "," + j + ")");
					return false;
				}
			}
		LogFileController.writeToFile("upper left diagonal direction check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		if (finishX - startX == startY - finishY) // upper right diagonal direction
			for (int i = startX - 1, j = startY + 1; i > finishX && j < finishY; i--, j++) {
				if (!TableController.isBlank(table, i, j)) {
					LogFileController.writeToFile("upper right diagonal direction failed at position: (" + i + "," + j + ")");
					return false;
				}
			}
		LogFileController.writeToFile("upper right diagonal direction check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		if (finishX - startX == startY - finishY) // lower left diagonal direction
			for (int i = startX + 1, j = startY - 1; i < finishX && j > finishY; i++, j--) {
				if (!TableController.isBlank(table, i, j)) {
					LogFileController.writeToFile("lower left diagonal direction failed at position: (" + i + "," + j + ")");
					return false;
				}
			}
		LogFileController.writeToFile("lower left diagonal direction check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		if (finishX - startX == finishY - startY) // lower right diagonal direction
			for (int i = startX + 1, j = startY + 1; i < finishX && j < finishY; i++, j++) {
				if (!TableController.isBlank(table, i, j)) {
					LogFileController.writeToFile("lower right diagonal direction failed at position: (" + i + "," + j + ")");
					return false;
				}
			}
		LogFileController.writeToFile("lower right diagonal direction check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		if (table.getPieces(finishX, finishY).getOwner() == table.getPieces(startX, startY).getOwner())
			return false;
		LogFileController.writeToFile("attack move check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		return true;
	}
	
	private static boolean isRookMoveValid(TableModel table, int startX, int startY, int finishX, int finishY) {
		if (!isMoveValid(table, startX, startY, finishX, finishY))
			return false;
		if (startX != finishX && startY != finishY) // not on the same line or row
			return false;
		LogFileController.writeToFile("same line or row check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		int minX = 0, minY = 0, maxX = 0, maxY = 0;
		if (startX < finishX) { // choosing direction
			minX = startX + 1;
			maxX = finishX;
		}
		else if (startX > finishX) {
			minX = finishX + 1;
			maxX = startX;
		}
		if (startY < finishY) {
			minY = startY + 1;
			maxY = finishY;
		}
		else if (startY > finishY) {
			minY = finishY + 1;
			maxY = startY;
		}
		LogFileController.writeToFile("direction chosen: minX=" + minX + " maxX=" + maxX + " minY=" + minY + " maxY=" + maxY);
		if (startX == finishX)
			for (int j = minY; j < maxY; j++)
				if (!TableController.isBlank(table, startX, j)) {
					LogFileController.writeToFile("same row blank check failed at (" + startX + "," + j + ")");
					return false;
				}
		LogFileController.writeToFile("not on the same row / same row blank check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		if (startY == finishY)
			for (int i = minX; i < maxX; i++) {
				LogFileController.writeToFile("same line blank check failed at (" + i + "," + startY + ")");
				if (!TableController.isBlank(table, i, startY))
					return false;
			}
		LogFileController.writeToFile("not on the same line / same line blank check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		return true;
	}
	
	private static boolean isQueenMoveValid(TableModel table, int startX, int startY, int finishX, int finishY) {
		if (!isMoveValid(table, startX, startY, finishX, finishY))
			return false;
		if (PieceController.isBishopMoveValid(table, startX, startY, finishX, finishY)
			|| PieceController.isRookMoveValid(table, startX, startY, finishX, finishY))
			return true;
		return false;
	}
	
	private static boolean isKingMoveValid(TableModel table, int startX, int startY, int finishX, int finishY) {
		if (!isMoveValid(table, startX, startY, finishX, finishY))
			return false;
		LogFileController.writeToFile("general check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		if (finishX > startX + 1 || finishX < startX - 1 || finishY > startY + 1 || finishY < startY - 1)
			return false; // check near vicinity restraint
		LogFileController.writeToFile("near vicinity restraint passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		TableModel aux = new TableModel(table);
		TableController.makeMove(aux, startX, startY, finishX, finishY);
		if (TableController.simpleIsCheck(aux, finishX, finishY, aux.getPieces(startX, startY).getOwner()))
			return false;
		LogFileController.writeToFile("check passed at positions: (" + startX + "," + startY + ") (" + finishX + "," + finishY + ")");
		return true;
	}
	
	public static boolean isGeneralMoveValid(TableModel table, int startX, int startY, int finishX, int finishY) {
		switch (table.getPieces(startX, startY).getType()) {
			case PAWN:
				return isPawnMoveValid(table, startX, startY, finishX, finishY);
			case HORSE:
				return isHorseMoveValid(table, startX, startY, finishX, finishY);
			case BISHOP:
				return isBishopMoveValid(table, startX, startY, finishX, finishY);
			case ROOK:
				return isRookMoveValid(table, startX, startY, finishX, finishY);
			case QUEEN:
				return isQueenMoveValid(table, startX, startY, finishX, finishY);
			case KING:
				return isKingMoveValid(table, startX, startY, finishX, finishY);
			default:
				return false;
		}
	}
}
