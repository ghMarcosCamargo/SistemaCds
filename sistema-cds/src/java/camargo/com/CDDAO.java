/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camargo.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcos
 */
public class CDDAO implements CrudDAO<CD> {
    @Override
    public void salvar(CD cd) throws ErroSistema {
        try {
            Connection conexao;
            conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(cd.getId()==null) {
                ps = conexao.prepareStatement("INSERT INTO `sistema-cd`.`cds`(`artista`,`lancamento`,`genero`,`duracaoMinutos`,`nome`)VALUES(?,?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update cds set artista=?, lancamento=?, genero=?, duracaoMinutos=?, nome=? where id=?");
                ps.setInt(6, cd.getId());
            }
            
            ps.setString(1, cd.getArtista());
            ps.setInt(2, cd.getLancamento());
            ps.setString(3, cd.getGenero());
            ps.setInt(4, cd.getDuracaoMinutos());
            ps.setString(5, cd.getNome());
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
                throw new ErroSistema("Erro ao tentar salvar o objeto.", ex);

        }

    }
    
    @Override
    public List<CD> buscar() throws ErroSistema  {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from cds");
            ResultSet resultSet = ps.executeQuery();
            List<CD> cds = new ArrayList<>();
            
            while(resultSet.next()) {  
                CD cd = new CD();
              
                cd.setId(resultSet.getInt("id"));
                cd.setArtista(resultSet.getString("artista"));
                cd.setLancamento(resultSet.getInt("lancamento"));
                cd.setGenero(resultSet.getString("genero"));
                cd.setDuracaoMinutos(resultSet.getInt("duracaoMinutos"));
                cd.setNome(resultSet.getString("nome"));
                
                cds.add(cd);
            }
            FabricaConexao.fecharConexao();
            return cds;
                
           
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar objetos.", ex);
        }
    }
    
    @Override
    public void deletar(CD cd) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from cds where id=?");
            ps.setInt(1, cd.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o carro do sistema.");
        }
    }
    
}
