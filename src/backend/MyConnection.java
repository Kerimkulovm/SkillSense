package backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {

    static private Connection connection;

    public static Connection getConnection() throws Exception{
        if(connection == null){
            //JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-P9M9IPC\\SQLEXPRESS:1433;databaseName=MineTraining;user=DESKTOP-P9M9IPC\\Murat;");
        }
        return connection;
    }
}