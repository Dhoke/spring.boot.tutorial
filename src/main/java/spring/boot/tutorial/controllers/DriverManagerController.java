package spring.boot.tutorial.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.tutorial.beans.BookBean;

@RestController
public class DriverManagerController {

	private static final String _URL = "jdbc:h2:tcp://localhost/~/test";
	private static final String _USER = "sa";
	private static final String _PW = "";

	@RequestMapping(path = "/books", method = RequestMethod.GET)
	public BookBean[] getAllBooks() {

		String query = "SELECT * FROM TEST_SCHEMA.BOOKS";
		List<BookBean> allBooksInDB = new ArrayList<>();

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try (Connection con = DriverManager.getConnection(_URL, _USER, _PW);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query)) {

			while (rs.next()) {
				
				BookBean book = new BookBean();
				book.setId(rs.getInt(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				allBooksInDB.add(book);
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return allBooksInDB.toArray(new BookBean[allBooksInDB.size()]);
	}

	@RequestMapping(path = "/books/{bookId}", method = RequestMethod.GET)
	public BookBean getSpecificBook(@PathVariable int bookId) {

		BookBean book = new BookBean();
		String query = "SELECT * FROM TEST_SCHEMA.BOOKS WHERE ID=?";

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try (Connection con = DriverManager.getConnection(_URL, _USER, _PW)) {

			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, bookId);
			
			ResultSet rs = st.executeQuery();
			
			rs.next();
			book.setId(rs.getInt(1));
			book.setTitle(rs.getString(2));
			book.setAuthor(rs.getString(3));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return book;
	}
	
	@RequestMapping(path = "/books", method = RequestMethod.POST, consumes="application/json")
	public void addBook(@RequestBody BookBean book) {
		
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		try (Connection con = DriverManager.getConnection(_URL, _USER, _PW)) {		
			
			String query = "INSERT INTO TEST_SCHEMA.BOOKS VALUES (?, ?, ?)";
			
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, book.getId());
			statement.setString(2, book.getTitle());
			statement.setString(3, book.getAuthor());
			
			statement.execute();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping(path="/books/{bookId}", method = RequestMethod.DELETE)
	public void deleteBook(@PathVariable int bookId) {
		
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		try (Connection con = DriverManager.getConnection(_URL, _USER, _PW)) {		
			
			String query = "DELETE FROM TEST_SCHEMA.BOOKS WHERE ID=?";
			
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, bookId);
			
			statement.execute();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping(path="/books", method=RequestMethod.PUT)
	public void updateBook(@RequestBody BookBean book) {
		
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		try (Connection con = DriverManager.getConnection(_URL, _USER, _PW)) {		
			
			String query = "UPDATE TEST_SCHEMA.BOOKS SET ID=?, AUTHOR=?, TITLE=? WHERE ID=?";
			
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1,  book.getId());
			statement.setString(2, book.getAuthor());
			statement.setString(3, book.getTitle());
			statement.setInt(4,  book.getId());
			
			statement.execute();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
