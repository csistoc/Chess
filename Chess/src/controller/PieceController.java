package controller;

import model.PieceModel;
import model.PieceType;
import model.Player;
import model.TableModel;

public class PieceController {
	
	public static boolean isMoveValid(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		if (table.getPieces(startX, startY).getOwner() == table.getPieces(finishX, finishY).getOwner()) { // same owner
			if (debug)
				LogFileController.writeToFile("[isMoveValid] same owner check failed");
			return false;
		}
		if (debug)
			LogFileController.writeToFile("[isMoveValid] same owner check passed");
		int size = table.getPieces().size();
		if (finishX >= size || finishY >= size || finishX < 0 || finishY < 0) { // out of table
			if (debug)
				LogFileController.writeToFile("[isMoveValid] out of bounds check failed");
			return false;
		}
		if (debug)
			LogFileController.writeToFile("[isMoveValid] out of bounds check passed");
		return true;
	}
	
	private static boolean isPawnMoveValid(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		if (!isMoveValid(table, startX, startY, finishX, finishY, debug))
			return false;
		PieceModel start = table.getPieces(startX, startY);
		PieceModel finish = table.getPieces(finishX, finishY);
		Player owner = table.getPieces(startX, startY).getOwner();
		switch (owner) {
			case PLAYER1: {
				if (finish.getOwner() != Player.NEUTRAL)
					if (start.getOwner() != finish.getOwner())
						if (startX + 1 == finishX && startY - 1 == finishY) { // lower left attack
							if (debug)
								LogFileController.writeToFile("[isPawnMoveValid] lower left attack");
							return true;
						}
						else if (startX + 1 == finishX && startY + 1 == finishY) { // lower right attack
							if (debug)
								LogFileController.writeToFile("[isPawnMoveValid] lower right attack");
							return true;
						}
				if (startX + 1 == finishX && startY == finishY && finish.getOwner() == Player.NEUTRAL) { // front move
					if (debug)
						LogFileController.writeToFile("[isPawnMoveValid] lower front move");
					return true;
				}
				return false;
			}
			case PLAYER2: {
				if (finish.getOwner() != Player.NEUTRAL)
					if (start.getOwner() != finish.getOwner())
						if (startX - 1 == finishX && startY - 1 == finishY) { // upper left attack
							if (debug)
								LogFileController.writeToFile("[isPawnMoveValid] upper left attack");
							return true;
						}
						else if (startX - 1 == finishX && startY + 1 == finishY) { // upper right attack
							if (debug) 
								LogFileController.writeToFile("[isPawnMoveValid] upper right attack");
							return true;
						}
				if (startX - 1 == finishX && startY == finishY && finish.getOwner() == Player.NEUTRAL) { // front move
					if (debug)
						LogFileController.writeToFile("[isPawnMoveValid] upper front move");
					return true;
				}
				return false;
			}
			default:
				return false;
		}
	}
	
