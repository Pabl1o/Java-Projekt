import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Logowanie extends JFrame{
    private JPanel LogPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton wyjscieButton;
    private JButton zalogujButton;
    private JButton powrotButton;
    private int width=400, height=300;

    public Logowanie(){
        super("Logowanie");
        this.setContentPane(this.LogPanel);
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
        powrotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LogOrSign los=new LogOrSign();
                los.setVisible(true);
            }
        });
        zalogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login=loginField.getText();
                String haslo=new String(passwordField.getPassword());
                if(login.isEmpty() || haslo.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Pola nie mogą być puste.",
                            "Komunikat",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(loguj(login,haslo)){
                        System.out.println("Poprawnie zalogowano dispose i otwarcie panelu po logowaniu");
                        //zrobic metode isadmin i otwierac panel admina lub panel zwyklego uzytkownika
                        if(isadmin(login)){
                            System.out.println("to admin. otwarcie panelu dla administratora");
                        }
                        else{
                            System.out.println("otwarcie panelu dla zwyklego uzytkownika");
                        }
                    }
                }
            }
        });
    }


    private boolean loguj(String login, String password){
        boolean wynik=false;
        String sql="SELECT pasword FROM users WHERE login='"+login+"'";
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            if(rs.next()){
                String haslo=rs.getString("pasword");
                if(password.equals(haslo)){
                    wynik=true;
                }
                else{
                    JOptionPane.showMessageDialog(null,"Niepoprawne hasło.",
                            "Warn",JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Konto nie istnieje.",
                        "Warn",JOptionPane.ERROR_MESSAGE);
                loginField.setText("");
                passwordField.setText("");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            return wynik;
        }
    }

    private boolean isadmin(String login){
        boolean wynik=false;
        String sql="SELECT isadmin FROM users WHERE login='"+login+"'";
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            if(rs.next()){
                int w=rs.getInt("isadmin");
                if(w==1){
                    wynik=true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            return wynik;
        }
    }


    //ten main przeznaczony jest do usuniecia
    public static void main(String[] args) {
        Logowanie logowanie=new Logowanie();
    }
}
