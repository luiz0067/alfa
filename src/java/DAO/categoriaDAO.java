package DAO;
import BD.Conexao;
import BEAN.categoriaBEAN;
import BEAN.categoriaBEAN;
import BEAN.menuBEAN;
import BEAN.multisegmentosBEAN;
import POJO.categoriaPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;
public class categoriaDAO{
    private Conexao MinhaConexao=null;
  
    private String SELECT="select categoria.id,categoria.id_categoria,categoria.id_tipo,categoria.titulo,categoria.src, categoria.descricao, categoria.inicio, categoria.id_multisegmentos , categoria.id_menu from categoria ";
    public categoriaDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private categoriaBEAN inserir(categoriaBEAN categoria)throws Exception{
        categoriaBEAN resultado=new categoriaBEAN();
        String mensagem="Erro ao cadastrar categoria.";
        try{
            String SQL="insert into categoria("
                    + "categoria.id_multisegmentos, "
                    + "categoria.id_menu, "
                    + "categoria.id_categoria ,"
                    + "categoria.id_tipo ,"
                    + "categoria.titulo,"
                    + "categoria.src, "
                    + "categoria.descricao, "
                    + "categoria.inicio "
                    + ") "
                    + "values("
                        +categoria.getID_multisegmentosStr()+","
                        +categoria.getID_menuStr()+","
                        +categoria.getID_categoriaStr()+","
                        +categoria.getID_tipoStr()+","
                        +Conexao.sqlProtection(categoria.getTitulo())+","
                        +Conexao.sqlProtection(categoria.getSRC())+","
                        +Conexao.sqlProtection(categoria.getDescricao())+","
                        +Conexao.sqlProtection(categoria.getInicioStr("yyyy-MM-dd HH:mm:ss"))+""
                    + ")";
                if(this.MinhaConexao.Executa(SQL)){
                resultado=buscarUltimo(categoria);
                this.MinhaConexao.Executa("commit");
            }
            else
               throw new Exception(mensagem); 
        }
        catch(Exception erro){throw new Exception(mensagem);}
        return resultado;
    }
    private categoriaBEAN alterar(categoriaBEAN categoria,String path_upload)throws Exception{
        categoriaBEAN resultado=new categoriaBEAN();
        String mensagem="Erro ao cadastrar categoria.";
        try{
            String condicao="";
            if(categoria.getIDStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id ="+categoria.getIDStr()+")";
                
                if(categoria.getID_menuStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(categoria.id_menu ="+categoria.getID_menuStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(categoria.id_menu is null)";
                }
                if(categoria.getID_multisegmentosStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(categoria.id_multisegmentos ="+categoria.getID_multisegmentosStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(categoria.id_multisegmentos is null)";
                }
                if(categoria.getID_tipoStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(categoria.id_tipo ="+categoria.getID_tipoStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(categoria.id_tipo is null)";
                }
                if(categoria.getID_categoriaStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(categoria.id_categoria ="+categoria.getID_categoriaStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(categoria.id_categoria is null)";
                }
            }
            String SQL=" update categoria set ";
            SQL+="id_multisegmentos="+categoria.getID_multisegmentosStr()+",";
            SQL+="id_menu="+categoria.getID_menuStr()+",";
            SQL+="id_categoria="+categoria.getID_categoriaStr()+",";
            if((categoria.getSRC()!=null)&&(categoria.getSRC().length()!=0)){
                SQL+="src="+Conexao.sqlProtection(categoria.getSRC())+",";
                categoriaBEAN categoria1=buscarID(categoria);
                if((categoria1.getIDStr()!=null)&&((categoria1.getSRC()!=null)&&(categoria1.getSRC().length()!=0))){
                    if (categoria1.getSRC()!=null){
                        functions.deletaImagensRedimencionadas(path_upload,categoria1.getSRC());
                    }
                }  
            }
            SQL+="titulo=" +Conexao.sqlProtection(categoria.getTitulo())+"," ;
            SQL+="descricao="+Conexao.sqlProtection(categoria.getDescricao())+",";
            SQL+="inicio="+Conexao.sqlProtection(categoria.getInicioStr("yyyy-MM-dd HH:mm:ss"))+" ";
            SQL+=" where("+condicao+");";                      
            if(this.MinhaConexao.Executa(SQL)){
                resultado=buscarID(categoria);
                this.MinhaConexao.Executa("commit");
            }
            else
               throw new Exception(mensagem); 
        }
        catch(Exception erro){throw new Exception(mensagem);}
        return resultado;
    }
     public categoriaBEAN excluir(categoriaBEAN categoria,String path_upload)throws Exception{
        String condicao="";
        if(categoria.getIDStr()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(categoria.id ="+categoria.getIDStr()+")";

            if(categoria.getID_menuStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_menu ="+categoria.getID_menuStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_menu is null)";
            }
            if(categoria.getID_multisegmentosStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_multisegmentos ="+categoria.getID_multisegmentosStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_multisegmentos is null)";
            }
            if(categoria.getID_categoriaStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_categoria ="+categoria.getID_categoriaStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_categoria is null)";
            }
        }
        String SQL=" delete from categoria where("+condicao+");";   
        try{
            categoria=buscarID(categoria);
            if(categoria.getIDStr()!=null){
                if (categoria.getSRC()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,categoria.getSRC());
                }
            }            
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return categoria;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir foto.");
        }
    }
    public categoriaBEAN salvar(categoriaBEAN categoria, String path_upload)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((categoria.getIDStr()!=null)&&(buscarID(categoria).getIDStr()!=null)){
            return alterar(categoria, path_upload);
        }
        else{
           return inserir(categoria);
        }
    } 
    public categoriaBEAN buscarUltimo(categoriaBEAN categoria)throws Excecao{
        String condicao="";
        if(categoria.getID_menuStr()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(categoria.id_menu ="+categoria.getID_menuStr()+")";
        }
        else{
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(categoria.id_menu is null)";
        }
        if(categoria.getID_multisegmentosStr()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(categoria.id_multisegmentos ="+categoria.getID_multisegmentosStr()+")";
        }
        else{
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(categoria.id_multisegmentos is null)";
        }
        if(categoria.getID_categoriaStr()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(categoria.id_categoria ="+categoria.getID_categoriaStr()+")";
        }
        else{
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(categoria.id_categoria is null)";
       }
       categoriaBEAN ultimo = new categoriaBEAN();
       try{ultimo= ((categoriaBEAN)buscar(SELECT+" where("+condicao+") order by categoria.id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public categoriaBEAN buscarPrimeiro()throws Excecao{
       categoriaBEAN Primeiro = new categoriaBEAN();
       try{Primeiro= ((categoriaBEAN)buscar(SELECT+" order by categoria.titulo asc").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public categoriaBEAN buscarID(categoriaBEAN categoria)throws Excecao{
       categoriaBEAN registro = new categoriaBEAN();
       try{registro= ((categoriaBEAN)buscar(SELECT+" where(categoria.id="+categoria.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar(SELECT);}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public Vector buscarUltimos(int posicao,int numero_registros)throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar(SELECT+" order by categoria.id desc limit "+((posicao-1)*numero_registros)+","+numero_registros);}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public int contador()throws Excecao{       
        int resultado=0;
        String SQL="select categoria.id from categoria";
        try{
            MinhaConexao.Busca(SQL);
            while(MinhaConexao.MoverProximo()){
                resultado++;
            }
            resultado=MinhaConexao.MostrarCampoInteger("n1").intValue();
        }
        catch(Exception erro){
        }
        return resultado;        
    }
   
    public Vector buscar(categoriaBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL=SELECT;
            String condicao="";
            if (categoria.getTitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.titulo like '%"+categoria.getTitulo().replaceAll("'","''") +"%')";
            }
             if(categoria.getDescricao()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(categoria.descricao like '%"+categoria.getDescricao().replaceAll("'","''") +"%')";
            }
            if(categoria.getID_multisegmentosStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_multisegmentos ="+categoria.getID_multisegmentosStr()+")";
            }  
            if(categoria.getID_menuStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_menu ="+categoria.getID_menuStr()+")";
            }  
            if (categoria.getInicio()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.inicio='"+categoria.getInicioStr("yyyy-MM-dd HH:mm:ss")+"')";
            }    
             
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;        
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
    
    public Vector buscar(String SQL)throws Excecao{
        Vector categorias=new Vector();
        try{
            if(SQL==null)
                SQL = SELECT;                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       categoriaPOJO POJO = new  categoriaPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setID_categoria(MinhaConexao.MostrarCampoInteger("id_categoria"));                             
                       POJO.setID_multisegmentos(MinhaConexao.MostrarCampoInteger("id_multisegmentos"));                             
                       POJO.setID_menu(MinhaConexao.MostrarCampoInteger("id_menu"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       POJO.setDescricao(MinhaConexao.MostrarCampoStr("descricao"));                              
                       POJO.setSRC(MinhaConexao.MostrarCampoStr("src")); 
                       POJO.setInicio(MinhaConexao.MostrarCampoDate("inicio")); 
                       categoriaBEAN categoria_BEAN= new categoriaBEAN(POJO);
                       categorias.add(categoria_BEAN);
                  }                                                  
            }
            return categorias;
        }    
        catch (Exception erro)
        {
            throw new Excecao(" erro ao buscar categoria! ERRO: ");
        }
    }
    
     public Vector buscarPorsegmento(categoriaBEAN categoria)throws Excecao{
        try{
            String SQL="";
            if (
                    (categoria.getID_multisegmentosStr()==null)
                    ||
                    (
                        (categoria.getID_multisegmentosStr()!=null)
                         &&
                        (categoria.getID_multisegmentos()==0)
                    )
            )
            {
                String id=(new categoriaDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                categoria.setID_multisegmentos(id);
            }
            else{}
            if (categoria.getID_multisegmentosStr()!=null){
                SQL=SELECT+" where(categoria.id_multisegmentos="+categoria.getID_multisegmentosStr()+");";
            }         
            return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorsegmento(categoriaBEAN categoria,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                    (categoria.getID_multisegmentosStr()==null)
                    ||
                    (
                        (categoria.getID_multisegmentosStr()!=null)
                         &&
                        (categoria.getID_multisegmentos()==0)
                    )
            )
            {
                String id=(new categoriaDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                categoria.setID_multisegmentos(id);
            }
            if (categoria.getID_multisegmentosStr()!=null){
                SQL=SELECT+" where(fotos.id_categoria="+categoria.getID_multisegmentosStr()+");";
            }
            else{
                SQL=SELECT+" order by fotos.id desc ";
            }
         return buscar(SQL+" limit 0,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorsegmento(multisegmentosBEAN multisegmentos,int quantidade)throws Excecao{
        try{
            String SQL="";
            categoriaBEAN categoria=new categoriaBEAN();
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
                String id=(new categoriaDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                
                multisegmentos.setID(id);
            }
            else{
                multisegmentos.setID(multisegmentos.getIDStr());
            }
            if (multisegmentos.getIDStr()!=null){
                SQL=SELECT+" where(categoria.id_multisegmentos="+categoria.getID_multisegmentosStr()+") ";
            }
            else{
                SQL=SELECT+" order by fotos.id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorMenuSegmentoCategoria(categoriaBEAN categoria)throws Excecao{
       try{
            String condicao="";
            if(categoria.getID_menuStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_menu ="+categoria.getID_menuStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_menu is null)";
            }
            if(categoria.getID_multisegmentosStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_multisegmentos ="+categoria.getID_multisegmentosStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_multisegmentos is null)";
            }
            if(categoria.getID_categoriaStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_categoria ="+categoria.getID_categoriaStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_categoria is null)";
            }
            
            String SQL=SELECT+" where("+condicao+");";
            return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar categoria! ERRO:");
       }
    }
    public Vector buscarPorMenuSegmentoCategoria(categoriaBEAN categoria,int posicao,int numero_registros)throws Excecao{
       try{
            String condicao="";
            if(categoria.getID_menuStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_menu ="+categoria.getID_menuStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_menu is null)";
            }
            if(categoria.getID_multisegmentosStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_multisegmentos ="+categoria.getID_multisegmentosStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_multisegmentos is null)";
            }
            if(categoria.getID_categoriaStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_categoria ="+categoria.getID_categoriaStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(categoria.id_categoria is null)";
            }
            
            String SQL=SELECT+" where("+condicao+") order by categoria.id desc ";
            SQL+=" limit "+((posicao-1)*numero_registros)+","+numero_registros;
            return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar categoria! ERRO:");
       }
    }
    public Vector buscarPormenu(categoriaBEAN categoria,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                   (categoria.getID_menuStr()==null)
                    ||
                    (
                        (categoria.getID_menuStr()!=null)
                         &&
                        (categoria.getID_menu()==0)
                    )
            )
            {
                String id=(new menuDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                categoria.setID_menu(id);
            }
            if (categoria.getID_menuStr()!=null){
                SQL=SELECT+" where(categoria.id_menu="+categoria.getID_multisegmentosStr()+");";
            }
            else{
                SQL=SELECT+" order by categoria.id desc ";
            }
         return buscar(SQL+" limit 0,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar categoria! ERRO:");
       }
    }
    public Vector buscarPormenu(menuBEAN menu,int quantidade)throws Excecao{
        try{
            String SQL="";
            categoriaBEAN categoria=new categoriaBEAN();
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
                categoria.setID_menu(id);
            }
            else{
                menu.setID(menu.getIDStr());
            }
            if (menu.getIDStr()!=null){
                SQL=SELECT+" where(categoria.id_menu="+categoria.getID_menuStr()+") ";
            }
            else{
                SQL=SELECT+" order by categoria.id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar categoria! ERRO:");
       }
    }
}