import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;

public class ParagonFaktura implements PobierzWypozyczenia{
    private int id;//id z tabeli wypozyczenia
    private int kosztcalkowity;
    private boolean para=false;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ParagonFaktura(int id) {
        this.id = id;
    }

    public void wystawparagon(){
        para=true;
        danewypozyczen();
    }

    public void wystawfakture(){
        para=false;
        danewypozyczen();
    }

    @Override
    public void danewypozyczen() {
        String sql="SELECT w.id, sp.nazwa, w.ilosc, u.imie, u.nazwisko, sp.kosztzadzien, w.dataod, w.datado FROM wypozyczenia w JOIN sprzet sp " +
                "ON sp.id=w.sprzet_id JOIN users u ON u.id=w.user_id WHERE w.id="+this.id+"";
        try (Connection conn=DBConnection.getConnection();
             Statement stmt=conn.createStatement();
             ResultSet rs=stmt.executeQuery(sql)){
            if(rs.next()){
                String nazwa=rs.getString("nazwa");
                int ilosc=rs.getInt("ilosc");
                String name=rs.getString("imie");
                String surname=rs.getString("nazwisko");
                int kosztdniowy=rs.getInt("kosztzadzien");
                String dataod=rs.getString("dataod");
                String datado=rs.getString("datado");
                LocalDate date1 = LocalDate.parse(dataod, formatter);//dataod
                LocalDate date2 = LocalDate.parse(datado, formatter);//datado
                long daysBetween = ChronoUnit.DAYS.between(date1, date2);//liczba dni pomiedzy datami
                kosztcalkowity= (int) (daysBetween*kosztdniowy*ilosc);
                if(para){
                    String tresc=paragontresc(dataod,ilosc,kosztdniowy,kosztcalkowity,nazwa);
                    zapispliku(tresc);
                }
                else{
                    String nrfakt=JOptionPane.showInputDialog("Podaj numer faktury: ");
                    String fakturatresc=fakturatresc(dataod,ilosc,kosztdniowy,kosztcalkowity,nazwa,name,surname,nrfakt);
                    zapispliku(fakturatresc);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    //tresc paragonu
    private String paragontresc(String data, int ilosc, int cena, int kosztcalkowity, String nazwa) {
        StringBuilder paragon = new StringBuilder();
        Formatter formatter = new Formatter(paragon);
        paragon.append("=============== PARAGON ===============\n");
        paragon.append("============ Wypożyczalnia ============\n");
        paragon.append("Data: ");
        paragon.append(data);
        paragon.append("\n");
        paragon.append("---------------------------------------\n");
        paragon.append("Nazwa produktu     Ilość   Cena   Razem\n");
        paragon.append("---------------------------------------\n");
        String format="%-19s %4d %5d %6d\n";
        formatter.format(format, nazwa, ilosc, cena, kosztcalkowity);
        paragon.append("---------------------------------------\n");
        paragon.append(String.format("SUMA: %30d PLN\n", kosztcalkowity));
        paragon.append("=======================================\n");
        return paragon.toString();
    }

    private String fakturatresc(String data, int ilosc, int cena, int kosztcalkowity, String nazwa, String imie, String nazwisko, String nrfaktury){
        StringBuilder faktura = new StringBuilder();
        Formatter formatter = new Formatter(faktura);
        faktura.append("=============== FAKTURA ===============\n");
        faktura.append("============ Wypożyczalnia ============\n");
        faktura.append("Data: ");
        faktura.append(data);
        faktura.append("\n");
        faktura.append("Numer Faktury: ");
        faktura.append(nrfaktury);
        faktura.append("\n");
        faktura.append("=====================================\n");
        faktura.append("Sprzedawca:   Wypożyczalnia Sp. z.o.o\n");
        faktura.append("=====================================\n");
        faktura.append("Nabywca:\n");
        faktura.append(imie);
        faktura.append(" ");
        faktura.append(nazwisko);
        faktura.append("\n");
        faktura.append("---------------------------------------\n");
        faktura.append("Nazwa produktu     Ilość   Cena   Razem\n");
        faktura.append("---------------------------------------\n");
        String format="%-19s %4d %5d %6d\n";
        formatter.format(format, nazwa, ilosc, cena, kosztcalkowity);
        faktura.append("---------------------------------------\n");
        faktura.append(String.format("SUMA: %30d PLN\n", kosztcalkowity));
        faktura.append("=======================================\n");
        return faktura.toString();
    }


    //metoda do zapisu pliku, otwiera okno do wyboru lokalizacji zapisu
    private void zapispliku(String tresc){
        JFileChooser fileChooser=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Pliki tekstowe", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Wybierz miejsce zapisu");
        int userSelection=fileChooser.showSaveDialog(null);
        if(userSelection==JFileChooser.APPROVE_OPTION){
            File fileToSave=fileChooser.getSelectedFile();
            if(!fileToSave.getAbsolutePath().endsWith(".txt")){
                fileToSave=new File(fileToSave.getAbsolutePath() + ".txt");
            }
            try (FileWriter fileWriter=new FileWriter(fileToSave)){
                fileWriter.write(tresc);
                JOptionPane.showMessageDialog(null,"Plik zapisano.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
