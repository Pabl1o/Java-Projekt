import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserPanel extends JFrame implements PobierzSprzet{
    private JPanel UserPanel;
    private JButton wyjscieButton;
    private JButton wylogujButton;
    private JButton rezerwujButton;
    private JList<String> sprzetList;
    private JLabel nazwaLabel;
    private JLabel iloscLabel;
    private JLabel kosztLabel;
    private JLabel maxdayLabel;
    private int width=600, height=450;
    private int userid;
    private DefaultListModel<String> listModel=new DefaultListModel<>();
    private ArrayList<Integer> sprzetyids=new ArrayList<>();
    private ArrayList<Integer> ilosci=new ArrayList<>();
    private ArrayList<Integer> koszty=new ArrayList<>();
    private ArrayList<Integer> maxdaystime=new ArrayList<>();

    public UserPanel(int uid){
        super("Panel Użytkownika");
        this.setContentPane(this.UserPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.userid=uid;
        danesprzet();
        sprzetList.setModel(listModel);

        sprzetList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!sprzetList.isSelectionEmpty()){
                    nazwaLabel.setText("Nazwa: "+sprzetList.getSelectedValue());
                    int index=sprzetList.getSelectedIndex();
                    iloscLabel.setText("Ilość: "+ilosci.get(index));
                    kosztLabel.setText("Koszt za dzień: "+koszty.get(index));
                    maxdayLabel.setText("Maksymalny czas wypożyczenia: "+maxdaystime.get(index));
                    rezerwujButton.setEnabled(true);
                }
                else{
                    rezerwujButton.setEnabled(false);
                }
            }
        });
        rezerwujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=sprzetList.getSelectedIndex();
                int ilosc=ilosci.get(index);
                int iloscdni=Integer.parseInt(JOptionPane.showInputDialog(null,"Podaj ilość dni:",
                        "Podaj dane"));
                if(!(iloscdni<=maxdaystime.get(index))){
                    JOptionPane.showMessageDialog(null,"Nie można wynająć na tyle dni.",
                            "Komunikat",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int ilesztukwyn=Integer.parseInt(JOptionPane.showInputDialog(null,"Podaj ilość sprzętu:",
                            "Podaj dane"));
                    if(!(ilosc-ilesztukwyn>=0))
                    {
                        JOptionPane.showMessageDialog(null,"Nie ma tyle sprzętu do wynajęcia",
                                "Komunikat",JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        int kosztrezerwacji=iloscdni*koszty.get(index)*ilesztukwyn;
                        JOptionPane.showMessageDialog(null,"Koszt rezerwacji to: "+kosztrezerwacji,
                                "Opłata",JOptionPane.INFORMATION_MESSAGE);
                        int sprzet_id=sprzetyids.get(index);
                        int user_id=userid;
                        int nowailosc=ilosc-ilesztukwyn;
                        LocalDate datadzis=LocalDate.now();
                        LocalDate nextDate=datadzis.plusDays(iloscdni);
                        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String datado=nextDate.format(formatter);
                        String dzis=datadzis.format(formatter);
                        addwypozyczenie(sprzet_id,user_id,ilosc,dzis,datado,nowailosc);
                        ilosci.set(index,nowailosc);
                        sprzetList.setModel(listModel);
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
        wylogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Logowanie logowanie=new Logowanie();
                //logowanie.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        sprzetList=new JList<>();
        this.sprzetList.setFont(new Font("Arial",Font.BOLD,14));
    }

    //pobranie nazw sprzetow do wypozyczenia
    @Override
    public void danesprzet() {
        String sql="SELECT id, nazwa, ilosc, kosztzadzien, maxczasdni FROM sprzet";
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            while (rs.next()){
                int sprzetid=rs.getInt("id");
                String nazwa=rs.getString("nazwa");
                int ilosc=rs.getInt("ilosc");
                int kosztdniowy=rs.getInt("kosztzadzien");
                int maxczasdni=rs.getInt("maxczasdni");
                sprzetyids.add(sprzetid);
                listModel.addElement(nazwa);
                ilosci.add(ilosc);
                koszty.add(kosztdniowy);
                maxdaystime.add(maxczasdni);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //dodawanie do bazy wypozyczonej pozycji
    private void addwypozyczenie(int idsprzet, int iduser, int ilosc, String dataod, String datado, int nowailosc){
        String sql="INSERT INTO wypozyczenia (sprzet_id, user_id, ilosc, dataod, datado) VALUES (?, ?, ?, ?, ?)";
        String sql2="UPDATE sprzet SET ilosc=? WHERE id="+idsprzet+"";
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement pstmt=conn.prepareStatement(sql);
             PreparedStatement pstmt2=conn.prepareStatement(sql2)){
            pstmt.setInt(1,idsprzet);
            pstmt.setInt(2,iduser);
            pstmt.setInt(3,ilosc);
            pstmt.setString(4,dataod);
            pstmt.setString(5,datado);
            pstmt.executeUpdate();
            pstmt2.setInt(1,nowailosc);
            pstmt2.executeUpdate();
            JOptionPane.showMessageDialog(null,"Sprzet został wypożyczony.",
                    "Komunikat",JOptionPane.INFORMATION_MESSAGE);
            //okno wyboru paragonu lub faktury
            UserParFakt upf=new UserParFakt();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
