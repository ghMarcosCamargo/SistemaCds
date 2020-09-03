////
package camargo.com;

import java.util.List;

/**
 *
 * @author Marcos
 */
public interface CrudDAO<E> {//E representa entidade
   public void salvar(E entidade) throws ErroSistema;
   public void deletar(E entidade) throws ErroSistema;
   public List<E> buscar() throws ErroSistema;
   
    
}
