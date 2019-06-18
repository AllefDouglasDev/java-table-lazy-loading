package database.seeds;

import controllers.UserController;
import models.User;

import java.util.UUID;

public class UsersTableSeeder implements Seeder {

	@Override
	public void run() {
		UserController cc = new UserController();

		for (int i = 0; i < 320; i++)
			cc.store(new User(i+1, randomString(), randomString(), "close"));
	}

	private String randomString() {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		return myRandom.substring(0,20);
	}

}
