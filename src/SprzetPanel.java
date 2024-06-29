import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SprzetPanel extends JFrame implements PobierzSprzet{
    private JPanel Spanel;
    private JTable sprzetTable;
    private JComboBox opcjeBox;
    private JButton okButton;
    private JButton wyjscieButton;
    private JButton powrotButton;
    private int width=700, height=500;
    private String[] kolumny={"ID:","Rodzaj:","Nazwa:","Ilość:","Koszt:","Maksymalny czas:"};
    private DefaultTableModel defaultTableModel=new DefaultTableModel(null,kolumny);

    public SprzetPanel(){
        super("Sprzet Panel");
        this.setContentPane(this.Spanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        danesprzet();
        sprzetTable.setModel(defaultTableModel);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wybor=String.valueOf(opcjeBox.getSelectedItem());
                switch(wybor){
                    case "Dodaj":
                        dispose();
                        SprzedAddPanel sap=new SprzedAddPanel();
                        //sap.setVisible(true);
                        break;
                    case "Edytuj":
                        if(sprzetTable.getSelectedRow()==-1){
                            JOptionPane.showMessageDialog(null,"Zaznacz wiersz tabeli!",
                                    "Warn",JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            String sid=String.valueOf(sprzetTable.getValueAt(sprzetTable.getSelectedRow(),0));
                            int id=Integer.parseInt(sid);
                            dispose();
                            SprzetEditPanel sep=new SprzetEditPanel(id);
                            //sep.setVisible(true);
                        }
                        break;
                    case "Usuń":
                        if(sprzetTable.getSelectedRow()==-1){
                            JOptionPane.showMessageDialog(null,"Zaznacz wiersz tabeli!",
                                    "Warn",JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            String sid=String.valueOf(sprzetTable.getValueAt(sprzetTable.getSelectedRow(),0));
                            int id=Integer.parseInt(sid);
                            usunsprzet(id);
                            int w=defaultTableModel.getRowCount();
                            for(int i=0;i<w;i++){
                                defaultTableModel.removeRow(0);
                            }
                            danesprzet();
                            sprzetTable.setModel(defaultTableModel);
                        }
                        break;
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
                AdminPanel ap=new AdminPanel();
                //ap.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        this.sprzetTable=new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.sprzetTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.sprzetTable.setFont(new Font("Arial",Font.BOLD,14));
        this.sprzetTable.setColumnSelectionAllowed(true);
        this.sprzetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void danesprzet(){
        String sql="SELECT * FROM sprzet";
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            while (rs.next()){
                int id=rs.getInt("id");
                String rodzaj=rs.getString("rodzaj");
                String nazwa=rs.getString("nazwa");
                int ilosc=rs.getInt("ilosc");
                int kosztdniowy=rs.getInt("kosztzadzien");
                int maxczasdni=rs.getInt("maxczasdni");
                defaultTableModel.addRow(new Object[]{id,rodzaj,nazwa,ilosc,kosztdniowy,maxczasdni});
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void usunsprzet(int id){
        String sql="DELETE FROM sprzet WHERE id=?";
        try(Connection conn=DBConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
