import org.jdatepicker.impl.JDatePickerImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DatabaseQueries {

    Statement employeeSearchStatement;
    PreparedStatement updateEmployee;
    PreparedStatement insertEmployee;

    String employeeID;

    String fullName;

    int supervisorID;
    String superVisorName;

    int jobTitleID;
    String jobName;

    int departmentID;
    String departmentName;

    int terminatedID;
    String terminatedStatus;

    int crewID;
    String crewName;

    String lastSafetyOr;

    JTable drivingLicence_table = new JTable(13,2);
    Date gos_A_date;
    Date gos_A1_date;
    Date gos_B_date;
    Date gos_B1_date;
    Date gos_C_date;
    Date gos_C1_date;
    Date gos_D_date;
    Date gos_D1_date;
    Date gos_BE_date;
    Date gos_CE_date;
    Date gos_C1E_date;
    Date gos_DE_date;
    Date gos_D1E_date;

    JTable truckLicence_table = new JTable(7,2);
    Date kumtor_A_date;
    Date kumtor_B_date;
    Date kumtor_V_date;
    Date kumtor_G_date;
    Date kumtor_D_date;
    Date kumtor_E_date;
    Date kumtor_E1_date;

    JLabel photoLabel = new JLabel();

    List<String> employeeNames_list = new ArrayList<>();
    List<Integer> employeeID_list = new ArrayList<>();

    public void queryEmployeeData(String EmployeeID) {

        employeeID = EmployeeID;

        try {
            employeeSearchStatement = MineOperations.conn.createStatement();
            String id_query = "SELECT * FROM dbo.Employees WHERE EmployeeID = " + employeeID;
            ResultSet employeeSearch_resultSet = employeeSearchStatement.executeQuery(id_query);

            if (!employeeSearch_resultSet.next()){
                JOptionPane.showMessageDialog(MineOperations.cardPane, "Сотрудник не найден!");
            } else {
                fullName = employeeSearch_resultSet.getString("FullNameRu");
                supervisorID = findSupervisorName(employeeSearch_resultSet.getInt("SupervisorId"));
                jobTitleID = findJobName(employeeSearch_resultSet.getInt("JobTitleId"));
                departmentID = findDepartmentName(employeeSearch_resultSet.getInt("DepartmentId"));
                terminatedID = findTerminatedStatus(employeeSearch_resultSet.getInt("Terminated"));
                crewID = findCrewName(employeeSearch_resultSet.getInt("CrewId"));
                lastSafetyOr = employeeSearch_resultSet.getString("SafetyOrientation");

                kumtor_A_date = employeeSearch_resultSet.getDate("kumtor_A");
                truckLicence_table.setValueAt(kumtor_A_date, 0, 1);
                kumtor_B_date = employeeSearch_resultSet.getDate("kumtor_B");
                truckLicence_table.setValueAt(kumtor_B_date,1,1);
                kumtor_V_date = employeeSearch_resultSet.getDate("kumtor_V");
                truckLicence_table.setValueAt(kumtor_V_date, 2, 1);
                kumtor_G_date = employeeSearch_resultSet.getDate("kumtor_G");
                truckLicence_table.setValueAt(kumtor_G_date, 3, 1);
                kumtor_D_date = employeeSearch_resultSet.getDate("kumtor_D");
                truckLicence_table.setValueAt(kumtor_D_date, 4, 1);
                kumtor_E_date = employeeSearch_resultSet.getDate("kumtor_E");
                truckLicence_table.setValueAt(kumtor_E_date, 5, 1);
                kumtor_E1_date = employeeSearch_resultSet.getDate("kumtor_E1");
                truckLicence_table.setValueAt(kumtor_E1_date, 6, 1);

                gos_A_date = employeeSearch_resultSet.getDate("gos_A");
                drivingLicence_table.setValueAt(gos_A_date,0,1);
                gos_A1_date = employeeSearch_resultSet.getDate("gos_A1");
                drivingLicence_table.setValueAt(gos_A1_date,1,1);
                gos_B_date = employeeSearch_resultSet.getDate("gos_B");
                drivingLicence_table.setValueAt(gos_B_date,2,1);
                gos_B1_date = employeeSearch_resultSet.getDate("gos_B1");
                drivingLicence_table.setValueAt(gos_B1_date,3,1);
                gos_C_date = employeeSearch_resultSet.getDate("gos_C");
                drivingLicence_table.setValueAt(gos_C_date,4,1);
                gos_C1_date = employeeSearch_resultSet.getDate("gos_C1");
                drivingLicence_table.setValueAt(gos_C1_date,5,1);
                gos_D_date = employeeSearch_resultSet.getDate("gos_D");
                drivingLicence_table.setValueAt(gos_D_date,6,1);
                gos_D1_date = employeeSearch_resultSet.getDate("gos_D1");
                drivingLicence_table.setValueAt(gos_D1_date,7,1);
                gos_BE_date = employeeSearch_resultSet.getDate("gos_BE");
                drivingLicence_table.setValueAt(gos_BE_date,8,1);
                gos_CE_date = employeeSearch_resultSet.getDate("gos_CE");
                drivingLicence_table.setValueAt(gos_CE_date,9,1);
                gos_C1E_date = employeeSearch_resultSet.getDate("gos_C1E");
                drivingLicence_table.setValueAt(gos_C1E_date,10,1);
                gos_DE_date = employeeSearch_resultSet.getDate("gos_DE");
                drivingLicence_table.setValueAt(gos_DE_date,11,1);
                gos_D1E_date = employeeSearch_resultSet.getDate("gos_D1E");
                drivingLicence_table.setValueAt(gos_D1E_date,12,1);

                if (employeeSearch_resultSet.getBlob("Photo") != null){
                    InputStream is = null;
                    try {
                        Blob aBlob = employeeSearch_resultSet.getBlob("Photo");
                        byte[] imageByte = aBlob.getBytes(1, (int) aBlob.length());
                        is = new ByteArrayInputStream(imageByte);
                    } catch (NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }

                    BufferedImage imagef = null;
                    try{
                        imagef = ImageIO.read(is);
                    }catch (NullPointerException | IOException ex){
                        ex.printStackTrace();
                    }

                    photoLabel = new JLabel(new ImageIcon(imagef));

                } else {
                    photoLabel = null;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int findSupervisorName(int supervisorID_input) {

        String supervisorName_query = "SELECT * FROM dbo.Supervisors WHERE SupervisorId = " + supervisorID_input;

        try {

            Statement supervisorName_statement = MineOperations.conn.createStatement();
            ResultSet supervisorName_result = supervisorName_statement.executeQuery(supervisorName_query);

            while (supervisorName_result.next()) {
                superVisorName = supervisorName_result.getString("SupervisorNameRu");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return supervisorID_input;
    }

    private int findJobName(int jobTitleID_input){

        String jobTitle_query = "SELECT * FROM dbo.JobTitles WHERE JobTitleId = '" + jobTitleID_input + "'";

        try{

            Statement jobTitle_statement = MineOperations.conn.createStatement();
            ResultSet jobTitle_result = jobTitle_statement.executeQuery(jobTitle_query);

            while(jobTitle_result.next()){
                jobName = jobTitle_result.getString("JobTitleNameRu");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return jobTitleID_input;
    }

    private int findDepartmentName(int departmentID_input){

        String departmentID_query = "SELECT * FROM dbo.Departments WHERE DepartmentID = " + departmentID_input;

        try {

            Statement departmentName_statement = MineOperations.conn.createStatement();
            ResultSet departmentName_result = departmentName_statement.executeQuery(departmentID_query);

            while(departmentName_result.next()){
                departmentName = departmentName_result.getString("DepartmentNameRu");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return departmentID_input;
    }

    private int findTerminatedStatus(int terminatedID_input){

        if (terminatedID_input == 0 ){
            terminatedStatus = "Работает";
        } else {
            terminatedStatus = "Уволен";
        }

        return terminatedID_input;
    }

    private int findCrewName(int crewID_input){

        String crewID_query = "SELECT * FROM dbo.Crews WHERE CrewId = " + crewID_input;

        try {

            Statement crewName_statement = MineOperations.conn.createStatement();
            ResultSet crewName_result = crewName_statement.executeQuery(crewID_query);

            while (crewName_result.next()){
                crewName = crewName_result.getString("CrewName");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return crewID_input;
    }

    public String getEmployeeName(){
        return Objects.requireNonNullElse(fullName, "Нет данных");
    }

    public String getSuperVisorName(){
        return Objects.requireNonNullElse(superVisorName, "Нет данных");
    }

    public String getJobName(){
        return Objects.requireNonNullElse(jobName, "Нет данных");
    }

    public String getDepartmentName(){
        return Objects.requireNonNullElse(departmentName, "Нет данных");
    }

    public String getTerminatedStatus(){
        return Objects.requireNonNullElse(terminatedStatus, "Нет данных");
    }

    public String getCrewName(){
        return Objects.requireNonNullElse(crewName, "Нет данных");
    }

    public JLabel getPhotoLabel(){
        return photoLabel;
    }

    public String getLastSafetyOr() {return lastSafetyOr;}

    public void updateEmployee(){

        String updateQuery =
                "UPDATE dbo.Employees SET FullNameRu = N'" + fullName + "', " +
                        "JobTitleId = " + jobTitleID + ", " +
                        "CrewId = " + crewID + ", " +
                        "DepartmentId = " + departmentID + ", " +
                        "SupervisorId = " + supervisorID + ", " +
                        "Terminated = " + terminatedID + ", " +
                        "SafetyOrientation = " + checkTablesContent(lastSafetyOr) + ", " +
                        "kumtor_A = " + checkTablesContent(truckLicence_table.getValueAt(0,1)) + ", " +
                        "kumtor_B = " + checkTablesContent(truckLicence_table.getValueAt(1,1)) + ", " +
                        "kumtor_V = " + checkTablesContent(truckLicence_table.getValueAt(2,1)) + ", " +
                        "kumtor_G = " + checkTablesContent(truckLicence_table.getValueAt(3,1)) + ", " +
                        "kumtor_D = " + checkTablesContent(truckLicence_table.getValueAt(4,1)) + ", " +
                        "kumtor_E = " + checkTablesContent(truckLicence_table.getValueAt(5,1)) + ", " +
                        "kumtor_E1 = " + checkTablesContent(truckLicence_table.getValueAt(6,1)) + ", " +
                        "gos_A = " + checkTablesContent(drivingLicence_table.getValueAt(0,1)) + ", " +
                        "gos_A1 = " + checkTablesContent(drivingLicence_table.getValueAt(1,1)) + ", " +
                        "gos_B = " + checkTablesContent(drivingLicence_table.getValueAt(2,1)) + ", " +
                        "gos_B1 = " + checkTablesContent(drivingLicence_table.getValueAt(3,1)) + ", " +
                        "gos_C = " + checkTablesContent(drivingLicence_table.getValueAt(4,1)) + ", " +
                        "gos_C1 = " + checkTablesContent(drivingLicence_table.getValueAt(5,1)) + ", " +
                        "gos_D = " + checkTablesContent(drivingLicence_table.getValueAt(6,1)) + ", " +
                        "gos_D1 = " + checkTablesContent(drivingLicence_table.getValueAt(7,1)) + ", " +
                        "gos_BE = " + checkTablesContent(drivingLicence_table.getValueAt(8,1)) + ", " +
                        "gos_CE = " + checkTablesContent(drivingLicence_table.getValueAt(9,1)) + ", " +
                        "gos_C1E = " + checkTablesContent(drivingLicence_table.getValueAt(10,1)) + ", " +
                        "gos_DE = " + checkTablesContent(drivingLicence_table.getValueAt(11,1)) + ", " +
                        "gos_D1E = " + checkTablesContent(drivingLicence_table.getValueAt(12,1)) + " " +
                        "WHERE EmployeeID = " + employeeID;

        System.out.println(updateQuery);

        try{
            updateEmployee = MineOperations.conn.prepareStatement(updateQuery);
            updateEmployee.executeUpdate();
            JOptionPane.showMessageDialog(MineOperations.cardPane, "Информация о сотруднике изменена успешна");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setFullName(String fullName_input){
        fullName = fullName_input;
    }


    public void setJobID(JComboBox inputBox){

        String jobID_query = "SELECT * FROM dbo.JobTitles WHERE JobTitleNameRu = N'" + (String) inputBox.getSelectedItem() + "'";

        try {

            Statement jobID_statement = MineOperations.conn.createStatement();
            ResultSet jobID_result = jobID_statement.executeQuery(jobID_query);

            while (jobID_result.next()){
                jobTitleID = jobID_result.getInt("JobTitleId");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setCrewID(JComboBox inputBox){

        String crew_query = "SELECT * FROM dbo.Crews WHERE CrewName = N'" + (String) inputBox.getSelectedItem() + "'";

        try{
            Statement crew_statement = MineOperations.conn.createStatement();
            ResultSet crew_results = crew_statement.executeQuery(crew_query);

            while (crew_results.next()){
                crewID = crew_results.getInt("CrewId");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setDepartmentID(JComboBox inputBox){

        String departQuery = "SELECT * FROM dbo.Departments WHERE DepartmentNameRu = N'" + (String) inputBox.getSelectedItem()+"'";

        try {
            Statement departmentSt = MineOperations.conn.createStatement();
            ResultSet departmentResult = departmentSt.executeQuery(departQuery);

            while(departmentResult.next())
            {
                departmentID = departmentResult.getInt("DepartmentId");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setSupervisorID(JComboBox inputBox){

        String supervisorID_query = "SELECT * FROM dbo.Supervisors WHERE SupervisorNameRu = N'" + (String) inputBox.getSelectedItem() + "'";

        try {

            Statement supervisorID_statement = MineOperations.conn.createStatement();
            ResultSet supervisorID_result = supervisorID_statement.executeQuery(supervisorID_query);

            while (supervisorID_result.next()){
                supervisorID = supervisorID_result.getInt("SupervisorId");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setTerminatedID(JComboBox inputBox){

        String inputString = (String) inputBox.getSelectedItem();
        terminatedID = 0;

        if (Objects.equals(inputString, "Уволен")){
            terminatedID = 1;
        }
    }

    public void setTruckLicence_table(JTable inputTable){
        for (int i = 0; i < inputTable.getRowCount(); i ++){
            truckLicence_table.setValueAt(inputTable.getValueAt(i, 1), i,1);
        }
    }

    public void setDrivingLicence_table(JTable inputTable){
        for (int i = 0; i < inputTable.getRowCount(); i++){
            drivingLicence_table.setValueAt(inputTable.getValueAt(i,1), i,1);
        }
    }

    public void setLastSafetyOr(JDatePickerImpl inputPicker){
        lastSafetyOr = inputPicker.getJFormattedTextField().getText();
    }

    public void setEmployeeID(String employeeID_input){
        employeeID = employeeID_input;
    }

    public String checkTablesContent(Object objectInput){

        String returnString;

        if (objectInput == null){
            return null;
        } else {
            returnString = "'" + objectInput.toString() + "'";
            return returnString;
        }
    }

    public void createEmployee(){

        final String checkEmployee_query = "SELECT * FROM dbo.Employees WHERE EmployeeID = " + employeeID;

        try {

            final Statement checkEmployee_st = MineOperations.conn.createStatement();
            final ResultSet employeeExist_result = checkEmployee_st.executeQuery(checkEmployee_query);

            if (!employeeExist_result.next()){
                String insertQuery = "INSERT INTO dbo.Employees " +
                        "(EmployeeID, FullName, FullNameRu, JobTitleId, SupervisorId, CrewId, DepartmentId, Terminated, SafetyOrientation, " +
                        "kumtor_A, kumtor_B, kumtor_V, kumtor_G, kumtor_D, kumtor_E, kumtor_E1, " +
                        "gos_A, gos_A1, gos_B, gos_B1, gos_C, gos_C1, gos_D, gos_D1, gos_BE, gos_CE, gos_C1E, gos_DE, gos_D1E ) " +
                        "VALUES (" +
                        employeeID + ", N'" +
                        fullName + "', N'" +
                        fullName + "', " +
                        jobTitleID + ", " +
                        supervisorID + ", " +
                        crewID + ", " +
                        departmentID + ", " +
                        terminatedID + ", " +
                        checkTablesContent(lastSafetyOr) + ", " +
                        checkTablesContent(truckLicence_table.getValueAt(0,1)) + ", " +
                        checkTablesContent(truckLicence_table.getValueAt(1,1)) + ", " +
                        checkTablesContent(truckLicence_table.getValueAt(2,1)) + ", " +
                        checkTablesContent(truckLicence_table.getValueAt(3,1)) + ", " +
                        checkTablesContent(truckLicence_table.getValueAt(4,1)) + ", " +
                        checkTablesContent(truckLicence_table.getValueAt(5,1)) + ", " +
                        checkTablesContent(truckLicence_table.getValueAt(6,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(0,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(1,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(2,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(3,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(4,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(5,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(6,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(7,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(8,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(9,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(10,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(11,1)) + ", " +
                        checkTablesContent(drivingLicence_table.getValueAt(12,1)) + ")";

                System.out.println(insertQuery);
                insertEmployee = MineOperations.conn.prepareStatement(insertQuery);
                insertEmployee.executeUpdate();
                JOptionPane.showMessageDialog(MineOperations.cardPane,"Сотрудник успешно добавлен");

            } else {
                do {
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Табельный номер: " +
                            employeeID + " уже занят. Пожалуйста введите другой");
                } while (employeeExist_result.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void findBySurname(String surname_input){

        try{
            Statement searchStatement = MineOperations.conn.createStatement();
            String query_text = "SELECT FullNameRu, EmployeeID  FROM dbo.Employees " +
                    "WHERE EmployeeID is not null and FullNameRu like N'%"+ surname_input +"%' order by FullNameRu desc";

            System.out.println(query_text);
            ResultSet rs = searchStatement.executeQuery(query_text);

            if (rs.next()){
                System.out.println("RR");
                do {
                    String fullNameRu = rs.getString("FullNameRu");
                    employeeNames_list.add(fullNameRu);

                    int employeeID = rs.getInt("EmployeeID");
                    employeeID_list.add(employeeID);

                }while (rs.next());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<String> getListOfSurnames() {
        return employeeNames_list;
    }

    public List<Integer> getListOfID(){
        return employeeID_list;
    }
}