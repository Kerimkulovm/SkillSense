import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class Departments extends JPanel {

    private BufferedImage logo_image, bg_image;

    private JTable departments_table;
    private DefaultTableModel departments_tableModel;

    public DatabaseQueries databaseQueries = new DatabaseQueries();
    public String departmentSelected;

    public List<String> departments_list = new ArrayList<>();


    public Departments(){

        try {
            logo_image = ImageIO.read(new File("resources/logo/Logo2.png"));
            bg_image = ImageIO.read(new File("resources/logo/bg.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Отделы'</big><br /></html>");
        titleEng.setBounds(160, 0, 600, 100);
        titleEng.setFont(Font.getFont("Lena"));
        titleEng.setForeground(Color.BLACK);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JTable departments_panel = new JTable();
        departments_panel.setBackground(Color.WHITE);
        departments_panel.setBounds(20,120,650,500);
        departments_panel.setLayout(new BorderLayout());
        this.add(departments_panel);

        JPanel buttons_panel = new JPanel(new GridLayout(1,2,5,0));
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20,635,300,30);
        this.add(buttons_panel);

        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.WHITE);
        exit_button.setForeground(Color.RED);
        exit_button.setBorder(new RoundedBorder(10));
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Classificatory");
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buildTable();
        JScrollPane instructors_scrollPane = new JScrollPane(departments_table);
        instructors_scrollPane.setBackground(Color.WHITE);
        departments_panel.add(instructors_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton edit_button = new JButton("Изменить");
        edit_button.setForeground(Color.BLACK);
        edit_button.setBackground(Color.WHITE);
        edit_button.setBorder(new RoundedBorder(10));
        edit_button.setFont(Font.getFont("Lena"));
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditDepartmentFrame editDepartmentFrame= new EditDepartmentFrame();
                editDepartmentFrame.setVisible(true);
                editDepartmentFrame.pack();
            }
        });

        JButton create_button = new JButton("Создать");
        create_button.setBackground(Color.WHITE);
        create_button.setForeground(Color.BLACK);
        create_button.setFont(Font.getFont("Lena"));
        create_button.setBorder(new RoundedBorder(10));
        buttons_panel.add(create_button);
        create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDepartmentFrame addDepartmentFrame = new AddDepartmentFrame();
                addDepartmentFrame.setVisible(true);
                addDepartmentFrame.pack();
            }
        });


    }

    private void buildTable(){

        departments_list = loadDepartments(departments_list);

        departments_tableModel = new DefaultTableModel(departments_list.size(),3){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        DefaultTableCellRenderer departments_cellRenderer = new DefaultTableCellRenderer();
        departments_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        departments_table = new JTable(departments_tableModel);
        departments_table.setBorder(new LineBorder(Color.BLACK));
        departments_table.setBackground(Color.WHITE);
        departments_table.setRowHeight(20);

        JTableHeader departments_header = departments_table.getTableHeader();
        departments_header.setBorder(new LineBorder(Color.BLACK));
        departments_header.setBackground(Color.WHITE);
        departments_header.setFont(Font.getFont("Lena"));

        TableColumnModel departments_columns = departments_header.getColumnModel();

        TableColumn tabCol0 = departments_columns.getColumn(0);
        tabCol0.setHeaderValue("ID Отдела");
        tabCol0.setCellRenderer(departments_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = departments_columns.getColumn(1);
        tabCol1.setHeaderValue("Отдел");
        tabCol1.setCellRenderer(departments_cellRenderer);
        tabCol1.setPreferredWidth(50);

        TableColumn tabCol2 = departments_columns.getColumn(2);
        tabCol2.setHeaderValue("Статус");
        tabCol2.setCellRenderer(departments_cellRenderer);
        tabCol2.setPreferredWidth(50);

        for (int i = 0; i <  departments_table.getRowCount();i++){
            departments_table.setValueAt(getDepartmentID(departments_list.get(i)),i,0);
            departments_table.setValueAt(departments_list.get(i),i,1);
            departments_table.setValueAt(getDepartmentActivity(departments_list.get(i)),i,2);
        }

        departments_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    departmentSelected = (String) departments_table.getValueAt(departments_table.getSelectedRow(),1);
                    System.out.println(departmentSelected);
                }
            }
        });
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<String> loadDepartments(List<String> inputList){

        inputList.clear();

        try {

            String departments_query = "SELECT * FROM dbo.Departments";
            Statement departments_st = MineOperations.conn.createStatement();
            ResultSet departments_rs = departments_st.executeQuery(departments_query);

            if (departments_rs.next()){
                do {
                    String departmentsTitle = departments_rs.getString("russian");
                    inputList.add(departmentsTitle);
                } while (departments_rs.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return  inputList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public int getDepartmentID(String departmentTitle){

        int departmentID = 0;

        try {
            String departmentID_query = "SELECT * FROM dbo.Departments WHERE russian = N'" + departmentTitle +"'";
            Statement departmentID_statement = MineOperations.conn.createStatement();
            ResultSet departmentID_results = departmentID_statement.executeQuery(departmentID_query);

            if (departmentID_results.next()){
                do{
                    int instructorId= departmentID_results.getInt("DeptId");
                    departmentID = instructorId;
                } while (departmentID_results.next());
            }


        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return departmentID;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getDepartmentActivity(String departmentTitle){

        String status = null;
        int statusID = 0;

        try {
            String department_query = "SELECT * FROM dbo.Departments WHERE russian = N'" + departmentTitle +"'";
            Statement department_statement = MineOperations.conn.createStatement();
            ResultSet department_results = department_statement.executeQuery(department_query);

            if (department_results.next()){
                do{
                    int isActiveId= department_results.getInt("isActive");
                    statusID = isActiveId;
                } while (department_results.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        if (statusID == 0){
            status = "Неактивен";
        }  else {
            status = "Активен";
        }

        return status;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void paintComponent(Graphics g){
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.drawImage(bg_image,0,0,this);
        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }

    private class AddDepartmentFrame extends JFrame{

        public AddDepartmentFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();
        }

        private void CreateFrame(){

            JPanel departmentsInfo_panel = new JPanel();
            departmentsInfo_panel.setBackground(Color.WHITE);
            departmentsInfo_panel.setLayout(new BorderLayout());
            this.add(departmentsInfo_panel);

            JLabel departments_label = new JLabel("  Название отдела:  ");
            departments_label.setForeground(Color.BLACK);
            departmentsInfo_panel.add(departments_label, BorderLayout.WEST);

            JTextField departments_textField = new JTextField();
            departmentsInfo_panel.add(departments_textField, BorderLayout.CENTER);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel buttons_panel = new JPanel();
            buttons_panel.setBackground(Color.WHITE);
            buttons_panel.setLayout(new GridLayout(1,2));
            this.add(buttons_panel);

            JButton save_button = new JButton("Сохранить");
            save_button.setBackground(Color.WHITE);
            buttons_panel.add(save_button);
            save_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (departments_textField.getText() == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите название отдела");
                    } else {
                        int maxIDDepartment = 0;
                        String checkDepartment_query = "SELECT * FROM dbo.Departments WHERE russian = N'" + departments_textField.getText() +"'";
                        try {
                            Statement checkDepartment_st = MineOperations.conn.createStatement();
                            ResultSet departmentExists_rs = checkDepartment_st.executeQuery(checkDepartment_query);
                            if (!departmentExists_rs.next()){
                                String maxInstructorId_query = "SELECT max (DeptId) as DeptId FROM dbo.Departments";
                                try{
                                    Statement maxDepartmentId_st = MineOperations.conn.createStatement();
                                    ResultSet maxDepartmentId_rs = maxDepartmentId_st.executeQuery(maxInstructorId_query);
                                    if (maxDepartmentId_rs.next()){
                                        maxIDDepartment = maxDepartmentId_rs.getInt(1);
                                        System.out.println(maxIDDepartment);
                                    }
                                } catch (SQLException ex){
                                    ex.printStackTrace();
                                }

                                maxIDDepartment++;

                                String insert_query = "INSERT INTO dbo.Departments " +
                                        "(DeptId, Departmant, russian, isActive) " +
                                        "VALUES (" + maxIDDepartment + ", N'" + departments_textField.getText() + "', N'" +  departments_textField.getText() + "', 1)";
                                System.out.println(insert_query);

                                PreparedStatement insertDepartment = MineOperations.conn.prepareStatement(insert_query);
                                insertDepartment.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Инструктор успешно добавлен");

                                departments_tableModel.addRow(new Object[]{maxIDDepartment,departments_textField.getText(), "Активен" });
                                departments_tableModel.fireTableDataChanged();

                                dispose();

                            } else {
                                JOptionPane.showMessageDialog(MineOperations.cardPane,"Инструктор: " +
                                        departments_textField.getText() + " уже существует");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            JButton edit_button = new JButton("Отменить");
            edit_button.setBackground(Color.WHITE);
            buttons_panel.add(edit_button);
            edit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });


        }

    }

    private class EditDepartmentFrame extends JFrame{

        public EditDepartmentFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel departmentInfo_panel = new JPanel();
            departmentInfo_panel.setBackground(Color.WHITE);
            departmentInfo_panel.setLayout(new GridLayout(1,2));
            this.add(departmentInfo_panel);

            JLabel department_label = new JLabel(departmentSelected, SwingConstants.CENTER);
            department_label.setForeground(Color.BLACK);
            departmentInfo_panel.add(department_label);

            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            departmentInfo_panel.add(isActive_box);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel buttons_panel = new JPanel();
            buttons_panel.setBackground(Color.WHITE);
            buttons_panel.setLayout(new GridLayout(1,2));
            this.add(buttons_panel);

            JButton save_button = new JButton("Сохранить");
            save_button.setBackground(Color.WHITE);
            buttons_panel.add(save_button);
            save_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        int isActive_int = isActive_box.getSelectedItem() == "Неактивен" ? 0:1;
                        String update_query = "UPDATE dbo.Departments set isActive = " + isActive_int + "WHERE russian = N'" + department_label.getText() + "'";
                        PreparedStatement updateDepartment_pst = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Отдел успешно обнавлен");
                        updateDepartment_pst.executeUpdate();

                        departments_tableModel.setValueAt(isActive_box.getSelectedItem(),departments_table.getSelectedRow(),2);

                        dispose();

                    } catch (SQLException ex){
                        ex.printStackTrace();
                    }
                }
            });

            JButton edit_button = new JButton("Отменить");
            edit_button.setBackground(Color.WHITE);
            buttons_panel.add(edit_button);
            edit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
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
