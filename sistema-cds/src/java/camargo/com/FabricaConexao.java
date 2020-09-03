/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camargo.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Marcos
 */
public class FabricaConexao {
    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localhost:3306/sistema-cd";
    private static final String USUARIO = "root";
    private static final String SENHA = null;

    public static Connection getConexao() throws ErroSistema {
        if(conexao == null) { 
           
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            } catch (SQLException ex) {
                throw new ErroSistema("Não foi possível conectar ao banco de dados.", ex);
            } catch(ClassNotFoundException ex) {
                throw new ErroSistema("O driver do banco de dados não foi encontrado.", ex);
            }
        }
                            return conexao;
    }
    
    
    public static void fecharConexao() throws ErroSistema{
        if(conexao!= null) {
            
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
               throw new ErroSistema("Erro ao fechar conexão com o banco de dados.", ex);
            }
            
        }
    }

   
    
    
}
    
    
    


