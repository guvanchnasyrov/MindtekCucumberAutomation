package examples;

import utilities.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JDBC_practice {

    public static void main(String[] args) throws SQLException, InterruptedException {
        String password = Configuration.getProperty("password");
        //jdbc:oracle:thin:YOUR_END_POINT:1521/ORCL
        String URL = "jdbc:oracle:thin:@mysqldatabase.cibtdbhxn1xs.us-east-2.rds.amazonaws.com:1521/ORCL";
        String username = "admin";

        DriverManager.getConnection(URL, username, password);
        Connection connection = DriverManager.getConnection(URL, username, password);

        Statement statement = connection.createStatement();
        //ResultSet resultSet = statement.executeQuery("select * from employees");
        List<ResultSet> resultSetList = new ArrayList<>();
        boolean b = Collections.addAll(resultSetList,
                statement.executeQuery("select * from employees where first_name='Steven' and last_name='King'"),
                statement.executeQuery("select employee_id, first_name, last_name, manager_id, hire_date from employees"),
                statement.executeQuery("select country_id, country_name, region_id from countries"),
                statement.executeQuery("select department_id, department_name, location_id from departments"),
                statement.executeQuery("select start_date, end_date, job_id from job_history"),
                statement.executeQuery("select min(min_salary) as min from jobs"));
        System.out.println(b);
//        ResultSet resultSet = statement.executeQuery(
//                "select * from employees where first_name='Steven' and last_name='King'");
        //resultSet.next(); // first one we have to write this line for get same index java with sql

        while (resultSetList.get(0).next()) {
            System.out.println(resultSetList.get(0).getString("first_name"));
            System.out.println(resultSetList.get(0).getString("last_name"));
//            WebDriver driver = Driver.getDriver();
//            driver.get("https://www.google.com/");
//            driver.findElement(By.name("q")).sendKeys(resultSet.getString("first_name") + " " +
//                    resultSet.getString("last_name") + Keys.ENTER);
//
//            Thread.sleep(5000);
//            driver.quit();
        }

//        Print employee_id, first_name, last_name, manager_id and hire_date columns from employees
//        ResultSet resultSet1 = statement.executeQuery(
//                "select employee_id, first_name, last_name, manager_id, hire_date from employees");
//        System.out.println("\nEmployee ID - First Name - Last Name - Manager ID - Hire Date");
//        while(resultSetList.get(1).next()) {
//            System.out.print(resultSetList.get(1).getInt("employee_id") + " - " +
//                    resultSetList.get(1).getString("first_name") + " - " +
//                    resultSetList.get(1).getString("last_name") + " - " +
//                    resultSetList.get(1).getInt("manager_id") + " - " +
//                    resultSetList.get(1).getDate("hire_date") + "\n");
//        }

//        Print country id, country name, and region_id columns from countries

//        ResultSet resultSet2 = statement.executeQuery(
//                "select country_id, country_name, region_id from countries");
//        System.out.println("\nCountry ID - Country Name - Region ID");
//        while(resultSetList.get(2).next()) {
//            System.out.print(resultSetList.get(2).getString("country_id") + " - " +
//                    resultSetList.get(2).getString("country_name") + " - " +
//                    resultSetList.get(2).getInt("region_id") + "\n");
//        }

//        Print department_id, department_name, and location_id columns from job_history

//        ResultSet resultSet3 = statement.executeQuery(
//                "select department_id, department_name, location_id from departments");
//        System.out.println("\nCountry ID - Country Name - Region ID");
//        while(resultSet3.next()) {
//            System.out.print(resultSet3.getInt("department_id") + " - " +
//                    resultSet3.getString("department_name") + " - " +
//                    resultSet3.getInt("location_id") + "\n");
//        }

//        Print start_date, end_date, and job_id columns from job_history.

        ResultSet resultSet4 = statement.executeQuery(
                "select start_date, end_date, job_id from job_history");
        System.out.println("\nStart Date - End Date - Job ID");
        while(resultSet4.next()) {
            System.out.println(resultSet4.getDate("start_date") + " - " +
                    resultSet4.getDate("end_date") + " - " +
                    resultSet4.getString("job_id"));
        }

//        Ask google how to live on the lowest min_salary from jobs table? Min_salary should be dynamic from database

//        ResultSet resultSet5 = statement.executeQuery(
//                "select min(min_salary) as min from jobs");
//        System.out.println("Asking from google...");
//        while (resultSet5.next()) {
//            WebDriver driver = Driver.getDriver();
//            driver.get("https://www.google.com/");
//            driver.findElement(By.name("q")).sendKeys("How to live on the " +
//                    resultSet5.getString("min") + " dollar" + Keys.ENTER);
//            Thread.sleep(10000);
//            driver.quit();
//        }


    }
}
