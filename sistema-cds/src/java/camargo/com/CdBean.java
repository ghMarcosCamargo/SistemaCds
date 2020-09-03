
package camargo.com;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marcos
 */

@ManagedBean
@SessionScoped
public class CdBean extends CrudBean<CD, CDDAO> {
    
    private CDDAO cdDAO;

    @Override
    public CDDAO getDao() {
        if(cdDAO == null) {
            cdDAO = new CDDAO();
        }
        return cdDAO;
    }

    @Override
    public CD criarNovaEntidade() {
        return new CD();
    }

    
}
