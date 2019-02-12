
package service;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import model.Student;
import java.awt.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ConnectionService {
        
        private Connection connection;
    
        private ConnectionService(){
            String url = "jdbc:mysql://localhost/java1pexamen";
            String username = "tudorindan";
            String password = "123Dan3211@";
            
            try{
                
                connection = (Connection) DriverManager.getConnection(url,username,password);
                
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        
        private static final class SingletonHolder{
        private static final ConnectionService SINGLETON = new ConnectionService();
    }
    
        public static ConnectionService getInstance(){
        return SingletonHolder.SINGLETON;
    }
        
        public void adaugaStudent(Student student){
            
            try{
                String sql = "SELECT * FROM studenti WHERE CNP=?";
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                preparedStatement.setString(1,student.getCNP());
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet.next()){
                    JOptionPane.showMessageDialog(null,"Studentul exista deja in Baza de date Conform CNP");
                }else{
                    String update = "INSERT INTO studenti VALUES(NULL,?,?,?)";
                    PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(update);
                    preparedStatement1.setString(1,student.getNume());
                    preparedStatement1.setString(2,student.getPrenume());
                    preparedStatement1.setString(3,student.getCNP());
                    preparedStatement1.executeUpdate();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }    
        }
        public ArrayList<Student>getStudenti(){
        
            ArrayList<Student>studenti = new ArrayList<>();
           
            try{
                String sql = "SELECT * FROM studenti";
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                while(resultSet.next()){
                
                    Student s = new Student();
                    s.setId(resultSet.getInt("id"));
                    s.setNume(resultSet.getString("Nume"));
                    s.setPrenume(resultSet.getString("Prenume"));
                    s.setCNP(resultSet.getString("CNP"));
                    
                    studenti.add(s);
                    
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            
            return (ArrayList<Student>)studenti;
            
        }
        public void stergeStudent(int id){
            
            try{
                String sql = "DELETE FROM studenti where id=?";
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();            
            }catch(SQLException e){
                e.printStackTrace();
            }
        
        }
}
