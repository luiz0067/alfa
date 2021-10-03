/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author Paty
 */
public class emailPOJO {
    private Integer ID;
    private Integer ID_multisegmentos;
    private Integer ID_menu;
    private String Titulo;

    /**
     * @return the ID
     */
    public Integer getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * @return the Titulo
     */
    public String getTitulo() {
        return Titulo;
    }

    /**
     * @param Titulo the Titulo to set
     */
    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }
  public Integer getID_multisegmentos() {
        return ID_multisegmentos;
    }
    
    public void setID_multisegmentos(Integer ID_multisegmentos) {
        this.ID_multisegmentos = ID_multisegmentos;
    }
  public Integer getID_menu() {
        return ID_menu;
    }
    
    public void setID_menu(Integer ID_menu) {
        this.ID_menu = ID_menu;
    }
}
