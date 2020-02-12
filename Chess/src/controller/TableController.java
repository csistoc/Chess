package controller;

import java.util.ArrayList;

import javax.swing.JTextArea;

import model.CheckPiece;
import model.PieceModel;
import model.PieceType;
import model.Player;
import model.TableModel;
import model.CheckPiece;

public class TableController {
	
	private static void ownerSwap(TableModel table, int start_x, int start_y, int finish_x, int finish_y, boolean debug) {
		/*Player start = table.getPieces(start_x, start_y).getOwner();
		Player finish = table.getPieces(finish_x, finish_y).getOwner();
		table.getPieces(start_x, start_y).setOwner(finish);
		table.getPieces(finish_x, finish_y).setOwner(start);
		*/
		Player aux = table.getPieces(start_x, start_y).getOwner();
		table.getPieces(start_x, start_y).setOwner(table.getPieces(finish_x, finish_y).getOwner());
		table.getPieces(finish_x, finish_y).setOwner(aux);
	}
	
	private static void locationSwap(TableModel table, int start_x, int start_y, int finish_x, int finish_y, boolean debug) {
		if (debug) {
			System.out.println("[locationSwap] Start: " + table.getPieces(start_x, start_y));
			System.out.println("[locationSwap] Finish: " + table.getPieces(finish_x, finish_y));
		}
		int startIndex = table.getPieceIndex(start_x, start_y);
		int finishIndex = table.getPieceIndex(finish_x, finish_y);
		PieceModel aux = table.getPieces().get(startIndex);
		aux.setLocation(finish_x, finish_y);
		table.getPieces().get(finishIndex).setLocation(start_x, start_y);
		table.getPieces().set(startIndex, aux);
		if (debug) {
			System.out.println("[locationSwap] Start: " + table.getPieces(start_x, start_y));
			System.out.println("[locationSwap] Finish: " + table.getPieces(finish_x, finish_y));
		}
	}
	
	public static void reverseTable(TableModel table, int n, int m, boolean debug) {
		for (int i = 0; i < n / 2; i++)
			for (int j = 0; j < m; j++) {
				System.out.println("[reverseTable] Start: " + table.getPieces(i, j));
				System.out.println("[reverseTable] Finish: " + table.getPieces((n-1)-i, j) + "\n------------------");
				//if(table.getPieces(i, j).getType().equals(PieceType.BLANK) || table.getPieces((n-1)-i, j).getType().equals(PieceType.BLANK))
				//ownerSwap(table, i, j, (n - 1) - i, j);
				//System.out.println("i: " + i + " j: " + j + " (n-1)-i: " + ((n-1)-i) + " j: " + j);
				locationSwap(table, i, j, (n - 1) - i, j, debug);
				System.out.println("[reverseTable] Start: " + table.getPieces(i, j));
				System.out.println("[reverseTable] Finish: " + table.getPieces((n-1)-i, j) + "\n------------------\n------------------");
			}
	}
	
	public static boolean isBlank(TableModel table, int x, int y) {
		if (PieceType.BLANK.equals(table.getPieces(x, y).getType())) {
			return true;
		}
		return false;
	}
	
