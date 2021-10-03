/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.usuarioBEAN;
import POJO.usuarioPOJO;
import excecoes.Excecao;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class usuarioDAO {
    private Conexao MinhaConexao=null;
    private String SELECT="select id,nome,login,senha from usuario ";
    public usuarioDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }   
    public usuarioBEAN salvar(usuarioBEAN usuario)throws Exception{
        try{
            if(this.MinhaConexao.isConectado())
                this.MinhaConexao.Abrir();
            if ((usuario.getIDStr()!=null)&&(buscarID(usuario).getIDStr()!=null)){
                return alterar(usuario);
            }
            else{
               return inserir(usuario);
            }
        }
        catch(Exception erro){
            throw new Exception(erro.getMessage());
        }
    }
    private usuarioBEAN inserir(usuarioBEAN usuario)throws Exception{
        String msg="Erro ao cadastrar usuário.";
        String SQL="insert into usuario(nome,login,senha) values("
                +Conexao.sqlProtection(usuario.getNome())+","
                +Conexao.sqlProtection(usuario.getLogin())+","
                +Conexao.sqlProtection(usuario.getSenha())+""
                + ")";
        try{
            usuario=buscarLogin(usuario.getLogin());
            if (usuario.getIDStr()==null){
                this.MinhaConexao.Executa(SQL);
                this.MinhaConexao.Executa("commit");
                return buscarUltimo();
            }
            else{
                msg="Usuário já existe";
                throw new Exception(msg);
            }
        }
        catch(Exception erro){
            throw new Exception(msg);
        }
    
    }
    private usuarioBEAN alterar(usuarioBEAN usuario)throws Exception{
        String SQL="update usuario set senha="+Conexao.sqlProtection(usuario.getSenha()) +",nome=" +Conexao.sqlProtection(usuario.getNome()) +" where(id="+usuario.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return buscarID(usuario);
        }
        catch(Exception erro){
            throw new Exception("Erro ao alterar senha do usuário.");
        }
    }
    public void alterarSenha(usuarioBEAN usuario,String senha_antiga)throws Exception{
        String SQL="update usuario set senha="+Conexao.sqlProtection(usuario.getSenha()) +" where(id="+usuario.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao alterar senha do usuário."+erro.getMessage());
        }
    }
    public void alterarNome(usuarioBEAN usuario)throws Exception{
        String SQL="update usuario set nome=" +Conexao.sqlProtection(usuario.getNome()) +" where(id="+usuario.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao nome do usuário."+erro.getMessage());
        }
           
    }
    public usuarioBEAN login(usuarioBEAN usuario){
        try{ 
         String SQL=SELECT+" where"
                 + "(login="+Conexao.sqlProtection(usuario.getLogin())+")"
                 + "and"
                  + "(senha="+Conexao.sqlProtection(usuario.getSenha())+")"
                 + ";";
            return ((usuarioBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
            return null;
       }
    }
    public void excluirConta(usuarioBEAN usuario)throws Exception{
        String SQL="delete from usuario where(id="+usuario.getID()+") and (login<>'root')";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir usuário."+erro.getMessage());
        }
    }
    public Vector buscarNome(String Nome)throws Excecao{
       try{ 
         String SQL=SELECT+" where(nome like'%"+Nome.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     } 
    public usuarioBEAN buscarLogin(String Login)throws Excecao{
       try{ 
         String SQL=SELECT+" where(login ='"+Login.replaceAll("'","''")+"');";
         usuarioBEAN resultado=new usuarioBEAN(); 
         Vector acc =buscar(SQL);
         if(acc.size()>0)
             resultado=(usuarioBEAN)acc.get(0);
         return resultado;
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     } 
    public Vector buscar(usuarioBEAN usuario)throws Excecao{
       try{ 
         String SQL=SELECT;
         String condicao="";
         if (usuario.getNome()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(nome like'%"+usuario.getNome().replaceAll("'","''") +"%')";
         }
         if(usuario.getLogin()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(login like'%"+usuario.getLogin().replaceAll("'","''") +"%')";
         }
         SQL=(condicao.length()>0)?SQL+" where "+condicao:SQL;
                
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     } 
    public usuarioBEAN buscarUltimo()throws Excecao{
       try{ 
         String SQL=SELECT+" order by id desc";
         
         return ((usuarioBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return new usuarioBEAN();
       }
     }
    public usuarioBEAN buscarID(usuarioBEAN usuario)throws Excecao{
       try{ 
         String SQL=SELECT+" where(id="+usuario.getIDStr()+");";
         return ((usuarioBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return new usuarioBEAN();
       }
     }
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(SELECT);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     }
    public usuarioBEAN excluir(usuarioBEAN usuario)throws Exception{
        String msg="Erro ao excluir usuario.";
        String SQL="delete from usuario where(id="+usuario.getID()+") and (login<>'root')";
        try{
            usuario= buscarID(usuario);
            if (usuario.getLogin().equals("root")){
                msg="O usuário root não pode ser excluido!";
                throw new Exception(msg);
            }
            else if (usuario.getLogin().equals("adm")){
                msg="O usuário adm não pode ser excluido!";
                throw new Exception(msg);
            }
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return usuario;
        }
        catch(Exception erro){
            throw new Exception(msg);
        }
    }
    public Vector buscar(String SQL)throws Excecao{
            Vector usuarios=new Vector();
            try{
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       usuarioPOJO usuario = new  usuarioPOJO();
                       usuario.setID(MinhaConexao.MostrarCampoInteger("id"));
                       usuario.setSenha(MinhaConexao.MostrarCampoStr("senha"));
                       usuario.setNome(MinhaConexao.MostrarCampoStr("nome"));
                       usuario.setLogin(MinhaConexao.MostrarCampoStr("login"));
                       usuarioBEAN usuario_BEAN= new usuarioBEAN(usuario);
                       usuarios.add(usuario_BEAN);
                  }                     
                              
            }
            return usuarios;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar atendimento! ERRO: "+ erro.getMessage());
        }
    }
    
}


