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
import java.util.List;
import java.util.Objects;

public class Courses extends JPanel {

    private BufferedImage logo_image, bg_image;
    private JTextField newCourse_text = null;
    private JTextField hiddenCourseId_text = new JTextField();
    private JTextField hiddenCourseName_text = new JTextField();
    private JTextField hiddenIsActive_text = new JTextField();

    private final JPanel coursesList_panel;
    private JTable listOfCourses_table;
    private DefaultTableModel listOfCourses_model;
    private int numOfRows = 1, numOfColumns = 3;

    private List<Integer> coursesNum_list = new ArrayList<>();
    private List<String> coursesName_list = new ArrayList<>();
    private List<Integer> coursesActive_list = new ArrayList<>();

    private JButton  add_button;
    private JButton  edit_button;
    private JButton  save_button;
    private JButton  cancel_button;

    private JComboBox isActive_box;


    public Courses()
    {
        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Курсы'</big></html>");
        titleEng.setBounds(160, 0, 600, 100);
        titleEng.setBackground(Color.WHITE);
        titleEng.setForeground(Color.BLACK);
        titleEng.setFont(Font.getFont("Lena"));
        this.add(titleEng);
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
                MineOperations.card.show(MineOperations.cardPane,"Classificatory");
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        coursesList_panel = new JPanel();
        coursesList_panel.setBackground(Color.WHITE);
        coursesList_panel.setBounds(20, 120, 650, 500);
        coursesList_panel.setLayout(new BorderLayout());
        coursesList_panel.setBorder(new LineBorder(Color.BLACK));
        coursesList_panel.setVisible(true);
        this.add(coursesList_panel);

        createTable();

        JPanel buttons_panel = new JPanel(new GridLayout());
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20, 635, 300, 30);
        buttons_panel.setLayout(new GridLayout(1, 5,5,0));
        this.add(buttons_panel);

