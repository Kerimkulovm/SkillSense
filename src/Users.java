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
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Users extends JPanel {

    private BufferedImage logo_image, bg_image;

    private JTable users_table;
    private DefaultTableModel users_tableModel;

    public DatabaseQueries databaseQueries = new DatabaseQueries();
    public String usersSelected;

    public List<String> user_list = new ArrayList<>();


    public Users(){

        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Пользователи'</big><br /></html>");
        titleEng.setBounds(160, 0, 600, 100);
        titleEng.setFont(Font.getFont("Lena"));
        titleEng.setForeground(Color.BLACK);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JTable users_panel = new JTable();
        users_panel.setBackground(Color.WHITE);
        users_panel.setBounds(20,120,650,500);
        users_panel.setLayout(new BorderLayout());
        this.add(users_panel);

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
        JScrollPane users_scrollPane = new JScrollPane(users_table);
        users_scrollPane.setBackground(Color.WHITE);
        users_panel.add(users_scrollPane);

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
                EditUserFrame editUserFrame = new EditUserFrame();
                editUserFrame.setVisible(true);
                editUserFrame.pack();
            }
        });

        JButton create_button = new JButton("Создать");
        create_button.setBackground(Color.WHITE);
        create_button.setForeground(Color.BLACK);
        create_button.setBorder(new RoundedBorder(10));
        create_button.setFont(Font.getFont("Lena"));
        buttons_panel.add(create_button);
        create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUserFrame createUserFrame = new AddUserFrame();
                createUserFrame.setVisible(true);
                createUserFrame.pack();
            }
        });
    }

    private void buildTable(){

        user_list = loadUsers(user_list);

        users_tableModel = new DefaultTableModel(user_list.size(),3){
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        DefaultTableCellRenderer users_cellRenderer = new DefaultTableCellRenderer();
        users_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        users_table = new JTable(users_tableModel);
        users_table.setBorder(new LineBorder(Color.BLACK));
        users_table.setBackground(Color.WHITE);
        users_table.setRowHeight(20);

        JTableHeader users_header = users_table.getTableHeader();
        users_header.setBorder(new LineBorder(Color.BLACK));
        users_header.setBackground(Color.WHITE);
        users_header.setFont(Font.getFont("Lena"));

        TableColumnModel users_columns = users_header.getColumnModel();

        TableColumn tabCol0 = users_columns.getColumn(0);
        tabCol0.setHeaderValue("ID Пользователя");
        tabCol0.setCellRenderer(users_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = users_columns.getColumn(1);
        tabCol1.setHeaderValue("Логин");
        tabCol1.setCellRenderer(users_cellRenderer);
        tabCol1.setPreferredWidth(50);

        TableColumn tabCol2 = users_columns.getColumn(2);
        tabCol2.setHeaderValue("Статус");
        tabCol2.setCellRenderer(users_cellRenderer);
        tabCol2.setPreferredWidth(50);

        for (int i = 0; i <  users_table.getRowCount();i++){
            users_table.setValueAt(getUserID(user_list.get(i)),i,0);
            users_table.setValueAt(user_list.get(i),i,1);
            users_table.setValueAt(getUserActivity(user_list.get(i)),i,2);
        }

        users_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    usersSelected = (String) users_table.getValueAt(users_table.getSelectedRow(),1);
                    System.out.println(usersSelected);
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<String> loadUsers(List<String> inputList){

        inputList.clear();

        try {

            String users_query = "SELECT * FROM Users";
            Statement users_st = MineOperations.conn.createStatement();
            ResultSet users_rs = users_st.executeQuery(users_query);

            if (users_rs.next()){
                do {
                    String userTitle = users_rs.getString("login");
                    inputList.add(userTitle);
                } while (users_rs.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return  inputList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getUserID(String userTitle){

        int userID = 0;

        try {
            String userID_query = "SELECT * FROM Users WHERE login = N'" + userTitle +"'";
            Statement userID_statement = MineOperations.conn.createStatement();
            ResultSet userID_results = userID_statement.executeQuery(userID_query);

            if (userID_results.next()){
                do{
                    int logId= userID_results.getInt("userid");
                    userID = logId;
                } while (userID_results.next());
            }


        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return userID;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getUserActivity(String loginTitle){

        String status = null;
        int statusID = 0;

        try {
            String login_query = "SELECT * FROM Users WHERE login = N'" + loginTitle +"'";
            Statement user_statement = MineOperations.conn.createStatement();
            ResultSet user_results = user_statement.executeQuery(login_query);

            if (user_results.next()){
                do{
                    int isActiveId= user_results.getInt("isActive");
                    statusID = isActiveId;
                } while (user_results.next());
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

    private class AddUserFrame extends JFrame{

        public AddUserFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();
        }

        private void CreateFrame(){


            JPanel user_panel = new JPanel();
            user_panel.setBackground(Color.WHITE);
            user_panel.setLayout(new BorderLayout());
            this.add(user_panel);

            JLabel user_label = new JLabel("  Пользователь :  ");
            user_label.setForeground(Color.BLACK);
            user_panel.add(user_label, BorderLayout.WEST);

            JTextField user_textField = new JTextField();
            user_panel.add(user_textField, BorderLayout.CENTER);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel buttons_panel = new JPanel();
            buttons_panel.setBackground(Color.WHITE);
            buttons_panel.setLayout(new GridLayout(1,2,5,0));
            this.add(buttons_panel);

            JButton save_button = new JButton("Сохранить");
            save_button.setBackground(Color.WHITE);
            buttons_panel.add(save_button);
            save_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (user_textField.getText() == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите логин");
                    } else {
                        int maxIDUser = 0;
                        String checkUserID_query = "SELECT * FROM users WHERE login = N'" + user_textField.getText() +"'";
                        try{
                            Statement checkUser_st = MineOperations.conn.createStatement();
                            ResultSet userExists_rs = checkUser_st.executeQuery(checkUserID_query);
                            if (!userExists_rs.next()){


                                String insert_query = "INSERT INTO users " +
                                        "( login, psw, isActive) " +
                                        "VALUES ( ? , ? , 1)";


                                PreparedStatement insertUser = MineOperations.conn.prepareStatement(insert_query);
                                insertUser.setString(1,user_textField.getText());
                                insertUser.setString(2,user_textField.getText());
                                insertUser.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Пользователь успешно добавлен");

                                users_tableModel.addRow(new Object[]{maxIDUser, user_textField.getText(), "Активен"});
                                users_tableModel.fireTableDataChanged();

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

    private class EditUserFrame extends JFrame{

        public EditUserFrame(){
            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить должность");
            this.setLocation(200,200);

            CreateFrame();
        }

        private void CreateFrame(){

            JPanel userInfo_panel = new JPanel();
            userInfo_panel.setBackground(Color.WHITE);
            userInfo_panel.setLayout(new GridLayout(1,2));
            this.add(userInfo_panel);

            JLabel user_label = new JLabel(usersSelected, SwingConstants.CENTER);
            user_label.setForeground(Color.BLACK);
            userInfo_panel.add(user_label);

            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            userInfo_panel.add(isActive_box);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel buttons_panel = new JPanel();
            buttons_panel.setBackground(Color.WHITE);
            buttons_panel.setLayout(new GridLayout(1,2,5,0));
            this.add(buttons_panel);

            JButton save_button = new JButton("Сохранить");
            save_button.setBackground(Color.WHITE);
            buttons_panel.add(save_button);
            save_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int isActive_int = isActive_box.getSelectedItem() == "Неактивен" ? 0:1;
                        String update_query = "UPDATE users set isActive = " + isActive_int + "WHERE login = N'" + user_label.getText() + "'";
                        PreparedStatement updateUser_pst = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Должность успешно обнавлена");
                        updateUser_pst.executeUpdate();

                        users_tableModel.setValueAt(isActive_box.getSelectedItem(),users_table.getSelectedRow(),2);

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
