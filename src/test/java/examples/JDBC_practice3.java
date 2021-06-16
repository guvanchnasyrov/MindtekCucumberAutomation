package examples;

import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBC_practice3 {

    public static void main(String[] args) throws SQLException {
        JDBCUtils.establishConnection();
        String query = "select * from employees";
        List<Map<String, Object>> dataTable = JDBCUtils.runQuery(query);
        //System.out.println(JDBCUtils.countRows(query));
        //System.out.println(dataTable);
        System.out.println(dataTable.get(0).get("FIRST_NAME"));

        System.out.print(dataTable.get(1).values());
        JDBCUtils.closeDatabase();
    }
}
