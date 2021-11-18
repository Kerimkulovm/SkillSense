import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
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

public class Positions extends JPanel {

    private BufferedImage logo_image;

    private JTable positions_table;
    private DefaultTableModel positions_tableModel;

    public DatabaseQueries databaseQueries = new DatabaseQueries();
    public String positionSelected;

    public List<String> positions_list = new ArrayList<>();


    public Positions(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Должности'</big><br /></html>");
        titleEng.setBounds(160, 0, 400, 100);
        titleEng.setFont(new Font("Helvetica", Font.BOLD, 20));
        titleEng.setForeground(Color.WHITE);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JTable positions_panel = new JTable();
        positions_panel.setBackground(Color.WHITE);
        positions_panel.setBounds(20,120,650,500);
        positions_panel.setLayout(new BorderLayout());
        this.add(positions_panel);

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
        JScrollPane positions_scrollPane = new JScrollPane(positions_table);
        positions_scrollPane.setBackground(Color.WHITE);
        positions_panel.add(positions_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton edit_button = new JButton("Изменить");
        edit_button.setForeground(Color.BLACK);
        edit_button.setBackground(Color.WHITE);
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditPositionFrame editPositionFrame = new EditPositionFrame();
                editPositionFrame.setVisible(true);
                editPositionFrame.pack();
            }
        });

        JButton create_button = new JButton("Создать");
        create_button.setBackground(Color.WHITE);
        create_button.setForeground(Color.BLACK);
        buttons_panel.add(create_button);
        create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPositionFrame createPositionFrame = new AddPositionFrame();
                createPositionFrame.setVisible(true);
                createPositionFrame.pack();
            }
        });
    }

    private void buildTable(){

        positions_list = databaseQueries.loadPositions(positions_list);

        positions_tableModel = new DefaultTableModel(positions_list.size(),3){
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        DefaultTableCellRenderer positions_cellRenderer = new DefaultTableCellRenderer();
        positions_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        positions_table = new JTable(positions_tableModel);
        positions_table.setBorder(new LineBorder(Color.BLACK));
        positions_table.setBackground(Color.WHITE);
        positions_table.setRowHeight(20);

        JTableHeader positions_header = positions_table.getTableHeader();
        positions_header.setBorder(new LineBorder(Color.BLACK));
        positions_header.setBackground(Color.WHITE);
        positions_header.setFont(new Font("Helvetica", Font.BOLD,12));

        TableColumnModel positions_columns = positions_header.getColumnModel();

        TableColumn tabCol0 = positions_columns.getColumn(0);
        tabCol0.setHeaderValue("ID Должности");
        tabCol0.setCellRenderer(positions_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = positions_columns.getColumn(1);
        tabCol1.setHeaderValue("Должность");
        tabCol1.setCellRenderer(positions_cellRenderer);
        tabCol1.setPreferredWidth(50);

        TableColumn tabCol2 = positions_columns.getColumn(2);
        tabCol2.setHeaderValue("Статус");
        tabCol2.setCellRenderer(positions_cellRenderer);
        tabCol2.setPreferredWidth(50);

        for (int i = 0; i <  positions_table.getRowCount();i++){
            positions_table.setValueAt(databaseQueries.getPositionID(positions_list.get(i)),i,0);
            positions_table.setValueAt(positions_list.get(i),i,1);
            positions_table.setValueAt(databaseQueries.getPositionActivity(positions_list.get(i)),i,2);
        }

        positions_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    positionSelected = (String) positions_table.getValueAt(positions_table.getSelectedRow(),1);
                    System.out.println(positionSelected);
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

    private class AddPositionFrame extends JFrame{

        public AddPositionFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();
        }

        private void CreateFrame(){


            JPanel position_panel = new JPanel();
            position_panel.setBackground(Color.WHITE);
            position_panel.setLayout(new BorderLayout());
            this.add(position_panel);

            JLabel position_label = new JLabel("  Позиция :  ");
            position_label.setForeground(Color.BLACK);
            position_panel.add(position_label, BorderLayout.WEST);

            JTextField position_textField = new JTextField();
            position_panel.add(position_textField, BorderLayout.CENTER);

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
                    if (position_textField.getText() == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите название должности");
                    } else {
                        int maxIDPosition = 0;
                        String checkPositionID_query = "SELECT * FROM dbo.JobTitles WHERE RusTitle = N'" + position_textField.getText() +"'";
                        try{
                            Statement checkPosition_st = MineOperations.conn.createStatement();
                            ResultSet positionExists_rs = checkPosition_st.executeQuery(checkPositionID_query);
                            if (!positionExists_rs.next()){
                                String maxPositionId_query = "SELECT max (JobTitleId) as JobTitleId FROM dbo.JobTitles";
                                try{
                                    Statement maxPositionID_st = MineOperations.conn.createStatement();
                                    ResultSet maxInstructorId_rs = maxPositionID_st.executeQuery(maxPositionId_query);
                                    if (maxInstructorId_rs.next()){
                                        maxIDPosition = maxInstructorId_rs.getInt(1);
                                        System.out.println(maxIDPosition);
                                    }
                                } catch (SQLException ex){
                                    ex.printStackTrace();
                                }

                                maxIDPosition++;

                                String insert_query = "INSERT INTO dbo.JobTitles " +
                                        "(JobTitleId, Title, RusTitle, isActive) " +
                                        "VALUES (" + maxIDPosition + ", N'" + position_textField.getText() + "', N'" + position_textField.getText()+ "', 1)";



                                PreparedStatement insertPosition = MineOperations.conn.prepareStatement(insert_query);
                                insertPosition.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Должность успешно добавлена");

                                positions_tableModel.addRow(new Object[]{maxIDPosition, position_textField.getText(), "Активен"});
                                positions_tableModel.fireTableDataChanged();

                                dispose();


                            }
                        } catch (SQLException ex){
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

    private class EditPositionFrame extends JFrame{

        public EditPositionFrame(){
            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить должность");
            this.setLocation(200,200);

            CreateFrame();
        }

        private void CreateFrame(){

            JPanel positionInfo_panel = new JPanel();
            positionInfo_panel.setBackground(Color.WHITE);
            positionInfo_panel.setLayout(new GridLayout(1,2));
            this.add(positionInfo_panel);

            JLabel position_label = new JLabel(positionSelected, SwingConstants.CENTER);
            position_label.setForeground(Color.BLACK);
            positionInfo_panel.add(position_label);

            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            positionInfo_panel.add(isActive_box);

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
                    try {
                        int isActive_int = isActive_box.getSelectedItem() == "Неактивен" ? 0:1;
                        String update_query = "UPDATE dbo.JobTitles set isActive = " + isActive_int + "WHERE RusTitle = N'" + position_label.getText() + "'";
                        PreparedStatement updatePosition_pst = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Должность успешно обнавлена");
                        updatePosition_pst.executeUpdate();

                        positions_tableModel.setValueAt(isActive_box.getSelectedItem(),positions_table.getSelectedRow(),2);

                        dispose();

                    } catch (SQLException ex) {
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
