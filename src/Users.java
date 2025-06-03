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
    private Integer roleId=0;

    public List<String> user_list = new ArrayList<>();
    public objectUser editUser = new objectUser();

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

                if (usersSelected == null) {
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Необходимо выбрать пользователя");
                }else if (usersSelected.equals(LoginWin.user.getLogin().toString())  || usersSelected == LoginWin.user.getLogin().toString()){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Вы не можете изменить себя");
                }else {
                    editUser = DatabaseQueries.getUserInfoByName(usersSelected);

                    EditUserFrame editUserFrame = new EditUserFrame();
                    editUserFrame.setVisible(true);
                    editUserFrame.pack();
                }
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

        users_tableModel = new DefaultTableModel(user_list.size(),4){
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
        tabCol2.setHeaderValue("Роль");
        tabCol2.setCellRenderer(users_cellRenderer);
        tabCol2.setPreferredWidth(50);

        TableColumn tabCol3 = users_columns.getColumn(3);
        tabCol3.setHeaderValue("Статус");
        tabCol3.setCellRenderer(users_cellRenderer);
        tabCol3.setPreferredWidth(50);

        for (int i = 0; i <  users_table.getRowCount();i++){

            users_table.setValueAt(getUserID(user_list.get(i)),i,0);
            users_table.setValueAt(user_list.get(i),i,1);
            users_table.setValueAt(getUserRole(user_list.get(i)),i,2);
            users_table.setValueAt(getUserActivity(user_list.get(i)),i,3);


        }

        users_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    usersSelected = (String) users_table.getValueAt(users_table.getSelectedRow(),1);

                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<String> loadUsers(List<String> inputList){

        inputList.clear();

        try {

            String users_query = "SELECT u.login, r.rolename, u.isactive FROM Users u left join roles r on r.roleid = u.role";
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
    public String getUserRole(String loginTitle){

        String role = null;


        try {
            String login_query = "SELECT r.rolename FROM Users u left join roles r on r.roleid = u.role where login = N'" + loginTitle +"'";
            Statement user_statement = MineOperations.conn.createStatement();
            ResultSet user_results = user_statement.executeQuery(login_query);

            if (user_results.next()){
                do{
                    role= user_results.getString("rolename");

                } while (user_results.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }



        return role;
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

            this.setPreferredSize(new Dimension(400,200));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(4,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить пользователя");
            this.setLocation(200,200);

            CreateFrame();
        }

        private void CreateFrame(){


            JPanel user_panel = new JPanel();
            user_panel.setBackground(Color.WHITE);
            user_panel.setLayout(new BorderLayout());
            this.add(user_panel);

            JLabel user_label = new JLabel("  Пользователь :  ");
            user_label.setPreferredSize(new Dimension(120, 30));
            user_label.setForeground(Color.BLACK);
            user_panel.add(user_label, BorderLayout.WEST);

            JTextField user_textField = new JTextField();
            user_panel.add(user_textField, BorderLayout.CENTER);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel role_panel = new JPanel();
            role_panel.setBackground(Color.white);
            role_panel.setLayout(new GridLayout(1,2,5,0));
            role_panel.setLayout(new BorderLayout());
            this.add(role_panel);

            JLabel role_label = new JLabel("  Роль :  ");
            role_label.setPreferredSize(new Dimension(120, 30));
            role_label.setForeground(Color.BLACK);
            role_panel.add(role_label,  BorderLayout.WEST);


            JComboBox role_box = new JComboBox();
            role_box.setBackground(Color.WHITE);
            role_box = databaseQueries.loadRoleBox(role_box);
            role_panel.add(role_box, BorderLayout.CENTER);

            JComboBox finalRole_box = role_box;
            role_box.addActionListener (new ActionListener () {
                public void actionPerformed(ActionEvent e) {
                    if (finalRole_box.getItemCount()>0) {

                        JComboBox c = (JComboBox) e.getSource();
                        DatabaseQueries.Item item = (DatabaseQueries.Item) c.getSelectedItem();
                        roleId = item.getId();

                    }
                }
            });

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            JPanel psw_panel = new JPanel();
            psw_panel.setBackground(Color.WHITE);
            psw_panel.setLayout(new BorderLayout());
            this.add(psw_panel);

            JLabel psw_label = new JLabel("  Пароль :  ");
            psw_label.setPreferredSize(new Dimension(120, 30));
            psw_label.setForeground(Color.BLACK);
            psw_panel.add(psw_label, BorderLayout.WEST);

            JTextField psw_textField = new JTextField();
            psw_panel.add(psw_textField, BorderLayout.CENTER);

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
                    } else if  (roleId == null || roleId ==0 || roleId == 100){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, выберите роль");
                    } else if  (psw_textField.getText()== null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите пароль");
                    } else {
                        int maxIDUser = 0;
                        String checkUserID_query = "SELECT * FROM users WHERE login = N'" + user_textField.getText() +"'";
                        try{
                            Statement checkUser_st = MineOperations.conn.createStatement();
                            ResultSet userExists_rs = checkUser_st.executeQuery(checkUserID_query);
                            if (!userExists_rs.next()){

                                String insert_query = "INSERT INTO users " +
                                        "( login, role, psw,  isActive) " +
                                        "VALUES ( ? , ? , ?, 1)";


                                PreparedStatement ps = MineOperations.conn.prepareStatement(insert_query, PreparedStatement.RETURN_GENERATED_KEYS);
                                ps.setString(1,user_textField.getText());
                                ps.setInt(2,roleId);
                                ps.setString(3,psw_textField.getText());
                                ps.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Пользователь успешно добавлен");


                                ResultSet rs2 = ps.getGeneratedKeys();
                                if (rs2.next()) {
                                    maxIDUser = rs2.getInt(1);
                                }

                                users_tableModel.addRow(new Object[]{maxIDUser, user_textField.getText(), finalRole_box.getSelectedItem(), "Активен"});
                                users_tableModel.fireTableDataChanged();

                                dispose();

                                insert_query = "INSERT INTO users " +
                                        "( login, role, psw,  isActive) " +
                                        "VALUES ( '" + user_textField.getText() + "' , '" + roleId + "', '" + psw_textField.getText() + "' , 1)";
                                DatabaseQueries.saveLogs(insert_query, LoginWin.user.getId());


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
            this.setPreferredSize(new Dimension(400,200));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(4,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Изменить пользователя");
            this.setLocation(200,200);

            EditFrame();
        }

        private void EditFrame(){

//User
            JPanel user_panel = new JPanel();
            user_panel.setBackground(Color.WHITE);
            user_panel.setLayout(new BorderLayout());
            this.add(user_panel);

            JLabel user_label = new JLabel("  Пользователь :  ");
            user_label.setPreferredSize(new Dimension(120, 30));
            user_label.setForeground(Color.BLACK);
            user_panel.add(user_label, BorderLayout.WEST);

            JLabel userName_label = new JLabel(editUser.getLogin());
            user_panel.add(userName_label, BorderLayout.CENTER);



// Role
            JPanel role_panel = new JPanel();
            role_panel.setBackground(Color.white);
            role_panel.setLayout(new GridLayout(1,2,5,0));
            role_panel.setLayout(new BorderLayout());
            this.add(role_panel);

            JLabel role_label = new JLabel("  Роль :  ");
            role_label.setPreferredSize(new Dimension(120, 30));
            role_label.setForeground(Color.BLACK);
            role_panel.add(role_label,  BorderLayout.WEST);


            JComboBox role_box = new JComboBox();
            role_box.setBackground(Color.WHITE);

            role_box = databaseQueries.loadRoleBox(role_box);
            role_panel.add(role_box, BorderLayout.CENTER);


            for (int i = 0; i < role_box.getItemCount(); i++)
            {
                role_box.setSelectedIndex(i);

                if (editUser.getRolename().equals(role_box.getSelectedItem().toString()) || editUser.getRolename() == role_box.getSelectedItem().toString())
                {
                    break;
                }
            }



            JComboBox finalRole_box = role_box;
            role_box.addActionListener (new ActionListener () {
                public void actionPerformed(ActionEvent e) {
                    if (finalRole_box.getItemCount()>0) {

                        JComboBox c = (JComboBox) e.getSource();
                        DatabaseQueries.Item item = (DatabaseQueries.Item) c.getSelectedItem();
                        roleId = item.getId();

                    }
                }
            });


//Isactive

            JPanel isactive_panel = new JPanel();
            isactive_panel.setBackground(Color.WHITE);
            isactive_panel.setLayout(new BorderLayout());
            this.add(isactive_panel);

            JLabel psw_label = new JLabel("  Статус :  ");
            psw_label.setPreferredSize(new Dimension(120, 30));
            psw_label.setForeground(Color.BLACK);
            isactive_panel.add(psw_label, BorderLayout.WEST);


            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            isactive_panel.add(isActive_box);
            isActive_box.setSelectedItem(editUser.getIsactive()==0 ? "Неактивен" : "Активен");



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
                    String update_query = "";
                    try {
                        if (finalRole_box.getSelectedIndex()==0) {
                            JOptionPane.showMessageDialog(MineOperations.cardPane, "Необходимо выбрать роль пользователя");
                        }else {
                            int isActive_int = isActive_box.getSelectedItem() == "Неактивен" ? 0 : 1;
                            update_query = "UPDATE users set role = " + roleId + ",  isActive = " + isActive_int + " WHERE login = N'" + editUser.getLogin() + "'";
                            PreparedStatement updateUser_pst = MineOperations.conn.prepareStatement(update_query);
                            JOptionPane.showMessageDialog(MineOperations.cardPane, "Должность успешно обнавлена");
                            updateUser_pst.executeUpdate();

                            users_tableModel.setValueAt(finalRole_box.getSelectedItem(), users_table.getSelectedRow(), 2);
                            users_tableModel.setValueAt(isActive_box.getSelectedItem(), users_table.getSelectedRow(), 3);
                            dispose();
                            DatabaseQueries.saveLogs(update_query, LoginWin.user.getId());
                        }

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
