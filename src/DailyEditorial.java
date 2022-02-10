import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DailyEditorial extends JPanel {

    private BufferedImage logo_image, bg_image;

    private JLabel tableID_label;

    public JTextField tableID_text;

    private JButton surnameSearch_button, search_button;

    public DatabaseQueries dailyEditorialQueries = new DatabaseQueries();

    private JTextField nameRus_text;

    private List<Object[]> dailyHours_list = new ArrayList<Object[]>();

    int numOfAcceptedHours = 0;

    private DefaultTableModel dailyHours_tableModel;
    private JTable dailyHours_table;

    private Action searchAction;

    public DailyEditorial(){

        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel title_label = new JLabel("<html><big>Ежедневные часы</big><br />Поиск данных о часах сотрудника</html>");
        title_label.setBounds(160, 0, 500, 100);
        title_label.setForeground(Color.BLACK);
        title_label.setFont(Font.getFont("Lena"));
        this.add(title_label);

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
                if (tableID_text.getText().equals("") )
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите табельный номер");
                else {
                    dailyEditorialQueries.queryEmployeeData(tableID_text.getText());
                    nameRus_text.setText(dailyEditorialQueries.getEmployeeName());
                    loadHours();
                }
            }
        };
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        tableID_text = new JTextField();
        tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
        tableID_text.addActionListener(searchAction);
        tableID_panel.add(tableID_text);
        searchEmployee_panel.add(tableID_text);

        search_button = new JButton("Поиск");
        search_button.setForeground(Color.RED);
        search_button.setBackground(Color.WHITE);
        search_button.setFont(Font.getFont("Lena"));
        search_button.setBorder(new RoundedBorder(10));
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

        JPanel courseAccessPanel = new JPanel();
        courseAccessPanel.setBackground(Color.WHITE);
        courseAccessPanel.setBounds(20, 235,850,400);
        courseAccessPanel.setLayout(new BorderLayout());
        courseAccessPanel.setBorder(line);
        this.add(courseAccessPanel);

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

        JButton deleteRecord_button = new JButton("Удалить запись");
        deleteRecord_button.setBackground(Color.WHITE);
        deleteRecord_button.setForeground(Color.BLACK);
        deleteRecord_button.setFont(Font.getFont("Lena"));
        deleteRecord_button.setBorder(new RoundedBorder(10));
        deleteRecord_button.setBounds(20,650,150,30);
        this.add(deleteRecord_button);
        deleteRecord_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dailyHours_table.getSelectionModel().isSelectionEmpty()){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Выберите запись");
                } else {
                    int input = JOptionPane.showConfirmDialog(null,"Вы уверены что хотите удалить эту запись?","Удаление записи",JOptionPane.YES_NO_CANCEL_OPTION);
                    if (input == 0){
                        int selectedRowIndex = dailyHours_table.getSelectedRow();
                        System.out.println(selectedRowIndex);

                        int selectedRecord = (Integer) dailyHours_table.getValueAt(selectedRowIndex,7);

                        String delete_query = "DELETE FROM dbo.TrainingData WHERE RecID = " + selectedRecord;
                        try {
                            PreparedStatement delete_pst = MineOperations.conn.prepareStatement(delete_query);
                            delete_pst.executeUpdate();

                            dailyHours_tableModel.removeRow(selectedRowIndex);
                            dailyHours_tableModel.fireTableDataChanged();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        dailyHours_tableModel = new DefaultTableModel(numOfAcceptedHours, 9){
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        DefaultTableCellRenderer acceptedHours_cellRenderer = new DefaultTableCellRenderer();
        acceptedHours_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        dailyHours_table = new JTable(dailyHours_tableModel);
        dailyHours_table.setBorder(new LineBorder(Color.BLACK));
        dailyHours_table.setBackground(Color.WHITE);
        dailyHours_table.setRowHeight(20);

        JTableHeader acceptedhours_header = dailyHours_table.getTableHeader();
        acceptedhours_header.setBorder(new LineBorder(Color.BLACK));
        acceptedhours_header.setBackground(Color.WHITE);
        acceptedhours_header.setFont(Font.getFont("Lena"));

        TableColumnModel acceptedHours_columns = acceptedhours_header.getColumnModel();

        TableColumn tabCol0 = acceptedHours_columns.getColumn(0);
        tabCol0.setHeaderValue("Дата");
        tabCol0.setCellRenderer(acceptedHours_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = acceptedHours_columns.getColumn(1);
        tabCol1.setHeaderValue("Работник");
        tabCol1.setCellRenderer(acceptedHours_cellRenderer);
        tabCol1.setPreferredWidth(30);

        TableColumn tabCol2 = acceptedHours_columns.getColumn(2);
        tabCol2.setHeaderValue("Курс");
        tabCol2.setCellRenderer(acceptedHours_cellRenderer);
        tabCol2.setPreferredWidth(80);

        TableColumn tabCol3 = acceptedHours_columns.getColumn(3);
        tabCol3.setHeaderValue("Теория");
        tabCol3.setCellRenderer(acceptedHours_cellRenderer);
        tabCol3.setPreferredWidth(20);

        TableColumn tabCol4 = acceptedHours_columns.getColumn(4);
        tabCol4.setHeaderValue("Практика");
        tabCol4.setCellRenderer(acceptedHours_cellRenderer);
        tabCol4.setPreferredWidth(20);

        TableColumn tabCol5 = acceptedHours_columns.getColumn(5);
        tabCol5.setHeaderValue("ExpHours");
        tabCol5.setCellRenderer(acceptedHours_cellRenderer);
        tabCol5.setPreferredWidth(20);

        TableColumn tabCol6 = acceptedHours_columns.getColumn(6);
        tabCol6.setHeaderValue("С инструктором");
        tabCol6.setCellRenderer(acceptedHours_cellRenderer);
        tabCol6.setPreferredWidth(20);

        TableColumn tabCol7 = acceptedHours_columns.getColumn(7);
        tabCol7.setHeaderValue("Инструктор");
        tabCol7.setCellRenderer(acceptedHours_cellRenderer);
        tabCol7.setPreferredWidth(60);

        TableColumn tabCol8 = acceptedHours_columns.getColumn(8);
        tabCol8.setHeaderValue("ID");
        tabCol8.setCellRenderer(acceptedHours_cellRenderer);
        tabCol8.setPreferredWidth(10);

        JScrollPane instructors_scrollPane = new JScrollPane(dailyHours_table);
        instructors_scrollPane.setBackground(Color.WHITE);
        courseAccessPanel.add(instructors_scrollPane);

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
                MineOperations.card.show(MineOperations.cardPane,"Home Page");
            }
        });

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void loadHours(){

        dailyHours_list.clear();

        String hours_query = "select td.date, td.EmployeeId, cc.Course, td.RecID, td.Fhours, td.Thours, td.ExpHours, td.Phours,  ii.Instructor from dbo.TrainingData td\n" +
                "\tleft join dbo.Courses cc on td.Coarse = cc.CoarseNo \n" +
                "\tleft join dbo.Instructor ii on td.instructor = ii.InstructoId \n" +
                "\twhere td.EmployeeID = '" + tableID_text.getText() + "'\n" +
                "\torder by date desc";

        try {
            Statement hours_st = MineOperations.conn.createStatement();
            ResultSet hours_rs = hours_st.executeQuery(hours_query);

            if (hours_rs.next()){
                do{

                    Date date = hours_rs.getDate("date");
                    String EmployeeId = hours_rs.getString("EmployeeId");
                    String course = hours_rs.getString("Course");
                    float tHours = hours_rs.getFloat("Thours");
                    float pHours = hours_rs.getFloat("Phours");
                    float expHours = hours_rs.getFloat("ExpHours");
                    float fHours = hours_rs.getFloat("fHours");
                    String instructor = hours_rs.getString("Instructor");
                    int recId = hours_rs.getInt("RecID");

                    dailyHours_list.add(new Object[]{date,EmployeeId, course, tHours, pHours, expHours,fHours,instructor,recId});

                }while (hours_rs.next());

                numOfAcceptedHours = dailyHours_list.size();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        dailyHours_tableModel.setRowCount(0);
        for (Object[] acceptedHour : dailyHours_list) {
            dailyHours_tableModel.addRow(acceptedHour);
        }
        dailyHours_tableModel.fireTableDataChanged();
    }

    protected void paintComponent(Graphics g) {
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
                        tableID_text.setText(listOfEmployees_table.getValueAt(index1,0).toString());

                        dailyEditorialQueries.queryEmployeeData(tableID_text.getText());
                        nameRus_text.setText(dailyEditorialQueries.getEmployeeName());

                        loadHours();

                    }
                }
            });

            tablePanel.add(new JScrollPane(listOfEmployees_table));
        }

        private void findSurnames(String surname){

            if (surname == null){
                JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите фамилию или имя");
            } else {
                dailyEditorialQueries.findBySurname(surname);
                employeeNames_list = dailyEditorialQueries.getListOfSurnames();
                employeeID_list = dailyEditorialQueries.getListOfID();
                numOfRows = employeeNames_list.size();
            }
        }
    }


}
