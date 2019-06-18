package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static boolean hasConnection;
	
	public static boolean hasBeenRecreated;
	
	public static Connection getConnection() {
		Connection conn = null;
		
		verifyDatabaseExists();
		
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:database/mercado.db";
			
			conn = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			hasConnection = false;
			System.out.println("Classe de banco de dados n�o encontrada");
		} catch (SQLException sqle) {
			hasConnection = false;
			System.out.println("Erro ao se conectar ao banco mercado.db");
		}
		
		return conn;
	}
	
	private static void verifyDatabaseExists() {
		File directory = new File("database");
		
		if (directory.exists()) {
			File db = new File("database\\mercado.db");
			if (db.exists()) {
				hasConnection = true;
			} else {
				createDatabaseFile(db);
				hasBeenRecreated = true;
				verifyDatabaseExists();
			}
		} else {
			directory.mkdir();
			verifyDatabaseExists();
		}
	}
	
	private static void createDatabaseFile(File db) {
		try {
			db.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
