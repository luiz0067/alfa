package BEAN;
import POJO.conteudoPOJO;
import java.util.Date;
public class conteudoBEAN {
    private Integer id;
    private Integer id_multisegmentos;
    private Integer id_menu;
    private Integer id_categoria;
    private String titulo;
    private String descricao;
    private String SRC;
    private String qrcode;
    private String conteudo;
    private Date data_cadastro;
   
    public conteudoBEAN(){
        this.id=null;
        this.id_multisegmentos=null;
        this.id_menu=null;
        this.id_categoria=null;
        this.titulo=null; 
        this.descricao=null;
        this.SRC=null;
        this.qrcode=null;
        this.conteudo=null;
        this.data_cadastro=null;
    }
    public conteudoBEAN(conteudoPOJO POJO){
        this.id=POJO.getID();
        this.id_multisegmentos=POJO.getID_multisegmentos();
        this.id_menu=POJO.getID_menu();
        this.id_categoria=POJO.getid_categoria();
        this.titulo=POJO.getTitulo();
        this.descricao=POJO.getDescricao();
        this.conteudo=POJO.getconteudo();
        this.SRC=POJO.getSRC();
        this.qrcode=POJO.getqrcode();
        this.data_cadastro=POJO.getdata_cadastro();
    }
    public int getid(){
            return this.id.intValue();
    } 
    public String getidStr(){
        if(this.id==null)
            return null;
        else
            return this.id.toString();
    }    
    public void setid(String id) throws Exception{
        try{
            this.id=Integer.parseInt(id);
        }
        catch (Exception erro){
            throw new Exception("Código de categoria inválido.");
        }
    }
    public String gettitulo() {
        return titulo;
    }
    public void settitulo(String titulo)throws Exception {
        if(
                (titulo!=null)
                &&
                (titulo.length()>=3)
                &&
                (titulo.length()<=50)
          )
        this.titulo = titulo;
            else
                throw new Exception("O titulo da categoria deve ter entre 3 e 50 letras.");
    }
    
     public String getdescricao() {
        return descricao;
    }
    public void setdescricao(String descricao)throws Exception {
        if(      
                (descricao.length()>=3)
                &&
                (descricao.length()<=50)
          )      
        this.descricao = descricao;
            else
                throw new Exception("descricao inválida.");
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
    
    
      public String getqrcode() {
        return qrcode;
    }
    public void setqrcode(String qrcode)throws Exception {
        if(
                
               
                (qrcode.length()>=1)
                &&
                (qrcode.length()<=250)
          )       
            this.qrcode = qrcode;
        else
            throw new Exception ("qrcode da foto inválida");
    }
    
    
    
    
    
     public Date getdata_cadastro(){
        return data_cadastro;
    }
    public String getdata_cadastroStr(String formato){
        return Until.functions.DateToString(data_cadastro, formato);
    }
    public void setdata_cadastro(String data_cadastro,String formato)throws Exception{
        try{
            this.data_cadastro = Until.functions.StringToDate(data_cadastro, formato);
        }
        catch(Exception erro){
            throw new Exception("Data data inválida");
        }
    }
    public void setdata_cadastro(Date data_cadastro)throws Exception{
        this.data_cadastro = data_cadastro;
    }
     public String getconteudo() {
        return conteudo;
    }
    public void setconteudo(String conteudo)throws Exception {
        if(      
                (conteudo.length()>=3)
                &&
                (conteudo.length()<=50)
          )      
        this.conteudo = conteudo;
            else
                throw new Exception("Descrição da foto inválida.");
    }
    public int getid_multisegmentos(){
        return this.id_multisegmentos.intValue();
    } 
    public String getid_multisegmentosStr(){
        if(this.id_multisegmentos==null)
            return null;
        else
            return this.id_multisegmentos.toString();
    }
    public void setid_multisegmentos(String id_multisegmentos) throws Exception{
        if(id_multisegmentos==null)
            this.id_multisegmentos=null;
        else{
            try{
                this.id_multisegmentos=Integer.parseInt(id_multisegmentos);
            }
            catch (Exception erro){
                throw new Exception("Código de página inválido.");
            }
        }
    }
    public void setid_multisegmentos(int id_multisegmentos) throws Exception{
        this.id_multisegmentos=id_multisegmentos;
    }
    
    
    
    public int getid_categoria(){
        return this.id_categoria.intValue();
    } 
    public String getid_categoriaStr(){
        if(this.id_categoria==null)
            return null;
        else
            return this.id_categoria.toString();
    }
    public void setid_categoria(String id_categoria) throws Exception{
        if(id_categoria==null)
            this.id_categoria=null;
        else{
            try{
                this.id_categoria=Integer.parseInt(id_categoria);
            }
            catch (Exception erro){
                throw new Exception("Código de página inválido.");
            }
        }
    }
    public void setid_categoria(int id_multisegmentos) throws Exception{
        this.id_categoria=id_multisegmentos;
    }
    

    public int getid_menu(){
        return this.id_menu.intValue();
    } 
    public String getid_menuStr(){
        if(this.id_menu==null)
            return null;
        else
            return this.id_menu.toString();
    }
    public void setid_menu(String id_menu) throws Exception{
        if(id_menu==null)
            this.id_menu=null;
        else{
            try{
                this.id_menu=Integer.parseInt(id_menu);
            }
            catch (Exception erro){
                throw new Exception("Código de página inválido.");
            }
        }
    }
    public void setid_menu(int id_menu) throws Exception{
        this.id_menu=id_menu;
    }
     public void Clear(){
        this.id=0;
        this.id_multisegmentos=0;
        this.id_menu=0;
        this.id_categoria=0;
        this.titulo="";
        this.descricao="";
        this.SRC="";
        this.qrcode="";
        this.conteudo="";
        this.data_cadastro=null;  
    }

}