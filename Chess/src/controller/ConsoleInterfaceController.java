package controller;

import model.PieceType;
import model.Player;
import view.ConsoleInterface;

public class ConsoleInterfaceController {
	
	public static String pieceTypeToString(PieceType piece, Player player) {
		switch (player) {
			case PLAYER1:
				switch (piece) {
					case PAWN:
						return "p";
					case HORSE:
						return "h";
					case BISHOP:
						return "b";
					case ROOK:
						return "r";
					case QUEEN:
						return "q";
					case KING:
						return "k";
					default:
						return "Unexpected error\n";
				}
			case PLAYER2:
				switch (piece) {
					case PAWN:
						return "P";
					case HORSE:
						return "H";
					case BISHOP:
						return "B";
					case ROOK:
						return "R";
					case QUEEN:
						return "Q";
					case KING:
						return "K";
					default:
						return "Unexpected error\n";
				}
			default:
				return "-";
		}
	}

	
	public static void showCurrentStage(ConsoleInterface I) {
		System.out.println("    A B C D E F G H");
		System.out.println("   ________________");
		for(int i = 0; i < I.getTableSize(); i++) {
			System.out.print(i);
			System.out.print(" | ");
			for(int j = 0; j < I.getTableSize(); j++) {
				System.out.print(I.getTable(i, j));
				System.out.print(' ');
			}
			System.out.print('\n');
		}
	}
	
	private static void tableSwap(ConsoleInterface I, int i, int j, int x, int y) {
		char aux = I.getTable(i, j);
		I.setTable(I.getTable(x, y), i, j);
		I.setTable(aux, x, y);
	}
	
	public static void reverseTable(ConsoleInterface I) {
		for (int i = 0; i < I.getTableSize() / 2; i++)
			for (int j = 0; j < I.getTableSize(); j++)
				tableSwap(I, i, j, (I.getTableSize() - 1) - i, j);
	}
	
	public static void tick(ConsoleInterface I) {
		reverseTable(I);
		showCurrentStage(I);
	}

	
	
}
