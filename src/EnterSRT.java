import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EnterSRT extends JPanel {

    private BufferedImage logo_image;

    public EnterSRT(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ignored) {

        }

        setLayout(null);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Safety Refresher Training</big><br /> Ввод данных по ТБ (ежегод. проверка)</html>");
        titleEng.setBounds(160, 0, 500, 100);
        titleEng.setBackground(Color.WHITE);
        titleEng.setForeground(Color.WHITE);
        titleEng.setFont(new Font("Kumtor", Font.BOLD, 20));
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton exit_button = new JButton("Exit | Выход");
        exit_button.setBounds(730, 60, 150, 30);
        exit_button.setForeground(Color.RED);
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Enter Data");
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
