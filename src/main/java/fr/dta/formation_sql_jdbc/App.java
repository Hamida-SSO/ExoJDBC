package fr.dta.formation_sql_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/formationSQL";

		try {
			Connection connection = DriverManager.getConnection(url, "formation", "postgres");
			Statement state = connection.createStatement();

			// Create Table
			state.executeUpdate("DROP TABLE IF EXISTS buy");
			state.executeUpdate("DROP TABLE IF EXISTS client");
			state.executeUpdate("DROP TABLE IF EXISTS book");
			// Book
			state.executeUpdate("CREATE TABLE book(" + " id BIGSERIAL PRIMARY KEY NOT NULL," + " title VARCHAR(100),"
					+ " author varchar(100) NOT NULL)");
			// Client
			state.executeUpdate("CREATE TABLE client(" + " id BIGSERIAL PRIMARY KEY NOT NULL,"
					+ " lastname VARCHAR(100)," + " firstname VARCHAR(80)," + " gender varchar(8),"
					+ " id_book integer," + " CONSTRAINT fk_book FOREIGN KEY (id_book) REFERENCES book(id))" // book
																												// favorite
			);
			// Buy
			state.executeUpdate(
					"CREATE TABLE buy(" + " id_book integer" + " CONSTRAINT fk_id_book REFERENCES book(id) NOT NULL,"
							+ " id_client integer" + " CONSTRAINT fk_id_client REFERENCES client(id) NOT NULL)");

			state.close();

			// Book
			Book book1 = new Book("Roswell", "Quentin Delanghe");
			createBook(book1, connection);

			Book book2 = new Book("SmallVille", "Alfred & Miles");
			createBook(book2, connection);
			
			Book book3 = new Book("The Great Doctor", "Kim Jong Hak");
			createBook(book3, connection);

			// Client
			Client client1 = new Client("Maïssane", "SIDI", Gender.F);
			createClient(client1, connection);

			Client client2 = new Client("Lee", "MIN HO", Gender.M);
			createClient(client2, connection);

			Client client3 = new Client("Joshua", "CONAN", Gender.M);
			createClient(client3, connection);

			// Buy
			createBuy(book1.getId(), client1.getId(), connection);
			createBuy(book1.getId(), client2.getId(), connection);
			createBuy(book2.getId(), client2.getId(), connection);
			createBuy(book3.getId(), client2.getId(), connection);
			createBuy(book3.getId(), client1.getId(), connection);
			
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Insert Client
	public static void createClient(Client client, Connection conn) {

		try {
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO client(lastname, firstname, gender) VALUES(?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.setString(1, client.getFirstName());
			stmt.setString(2, client.getLastName());
			stmt.setString(3, client.getGender().name());
			stmt.executeUpdate();

			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			Long id = generatedKeys.getLong("id");

			client.setId(id);

			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Insert Book
	public static void createBook(Book book, Connection conn) {

		try {
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO book(title, author) VALUES(?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.executeUpdate();

			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			Long id = generatedKeys.getLong("id");

			book.setId(id);

			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Insert Buy
	public static void createBuy(Long id_book, Long id_client, Connection conn) {

		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO buy(id_book, id_client) VALUES(?,?)");

			stmt.setLong(1, id_book);
			stmt.setLong(2, id_client);
			stmt.executeUpdate();

			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
