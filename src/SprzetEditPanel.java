import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SprzetEditPanel extends JFrame implements PobierzSprzet{
    private JPanel EditPanel;
    private JTextField rodzajField;
    private JTextField nazwaField;
    private JTextField iloscField;
    private JTextField kosztField;
    private JTextField maxdniField;
    private JButton zapiszZmianyButton;
    private JButton wyjscieButton;
    private JButton powrotButton;
    private int width=400, height=400;
    private int rowid;

    public SprzetEditPanel(int id){
        super("Edytuj sprzęt");
        this.setContentPane(this.EditPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.rowid=id;
        danesprzet();

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
                SprzetPanel sprzetPanel=new SprzetPanel();
                //sprzetPanel.setVisible(true);
            }
        });
        zapiszZmianyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rodzaj=rodzajField.getText();
                String nazwa=nazwaField.getText();
                if(rodzaj.isEmpty() || nazwa.isEmpty() || iloscField.getText().isEmpty() ||
                        kosztField.getText().isEmpty() || maxdniField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Pola nie mogą być puste.",
                            "Ostrzeżenie",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    try{
                        int ilosc=Math.abs(Integer.parseInt(iloscField.getText()));
                        int kosztdniowy=Math.abs(Integer.parseInt(kosztField.getText()));
                        int maxczasdni=Math.abs(Integer.parseInt(maxdniField.getText()));
                        edytujsprzedwDB(rodzaj,nazwa,ilosc,kosztdniowy,maxczasdni);
                    } catch (NumberFormatException error){
                        JOptionPane.showMessageDialog(null,"Pola ilość, koszt, i dni musza zawierac liczbe naturalną.",
                                "Ostrzeżenie",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    @Override
    public void danesprzet() {
        String sql="SELECT * FROM sprzet WHERE id="+this.rowid+"";
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            if(rs.next()){
                String rodzaj=rs.getString("rodzaj");
                String nazwa=rs.getString("nazwa");
                int ilosc=rs.getInt("ilosc");
                int kosztdniowy=rs.getInt("kosztzadzien");
                int maxczasdni=rs.getInt("maxczasdni");
                rodzajField.setText(rodzaj);
                nazwaField.setText(nazwa);
                iloscField.setText(String.valueOf(ilosc));
                kosztField.setText(String.valueOf(kosztdniowy));
                maxdniField.setText(String.valueOf(maxczasdni));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //edycja danych w bazie danych
    private void edytujsprzedwDB(String rodzaj, String nazwa, int ilosc, int koszt, int maxdniw){
        String sql="UPDATE sprzet SET rodzaj=?, nazwa=?, ilosc=?, kosztzadzien=?, maxczasdni=? WHERE id="+this.rowid+"";
        try(Connection conn=DBConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setString(1,rodzaj);
            pstmt.setString(2,nazwa);
            pstmt.setInt(3,ilosc);
            pstmt.setInt(4,koszt);
            pstmt.setInt(5,maxdniw);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Poprawnie zmodyfikowano sprzet.",
                    "Sukces",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
