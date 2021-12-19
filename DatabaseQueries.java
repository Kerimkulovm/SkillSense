import org.jdatepicker.impl.JDatePickerImpl;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DatabaseQueries {

    PreparedStatement st;
    PreparedStatement updateEmployee;
    PreparedStatement insertEmployee;

    String employeeID;

    String fullName;

    int ReportsTo;
    String superVisorName;

    String jobTitle;

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
    List<String> courses_list = new ArrayList<>();
    List<String> employeeID_list = new ArrayList<>();

    public void queryEmployeeData(String EmployeeID) {

        employeeID = EmployeeID;
        String id_query = "SELECT * FROM dbo.Employees WHERE EmployeeID =?";
        try {
            st = MineOperations.conn.prepareStatement(id_query);
            st.setString(1,employeeID);
            ResultSet employeeSearch_resultSet = st.executeQuery();

            if (!employeeSearch_resultSet.next()){
                JOptionPane.showMessageDialog(MineOperations.cardPane, "Сотрудник не найден!");
            } else {
                fullName = employeeSearch_resultSet.getString("LastName");
                ReportsTo = findSupervisorName(employeeSearch_resultSet.getInt("ReportsTo"));
                jobTitle = employeeSearch_resultSet.getString("RusTitle");
                departmentID = findDepartmentName(employeeSearch_resultSet.getInt("Department"));
                terminatedID = findTerminatedStatus(employeeSearch_resultSet.getInt("Terminated"));
                crewID = findCrewName(employeeSearch_resultSet.getInt("Shiftn"));
                lastSafetyOr = employeeSearch_resultSet.getString("SafteyOrin");

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
                        photoLabel = null;
                        ex.printStackTrace();
                    }

                    if (imagef != null) {
                        Image dimg = imagef.getScaledInstance(210,230, Image.SCALE_SMOOTH);
                        ImageIcon imageIcon = new ImageIcon(dimg);
                        photoLabel = new JLabel(imageIcon);
                    }
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
                superVisorName = supervisorName_result.getString("Russian");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return supervisorID_input;
    }

    private int findDepartmentName(int departmentID_input){

        String departmentID_query = "SELECT * FROM dbo.Departments WHERE DeptID = " + departmentID_input;

        try {

            Statement departmentName_statement = MineOperations.conn.createStatement();
            ResultSet departmentName_result = departmentName_statement.executeQuery(departmentID_query);

            while(departmentName_result.next()){
                departmentName = departmentName_result.getString("russian");
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

        String crewID_query = "SELECT * FROM dbo.Crews WHERE CrewNo = " + crewID_input;

        try {

            Statement crewName_statement = MineOperations.conn.createStatement();
            ResultSet crewName_result = crewName_statement.executeQuery(crewID_query);

            while (crewName_result.next()){
                crewName = crewName_result.getString("Crew");
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
        return Objects.requireNonNullElse(jobTitle, "Нет данных");
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
                "UPDATE dbo.Employees SET LastName = N'" + fullName + "', " +
                        "RusTitle = N'" + jobTitle + "', " +
                        "Shiftn = " + crewID + ", " +
                        "Department = " + departmentID + ", " +
                        "ReportsTo = " + ReportsTo + ", " +
                        "Terminated = " + terminatedID + ", " +
                        "SafteyOrin = " + checkTablesContent(lastSafetyOr) + ", " +
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
                        "WHERE EmployeeID = '" + employeeID+"'";

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

    public void setCrewID(JComboBox inputBox){

        String crew_query = "SELECT * FROM dbo.Crews WHERE Crew = N'" + (String) inputBox.getSelectedItem() + "'";

        try{
            Statement crew_statement = MineOperations.conn.createStatement();
            ResultSet crew_results = crew_statement.executeQuery(crew_query);

            while (crew_results.next()){
                crewID = crew_results.getInt("CrewNo");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setDepartmentID(JComboBox inputBox){

        String departQuery = "SELECT * FROM dbo.Departments WHERE russian = N'" + (String) inputBox.getSelectedItem()+"'";

        try {
            Statement departmentSt = MineOperations.conn.createStatement();
            ResultSet departmentResult = departmentSt.executeQuery(departQuery);

            while(departmentResult.next())
            {
                departmentID = departmentResult.getInt("DeptId");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setJobTitle(JComboBox inputBox){
        jobTitle = inputBox.getSelectedItem().toString();
    }

    public void setReportsTo(JComboBox inputBox){

        String supervisorID_query = "SELECT * FROM dbo.Supervisors WHERE Russian = N'" + (String) inputBox.getSelectedItem() + "'";

        try {

            Statement supervisorID_statement = MineOperations.conn.createStatement();
            ResultSet supervisorID_result = supervisorID_statement.executeQuery(supervisorID_query);

            while (supervisorID_result.next()){
                ReportsTo = supervisorID_result.getInt("SupervisorId");
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
        }  else if  (Objects.equals(objectInput.toString(), "")){
            return null;
        } else {
            returnString = "'" + objectInput.toString() + "'";
            System.out.println(returnString);
            return returnString;
        }
    }

    public void createEmployee(){

        final String checkEmployee_query = "SELECT * FROM dbo.Employees WHERE EmployeeID = ?";

        try {

            PreparedStatement checkEmployee_st = MineOperations.conn.prepareStatement(checkEmployee_query);
            checkEmployee_st.setString(1,employeeID);
            ResultSet employeeExist_result = checkEmployee_st.executeQuery();

            if (!employeeExist_result.next()){
                String insertQuery = "INSERT INTO dbo.Employees " +
                        "(EmployeeID, LastName, FirstName, RusTitle, ReportsTo, Shiftn, Department, Terminated, SafteyOrin, " +
                        "kumtor_A, kumtor_B, kumtor_V, kumtor_G, kumtor_D, kumtor_E, kumtor_E1, " +
                        "gos_A, gos_A1, gos_B, gos_B1, gos_C, gos_C1, gos_D, gos_D1, gos_BE, gos_CE, gos_C1E, gos_DE, gos_D1E, Transfered," +
                        "Driverschk, LightTtruck, MineResc, CraneTr) " +
                        "VALUES ('" +
                        employeeID + "', N'" +
                        fullName + "', N'" +
                        fullName + "', N'" +
                        jobTitle + "', " +
                        ReportsTo + ", " +
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
                        checkTablesContent(drivingLicence_table.getValueAt(12,1)) + ",0,0,0,0,0)";

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
            String query_text = "SELECT LastName, EmployeeID  FROM dbo.Employees " +
                    "WHERE EmployeeID is not null and LastName like N'%"+ surname_input +"%' order by LastName desc";

            System.out.println(query_text);
            ResultSet rs = searchStatement.executeQuery(query_text);
            employeeNames_list = new ArrayList<>();
            employeeID_list = new ArrayList<>();

            if (rs.next()){
                do {
                    String LastName = rs.getString("LastName");
                    employeeNames_list.add(LastName);

                    String employeeID = rs.getString("EmployeeID");
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

    public List<String> getListOfID(){
        return employeeID_list;
    }

    public boolean ifUserExists( String userID){

        boolean isExists = false;
        String query = "SELECT * FROM dbo.Employees WHERE EmployeeID = '" + userID + "'";

        try {
            Statement st = MineOperations.conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                isExists = true;
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return isExists;
    }

    public JComboBox loadSupervisorBox( JComboBox inputBox) {

        inputBox.removeAllItems();

        try{

            String reportsTo_query = "SELECT * FROM dbo.Supervisors WHERE isActive = 1";
            Statement reportsTo_statement = MineOperations.conn.createStatement();
            ResultSet reportsTo_result = reportsTo_statement.executeQuery((reportsTo_query));

            while(reportsTo_result.next()){
                String addReportsTo = reportsTo_result.getString("Russian");
                inputBox.addItem(addReportsTo);
            }
        }

        catch (SQLException e){

        }

        return inputBox;
    }

    public JComboBox loadJobTitlesBox(JComboBox inputBox){

        inputBox.removeAllItems();

        try {

            String positionRus_query = "SELECT * FROM dbo.JobTitles WHERE isActive = 1";
            Statement positionRus_statement = MineOperations.conn.createStatement();
            ResultSet positionRus_result = positionRus_statement.executeQuery(positionRus_query);

            while(positionRus_result.next())
            {
                String addPositionRusItem = positionRus_result.getString("RusTitle");
                if (addPositionRusItem != null){
                    inputBox.addItem(addPositionRusItem);
                } else continue;
            }
        }
        catch (SQLException ignored){

        }
        return inputBox;
    }

    public JComboBox loadDepartmentsBox(JComboBox inputBox){

        inputBox.removeAllItems();

        try{
            String departmentRus_query = "SELECT * FROM dbo.Departments WHERE isActive = 1";
            Statement departmentRus_statement = MineOperations.conn.createStatement();
            ResultSet departmentRus_result = departmentRus_statement.executeQuery(departmentRus_query);

            while(departmentRus_result.next()){
                String addDepartmentRus = departmentRus_result.getString("russian");
                int isActive = departmentRus_result.getInt("IsActive");

                if (isActive == 1){
                    inputBox.addItem(addDepartmentRus);
                } else continue;
            }
        }

        catch (SQLException ex){
            ex.printStackTrace();
        }

        return inputBox;
    }

    public JComboBox loadCrewBox(JComboBox inputBox){

        inputBox.removeAllItems();

        try {

            String crews_query = "SELECT * FROM dbo.Crews WHERE isActive = 1";
            Statement crews_statement = MineOperations.conn.createStatement();
            ResultSet crews_results = crews_statement.executeQuery(crews_query);

            while (crews_results.next()){
                String addCrew = crews_results.getString("Crew");
                int isActive = crews_results.getInt("IsActive");

                if (isActive == 1){
                    inputBox.addItem(addCrew);
                } else continue;
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return inputBox;
    }

    public List<String> loadCourses(){

        try {

            String courses_query = "select * from dbo.Courses Where isActive = " + 1 + " AND Course is not null";
            Statement courseSearchStatement = MineOperations.conn.createStatement();
            ResultSet coursesResult = courseSearchStatement.executeQuery(courses_query);

            if (coursesResult.next()){
                do{
                    String courseName= coursesResult.getString("Course");
                    courses_list.add(courseName);
                } while (coursesResult.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return courses_list;
    }

    public JTable loadAcceptedQualifications(JTable inputTable){

        for (int i = 0; i < inputTable.getRowCount(); i++){
            inputTable.setValueAt(false,i,1);
            inputTable.setValueAt(false,i,2);
            inputTable.setValueAt(false,i,3);
            inputTable.setValueAt(false,i,4);
            inputTable.setValueAt("",i,5);
        }

            try {
                String acceptedQialifications_query = "select * from Qualified where EmployeeID = '" + employeeID +"'";
                Statement acceptedSearchStatement = MineOperations.conn.createStatement();
                ResultSet acceptedQResult = acceptedSearchStatement.executeQuery(acceptedQialifications_query);

                if (acceptedQResult.next()){
                    do {

                         int QualifiedID = acceptedQResult.getInt("Equipment");
                         String courseName = "";
                         Date acceptedDate = acceptedQResult.getDate("Date");
                         int experienced = acceptedQResult.getInt("Experienced");
                         int qualified = acceptedQResult.getInt("Qualified");
                         int approved = acceptedQResult.getInt("Approved");
                         int training = acceptedQResult.getInt("Training");

                         Statement courseSearchSt = MineOperations.conn.createStatement();
                         ResultSet courseNames = courseSearchSt.executeQuery("select * from dbo.Courses where CoarseNo = " +  QualifiedID);
                         while(courseNames.next()){
                             System.out.println("select * from dbo.Courses where CoarseNo = " +  QualifiedID);
                             courseName = courseNames.getString("Course");
                             System.out.println("Qualified " + courseName);
                         }

                         for (int i = 0; i < inputTable.getRowCount(); i++){

                             if (Objects.equals((String) inputTable.getValueAt(i, 0), courseName)){

                                 System.out.println("Course Selected " + courseName);

                                 //Set checkbox for experienced
                                 if (experienced == 0){
                                     inputTable.setValueAt(false, i,1);
                                 } else {
                                     inputTable.setValueAt(true, i,1);
                                 }

                                 if (qualified == 0){
                                     inputTable.setValueAt(false, i,2);
                                 } else {
                                     inputTable.setValueAt(true, i,2);
                                 }

                                 if (approved == 0){
                                     inputTable.setValueAt(false, i,3);
                                 } else {
                                     inputTable.setValueAt(true, i,3);
                                 }

                                 if (training == 0){
                                     inputTable.setValueAt(false, i,4);
                                 } else {
                                     inputTable.setValueAt(true, i,4);
                                 }

                                 inputTable.setValueAt(acceptedDate, i, 5);

                             }
                         }

                    } while (acceptedQResult.next());
                }

            } catch (SQLException ex){
                ex.printStackTrace();
            }

        return inputTable;

    }

    public JTable saveAcceptedQualifications(JTable inputTable){

        try{
            String deleteQuery = "DELETE FROM Qualified WHERE EmployeeID= '" + employeeID +"'  and Equipment in (select distinct CoarseNo from Courses where isActive = 1)";
            PreparedStatement deleteQualifications_st = MineOperations.conn.prepareStatement(deleteQuery);
            deleteQualifications_st.executeUpdate();


            PreparedStatement insertQStatement;

            for (int i = 0; i < inputTable.getRowCount(); i++){
                  if (inputTable.getValueAt(i,5) != ""){
                      String insertQuery = "INSERT INTO dbo.Qualified " +
                              "(RecID, Equipment, EmployeeID, Date, Experienced, Qualified, Approved, Training) " +
                                      "VALUES (" + 0 + ", " +
                                      convertQualificationName((String) inputTable.getValueAt(i,0)) + ", '" +
                                      employeeID + "', " +
                                      checkTablesContent(inputTable.getValueAt(i,5)) + ", " +
                                      convertQualifiedBoolean((Boolean)inputTable.getValueAt(i,1)) + ", " +
                                      convertQualifiedBoolean((Boolean)inputTable.getValueAt(i, 2)) + ", " +
                                      convertQualifiedBoolean((Boolean)inputTable.getValueAt(i,3)) + ", " +
                                      convertQualifiedBoolean((Boolean)inputTable.getValueAt(i, 4)) + ")";

                      System.out.println(insertQuery);
                      insertQStatement = MineOperations.conn.prepareStatement(insertQuery);
                      insertQStatement.executeUpdate();
                  }
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return inputTable;
    }

    public int convertQualificationName(String inputString){

        int qualifID = 0;
        String QUI_query = "SELECT * FROM dbo.Courses WHERE Course = N'" + inputString+"'";

        try {

            Statement QID_st = MineOperations.conn.createStatement();
            ResultSet QID_resultSet = QID_st.executeQuery(QUI_query);

            while (QID_resultSet.next()){
                qualifID = QID_resultSet.getInt("CoarseNo");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        System.out.println(QUI_query);
        System.out.println(qualifID);
        return qualifID;
    }

    private int convertQualifiedBoolean(boolean inputBool){
        int boolInt = 0;

        if (!inputBool){
            boolInt = 0;
        } else {
            boolInt = 1;
        }

        return boolInt;
    }

    public JTable loadPracticedHoursJTable(JTable inputTable){

        for (int i = 0; i < inputTable.getRowCount(); i++){
            inputTable.setValueAt("",i,0);
            inputTable.setValueAt(null,i,1);
            inputTable.setValueAt(null,i,2);
            inputTable.setValueAt(null,i,3);
            inputTable.setValueAt(null,i,4);

        }

         try {
            String loadHours_query = "select t.employeeID, t.coarse, c.Course, sum(thours) as thours, sum(Phours) as Phours, sum(ExpHours) as Ehours, sum(fhours) as fhours   from TrainingData t " +
                    "left join dbo.Courses c on c.coarseNo = t.coarse " +
                    "where c.IsActive = 1 and  t.employeeID = '" + employeeID + "' " +
                    "group by  t.employeeID, t.coarse, c.Course";

             System.out.println(loadHours_query);

            Statement practicedHoursStatement = MineOperations.conn.createStatement();
            ResultSet practicedHoursResult = practicedHoursStatement.executeQuery(loadHours_query);

            int courseNum = 0;
             DecimalFormat format = new DecimalFormat("0.#");
             while (practicedHoursResult.next()){

                String courseName = practicedHoursResult.getString("Course");
                double tHours = practicedHoursResult.getDouble("thours");
                double pHours = practicedHoursResult.getDouble("Phours");
                double eHours = practicedHoursResult.getDouble("Ehours");
                double fHours = practicedHoursResult.getDouble("fhours");

                inputTable.setValueAt(courseName,courseNum,0);
                inputTable.setValueAt(format.format(tHours),courseNum,1);
                inputTable.setValueAt(format.format(pHours),courseNum,2);
                inputTable.setValueAt(format.format(eHours),courseNum,3);
                inputTable.setValueAt(format.format(fHours),courseNum,4);
                courseNum++;
             }


        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return inputTable;
    }

    public JComboBox loadCourseNameBox(JComboBox inputBox){

        try {

            String positionRus_query = " select 0 CoarseNo, N'Нет данных' Equipment, N'Нет данных' Course, 1 isActive\n" +
                    " union all \n" +
                    " SELECT * FROM dbo.Courses where isactive = 1";
            Statement positionRus_statement = MineOperations.conn.createStatement();
            ResultSet positionRus_result = positionRus_statement.executeQuery(positionRus_query);

            while(positionRus_result.next())
            {
                String addCourseNameItem = positionRus_result.getString("Course");
                Integer addCourseId = positionRus_result.getInt("CoarseNo");
                if (addCourseNameItem != null){
                    inputBox.addItem(new Item(addCourseId, addCourseNameItem));
                } else continue;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return inputBox;
    }

    public JComboBox loadTrainerBox( JComboBox inputBox) {

        try{

            String Trainer_query = "  select 0 InstructoId, N'Нет данных' InstructorName, N'Нет данных' Instructor, 1 isActive\n" +
                    " union all \n" +
                    " SELECT * FROM dbo.Instructor where isactive = 1";
            Statement rs = MineOperations.conn.createStatement();
            ResultSet reportsTo_result = rs.executeQuery((Trainer_query));

            while(reportsTo_result.next()){
                String addTrainer = reportsTo_result.getString("Instructor");
                Integer addTrainerId = reportsTo_result.getInt("InstructoId");
               // inputBox.addItem(addTrainer);
                inputBox.addItem(new Item(addTrainerId, addTrainer));
            }
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return inputBox;
    }

    public String getLastDate(String idUser, int idCourse){

        String lastDate_query = " select max(LastDate) as lastDate from SRT where employeeId = '"+idUser+"' and coarse = " + idCourse;
        System.out.println(lastDate_query);
        String lastDate = "";
        try {

            Statement st = MineOperations.conn.createStatement();
            ResultSet rs = st.executeQuery(lastDate_query);

            while (rs.next()){
                lastDate = rs.getString("lastDate");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return lastDate;
    }

    class Item {

        private int id;
        private String description;

        public Item(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public boolean saveSRT(String empId, int CourseId, int instructorId, String lastDate, int mark_text, int fHours, int tHours){
        boolean res = false;
        try{

            //int m = getMaxIDFromTable("SRT");
           // m++;
            PreparedStatement insertQStatement;
            String insertQuery = "INSERT INTO dbo.SRT " +
                    "( EmployeeID, LastDate, ScheduledDate, coarse, payslip, FieldHours, Thours, Instructor, Mark) " +
                    "VALUES ( '" + empId + "', '" + lastDate + "', DATEADD(year, 1, '" + lastDate + "'), " +
                    CourseId + ", 0,  " + fHours+ ", " + tHours + ", " + instructorId + ", " + mark_text + ")";

            System.out.println(insertQuery);
            insertQStatement = MineOperations.conn.prepareStatement(insertQuery);
            insertQStatement.executeUpdate();
            res = true;

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return res;
    }

    public boolean saveOperationDaily(String empId, int CourseId, int instructorId, String Date, int tHours, int fHours, int pHours, int expHours){
        boolean res = false;
        try{
            System.out.println(tHours);
            System.out.println(fHours);
            System.out.println(pHours);
            System.out.println(expHours);
            //int m = getMaxIDFromTable("TrainingData");
            //m++;
            PreparedStatement insertQStatement;
            String insertQuery = "INSERT INTO dbo.TrainingData " +
                        "( EmployeeID, Date, Thours, FHours, Phours, Exphours, coarse, Instructor  ) " +
                    "VALUES ( '" + empId + "', '" + Date + "', " + tHours + ", " + fHours + ", " + pHours + ", " + expHours + ", " +
                     CourseId + ", " + instructorId + ")";

            System.out.println(insertQuery);
            insertQStatement = MineOperations.conn.prepareStatement(insertQuery);
            insertQStatement.executeUpdate();
            res = true;

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return res;
    }
}