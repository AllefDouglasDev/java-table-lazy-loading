package dao;

import models.Paginate;
import models.User;

import java.util.ArrayList;

public class UserDAO {

    /**
     * Get all data by the query passed by param
     * Execute the query and return the length of the result
     */
    public int getUsersSize (String query) {
        return 200;
    }

    public Paginate<User> getUsers (int perPage, int page) {
        ArrayList<User> users = new ArrayList<>();
        /**
         * Query then will be executed to get the users
         */
        String query = "SELECT * FROM users";
        int usersSize = getUsersSize(query);
        int sizeToLoad = Paginate.calculateSizeToLoad(perPage, page, usersSize);
        if (sizeToLoad > 0) {
            /**
             * Use the query in limited way to get the data
             */
            query = query + " LIMIT ?, ?";
            /**
             * Execute the query to get all users passing the offset and perPage
             * ------ Calculate the offset ------
             * int offset = (page - 1) * perPage;
             *
             * use:
             * stmt.setInt(1, offset);
             * stmt.setInt(2, perPage);
             */
            for (int i = 0; i < sizeToLoad; i++) {
                users.add(new User(
                        generateId(),
                        "Pessoa " + generateId(),
                        "Meu phone " + generateId(),
                        generateId() > 5 ? "Open" : "Close"
                    )
                );
            }
        }

        return new Paginate<>(usersSize, page, perPage, users);
    }

    private int generateId() {
        return (int)(Math.random() * 10);
    }

    /**
     * Methods exemple to get data lazily
     *
     * public int getUsersSize (String query) {
     *      int size = 0;
     *      PreparedStatement stmt = null;
     * 		ResultSet rs;
     * 		try {
     *
     * 		    stmt = con.prepareStatement(query);
     * 		    rs = stmt.executeQuery();
     * 		    if (rs != null) {
     * 		        rs.last();
     * 		        size = rs.getRow();
     * 		    }
     * 		    stmt.close();
     *      } catch (SQLException e) {
     * 			e.printStackTrace();
     *      } finally {
     * 			try {
     * 		    	stmt.close();
     *          } catch (SQLException e) {
     *              e.printStackTrace();
     *          }
     *      }
     * 		return size;
     * }
     *
     * public Paginate<User> getUsers(int perPage, int page){
     * 		ArrayList<User> users = new ArrayList<>();
     * 	    String query = "SELECT * FROM users";
     * 	    int usersSize = getUsersSize(query);
     *
     * 		PreparedStatement stmt = null;
     * 		ResultSet rs;
     * 		try {
     * 	        query = query + " LIMIT ?, ?";
     * 	        int offset = (page - 1) * perPage;
     *
     * 		    stmt = con.prepareStatement(query);
     * 		    stmt.setInt(1, offset);
     * 		    stmt.setInt(2, perPage);
     * 		    rs = stmt.executeQuery();
     * 		    while (rs.next()) {
     * 			    users.add(new User(
     * 			            rs.getInt("id"),
     * 			            rs.getString("name"),
     * 			            rs.getString("phone"),
     * 			            rs.getString("status")
     * 			        )
     * 			     );
     *          }
     * 		    stmt.close();
     *      } catch (SQLException e) {
     * 			e.printStackTrace();
     *      } finally {
     * 			try {
     * 		    	stmt.close();
     *          } catch (SQLException e) {
     *              e.printStackTrace();
     *          }
     *      }
     * 		return new Paginate<>(usersSize, page, perPage, users);
     * 	}
     */
}
