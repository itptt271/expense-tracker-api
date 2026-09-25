package com.itptt.expense_tracker_api.manager;
import com.itptt.expense_tracker_api.model.Debt;
import com.itptt.expense_tracker_api.model.Transaction;
import com.itptt.expense_tracker_api.model.TransactionType;
import com.itptt.expense_tracker_api.model.Category;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:finance.db";
    // データベースへの接続を取得
    public static Connection connect(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("データベース接続に失敗しました: " + e.getMessage());
        }
        return conn;
    }
    // transactionsテーブルを作成（存在しない場合のみ）
    public static void createTransactionsTable(){
        String sql ="CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "date TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "category TEXT NOT NULL," +
                    "amount INTEGER NOT NULL" +
                    ")";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            System.out.println("transactionsテーブルを作成しました。");
        } catch (SQLException e) {
            System.out.println("テーブル作成に失敗しました: " + e.getMessage());
        }
    }
    // 取引をDBに追加
    public static void insertTransaction(String date, String type, String category, int amount){
        String sql = "INSERT INTO transactions (date, type, category, amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, date);
                pstmt.setString(2, type);
                pstmt.setString(3, category);
                pstmt.setInt(4, amount);
                pstmt.executeUpdate();
                System.out.println("取引をDBに追加しました。");
        } catch (SQLException e) {
            System.out.println("追加に失敗しました: " + e.getMessage());
        }
    }
    // 全ての取引をDBから取得
    public static List<Transaction>getAllTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
                while(rs.next()){
                    int id = rs.getInt("id");
                    java.time.LocalDate date = java.time.LocalDate.parse(rs.getString("date"));
                    TransactionType type = TransactionType.valueOf(rs.getString("type"));
                    Category category = Category.valueOf(rs.getString("category"));
                    int amount = rs.getInt("amount");
                    Transaction t = new Transaction(id, date, type, category, amount);
                    transactions.add(t);
                }
        } catch (SQLException e) {
            System.out.println("取得に失敗しました: " + e.getMessage());
        }
        return transactions;
    }
    // 指定したIDの取引を削除
    public static boolean deleteTransaction(int id){
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, id);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("削除に失敗しました: " + e.getMessage());
            return false;
        }
    }
    // 指定したIDの取引を更新
    public static boolean updateTransaction(int id, String date, String type, String category, int amount){
        String sql = "UPDATE transactions SET date = ?, type = ?, category = ?, amount = ? WHERE id = ? ";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, date);
                pstmt.setString(2, type);
                pstmt.setString(3, category);
                pstmt.setInt(4, amount);
                pstmt.setInt(5, id);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("更新に失敗しました: " + e.getMessage());
            return false;
        }
    }
    // Debtsテーブルを作成
    public static void createDebtsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS debts (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "creditorName TEXT NOT NULL," +
                     "amount INTEGER NOT NULL," +
                     "paiAmount INTEGER NOT NULL" +
                     ")";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement()){
                stmt.execute(sql);
                System.out.println("debtsテーブルを作成しました。");
        } catch (SQLException e) {
            System.out.println("テーブル作成に失敗しました: " + e.getMessage());
        }
    }
    // DebtをDBに追加
    public static void insertDebt(String creditorName, int amount, int paidAmount){
        String sql = "INSERT INTO debts (creditorName, amount, paidAmount) VALUES (?, ?, ?)";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, creditorName);
                pstmt.setInt(2, amount);
                pstmt.setInt(3, paidAmount);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("追加に失敗しました: " + e.getMessage());
        }
    }
    // 全てのDebtをDBから取得
    public static List<Debt> getAllDebts(){
        List<Debt> debts = new ArrayList<>();
        String sql = "SELECT * FROM debts";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
                while(rs.next()){
                    int id = rs.getInt("id");
                    String creditorName = rs.getString("creditorName");
                    int amount = rs.getInt("amount");
                    int paidAmount = rs.getInt("paidAmount");
                    debts.add(new Debt(id, creditorName, amount,  paidAmount));
                }
        } catch (SQLException e) {
            System.out.println("取得に失敗しました: " + e.getMessage());
        }
        return debts;
    }
    // 指定したIDのDebtを更新
    public static boolean updateDebtPayment(int id, int newPaiAmount){
        String sql = "UPDATE debts SET paiAmount = ? WHERE id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){         
                pstmt.setInt(1, newPaiAmount);
                pstmt.setInt(2, id);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;   
        } catch (SQLException e) {
            System.out.println("更新に失敗しました: " + e.getMessage());
            return false;
        }
    }
    // 指定したIDのDebtを削除
    public static boolean deleteDebt(int id){
        String sql = "DELETE FROM debts WHERE id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, id);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("削除に失敗しました: " + e.getMessage());
            return false;
        }
    }
}
