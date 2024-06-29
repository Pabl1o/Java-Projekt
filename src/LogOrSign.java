import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogOrSign extends JFrame{
    private JPanel LoSpanel;
    private JButton wyjscieButton;
    private JButton zarejestrujButton;
    private JButton zalogujButton;
    private int width=400, height=300;

    public LogOrSign(){
        super("Wybór opcji");
        this.setContentPane(this.LoSpanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        wyjscieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        zarejestrujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Rejestracja rejestracja=new Rejestracja();
                rejestracja.setVisible(true);
            }
        });
        zalogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Logowanie logowanie=new Logowanie();
                logowanie.setVisible(true);
            }
        });
    }

    //do usuniecia jest ten main sluzy do testow tylko
    public static void main(String[] args) {
        LogOrSign los=new LogOrSign();
    }
}