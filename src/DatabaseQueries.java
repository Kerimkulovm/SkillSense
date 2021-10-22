import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQueries {

    Statement employeeSearchStatement;
    private int employeeID;

    String fullName;

    public void queryEmployeeData(int EmployeeID) {

        employeeID = EmployeeID;

        try {
            employeeSearchStatement = MineOperations.conn.createStatement();
            String id_query = "SELECT FROM dbo.Employees WHERE EmployeeID = " + employeeID;
            ResultSet employeeSearch_resultset = employeeSearchStatement.executeQuery(id_query);

            if (!employeeSearch_resultset.next()){
                JOptionPane.showMessageDialog(MineOperations.cardPane, "Сотрудник не найден!");
            } else {

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
