package BEAN;
import POJO.menuPOJO;
public class menuBEAN {
    private Integer ID;
    private Integer ID_menu;
    private Integer ID_multisegmentos;
    private String Titulo;
    public menuBEAN(){
        this.ID=null;
        this.Titulo=null;
        this.ID_menu=null;
        this.ID_multisegmentos=null;
    }
    public menuBEAN(menuPOJO POJO){
        this.ID=POJO.getID();
        this.Titulo=POJO.getTitulo();
        this.ID_menu=POJO.getID_menu();
        this.ID_multisegmentos=POJO.getID_multisegmentos();
    }
    public int getID(){
        return this.ID.intValue();
    } 
    public String getIDStr(){
        if(this.ID==null)
            return null;
        else
            return this.ID.toString();
    }    
    public void setID(String ID) throws Exception{
        try{
            this.ID=Integer.parseInt(ID);
        }
        catch (Exception erro){
            throw new Exception("Código de categoria inválido.");
        }
    }
    public String getTitulo() {
        return Titulo;
    }
    public void setTitulo(String Titulo)throws Exception {
        if(
                (Titulo!=null)
                &&
                (Titulo.length()>=3)
                &&
                (Titulo.length()<=50)
          )
        this.Titulo = Titulo;
            else
                throw new Exception("O Titulo da categoria deve ter entre 3 e 50 letras.");
    }
    public int getID_multisegmentos(){
        return this.ID_multisegmentos.intValue();
    } 
    public String getID_multisegmentosStr(){
        if(this.ID_multisegmentos==null)
            return null;
        else
            return this.ID_multisegmentos.toString();
    }
    public void setID_multisegmentos(String ID_multisegmentos) throws Exception{
        try{
            this.ID_multisegmentos=Integer.parseInt(ID_multisegmentos);
        }
        catch (Exception erro){
            throw new Exception("Código de página inválido.");
        }
    }
    public void setID_multisegmentos(int ID_multisegmentos) throws Exception{
        this.ID_multisegmentos=ID_multisegmentos;
    }
    public int getID_menu(){
        return this.ID_menu.intValue();
    } 
    public String getID_menuStr(){
        if(this.ID_menu==null)
            return null;
        else
            return this.ID_menu.toString();
    }
    public void setID_menu(String ID_menu) throws Exception{
        try{
            this.ID_menu=Integer.parseInt(ID_menu);
        }
        catch (Exception erro){
            throw new Exception("Código de página inválido.");
        }
    }
    public void setID_menu(int ID_menu) throws Exception{
        this.ID_menu=ID_menu;
    }
    public void Clear(){
        this.ID=0;
        this.ID_menu=0;
        this.ID_multisegmentos=0;
        this.Titulo="";
    }
}
