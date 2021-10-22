import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

public class EmployeeInfo extends JPanel {

    private final JLabel tableID_label;

    private final JTextField
            nameRus_text,
            tableID_text;

    private final JButton
            search_button,
            surnameSearch_button,
            add_button,
            save_button,
            edit_button,
            delete_button,
            cancel_button,
            select_button,
            upload_button;

    private JComboBox
            departmentRus_box,
            crewRus_box,
            positionRus_box,
            supervisor_box,
            terminatedStatus_box;

    JDatePickerImpl LastOr_dtp = null;

    private BufferedImage logo_image, profile_image;
    private JPanel photoPanel;
    private JPanel uploadPhotoPanel;
    private JPanel PhotoPathPanel;
    JPanel pobj, innerPanel;
    private JTextField  photoPath;

    private JTable truckLicence_table, drivingLicence_table;

    private boolean editUser;
    JFileChooser fileChooser;

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
        searchEmployee_panel.setBounds(20, 120, 500, 50);
        this.add(searchEmployee_panel);

        JPanel tableID_panel = new JPanel();
        tableID_label = new JLabel(" Табельный номер:  ");
        tableID_label.setForeground(Color.RED);
        tableID_panel.setBackground(Color.WHITE);
        tableID_panel.add(tableID_label);
        searchEmployee_panel.add(tableID_label);

