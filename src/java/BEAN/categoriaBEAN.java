package BEAN;
import POJO.categoriaPOJO;
import java.util.Date;
public class categoriaBEAN {
    private Integer ID;
    private Integer ID_categoria;
    private Integer ID_multisegmentos;
    private Integer ID_menu;
    private Integer ID_tipo;
    private String Titulo;
    private String SRC;
    private Date Inicio;
    private String Descricao;
   
    public categoriaBEAN(){
        this.ID=null;
        this.ID_categoria=null;
        this.ID_multisegmentos=null;
        this.ID_menu=null;
        this.ID_tipo=null;
        this.ID_menu=null;
        this.Titulo=null; 
         this.SRC=null;
         this.Descricao=null;
         this.Inicio=null;
    }
    public categoriaBEAN(categoriaPOJO POJO){
        this.ID=POJO.getID();
        this.ID_categoria=POJO.getID_categoria();
        this.ID_multisegmentos=POJO.getID_multisegmentos();
        this.ID_menu=POJO.getID_menu();
        this.ID_tipo=POJO.getID_tipo();
        this.Titulo=POJO.getTitulo();
        this.Descricao=POJO.getDescricao();
        this.SRC=POJO.getSRC();
        this.Inicio=POJO.getInicio();
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
   
    public String getSRC() {
        return SRC;
    }
    public void setSRC(String SRC)throws Exception {
        if(
                
               
                (SRC.length()>=3)
                &&
                (SRC.length()<=50)
          )       
            this.SRC = SRC;
        else
            throw new Exception ("SRC da foto inválida");
    }
     public Date getInicio(){
        return Inicio;
    }
    public String getInicioStr(String formato){
        return Until.functions.DateToString(Inicio, formato);
    }
    public void setInicio(String Inicio,String formato)throws Exception{
        try{
            this.Inicio = Until.functions.StringToDate(Inicio, formato);
        }
        catch(Exception erro){
            throw new Exception("Data inicio inválida");
        }
    }
    public void setInicio(Date Inicio)throws Exception{
        this.Inicio = Inicio;
    }
     public String getDescricao() {
        return Descricao;
    }
    public void setDescricao(String Descricao)throws Exception {
        if(
                
                (Descricao.length()>=3)
                &&
                (Descricao.length()<=50)
          )      
        this.Descricao = Descricao;
            else
                throw new Exception("Descrição da foto inválida.");
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
    public int getID_categoria(){
        return this.ID_categoria.intValue();
    } 
    public String getID_categoriaStr(){
        if(this.ID_categoria==null)
            return null;
        else
            return this.ID_categoria.toString();
    }
    public void setID_categoria(String ID_categoria) throws Exception{
        if(ID_categoria==null){
            this.ID_categoria=null;
        }
        else{
            try{
                this.ID_categoria=Integer.parseInt(ID_categoria);
            }
            catch (Exception erro){
                throw new Exception("Código de categoria inválido.");
            }
        }
    }
    public void setID_categoria(int ID_categoria) throws Exception{
        this.ID_categoria=ID_categoria;
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
    public int getID_tipo(){
        return this.ID_tipo.intValue();
    } 
    public String getID_tipoStr(){
        if(this.ID_tipo==null)
            return null;
        else
            return this.ID_tipo.toString();
    }
    public void setID_tipo(String ID_tipo) throws Exception{
        try{
            this.ID_tipo=Integer.parseInt(ID_tipo);
        }
        catch (Exception erro){
            throw new Exception("Código de página inválido.");
        }
    }
    public void setID_tipo(int ID_tipo) throws Exception{
        this.ID_tipo=ID_tipo;
    }
     public void Clear(){
        this.ID=0;
        this.ID_categoria=0;
        this.ID_multisegmentos=0;
        this.ID_menu=0;
        this.ID_tipo=0;
        this.Titulo="";
        this.SRC="";
        this.Descricao="";
        this.Inicio=null;  
    }
}
