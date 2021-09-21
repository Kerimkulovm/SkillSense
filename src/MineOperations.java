import javax.swing.*;
import java.awt.*;

public class MineOperations extends JFrame {

    public static JFrame application_frame;

    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 750;

    public static HomePage homePagePanel = new HomePage();
    public static EmployeeInfo employeeInfoPanel = new EmployeeInfo();
    public static EnterMenu enterMenuPanel = new EnterMenu();
    public static OperationsDaily operationsDailyPanel = new OperationsDaily();

    public static CardLayout card;
    public static JPanel cardPane;

    public static void main(String[] args) {
        launchMineOperationsTraining();
    }

    private static void launchMineOperationsTraining() {

        application_frame = new JFrame();
        application_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        application_frame.setTitle("Mine Operations Training");
        application_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPane = new JPanel();
        card = new CardLayout();

        cardPane.setLayout(card);
        cardPane.add(homePagePanel,"Home Page");
        cardPane.add(enterMenuPanel, "Enter Data");
        cardPane.add(employeeInfoPanel, "Employee Info");
        cardPane.add(operationsDailyPanel, "Operations Daily");

        application_frame.add(cardPane);
        application_frame.setVisible(true);
    }
}
