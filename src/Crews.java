import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
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

public class Crews extends JPanel {

    private BufferedImage logo_image;

    private JTable crews_table;
    private DefaultTableModel crews_tableModel;

    public DatabaseQueries databaseQueries = new DatabaseQueries();
    public String crewsSelected;

    public List<String> crews_list = new ArrayList<>();


    public Crews(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Смены'</big><br /></html>");
        titleEng.setBounds(160, 0, 400, 100);
        titleEng.setFont(new Font("Helvetica", Font.BOLD, 20));
        titleEng.setForeground(Color.WHITE);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JTable crews_panel = new JTable();
        crews_panel.setBackground(Color.WHITE);
        crews_panel.setBounds(20,120,650,500);
        crews_panel.setLayout(new BorderLayout());
        this.add(crews_panel);

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
        JScrollPane crews_scrollPane = new JScrollPane(crews_table);
        crews_scrollPane.setBackground(Color.WHITE);
        crews_panel.add(crews_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton edit_button = new JButton("Изменить");
        edit_button.setForeground(Color.BLACK);
        edit_button.setBackground(Color.WHITE);
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditCrewFrame editCrewFrame= new EditCrewFrame();
                editCrewFrame.setVisible(true);
                editCrewFrame.pack();
            }
        });

        JButton create_button = new JButton("Создать");
        create_button.setBackground(Color.WHITE);
        create_button.setForeground(Color.BLACK);
        buttons_panel.add(create_button);
        create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCrewFrame addCrewFrame = new AddCrewFrame();
                addCrewFrame.setVisible(true);
                addCrewFrame.pack();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void buildTable(){

        crews_list = loadCrews(crews_list);

        crews_tableModel = new DefaultTableModel(crews_list.size(),3){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        DefaultTableCellRenderer crews_cellRenderer = new DefaultTableCellRenderer();
        crews_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        crews_table = new JTable(crews_tableModel);
        crews_table.setBorder(new LineBorder(Color.BLACK));
        crews_table.setBackground(Color.WHITE);
        crews_table.setRowHeight(20);

        JTableHeader crews_header = crews_table.getTableHeader();
        crews_header.setBorder(new LineBorder(Color.BLACK));
        crews_header.setBackground(Color.WHITE);
        crews_header.setFont(new Font("Helvetica", Font.BOLD,12));

        TableColumnModel crews_columns = crews_header.getColumnModel();

        TableColumn tabCol0 = crews_columns.getColumn(0);
        tabCol0.setHeaderValue("ID Смены");
        tabCol0.setCellRenderer(crews_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = crews_columns.getColumn(1);
        tabCol1.setHeaderValue("Смена");
        tabCol1.setCellRenderer(crews_cellRenderer);
        tabCol1.setPreferredWidth(50);

        TableColumn tabCol2 = crews_columns.getColumn(2);
        tabCol2.setHeaderValue("Статус");
        tabCol2.setCellRenderer(crews_cellRenderer);
        tabCol2.setPreferredWidth(50);

        for (int i = 0; i <  crews_table.getRowCount();i++){
            crews_table.setValueAt(getCrewID(crews_list.get(i)),i,0);
            crews_table.setValueAt(crews_list.get(i),i,1);
            crews_table.setValueAt(getCrewActivity(crews_list.get(i)),i,2);
        }

        crews_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    crewsSelected = (String) crews_table.getValueAt(crews_table.getSelectedRow(),1);
                    System.out.println(crewsSelected);
                }
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<String> loadCrews(List<String> inputList){

        inputList.clear();

        try {

            String crews_query = "SELECT * FROM dbo.Crews";
            Statement crews_st = MineOperations.conn.createStatement();
            ResultSet crews_rs = crews_st.executeQuery(crews_query);

            if (crews_rs.next()){
                do {
                    String departmentsTitle = crews_rs.getString("Crew");
                    inputList.add(departmentsTitle);
                } while (crews_rs.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return  inputList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getCrewID(String crewTitle){

        int crewID = 0;

        try {
            String crewID_query = "SELECT * FROM dbo.Crews WHERE Crew = N'" + crewTitle +"'";
            Statement crewID_statement = MineOperations.conn.createStatement();
            ResultSet crewID_results = crewID_statement.executeQuery(crewID_query);

            if (crewID_results.next()){
                do{
                    int crewrId= crewID_results.getInt("CrewNo");
                    crewID = crewrId;
                } while (crewID_results.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return crewID;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getCrewActivity(String departmentTitle){

        String status = null;
        int statusID = 0;

        try {
            String crew_query = "SELECT * FROM dbo.Crews WHERE Crew = N'" + departmentTitle +"'";
            Statement crew_statement = MineOperations.conn.createStatement();
            ResultSet crew_results = crew_statement.executeQuery(crew_query);

            if (crew_results.next()){
                do{
                    int isActiveId= crew_results.getInt("isActive");
                    statusID = isActiveId;
                } while (crew_results.next());
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

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }

    private class AddCrewFrame extends JFrame{

        public AddCrewFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel crewInfo_panel = new JPanel();
            crewInfo_panel.setBackground(Color.WHITE);
            crewInfo_panel.setLayout(new BorderLayout());
            this.add(crewInfo_panel);

            JLabel crew_label = new JLabel("  Название смены:  ");
            crew_label.setForeground(Color.BLACK);
            crewInfo_panel.add(crew_label, BorderLayout.WEST);

            JTextField crew_textField = new JTextField();
            crewInfo_panel.add(crew_textField, BorderLayout.CENTER);


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
                    if (crew_textField.getText() == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите название смены");
                    } else {
                        int maxIDCrew = 0;
                        String checkCrew_query = "SELECT * FROM dbo.Crews WHERE Crew = N'" + crew_textField.getText() +"'";
                        try {
                            Statement checkCrew_st = MineOperations.conn.createStatement();
                            ResultSet crewExists_rs = checkCrew_st.executeQuery(checkCrew_query);
                            if (!crewExists_rs.next()){
                                String maxCrewId_query = "SELECT max (CrewNo) as CrewNo FROM dbo.Crews";
                                try{
                                    Statement maxCrewId_st = MineOperations.conn.createStatement();
                                    ResultSet maxCrewId_rs = maxCrewId_st.executeQuery(maxCrewId_query);
                                    if (maxCrewId_rs.next()){
                                        maxIDCrew = maxCrewId_rs.getInt(1);
                                        System.out.println(maxIDCrew);
                                    }
                                } catch (SQLException ex){
                                    ex.printStackTrace();
                                }

                                maxIDCrew++;

                                String insert_query = "INSERT INTO dbo.Crews " +
                                        "(CrewNo, Crew, isActive) " +
                                        "VALUES (" + maxIDCrew + ", N'" +  crew_textField.getText() + "', 1)";
                                System.out.println(insert_query);

                                PreparedStatement insertCrew = MineOperations.conn.prepareStatement(insert_query);
                                insertCrew.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Смена успешно добавлена");


                                crews_tableModel.addRow(new Object[]{maxIDCrew,crew_textField.getText(), "Активен" });
                                crews_tableModel.fireTableDataChanged();

                                dispose();

                            } else {
                                JOptionPane.showMessageDialog(MineOperations.cardPane,"Смена: " +
                                        crew_textField.getText() + " уже существует");
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

    private class EditCrewFrame extends JFrame{

        public EditCrewFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel crewInfo_panel = new JPanel();
            crewInfo_panel.setBackground(Color.WHITE);
            crewInfo_panel.setLayout(new GridLayout(1,2));
            this.add(crewInfo_panel);

            JLabel crew_label = new JLabel(crewsSelected, SwingConstants.CENTER);
            crew_label.setForeground(Color.BLACK);
            crewInfo_panel.add(crew_label);

            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            crewInfo_panel.add(isActive_box);

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
                        String update_query = "UPDATE dbo.Crews set isActive = " + isActive_int + "WHERE Crew = N'" + crew_label.getText() + "'";
                        PreparedStatement updateCrew_pst = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Инструктор успешно обнавлен");
                        updateCrew_pst.executeUpdate();

                        crews_tableModel.setValueAt(isActive_box.getSelectedItem(),crews_table.getSelectedRow(),2);

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
