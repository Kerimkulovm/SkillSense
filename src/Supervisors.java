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
import java.util.Objects;

public class Supervisors extends JPanel {

    private BufferedImage logo_image, bg_image;
    private JTable supervisors_table;
    private DefaultTableModel supervisors_tableModel;
    public String supervisorsSelected;
    public List<String> supervisors_list = new ArrayList<>();

    public Supervisors(){

        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Руководители'</big><br /></html>");
        titleEng.setBounds(160, 0, 600, 100);
        titleEng.setFont(Font.getFont("Lena"));
        titleEng.setForeground(Color.BLACK);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JTable supervisors_panel = new JTable();
        supervisors_panel.setBackground(Color.WHITE);
        supervisors_panel.setBounds(20,120,650,500);
        supervisors_panel.setLayout(new BorderLayout());
        this.add(supervisors_panel);

        JPanel buttons_panel = new JPanel(new GridLayout(1,2,5,0));
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20,635,300,30);
        this.add(buttons_panel);

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

        buildTable();
        JScrollPane supervisors_scrollPane = new JScrollPane(supervisors_table);
        supervisors_scrollPane.setBackground(Color.WHITE);
        supervisors_panel.add(supervisors_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton edit_button = new JButton("Изменить");
        edit_button.setForeground(Color.BLACK);
        edit_button.setBackground(Color.WHITE);
        edit_button.setFont(Font.getFont("Lena"));
        edit_button.setBorder(new RoundedBorder(10));
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditSupervisorFrame editSupervisorFrame = new EditSupervisorFrame();
                editSupervisorFrame.setVisible(true);
                editSupervisorFrame.pack();
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
                AddSupervisorFrame createSupervisorFrame = new AddSupervisorFrame();
                createSupervisorFrame.setVisible(true);
                createSupervisorFrame.pack();
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void buildTable(){

        supervisors_list = loadSupervisors(supervisors_list);

        supervisors_tableModel = new DefaultTableModel(supervisors_list.size(),3){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        DefaultTableCellRenderer supervisors_cellRenderer = new DefaultTableCellRenderer();
        supervisors_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        supervisors_table = new JTable(supervisors_tableModel);
        supervisors_table.setBorder(new LineBorder(Color.BLACK));
        supervisors_table.setBackground(Color.WHITE);
        supervisors_table.setRowHeight(20);

        JTableHeader supervisors_header = supervisors_table.getTableHeader();
        supervisors_header.setBorder(new LineBorder(Color.BLACK));
        supervisors_header.setBackground(Color.WHITE);
        supervisors_header.setFont(Font.getFont("Lena"));

        TableColumnModel supervisors_columns = supervisors_header.getColumnModel();

        TableColumn tabCol0 = supervisors_columns.getColumn(0);
        tabCol0.setHeaderValue("ID Руководителя");
        tabCol0.setCellRenderer(supervisors_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = supervisors_columns.getColumn(1);
        tabCol1.setHeaderValue("Руководитель");
        tabCol1.setCellRenderer(supervisors_cellRenderer);
        tabCol1.setPreferredWidth(50);

        TableColumn tabCol2 = supervisors_columns.getColumn(2);
        tabCol2.setHeaderValue("Статус");
        tabCol2.setCellRenderer(supervisors_cellRenderer);
        tabCol2.setPreferredWidth(50);

        for (int i = 0; i <  supervisors_table.getRowCount(); i++){
            supervisors_table.setValueAt(getSupervisorID(supervisors_list.get(i)),i,0);
            supervisors_table.setValueAt(supervisors_list.get(i),i,1);
            supervisors_table.setValueAt(getSupervisorActivity(supervisors_list.get(i)),i,2);
        }

        supervisors_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    supervisorsSelected = (String) supervisors_table.getValueAt(supervisors_table.getSelectedRow(),1);
                    System.out.println(supervisorsSelected);
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<String> loadSupervisors(List<String> inputList){

        inputList.clear();

        try {
            String supervisors_query = "SELECT * FROM dbo.Supervisors";
            Statement supervisors_statement = MineOperations.conn.createStatement();
            ResultSet supervisors_results = supervisors_statement.executeQuery(supervisors_query);

            if (supervisors_results.next()){
                do{
                    String supervisorName= supervisors_results.getString("Russian");
                    inputList.add(supervisorName);
                } while (supervisors_results.next());
            }


        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return inputList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getSupervisorID(String supervisorName){

        int supervisorID = 0;

        try {
            String supervisors_query = "SELECT * FROM dbo.Supervisors WHERE Russian = N'" + supervisorName +"'";
            Statement supervisor_statement = MineOperations.conn.createStatement();
            ResultSet supervisor_results = supervisor_statement.executeQuery(supervisors_query);
            if (supervisor_results.next()){
                do{
                    int supervisorId= supervisor_results.getInt("SupervisorId");
                    supervisorID = supervisorId;
                } while (supervisor_results.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return supervisorID;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getSupervisorActivity(String supervisorName){

        String status = null;
        int statusID = 0;

        try {
            String supervisorName_query = "SELECT * FROM dbo.Supervisors WHERE Russian = N'" + supervisorName +"'";
            Statement supervisorName_statement = MineOperations.conn.createStatement();
            ResultSet supervisor_results = supervisorName_statement.executeQuery(supervisorName_query);

            if (supervisor_results.next()){
                do{
                    int isActiveId= supervisor_results.getInt("isActive");
                    statusID = isActiveId;
                } while (supervisor_results.next());
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

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }


    protected void paintComponent(Graphics g){
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.drawImage(bg_image,0,0,this);
        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }


    private class AddSupervisorFrame extends JFrame{

        public AddSupervisorFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel supervisorInfo_panel = new JPanel();
            supervisorInfo_panel.setBackground(Color.WHITE);
            supervisorInfo_panel.setLayout(new BorderLayout());
            this.add(supervisorInfo_panel);

            JLabel supervisor_label = new JLabel("  Ф.И.О Руководителя:  ");
            supervisor_label.setForeground(Color.BLACK);
            supervisorInfo_panel.add(supervisor_label, BorderLayout.WEST);

            JTextField supervisor_textField = new JTextField();
            supervisorInfo_panel.add(supervisor_textField, BorderLayout.CENTER);

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
                    if (supervisor_textField.getText() == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите имя руководителя");
                    } else {
                        int maxIDSupervisor = 0;
                        String checkSupervisor_query = "SELECT * FROM dbo.Supervisors WHERE Russian = N'" + supervisor_textField.getText() +"'";
                        try {
                            Statement checkSupervisor_st = MineOperations.conn.createStatement();
                            ResultSet supervisorExists_rs = checkSupervisor_st.executeQuery(checkSupervisor_query);
                            if (!supervisorExists_rs.next()){
                                String maxSupervisorId_query = "SELECT max (SupervisorId) as SupervisorId FROM dbo.Supervisors";
                                try{
                                    Statement maxSupervisorId_st = MineOperations.conn.createStatement();
                                    ResultSet maxSupervisorId_rs = maxSupervisorId_st.executeQuery(maxSupervisorId_query);
                                    if (maxSupervisorId_rs.next()){
                                        maxIDSupervisor = maxSupervisorId_rs.getInt(1);
                                        System.out.println(maxIDSupervisor);
                                    }
                                } catch (SQLException ex){
                                    ex.printStackTrace();
                                }

                                maxIDSupervisor++;

                                String insert_query = "INSERT INTO dbo.Supervisors " +
                                        "(SupervisorId, Supervisor, Russian, isActive) " +
                                        "VALUES (" + maxIDSupervisor + ", ? , ? , 1)";

                                PreparedStatement insertSupervisor = MineOperations.conn.prepareStatement(insert_query);
                                insertSupervisor.setString(1,supervisor_textField.getText());
                                insertSupervisor.setString(2,supervisor_textField.getText());
                                insertSupervisor.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Руководитель успешно добавлен");

                                supervisors_tableModel.addRow(new Object[]{maxIDSupervisor,supervisor_textField.getText(), "Активен" });
                                supervisors_tableModel.fireTableDataChanged();

                                dispose();

                            } else {
                                JOptionPane.showMessageDialog(MineOperations.cardPane,"Руководитель: " +
                                        supervisor_textField.getText() + " уже существует");
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

    private class EditSupervisorFrame extends JFrame{



        public EditSupervisorFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel supervisorInfo_panel = new JPanel();
            supervisorInfo_panel.setBackground(Color.WHITE);
            supervisorInfo_panel.setLayout(new GridLayout(1,2));
            this.add(supervisorInfo_panel);

            JLabel supervisor_label = new JLabel(supervisorsSelected, SwingConstants.CENTER);
            supervisor_label.setForeground(Color.BLACK);
            supervisorInfo_panel.add(supervisor_label);

            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            supervisorInfo_panel.add(isActive_box);

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
                        String update_query = "UPDATE dbo.Supervisors set isActive = " + isActive_int + "WHERE Russian = N'" + supervisor_label.getText() + "'";
                        PreparedStatement updateSupervisor_pst = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Инструктор успешно обнавлен");
                        updateSupervisor_pst.executeUpdate();

                        supervisors_tableModel.setValueAt(isActive_box.getSelectedItem(), supervisors_table.getSelectedRow(),2);
                        supervisors_tableModel.fireTableDataChanged();
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
