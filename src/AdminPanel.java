import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame{
    private JPanel Apanel;
    private JButton panelSprzetButton;
    private JButton listaWypozyczenButton;
    private JButton wyjscieButton;
    private JButton wylogujButton;
    private int width=400, height=400;

    public AdminPanel(){
        super("Panel Admina");
        this.setContentPane(this.Apanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        panelSprzetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SprzetPanel sprzetPanel=new SprzetPanel();
                //sprzetPanel.setVisible(true);
            }
        });
        listaWypozyczenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                WypozyczeniaPanel wypozyczeniaPanel=new WypozyczeniaPanel();
                //wypozyczeniaPanel.setVisible(true);
            }
        });
        wyjscieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        wylogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Logowanie logowanie=new Logowanie();
                //logowanie.setVisible(true);
            }
        });
    }
}
