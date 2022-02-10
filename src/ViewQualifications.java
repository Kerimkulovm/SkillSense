import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ViewQualifications  extends JPanel{

    private JButton search_button;
    private JButton editButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton  surnameSearch_button;

    public static DatabaseQueries databaseQueries = new DatabaseQueries();

    private JTextField tableID_text;
    private JTextField nameRus_text;

    public static List<String> coursesList = new ArrayList<>();

    public int numOfCourses;

    private JLabel tableID_label;

    private BufferedImage logo_image, bg_image;

    public static TrucksTableModel courses_tableModel;
    private JTable courseQualifications_table;
    private JTable practiceHours_table;

    private JPanel photoPanel;

    private Action searchAction;

    List<String> employeeQualifiedCourses_list = databaseQueries.loadCourses();

    public ViewQualifications(){

        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Информация о квалификации сотрудника</big><br />Ввод данных о квалификации сотрудников</html>");
        titleEng.setBounds(160, 0, 500, 100);
        titleEng.setFont(Font.getFont("Lena"));
        titleEng.setForeground(Color.BLACK);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        LineBorder line = new LineBorder(Color.GRAY, 1, true);
        JPanel searchEmployee_panel = new JPanel();
        searchEmployee_panel.setBackground(Color.white);
        searchEmployee_panel.setLayout(new BoxLayout(searchEmployee_panel, BoxLayout.X_AXIS));
        searchEmployee_panel.setBorder(new TitledBorder(line, "Поиск сотрудника"));
        searchEmployee_panel.setBounds(20, 120, 520, 50);
        this.add(searchEmployee_panel);

        JPanel tableID_panel = new JPanel();
        tableID_label = new JLabel(" Табельный номер:  ");
        tableID_label.setForeground(Color.RED);
        tableID_label.setFont(Font.getFont("Lena"));
        tableID_panel.setBackground(Color.WHITE);
        tableID_panel.add(tableID_label);
        searchEmployee_panel.add(tableID_label);

        surnameSearch_button = new JButton("Ф.И.О.");
        surnameSearch_button.setForeground(Color.RED);
        surnameSearch_button.setBackground(Color.WHITE);
        surnameSearch_button.setFont(Font.getFont("Lena"));
        surnameSearch_button.setBorder(new RoundedBorder(10));
        tableID_panel.add(surnameSearch_button);
        searchEmployee_panel.add(surnameSearch_button);
        surnameSearch_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchBySurname searchBySurname = new SearchBySurname(tableID_text.getText());
                searchBySurname.pack();
                searchBySurname.setVisible(true);
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        searchAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableID_text.getText().equals("")){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите табельный номер");
                } else {
                    databaseQueries.queryEmployeeData(tableID_text.getText());
                    nameRus_text.setText(databaseQueries.getEmployeeName());
                    courseQualifications_table = databaseQueries.loadAcceptedQualifications(courseQualifications_table);
                    practiceHours_table = databaseQueries.loadPracticedHoursJTable(practiceHours_table);

                    if (databaseQueries.getPhotoLabel() != null){
                        photoPanel.removeAll();
                        photoPanel.add(databaseQueries.getPhotoLabel());
                        revalidate();
                        repaint();
                    }else {
                        photoPanel.removeAll();
                        revalidate();
                        repaint();
                    }
                    editButton.setEnabled(true);
                }
            }
        };
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        tableID_text = new JTextField();
        tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
        tableID_text.addActionListener(searchAction);
        tableID_panel.add(tableID_text);
        searchEmployee_panel.add(tableID_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        employeeInfo_panel.setBounds(20, 175, 520, 50);
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
        photoPanel.setBounds(550, 150, 210, 230);
        photoPanel.setLayout(new BorderLayout());
        photoPanel.setBorder(line);
        this.add(photoPanel);

        JPanel courseAccessPanel = new JPanel();
        courseAccessPanel.setBackground(Color.WHITE);
        courseAccessPanel.setBounds(20, 235,520,450);
        courseAccessPanel.setLayout(new BorderLayout());
        courseAccessPanel.setBorder(line);
        this.add(courseAccessPanel);


        JPanel practiceHoursPanel = new JPanel();
        practiceHoursPanel.setBackground(Color.WHITE);
        practiceHoursPanel.setBounds(550, 390, 300, 200);
        practiceHoursPanel.setLayout(new BorderLayout());
        practiceHoursPanel.setBorder(line);
        this.add(practiceHoursPanel);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(0,0,0,0));
        buttonsPanel.setBounds(550,600, 210,80);
        buttonsPanel.setLayout(new GridLayout(2,1, 5, 5));
        this.add(buttonsPanel);

        JPanel firstLineButtons = new JPanel();
        firstLineButtons.setBackground(new Color(0,0,0,0));
        firstLineButtons.setLayout(new GridLayout(1,2, 5 ,0));
        buttonsPanel.add(firstLineButtons);

        JPanel secondLineButtons = new JPanel();
        secondLineButtons.setBackground(new Color(0,0,0,0));
        secondLineButtons.setLayout(new BorderLayout());
        buttonsPanel.add(secondLineButtons);

        editButton = new JButton("Изменить");
        editButton.setForeground(Color.BLUE);
        editButton.setBackground(Color.WHITE);
        editButton.setBorder(new RoundedBorder(10));
        editButton.setFont(Font.getFont("Lena"));
        firstLineButtons.add(editButton);
        editButton.setEnabled(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editFields();
            }
        });

        saveButton = new JButton("Сохранить");
        saveButton.setForeground(Color.GREEN);
        saveButton.setBackground(Color.WHITE);
        saveButton.setBorder(new RoundedBorder(10));
        saveButton.setFont(Font.getFont("Lena"));
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseQualifications_table = databaseQueries.saveAcceptedQualifications(courseQualifications_table);
                JOptionPane.showMessageDialog(MineOperations.cardPane, "Информация о сотруднике успешно изменена!");
                clearFields();
            }
        });
        firstLineButtons.add(saveButton);

        cancelButton = new JButton("Сброс");
        cancelButton.setForeground(Color.RED);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setFont(Font.getFont("Lena"));
        cancelButton.setBorder(new RoundedBorder(10));
        secondLineButtons.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

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
        //Initialize practice hours table
        DefaultTableModel practiceHours_model = new DefaultTableModel(50,5);
        DefaultTableCellRenderer practiceHoursRendered = new DefaultTableCellRenderer();
        practiceHoursRendered.setHorizontalAlignment(JLabel.CENTER);

        practiceHours_table = new JTable(practiceHours_model);
        practiceHours_table.setBorder(new LineBorder(Color.BLACK));
        practiceHours_table.setEnabled(false);
        practiceHours_table.setBackground(Color.WHITE);
        practiceHours_table.setRowHeight(20);

        JTableHeader practiceHours_header = practiceHours_table.getTableHeader();
        practiceHours_header.setBorder(new LineBorder(Color.BLACK));
        practiceHours_header.setBackground(Color.WHITE);
        practiceHours_header.setFont(Font.getFont("Lena"));

        TableColumnModel practiceHours_columns = practiceHours_header.getColumnModel();

        TableColumn tabCol0 = practiceHours_columns.getColumn(0);
        tabCol0.setHeaderValue("Транспорт");
        tabCol0.setCellRenderer(practiceHoursRendered);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = practiceHours_columns.getColumn(1);
        tabCol1.setHeaderValue("Теор");
        tabCol1.setCellRenderer(practiceHoursRendered);
        tabCol1.setPreferredWidth(10);

        TableColumn tabCol2 = practiceHours_columns.getColumn(2);
        tabCol2.setHeaderValue("С Трен");
        tabCol2.setCellRenderer(practiceHoursRendered);
        tabCol2.setPreferredWidth(10);

        TableColumn tabCol3 = practiceHours_columns.getColumn(3);
        tabCol3.setHeaderValue("Прак");
        tabCol3.setCellRenderer(practiceHoursRendered);
        tabCol3.setPreferredWidth(10);

        TableColumn tabCol4 = practiceHours_columns.getColumn(4);
        tabCol4.setHeaderValue("Опыт");
        tabCol4.setCellRenderer(practiceHoursRendered);
        tabCol4.setPreferredWidth(10);

        JScrollPane practice_scrollPane = new JScrollPane(practiceHours_table);
        practice_scrollPane.setBackground(Color.WHITE);
        practiceHoursPanel.add(practice_scrollPane);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Initialize table of accepted course qualifications
        numOfCourses = employeeQualifiedCourses_list.size();
        courses_tableModel = new TrucksTableModel();
        courseQualifications_table = new JTable(courses_tableModel);
        courseQualifications_table.setEnabled(false);
        courseQualifications_table.setRowHeight(20);
        courseQualifications_table.setFont(Font.getFont("Lena"));
        courseQualifications_table.setBackground(Color.WHITE);
        DefaultTableCellRenderer courseQualification_cellRenderer = new DefaultTableCellRenderer();
        courseQualification_cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        courseQualifications_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (editButton.isEnabled()){
                    if (e.getClickCount() == 1){
                        if (courseQualifications_table.getSelectedColumn() > 0 && courseQualifications_table.getSelectedColumn() < 5){
                            if (!(Boolean) courseQualifications_table.getValueAt(courseQualifications_table.getSelectedRow(), courseQualifications_table.getSelectedColumn())){
                                courseQualifications_table.setValueAt(false, courseQualifications_table.getSelectedRow(), courseQualifications_table.getSelectedColumn());
                                courseQualifications_table.setValueAt("", courseQualifications_table.getSelectedRow(), 5);
                            } else {
                                int c = 0;
                                for (int i = 1; i < 5; i ++){
                                    if ((Boolean) courseQualifications_table.getValueAt(courseQualifications_table.getSelectedRow(), i)){
                                        c++;
                                        if (c == 2){
                                            for (int j = 1; j < 5; j++){
                                                courseQualifications_table.setValueAt(false, courseQualifications_table.getSelectedRow(), j);
                                            }
                                            courseQualifications_table.setValueAt(true, courseQualifications_table.getSelectedRow(), courseQualifications_table.getSelectedColumn());
                                        }
                                    }
                                }

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
                                            courseQualifications_table.setValueAt(todayDate, courseQualifications_table.getSelectedRow(),5);

                                        } else {
                                            String convertedDate = dateFormatter.format(selectedDate);
                                            System.out.println(convertedDate);
                                            courseQualifications_table.setValueAt(convertedDate, courseQualifications_table.getSelectedRow(),5);
                                        }
                                        System.out.println(courseQualifications_table.getValueAt(courseQualifications_table.getSelectedRow(), 5));
                                        tempDateFrame.dispatchEvent(new WindowEvent(tempDateFrame, WindowEvent.WINDOW_CLOSING));
                                    }
                                });

                                buttonsPanel.add(okButton);

                                JButton clearButton =new JButton("Стереть");
                                clearButton.setBackground(Color.WHITE);
                                clearButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        courseQualifications_table.setValueAt(null, courseQualifications_table.getSelectedRow(),5);
                                        courseQualifications_table.setValueAt(false, courseQualifications_table.getSelectedRow(), courseQualifications_table.getSelectedColumn());
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
                    }
                }
            }
        });

        //Set Column Width
        TableColumn column = null;
        for (int i = 0; i < 6; i++) {
            column = courseQualifications_table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(300); //third column is bigger
                column.setCellRenderer(courseQualification_cellRenderer);
            } else if (i == 5){
                column.setPreferredWidth(100);
                column.setCellRenderer(courseQualification_cellRenderer);
            } else {
                column.setPreferredWidth(30);
            }
        }

        //Cell Selection
        String.format("Lead Selection: %d, %d. ",
                courseQualifications_table.getSelectionModel().getLeadSelectionIndex(),
                courseQualifications_table.getColumnModel().getSelectionModel().getLeadSelectionIndex());

        JScrollPane tQ_sctollPane = new JScrollPane(courseQualifications_table);
        courseQualifications_table.setFillsViewportHeight(true);
        courseAccessPanel.add(tQ_sctollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.WHITE);
        exit_button.setForeground(Color.RED);
        exit_button.setBorder(new RoundedBorder(10));
        exit_button.setFont(Font.getFont("Lena"));
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                MineOperations.card.show(MineOperations.cardPane,"Home Page");
            }
        });
    }

    public void editFields(){
        courseQualifications_table.setEnabled(true);
        saveButton.setEnabled(true);
        search_button.setEnabled(false);
    }

    public void clearFields(){

        editButton.setEnabled(false);
        saveButton.setEnabled(false);

        nameRus_text.setText("");
        tableID_text.setText("");

        for (int i = 0; i < courseQualifications_table.getRowCount(); i++){
            courseQualifications_table.setValueAt(false, i,1);
            courseQualifications_table.setValueAt(false, i,2);
            courseQualifications_table.setValueAt(false, i,3);
            courseQualifications_table.setValueAt(false, i,4);
            courseQualifications_table.setValueAt("", i,5);
        }

        for (int i = 0; i < practiceHours_table.getRowCount(); i++){
            practiceHours_table.setValueAt("",i,0);
            practiceHours_table.setValueAt(null,i,1);
            practiceHours_table.setValueAt(null,i,2);
            practiceHours_table.setValueAt(null,i,3);
            practiceHours_table.setValueAt(null,i,4);
        }

        courseQualifications_table.setEnabled(false);
        photoPanel.removeAll();
        revalidate();
        repaint();

        search_button.setEnabled(true);

    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.drawImage(bg_image,0,0,this);
        g.drawImage(logo_image, 0, 0, 150, 100, this);
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

    public class TrucksTableModel extends AbstractTableModel {

        private final String[] columnNames = {"Курс","E","Q","A", "T", "Дата"};
        private final Object[][] data = new Object[numOfCourses][6];

        public TrucksTableModel(){

            coursesList = databaseQueries.loadCourses();

            for (int i = 0; i < numOfCourses; i++){
                data[i][0] = coursesList.get(i);
                data[i][1] = false;
                data[i][2] = false;
                data[i][3] = false;
                data[i][4] = false;
                data[i][5] = "";
            }
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

        static class DateRenderer extends DefaultTableCellRenderer {
            DateFormat formatter;
            public DateRenderer() { super(); }

            public void setValue(Object value) {
                if (formatter==null) {
                    formatter = DateFormat.getDateInstance();
                }
                setText((value == null) ? "" : formatter.format(value));
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
            foundEmployees_JLabel.setFont(Font.getFont("Lena"));
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


}