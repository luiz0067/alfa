package POJO;

import java.util.Date;

public class categoriaPOJO {
    private Integer ID;
    private Integer ID_categoria;
    private Integer ID_multisegmentos;
    private Integer ID_menu;
    private Integer ID_tipo;
    private String Titulo;    
    private String SRC; 
    private Date Inicio;
    private String Descricao;
    
    
    public Integer getID() {
        return ID;
    }
    public void setID(Integer ID) {
        this.ID = ID;
    }
    public Integer getID_multisegmentos() {
        return ID_multisegmentos;
    }
    
    public void setID_multisegmentos(Integer ID_multisegmentos) {
        this.ID_multisegmentos = ID_multisegmentos;
    }
    public String getTitulo() {
        return Titulo;
    }
    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }
    public void setSRC(String SRC) {
        this.SRC = SRC;
    }
    public String getSRC() {
        return this.SRC;
    }
    public Date getInicio() {
        return Inicio;
    }

    public void setInicio(Date Inicio) {
        this.Inicio = Inicio;
    }
    public String getDescricao() {
        return Descricao;
    }
    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }
    public Integer getID_categoria() {
        return ID_categoria;
    }
    
    public void setID_categoria(Integer ID_categoria) {
        this.ID_categoria=ID_categoria;
    }
    public Integer getID_tipo() {
        return ID_tipo;
    }
    
    public void setID_tipo(Integer ID_tipo) {
        this.ID_tipo = ID_tipo;
    }
    public Integer getID_menu() {
        return ID_menu;
    }
    
    public void setID_menu(Integer ID_menu) {
        this.ID_menu = ID_menu;
    }
}

