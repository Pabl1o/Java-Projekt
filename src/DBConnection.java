import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//klasa sluzy do polaczenia z baza danych
public class DBConnection {
    private static final String URL="jdbc:mysql://localhost:3306/jprojekt";//nazwa bazy
    private static final String USER="root";//domyslny uzytkownik w phpmyadmin
    private static final String PASSWORD="";//domyslne haslo do bazy

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