	private static boolean isHorseMoveValid(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		if (!isMoveValid(table, startX, startY, finishX, finishY, debug))
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
	
	private static boolean isBishopMoveValid(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		if (!isMoveValid(table, startX, startY, finishX, finishY, debug))
			return false;
		if (Math.abs(finishX - startX) != Math.abs(finishY - startY)) // not diagonal
			return false;
		if (debug)
			LogFileController.writeToFile("[isBishopMoveValid] General check passed");
		if (finishX - startX == finishY - startY) // upper left diagonal direction
			for (int i = startX - 1, j = startY - 1; i > finishX && j > finishY; i--, j--) {
				if (!TableController.isBlank(table, i, j)) {
					if (debug)
						LogFileController.writeToFile("[isBishopMoveValid] upper left diagonal direction failed at postion: " + i + " " + j);
					return false;
				}
			}
		if (debug)
			LogFileController.writeToFile("[isBishopMoveValid] upper left diagonal direction check passed");
		if (finishX - startX == startY - finishY) // upper right diagonal direction
			for (int i = startX - 1, j = startY + 1; i > finishX && j < finishY; i--, j++) {
				if (!TableController.isBlank(table, i, j)) {
					if (debug)
						LogFileController.writeToFile("[isBishopMoveValid] upper right diagonal direction failed at postion: " + i + " " + j);
					return false;
				}
			}
		if (debug)
			LogFileController.writeToFile("[isBishopMoveValid] upper right diagonal direction check passed");
		if (finishX - startX == startY - finishY) // lower left diagonal direction
			for (int i = startX + 1, j = startY - 1; i < finishX && j > finishY; i++, j--) {
				if (!TableController.isBlank(table, i, j)) {
					if (debug)
						LogFileController.writeToFile("[isBishopMoveValid] lower left diagonal direction failed at postion: " + i + " " + j);
					return false;
				}
			}
		if (debug)
			LogFileController.writeToFile("[isBishopMoveValid] lower left diagonal direction check passed");
		if (finishX - startX == finishY - startY) // lower right diagonal direction
			for (int i = startX + 1, j = startY + 1; i < finishX && j < finishY; i++, j++) {
				if (!TableController.isBlank(table, i, j)) {
					if (debug)
						LogFileController.writeToFile("[isBishopMoveValid] lower right diagonal direction failed at postion: " + i + " " + j);
					return false;
				}
			}
		if (debug)
			LogFileController.writeToFile("[isBishopMoveValid] lower right diagonal direction check passed");
		if (table.getPieces(finishX, finishY).getOwner() == table.getPieces(startX, startY).getOwner())
			return false;
		if (debug)
			LogFileController.writeToFile("[isBishopMoveValid] attack move check passed");
		return true;
	}
	
	private static boolean isRookMoveValid(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		if (!isMoveValid(table, startX, startY, finishX, finishY, debug))
			return false;
		if (startX != finishX && startY != finishY) // not on the same line or row
			return false;
		if (debug) 
			LogFileController.writeToFile("[isRookMoveValid] Same line or low check passed");
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
		if (debug)
			LogFileController.writeToFile("[isRookMoveValid] Direction chosen: minX=" + minX + " maxX=" + maxX + " minY=" + minY + " maxY=" + maxY);
		if (startX == finishX)
			for (int j = minY; j < maxY; j++)
				if (!TableController.isBlank(table, startX, j)) {
					if (debug) 
						LogFileController.writeToFile("[isRookMoveValid] same row blank check failed at " + startX + " " + j);
					return false;
				}
		if (debug) 
			LogFileController.writeToFile("[isRookMoveValid] Not on the same row / same row blank check passed");
		if (startY == finishY)
			for (int i = minX; i < maxX; i++) {
				if (debug) 
					LogFileController.writeToFile("[isRookMoveValid] same line blank check failed at " + i + " " + startY);
				if (!TableController.isBlank(table, i, startY))
					return false;
			}
		if (debug) 
			LogFileController.writeToFile("[isRookMoveValid] Not on the same line / same line blank check passed");
		return true;
	}
	
	private static boolean isQueenMoveValid(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		if (!isMoveValid(table, startX, startY, finishX, finishY, debug))
			return false;
		if (debug) {
			LogFileController.writeToFile("[isQueenMoveValid] isBishopModeValid: " + PieceController.isBishopMoveValid(table, startX, startY, finishX, finishY, false));
			LogFileController.writeToFile("[isQueenMoveValid] isRookMoveValid: " + PieceController.isRookMoveValid(table, startX, startY, finishX, finishY, false));
		}
		if (PieceController.isBishopMoveValid(table, startX, startY, finishX, finishY, debug)
			|| PieceController.isRookMoveValid(table, startX, startY, finishX, finishY, debug))
			return true;
		return false;
	}
	
	private static boolean isKingMoveValid(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		if (!isMoveValid(table, startX, startY, finishX, finishY, debug))
			return false;
		if (debug) 
			LogFileController.writeToFile("[isKingMoveValid] general check passed");
		if (finishX > startX + 1 || finishX < startX - 1 || finishY > startY + 1 || finishY < startY - 1)
			return false; // check near vicinity restraint
		if (debug)
			LogFileController.writeToFile("[isKingMoveValid] near vicinity restraint passed");
		TableModel aux = new TableModel(table);
		TableController.makeMove(aux, startX, startY, finishX, finishY, debug);
		if (TableController.simpleIsCheck(aux, finishX, finishY, aux.getPieces(startX, startY).getOwner(), debug))
			return false;
		if (debug)
			LogFileController.writeToFile("[isKingMoveValid] check passed");
		return true;
	}
	
	public static boolean isGeneralMoveValid(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		switch (table.getPieces(startX, startY).getType()) {
			case PAWN:
				return isPawnMoveValid(table, startX, startY, finishX, finishY, debug);
			case HORSE:
				return isHorseMoveValid(table, startX, startY, finishX, finishY, debug);
			case BISHOP:
				return isBishopMoveValid(table, startX, startY, finishX, finishY, debug);
			case ROOK:
				return isRookMoveValid(table, startX, startY, finishX, finishY, debug);
			case QUEEN:
				return isQueenMoveValid(table, startX, startY, finishX, finishY, debug);
			case KING:
				return isKingMoveValid(table, startX, startY, finishX, finishY, debug);
			default:
				return false;
		}
	}
}
