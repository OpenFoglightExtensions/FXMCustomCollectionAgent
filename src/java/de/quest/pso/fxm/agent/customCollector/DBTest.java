package de.quest.pso.fxm.agent.customCollector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connect = null;

		// This will load the MySQL driver, each DB has its own driver

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://10.10.8.130:3306/UserSession?"
							+ "user=foglight&password=foglight");
			System.out.println("Connection :"+connect.toString());
			
			// Statements allow to issue SQL queries to the database
			Statement statement = connect.createStatement();
			// Result set get the result of the SQL query
			ResultSet resultSet = statement
					.executeQuery("select s.ClientIP saugerip, MAX(u.Count) anzahlMax , " +
							              "MIN(u.Count) as anzahlMin, SUM(u.Count) as hits, " +
							              "count(*) as numberSessions,s.timestamp " +
							      "from Session s, UserSessionValueCount u  " +
							      "where (s.ResourceID = u.ResourceID  ) and " +
							      "s.timestamp = (select MAX(s.timestamp) from Session s ) " +
							      "group by saugerip,s.timestamp having anzahlMax > 1000 order by anzahlMax desc limit 20;");
			
			//				"select s.ClientIP saugerip, u.Count anzahl from Session s, UserSessionValueCount u where s.ResourceID = u.ResourceID and s.UserAgent not like '%bot%' and u.Count > 1000 order by u.Count desc limit 20;");

			System.out.println("Result :"+resultSet.toString());
			resultSet.beforeFirst();
			int i = 1;
			while (resultSet.next()) {
				System.out.println(i+" : "+resultSet.getString(1)+"   ,   "+resultSet.getString(2));
				i++;
			}

			connect.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			if (connect != null)
				try {
					connect.close();
				} catch (SQLException e) {
				
				}
		}

	}

}
