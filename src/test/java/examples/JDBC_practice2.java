package examples;

import utilities.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC_practice2 {

    public static void main(String[] args) throws SQLException {
        String password = Configuration.getProperty("password");
        //jdbc:oracle:thin:YOUR_END_POINT:1521/ORCL
        String URL = "jdbc:oracle:thin:@mysqldatabase.cibtdbhxn1xs.us-east-2.rds.amazonaws.com:1521/ORCL";
        String username = "admin";

        DriverManager.getConnection(URL, username, password);
        Connection connection = DriverManager.getConnection(URL, username, password);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from employees");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        System.out.println(resultSetMetaData.getColumnCount()); // It gives how many columns have in table
        System.out.println("Employee table has (1 column) " + resultSetMetaData.getColumnName(1)); // It gives name of columns
        System.out.println("Employee table has (2 column) " + resultSetMetaData.getColumnName(2));
        System.out.println("Employee table has (3 column) " + resultSetMetaData.getColumnName(3));

        List<Map<String, Object>> table = new ArrayList<>();

        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(resultSetMetaData.getColumnName(i)));
                System.out.print(resultSet.getObject(resultSetMetaData.getColumnName(i)) + "     ");
            }
            System.out.println();
            table.add(row);
        }

    }

}
