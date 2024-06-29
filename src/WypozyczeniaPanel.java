import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WypozyczeniaPanel extends JFrame implements PobierzWypozyczenia{
    private JPanel wypoPanel;
    private JTable wypTable;
    private JButton paragonButton;
    private JButton fakturaButton;
    private JButton powrotButton;
    private JButton exitButton;
    private int width=800, height=400;
    private String[] kolumny={"ID:","Nazwa:","Ilość:","Login:","Imie:","Nazwisko:","Od kiedy:","Do kiedy:"};
    private DefaultTableModel defaultTableModel=new DefaultTableModel(null,kolumny);

    public WypozyczeniaPanel(){
        super("Wypożyczenia");
        this.setContentPane(this.wypoPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //this.pack();
        danewypozyczen();
        wypTable.setModel(defaultTableModel);

        paragonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wypTable.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(null,"Zaznacz wiersz tabeli!",
                            "Warn",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    String sid=String.valueOf(wypTable.getValueAt(wypTable.getSelectedRow(),0));
                    int id=Integer.parseInt(sid);
                    ParagonFaktura pf=new ParagonFaktura(id);
                    pf.wystawparagon();
                }
            }
        });
        fakturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wypTable.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(null,"Zaznacz wiersz tabeli!",
                            "Warn",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    String sid=String.valueOf(wypTable.getValueAt(wypTable.getSelectedRow(),0));
                    int id=Integer.parseInt(sid);
                    ParagonFaktura pf=new ParagonFaktura(id);
                    pf.wystawfakture();
                }
            }
        });
        powrotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminPanel adminPanel=new AdminPanel();
                adminPanel.setVisible(true);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }


    private void createUIComponents() {
        this.wypTable=new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.wypTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.wypTable.setFont(new Font("Arial",Font.BOLD,14));
        this.wypTable.setColumnSelectionAllowed(true);
        this.wypTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    @Override
    public void danewypozyczen() {
        String sql="SELECT w.id, sp.nazwa, w.ilosc, u.login, u.imie, u.nazwisko, w.dataod, w.datado FROM wypozyczenia w JOIN " +
                "sprzet sp ON sp.id=w.sprzet_id JOIN users u ON u.id=w.user_id";
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            while (rs.next()){
                int id=rs.getInt("id");
                String nazwa=rs.getString("nazwa");
                int ilosc=rs.getInt("ilosc");
                String login=rs.getString("login");
                String imie=rs.getString("imie");
                String nazwisk=rs.getString("nazwisko");
                String dataod=rs.getString("dataod");
                String datado=rs.getString("datado");
                defaultTableModel.addRow(new Object[]{id,nazwa,ilosc,login,imie,nazwisk,dataod,datado});
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        WypozyczeniaPanel wp=new WypozyczeniaPanel();
    }
}