        surnameSearch_button = new JButton("Ф.И.О.");
        surnameSearch_button.setForeground(Color.RED);
        surnameSearch_button.setBackground(Color.WHITE);
        tableID_panel.add(surnameSearch_button);
        searchEmployee_panel.add(surnameSearch_button);
        surnameSearch_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("1234");
                SearchBySurname searchBySurname = new SearchBySurname(tableID_text.getText());
                searchBySurname.pack();
                searchBySurname.setVisible(true);
            }
        });

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
                if (tableID_text.getText().equals("") )
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите табельный номер");
                else {
                    try {
                        Statement searchStatement = MineOperations.conn.createStatement();
                        String query_text =
                                "SELECT * FROM dbo.Employees WHERE EmployeeID = " + tableID_text.getText();
                        ResultSet searchResults = searchStatement.executeQuery(query_text);

                        if (!searchResults.next())
                            JOptionPane.showMessageDialog(MineOperations.cardPane, "Сотрудник не найден!");
                        else {
                            String employeeRusName_string = searchResults.getString("FullNameRu");
                            nameRus_text.setText(employeeRusName_string);

                            String supervisor_string = findSupervisorName(searchResults.getInt("SupervisorId"));
                            enableComboText(supervisor_box).setSelectedItem((supervisor_string));

                            String positionRus_string = findJobName(searchResults.getInt("JobTitleId"));
                            enableComboText(positionRus_box).setSelectedItem(positionRus_string);

                            String departmentID_string = findDepartment(searchResults.getInt("DepartmentId"));
                            enableComboText(departmentRus_box).setSelectedItem(departmentID_string);

                            String terminatedStatus_string = findTerminatedStatus(searchResults.getInt("Terminated"));
                            enableComboText(terminatedStatus_box).setSelectedItem(terminatedStatus_string);

                            String crewID_string = findCrew(searchResults.getInt("CrewId"));
                            enableComboText(crewRus_box).setSelectedItem(crewID_string);

                            Date kumtor_A_date = searchResults.getDate("kumtor_A");
                            truckLicence_table.setValueAt(kumtor_A_date, 0, 1);

                            Date kumtor_B_date = searchResults.getDate("kumtor_B");
                            truckLicence_table.setValueAt(kumtor_B_date,1,1);

                            Date kumtor_V_date = searchResults.getDate("kumtor_V");
                            truckLicence_table.setValueAt(kumtor_V_date, 2, 1);

                            Date kumtor_G_date = searchResults.getDate("kumtor_G");
                            truckLicence_table.setValueAt(kumtor_G_date, 3, 1);

                            Date kumtor_D_date = searchResults.getDate("kumtor_D");
                            truckLicence_table.setValueAt(kumtor_D_date, 4, 1);

                            Date kumtor_E_date = searchResults.getDate("kumtor_E");
                            truckLicence_table.setValueAt(kumtor_E_date, 5, 1);

                            Date kumtor_E1_date = searchResults.getDate("kumtor_E1");
                            truckLicence_table.setValueAt(kumtor_E1_date, 6, 1);

                            Date gos_A_date = searchResults.getDate("gos_A");
                            drivingLicence_table.setValueAt(gos_A_date,0,1);

                            Date gos_A1_date = searchResults.getDate("gos_A1");
                            drivingLicence_table.setValueAt(gos_A1_date,1,1);

                            Date gos_B_date = searchResults.getDate("gos_B");
                            drivingLicence_table.setValueAt(gos_B_date,2,1);

                            Date gos_B1_date = searchResults.getDate("gos_B1");
                            drivingLicence_table.setValueAt(gos_B1_date,3,1);

                            Date gos_C_date = searchResults.getDate("gos_C");
                            drivingLicence_table.setValueAt(gos_C_date,4,1);

                            Date gos_C1_date = searchResults.getDate("gos_C1");
                            drivingLicence_table.setValueAt(gos_C1_date,5,1);

                            Date gos_D_date = searchResults.getDate("gos_D");
                            drivingLicence_table.setValueAt(gos_D_date,6,1);

                            Date gos_D1_date = searchResults.getDate("gos_D1");
                            drivingLicence_table.setValueAt(gos_D1_date,7,1);

                            Date gos_BE_date = searchResults.getDate("gos_BE");
                            drivingLicence_table.setValueAt(gos_BE_date,8,1);

                            Date gos_CE_date = searchResults.getDate("gos_CE");
                            drivingLicence_table.setValueAt(gos_CE_date,9,1);

                            Date gos_C1E_date = searchResults.getDate("gos_C1E");
                            drivingLicence_table.setValueAt(gos_C1E_date,10,1);

                            Date gos_DE_date = searchResults.getDate("gos_DE");
                            drivingLicence_table.setValueAt(gos_DE_date,11,1);

                            Date gos_D1E_date = searchResults.getDate("gos_D1E");
                            drivingLicence_table.setValueAt(gos_D1E_date,12,1);


                            //Downloading Photo
                            if (searchResults.getBlob("Photo") != null) {
                                InputStream is = null;
                                try {
                                    Blob aBlob = searchResults.getBlob("Photo");
                                    byte[] imageByte = aBlob.getBytes(1, (int) aBlob.length());
                                    is = new ByteArrayInputStream(imageByte);
                                } catch (NullPointerException ex) {
                                    System.out.println(ex.getMessage());
                                    System.out.print("Caught the NullPointerException");
                                }

                                BufferedImage imagef = null;
                                try {
                                    imagef = ImageIO.read(is);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                } catch (NullPointerException ex) {
                                    ex.printStackTrace();
                                }

                                JLabel label = new JLabel(new ImageIcon(imagef));

                                photoPanel.removeAll();
                                photoPanel.add(label);
                                revalidate();
                                repaint();
                            } else {
                                photoPanel.removeAll();
                                revalidate();
                                repaint();
                            }

                            String lastOr_string = searchResults.getString("SafetyOrientation");
                            LastOr_dtp.getJFormattedTextField().setText(lastOr_string);
                            edit_button.setEnabled(true);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();

                    }
                    setVisible(true);
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel employeeInfo_panel = new JPanel();
        employeeInfo_panel.setBackground(Color.white);
        employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
        employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Персональная Информация"));
        employeeInfo_panel.setBounds(20, 175, 500, 210);
        this.add(employeeInfo_panel);

        JPanel infoLabels = new JPanel();
        JPanel inputPanel = new JPanel();

        infoLabels.setLayout(new BoxLayout(infoLabels, BoxLayout.Y_AXIS));
        infoLabels.setBackground(Color.WHITE);
        employeeInfo_panel.add(infoLabels);

        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        employeeInfo_panel.add(inputPanel);

        photoPanel = new JPanel();
        photoPanel.setBackground(Color.WHITE);
        photoPanel.setBounds(530, 120, 210, 230);
        photoPanel.setLayout(new BorderLayout());
        photoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Фото"));
        this.add(photoPanel);

        uploadPhotoPanel = new JPanel(new GridLayout());
        uploadPhotoPanel.setBackground(Color.WHITE);
        uploadPhotoPanel.setBounds(530, 350, 210, 30);
        uploadPhotoPanel.setLayout(new GridLayout(1,2));
        uploadPhotoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange)));
        this.add(uploadPhotoPanel);

        PhotoPathPanel = new JPanel();
        PhotoPathPanel = new JPanel();
        PhotoPathPanel.setBackground(Color.WHITE);
        PhotoPathPanel.setBounds(705, 350, 120, 30);
        PhotoPathPanel.setLayout(new BorderLayout());
        PhotoPathPanel.setBorder(new TitledBorder(new LineBorder(Color.orange)));
        this.add(PhotoPathPanel);

        JPanel driverLicenceInfo_panel = new JPanel();
        driverLicenceInfo_panel.setBackground(Color.WHITE);
        driverLicenceInfo_panel.setBounds(340, 390, 305, 200);
        driverLicenceInfo_panel.setLayout(new BorderLayout());
        driverLicenceInfo_panel.setBorder(new LineBorder(Color.orange));
        this.add(driverLicenceInfo_panel);

        JPanel licenceInfoPanel = new JPanel();
        licenceInfoPanel.setBackground(Color.WHITE);
        licenceInfoPanel.setBounds(20, 390,315,200);
        licenceInfoPanel.setLayout(new BorderLayout());
        licenceInfoPanel.setBorder(new LineBorder(Color.orange));
        this.add(licenceInfoPanel);

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

            String positionRus_query = "SELECT * FROM dbo.JobTitles";
            Statement positionRus_statement = MineOperations.conn.createStatement();
            ResultSet positionRus_result = positionRus_statement.executeQuery(positionRus_query);

            while(positionRus_result.next())
            {
                String addPositionRusItem = positionRus_result.getString("JobTitleNameRu");
                if (addPositionRusItem != null){
                    positionRus_box.addItem(addPositionRusItem);
                } else continue;
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
                String addReportsTo = reportsTo_result.getString("SupervisorNameRu");
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
            String departmentRus_query = "SELECT * FROM dbo.Departments";
            Statement departmentRus_statement = MineOperations.conn.createStatement();
            ResultSet departmentRus_result = departmentRus_statement.executeQuery(departmentRus_query);

            while(departmentRus_result.next()){
                String addDepartmentRus = departmentRus_result.getString("DepartmentNameRu");
                int isActive = departmentRus_result.getInt("IsActive");

                if (isActive == 1){
                    departmentRus_box.addItem(addDepartmentRus);
                } else continue;
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

        crewRus_box = new JComboBox();
        crewRus_box.setBackground(Color.WHITE);
        crewRus_box.setEnabled(false);
        try {

            String crews_query = "SELECT * FROM dbo.Crews";
            Statement crews_statement = MineOperations.conn.createStatement();
            ResultSet crews_results = crews_statement.executeQuery(crews_query);

            while (crews_results.next()){
                String addCrew = crews_results.getString("CrewName");
                int isActive = crews_results.getInt("IsActive");

                if (isActive == 1){
                    crewRus_box.addItem(addCrew);
                } else continue;
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        shiftEng_panel.add(crewRus_box);
        inputPanel.add(crewRus_box);

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
        infoLabels.add(lastOr_panel);


        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        LastOr_dtp = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        LastOr_dtp.setEnabled(false);
        lastOr_panel.add(LastOr_dtp);
        inputPanel.add(LastOr_dtp);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DefaultTableModel truckLicence_model = new DefaultTableModel(7,2);
        DefaultTableCellRenderer centerReder = new DefaultTableCellRenderer();
        centerReder.setHorizontalAlignment(JLabel.CENTER);

        truckLicence_table = new JTable(truckLicence_model);
        truckLicence_table.setBorder(new LineBorder(Color.BLACK));
        truckLicence_table.setEnabled(false);
        truckLicence_table.setBackground(Color.WHITE);
        truckLicence_table.setRowHeight(25);

        JTableHeader header= truckLicence_table.getTableHeader();
        header.setBorder(new LineBorder(Color.BLACK));
        header.setBackground(Color.WHITE);
        header.setFont(new Font("Helvetica", Font.BOLD,12));

        TableColumnModel truckLicence_columns = header.getColumnModel();

        TableColumn tabCol0 = truckLicence_columns.getColumn(0);
        tabCol0.setHeaderValue("Категория");
        tabCol0.setCellRenderer(centerReder);
        tabCol0.setPreferredWidth(10);

        String[] categories_list = new String[]{"А","Б","В","Г","Д","Е","Е1"};
        for (int i = 0; i < truckLicence_table.getRowCount(); i++){
            truckLicence_table.setValueAt(categories_list[i],i,0);
        }

        TableColumn tabCol1 = truckLicence_columns.getColumn(1);
        tabCol1.setHeaderValue("Дата");
        tabCol1.setCellRenderer(centerReder);
        truckLicence_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && truckLicence_table.isEnabled()){

                    JFrame tempDateFrame = new JFrame();

                    JPanel dateCellPanel = new JPanel();
                    dateCellPanel.setBounds(0,0,250,200);
                    dateCellPanel.setBackground(Color.WHITE);
                    tempDateFrame.add(dateCellPanel);

                    JPanel buttonsPanel = new JPanel();
                    buttonsPanel.setBounds(0,200,250,30);
                    buttonsPanel.setBackground(Color.WHITE);
                    buttonsPanel.setLayout(new GridLayout(1,2));
                    tempDateFrame.add(buttonsPanel);

                    UtilDateModel cellModel = new UtilDateModel();
                    Properties pr = new Properties();
                    pr.put("text.today", "Today");
                    pr.put("text.month", "Month");
                    pr.put("text.year", "Year");

                    JDatePanelImpl cellDatePicker = new JDatePanelImpl(cellModel,pr);
                    dateCellPanel.add(cellDatePicker);

                    JButton okButton = new JButton("OK");
                    okButton.setBackground(Color.WHITE);
                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Date selectedDate;
                            selectedDate = cellModel.getValue();

                            DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
                            String convertedDate = dateformatter.format(selectedDate);

                            System.out.println(convertedDate);
                            truckLicence_table.setValueAt(convertedDate,truckLicence_table.getSelectedRow(),1);
                            System.out.println(truckLicence_table.getValueAt(truckLicence_table.getSelectedRow(), 1));
                            tempDateFrame.dispatchEvent(new WindowEvent(tempDateFrame, WindowEvent.WINDOW_CLOSING));

                        }
                    });
                    buttonsPanel.add(okButton);

                    JButton clearButton =new JButton("Стереть");
                    clearButton.setBackground(Color.WHITE);
                    clearButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            truckLicence_table.setValueAt(null,truckLicence_table.getSelectedRow(),1);
                            tempDateFrame.dispatchEvent(new WindowEvent(tempDateFrame, WindowEvent.WINDOW_CLOSING));

                        }
                    });
                    buttonsPanel.add(clearButton);


                    tempDateFrame.setPreferredSize(new Dimension(260,270));
                    tempDateFrame.setLocation(new Point(300,300));
                    tempDateFrame.setLayout(null);
                    tempDateFrame.pack();
                    tempDateFrame.setVisible(true);
                }
            }
        });

        JScrollPane licence_scrollPane = new JScrollPane(truckLicence_table);
        licence_scrollPane.setBackground(Color.WHITE);
        licenceInfoPanel.add(licence_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DefaultTableModel drivingLicence_model = new DefaultTableModel(13,2);
        DefaultTableCellRenderer drivingLicenceRendered = new DefaultTableCellRenderer();
        drivingLicenceRendered.setHorizontalAlignment(JLabel.CENTER);

        drivingLicence_table = new JTable(drivingLicence_model);
        drivingLicence_table.setBorder(new LineBorder(Color.BLACK));
        drivingLicence_table.setEnabled(false);
        drivingLicence_table.setBackground(Color.WHITE);
        drivingLicence_table.setRowHeight(20);

        JTableHeader drivingLicence_header = drivingLicence_table.getTableHeader();
        drivingLicence_header.setBorder(new LineBorder(Color.BLACK));
        drivingLicence_header.setBackground(Color.WHITE);
        drivingLicence_header.setFont(new Font("Helvetica", Font.BOLD,12));

        TableColumnModel drivingLicence_columns = drivingLicence_header.getColumnModel();

        TableColumn drivingCol0 = drivingLicence_columns.getColumn(0);
        drivingCol0.setHeaderValue("Категория");
        drivingCol0.setCellRenderer(drivingLicenceRendered);
        drivingCol0.setPreferredWidth(100);

        String[] drivingCategories_list = new String[]{"A","A1","B","B1","C","C1","D","D1","BE","CE","C1E","DE","D1E"};
        for (int i = 0; i < drivingLicence_table.getRowCount(); i++){
            drivingLicence_table.setValueAt(drivingCategories_list[i],i,0);
        }

        TableColumn drivingCol1 = drivingLicence_columns.getColumn(1);
        drivingCol1.setHeaderValue("Дата");
        drivingCol1.setCellRenderer(drivingLicenceRendered);
        drivingCol1.setPreferredWidth(150);

        drivingLicence_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && drivingLicence_table.isEnabled()){

                    JFrame tempDateFrame = new JFrame();

                    JPanel dateCellPanel = new JPanel();
                    dateCellPanel.setBounds(0,0,250,200);
                    dateCellPanel.setBackground(Color.WHITE);
                    tempDateFrame.add(dateCellPanel);

                    JPanel buttonsPanel = new JPanel();
                    buttonsPanel.setBounds(0,200,250,30);
                    buttonsPanel.setBackground(Color.WHITE);
                    buttonsPanel.setLayout(new GridLayout(1,2));
                    tempDateFrame.add(buttonsPanel);

                    UtilDateModel cellModel = new UtilDateModel();
                    Properties pr = new Properties();
                    pr.put("text.today", "Today");
                    pr.put("text.month", "Month");
                    pr.put("text.year", "Year");

                    JDatePanelImpl cellDatePicker = new JDatePanelImpl(cellModel,pr);
                    dateCellPanel.add(cellDatePicker);

                    JButton okButton = new JButton("OK");
                    okButton.setBackground(Color.WHITE);
                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Date selectedDate;
                            selectedDate = cellModel.getValue();

                            DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
                            String convertedDate = dateformatter.format(selectedDate);

                            System.out.println(convertedDate);
                            drivingLicence_table.setValueAt(convertedDate,drivingLicence_table.getSelectedRow(),1);
                            System.out.println(drivingLicence_table.getValueAt(drivingLicence_table.getSelectedRow(), 1));
                            tempDateFrame.dispatchEvent(new WindowEvent(tempDateFrame, WindowEvent.WINDOW_CLOSING));

                        }
                    });
                    buttonsPanel.add(okButton);

                    JButton clearButton =new JButton("Стереть");
                    clearButton.setBackground(Color.WHITE);
                    clearButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            drivingLicence_table.setValueAt(null,drivingLicence_table.getSelectedRow(),1);
                            tempDateFrame.dispatchEvent(new WindowEvent(tempDateFrame, WindowEvent.WINDOW_CLOSING));

                        }
                    });
                    buttonsPanel.add(clearButton);

                    tempDateFrame.setPreferredSize(new Dimension(260,270));
                    tempDateFrame.setLocation(new Point(300,300));
                    tempDateFrame.setLayout(null);
                    tempDateFrame.pack();
                    tempDateFrame.setVisible(true);
                }
            }
        });

        JScrollPane drivingLicence_scrollPane = new JScrollPane(drivingLicence_table);
        drivingLicence_scrollPane.setBackground(Color.WHITE);
        driverLicenceInfo_panel.add(drivingLicence_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Панель Управления
        JPanel buttons_panel = new JPanel(new GridLayout());
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20, 600, 500, 30);
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
                                "UPDATE dbo.Employees SET FullNameRu = N'" + nameRus_text.getText() +
                                        "', JobTitleId = " + setJobId(positionRus_box) +
                                        ", CrewId = " + setCrewID(crewRus_box) +
                                        ", DepartmentId = " + setDepartmentID(departmentRus_box) +
                                        ", SupervisorId = " + setSupervisorID(supervisor_box) +
                                        ", Terminated = " + setTerminatedStatus(terminatedStatus_box) +
                                        ", kumtor_A = " + checkTablesContent(truckLicence_table.getValueAt(0,1)) +
                                        ", kumtor_B = " + checkTablesContent(truckLicence_table.getValueAt(1,1)) +
                                        ", kumtor_V = " + checkTablesContent(truckLicence_table.getValueAt(2,1)) +
                                        ", kumtor_G = " + checkTablesContent(truckLicence_table.getValueAt(3,1)) +
                                        ", kumtor_D = " + checkTablesContent(truckLicence_table.getValueAt(4,1)) +
                                        ", kumtor_E = " + checkTablesContent(truckLicence_table.getValueAt(5,1)) +
                                        ", kumtor_E1 = " + checkTablesContent(truckLicence_table.getValueAt(6,1)) +
                                        ", gos_A = " + checkTablesContent(drivingLicence_table.getValueAt(0,1)) +
                                        ", gos_A1 = " + checkTablesContent(drivingLicence_table.getValueAt(1,1)) +
                                        ", gos_B = " + checkTablesContent(drivingLicence_table.getValueAt(2,1)) +
                                        ", gos_B1 = " + checkTablesContent(drivingLicence_table.getValueAt(3,1)) +
                                        ", gos_C = " + checkTablesContent(drivingLicence_table.getValueAt(4,1)) +
                                        ", gos_C1 = " + checkTablesContent(drivingLicence_table.getValueAt(5,1)) +
                                        ", gos_D = " + checkTablesContent(drivingLicence_table.getValueAt(6,1)) +
                                        ", gos_D1 = " + checkTablesContent(drivingLicence_table.getValueAt(7,1)) +
                                        ", gos_BE = " + checkTablesContent(drivingLicence_table.getValueAt(8,1)) +
                                        ", gos_CE = " + checkTablesContent(drivingLicence_table.getValueAt(9,1)) +
                                        ", gos_C1E = " + checkTablesContent(drivingLicence_table.getValueAt(10,1)) +
                                        ", gos_DE = " + checkTablesContent(drivingLicence_table.getValueAt(11,1)) +
                                        ", gos_D1E = " + checkTablesContent(drivingLicence_table.getValueAt(12,1)) +
                                        " WHERE EmployeeID = " + tableID_text.getText();

                        System.out.println(updateQuery);
                        search_button.setEnabled(true);
                        clearFields();

                        try{
                            PreparedStatement updateEmployee = MineOperations.conn.prepareStatement(updateQuery);
                            updateEmployee.executeUpdate();
                            JOptionPane.showMessageDialog(MineOperations.cardPane, "Информация о сотруднике изменена успешна");
                        }catch (SQLException ex){
                            ex.printStackTrace();
                        }

                    } else {
                        final String checkEmployee_query = "SELECT * FROM dbo.Employees WHERE EmployeeID = " + tableID_text.getText();
                        try {
                            final Statement checkEmployee_st = MineOperations.conn.createStatement();
                            final ResultSet employeeExist_result = checkEmployee_st.executeQuery(checkEmployee_query);
                            if (!employeeExist_result.next())
                            {
                                String insert_query = "INSERT INTO dbo.Employees " +
                                        "(EmployeeID, FullName, FullNameRu, JobTitleId, SupervisorId, CrewId, DepartmentId, Terminated, " +
                                        "kumtor_A, kumtor_B, kumtor_V, kumtor_G, kumtor_D, kumtor_E, kumtor_E1, " +
                                        "gos_A, gos_A1, gos_B, gos_B1, gos_C, gos_C1, gos_D, gos_D1, gos_BE, gos_CE, gos_C1E, gos_DE, gos_D1E ) " +
                                        "VALUES (" +
                                        tableID_text.getText() + ", N'" +
                                        nameRus_text.getText() + "', N'" +
                                        nameRus_text.getText() + "', " +
                                        setJobId(positionRus_box) + ", " +
                                        setSupervisorID(supervisor_box) + ", " +
                                        setCrewID(crewRus_box) + ", " +
                                        setDepartmentID(departmentRus_box) + ", " +
                                        setTerminatedStatus(terminatedStatus_box) + ", " +
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

        photoPath = new JTextField();
        //photoPath.setEnabled(false);
        photoPath.setForeground(Color.BLACK);
        photoPath.setDisabledTextColor(Color.BLACK);
        PhotoPathPanel.add(photoPath);
        //inputPanel.add(photoPath);

        select_button = new JButton("Выбрать фото");
        select_button.setForeground(Color.BLACK);
        select_button.setBackground(Color.WHITE);
        select_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        uploadPhotoPanel.add(select_button);
        select_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("ыудддудудсе");
                fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif", "gif", "bmp"));
                int returnVal = fileChooser.showOpenDialog(pobj);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getName();
                    String extension = fileName.substring(fileName.lastIndexOf("."));
                    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")
                            || extension.equalsIgnoreCase(".bmp") || extension.equalsIgnoreCase(".tif")
                            || extension.equalsIgnoreCase(".gif")) {
                        System.out.println("Zawel");
                        System.out.println(fileChooser.getSelectedFile().getPath());
                        photoPath.setText(fileChooser.getSelectedFile().getPath());
                    } else {
                        JOptionPane.showMessageDialog(pobj, "Kindly Select Image File Only",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    photoPath.setText("No File Uploaded");
                }
            }
        });

        upload_button = new JButton("Загрузить");
        upload_button.setForeground(Color.BLACK);
        upload_button.setBackground(Color.WHITE);
        upload_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        uploadPhotoPanel.add(upload_button);
        upload_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ЗАгр");
                if (photoPath.getText().equals("No File Uploaded")) {
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Файл не выбран");
                }else
                if (tableID_text.getText().equals("") || nameRus_text.getText().equals("")){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите данные сотрудника");
                }else
                if (!isUserExist(tableID_text.getText())){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Табельный номер " + tableID_text.getText() + " не существует!" );
                }
                else {
                    if (editUser){
                        byte[] rawBytes = null;
                        FileInputStream fis = null;

                        File fileObj = new File(photoPath.getText());
                        try {
                            fis = new FileInputStream(fileObj);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        String updateQuery =
                                "UPDATE dbo.Employees SET Photo = ? WHERE EmployeeID = " + tableID_text.getText();

                        try{
                            PreparedStatement updateEmployee = MineOperations.conn.prepareStatement(updateQuery);
                            int imageLength = Integer.parseInt(String.valueOf(fileObj.length()));
                            rawBytes = new byte[imageLength];
                            fis.read(rawBytes, 0, imageLength);
                            //st.setBinaryStream(4, (InputStream) fis, imageLength);
                            updateEmployee.setBytes(1, rawBytes);
                            updateEmployee.executeUpdate();
                            int count = updateEmployee.executeUpdate();
                            if (count > 0) {
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Фото загруженно успешно!");
                            } else {
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Ошибка загрузки фотографии!");
                            }
                        }catch (SQLException | IOException ex){
                            ex.printStackTrace();
                        }

                    }
                }
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
                MineOperations.card.show(MineOperations.cardPane,"Home Page");
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
        System.out.println("dddd" + inputString);
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
        surnameSearch_button.setEnabled(true);

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

        crewRus_box.setEnabled(false);
        disableComboText(crewRus_box).setSelectedIndex(0);

        terminatedStatus_box.setEnabled(false);
        disableComboText(terminatedStatus_box).setSelectedItem(0);

        drivingLicence_table.setEnabled(false);
        for (int i = 0; i < drivingLicence_table.getRowCount();i++){
            drivingLicence_table.setValueAt("",i,1);
        }

        truckLicence_table.setEnabled(false);
        for (int i = 0; i < truckLicence_table.getRowCount();i++){
            truckLicence_table.setValueAt("",i,1);
        }

        LastOr_dtp.setEnabled(false);
        LastOr_dtp.getJFormattedTextField().setText("");
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
        enableComboText(crewRus_box).setEnabled(true);
        enableComboText(supervisor_box).setEnabled(true);
        enableComboText(terminatedStatus_box).setEnabled(true);

        truckLicence_table.setEnabled(true);
        drivingLicence_table.setEnabled(true);

        search_button.setEnabled(false);
        surnameSearch_button.setEnabled(false);
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
        crewRus_box.setEnabled(true);
        terminatedStatus_box.setEnabled(true);

        drivingLicence_table.setEnabled(true);
        truckLicence_table.setEnabled(true);

        LastOr_dtp.setEnabled(true);
    }

    private String findDepartment(int departmentID){

        String departmentName = "";
        String departmentID_query = "SELECT * FROM dbo.Departments WHERE DepartmentID = " + departmentID;

        try {

            Statement departmentName_statement = MineOperations.conn.createStatement();
            ResultSet departmentName_result = departmentName_statement.executeQuery(departmentID_query);

            while(departmentName_result.next()){
                departmentName = departmentName_result.getString("DepartmentNameRu");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return departmentName;
    }

    private int setDepartmentID(JComboBox inputBox){

        int deptID = 0;
        String departQuery = "SELECT * FROM dbo.Departments WHERE DepartmentNameRu = N'" + (String) inputBox.getSelectedItem()+"'";


        try {
            Statement departmentSt = MineOperations.conn.createStatement();
            ResultSet departmentResult = departmentSt.executeQuery(departQuery);

            while(departmentResult.next())
            {
                deptID = departmentResult.getInt("DepartmentId");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return deptID;
    }

    private String findCrew(int crewID){
        String crewName = "";
        String crewID_query = "SELECT * FROM dbo.Crews WHERE CrewId = " + crewID;

        try {

            Statement crewName_statement = MineOperations.conn.createStatement();
            ResultSet crewName_result = crewName_statement.executeQuery(crewID_query);

            while (crewName_result.next()){
                crewName = crewName_result.getString("CrewName");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return crewName;
    }

    private int setCrewID(JComboBox inputBox){

        int crewID = 0;
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

        return crewID;
    }

    private String findSupervisorName(int reportsID){

        String supervisorName = "";
        String supervisorName_query = "SELECT * FROM dbo.Supervisors WHERE SupervisorId = " + reportsID;

        try{

            Statement supervisorName_statement = MineOperations.conn.createStatement();
            ResultSet supervisorName_result = supervisorName_statement.executeQuery(supervisorName_query);

            while(supervisorName_result.next()){
                supervisorName = supervisorName_result.getString("SupervisorNameRu");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return supervisorName;
    }

    private int setSupervisorID(JComboBox inputBox){

        int supervisorID = 0;
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

        return supervisorID;
    }

    private String findJobName(int jobID){
        String jobTitle = "";
        String jobTitle_query = "SELECT * FROM dbo.JobTitles WHERE JobTitleId = '" + jobID + "'";

        try{

            Statement jobTitle_statement = MineOperations.conn.createStatement();
            ResultSet jobTitle_result = jobTitle_statement.executeQuery(jobTitle_query);

            while(jobTitle_result.next()){
                jobTitle = jobTitle_result.getString("JobTitleNameRu");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return jobTitle;
    }

    private int setJobId(JComboBox inputBox){
        int jobId = 0;

        String jobID_query = "SELECT * FROM dbo.JobTitles WHERE JobTitleNameRu = N'" + (String) inputBox.getSelectedItem() + "'";

        try {

            Statement jobID_statement = MineOperations.conn.createStatement();
            ResultSet jobID_result = jobID_statement.executeQuery(jobID_query);

            while (jobID_result.next()){
                jobId = jobID_result.getInt("JobTitleId");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return jobId;
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

    private boolean isUserExist(String userid){

        boolean  isexist = false;
        String query = "SELECT * FROM dbo.Employees WHERE EmployeeID = " + userid;

        try {
            Statement st = MineOperations.conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                isexist = true;
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return isexist;
    }

    private String checkTablesContent(Object objectInput){

        String returnString;

        if (objectInput == null){
            System.out.println( "1111");
            return null;
        } else {
            System.out.println( "2222");
            returnString = "'" + objectInput.toString() + "'";
            return returnString;
        }
    }

    private class SearchBySurname extends JFrame{

        private JTable listOfEmployees_table;

        private List<String> employeeNames_list = new ArrayList<>();
        private List<Integer> employeeID_list = new ArrayList<>();

        private JPanel pageTitlePanel, tablePanel, backgroundPanel;

        private JLabel foundEmployees_JLabel;

        private int numOfRows = 1, numOfColumns = 2;

        public SearchBySurname(String surname){

            findSurnames(surname);
            buildFrame();
            createTable();

            this.setPreferredSize(new Dimension(400, 600));
            this.setFocusableWindowState(true);
            this.setAutoRequestFocus(true);
            this.setLocation(900,40);
            this.setLayout(null);
        }

        private void buildFrame(){

            pageTitlePanel = new JPanel();
            pageTitlePanel.setBounds(0,0,400,50);
            pageTitlePanel.setBackground(Color.WHITE);
            pageTitlePanel.setVisible(true);
            this.add(pageTitlePanel);

            foundEmployees_JLabel = new JLabel("Найденные Сотрудники");
            foundEmployees_JLabel.setFont(new Font("Helvetica",Font.BOLD, 20));
            pageTitlePanel.add(foundEmployees_JLabel);

            tablePanel = new JPanel();
            tablePanel.setBackground(Color.WHITE);
            tablePanel.setBounds(25,50,350,500);
            tablePanel.setLayout(new BorderLayout());
            tablePanel.setBorder(new LineBorder(Color.BLACK));
            tablePanel.setVisible(true);
            this.add(tablePanel);

            backgroundPanel = new JPanel();
            backgroundPanel.setBounds(0,0,400,600);
            backgroundPanel.setBackground(Color.WHITE);
            this.add(backgroundPanel);
        }

        private void createTable(){

            DefaultTableModel listOfEmployees_model = new DefaultTableModel(numOfRows ,numOfColumns){
                public boolean isCellEditable(int row, int column){
                    return false;
                }
            };
            DefaultTableCellRenderer centerRederer = new DefaultTableCellRenderer();
            centerRederer.setHorizontalAlignment(JLabel.CENTER);

            listOfEmployees_table = new JTable(listOfEmployees_model);
            listOfEmployees_table.setBorder(new LineBorder(Color.BLACK));
            listOfEmployees_table.setBackground(Color.WHITE);
            listOfEmployees_table.setRowHeight(25);
            listOfEmployees_table.setVisible(true);

            JTableHeader listHeader= listOfEmployees_table.getTableHeader();
            listHeader.setBorder(new LineBorder(Color.BLACK));
            listHeader.setBackground(Color.WHITE);
            listHeader.setFont(new Font("Helvetica", Font.BOLD,12));

            TableColumnModel listOfEmployees_columns = listHeader.getColumnModel();

            TableColumn tabCol0 = listOfEmployees_columns.getColumn(0);
            tabCol0.setHeaderValue("Табельный No");
            tabCol0.setCellRenderer(centerRederer);
            tabCol0.setPreferredWidth(10);

            for (int i = 0; i < numOfRows; i++){
                listOfEmployees_table.setValueAt(employeeID_list.get(i),i,0);
            }

            TableColumn tabCol1 = listOfEmployees_columns.getColumn(1);
            tabCol1.setCellRenderer(centerRederer);
            tabCol1.setHeaderValue("Ф.И.О");

            for (int i = 0; i < numOfRows; i++){
                listOfEmployees_table.setValueAt(employeeNames_list.get(i),i,1);
            }


            listOfEmployees_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1){
                        int index1 = listOfEmployees_table.getSelectedRow();//Get the selected row
                        System.out.println(listOfEmployees_table.getValueAt(index1, 0));
                        tableID_text.setText(listOfEmployees_table.getValueAt(index1,0).toString());
                    }
                }
            });

            tablePanel.add(new JScrollPane(listOfEmployees_table));
        }

        private void findSurnames(String surname){

            if (surname == null){
                JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите фамилию или имя");
            } else {
                try{
                    Statement searchStatement = MineOperations.conn.createStatement();
                    String query_text = "SELECT FullNameRu, EmployeeID  FROM dbo.Employees " +
                            "WHERE EmployeeID is not null and FullNameRu like N'%"+surname+"%' order by FullNameRu desc";

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

                }

                numOfRows = employeeNames_list.size();
            }
        }
    }

}