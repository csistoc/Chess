package controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class DBController {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost:3306/HSC";
	private static final String USER = "hsc_admin";
	private static final String PASS = "HSCAdmin";
	private static final String databaseName = "HSC";
	private static final String playerTableName = "Players";
	private static final String scoreTableName = "Scores";
	private static final String commonFieldID = "playerID";
	private static final String playerFieldID = "playerID";
	private static final String playerFieldName = "name";
	private static final String scoreFieldID = "scoreID";
	private static final String scoreFieldValue = "value";
	private static final int playerDefaultPop = 10, scoreDefaultPop = 10;
	private static final int maximumDefaultScore = 100;
	private static final String[] playerDefaultNames = {"Joe", "Anna", "Michael", "John", "Andrew", "Diana", "Jessica", "Emma", "Olivia", "Anonymous"};
	private static Connection conn = null;
	private static Statement stmt = null;
	
	public static void init() {
		try {
			Class.forName(JDBC_DRIVER);
			
			LogFileController.writeToFile("connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
			stmt.executeUpdate(createDatabaseSql);
			LogFileController.writeToFile("created/found database");
			
			String useDatabaseSql = "USE " + databaseName;
			stmt.executeUpdate(useDatabaseSql);
			
			if (!checkTableExists(conn, playerTableName)) {
				createPlayersTable(stmt, playerTableName, playerFieldID, playerFieldName);
				LogFileController.writeToFile("created " + playerTableName + " table");
			}
			else LogFileController.writeToFile("found " + playerTableName + " table");
			
			if (!checkTableExists(conn, scoreTableName)) {
				createScoresTable(stmt, scoreTableName, scoreFieldID, scoreFieldValue, playerFieldID);
				LogFileController.writeToFile("created " + scoreTableName + " table");
			}
			else LogFileController.writeToFile("found " + scoreTableName + " table");
			
			if (isTableEmpty(stmt, playerTableName)) {
				LogFileController.writeToFile("empty table found");
				populatePlayersTable(stmt, playerDefaultPop, playerTableName, playerDefaultNames);
				LogFileController.writeToFile("table " + playerTableName + " populated succesfuly");
				
				populateScoresTable(stmt, playerDefaultPop, scoreDefaultPop, scoreTableName, maximumDefaultScore);
				LogFileController.writeToFile("table " + scoreTableName + " populated succesfuly");
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void createPlayersTable(Statement stmt, String playerTableName, String playerFieldID, String playerFieldName) throws SQLException {
		String createPlayersTableSql = "CREATE TABLE " + playerTableName + " " +
				"(" + playerFieldID + " INT NOT NULL, " +
				playerFieldName + " VARCHAR (255), " +
				" CONSTRAINT Pk_player PRIMARY KEY (" + playerFieldID + "))";
		stmt.executeUpdate(createPlayersTableSql);
	}
	
	private static void createScoresTable(Statement stmt, String scoresTableName, String scoreFieldID, String scoreFieldValue, String playerFieldID) throws SQLException {
		String createScoreTableSql = "CREATE TABLE " + scoresTableName + " " +
				"(" + scoreFieldID + " INT NOT NULL, " +
				scoreFieldValue + " INT NOT NULL, " +
				playerFieldID + " INT, " +
				" CONSTRAINT Pk_score PRIMARY KEY (" + scoreFieldID + "), " + 
				" CONSTRAINT Fk_score FOREIGN KEY (" + playerFieldID + ") " +
				" REFERENCES Players(" + playerFieldID + "))";
		stmt.executeUpdate(createScoreTableSql);
	}
	
	private static boolean isTableEmpty(Statement stmt, String tableName) throws SQLException {
		boolean isEmpty = false;
		String checkPlayersIsEmptySql = "SELECT * FROM  " + tableName;
		ResultSet rs = stmt.executeQuery(checkPlayersIsEmptySql);
		if (!rs.next())
			isEmpty = true;
		return isEmpty;
	}
	
	private static boolean checkTableExists(Connection conn, String tableName) throws SQLException {
		boolean tableExists = false;
		try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
	        while (rs.next()) { 
	            String tempTableName = rs.getString("TABLE_NAME");
	            if (tempTableName != null && tempTableName.equals(tableName.toLowerCase())) {
	                tableExists = true;
	                break;
	            }
	        }
	    }
		return tableExists;
	}
	
	private static void populatePlayersTable(Statement stmt, int playerPop, String playerTableName, String[] playerDefaultNames) throws SQLException {
		String insertIntoPlayersSql = "INSERT INTO " + playerTableName + " VALUES (?, ?)";
		int id = 100;
		
		PreparedStatement playerPstmt = stmt.getConnection().prepareStatement(insertIntoPlayersSql);
		for (int i = 0; i < playerPop; i++) {
			playerPstmt.setInt(1, id + i);
			playerPstmt.setString(2, playerDefaultNames[i]);
			playerPstmt.executeUpdate();
		}
		playerPstmt.close();
	}
	
	private static void populateScoresTable(Statement stmt, int playerPop, int scorePop, String scoreTableName, int maximumDefaultScore) throws SQLException {
		String deleteDataFromTable = "DELETE FROM " + scoreTableName;
		stmt.executeUpdate(deleteDataFromTable);
		
		Random random = new Random();
		int id = 100;
		String insertIntoScoreSql = "INSERT INTO " + scoreTableName + " VALUES (?, ?, ?)";
		PreparedStatement scorePstmt = stmt.getConnection().prepareStatement(insertIntoScoreSql);
		for (int i = 0; i < scorePop; i++) {
			int randScore = random.nextInt(maximumDefaultScore);
			int randPlayer = random.nextInt(playerPop);
			scorePstmt.setInt(1, id + i);
			scorePstmt.setInt(2, randScore);
			scorePstmt.setInt(3, id + randPlayer);
			scorePstmt.executeUpdate();
		}
		scorePstmt.close();
	}
	
	private static int getLatestIDFromTable(Statement stmt, String tableName, String id) throws SQLException {
		String getIDSql = "SELECT MAX(" + id + ") FROM " + tableName;
		ResultSet rs = stmt.executeQuery(getIDSql);
		rs.next();
		return rs.getInt("MAX(" + id + ")");
	}
	
	private static int getSpecificIDFromTable(Statement stmt, String tableName, String searchingField, String valueSearched, String id) throws SQLException {
		String getIDSql = "SELECT * FROM " + tableName + " WHERE " + searchingField + " = ?";
		PreparedStatement pstmt = stmt.getConnection().prepareStatement(getIDSql);
		pstmt.setString(1, valueSearched);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt(id);
	}
	
	public static void insertIntoPlayersTable(String name) {
		try {
			int lastID = getLatestIDFromTable(stmt, playerTableName, playerFieldID);
			String insertIntoTableSql = "INSERT INTO " + playerTableName + " VALUES (?, ?)";
			PreparedStatement pstmt = stmt.getConnection().prepareStatement(insertIntoTableSql);
			pstmt.setInt(1, lastID + 1);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertIntoScoresTable(String playerName, int value) {
		try {
			int lastScoresID = getLatestIDFromTable(stmt, scoreTableName, scoreFieldID);
			int playerID = getSpecificIDFromTable(stmt, playerTableName, playerFieldName, playerName, playerFieldID);
			String insertIntoTableSql = "INSERT INTO " + scoreTableName + " VALUES (?, ?, ?)";
			PreparedStatement pstmt = stmt.getConnection().prepareStatement(insertIntoTableSql);
			pstmt.setInt(1, lastScoresID + 1);
			pstmt.setInt(2, value);
			pstmt.setInt(3, playerID);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertData(String playerName, int value) {
		insertIntoPlayersTable(playerName);
		insertIntoScoresTable(playerName, value);
	}
	
	public static void close() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String show(String firstTableName, String secondTableName, String commonID) {
		String output = new String();
		try {
			String selectJoinSql = "SELECT * FROM " + firstTableName + " x JOIN " + secondTableName + " y ON x." + commonID + " = y." + commonID + " ORDER BY value";
			ResultSet rs = stmt.executeQuery(selectJoinSql);
			while (rs.next())
				output += TextFormatController.leaderboardFormat(rs.getString("name"), rs.getInt("value"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static String show() {
		return show(playerTableName, scoreTableName, commonFieldID);
	}
	
	public static void showTableType() {
		try {
			ResultSet rsColumns = null;
		    DatabaseMetaData meta;
			meta = conn.getMetaData();
			rsColumns = meta.getColumns(null, null, playerTableName, null);
		    while (rsColumns.next()) {
		      System.out.println(rsColumns.getString("TYPE_NAME"));
		      System.out.println(rsColumns.getString("COLUMN_NAME"));
		    }
		    System.out.println("--------------");
		    rsColumns = meta.getColumns(null, null, scoreTableName, null);
		    while (rsColumns.next()) {
		      System.out.println(rsColumns.getString("TYPE_NAME"));
		      System.out.println(rsColumns.getString("COLUMN_NAME"));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

