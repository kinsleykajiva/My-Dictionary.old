package brevity.main.utility;

import brevity.main.pojos.WordMeanings;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.sqlite.SQLiteConfig;

import java.sql.*;

import static brevity.main.utility.Strings.PATH_INJAR_DB;

public class SqliteDB {
    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string

        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(true);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(PATH_INJAR_DB , config.toProperties());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String word, String definitions) {
        String sql = "INSERT INTO dictionary(word,definitions) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            pstmt.setString(2, definitions);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createNewTable() {
        // SQLite connection string


        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS dictionary ("
       + " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "	word text NOT NULL,"
                + "	definitions text NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(PATH_INJAR_DB);
             Statement stmt = conn.createStatement()) {
            // create a new table
            System.out.println("done");
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public ImmutableList<WordMeanings> getWords(){
        MutableList<WordMeanings> ret = Lists.mutable.empty();
        String sql = "SELECT * FROM dictionary LIMIT 100";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                ret.add( new WordMeanings( rs.getInt("id"),
                        rs.getString("word") ,
                        rs.getString("definitions")));
            }
        } catch (SQLException e) {
                 e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return ret.toImmutable();
    }
    public void selectAll(){
        String sql = "SELECT * FROM dictionary WHERE id='1'";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("word") + "\t" +
                        rs.getString("definitions"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
