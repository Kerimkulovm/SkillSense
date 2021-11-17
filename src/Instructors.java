import javax.imageio.ImageIO;
import javax.swing.*;
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

public class Instructors extends JPanel {

    private BufferedImage logo_image;

    private JTable instructors_table;
    private DefaultTableModel instructors_tableModel;

    public DatabaseQueries databaseQueries = new DatabaseQueries();
    public String instructorSelected;

    public List<String> instructors_list = new ArrayList<>();

    public Instructors(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Инструкторы'</big><br /></html>");
        titleEng.setBounds(160, 0, 400, 100);
        titleEng.setFont(new Font("Helvetica", Font.BOLD, 20));
        titleEng.setForeground(Color.WHITE);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JTable instructors_panel = new JTable();
        instructors_panel.setBackground(Color.WHITE);
        instructors_panel.setBounds(20,120,650,500);
        instructors_panel.setLayout(new BorderLayout());
        this.add(instructors_panel);

        JPanel buttons_panel = new JPanel(new GridLayout());
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20,635,300,30);
        this.add(buttons_panel);

        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.RED);
        exit_button.setForeground(Color.WHITE);
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Classificatory");
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buildTable();
        JScrollPane instructors_scrollPane = new JScrollPane(instructors_table);
        instructors_scrollPane.setBackground(Color.WHITE);
        instructors_panel.add(instructors_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton edit_button = new JButton("Изменить");
        edit_button.setForeground(Color.BLACK);
        edit_button.setBackground(Color.WHITE);
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditInstructorFrame editInstructorFrame = new EditInstructorFrame();
                editInstructorFrame.setVisible(true);
                editInstructorFrame.pack();
            }
        });

        JButton create_button = new JButton("Создать");
        create_button.setBackground(Color.WHITE);
        create_button.setForeground(Color.BLACK);
        buttons_panel.add(create_button);
        create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddInstructorFrame createInstructorFrame = new AddInstructorFrame();
                createInstructorFrame.setVisible(true);
                createInstructorFrame.pack();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void buildTable(){

        instructors_list = databaseQueries.loadInstructors(instructors_list);

        instructors_tableModel = new DefaultTableModel(instructors_list.size(),3){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        DefaultTableCellRenderer instructors_cellRenderer = new DefaultTableCellRenderer();
        instructors_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        instructors_table = new JTable(instructors_tableModel);
        instructors_table.setBorder(new LineBorder(Color.BLACK));
        instructors_table.setBackground(Color.WHITE);
        instructors_table.setRowHeight(20);

        JTableHeader instructors_header = instructors_table.getTableHeader();
        instructors_header.setBorder(new LineBorder(Color.BLACK));
        instructors_header.setBackground(Color.WHITE);
        instructors_header.setFont(new Font("Helvetica", Font.BOLD,12));

        TableColumnModel instructors_columns = instructors_header.getColumnModel();

        TableColumn tabCol0 = instructors_columns.getColumn(0);
        tabCol0.setHeaderValue("ID Инструктора");
        tabCol0.setCellRenderer(instructors_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = instructors_columns.getColumn(1);
        tabCol1.setHeaderValue("Инструктор");
        tabCol1.setCellRenderer(instructors_cellRenderer);
        tabCol1.setPreferredWidth(50);

        TableColumn tabCol2 = instructors_columns.getColumn(2);
        tabCol2.setHeaderValue("Статус");
        tabCol2.setCellRenderer(instructors_cellRenderer);
        tabCol2.setPreferredWidth(50);

        for (int i = 0; i <  instructors_table.getRowCount();i++){
            instructors_table.setValueAt(databaseQueries.getInstructorID(instructors_list.get(i)),i,0);
            instructors_table.setValueAt(instructors_list.get(i),i,1);
            instructors_table.setValueAt(databaseQueries.getInstructorActivity(instructors_list.get(i)),i,2);
        }

        instructors_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    int index = instructors_table.getSelectedRow();
                    instructorSelected = (String) instructors_table.getValueAt(instructors_table.getSelectedRow(),1);
                    System.out.println(instructorSelected);
                }
            }
        });
    }




    protected void paintComponent(Graphics g){
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }


    private class AddInstructorFrame extends JFrame{

        public AddInstructorFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel instructorInfo_panel = new JPanel();
            instructorInfo_panel.setBackground(Color.WHITE);
            instructorInfo_panel.setLayout(new BorderLayout());
            this.add(instructorInfo_panel);

            JLabel instructor_label = new JLabel("  Ф.И.О Инструктора:  ");
            instructor_label.setForeground(Color.BLACK);
            instructorInfo_panel.add(instructor_label, BorderLayout.WEST);

            JTextField instructor_textField = new JTextField();
            instructorInfo_panel.add(instructor_textField, BorderLayout.CENTER);

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
                    if (instructor_textField.getText() == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите имя инструктора");
                    } else {
                        int maxIDInstructor = 0;
                        String checkInstructor_query = "SELECT * FROM dbo.Instructor WHERE Инструктор = N'" + instructor_textField.getText() +"'";
                        try {
                            Statement checkInstructor_st = MineOperations.conn.createStatement();
                            ResultSet instructorExists_rs = checkInstructor_st.executeQuery(checkInstructor_query);
                            if (!instructorExists_rs.next()){
                                String maxInstructorId_query = "SELECT max (InstructoId) as InstructoId FROM dbo.Instructor";
                                try{
                                    Statement maxInstructorId_st = MineOperations.conn.createStatement();
                                    ResultSet maxInstructorId_rs = maxInstructorId_st.executeQuery(maxInstructorId_query);
                                    if (maxInstructorId_rs.next()){
                                        maxIDInstructor = maxInstructorId_rs.getInt(1);
                                        System.out.println(maxIDInstructor);
                                    }
                                } catch (SQLException ex){
                                    ex.printStackTrace();
                                }

                                maxIDInstructor++;

                                String insert_query = "INSERT INTO dbo.Instructor " +
                                        "(InstructoId, InstructorName, Инструктор, isActive) " +
                                        "VALUES (" + maxIDInstructor + ", N'" + instructor_textField.getText() + "', N'" +  instructor_textField.getText() + "', 1)";
                                System.out.println(insert_query);

                                PreparedStatement insertInstructor = MineOperations.conn.prepareStatement(insert_query);
                                insertInstructor.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Инструктор успешно добавлен");


                                instructors_tableModel.addRow(new Object[]{maxIDInstructor,instructor_textField.getText(), "Активен" });
                                instructors_tableModel.fireTableDataChanged();

                                dispose();

                            } else {
                                JOptionPane.showMessageDialog(MineOperations.cardPane,"Инструктор: " +
                                        instructor_textField.getText() + " уже существует");
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

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        }

    }

    private class EditInstructorFrame extends JFrame{



        public EditInstructorFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel instructorInfo_panel = new JPanel();
            instructorInfo_panel.setBackground(Color.WHITE);
            instructorInfo_panel.setLayout(new GridLayout(1,2));
            this.add(instructorInfo_panel);

            JLabel instructor_label = new JLabel(instructorSelected, SwingConstants.CENTER);
            instructor_label.setForeground(Color.BLACK);
            instructorInfo_panel.add(instructor_label);

            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            instructorInfo_panel.add(isActive_box);

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
                        String update_query = "UPDATE dbo.Instructor set isActive = " + isActive_int + "WHERE Инструктор = N'" + instructor_label.getText() + "'";
                        PreparedStatement updateInstructor_pst = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Инструктор успешно обнавлен");
                        updateInstructor_pst.executeUpdate();

                        instructors_tableModel.setValueAt(isActive_box.getSelectedItem(),instructors_table.getSelectedRow(),2);

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

}
