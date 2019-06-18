package database.migrations;

public class Users implements Migration {

	@Override
	public void create(Table table) {
		table.create("CREATE TABLE IF NOT EXISTS users ("
				+ " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ " name VARCHAR(70) NOT NULL,"
				+ " phone VARCHAR(70) NOT NULL,"
				+ " status VARCHAR(70) NOT NULL,"
				+ " deleted INTEGER DEFAULT 0,"
				+ "	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
				+ " updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
				+ ")", "users");
	}

}
