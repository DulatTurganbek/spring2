package kz.iitu.spring1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;

public class BankService implements IBankService {
    Bank bank;
    private String url = "jdbc:mysql://localhost:3306/spring_atm";
    private String username = "root";
    private String password = "root";
    public Connection conn;
//    ApplicationContext context = Context.getContext().context;

    public BankService(Bank bank) {
        this.bank = bank;
    }

    public String checkPinAndGetName(String pin){
            PreparedStatement ps = null;
            try {
                String sql = "SELECT name FROM `accounts` where `pin`=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, pin);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ResultSet resultSet = null;
            try {
                assert ps != null;
                resultSet = ps.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (resultSet != null){
                while(true){
                    try {
                        if (!resultSet.next()) break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        return resultSet.getString("name");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return "Not Found";
    }
    public void changePin(String oldPin, String newPin){
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE `accounts` set pin=? where pin=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newPin);
            ps.setString(2, oldPin);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            assert ps != null;
            ps.executeUpdate();
            System.out.println("Updated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int checkBalance(String name, String pin) {
        PreparedStatement ps = null;
        try {
            String sql = "SELECT cash FROM `accounts` where `pin`=? and `name`=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pin);
            ps.setString(2, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            assert ps != null;
            resultSet = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (resultSet != null){
            while(true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    return resultSet.getInt("cash");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        try {
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public void withdraw(String name, String pin, int money) {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE `accounts` set cash = cash-? where pin = ? and `name` = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, money);
            ps.setString(2, pin);
            ps.setString(3, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            assert ps != null;
            ps.executeUpdate();
            System.out.println("Updated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void upToBalance(String name, String pin, int money) {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE `accounts` set cash = cash+? where pin = ? and `name` = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, money);
            ps.setString(2, pin);
            ps.setString(3, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            assert ps != null;
            ps.executeUpdate();
            System.out.println("Updated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void destroy() throws SQLException {
        conn.close();
    }
}
