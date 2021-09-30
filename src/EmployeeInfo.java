import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;

public class EmployeeInfo extends JPanel {

    private final JLabel tableID_label;

    private final JTextField
            nameRus_text,
            lastOr_text,
            tableID_text;

    private final JButton
            search_button,
            add_button,
            save_button,
            edit_button,
            delete_button,
            cancel_button;

    private JComboBox
            departmentRus_box,
            shiftRus_box,
            positionRus_box,
            supervisor_box,
            terminatedStatus_box;

    private DefaultComboBoxModel shiftsModelBox;

    private BufferedImage logo_image;
    private boolean editUser;


    public EmployeeInfo()
    {
        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {

        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Информация о сотрудниках</big><br />Поиск данных сотрудника</html>");
        titleEng.setBounds(160, 0, 400, 100);
        titleEng.setFont(new Font("Helvetica", Font.BOLD, 20));
        titleEng.setForeground(Color.WHITE);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel searchEmployee_panel = new JPanel();
        searchEmployee_panel.setBackground(Color.white);
        searchEmployee_panel.setLayout(new BoxLayout(searchEmployee_panel, BoxLayout.X_AXIS));
        searchEmployee_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Поиск сотрудника"));
        searchEmployee_panel.setBounds(20, 120, 450, 50);
        this.add(searchEmployee_panel);

        JPanel tableID_panel = new JPanel();
        tableID_label = new JLabel(" Табельный номер:  ");
        tableID_label.setForeground(Color.RED);
        tableID_panel.setBackground(Color.WHITE);
        tableID_panel.add(tableID_label);
        searchEmployee_panel.add(tableID_label);

        tableID_text = new JTextField();
        tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
        tableID_panel.add(tableID_text);
        searchEmployee_panel.add(tableID_text);

        search_button = new JButton("Поиск");
        search_button.setForeground(Color.RED);
        search_button.setBackground(Color.WHITE);
        tableID_panel.add(search_button);
        searchEmployee_panel.add(search_button);
        search_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement searchStatement = MineOperations.conn.createStatement();
                    String query_text =
                            "SELECT * FROM dbo.Employees WHERE EmployeeID = " + tableID_text.getText();
                    System.out.println(query_text);
                    ResultSet searchResults = searchStatement.executeQuery(query_text);

                    if (!searchResults.next()) throw new SQLException("Error");

                    String employeeRusName_string = searchResults.getString("LastName");
                    nameRus_text.setText(employeeRusName_string);

                    String supervisor_string = searchResults.getString("ReportsTo");
                    enableComboText(supervisor_box).setSelectedItem(selectSupervisorName(supervisor_string));

                    String positionRus_string = searchResults.getString("RusTitle");
                    enableComboText(positionRus_box).setSelectedItem(positionRus_string);

                    String departmentID_string = findDepartment(searchResults.getInt("Department"));
                    enableComboText(departmentRus_box).setSelectedItem(departmentID_string);

                    String terminatedStatus_string = findTerminatedStatus(searchResults.getInt("Terminated"));
                    enableComboText(terminatedStatus_box).setSelectedItem(terminatedStatus_string);

                    String shift_string  = searchResults.getString("Shift");
                    if (shiftsModelBox.getIndexOf(checkNullVariable(shift_string))==-1){
                        shiftsModelBox.setSelectedItem("");
                    } else {
                        enableComboText(shiftRus_box).setSelectedItem(checkNullVariable(shift_string));
                    }

                    String lastOr_string = searchResults.getString("SafteyOrin");
                    lastOr_text.setText(checkNullVariable(lastOr_string).substring(0,10));

                    edit_button.setEnabled(true);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Сотрудник не найден!");
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel employeeInfo_panel = new JPanel();
        employeeInfo_panel.setBackground(Color.white);
        employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
        employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Персональная Информация"));
        employeeInfo_panel.setBounds(20, 175, 450, 180);
        this.add(employeeInfo_panel);

        JPanel infoLabels = new JPanel();
        JPanel inputPanel = new JPanel();

        infoLabels.setLayout(new BoxLayout(infoLabels, BoxLayout.Y_AXIS));
        infoLabels.setBackground(Color.WHITE);
        employeeInfo_panel.add(infoLabels);

        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        employeeInfo_panel.add(inputPanel);

        JPanel lastOrientationInfoPanel = new JPanel();
        lastOrientationInfoPanel.setBackground(Color.WHITE);
        lastOrientationInfoPanel.setBounds(20, 360, 450, 50);
        lastOrientationInfoPanel.setLayout(new BoxLayout(lastOrientationInfoPanel, BoxLayout.X_AXIS));
        lastOrientationInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Общие Правила ТБ"));
        this.add(lastOrientationInfoPanel);

        JPanel lastOrientLabels = new JPanel();
        JPanel lastOrientTexts = new JPanel();

        lastOrientLabels.setLayout(new BoxLayout(lastOrientLabels, BoxLayout.Y_AXIS));
        lastOrientLabels.setBackground(Color.WHITE);
        lastOrientationInfoPanel.add(lastOrientLabels);

        lastOrientTexts.setLayout(new BoxLayout(lastOrientTexts, BoxLayout.Y_AXIS));
        lastOrientTexts.setBackground(Color.WHITE);
        lastOrientationInfoPanel.add(lastOrientTexts);

        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(Color.WHITE);
        photoPanel.setBounds(480, 120, 200, 240);
        photoPanel.setLayout(new BorderLayout());
        photoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Фото"));
        this.add(photoPanel);

        JPanel licenceInfoPanel = new JPanel();
        licenceInfoPanel.setBackground(Color.WHITE);
        licenceInfoPanel.setBounds(20, 415, 450, 80);
        licenceInfoPanel.setLayout(new BorderLayout());
        licenceInfoPanel.setBorder(new LineBorder(Color.orange));
        this.add(licenceInfoPanel);

        JPanel driverLicenceInfo_panel = new JPanel();
        driverLicenceInfo_panel.setBackground(Color.WHITE);
        driverLicenceInfo_panel.setBounds(20, 500,450,160);
        driverLicenceInfo_panel.setLayout(new BorderLayout());
        driverLicenceInfo_panel.setBorder(new LineBorder(Color.orange));
        this.add(driverLicenceInfo_panel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel nameRus_panel = new JPanel();
        JLabel nameRus_label = new JLabel("Имя и Фамилия: ");
        nameRus_panel.add(nameRus_label);
        nameRus_panel.setBackground(Color.WHITE);
        nameRus_panel.setForeground(Color.BLACK);
        infoLabels.add(nameRus_panel);

        nameRus_text = new JTextField();
        nameRus_text.setEnabled(false);
        nameRus_text.setForeground(Color.BLACK);
        nameRus_text.setDisabledTextColor(Color.BLACK);
        nameRus_panel.add(nameRus_text);
        inputPanel.add(nameRus_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel positionRus_panel = new JPanel();
        JLabel positionRus_label = new JLabel("Должность: ");
        positionRus_panel.add(positionRus_label);
        positionRus_panel.setBackground(Color.WHITE);
        infoLabels.add(positionRus_panel);

        positionRus_box = new JComboBox();
        positionRus_box.setBackground(Color.WHITE);
        positionRus_box.setEnabled(false);
        try {

            String positionRus_query = "SELECT * FROM dbo.EnTitles";
            Statement positionRus_statement = MineOperations.conn.createStatement();
            ResultSet positionRus_result = positionRus_statement.executeQuery(positionRus_query);

            while(positionRus_result.next())
            {
                String addPositionRusItem = positionRus_result.getString("RusTitle");
                positionRus_box.addItem(addPositionRusItem);
            }
        }
        catch (SQLException ignored){

        }
        positionRus_panel.add(positionRus_box);
        inputPanel.add(positionRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel reportsTo_panel = new JPanel();
        JLabel reportsTo_label = new JLabel("Начальник: ");
        reportsTo_panel.add(reportsTo_label);
        reportsTo_panel.setBackground(Color.WHITE);
        infoLabels.add(reportsTo_panel);

        supervisor_box = new JComboBox();
        supervisor_box.setBackground(Color.WHITE);
        supervisor_box.setEnabled(false);
        try{

            String reportsTo_query = "SELECT * FROM dbo.Supervisors";
            Statement reportsTo_statement = MineOperations.conn.createStatement();
            ResultSet reportsTo_result = reportsTo_statement.executeQuery((reportsTo_query));

            while(reportsTo_result.next()){
                String addReportsTo = reportsTo_result.getString("Russian");
                supervisor_box.addItem(addReportsTo);
            }
        }

        catch (SQLException e){

        }
        reportsTo_panel.add(supervisor_box);
        inputPanel.add(supervisor_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel departmentRus_panel = new JPanel();
        JLabel departmentRus_label = new JLabel("Отдел: ");
        departmentRus_panel.add(departmentRus_label);
        departmentRus_panel.setBackground(Color.WHITE);
        infoLabels.add(departmentRus_panel);

        departmentRus_box = new JComboBox();
        departmentRus_box.setBackground(Color.WHITE);
        departmentRus_box.setEnabled(false);

        try{

            String departmentRus_query = "SELECT * FROM dbo.Department";
            Statement departmentRus_statement = MineOperations.conn.createStatement();
            ResultSet departmentRus_result = departmentRus_statement.executeQuery((departmentRus_query));

            while(departmentRus_result.next()){
                String addDepartmentRus = departmentRus_result.getString("russian");
                departmentRus_box.addItem(addDepartmentRus);
            }
        }

        catch (SQLException ex){
            ex.printStackTrace();
        }

        departmentRus_panel.add(departmentRus_box);
        inputPanel.add(departmentRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel shiftEng_panel = new JPanel();
        JLabel shift_label = new JLabel("Смена: ");
        shiftEng_panel.add(shift_label);
        shiftEng_panel.setBackground(Color.WHITE);
        infoLabels.add(shiftEng_panel);

        String[] shifts = new String[]{"Нет данных","A   Crew","B   Crew","C   Crew","D   Crew"};
        shiftsModelBox = new DefaultComboBoxModel(shifts);
        shiftRus_box = new JComboBox(shiftsModelBox);
        shiftRus_box.setBackground(Color.WHITE);
        shiftRus_box.setEnabled(false);
        shiftEng_panel.add(shiftRus_box);
        inputPanel.add(shiftRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel terminated_panel = new JPanel();
        JLabel terminated_label = new JLabel("Статус: ");
        terminated_panel.add(terminated_label);
        terminated_panel.setBackground(Color.WHITE);
        infoLabels.add(terminated_panel);

        String[] status = new String[]{"Работает","Уволен"};
        terminatedStatus_box = new JComboBox(status);
        terminatedStatus_box.setBackground(Color.WHITE);
        terminatedStatus_box.setEnabled(false);
        terminated_panel.add(terminatedStatus_box);
        inputPanel.add(terminatedStatus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel lastOr_panel = new JPanel();
        JLabel lastOr_label = new JLabel("Общие правила ТБ: ");
        lastOr_panel.add(lastOr_label);
        lastOr_panel.setBackground(Color.WHITE);
        lastOrientLabels.add(lastOr_panel);

        lastOr_text = new JTextField();
        lastOr_text.setEnabled(false);
        lastOr_text.setForeground(Color.BLACK);
        lastOr_text.setDisabledTextColor(Color.BLACK);
        lastOr_panel.add(lastOr_text);
        lastOrientTexts.add(lastOr_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DefaultTableModel licence_model = new DefaultTableModel(7,3);
        DefaultTableCellRenderer centerRederer = new DefaultTableCellRenderer();
        centerRederer.setHorizontalAlignment(JLabel.CENTER);

        JTable licence_table = new JTable(licence_model);
        licence_table.setBorder(new LineBorder(Color.BLACK));
        licence_table.setBackground(Color.WHITE);
        licence_table.setRowHeight(20);
        licence_table.setVisible(true);

        JTableHeader header= licence_table.getTableHeader();
        header.setBorder(new LineBorder(Color.BLACK));
        header.setBackground(Color.WHITE);

        TableColumnModel licence_columns = header.getColumnModel();

        TableColumn tabCol0 = licence_columns.getColumn(0);
        tabCol0.setHeaderValue("Категория");
        tabCol0.setCellRenderer(centerRederer);
        tabCol0.setPreferredWidth(10);

        String[] categories_list = new String[]{"А","Б","В","Г","Д","Е","Е1"};
        for (int i = 0; i < licence_table.getRowCount(); i++){
            licence_table.setValueAt(categories_list[i],i,0);
        }

        TableColumn tabCol1 = licence_columns.getColumn(1);
        tabCol1.setCellRenderer(centerRederer);
        tabCol1.setHeaderValue("Статус");

        String[] licenceStatus_string = new String[]{"Разрешено", "Недопущен"};
        JComboBox licenceStatus_box = new JComboBox(licenceStatus_string);
        tabCol1.setCellEditor(new DefaultCellEditor(licenceStatus_box));

        for (int i = 0; i < licence_table.getRowCount(); i++ ){
            licence_table.setValueAt(licenceStatus_string[1],i,1);
        }

        TableColumn tabCol2 = licence_columns.getColumn(2);
        tabCol2.setHeaderValue("Дата");

        licenceInfoPanel.add(new JScrollPane(licence_table));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DefaultTableModel drivingLicence_model = new DefaultTableModel(13,2);
        DefaultTableCellRenderer drivingLicenceRendered = new DefaultTableCellRenderer();
        drivingLicenceRendered.setHorizontalAlignment(JLabel.CENTER);

        JTable drivingLicence_table = new JTable(drivingLicence_model);
        drivingLicence_table.setBorder(new LineBorder(Color.BLACK));
        drivingLicence_table.setBackground(Color.WHITE);
        drivingLicence_table.setRowHeight(20);

        JTableHeader drivingLicence_header = drivingLicence_table.getTableHeader();
        drivingLicence_header.setBorder(new LineBorder(Color.BLACK));
        drivingLicence_header.setBackground(Color.WHITE);

        TableColumnModel drivingLicence_columns = drivingLicence_header.getColumnModel();

        TableColumn drivingCol0 = drivingLicence_columns.getColumn(0);
        drivingCol0.setHeaderValue("Категория");
        drivingCol0.setCellRenderer(drivingLicenceRendered);
        drivingCol0.setPreferredWidth(100);

        String[] drivingCategories_list = new String[]{"A","A1","B","B1","C","C1","D","D1","BE","CE","C1E","DE","D1E"};
        for (int i = 0; i < drivingLicence_table.getRowCount(); i++){
            drivingLicence_table.setValueAt(drivingCategories_list[i],i,0);
        }

        driverLicenceInfo_panel.add(new JScrollPane(drivingLicence_table));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Панель Управления
        JPanel buttons_panel = new JPanel(new GridLayout());
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20, 670, 450, 30);
        buttons_panel.setLayout(new GridLayout(1, 5));
        buttons_panel.setBorder(new TitledBorder(new LineBorder(Color.orange)));
        this.add(buttons_panel);

        delete_button = new JButton("Удалить");
        delete_button.setBackground(Color.WHITE);
        delete_button.setForeground(Color.RED);
        delete_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        delete_button.setEnabled(false);
        buttons_panel.add(delete_button);

        edit_button = new JButton("Изменить");
        edit_button.setBackground(Color.WHITE);
        edit_button.setForeground(Color.BLUE);
        edit_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        edit_button.setEnabled(false);
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editFields();
                editUser = true;
            }
        });

        add_button = new JButton("Создать");
        add_button.setBackground(Color.GREEN);
        add_button.setForeground(Color.BLACK);
        add_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        buttons_panel.add(add_button);
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFields();
                editUser = false;
            }
        });

        save_button = new JButton("Сохранить");
        save_button.setBackground(Color.WHITE);
        save_button.setForeground(Color.BLACK);
        save_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        save_button.setEnabled(false);
        buttons_panel.add(save_button);
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableID_text.getText().equals("") || nameRus_text.getText().equals("")){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите данные сотрудника");
                } else {
                    if (editUser){
                        String updateQuery =
                                "UPDATE dbo.Employees SET RusTitle = '" + positionRus_box.getSelectedItem() +
                                        "', LastName = '" + nameRus_text.getText() +
                                        "', Shift = " + stringToNull(shiftRus_box) +
                                        ", Department = '" + setDepartmentName(departmentRus_box) +
                                        "', ReportsTo = '" + selectSupervisorID(supervisor_box) +
                                        "', Terminated = " + setTerminatedStatus(terminatedStatus_box) +
                                        " WHERE EmployeeID = " + tableID_text.getText();

                        try{
                            System.out.println(updateQuery);
                            PreparedStatement updateEmployee = MineOperations.conn.prepareStatement(updateQuery);
                            updateEmployee.executeUpdate();
                            JOptionPane.showMessageDialog(MineOperations.cardPane, "Информация о сотруднике изменена успешна");
                            clearFields();
                        }catch (SQLException ex){
                            ex.printStackTrace();
                            System.err.println("Got an exception! ");
                            System.err.println(ex.getMessage());
                        }

                    } else {
                        final String checkEmployee_query = "SELECT * FROM dbo.Employees WHERE EmployeeID = " + tableID_text.getText();
                        try {
                            final Statement checkEmployee_st = MineOperations.conn.createStatement();
                            final ResultSet employeeExist_result = checkEmployee_st.executeQuery(checkEmployee_query);
                            if (!employeeExist_result.next())
                            {
                                String insert_query = "INSERT INTO dbo.Employees (EmployeeID, LastName, FirstName, ReportsTo, Shift, " +
                                        "Terminated, Transfered, Driverschk, LightTtruck, MineResc, CraneTr, " +
                                        "Department, SafteyOrin, RusTitle) "+
                                        "VALUES ('" + tableID_text.getText() + "', " +
                                        "'" + nameRus_text.getText() + "', " +
                                        "'" + nameRus_text.getText() + "', " +
                                        selectSupervisorID(supervisor_box) + ", " +
                                        "" + stringToNull(shiftRus_box) + ", " +
                                        setTerminatedStatus(terminatedStatus_box) +
                                        ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " +
                                        "" + setDepartmentName(departmentRus_box) + ", " +
                                        "'" + lastOr_text.getText() + "', " +
                                        "'" + positionRus_box.getSelectedItem() + "')";
                                System.out.println(insert_query);
                                PreparedStatement insertEmployee = MineOperations.conn.prepareStatement(insert_query);
                                JOptionPane.showMessageDialog(MineOperations.cardPane,"Сотрудник успешно добавлен");
                                insertEmployee.executeQuery();
                                clearFields();
                            } else {
                                do {
                                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Табельный номер: " +
                                    tableID_text.getText() + " уже занят. Пожалуйста введите другой");
                                } while (employeeExist_result.next());
                            }
                        } catch (SQLException ignored) {
                        }
                    }
                }
            }
        });

        cancel_button = new JButton("Отмена");
        cancel_button.setBackground(Color.RED);
        cancel_button.setForeground(Color.WHITE);
        cancel_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        buttons_panel.add(cancel_button);
        cancel_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.RED);
        exit_button.setForeground(Color.WHITE);
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Enter Data");
                clearFields();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }

    private String checkNullVariable(String inputString){
        String outPutString = "Нет данных";
        return Objects.requireNonNullElse(inputString, outPutString);
    }

    private JComboBox enableComboText(JComboBox inputCombobox){
        inputCombobox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setForeground(Color.BLACK);
                super.paint(g);
            }
        });
        return inputCombobox;
    }

    private JComboBox disableComboText(JComboBox inputCombobox){
        inputCombobox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setForeground(Color.LIGHT_GRAY);
                super.paint(g);
            }
        });
        return inputCombobox;
    }

    private void clearFields(){

        search_button.setEnabled(true);
        add_button.setEnabled(true);

        save_button.setEnabled(false);
        edit_button.setEnabled(false);

        tableID_label.setForeground(Color.RED);
        tableID_text.setText("");

        nameRus_text.setEnabled(false);
        nameRus_text.setText("");

        departmentRus_box.setEnabled(false);
        disableComboText(departmentRus_box).setSelectedIndex(0);

        supervisor_box.setEnabled(false);
        disableComboText(supervisor_box).setSelectedItem(0);

        positionRus_box.setEnabled(false);
        disableComboText(positionRus_box).setSelectedIndex(0);

        shiftRus_box.setEnabled(false);
        disableComboText(shiftRus_box).setSelectedIndex(0);

        terminatedStatus_box.setEnabled(false);
        disableComboText(terminatedStatus_box).setSelectedItem(0);

        lastOr_text.setEnabled(false);
        lastOr_text.setText("");
    }

    private void enableFields(){

        edit_button.setEnabled(false);
        delete_button.setEnabled(false);
        add_button.setEnabled(false);

        save_button.setEnabled(true);

        tableID_text.setText("");

        nameRus_text.setEnabled(true);
        nameRus_text.setText("");

        enableComboText(departmentRus_box).setEnabled(true);
        enableComboText(positionRus_box).setEnabled(true);
        enableComboText(shiftRus_box).setEnabled(true);
        enableComboText(supervisor_box).setEnabled(true);
        enableComboText(terminatedStatus_box).setEnabled(true);

        lastOr_text.setEnabled(true);
        lastOr_text.setText("");

        search_button.setEnabled(false);
        tableID_label.setForeground(Color.BLACK);
    }

    private void editFields(){

        tableID_label.setForeground(Color.BLACK);

        search_button.setEnabled(false);
        add_button.setEnabled(false);
        edit_button.setEnabled(false);
        delete_button.setEnabled(false);
        save_button.setEnabled(true);

        nameRus_text.setEnabled(true);
        positionRus_box.setEnabled(true);
        supervisor_box.setEnabled(true);
        departmentRus_box.setEnabled(true);
        shiftRus_box.setEnabled(true);
        terminatedStatus_box.setEnabled(true);

        lastOr_text.setEnabled(true);
    }

    private String findDepartment(int departmentID){

        String departmentName = "";
        String departmentID_query = "SELECT * FROM dbo.Department WHERE DeptId = " + departmentID;

        try {

            Statement departmentName_statement = MineOperations.conn.createStatement();
            ResultSet departmentName_result = departmentName_statement.executeQuery(departmentID_query);

            while(departmentName_result.next()){
                departmentName = departmentName_result.getString("Departmant");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return departmentName;
    }

    private int setDepartmentName(JComboBox inputBox){

        String departQuery = "SELECT * FROM dbo.Department WHERE Russian = '" + (String) inputBox.getSelectedItem()+"'";
        int deptID = 0;

        try {
            Statement departmentSt = MineOperations.conn.createStatement();
            ResultSet departmentResult = departmentSt.executeQuery(departQuery);

            while(departmentResult.next())
            {
                deptID = departmentResult.getInt("DeptID");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return deptID;
    }

    private String selectSupervisorName(String reportsID){

        String supervisorName = "";
        String supervisorName_query = "SELECT * FROM dbo.Supervisors WHERE SupervisorId = '" + reportsID + "'";

        try{

            Statement supervisorName_statement = MineOperations.conn.createStatement();
            ResultSet supervisorName_result = supervisorName_statement.executeQuery(supervisorName_query);

            while(supervisorName_result.next()){
                supervisorName = supervisorName_result.getString("Russian");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return supervisorName;
    }

    private int selectSupervisorID(JComboBox inputBox){

        int supervisorID = 0;
        String supervisorID_query = "SELECT * FROM dbo.Supervisors WHERE Russian = '" + (String) inputBox.getSelectedItem() + "'";

        try {

            Statement supervisorID_statement = MineOperations.conn.createStatement();
            ResultSet supervisorID_result = supervisorID_statement.executeQuery(supervisorID_query);

            while (supervisorID_result.next()){
                supervisorID = supervisorID_result.getInt("SupervisorId");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return supervisorID;
    }

    private String stringToNull(JComboBox inputBox){
        String inputString = (String) inputBox.getSelectedItem();
        if (Objects.equals(inputString, "Нет данных")){
            return null;
        } else {
            inputString = "'" + inputString + "'";
            return inputString;
        }
    }

    private String findTerminatedStatus(int inputNum){

        String status = "";

        if (inputNum == 0){
            status = "Работает";
        } else {
            status = "Уволен";
        }

        return status;
    }

    private int setTerminatedStatus(JComboBox inputBox){

        String inputString = (String) inputBox.getSelectedItem();
        int terminatedID = 0;

        if (Objects.equals(inputString, "Уволен")){
            terminatedID = 1;
        }

        return terminatedID;
    }
}
