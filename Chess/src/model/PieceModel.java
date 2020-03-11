package model;

import model.PieceLocationModel;
import model.PieceModel;
import model.PieceType;
import model.Player;

public class PieceModel {
	
	private PieceType type;
	private PieceLocationModel location;
	private Player player;
	
	public PieceModel(int location_x, int location_y, Player player, PieceType type) {
		this.type = type;
		this.location = new PieceLocationModel(location_x, location_y);
		this.player = player;
	}
	
	public PieceType getType() {
		return type;
	}
	
	public PieceLocationModel getLocation() {
		return location;
	}
	
	public Player getOwner() {
		return this.player;
	}
	
	public void setType(PieceType type) {
		this.type = type;
	}
	
	public void setOwner(Player player) {
		this.player = player;
	}
	
	public void setLocation(int location_x, int location_y) {
		if (location_x == -1)
			this.location.setY(location_y);
		else if (location_y == -1)
			this.location.setX(location_x);
		else {
			this.location.setX(location_x);
			this.location.setY(location_y);
		}
	}
	
	public void setDead() {
		this.type = PieceType.BLANK;
		this.player = Player.NEUTRAL;
	}

	public void setLocation(PieceLocationModel location) {
		this.location = location;
	}

	public void setPiece(PieceModel piece) {
		this.setType(piece.getType());
		this.setOwner(piece.getOwner());
		this.location.setX(piece.getLocation().getX());
		this.location.setY(piece.getLocation().getY());
	}
	
	public String toString() {
		return "Type: " + type.name() + " Location: " + location.getX() + " " + location.getY() + " Owner: " + player.name() + "\n";
	}
	
	public String rawDataToString() {
		return type.name() + " " + location.getX() + " " + location.getY() + " " + player.name();
	}
}
