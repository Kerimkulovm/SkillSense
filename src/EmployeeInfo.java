import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

public class EmployeeInfo extends JPanel{

    private final JLabel tableID_label;

    private final JTextField
            nameRus_text,
            tableID_text;

    private JButton
            search_button,
            surnameSearch_button,
            add_button,
            save_button,
            edit_button,
            savePhoto_button,
            cancel_button,
            select_button,
            upload_button,
            eraseData_button;

    public static JComboBox
            departmentRus_box,
            crewRus_box,
            positionRus_box,
            supervisor_box,
            terminatedStatus_box;

    JDatePickerImpl LastOr_dtp = null;

    private BufferedImage logo_image, bg_image;
    private JPanel photoPanel;
    private JPanel uploadPhotoPanel;
    private JPanel PhotoPathPanel;
    private JPanel pobj;
    private JTextField  photoPath;

    private JTable truckLicence_table, drivingLicence_table;

    public Action searchAction;

    private boolean editUser;
    JFileChooser fileChooser;

    private SearchBySurname searchBySurname;
    public static DatabaseQueries databaseQueries = new DatabaseQueries();

    public EmployeeInfo()
    {
        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Информация о сотрудниках</big><br />Поиск данных сотрудника</html>");
        titleEng.setBounds(160, 0, 450, 120);
        titleEng.setFont(Font.getFont("Lena"));
        titleEng.setForeground(Color.BLACK);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        searchAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableID_text.getText().equals("") )
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите табельный номер");
                else {

                    databaseQueries.queryEmployeeData(tableID_text.getText());
                    nameRus_text.setText(databaseQueries.getEmployeeName());
                    enableComboText(supervisor_box).setSelectedItem((databaseQueries.getSuperVisorName()));
                    enableComboText(positionRus_box).setSelectedItem(databaseQueries.getJobName());
                    enableComboText(departmentRus_box).setSelectedItem(databaseQueries.getDepartmentName());
                    enableComboText(terminatedStatus_box).setSelectedItem(databaseQueries.getTerminatedStatus());
                    enableComboText(crewRus_box).setSelectedItem(databaseQueries.getCrewName());

                    for (int i = 0; i < truckLicence_table.getRowCount(); i++){
                        truckLicence_table.setValueAt(databaseQueries.truckLicence_table.getValueAt(i,1), i, 1);
                    }

                    for (int i = 0; i <  drivingLicence_table.getRowCount(); i++){
                        drivingLicence_table.setValueAt(databaseQueries.drivingLicence_table.getValueAt(i,1), i, 1);
                    }

                    if (databaseQueries.getPhotoLabel() != null){
                        photoPanel.removeAll();
                        photoPanel.add(databaseQueries.getPhotoLabel());
                        revalidate();
                        repaint();
                    } else {
                        photoPanel.removeAll();
                        revalidate();
                        repaint();
                    }

                    LastOr_dtp.getJFormattedTextField().setText(databaseQueries.getLastSafetyOr());
                    edit_button.setEnabled(true);
                    setVisible(true);
                }
            }
        };
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        LineBorder line = new LineBorder(Color.GRAY, 1, true);
        JPanel searchEmployee_panel = new JPanel();
        searchEmployee_panel.setBackground(Color.white);
        searchEmployee_panel.setLayout(new BoxLayout(searchEmployee_panel, BoxLayout.X_AXIS));
        searchEmployee_panel.setBorder(new TitledBorder(line, "Поиск сотрудника"));
        searchEmployee_panel.setBounds(20, 120, 500, 50);
        this.add(searchEmployee_panel);

        JPanel tableID_panel = new JPanel();

        tableID_label = new JLabel(" Табельный номер:  ");
        tableID_label.setForeground(Color.RED);
        tableID_panel.setBackground(Color.WHITE);
        tableID_label.setFont(Font.getFont("Lena"));
        tableID_panel.add(tableID_label);
        searchEmployee_panel.add(tableID_label);

        surnameSearch_button = new JButton("Ф.И.О.");
        surnameSearch_button.setForeground(Color.RED);
        surnameSearch_button.setBackground(Color.WHITE);
        surnameSearch_button.setBorder(new RoundedBorder(10));
        surnameSearch_button.setFont(Font.getFont("Lena"));
        tableID_panel.add(surnameSearch_button);
        searchEmployee_panel.add(surnameSearch_button);
        surnameSearch_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBySurname = new SearchBySurname(tableID_text.getText());
                searchBySurname.pack();
                searchBySurname.setVisible(true);
            }
        });

        tableID_text = new JTextField();
        tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
        tableID_text.addActionListener(searchAction);
        tableID_panel.add(tableID_text);
        searchEmployee_panel.add(tableID_text);

        search_button = new JButton("Поиск");
        search_button.setForeground(Color.RED);
        search_button.setBackground(Color.WHITE);
        search_button.setBorder(new RoundedBorder(10));
        search_button.setFont(Font.getFont("Lena"));
        tableID_panel.add(search_button);
        searchEmployee_panel.add(search_button);
        search_button.addActionListener(searchAction);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel employeeInfo_panel = new JPanel();
        employeeInfo_panel.setBackground(Color.white);
        employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
        employeeInfo_panel.setBorder(new TitledBorder(line, "Персональная Информация"));
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
        photoPanel.setBorder(new LineBorder(Color.BLACK));
        photoPanel.setLayout(new BorderLayout());
        this.add(photoPanel);

        uploadPhotoPanel = new JPanel(new GridLayout());
        uploadPhotoPanel.setBackground(Color.WHITE);
        uploadPhotoPanel.setBounds(530, 350, 210, 30);
        uploadPhotoPanel.setLayout(new GridLayout(1,2));
        this.add(uploadPhotoPanel);

        PhotoPathPanel = new JPanel();
        PhotoPathPanel.setVisible(false);
        PhotoPathPanel.setBackground(Color.WHITE);
        PhotoPathPanel.setBounds(705, 350, 120, 30);
        PhotoPathPanel.setLayout(new BorderLayout());
        this.add(PhotoPathPanel);

        JPanel driverLicenceInfo_panel = new JPanel();
        driverLicenceInfo_panel.setBackground(Color.WHITE);
        driverLicenceInfo_panel.setBounds(360, 390, 380, 200);
        driverLicenceInfo_panel.setLayout(new BorderLayout());
        driverLicenceInfo_panel.setBorder(new TitledBorder("Водительское Удостоверение"));
        this.add(driverLicenceInfo_panel);

        JPanel licenceInfoPanel = new JPanel();
        licenceInfoPanel.setBackground(Color.WHITE);
        licenceInfoPanel.setBounds(20, 390,330,200);
        licenceInfoPanel.setLayout(new BorderLayout());
        licenceInfoPanel.setBorder(new TitledBorder("Удостоверение Тракториста"));
        this.add(licenceInfoPanel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel nameRus_panel = new JPanel();
        JLabel nameRus_label = new JLabel("Имя и Фамилия: ");
        nameRus_label.setFont(Font.getFont("Lena"));
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
        positionRus_label.setFont(Font.getFont("Lena"));
        positionRus_panel.add(positionRus_label);
        positionRus_panel.setBackground(Color.WHITE);
        infoLabels.add(positionRus_panel);

        positionRus_box = new JComboBox();
        positionRus_box.setBackground(Color.WHITE);
        positionRus_box.setFont(Font.getFont("Lena"));
        positionRus_box.setEnabled(false);
        positionRus_box = databaseQueries.loadJobTitlesBox(positionRus_box);
        positionRus_panel.add(positionRus_box);
        inputPanel.add(positionRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel reportsTo_panel = new JPanel();
        JLabel reportsTo_label = new JLabel("Начальник: ");
        reportsTo_label.setFont(Font.getFont("Lena"));
        reportsTo_panel.add(reportsTo_label);
        reportsTo_panel.setBackground(Color.WHITE);
        infoLabels.add(reportsTo_panel);

        supervisor_box = new JComboBox();
        supervisor_box.setBackground(Color.WHITE);
        supervisor_box.setFont(Font.getFont("Lena"));
        supervisor_box.setEnabled(false);
        supervisor_box = databaseQueries.loadSupervisorBox(supervisor_box);
        reportsTo_panel.add(supervisor_box);
        inputPanel.add(supervisor_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel departmentRus_panel = new JPanel();
        JLabel departmentRus_label = new JLabel("Отдел: ");
        departmentRus_label.setFont(Font.getFont("Lena"));
        departmentRus_panel.add(departmentRus_label);
        departmentRus_panel.setBackground(Color.WHITE);
        infoLabels.add(departmentRus_panel);

        departmentRus_box = new JComboBox();
        departmentRus_box.setBackground(Color.WHITE);
        departmentRus_box.setFont(Font.getFont("Lena"));
        departmentRus_box.setEnabled(false);
        departmentRus_box = databaseQueries.loadDepartmentsBox(departmentRus_box);

        departmentRus_panel.add(departmentRus_box);
        inputPanel.add(departmentRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel shiftEng_panel = new JPanel();
        JLabel shift_label = new JLabel("Смена: ");
        shift_label.setFont(Font.getFont("Lena"));
        shiftEng_panel.add(shift_label);
        shiftEng_panel.setBackground(Color.WHITE);
        infoLabels.add(shiftEng_panel);

        crewRus_box = new JComboBox();
        crewRus_box.setBackground(Color.WHITE);
        crewRus_box.setFont(Font.getFont("Lena"));
        crewRus_box.setEnabled(false);
        crewRus_box = databaseQueries.loadCrewBox(crewRus_box);

        shiftEng_panel.add(crewRus_box);
        inputPanel.add(crewRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel terminated_panel = new JPanel();
        JLabel terminated_label = new JLabel("Статус: ");
        terminated_label.setFont(Font.getFont("Lena"));
        terminated_panel.add(terminated_label);
        terminated_panel.setBackground(Color.WHITE);
        infoLabels.add(terminated_panel);

        String[] status = new String[]{"Работает","Уволен"};
        terminatedStatus_box = new JComboBox(status);
        terminatedStatus_box.setFont(Font.getFont("Lena"));
        terminatedStatus_box.setBackground(Color.WHITE);
        terminatedStatus_box.setEnabled(false);
        terminated_panel.add(terminatedStatus_box);
        inputPanel.add(terminatedStatus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel lastOr_panel = new JPanel();
        JLabel lastOr_label = new JLabel("Общие правила ТБ: ");
        lastOr_label.setFont(Font.getFont("Lena"));
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
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        truckLicence_table = new JTable(truckLicence_model);
        truckLicence_table.setBorder(new LineBorder(Color.BLACK));
        truckLicence_table.setFont(Font.getFont("Lena"));
        truckLicence_table.setEnabled(false);
        truckLicence_table.setBackground(Color.WHITE);
        truckLicence_table.setRowHeight(25);

        JTableHeader header= truckLicence_table.getTableHeader();
        header.setBorder(new LineBorder(Color.BLACK));
        header.setBackground(Color.WHITE);
        header.setFont(Font.getFont("Lena"));

        TableColumnModel truckLicence_columns = header.getColumnModel();

        TableColumn tabCol0 = truckLicence_columns.getColumn(0);
        tabCol0.setHeaderValue("Категория");
        tabCol0.setCellRenderer(centerRenderer);
        tabCol0.setPreferredWidth(10);

        String[] categories_list = new String[]{"А","Б","В","Г","Д","Е","Е1"};
        for (int i = 0; i < truckLicence_table.getRowCount(); i++){
            truckLicence_table.setValueAt(categories_list[i],i,0);
        }

        TableColumn tabCol1 = truckLicence_columns.getColumn(1);
        tabCol1.setHeaderValue("✓");
        tabCol1.setCellRenderer(centerRenderer);
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
                            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

                            if (cellModel.getValue() == null){

                                Date today = Calendar.getInstance().getTime();
                                String todayDate = dateFormatter.format(today);
                                System.out.println(todayDate);
                                truckLicence_table.setValueAt(todayDate,truckLicence_table.getSelectedRow(),1);

                            } else {

                                String convertedDate = dateFormatter.format(selectedDate);
                                System.out.println(convertedDate);
                                truckLicence_table.setValueAt(convertedDate,truckLicence_table.getSelectedRow(),1);

                            }
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
        drivingLicence_table.setBorder(line);
        drivingLicence_table.setEnabled(false);
        drivingLicence_table.setBackground(Color.WHITE);
        drivingLicence_table.setRowHeight(20);

        JTableHeader drivingLicence_header = drivingLicence_table.getTableHeader();
        drivingLicence_header.setBorder(new LineBorder(Color.BLACK));
        drivingLicence_header.setBackground(Color.WHITE);
        drivingLicence_header.setFont(Font.getFont("Lena"));

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
        drivingCol1.setHeaderValue("✓");
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
                            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

                            if ( cellModel.getValue() == null ){
                                Date today = Calendar.getInstance().getTime();
                                String todayDate = dateFormatter.format(today);
                                drivingLicence_table.setValueAt(todayDate,drivingLicence_table.getSelectedRow(),1);
                                System.out.println(todayDate);
                            }
                            else {
                                String convertedDate = dateFormatter.format(selectedDate);
                                System.out.println(convertedDate);
                                drivingLicence_table.setValueAt(convertedDate,drivingLicence_table.getSelectedRow(),1);
                            }

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
        buttons_panel.setBounds(20, 600, 600, 60);
        buttons_panel.setLayout(new GridLayout(2, 5,5,5));
        this.add(buttons_panel);

        edit_button = new JButton("Изменить");
        edit_button.setBackground(Color.WHITE);
        edit_button.setForeground(Color.BLUE);
        edit_button.setBorder(new RoundedBorder(10));
        edit_button.setFont(Font.getFont("Lena"));
        edit_button.setEnabled(false);
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUser = true;
                editFields();
            }
        });

        add_button = new JButton("Создать");
        add_button.setBackground(Color.WHITE);
        add_button.setForeground(new Color(255,140,0));
        add_button.setBorder(new RoundedBorder(10));
        add_button.setFont(Font.getFont("Lena"));
        buttons_panel.add(add_button);
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUser = false;
                enableFields();
            }
        });

        save_button = new JButton("Сохранить");
        save_button.setBackground(Color.WHITE);
        save_button.setForeground(Color.BLACK);
        save_button.setBorder(new RoundedBorder(10));
        save_button.setFont(Font.getFont("Lena"));
        save_button.setEnabled(false);
        buttons_panel.add(save_button);
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableID_text.getText().equals("") || nameRus_text.getText().equals("")){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите данные сотрудника");
                } else {
                    if (editUser){

                        databaseQueries.setFullName(nameRus_text.getText());
                        databaseQueries.setCrewID(crewRus_box);
                        databaseQueries.setTerminatedID(terminatedStatus_box);
                        databaseQueries.setDepartmentID(departmentRus_box);
                        databaseQueries.setReportsTo(supervisor_box);
                        databaseQueries.setDrivingLicence_table(drivingLicence_table);
                        databaseQueries.setTruckLicence_table(truckLicence_table);
                        databaseQueries.setLastSafetyOr(LastOr_dtp);
                        databaseQueries.setJobTitle(positionRus_box);
                        databaseQueries.updateEmployee();

                    } else {

                        databaseQueries.setEmployeeID(tableID_text.getText());
                        databaseQueries.setFullName(nameRus_text.getText());
                        databaseQueries.setCrewID(crewRus_box);
                        databaseQueries.setTerminatedID(terminatedStatus_box);
                        databaseQueries.setDepartmentID(departmentRus_box);
                        databaseQueries.setReportsTo(supervisor_box);
                        databaseQueries.setDrivingLicence_table(drivingLicence_table);
                        databaseQueries.setTruckLicence_table(truckLicence_table);
                        databaseQueries.setLastSafetyOr(LastOr_dtp);
                        databaseQueries.setJobTitle(positionRus_box);
                        databaseQueries.createEmployee();
                    }

                    clearFields();
                }
            }
        });

        cancel_button = new JButton("Сброс");
        cancel_button.setBackground(Color.WHITE);
        cancel_button.setForeground(Color.RED);
        cancel_button.setBorder(new RoundedBorder(10));
        cancel_button.setFont(Font.getFont("Lena"));
        buttons_panel.add(cancel_button);
        cancel_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });


        savePhoto_button = new JButton("Скачать фото");
        savePhoto_button.setBackground(Color.WHITE);
        savePhoto_button.setForeground(Color.BLACK);
        savePhoto_button.setBorder(new RoundedBorder(10));
        savePhoto_button.setFont(Font.getFont("Lena"));
        buttons_panel.add(savePhoto_button);
        savePhoto_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });

        eraseData_button = new JButton("Обнулить данные");
        eraseData_button.setBackground(Color.WHITE);
        eraseData_button.setForeground(Color.BLACK);
        eraseData_button.setBorder(new RoundedBorder(10));
        eraseData_button.setFont(Font.getFont("Lena"));
        buttons_panel.add(eraseData_button);
        eraseData_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Objects.equals(tableID_text.getText(), "")){
                     EraseConfirmationFrame eraseConfirmationFrame = new EraseConfirmationFrame(tableID_text.getText());
                     eraseConfirmationFrame.pack();
                     eraseConfirmationFrame.setVisible(true);
                } else {
                     JOptionPane.showMessageDialog(MineOperations.cardPane, "Введите табельный номер сотрудника");
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        photoPath = new JTextField();
        photoPath.setForeground(Color.BLACK);
        photoPath.setDisabledTextColor(Color.BLACK);
        PhotoPathPanel.add(photoPath);

        select_button = new JButton("Выбрать фото");
        select_button.setForeground(Color.BLACK);
        select_button.setBackground(Color.WHITE);
        select_button.setEnabled(false);
        select_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        uploadPhotoPanel.add(select_button);
        select_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif", "gif", "bmp"));
                int returnVal = fileChooser.showOpenDialog(pobj);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getName();
                    String extension = fileName.substring(fileName.lastIndexOf("."));
                    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")
                            || extension.equalsIgnoreCase(".bmp") || extension.equalsIgnoreCase(".tif")
                            || extension.equalsIgnoreCase(".gif")) {
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
        upload_button.setEnabled(false);
        upload_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        uploadPhotoPanel.add(upload_button);
        upload_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (photoPath.getText().equals("No File Uploaded")) {
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Файл не выбран");
                }else
                if (tableID_text.getText().equals("") || nameRus_text.getText().equals("")){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите данные сотрудника");
                }else
                if (!databaseQueries.ifUserExists(tableID_text.getText())){
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
                                "UPDATE dbo.Employees SET Photo = ? WHERE EmployeeID = '" + tableID_text.getText() +"'";

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
        exit_button.setBackground(Color.WHITE);
        exit_button.setForeground(Color.RED);
        exit_button.setFont(Font.getFont("Lena"));
        exit_button.setBorder(new RoundedBorder(10));
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void saveImage(){
        if (tableID_text.getText() != null && databaseQueries.getPhotoLabel() != null){

            try{

                String path = new File(MineOperations.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();

                Container c = databaseQueries.getPhotoLabel();
                BufferedImage employeePhoto = new BufferedImage(c.getWidth(),c.getHeight(),BufferedImage.TYPE_INT_ARGB);
                c.paint(employeePhoto.getGraphics());
                ImageIO.write(employeePhoto,"PNG", new File(path + tableID_text.getText() + "-" + nameRus_text.getText() + ".png"));
                System.out.println("Image has been saved");
            } catch (IOException | URISyntaxException ex){
                ex.printStackTrace();
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.drawImage(bg_image,0,0,this);
        g.drawImage(logo_image, 0, 0, 150, 100, this);
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

    public void clearFields(){

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

        select_button.setEnabled(false);
        upload_button.setEnabled(false);

        photoPanel.removeAll();
        revalidate();
        repaint();
    }

    private void enableFields(){

        edit_button.setEnabled(false);
        add_button.setEnabled(false);

        search_button.setEnabled(false);
        surnameSearch_button.setEnabled(false);

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

        select_button.setEnabled(true);
        upload_button.setEnabled(true);

        tableID_label.setForeground(Color.BLACK);
    }

    private void editFields(){

        tableID_label.setForeground(Color.BLACK);

        search_button.setEnabled(false);
        surnameSearch_button.setEnabled(false);
        add_button.setEnabled(false);
        edit_button.setEnabled(false);

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

        select_button.setEnabled(true);
        upload_button.setEnabled(true);
    }


    private class EraseConfirmationFrame extends JFrame{


        String confirmationWord = "УДАЛИТЬ";
        JLabel employeeIDInfo_label;
        JTextField confirmationInput;
        JButton confirmButton;
        JButton cancelButton;

        JPanel pageTitlePanel;

        public EraseConfirmationFrame(String EmployeeID){

            buildFrame(EmployeeID);


            this.setPreferredSize(new Dimension(500,200));
            this.setFocusableWindowState(true);
            this.setAutoRequestFocus(true);
            this.setLocation(900,40);
            this.setLayout(null);
        }

        private void buildFrame(String EmployeeID){
            pageTitlePanel = new JPanel();
            pageTitlePanel.setBounds(0,0,500,50);
            pageTitlePanel.setBackground(Color.WHITE);
            pageTitlePanel.setVisible(true);
            this.add(pageTitlePanel);

            employeeIDInfo_label = new JLabel("<html> Вы уверены, что хотите удалить сотрудника: " + EmployeeID + "? <br /> Введите слово 'УДАЛИТЬ' для подтверждения. </html>");
            employeeIDInfo_label.setFont(new Font("Helvetica",Font.BOLD, 15));
            pageTitlePanel.add(employeeIDInfo_label);

            confirmationInput = new JTextField();
            confirmationInput.setBounds(50,60,400,25);
            confirmationInput.setBorder(new LineBorder(Color.BLACK,1));
            this.add(confirmationInput);

            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setBounds(50,90,400,40);
            buttonsPanel.setLayout(new GridLayout(1,2,5,0));
            this.add(buttonsPanel);

            confirmButton = new JButton("Удалить");
            confirmButton.setForeground(Color.BLACK);
            confirmButton.setBackground(Color.WHITE);
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Objects.equals(confirmationInput.getText(), confirmationWord)){
                        erasingData(EmployeeID);
                    } else {
                        JOptionPane.showMessageDialog(MineOperations.cardPane, "Введите повторно 'УДАЛИТЬ'");
                    }
                }
            });
            buttonsPanel.add(confirmButton);

            cancelButton = new JButton("Отменить");
            cancelButton.setForeground(Color.BLACK);
            cancelButton.setBackground(Color.WHITE);
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });
            buttonsPanel.add(cancelButton);

            JPanel backgroundPanel = new JPanel();
            backgroundPanel.setBackground(Color.WHITE);
            backgroundPanel.setBounds(0,0,500,200);
            this.add(backgroundPanel);
        }

        private void erasingData(String employeeID){
            try {
                PreparedStatement eraseDataStatement;
                String deleteQueryTrainingData = "DELETE FROM TrainingData Where EmployeeID = '"+employeeID+"'";
                String deleteQuerySRT = "DELETE FROM SRT Where EmployeeID = '"+employeeID+"'";
                String deleteQueryQualified = "DELETE FROM Qualified Where EmployeeID = '"+employeeID+"'";

                System.out.println(deleteQueryTrainingData);
                System.out.println(deleteQuerySRT);

                eraseDataStatement = MineOperations.conn.prepareStatement(deleteQueryTrainingData);
                eraseDataStatement.executeUpdate();

                eraseDataStatement = MineOperations.conn.prepareStatement(deleteQuerySRT);
                eraseDataStatement.executeUpdate();

                eraseDataStatement = MineOperations.conn.prepareStatement(deleteQueryQualified);
                eraseDataStatement.executeUpdate();

                JOptionPane.showMessageDialog(MineOperations.cardPane, "Данные обнулены");
                setVisible(false);
                dispose();

            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    private class SearchBySurname extends JFrame{

        private JTable listOfEmployees_table;

        private List<String> employeeNames_list = new ArrayList<>();
        private List<String> employeeID_list = new ArrayList<>();

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
                        search_button.doClick();
                    }
                }
            });
            tablePanel.add(new JScrollPane(listOfEmployees_table));
        }

        private void findSurnames(String surname){

            if (surname == null){
                JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите фамилию или имя");
            } else {
                databaseQueries.findBySurname(surname);
                employeeNames_list = databaseQueries.getListOfSurnames();
                employeeID_list = databaseQueries.getListOfID();
                numOfRows = employeeNames_list.size();
            }
        }
    }

    private static class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

}