/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camargo.com;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos
 */
public class UsuarioDAO implements CrudDAO<Usuario> {

    @Override
    public void salvar(Usuario entidade) throws ErroSistema {
          try {
            Connection conexao;
            conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(entidade.getId()==null) {
                ps = conexao.prepareStatement("INSERT INTO `sistema-cd`.`usuarios`(login,senha,nome) VALUES(?,?,?)");
            } else {
                ps = conexao.prepareStatement("update usuarios set login=?, senha=?, nome=? where id=?");
                ps.setInt(4, entidade.getId());
            }
            
            ps.setString(1, entidade.getLogin());
            ps.setString(2, entidade.getSenha());
            ps.setString(3, entidade.getNome());
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
                throw new ErroSistema("Erro ao tentar salvar o objeto.", ex);

        }
    }

    @Override
    public void deletar(Usuario entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from usuarios where id=?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o usuario do sistema.");
        }
    }


    @Override
    public List<Usuario> buscar() throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from usuarios");
            ResultSet resultSet = ps.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            
            while(resultSet.next()) {  
                Usuario usuario = new Usuario();
              
                usuario.setId(resultSet.getInt("id"));
                usuario.setLogin(resultSet.getString("login"));
                usuario.setSenha(resultSet.getString("senha"));
                usuario.setNome(resultSet.getString("nome"));

                
                usuarios.add(usuario);
            }
            FabricaConexao.fecharConexao();
            return usuarios;
                
           
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar objetos.", ex);
        }
    }
    
}
