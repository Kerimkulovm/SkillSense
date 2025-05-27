package backend;
import java.sql.*;

public class getDataFromDB {


    public static void testClick() {
        System.out.println("Clicked !!!!  ");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=MineTraining; username=MineTraining;password=qazwsx";

            Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            ResultSet resultSet;
            System.out.println("qweqweqwe");
            resultSet = statement.executeQuery("select top 1   * from crew");

            String crew = "";
            if (!resultSet.next())
                throw new SQLException("aaaaaaaaaaa");
            crew = resultSet.getString("Crew");

            System.out.println(crew);
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());

        }
    }



}