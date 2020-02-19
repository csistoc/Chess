package controller;

import java.util.ArrayList;

import javax.swing.JTextArea;

import model.PieceModel;
import model.PieceType;
import model.Player;
import model.TableModel;

public class TableController {
	
	private static void locationSwap(TableModel table, int start_x, int start_y, int finish_x, int finish_y) {
		LogFileController.writeToFile(table.getPieces(start_x, start_y).toString());
		LogFileController.writeToFile(table.getPieces(finish_x, finish_y).toString());
		int startIndex = table.getPieceIndex(start_x, start_y);
		int finishIndex = table.getPieceIndex(finish_x, finish_y);
		PieceModel aux = table.getPieces().get(startIndex);
		aux.setLocation(finish_x, finish_y);
		table.getPieces().get(finishIndex).setLocation(start_x, start_y);
		table.getPieces().set(startIndex, aux);
		LogFileController.writeToFile(table.getPieces(start_x, start_y).toString());
		LogFileController.writeToFile(table.getPieces(finish_x, finish_y).toString());
	}
	
	public static boolean isBlank(TableModel table, int x, int y) {
		if (PieceType.BLANK.equals(table.getPieces(x, y).getType())) {
			return true;
		}
		return false;
	}
	
	public static void makeMove(TableModel table, int startX, int startY, int finishX, int finishY) {
		if (!TableController.isBlank(table, finishX, finishY))
			table.getPieces(finishX, finishY).setDead();
		locationSwap(table, startX, startY, finishX, finishY);
	}
	
	private static int[] getPlayerKingCoord(TableModel table, Player player) {
		int[] output = new int[2];
		for (int i = 0; i < table.getPieces().size(); i++) {
			PieceModel aux = table.getPieces().get(i);
			if (aux.getOwner() == player)
				if (aux.getType() == PieceType.KING) {
					output[0] = aux.getLocation().getX();
					output[1] = aux.getLocation().getY();
					return output;
				}
		}
		return output;
	}
	
	private static Player getCurrentPlayer(int playerTurn) {
		if (playerTurn % 2 == 0)
			return Player.PLAYER1;
		else return Player.PLAYER2;
	}
	
	public static boolean simpleIsCheck(TableModel table, int kingX, int kingY, Player kingOwner) {
		ArrayList<PieceModel> pieces = table.getPieces();
		for (int i = 0; i < pieces.size(); i++) {
			PieceModel currPiece = pieces.get(i);
			if (currPiece.getOwner() != kingOwner && Player.NEUTRAL != currPiece.getOwner() && PieceType.KING != currPiece.getType())
				if (PieceController.isGeneralMoveValid(table, currPiece.getLocation().getX(), currPiece.getLocation().getY(), kingX, kingY)) {
					LogFileController.writeToFile("found piece at " + currPiece);
					return true;
				}
		}
		return false;
	}
	
	public static boolean isKingInCheckMate(TableModel table, int playerTurn) {
		Player currPlayer = getCurrentPlayer(playerTurn);
		int[] kingCoord = getPlayerKingCoord(table, currPlayer);
		for (int i = kingCoord[0] - 1; i <= kingCoord[0] + 1; i++)
			for (int j = kingCoord[1] - 1; j <= kingCoord[1] + 1; j++)
				if (i >= 0 && i < Math.sqrt(table.getPieces().size()) && j >= 0 && j < Math.sqrt(table.getPieces().size()) && !(i == kingCoord[0] && j == kingCoord[1]))
					if (PieceController.isMoveValid(table, kingCoord[0], kingCoord[1], i, j))
						if(!simpleIsCheck(table, i , j, currPlayer))
							return false;
		for (int i = 0; i < table.getPieces().size(); i++)
			if (table.getPieces().get(i).getOwner() == currPlayer) {
				int startX = table.getPieces().get(i).getLocation().getX();
				int startY = table.getPieces().get(i).getLocation().getY();
				for (int x = 0; x < Math.sqrt(table.getPieces().size()); x++)
					for (int y = 0; y < Math.sqrt(table.getPieces().size()); y++)
						if (PieceController.isGeneralMoveValid(table, startX, startY, x, y)) {
							TableModel aux = new TableModel(table);
							makeMove(aux, startX, startY, x, y);
							if (!simpleIsCheck(aux, kingCoord[0], kingCoord[1], currPlayer))
								return false;
						}
			}
		return true;
	}
	
	public static boolean isKingInCheckMate(TableModel table, int kingX, int kingY, Player kingOwner) {
		for (int i = kingX - 1; i <= kingX + 1; i++)
			for (int j = kingY - 1; j <= kingY + 1; j++)
				if (i >= 0 && i < Math.sqrt(table.getPieces().size()) && j >= 0 && j < Math.sqrt(table.getPieces().size()))
					if (i == kingX && j == kingY)
						j++;
					else if (PieceController.isMoveValid(table, kingX, kingY, i, j))
							if(!simpleIsCheck(table, i , j, kingOwner))
								return false;
		for (int i = 0; i < table.getPieces().size(); i++)
			if (table.getPieces().get(i).getOwner() == kingOwner) {
				int startX = table.getPieces().get(i).getLocation().getX();
				int startY = table.getPieces().get(i).getLocation().getY();
				for (int x = 0; x < Math.sqrt(table.getPieces().size()); x++)
					for (int y = 0; y < Math.sqrt(table.getPieces().size()); y++)
						if (PieceController.isGeneralMoveValid(table, startX, startY, x, y)) {
							TableModel aux = new TableModel(table);
							makeMove(aux, startX, startY, x, y);
							if (!simpleIsCheck(aux, kingX, kingY, kingOwner))
								return false;
						}
			}
		return true;
	}
	
	public static boolean tick(TableModel table, int playerTurn, JTextArea textArea, CurrentTime time) {
		Player player = getCurrentPlayer(playerTurn);
		int[] kingCoord = getPlayerKingCoord(table, player);
		if (simpleIsCheck(table, kingCoord[0], kingCoord[1], player)) 
			if(isKingInCheckMate(table, kingCoord[0], kingCoord[1], player)) {
				textArea.append("[" + time.getCurrentTime() + "] Check mate. Player " + player.name() + " won\n");
				return false;
			}
			else {
				textArea.append("[" + time.getCurrentTime() + "] Player " + player.name() + " is in check. Make a move to prevent it\n");
				return false;
			}
		return true;
	}
}
