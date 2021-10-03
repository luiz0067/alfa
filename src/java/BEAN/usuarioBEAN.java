/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import POJO.usuarioPOJO;

/**
 *
 * @author PC
 */
public class usuarioBEAN {
    private Integer ID;
    private String Nome;
    private String Login;
    private String Senha;

    /**
     * @return the Nome
     */
    public usuarioBEAN(){
        this.ID=null;
        this.Nome=null;
        this.Login=null;
        this.Senha=null;
    }
     public usuarioBEAN(usuarioPOJO POJO){
        this.ID=POJO.getID();
        this.Nome=POJO.getNome();
        this.Login=POJO.getLogin();
        this.Senha=POJO.getSenha();
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
            throw new Exception("Código de usuário inválido.");
        }
    }
    public String getNome() {
        return Nome;
    }

    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome)throws Exception {
        if(
                (Nome!=null)
                &&
                (Nome.length()>=3)
                &&
                (Nome.length()<=50)
          )
        this.Nome = Nome;
            else
                throw new Exception("O nome do usuário deve ter entre 3 e 50 letras.");
    }

    /**
     * @return the Login
     */
    public String getLogin() {
        return Login;
    }

    /**
     * @param Login the Login to set
     */
    public void setLogin(String Login) throws Exception {
        if(
                (Login!=null)
                &&
                (Login.length()>=3)
                &&
                (Login.length()<=50)
          )
        this.Login = Login;
            else
                throw new Exception("Login inválido.");
    }

    /**
     * @return the Senha
     */
    public String getSenha() {
        return Senha;
    }

    /**
     * @param Senha the Senha to set
     */
    public void setSenha(String Senha)throws Exception {
        if(
                (Senha!=null)
                &&
                (Senha.length()>=3)
                &&
                (Senha.length()<=50)
          )
        this.Senha = Senha;
            else
                throw new Exception("Senha inválida.");
    }
    public void Clear(){
        this.ID=0;
        this.Nome="";
        this.Login="";
        this.Senha="";
    }
    
}

