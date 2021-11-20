import javax.imageio.ImageIO;
import javax.swing.*;
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

public class SRTHoursEditorial extends JPanel {

    private BufferedImage logo_image;

    private JLabel tableID_label;

    public JTextField tableID_text;

    private JButton surnameSearch_button, search_button;

    public DatabaseQueries srtEditorialQueries = new DatabaseQueries();

    private JTextField nameRus_text;

    private List<Object[]> acceptedHours_list = new ArrayList<Object[]>();

    int numOfAcceptedHours = 0;

    private DefaultTableModel acceptedHours_tableModel;
    private JTable acceptedHours_table;

    public SRTHoursEditorial(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {

        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel title_label = new JLabel("Часы SRT");
        title_label.setBounds(160, 0, 500, 100);
        title_label.setBackground(Color.WHITE);
        title_label.setForeground(Color.WHITE);
        title_label.setFont(new Font("Helvetica", Font.BOLD, 40));
        this.add(title_label);

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
                    srtEditorialQueries.queryEmployeeData(tableID_text.getText());
                    nameRus_text.setText(srtEditorialQueries.getEmployeeName());
                    loadHours();
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel employeeInfo_panel = new JPanel();
        employeeInfo_panel.setBackground(Color.white);
        employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
        employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Персональная Информация"));
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
        courseAccessPanel.setBorder(new LineBorder(Color.orange));
        this.add(courseAccessPanel);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton deleteRecord_button = new JButton("Удалить запись");
        deleteRecord_button.setBackground(Color.WHITE);
        deleteRecord_button.setForeground(Color.BLACK);
        deleteRecord_button.setBounds(20,650,150,30);
        this.add(deleteRecord_button);
        deleteRecord_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (acceptedHours_table.getSelectionModel().isSelectionEmpty()){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Выберите запись");
                } else {
                    int input = JOptionPane.showConfirmDialog(null,"Вы уверены что хотите удалить эту запись?","Удаление записи",JOptionPane.YES_NO_CANCEL_OPTION);
                    if (input == 0){
                        int selectedRowIndex = acceptedHours_table.getSelectedRow();
                        System.out.println(selectedRowIndex);

                        int selectedRecord = (Integer) acceptedHours_table.getValueAt(selectedRowIndex,7);

                        String delete_query = "DELETE FROM dbo.SRT WHERE RecID = " + selectedRecord;
                        try {
                            PreparedStatement delete_pst = MineOperations.conn.prepareStatement(delete_query);
                            delete_pst.executeUpdate();

                            acceptedHours_tableModel.removeRow(selectedRowIndex);
                            acceptedHours_tableModel.fireTableDataChanged();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

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

        acceptedHours_tableModel = new DefaultTableModel(numOfAcceptedHours, 8){
          @Override
          public boolean isCellEditable(int row, int column) {return false;}
        };
        DefaultTableCellRenderer acceptedHours_cellRenderer = new DefaultTableCellRenderer();
        acceptedHours_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        acceptedHours_table = new JTable(acceptedHours_tableModel);
        acceptedHours_table.setBorder(new LineBorder(Color.BLACK));
        acceptedHours_table.setBackground(Color.WHITE);
        acceptedHours_table.setRowHeight(20);

        JTableHeader acceptedhours_header = acceptedHours_table.getTableHeader();
        acceptedhours_header.setBorder(new LineBorder(Color.BLACK));
        acceptedhours_header.setBackground(Color.WHITE);
        acceptedhours_header.setFont(new Font("Helvetica", Font.BOLD,12));

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
        tabCol5.setHeaderValue("С инструктором");
        tabCol5.setCellRenderer(acceptedHours_cellRenderer);
        tabCol5.setPreferredWidth(20);

        TableColumn tabCol6 = acceptedHours_columns.getColumn(6);
        tabCol6.setHeaderValue("Инструктор");
        tabCol6.setCellRenderer(acceptedHours_cellRenderer);
        tabCol6.setPreferredWidth(60);

        TableColumn tabCol7 = acceptedHours_columns.getColumn(7);
        tabCol7.setHeaderValue("ID");
        tabCol7.setCellRenderer(acceptedHours_cellRenderer);
        tabCol7.setPreferredWidth(10);

        JScrollPane instructors_scrollPane = new JScrollPane(acceptedHours_table);
        instructors_scrollPane.setBackground(Color.WHITE);
        courseAccessPanel.add(instructors_scrollPane);

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
            }
        });

        }

        private void loadHours(){

        acceptedHours_list.clear();

        String hours_query = "select s.lastdate as date, s.EmployeeId, cc.Course, s.RecID, s.Thours,  s.Phours, Fieldhours as fHours,  ii.Instructor from MineOperationsTestDb.dbo.SRT s\n" +
                "\tleft join MineOperationsTestDb.dbo.SafetyNames cc on s.Coarse = cc.ReviewNo \n" +
                "\tleft join MineOperationsTestDb.dbo.Instructor ii on s.instructor = ii.InstructoId \n" +
                "\twhere s.EmployeeID = '"+ tableID_text.getText() +"'\n" +
                "\torder by  s.lastdate  desc";

        try {
            Statement hours_st = MineOperations.conn.createStatement();
            ResultSet hours_rs = hours_st.executeQuery(hours_query);

            if (hours_rs.next()){
                do{

                    Date date = hours_rs.getDate("date");
                    int EmployeeId = hours_rs.getInt("EmployeeId");
                    String course = hours_rs.getString("Course");
                    int tHours = hours_rs.getInt("Thours");
                    int pHours = hours_rs.getInt("Phours");
                    int fHours = hours_rs.getInt("fHours");
                    String instructor = hours_rs.getString("Instructor");
                    int recId = hours_rs.getInt("RecID");

                    acceptedHours_list.add(new Object[]{date,EmployeeId, course, tHours, pHours, fHours,instructor,recId});

                }while (hours_rs.next());

                numOfAcceptedHours = acceptedHours_list.size();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        acceptedHours_tableModel.setRowCount(0);
        for (Object[] acceptedHour : acceptedHours_list) {
            acceptedHours_tableModel.addRow(acceptedHour);
        }
        acceptedHours_tableModel.fireTableDataChanged();
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }

    private class SearchBySurname extends JFrame{

        private JTable listOfEmployees_table;

        private java.util.List<String> employeeNames_list = new ArrayList<>();
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
                        tableID_text.setText(listOfEmployees_table.getValueAt(index1,0).toString());

                        srtEditorialQueries.queryEmployeeData(tableID_text.getText());
                        nameRus_text.setText(srtEditorialQueries.getEmployeeName());

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
                srtEditorialQueries.findBySurname(surname);
                employeeNames_list = srtEditorialQueries.getListOfSurnames();
                employeeID_list = srtEditorialQueries.getListOfID();
                numOfRows = employeeNames_list.size();
            }
        }
    }
}
