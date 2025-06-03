import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Courses extends JPanel {

    private BufferedImage logo_image, bg_image;

    private JTable courses_table;
    private DefaultTableModel courses_tableModel;

    public DatabaseQueries databaseQueries = new DatabaseQueries();
    public String courseSelected;
    public String statusSelected;

    public List<String> course_list = new ArrayList<>();


    public Courses(){

        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Курсы'</big><br /></html>");
        titleEng.setBounds(160, 0, 600, 100);
        titleEng.setFont(Font.getFont("Lena"));
        titleEng.setForeground(Color.BLACK);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JTable courses_panel = new JTable();
        courses_panel.setBackground(Color.WHITE);
        courses_panel.setBounds(20,120,650,500);
        courses_panel.setLayout(new BorderLayout());
        this.add(courses_panel);

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
        JScrollPane instructors_scrollPane = new JScrollPane(courses_table);
        instructors_scrollPane.setBackground(Color.WHITE);
        courses_panel.add(instructors_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (LoginWin.user.getRoleid() == 1 || LoginWin.user.getRoleid() == 2) {
            JButton edit_button = new JButton("Изменить");
            edit_button.setForeground(Color.BLACK);
            edit_button.setBackground(Color.WHITE);
            edit_button.setBorder(new RoundedBorder(10));
            edit_button.setFont(Font.getFont("Lena"));
            buttons_panel.add(edit_button);
            edit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (courseSelected == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Необходимо выбрать курс");
                    } else {
                        EditCourseFrame editCourseFrame = new EditCourseFrame();
                        editCourseFrame.setVisible(true);
                        editCourseFrame.pack();
                    }
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
                    AddCourseFrame addCourseFrame = new AddCourseFrame();
                    addCourseFrame.setVisible(true);
                    addCourseFrame.pack();
                }
            });

        }

    }

    private void buildTable(){

        course_list = loadCourses(course_list);

        courses_tableModel = new DefaultTableModel(course_list.size(),3){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        DefaultTableCellRenderer courses_cellRenderer = new DefaultTableCellRenderer();
        courses_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        courses_table = new JTable(courses_tableModel);
        courses_table.setBorder(new LineBorder(Color.BLACK));
        courses_table.setBackground(Color.WHITE);
        courses_table.setRowHeight(20);

        JTableHeader courses_header = courses_table.getTableHeader();
        courses_header.setBorder(new LineBorder(Color.BLACK));
        courses_header.setBackground(Color.WHITE);
        courses_header.setFont(Font.getFont("Lena"));

        TableColumnModel courses_columns = courses_header.getColumnModel();

        TableColumn tabCol0 = courses_columns.getColumn(0);
        tabCol0.setHeaderValue("ID курса");
        tabCol0.setCellRenderer(courses_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = courses_columns.getColumn(1);
        tabCol1.setHeaderValue("Наименование курса");
        tabCol1.setCellRenderer(courses_cellRenderer);
        tabCol1.setPreferredWidth(50);

        TableColumn tabCol2 = courses_columns.getColumn(2);
        tabCol2.setHeaderValue("Статус");
        tabCol2.setCellRenderer(courses_cellRenderer);
        tabCol2.setPreferredWidth(50);

        for (int i = 0; i <  courses_table.getRowCount(); i++){
            courses_table.setValueAt(getCourseID(course_list.get(i)),i,0);
            courses_table.setValueAt(course_list.get(i),i,1);
            courses_table.setValueAt(getCourseActivity(course_list.get(i)),i,2);
        }

        courses_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    courseSelected = (String) courses_table.getValueAt(courses_table.getSelectedRow(),1);
                    statusSelected = (String) courses_table.getValueAt(courses_table.getSelectedRow(),2);

                }
            }
        });
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<String> loadCourses(List<String> inputList){

        inputList.clear();

        try {

            String courses_query = "SELECT * FROM Courses";
            Statement course_st = MineOperations.conn.createStatement();
            ResultSet course_rs = course_st.executeQuery(courses_query);

            if (course_rs.next()){
                do {
                    String coursesTitle = course_rs.getString("RusName");
                    inputList.add(coursesTitle);
                } while (course_rs.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return  inputList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public int getCourseID(String courseTitle){

        int courseID = 0;

        try {
            String courseID_query = "SELECT * FROM Courses WHERE RusName = N'" + courseTitle +"'";
            Statement st = MineOperations.conn.createStatement();
            ResultSet rs = st.executeQuery(courseID_query);

            if (rs.next()){
                do{
                    int courseId= rs.getInt("coarseno");
                    courseID = courseId;
                } while (rs.next());
            }


        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return courseID;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getCourseActivity(String courseTitle){

        String status = null;
        int statusID = 0;

        try {
            String query = "SELECT * FROM Courses WHERE RusName = N'" + courseTitle +"'";
            Statement st = MineOperations.conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()){
                do{
                    int isActiveId= rs.getInt("isActive");
                    statusID = isActiveId;
                } while (rs.next());
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

    private class AddCourseFrame extends JFrame{

        public AddCourseFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить курс");
            this.setLocation(200,200);

            CreateFrame();
        }

        private void CreateFrame(){

            JPanel coursesInfo_panel = new JPanel();
            coursesInfo_panel.setBackground(Color.WHITE);
            coursesInfo_panel.setLayout(new BorderLayout());
            this.add(coursesInfo_panel);

            JLabel courses_label = new JLabel("  Название курса:  ");
            courses_label.setForeground(Color.BLACK);
            coursesInfo_panel.add(courses_label, BorderLayout.WEST);

            JTextField courses_textField = new JTextField();
            coursesInfo_panel.add(courses_textField, BorderLayout.CENTER);

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
                    if (courses_textField.getText() == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите название курса");
                    } else {
                        int maxIDCourse = 0;
                        String query = "SELECT * FROM Courses WHERE RusName = N'" + courses_textField.getText() +"'";
                        try {
                            Statement st = MineOperations.conn.createStatement();
                            ResultSet rs = st.executeQuery(query);
                            if (!rs.next()){


                                String insert_query = "INSERT INTO Courses " +
                                        "( EngName, RusName, isActive) " +
                                        "VALUES ( ? , ? , 1)";

                                PreparedStatement ps = MineOperations.conn.prepareStatement(insert_query);
                                ps.setString(1, courses_textField.getText());
                                ps.setString(2, courses_textField.getText());
                                ps.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Курс успешно добавлен");

                                courses_tableModel.addRow(new Object[]{maxIDCourse,courses_textField.getText(), "Активен" });
                                courses_tableModel.fireTableDataChanged();

                                dispose();
                                updateComboboxes();
                                insert_query = "INSERT INTO Courses " +
                                        "( EngName, RusName, isActive) " +
                                        "VALUES ( '" + courses_textField.getText() + "' , '" + courses_textField.getText() + "' , 1)";
                                DatabaseQueries.saveLogs(insert_query, LoginWin.user.getId());
                            } else {
                                JOptionPane.showMessageDialog(MineOperations.cardPane,"Курс: " +
                                        courses_textField.getText() + " уже существует");
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

    private class EditCourseFrame extends JFrame{

        public EditCourseFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Изменить статус курса");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel courseInfo_panel = new JPanel();
            courseInfo_panel.setBackground(Color.WHITE);
            courseInfo_panel.setLayout(new GridLayout(1,2));
            this.add(courseInfo_panel);

            JLabel course_label = new JLabel(courseSelected, SwingConstants.CENTER);
            course_label.setForeground(Color.BLACK);
            courseInfo_panel.add(course_label);

            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            courseInfo_panel.add(isActive_box);
            isActive_box.setSelectedItem(statusSelected);
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
                    String update_query = "";
                    try{
                        int isActive_int = isActive_box.getSelectedItem() == "Неактивен" ? 0:1;
                        update_query = "UPDATE Courses set isActive = " + isActive_int + "WHERE RusName = N'" + course_label.getText() + "'";
                        PreparedStatement ps = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Курс успешно обнавлен");
                        ps.executeUpdate();

                        courses_tableModel.setValueAt(isActive_box.getSelectedItem(), courses_table.getSelectedRow(),2);

                        dispose();
                        updateComboboxes();
                    } catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    DatabaseQueries.saveLogs(update_query, LoginWin.user.getId());

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
    private void updateComboboxes() {
        OperationsDaily.CourseName_box.removeAllItems();
        OperationsDaily.CourseName_box = OperationsDaily.databaseQueries.loadCourseNameBox(OperationsDaily.CourseName_box);
    }
}
