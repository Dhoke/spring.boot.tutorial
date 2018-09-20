package spring.boot.tutorial.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.PathVariable;
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
	public BookBean getTheOnlyBook() {

		String query = "SELECT * FROM TEST_SCHEMA.BOOKS";
		BookBean book = new BookBean();

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try (Connection con = DriverManager.getConnection(_URL, _USER, _PW);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query)) {

			while (rs.next()) {
				book.setId(rs.getInt(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				System.out.printf("%d %s %s%n", rs.getInt(1), rs.getString(2), rs.getString(3));
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return book;
	}

	@RequestMapping(path = "/books/{bookId}", method = RequestMethod.GET)
	public BookBean getSpecificBook(@PathVariable int bookId) {

		BookBean book = new BookBean();
		String query = "SELECT * FROM TEST_SCHEMA.BOOKS WHERE ID=" + bookId;

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try (Connection con = DriverManager.getConnection(_URL, _USER, _PW);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query)) {

			rs.next();
			book.setId(rs.getInt(1));
			book.setTitle(rs.getString(2));
			book.setAuthor(rs.getString(3));

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return book;
	}

}
