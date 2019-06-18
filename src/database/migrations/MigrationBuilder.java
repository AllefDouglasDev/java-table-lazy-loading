package database.migrations;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;

public class MigrationBuilder {
	
	private static Connection conn;
	
	private static List<Migration> migrations;
	
	/** 
	 * Criando tabelas por ordem de chamada de m�todo 
	 */
	public static void migrate() {
		conn = DBConnection.getConnection();
		
		add(new Users());
		
		run();
	}
	
	private static void add(Migration m) {
		if (null == migrations)
			migrations = new ArrayList<>();
		
		migrations.add(m);
	}
	
	private static void run() {
		if (null == migrations)
			migrations = new ArrayList<>();
		
		Table t = new Table(conn);
		
		migrations.forEach(m -> m.create(t));
	}
	
}
