package DAO;
import BD.Conexao;
import BEAN.multisegmentosBEAN;
import POJO.multisegmentosPOJO;
import excecoes.Excecao;
import java.util.Vector;

public class multisegmentosDAO {
    private Conexao MinhaConexao=null;
    public multisegmentosDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private void inserir(multisegmentosBEAN multisegmentos)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into multisegmentos(titulo) values("+Conexao.sqlProtection(multisegmentos.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar multisegmentos.");}    
    }
    private void alterar(multisegmentosBEAN multisegmentos)throws Exception{
        try{
            this.MinhaConexao.Executa("update multisegmentos set titulo=" +Conexao.sqlProtection(multisegmentos.getTitulo()) +" where(id="+multisegmentos.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar titulo da multisegmentos.");}          
    }
    public multisegmentosBEAN excluir(multisegmentosBEAN multisegmentos)throws Exception{
        try{
            multisegmentos=buscarID(multisegmentos);
            this.MinhaConexao.Executa("delete from multisegmentos where(id="+multisegmentos.getID()+")");
            this.MinhaConexao.Executa("commit");
            return multisegmentos;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir multisegmentos.");}
    }
    public multisegmentosBEAN salvar(multisegmentosBEAN multisegmentos)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((multisegmentos.getIDStr()!=null)&&(buscarID(multisegmentos).getIDStr()!=null)){
            alterar(multisegmentos);
            return buscarID(multisegmentos);
        }
        else{
           inserir(multisegmentos);
           return buscarUltimo();
        }
    } 
    public multisegmentosBEAN buscarUltimo()throws Excecao{
       multisegmentosBEAN ultimo = new multisegmentosBEAN();
       try{ultimo= ((multisegmentosBEAN)buscar("select id,titulo from multisegmentos order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public multisegmentosBEAN buscarPrimeiro()throws Excecao{
       multisegmentosBEAN Primeiro = new multisegmentosBEAN();
       try{Primeiro= ((multisegmentosBEAN)buscar("select id,titulo from multisegmentos order by Titulo asc").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public multisegmentosBEAN buscarID(multisegmentosBEAN multisegmentos)throws Excecao{
       multisegmentosBEAN registro = new multisegmentosBEAN();
       try{registro= ((multisegmentosBEAN)buscar("select id,titulo from multisegmentos where(ID="+multisegmentos.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from multisegmentos ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from multisegmentos where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(multisegmentosBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL="select id,titulo from multisegmentos ";
            String condicao="";
            if (categoria.getTitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(titulo like '%"+categoria.getTitulo().replaceAll("'","''") +"%')";
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
       //try{registros = buscar(" select distinct multisegmentos.id,multisegmentos.titulo from multisegmentos right join banners on(banners.id_categoria=multisegmentos.id) order by banners.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       try{registros = buscar(" select multisegmentos.id,multisegmentos.titulo from multisegmentos order by multisegmentos.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,titulo from multisegmentos";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       multisegmentosPOJO POJO = new  multisegmentosPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       multisegmentosBEAN multisegmentos_BEAN= new multisegmentosBEAN(POJO);
                       registros.add(multisegmentos_BEAN);
                  }                                                  
            }
            return registros;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar multisegmentos! ERRO: ");
        }
    }
}