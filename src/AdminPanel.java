import javax.swing.*;

public class AdminPanel extends JFrame{
    private JPanel Apanel;
    private JTabbedPane tabbedPane1;
    private int width=400, height=400;

    public AdminPanel(){
        super("Panel Admina");
        this.setContentPane(this.Apanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }


    //main do usuniecia
    public static void main(String[] args) {
        AdminPanel ap=new AdminPanel();
    }
}
