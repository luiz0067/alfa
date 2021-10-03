/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.menuBEAN;
import BEAN.multisegmentosBEAN;
import POJO.menuPOJO;
import excecoes.Excecao;
import java.util.Vector;

/**
 *
 * @author Paty
 */
public class menuDAO {
private Conexao MinhaConexao=null;
    private String SELECT="select id,titulo,id_multisegmentos from menu ";
    public menuDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private void inserir(menuBEAN menu)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into menu(id_multisegmentos, titulo) values("
                    +Conexao.sqlProtection(menu.getID_multisegmentosStr())+","
                    +Conexao.sqlProtection(menu.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar categoriaBanners.");}    
    }
    private void alterar(menuBEAN menu)throws Exception{
        try{
            this.MinhaConexao.Executa("update categoriabanners set titulo, id_multisegmentos=" 
                    +Conexao.sqlProtection(menu.getID_multisegmentosStr())+","
                    +Conexao.sqlProtection(menu.getTitulo())+"" 
                    +" where(id="+menu.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar titulo da categoriaBanners.");}          
    }
    public menuBEAN excluir(menuBEAN menu)throws Exception{
        try{
            menu=buscarID(menu);
            this.MinhaConexao.Executa("delete from menu where(id="+menu.getID()+")");
            this.MinhaConexao.Executa("commit");
            return menu;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir menu.");}
    }
    public menuBEAN salvar(menuBEAN menu)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((menu.getIDStr()!=null)&&(buscarID(menu).getIDStr()!=null)){
            alterar(menu);
            return buscarID(menu);
        }
        else{
           inserir(menu);
           return buscarUltimo();
        }
    } 
    public menuBEAN buscarUltimo()throws Excecao{
       menuBEAN ultimo = new menuBEAN();
       try{ultimo= ((menuBEAN)buscar(SELECT+" order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public menuBEAN buscarPrimeiro()throws Excecao{
       menuBEAN Primeiro = new menuBEAN();
       try{Primeiro= ((menuBEAN)buscar(SELECT+" order by Titulo asc limit 0,1").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public menuBEAN buscarPrimeiroPorsegmento(menuBEAN menu)throws Excecao{       
        menuBEAN ultimo = new menuBEAN();
        try{ultimo= ((menuBEAN)buscar(SELECT+" where(menu.id_multisegmentos="+menu.getID_multisegmentosStr()+") order by Titulo asc limit 0,1").get(0));}catch(Exception erro){}
        return ultimo;
    }
    public menuBEAN buscarID(menuBEAN menu)throws Excecao{
       menuBEAN registro = new menuBEAN();
       try{registro= ((menuBEAN)buscar(SELECT+"  where(ID="+menu.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar(SELECT);}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar(SELECT+"  where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(menuBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL= SELECT;
            String condicao="";
            if (categoria.getTitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(menu.titulo like '%"+categoria.getTitulo().replaceAll("'","''") +"%')";
            }       
             if(categoria.getID_multisegmentosStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(menu.id_multisegmentos ="+categoria.getID_multisegmentosStr()+")";
         }  
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;        
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
    public Vector buscarUltimasAtualizadas()throws Excecao{
       Vector registros=new Vector();
       //try{registros = buscar(" select distinct menu.id,menu.titulo from menu right join banners on(banners.id_categoria=menu.id) order by banners.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       try{registros = buscar(" select menu.id,menu.titulo from menu order by menu.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,titulo from menu";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       menuPOJO POJO = new  menuPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setID_multisegmentos(MinhaConexao.MostrarCampoInteger("id_multisegmentos"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       menuBEAN menu_BEAN= new menuBEAN(POJO);
                       registros.add(menu_BEAN);
                  }                                                  
            }
            return registros;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar menu! ERRO: ");
        }
    }
     public Vector buscarPorsegmento(menuBEAN menu)throws Excecao{
        try{
            String SQL="";
            if (
                    (menu.getID_multisegmentosStr()==null)
                    ||
                    (
                        (menu.getID_multisegmentosStr()!=null)
                         &&
                        (menu.getID_multisegmentos()==0)
                    )
            )
            {
                String id=(new menuDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                menu.setID_multisegmentos(id);
            }
            else{}
            if (menu.getID_multisegmentosStr()!=null){
                SQL=SELECT+" where(menu.id_multisegmentos="+menu.getID_multisegmentosStr()+");";
            }         
            return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar menu! ERRO:");
       }
    }
     
     public Vector buscarPorsegmento(multisegmentosBEAN menu)throws Excecao{
        try{
            String SQL="";
            if (
                    (menu.getIDStr()==null)
                    ||
                    (
                        (menu.getIDStr()!=null)
                         &&
                        (menu.getID()==0)
                    )
            )
            {
                String id=(new menuDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                menu.setID(id);
            }
            else{}
            if (menu.getIDStr()!=null){
                SQL=SELECT+" where(menu.id_multisegmentos="+menu.getIDStr()+");";
            }         
            return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar menu! ERRO:");
       }
    }
    public Vector buscarPorsegmento(menuBEAN menu,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                    (menu.getID_multisegmentosStr()==null)
                    ||
                    (
                        (menu.getID_multisegmentosStr()!=null)
                         &&
                        (menu.getID_multisegmentos()==0)
                    )
            )
            {
                String id=(new menuDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                menu.setID_multisegmentos(id);
            }
            if (menu.getID_multisegmentosStr()!=null){
                SQL=SELECT+" where(menu.id_menu="+menu.getID_multisegmentosStr()+");";
            }
            else{
                SQL=SELECT+" order by menu.id desc ";
            }
         return buscar(SQL+" limit 0,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar menu! ERRO:");
       }
    }
    public Vector buscarPorsegmento(multisegmentosBEAN multisegmentos,int quantidade)throws Excecao{
        try{
            String SQL="";
            menuBEAN menu=new menuBEAN();
            if (
                    (multisegmentos.getIDStr()==null)
                    ||
                    (
                        (multisegmentos.getIDStr()!=null)
                         &&
                        (multisegmentos.getID()==0)
                    )
            )
            {
                String id=(new menuDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                
                multisegmentos.setID(id);
            }
            else{
                multisegmentos.setID(multisegmentos.getIDStr());
            }
            if (multisegmentos.getIDStr()!=null){
                SQL=SELECT+" where(menu.id_multisegmentos="+menu.getID_multisegmentosStr()+") ";
            }
            else{
                SQL=SELECT+" order by menu.id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar menu! ERRO:");
       }
    }
    
}
