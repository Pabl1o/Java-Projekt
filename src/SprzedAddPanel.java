import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SprzedAddPanel extends JFrame{
    private JPanel SApanel;
    private JTextField rodzajField;
    private JTextField nazwaField;
    private JTextField iloscField;
    private JTextField kosztField;
    private JTextField maxdniField;
    private JButton wyjscieButton;
    private JButton powrotButton;
    private JButton dodajButton;
    private int width=400, height=400;

    public SprzedAddPanel(){
        super("Dodaj Sprzęt");
        this.setContentPane(this.SApanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rodzaj=rodzajField.getText();
                String nazwa=nazwaField.getText();
                if(rodzaj.isEmpty() || nazwa.isEmpty() || iloscField.getText().isEmpty() ||
                kosztField.getText().isEmpty() || maxdniField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Pola nie mogą być puste.",
                            "Ostrzeżenie",JOptionPane.ERROR_MESSAGE);
                }
                else if (rodzajField.getText().equals("podaj tekst") || nazwaField.getText().equals("podaj tekst")
                        || iloscField.getText().equals("podaj liczbe") || kosztField.getText().equals("podaj liczbe")
                        || maxdniField.getText().equals("podaj liczbe")) {
                    JOptionPane.showMessageDialog(null,"Wypełnij pola innymi wartościami.",
                            "Ostrzeżenie",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    try{
                        int ilosc=Math.abs(Integer.parseInt(iloscField.getText()));
                        int koszt=Math.abs(Integer.parseInt(kosztField.getText()));
                        int maxdni=Math.abs(Integer.parseInt(maxdniField.getText()));
                        dodajSprzetdoDB(rodzaj,nazwa,ilosc,koszt,maxdni);
                    } catch (NumberFormatException error){
                        JOptionPane.showMessageDialog(null,"Pola ilość, koszt, i dni musza zawierac liczbe.",
                                "Ostrzeżenie",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        wyjscieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        powrotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SprzetPanel sp=new SprzetPanel();
                //sp.setVisible(true);
            }
        });
    }


    //dodawanie sprzetu do bazy danych
    private void dodajSprzetdoDB(String rodzaj, String nazwa, int ilosc, int koszt, int maxdniw){
        String sql="INSERT INTO sprzet (rodzaj, nazwa, ilosc, kosztzadzien, maxczasdni) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setString(1,rodzaj);
            pstmt.setString(2,nazwa);
            pstmt.setInt(3,ilosc);
            pstmt.setInt(4,koszt);
            pstmt.setInt(5,maxdniw);
            pstmt.executeUpdate();
            rodzajField.setText("");
            nazwaField.setText("");
            iloscField.setText("");
            kosztField.setText("");
            maxdniField.setText("");
            JOptionPane.showMessageDialog(null,"Poprawnie dodano sprzet do bazy danych.",
                    "Sukces",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
