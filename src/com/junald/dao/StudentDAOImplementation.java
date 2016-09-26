package com.junald.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;

import com.junald.model.Student;
import com.junald.util.DBUtil;

public class StudentDAOImplementation implements StudentDAO {
	
	private Connection conn;
	
    
    

	public StudentDAOImplementation() throws InvalidSyntaxException {
		conn = DBUtil.getConnection();
	}
	
	
	public void createTableStudent() {
		try {
	DatabaseMetaData dbm = conn.getMetaData();
	// check if "employee" table is there
	ResultSet tables = dbm.getTables(null, null, "student", null);
	
	if (tables.next()) {
	 System.out.println("EXITSTS");
	}
	else {
		System.out.println("creating table");
		String createTableSQL = "CREATE TABLE student (studentId int(11) NOT NULL AUTO_INCREMENT,firstName varchar(20) NOT NULL,lastName varchar(20) NOT NULL,course varchar(20) NOT NULL,year varchar(20) DEFAULT NULL,PRIMARY KEY (studentId))";
		PreparedStatement preparedStatement = conn.prepareStatement( createTableSQL );

		System.out.println(createTableSQL);

		// execute create SQL stetement
		preparedStatement.executeUpdate();
		preparedStatement.close();
	  
	}
		}
	catch (SQLException e) {
		e.printStackTrace();
	}
	
	}
	
	
	
	
	public void addStudent( Student student ) {
		try {
			String query = "insert into student (firstName, lastName, course, year) values (?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setString( 1, student.getFirstName() );
			preparedStatement.setString( 2, student.getLastName() );
			preparedStatement.setString( 3, student.getCourse() );
			preparedStatement.setInt( 4, student.getYear() );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteStudent( int studentId ) {
		try {
			String query = "delete from student where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, studentId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateStudent( Student student ) {
		try {
			String query = "update student set firstName=?, lastName=?, course=?, year=? where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setString( 1, student.getFirstName() );
			preparedStatement.setString( 2, student.getLastName() );
			preparedStatement.setString( 3, student.getCourse() );
			preparedStatement.setInt( 4, student.getYear() );
			preparedStatement.setInt(5, student.getStudentId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery( "select * from student" );
			while( resultSet.next() ) {
				Student student = new Student();
				student.setStudentId( resultSet.getInt( "studentId" ) );
				student.setFirstName( resultSet.getString( "firstName" ) );
				student.setLastName( resultSet.getString( "lastName" ) );
				student.setCourse( resultSet.getString( "course" ) );
				student.setYear( resultSet.getInt( "year" ) );
				students.add(student);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	public Student getStudentById(int studentId) {
		Student student = new Student();
		try {
			String query = "select * from student where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, studentId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				student.setStudentId( resultSet.getInt( "studentId" ) );
				student.setFirstName( resultSet.getString( "firstName" ) );
				student.setLastName( resultSet.getString( "LastName" ) );
				student.setCourse( resultSet.getString( "course" ) );
				student.setYear( resultSet.getInt( "year" ) );
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

}