        edit_button = new JButton("Изменить");
        edit_button.setBackground(Color.WHITE);
        edit_button.setForeground(Color.BLACK);
        edit_button.setFont(Font.getFont("Lena"));
        edit_button.setBorder(new RoundedBorder(10));
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Courses.editCourse editCourse = new Courses.editCourse();
                editCourse.pack();
                editCourse.setVisible(true);
            }
        });

        add_button = new JButton("Создать");
        add_button.setBackground(Color.WHITE);
        add_button.setForeground(Color.BLACK);
        add_button.setFont(Font.getFont("Lena"));
        add_button.setBorder(new RoundedBorder(10));
        buttons_panel.add(add_button);
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Courses.createNewCourse newCourse = new Courses.createNewCourse();
                newCourse.pack();
                newCourse.setVisible(true);
            }
        });
    }

    private void loadCourses(){

        coursesActive_list.clear();

        try{
            Statement searchStatement = MineOperations.conn.createStatement();
            String query_text = "select coarseNo, Course, isActive from Courses where course is not null order by CoarseNo";

            ResultSet rs = searchStatement.executeQuery(query_text);

            if (rs.next()){

                do {

                    int courseNum = rs.getInt("coarseNo");
                    coursesNum_list.add(courseNum);

                    String courseName = rs.getString("Course");
                    coursesName_list.add(courseName);

                    int isActive = rs.getInt("isActive");
                    coursesActive_list.add(isActive);

                }while (rs.next());
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void createTable(){

        loadCourses();

        numOfRows = coursesName_list.size();

        listOfCourses_model = new DefaultTableModel(numOfRows, numOfColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRederer = new DefaultTableCellRenderer();
        centerRederer.setHorizontalAlignment(JLabel.CENTER);

        listOfCourses_table = new JTable(listOfCourses_model);
        listOfCourses_table.setBorder(new LineBorder(Color.BLACK));
        listOfCourses_table.setBackground(Color.WHITE);
        listOfCourses_table.setRowHeight(25);
        listOfCourses_table.setVisible(true);

        JTableHeader listHeader= listOfCourses_table.getTableHeader();
        listHeader.setBorder(new LineBorder(Color.BLACK));
        listHeader.setBackground(Color.WHITE);
        listHeader.setFont(Font.getFont("Lena"));

        TableColumnModel listOfCourses_columns = listHeader.getColumnModel();

        TableColumn tabCol0 = listOfCourses_columns.getColumn(0);
        tabCol0.setHeaderValue("Номер");
        tabCol0.setCellRenderer(centerRederer);
        tabCol0.setPreferredWidth(10);
        for (int i = 0; i < numOfRows; i++){
            listOfCourses_table.setValueAt(coursesNum_list.get(i),i,0);
        }

        TableColumn tabCol1 = listOfCourses_columns.getColumn(1);
        tabCol1.setHeaderValue("Курс");
        tabCol1.setCellRenderer(centerRederer);
        tabCol1.setPreferredWidth(200);
        for (int i = 0; i < numOfRows; i++){
            listOfCourses_table.setValueAt(coursesName_list.get(i),i,1);
        }

        TableColumn tabCol2 = listOfCourses_columns.getColumn(2);
        tabCol2.setCellRenderer(centerRederer);
        tabCol2.setHeaderValue("Активный");
        tabCol2.setPreferredWidth(10);
        for (int i = 0; i < numOfRows; i++){
            String v = coursesActive_list.get(i) == 0 ? "Нет" : "Да";
            listOfCourses_table.setValueAt(v ,i,2);
        }

        coursesList_panel.add(new JScrollPane(listOfCourses_table));

        listOfCourses_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    int index1 = listOfCourses_table.getSelectedRow();//Get the selected row
                    hiddenCourseId_text.setText(listOfCourses_table.getValueAt(index1,0).toString());
                    hiddenCourseName_text.setText(listOfCourses_table.getValueAt(index1,1).toString());
                    hiddenIsActive_text.setText(listOfCourses_table.getValueAt(index1,2).toString());
                    System.out.println("id = " + hiddenCourseId_text.getText());
                    System.out.println("name = " + hiddenCourseName_text.getText());
                    System.out.println("isactive = " + hiddenIsActive_text.getText());
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.drawImage(bg_image,0,0,this);
        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }

    private class createNewCourse extends JFrame{

        private JPanel  newCoursePanel, newCourseButtonsPanel;

        public createNewCourse(){

            buildFrame();

            this.setTitle("Добавить курс");
            this.setPreferredSize(new Dimension(400, 120));
            this.setLayout(new GridLayout(2,1));
            this.setFocusableWindowState(true);
            this.setAutoRequestFocus(true);
            this.setLocation(900,40);
        }

        private void buildFrame(){

            newCoursePanel = new JPanel();
            newCoursePanel.setBackground(Color.WHITE);
            newCoursePanel.setLayout(new BorderLayout());
            newCoursePanel.setBorder(new LineBorder(Color.BLACK));
            newCoursePanel.setVisible(true);
            this.add(newCoursePanel);

            JLabel newCourse_label = new JLabel("Наименование: ");
            newCoursePanel.add(newCourse_label);

            newCourse_text = new JTextField();
            newCourse_text.setEnabled(true);
            newCourse_text.setForeground(Color.BLACK);
            newCourse_text.setDisabledTextColor(Color.BLACK);
            newCoursePanel.add(newCourse_text);

            newCourseButtonsPanel = new JPanel();
            newCourseButtonsPanel.setBackground(Color.WHITE);
            newCourseButtonsPanel.setLayout(new GridLayout(1,2));
            newCourseButtonsPanel.setBorder(new LineBorder(Color.BLACK));
            newCourseButtonsPanel.setVisible(true);
            this.add(newCourseButtonsPanel);

            save_button = new JButton("Сохранить");
            save_button.setForeground(Color.BLACK);
            save_button.setBackground(Color.WHITE);
            save_button.setFont(new Font("Helvetica", Font.BOLD, 10));
            newCourseButtonsPanel.add(save_button);
            save_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (newCourse_text.getText().equals("") ){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите название курса");
                    } else {
                            int maxCourse = 0;
                            String checkCourse_query = "SELECT * FROM dbo.Courses WHERE Course = '" + newCourse_text.getText() + "'";
                            try {
                                Statement checkCourse_st = MineOperations.conn.createStatement();
                                ResultSet courseExist_result = checkCourse_st.executeQuery(checkCourse_query);
                                if (!courseExist_result.next()) {
                                    String MaxCourse_query = "SELECT max (coarseNo) as coarseNo FROM dbo.Courses";
                                    try{
                                        Statement maxCourse_st = MineOperations.conn.createStatement();
                                        ResultSet maxCourse_result = maxCourse_st.executeQuery(MaxCourse_query);
                                        if (maxCourse_result.next()) {
                                            maxCourse = maxCourse_result.getInt(1);
                                            System.out.println("maxId = " + maxCourse);
                                        }
                                    }catch (SQLException ex) {
                                        ex.printStackTrace();
                                    }

                                    maxCourse++;
                                    String insert_query = "INSERT INTO dbo.Courses " +
                                            "(coarseNo, Course  , isActive ) " +
                                            "VALUES (" + maxCourse +  ",N'" +
                                            newCourse_text.getText() + "', 1)";

                                    System.out.println(insert_query);
                                    PreparedStatement insertCourse = MineOperations.conn.prepareStatement(insert_query);
                                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Курс успешно добавлен");
                                    insertCourse.executeUpdate();

                                    listOfCourses_model.addRow(new Object[]{maxCourse, newCourse_text.getText(),"Да"});
                                    listOfCourses_model.fireTableDataChanged();
                                    dispose();
                                    updateComboboxes();
                                } else {
                                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Курс: " +
                                                newCourse_text.getText() + " уже существует");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                    }
                }
            });

            cancel_button = new JButton("Отмена");
            cancel_button.setBackground(Color.WHITE);
            cancel_button.setForeground(Color.BLACK);
            cancel_button.setFont(new Font("Helvetica", Font.BOLD, 10));
            newCourseButtonsPanel.add(cancel_button);
            cancel_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
    }

    private class editCourse extends JFrame{

        public editCourse(){

            buildFrame();

            this.setPreferredSize(new Dimension(400, 150));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setLocation(900,40);
        }

        private void buildFrame(){

            JPanel newCoursePanel = new JPanel();
            newCoursePanel.setBackground(Color.WHITE);
            newCoursePanel.setBounds(2,2,250,30);
            newCoursePanel.setLayout(new GridLayout(2,1));
            newCoursePanel.setBorder(new LineBorder(Color.BLACK));
            newCoursePanel.setVisible(true);

            this.add(newCoursePanel);

            JLabel newCourse_label = new JLabel(hiddenCourseName_text.getText());

            newCoursePanel.add(newCourse_label);

            String[] status = new String[]{"Да","Нет"};
            isActive_box = new JComboBox(status);
            isActive_box.setBackground(Color.WHITE);
            newCoursePanel.add(isActive_box);
            isActive_box.getModel().setSelectedItem(hiddenIsActive_text.getText());

            JPanel newCourseButtonsPanel = new JPanel();
            newCourseButtonsPanel.setBackground(Color.WHITE);
            newCourseButtonsPanel.setLayout(new GridLayout(1,2));
            newCourseButtonsPanel.setBorder(new LineBorder(Color.BLACK));
            newCourseButtonsPanel.setVisible(true);
            this.add(newCourseButtonsPanel);

            save_button = new JButton("Сохранить");
            save_button.setForeground(Color.BLACK);
            save_button.setBackground(Color.WHITE);
            save_button.setFont(new Font("Helvetica", Font.BOLD, 10));
            newCourseButtonsPanel.add(save_button);
            save_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                            Integer isact = isActive_box.getSelectedItem() == "Нет" ? 0 : 1;
                        System.out.println("Isact = " + isact);
                            String insert_query = "update dbo.Courses set isActive = " + isact + " where coarseNo = " + hiddenCourseId_text.getText() ;
                            PreparedStatement insertCourse = MineOperations.conn.prepareStatement(insert_query);
                            JOptionPane.showMessageDialog(MineOperations.cardPane,"Курс успешно обновлен");
                            insertCourse.executeUpdate();

                            listOfCourses_model.setValueAt(isActive_box.getSelectedItem(),listOfCourses_table.getSelectedRow(),2);

                            dispose();
                            updateComboboxes();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
            });

            cancel_button = new JButton("Отмена");
            cancel_button.setBackground(Color.WHITE);
            cancel_button.setForeground(Color.BLACK);
            cancel_button.setFont(new Font("Helvetica", Font.BOLD, 10));
            newCourseButtonsPanel.add(cancel_button);
            cancel_button.addActionListener(new ActionListener() {
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
