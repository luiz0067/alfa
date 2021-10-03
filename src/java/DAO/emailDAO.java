package DAO;
import BD.Conexao;
import BEAN.*;
import POJO.emailPOJO;
import excecoes.Excecao;
import java.util.Vector;

public class emailDAO {
    private Conexao MinhaConexao=null;
    private String SELECT="select id,titulo,id_multisegmentos,id_multisegmentos from email ";
    public emailDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private void inserir(emailBEAN email)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into email(id_multisegmentos,id_menu, titulo) values("
                    +Conexao.sqlProtection(email.getID_multisegmentosStr())+","
                    +Conexao.sqlProtection(email.getID_menuStr())+","
                    +Conexao.sqlProtection(email.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar categoriaBanners.");}    
    }
    private void alterar(emailBEAN email)throws Exception{
        try{
            this.MinhaConexao.Executa("update categoriabanners set id_multisegmentos,id_menu, titulo=" 
                    +Conexao.sqlProtection(email.getID_multisegmentosStr())+","
                    +Conexao.sqlProtection(email.getID_menuStr())+","
                    +Conexao.sqlProtection(email.getTitulo())+"" 
                    +" where(id="+email.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar titulo da categoriaBanners.");}          
    }
    public emailBEAN excluir(emailBEAN email)throws Exception{
        try{
            email=buscarID(email);
            this.MinhaConexao.Executa("delete from email where(id="+email.getID()+")");
            this.MinhaConexao.Executa("commit");
            return email;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir email.");}
    }
    public emailBEAN salvar(emailBEAN email)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((email.getIDStr()!=null)&&(buscarID(email).getIDStr()!=null)){
            alterar(email);
            return buscarID(email);
        }
        else{
           inserir(email);
           return buscarUltimo();
        }
    } 
    public emailBEAN buscarUltimo()throws Excecao{
       emailBEAN ultimo = new emailBEAN();
       try{ultimo= ((emailBEAN)buscar(SELECT+" order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public emailBEAN buscarPrimeiro()throws Excecao{
       emailBEAN Primeiro = new emailBEAN();
       try{Primeiro= ((emailBEAN)buscar(SELECT+" order by Titulo asc").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public emailBEAN buscarID(emailBEAN email)throws Excecao{
       emailBEAN registro = new emailBEAN();
       try{registro= ((emailBEAN)buscar(SELECT+"  where(ID="+email.getIDStr()+");").get(0));}catch(Exception erro){}
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
    public Vector buscar(emailBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL= SELECT;
            String condicao="";
            if (categoria.getTitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(email.titulo like '%"+categoria.getTitulo().replaceAll("'","''") +"%')";
            }       
             if(categoria.getID_multisegmentosStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(email.id_multisegmentos ="+categoria.getID_multisegmentosStr()+")";
         }  
             if(categoria.getID_menuStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(email.id_menu ="+categoria.getID_menuStr()+")";
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
       //try{registros = buscar(" select distinct email.id,email.titulo from email right join banners on(banners.id_categoria=email.id) order by banners.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       try{registros = buscar(" select email.id,email.titulo from email order by email.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,titulo from email";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       emailPOJO POJO = new  emailPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setID_multisegmentos(MinhaConexao.MostrarCampoInteger("id_multisegmentos"));                             
                       POJO.setID_menu(MinhaConexao.MostrarCampoInteger("id_menu"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       emailBEAN email_BEAN= new emailBEAN(POJO);
                       registros.add(email_BEAN);
                  }                                                  
            }
            return registros;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar email! ERRO: ");
        }
    }
     public Vector buscarPorsegmento(emailBEAN email)throws Excecao{
        try{
            String SQL="";
            if (
                    (email.getID_multisegmentosStr()==null)
                    ||
                    (
                        (email.getID_multisegmentosStr()!=null)
                         &&
                        (email.getID_multisegmentos()==0)
                    )
            )
            {
                String id=(new emailDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                email.setID_multisegmentos(id);
            }
            else{}
            if (email.getID_multisegmentosStr()!=null){
                SQL=SELECT+" where(email.id_multisegmentos="+email.getID_multisegmentosStr()+");";
            }         
            return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar email! ERRO:");
       }
    }
    public Vector buscarPorsegmento(emailBEAN email,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                    (email.getID_multisegmentosStr()==null)
                    ||
                    (
                        (email.getID_multisegmentosStr()!=null)
                         &&
                        (email.getID_multisegmentos()==0)
                    )
            )
            {
                String id=(new emailDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                email.setID_multisegmentos(id);
            }
            if (email.getID_multisegmentosStr()!=null){
                SQL=SELECT+" where(email.id_email="+email.getID_multisegmentosStr()+");";
            }
            else{
                SQL=SELECT+" order by email.id desc ";
            }
         return buscar(SQL+" limit 0,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar email! ERRO:");
       }
    }
    public Vector buscarPorsegmento(multisegmentosBEAN multisegmentos,int quantidade)throws Excecao{
        try{
            String SQL="";
            emailBEAN email=new emailBEAN();
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
                String id=(new emailDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                
                multisegmentos.setID(id);
            }
            else{
                multisegmentos.setID(multisegmentos.getIDStr());
            }
            if (multisegmentos.getIDStr()!=null){
                SQL=SELECT+" where(email.id_multisegmentos="+email.getID_multisegmentosStr()+") ";
            }
            else{
                SQL=SELECT+" order by email.id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar email! ERRO:");
       }
    }
    
public Vector buscarPormenu(emailBEAN email)throws Excecao{
        try{
            String SQL="";
            if (
                    (email.getID_menuStr()==null)
                    ||
                    (
                        (email.getID_menuStr()!=null)
                         &&
                        (email.getID_menu()==0)
                    )
            )
            {
                String id=(new menuDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                email.setID_menu(id);
            }
            else{}
            if (email.getID_menuStr()!=null){
                SQL=SELECT+" where(email.id_menu="+email.getID_menuStr()+");";
            }         
            return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar email! ERRO:");
       }
    }
    public Vector buscarPormenu(emailBEAN email,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                   (email.getID_menuStr()==null)
                    ||
                    (
                        (email.getID_menuStr()!=null)
                         &&
                        (email.getID_menu()==0)
                    )
            )
            {
                String id=(new menuDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                email.setID_menu(id);
            }
            if (email.getID_menuStr()!=null){
                SQL=SELECT+" where(email.id_menu="+email.getID_multisegmentosStr()+");";
            }
            else{
                SQL=SELECT+" order by email.id desc ";
            }
         return buscar(SQL+" limit 0,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar email! ERRO:");
       }
    }
    public Vector buscarPormenu(menuBEAN menu,int quantidade)throws Excecao{
        try{
            String SQL="";
            emailBEAN email=new emailBEAN();
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
                email.setID_menu(id);
            }
            else{
                menu.setID(menu.getIDStr());
            }
            if (menu.getIDStr()!=null){
                SQL=SELECT+" where(email.id_menu="+email.getID_menuStr()+") ";
            }
            else{
                SQL=SELECT+" order by email.id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar email! ERRO:");
       }
    }
}