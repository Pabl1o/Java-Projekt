import javax.swing.*;

public class WczytywanieStart extends JFrame{
    private JPanel Jpanel1;
    private JProgressBar pasekLadowania;
    private JLabel ladujLabel;
    private int width=400, height=300;

    public WczytywanieStart(){
        super("Wypożyczalnia");
        this.setContentPane(this.Jpanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        ladowanie();
    }

    private void ladowanie(){
        int licznik=0;
        while(licznik<=100){
            ladujLabel.setText("Wczytywanie aplikacji ...");
            pasekLadowania.setValue(licznik);
            pasekLadowania.setString(String.valueOf(licznik));
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            licznik=licznik+1;
        }
        dispose();
        LogOrSign los=new LogOrSign();
        los.setVisible(true);
    }

    //ten main jest do usuniecia
    public static void main(String[] args) {
        WczytywanieStart ws=new WczytywanieStart();
    }

}