	public static void makeMove(TableModel table, int startX, int startY, int finishX, int finishY, boolean debug) {
		//System.out.println("Boolean: " + TableController.isBlank(table, finish_x, finish_y));
		if (!TableController.isBlank(table, finishX, finishY))
			table.getPieces(finishX, finishY).setDead();
		locationSwap(table, startX, startY, finishX, finishY, debug);
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
	
	private static ArrayList<Integer> calculatePossibleTrajectory(TableModel table, int startX, int startY, int finishX, int finishY) {
		ArrayList<Integer> trajectory = new ArrayList<Integer>();
		switch(table.getPieces(startX, startY).getType()) {
			case BISHOP:
				return trajectory;
			case ROOK:
				return trajectory;
			case QUEEN:
				return trajectory;
			default:
				return null;
		}
	}
	
	private static ArrayList<Integer> calculatePossibleMoves(TableModel table, int startX, int startY) {
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		for (int x = 0; x < Math.sqrt(table.getPieces().size()); x++)
			for (int y = 0; y < Math.sqrt(table.getPieces().size()); y++)
				if (PieceController.isGeneralMoveValid(table, startX, startY, x, y, false))
					if (table.getPieces(x, y).getType() == PieceType.BISHOP || table.getPieces(x, y).getType() == PieceType.ROOK ||
							table.getPieces(x, y).getType() == PieceType.QUEEN) {
								ArrayList<Integer> trajectory = calculatePossibleTrajectory(table, startX, startY, x, y);
								possibleMoves.add(x);
								possibleMoves.add(y);
								for (int i = 0; i < trajectory.size(); i++)
									possibleMoves.add(trajectory.get(i));
					}
					else {
						possibleMoves.add(x);
						possibleMoves.add(y);
					}
		return possibleMoves;
	}
	
	public static CheckPiece isInCheckBy(TableModel table, int kingX, int kingY, Player kingOwner, boolean debug) {
		ArrayList<PieceModel> pieces = table.getPieces();
		for (int i = 0; i < pieces.size(); i++) {
			PieceModel currPiece = pieces.get(i);
			if (currPiece.getOwner() != kingOwner && Player.NEUTRAL != currPiece.getOwner() && PieceType.KING != currPiece.getType())
				if (PieceController.isGeneralMoveValid(table, currPiece.getLocation().getX(), currPiece.getLocation().getY(), kingX, kingY, false)) {
					if (debug) 
						System.out.println("[isInCheckBy] found piece at " + currPiece);
					ArrayList<Integer> possibleMoves = calculatePossibleMoves(table, currPiece.getLocation().getX(), currPiece.getLocation().getY());
					CheckPiece checkPiece = new CheckPiece(true, currPiece, possibleMoves);
					return checkPiece;
				}
		}
		return new CheckPiece(false, null, null);
	}
	
	public static boolean simpleIsCheck(TableModel table, int kingX, int kingY, Player kingOwner, boolean debug) {
		ArrayList<PieceModel> pieces = table.getPieces();
		for (int i = 0; i < pieces.size(); i++) {
			PieceModel currPiece = pieces.get(i);
			if (currPiece.getOwner() != kingOwner && Player.NEUTRAL != currPiece.getOwner() && PieceType.KING != currPiece.getType())
				if (PieceController.isGeneralMoveValid(table, currPiece.getLocation().getX(), currPiece.getLocation().getY(), kingX, kingY, debug)) {
					if (debug) 
						System.out.println("[simpleIsCheck] found piece at " + currPiece);
					return true;
				}
		}
		return false;
	}
	
	public static boolean isKingInCheckMate(TableModel table, int playerTurn, boolean debug) {
		Player currPlayer = getCurrentPlayer(playerTurn);
		int[] kingCoord = getPlayerKingCoord(table, currPlayer);
		for (int i = kingCoord[0] - 1; i <= kingCoord[0] + 1; i++)
			for (int j = kingCoord[1] - 1; j <= kingCoord[1] + 1; j++)
				if (i >= 0 && i < Math.sqrt(table.getPieces().size()) && j >= 0 && j < Math.sqrt(table.getPieces().size()) && !(i == kingCoord[0] && j == kingCoord[1]))
					if (PieceController.isMoveValid(table, kingCoord[0], kingCoord[1], i, j, debug))
						if(!simpleIsCheck(table, i , j, currPlayer, debug))
							return false;
		for (int i = 0; i < table.getPieces().size(); i++)
			if (table.getPieces().get(i).getOwner() == currPlayer) {
				int startX = table.getPieces().get(i).getLocation().getX();
				int startY = table.getPieces().get(i).getLocation().getY();
				for (int x = 0; x < Math.sqrt(table.getPieces().size()); x++)
					for (int y = 0; y < Math.sqrt(table.getPieces().size()); y++)
						if (PieceController.isGeneralMoveValid(table, startX, startY, x, y, debug)) {
							TableModel aux = new TableModel(table);
							makeMove(aux, startX, startY, x, y, debug);
							if (!simpleIsCheck(aux, kingCoord[0], kingCoord[1], currPlayer, debug))
								return false;
						}
			}
		return true;
	}
	
	public static boolean isKingInCheckMate(TableModel table, int kingX, int kingY, Player kingOwner, boolean debug) {
		for (int i = kingX - 1; i <= kingX + 1; i++)
			for (int j = kingY - 1; j <= kingY + 1; j++)
				if (i >= 0 && i < Math.sqrt(table.getPieces().size()) && j >= 0 && j < Math.sqrt(table.getPieces().size()))
					if (i == kingX && j == kingY)
						j++;
					else if (PieceController.isMoveValid(table, kingX, kingY, i, j, debug))
							if(!simpleIsCheck(table, i , j, kingOwner, debug))
								return false;
		for (int i = 0; i < table.getPieces().size(); i++)
			if (table.getPieces().get(i).getOwner() == kingOwner) {
				int startX = table.getPieces().get(i).getLocation().getX();
				int startY = table.getPieces().get(i).getLocation().getY();
				for (int x = 0; x < Math.sqrt(table.getPieces().size()); x++)
					for (int y = 0; y < Math.sqrt(table.getPieces().size()); y++)
						if (PieceController.isGeneralMoveValid(table, startX, startY, x, y, debug)) {
							TableModel aux = new TableModel(table);
							makeMove(aux, startX, startY, x, y, debug);
							if (!simpleIsCheck(aux, kingX, kingY, kingOwner, debug))
								return false;
						}
			}
		return true;
	}
	
	public static boolean tick(TableModel table, int playerTurn, JTextArea textArea, CurrentTime time, boolean debug) {
		Player player = getCurrentPlayer(playerTurn);
		int[] kingCoord = getPlayerKingCoord(table, player);
		if (simpleIsCheck(table, kingCoord[0], kingCoord[1], player, debug)) 
			if(isKingInCheckMate(table, kingCoord[0], kingCoord[1], player, debug)) {
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
