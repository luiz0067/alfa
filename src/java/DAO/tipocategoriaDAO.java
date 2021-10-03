package DAO;
import BD.Conexao;
import BEAN.tipocategoriaBEAN;
import POJO.tipocategoriaPOJO;
import excecoes.Excecao;
import java.util.Vector;

public class tipocategoriaDAO{
    private Conexao MinhaConexao=null;
    public tipocategoriaDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private void inserir(tipocategoriaBEAN tipo_categoria)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into tipo_categoria(titulo) values("+Conexao.sqlProtection(tipo_categoria.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar tipo_categoria.");}    
    }
    private void alterar(tipocategoriaBEAN tipo_categoria)throws Exception{
        try{
            this.MinhaConexao.Executa("update tipo_categoria set titulo=" +Conexao.sqlProtection(tipo_categoria.getTitulo()) +" where(id="+tipo_categoria.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar titulo da tipo_categoria.");}          
    }
    public tipocategoriaBEAN excluir(tipocategoriaBEAN tipo_categoria)throws Exception{
        try{
            tipo_categoria=buscarID(tipo_categoria);
            this.MinhaConexao.Executa("delete from tipo_categoria where(id="+tipo_categoria.getID()+")");
            this.MinhaConexao.Executa("commit");
            return tipo_categoria;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir tipo_categoria.");}
    }
    public tipocategoriaBEAN salvar(tipocategoriaBEAN tipo_categoria)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((tipo_categoria.getIDStr()!=null)&&(buscarID(tipo_categoria).getIDStr()!=null)){
            alterar(tipo_categoria);
            return buscarID(tipo_categoria);
        }
        else{
           inserir(tipo_categoria);
           return buscarUltimo();
        }
    } 
    public tipocategoriaBEAN buscarUltimo()throws Excecao{
       tipocategoriaBEAN ultimo = new tipocategoriaBEAN();
       try{ultimo= ((tipocategoriaBEAN)buscar("select id,titulo from tipo_categoria order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public tipocategoriaBEAN buscarPrimeiro()throws Excecao{
       tipocategoriaBEAN Primeiro = new tipocategoriaBEAN();
       try{Primeiro= ((tipocategoriaBEAN)buscar("select id,titulo from tipo_categoria order by Titulo asc").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public tipocategoriaBEAN buscarID(tipocategoriaBEAN tipo_categoria)throws Excecao{
       tipocategoriaBEAN registro = new tipocategoriaBEAN();
       try{registro= ((tipocategoriaBEAN)buscar("select id,titulo from tipo_categoria where(ID="+tipo_categoria.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from tipo_categoria ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from tipo_categoria where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(tipocategoriaBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL="select id,titulo from tipo_categoria ";
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
       //try{registros = buscar(" select distinct tipo_categoria.id,tipo_categoria.titulo from tipo_categoria right join banners on(banners.id_categoria=tipo_categoria.id) order by banners.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       try{registros = buscar(" select tipo_categoria.id,tipo_categoria.titulo from tipo_categoria order by tipo_categoria.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,titulo from tipo_categoria";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       tipocategoriaPOJO POJO = new  tipocategoriaPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       tipocategoriaBEAN tipo_categoria_BEAN= new tipocategoriaBEAN(POJO);
                       registros.add(tipo_categoria_BEAN);
                  }                                                  
            }
            return registros;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar tipo_categoria! ERRO: ");
        }
    }
}