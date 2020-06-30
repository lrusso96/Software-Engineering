package db;

import java.sql.*;


public class DatabaseManager {

    private static Connection connection;
    private static int id = 0;

    public static void main(String[] args) throws Exception {

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + args[0]);
        Statement stat = connection.createStatement();

        if (args[1].equals("create")) {
            stat.executeUpdate("drop table if exists flight;");
            stat.executeUpdate("create table flight (id, name);");
            insert("AZ140");
            insert("LH999");
            insert("FR123");
            insert("US404");
        } else {
            ResultSet rs = stat.executeQuery("select * from flight;");
            while (rs.next()) {
                System.out.print("Flight " + rs.getString("id") + " is ");
                System.out.println(rs.getString("name"));
            }
            rs.close();
        }
        connection.close();
    }

    private static void insert(String name) throws Exception {
        PreparedStatement prep = connection.prepareStatement("insert into flight values (?, ?);");
        id++;
        prep.setString(1, String.valueOf(id));
        prep.setString(2, name);
        prep.addBatch();
        connection.setAutoCommit(false);
        prep.executeBatch();
        connection.setAutoCommit(true);
    }
}
