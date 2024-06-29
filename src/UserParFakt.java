import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserParFakt extends JFrame{
    private JPanel Jfpanel;
    private JRadioButton paragonRadioButton;
    private JRadioButton fakturaRadioButton;
    private JButton zamknijOknoButton;
    private int width=300, height=200;

    public UserParFakt(){
        super("Wybór");
        this.setContentPane(this.Jfpanel);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        paragonRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idwypo=idwypozyczenia();
                ParagonFaktura pf=new ParagonFaktura(idwypo);
                pf.wystawparagon();
            }
        });
        fakturaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idwypo=idwypozyczenia();
                ParagonFaktura pf=new ParagonFaktura(idwypo);
                pf.wystawfakture();
            }
        });
        zamknijOknoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }


    private int idwypozyczenia() {
        String sql="SELECT id FROM wypozyczenia";
        int idwypo=0;
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            while(rs.next()){
                idwypo=rs.getInt("id");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return idwypo;
    }
}
