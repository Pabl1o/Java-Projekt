import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Rejestracja extends JFrame{
    private JPanel RejPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton wyjscieButton;
    private JButton zarejestrujButton;
    private JButton powrotButton;
    private JPasswordField confirmpassField;
    private JTextField imieField;
    private JTextField nazwiskoField;
    private int width=400, height=350;
    private String login, haslo, confirmhaslo, imie, nazwisko;

    public Rejestracja(){
        super("Rejestracja");
        this.setContentPane(this.RejPanel);
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
                //los.setVisible(true);
            }
        });
        zarejestrujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login=loginField.getText();
                imie=imieField.getText();
                nazwisko=nazwiskoField.getText();
                haslo=new String(passwordField.getPassword());
                confirmhaslo=new String(confirmpassField.getPassword());
                if(login.isEmpty() || imie.isEmpty() || nazwisko.isEmpty() || haslo.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Pola nie mogą być puste.",
                            "Komunikat",JOptionPane.ERROR_MESSAGE);
                }
                else if(!isloginaviable(login)){
                    JOptionPane.showMessageDialog(null,"Ten login jest już zajęty.",
                            "Komunikat",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(haslo.equals(confirmhaslo)){
                        addAccount(login,imie,nazwisko,haslo);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Hasła nie są zgodne.",
                                "Warning",JOptionPane.ERROR_MESSAGE);
                        passwordField.setText("");
                        confirmpassField.setText("");
                    }
                }
            }
        });
    }

    //dodanie konta uzytkownika do bazy danych
    private void addAccount(String login, String name, String surname, String password){
        String sql="INSERT INTO users (login, imie, nazwisko, pasword, isadmin) VALUES (?, ?, ?, ?, 0)";
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setString(1,login);
            pstmt.setString(2,name);
            pstmt.setString(3,surname);
            pstmt.setString(4,password);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Konto zostało poprawnie utworzone.",
                    "Komunikat",JOptionPane.INFORMATION_MESSAGE);
            loginField.setText("");
            imieField.setText("");
            nazwiskoField.setText("");
            passwordField.setText("");
            confirmpassField.setText("");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private boolean isloginaviable(String login){
        String sql="SELECT login FROM users WHERE login='"+login+"'";
        //sprawdzanie czy login nie jest zajety
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            if(rs.next()){
                return false;
            }
            else{
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
