package persistence;

import javax.swing.*;
import java.sql.*;

public class HighscoreDao {
    static  final String url = "jdbc:mysql://localhost/SnakeScore";
    static  final String user = "snake";
    static final String password = "snake";

    public static void saveHighscoreToDatabase(int spielModus, String nameHighscoretraeger, int newBestscore){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = null;
            if (spielModus == 1){
                sql = "INSERT INTO HighscoreLeicht (`Name`, `Score`) VALUES (?,?)";
            }else if (spielModus == 2){
                sql = "INSERT INTO HighscoreMittel (`Name`, `Score`) VALUES (?,?)";
            }else if (spielModus == 3){
                sql = "INSERT INTO HighscoreSchwer (`Name`, `Score`) VALUES (?,?)";
            }else if (spielModus == 4){
                sql = "INSERT INTO HighscoreModus (`Name`, `Score`) VALUES (?,?)";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nameHighscoretraeger);
            statement.setInt(2, newBestscore);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public static Highscore loadHighscoreFromDatabase(int spielModus){
        Highscore highscore = null;
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = null;
            if (spielModus == 1){
                sql = "SELECT * FROM HighscoreLeicht";
            }else if (spielModus == 2){
                sql = "SELECT * FROM HighscoreMittel";
            }else if (spielModus == 3){
                sql = "SELECT * FROM HighscoreSchwer";
            }else if (spielModus == 4){
                sql = "SELECT * FROM HighscoreModus";
            }
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if(result.next()) {
                highscore = new Highscore();
                highscore.setName(result.getString("Name"));
                highscore.setScore(result.getInt("Score"));
            }
        } catch (SQLException e){
            System.out.println("Error");
        }
        return highscore;
    }

    public static void deleteHighscoreFromDatabase(int spielModus, int bestscore){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = null;
            if (spielModus == 1){
                sql = "DELETE FROM HighscoreLeicht WHERE Score = ?";
            }else if (spielModus == 2){
                sql = "DELETE FROM HighscoreMittel WHERE Score = ?";
            }else if (spielModus == 3){
                sql = "DELETE FROM HighscoreSchwer WHERE Score = ?";
            }else if (spielModus == 4){
                sql = "DELETE FROM HighscoreModus WHERE Score = ?";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bestscore);
            statement.execute();
            connection.close();
        }catch (SQLException e){
            System.out.println("Error");
        }
    }

    public void namePlayer(int spielModus, int newBestscore){
        String nameHighscoretraeger = JOptionPane.showInputDialog(null, "Bitte Namen eingeben");
        saveHighscoreToDatabase(spielModus, nameHighscoretraeger, newBestscore);

    }
}
