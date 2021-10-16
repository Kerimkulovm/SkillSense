import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchByName extends JPanel {

    private BufferedImage logo_image;

    private JLabel tableID_label;

    private JTextField tableID_text;
    private JTextField nameRus_text;

    private JButton search_by_name_button;

    public SearchByName()
    {
        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {

        }


        setLayout(null);

        JLabel titleEng = new JLabel("<html><big>Search by name</big><br /> Поиск по имени</html>");
        titleEng.setBounds(160, 0, 600, 100);
        titleEng.setBackground(Color.WHITE);
        titleEng.setForeground(Color.WHITE);
        titleEng.setFont(new Font("Kumtor", Font.BOLD, 20));
        this.add(titleEng);


        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.RED);
        exit_button.setForeground(Color.WHITE);
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Employee Info");

            }
        });


        JPanel searchByName_panel = new JPanel();
        searchByName_panel.setBackground(Color.white);
        searchByName_panel.setLayout(new BoxLayout(searchByName_panel, BoxLayout.X_AXIS));
        searchByName_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Поиск сотрудника"));
        searchByName_panel.setBounds(20, 120, 450, 50);
        this.add(searchByName_panel);

        JPanel tableID_panel = new JPanel();
        tableID_label = new JLabel(" Фамилия или имя:  ");
        tableID_label.setForeground(Color.RED);
        tableID_panel.setBackground(Color.WHITE);
        tableID_panel.add(tableID_label);
        searchByName_panel.add(tableID_label);

        tableID_text = new JTextField();
        tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
        tableID_panel.add(tableID_text);
        searchByName_panel.add(tableID_text);


        search_by_name_button = new JButton("Поиск");
        search_by_name_button.setForeground(Color.RED);
        search_by_name_button.setBackground(Color.WHITE);
        tableID_panel.add(search_by_name_button);
        searchByName_panel.add(search_by_name_button);
        search_by_name_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dgsfg");
            }
        });


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }
}
