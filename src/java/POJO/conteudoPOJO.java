package POJO;

import java.util.Date;

public class conteudoPOJO {
    private Integer id;
    private Integer id_multisegmentos;
    private Integer id_menu;
    private Integer id_categoria;
    private String Titulo;  
    private String Descricao;
    private String SRC; 
    private String conteudo; 
    private Date data_cadastro;
    private String qrcode;
 
    
    
    public Integer getID() {
        return id;
    }
    public void setID(Integer ID) {
        this.id = ID;
    }
    public Integer getID_multisegmentos() {
        return id_multisegmentos;
    }
    
    public void setID_multisegmentos(Integer ID_multisegmentos) {
        this.id_multisegmentos = ID_multisegmentos;
    }

    
    public void setID_menu(Integer ID_menu) {
        this.id_menu = ID_menu;
    }
     public Integer getID_menu() {
        return id_menu;
    }
    public void setid_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }
     public Integer getid_categoria() {
        return id_categoria;
    } 
     
     
    public String getTitulo() {
        return Titulo;
    }
    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }
           public String getDescricao() {
        return Descricao;
    }
    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }
    public void setSRC(String SRC) {
        this.SRC = SRC;
    }
    public String getSRC() {
        return this.SRC;
    }
    public void setconteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    public String getconteudo() {
        return this.conteudo;
    }
    
    
    public void setqrcode(String qrcode) {
        this.qrcode = qrcode;
    }
    public String getqrcode() {
        return this.qrcode;
    }
    
    
    public Date getdata_cadastro() {
        return data_cadastro;
    }

    public void setInicio(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }
}