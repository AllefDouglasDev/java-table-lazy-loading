package database.migrations;

public interface Migration {
	void create(Table table);
}
