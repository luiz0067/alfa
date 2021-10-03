package DAO;
import BD.Conexao;
import BEAN.categoriaBEAN;
import BEAN.conteudoBEAN;
import BEAN.menuBEAN;
import BEAN.multisegmentosBEAN;
import POJO.conteudoPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;
public class conteudoDAO{
    private Conexao MinhaConexao=null;
    private String SELECt="select conteudo.id,conteudo.id_multisegmentos,conteudo.id_menu, conteudo.id_categoria, conteudo.titulo, conteudo.descricao , conteudo.src, conteudo.qrcode, conteudo.conteudo, conteudo.data_cadastro from conteudo ";
    private Object id;
    public conteudoDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private conteudoBEAN inserir(conteudoBEAN conteudo)throws Exception{
        conteudoBEAN resultado=new conteudoBEAN();
        try{
            String SQl="insert into conteudo(conteudo.id_multisegmentos,conteudo.id_menu,conteudo.id_categoria, conteudo.titulo, conteudo.descricao , conteudo.src , conteudo.qrcode, conteudo.conteudo , conteudo.data_cadastro) values("
                    +conteudo.getid_multisegmentosStr()+","
                    +conteudo.getid_menuStr()+","
                    +conteudo.getid_categoriaStr()+","
                    +Conexao.sqlProtection(conteudo.gettitulo())+","
                    +Conexao.sqlProtection(conteudo.getdescricao())+","
                    +Conexao.sqlProtection(conteudo.getSRC())+","
                    +Conexao.sqlProtection(conteudo.getqrcode())+","
                    +Conexao.sqlProtection(conteudo.getconteudo())+","
                    +Conexao.sqlProtection(conteudo.getdata_cadastroStr("yyyy-MM-dd HH:mm:ss"))+""
            + ")";
            if(!this.MinhaConexao.Executa(SQl)){
                throw new Exception("Erro ao cadastrar conteudo.");
            }
            else{
                resultado=buscarUltimo();
                this.MinhaConexao.Executa("commit");
            }
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar conteudo.");} 
        return  resultado;
    }
    private conteudoBEAN alterar(conteudoBEAN conteudo,String path_upload)throws Exception{
        conteudoBEAN resultado=new conteudoBEAN();
        try{
            String condicao="";
            if(conteudo.getidStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id ="+conteudo.getidStr()+")";
                
                if(conteudo.getid_menuStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_menu ="+conteudo.getid_menuStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_menu is null)";
                }
                if(conteudo.getid_multisegmentosStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_multisegmentos ="+conteudo.getid_multisegmentosStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_multisegmentos is null)";
                }
                if(conteudo.getid_categoriaStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_categoria ="+conteudo.getid_categoriaStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_categoria is null)";
                }
            }
            
            String SQL=" update conteudo set ";
            SQL+="titulo=" +Conexao.sqlProtection(conteudo.gettitulo())+"," ;
            SQL+="descricao="+Conexao.sqlProtection(conteudo.getdescricao())+",";
            SQL+="conteudo="+Conexao.sqlProtection(conteudo.getconteudo())+",";
            if((conteudo.getSRC()!=null)&&(conteudo.getSRC().length()!=0)){
                SQL+="src="+Conexao.sqlProtection(conteudo.getSRC())+",";
                conteudoBEAN conteudo1=buscarid(conteudo);
                if((conteudo1.getidStr()!=null)&&((conteudo1.getSRC()!=null)&&(conteudo1.getSRC().length()!=0))){
                    if (conteudo1.getSRC()!=null){
                        functions.deletaImagensRedimencionadas(path_upload,conteudo1.getSRC());
                    }
                }  
                SQL+="qrcode="+Conexao.sqlProtection(conteudo.getqrcode())+",";
            }
            SQL+="id_multisegmentos="+conteudo.getid_multisegmentosStr()+",";
            SQL+="id_menu="+conteudo.getid_menuStr()+",";
            SQL+="id_categoria="+conteudo.getid_categoriaStr()+"";
            SQL+=" where("+condicao+");";                      
            if(!this.MinhaConexao.Executa(SQL)){
                throw new Exception("Erro ao salvar foto.");
            }
            else{
                this.MinhaConexao.Executa("commit");
                resultado=buscarid(conteudo);
            }
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar foto.");
        }   
        return resultado;
    }
     public conteudoBEAN excluir(conteudoBEAN conteudo,String path_upload)throws Exception{
        try{
            String condicao="";
            if(conteudo.getidStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id ="+conteudo.getidStr()+")";

                if(conteudo.getid_menuStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_menu ="+conteudo.getid_menuStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_menu is null)";
                }
                if(conteudo.getid_multisegmentosStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_multisegmentos ="+conteudo.getid_multisegmentosStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_multisegmentos is null)";
                }
                if(conteudo.getid_categoriaStr()!=null){
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_categoria ="+conteudo.getid_categoriaStr()+")";
                }
                else{
                    if (condicao.length()>0)
                        condicao+="and";
                    condicao+="(conteudo.id_categoria is null)";
                }
            }
            String SQL=" delete from conteudo where "+condicao;
            conteudo=buscarid(conteudo);
            if(conteudo.getidStr()!=null){
                if (conteudo.getSRC()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,conteudo.getSRC());
                }
            }            
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return conteudo;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir foto.");
        }
    }
    public conteudoBEAN salvar(conteudoBEAN conteudo, String path_upload)throws Exception{
        conteudoBEAN resultado=null;
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((conteudo.getidStr()!=null)&&(buscarid(conteudo).getidStr()!=null)){
            alterar(conteudo, path_upload);
            return buscarid(conteudo);
        }
        else{
            resultado=inserir(conteudo);
            buscarUltimo();
        }
        return resultado;
    } 
    public conteudoBEAN buscarUltimo()throws Excecao{
       conteudoBEAN ultimo = new conteudoBEAN();
       try{ultimo= ((conteudoBEAN)buscar(SELECt+" order by conteudo.id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public conteudoBEAN buscarPrimeiro()throws Excecao{
       conteudoBEAN Primeiro = new conteudoBEAN();
       try{Primeiro= ((conteudoBEAN)buscar(SELECt+" order by conteudo.titulo asc").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public conteudoBEAN buscarid(conteudoBEAN conteudo)throws Excecao{
       conteudoBEAN registro = new conteudoBEAN();
       try{registro= ((conteudoBEAN)buscar(SELECt+" where(conteudo.id="+conteudo.getidStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscartodos(int posicao,int numero_registros)throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar(SELECt+"limit "+((posicao-1)*numero_registros)+","+numero_registros);}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public Vector buscarUltimos(int posicao,int numero_registros)throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar(SELECt+" order by conteudo.id desc limit "+((posicao-1)*numero_registros)+","+numero_registros);}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public int contador()throws Excecao{       
        int resultado=0;
        String SQL="select conteudo.id from conteudo";
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
   
    public Vector buscartitulo(String titulo,int posicao,int numero_registros)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar(SELECt+" where(conteudo.titulo like'%"+titulo.replaceAll("'","''") +"%')"+"limit "+((posicao-1)*numero_registros)+","+numero_registros);}catch(Exception erro){throw new Excecao("Erro ao buscar titulo ERRO:");}
       return registros;
    }
    public Vector buscar(conteudoBEAN conteudo,int posicao,int numero_registros)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL=SELECt;
            String condicao="";
             if(conteudo.getid_menuStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_menu ="+conteudo.getid_menuStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_menu is null)";
            }
            if(conteudo.getid_multisegmentosStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_multisegmentos ="+conteudo.getid_multisegmentosStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_multisegmentos is null)";
            }
            if(conteudo.getid_categoriaStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_categoria ="+conteudo.getid_categoriaStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_categoria is null)";
            }          
            if (conteudo.gettitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.titulo like '%"+conteudo.gettitulo().replaceAll("'","''") +"%')";
            }
            if(conteudo.getdescricao()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.descricao like '%"+conteudo.getdescricao().replaceAll("'","''") +"%')";
            }
           
            if (conteudo.getdata_cadastro()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.data_criacao='"+conteudo.getdata_cadastroStr("yyyy-MM-dd HH:mm:ss")+"')";
            }            
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;        
            SQL+="limit "+((posicao-1)*numero_registros)+","+numero_registros;
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
    
    public Vector buscar(String SQL)throws Excecao{
        Vector conteudos=new Vector();
        try{
            if(SQL==null)
                SQL = SELECt;                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       conteudoPOJO POJO = new  conteudoPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setID_multisegmentos(MinhaConexao.MostrarCampoInteger("id_multisegmentos"));                             
                       POJO.setID_menu(MinhaConexao.MostrarCampoInteger("id_menu"));                             
                       POJO.setid_categoria(MinhaConexao.MostrarCampoInteger("id_categoria"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       POJO.setDescricao(MinhaConexao.MostrarCampoStr("descricao"));                              
                       POJO.setconteudo(MinhaConexao.MostrarCampoStr("conteudo"));                              
                       POJO.setSRC(MinhaConexao.MostrarCampoStr("src")); 
                       POJO.setqrcode(MinhaConexao.MostrarCampoStr("qrcode")); 
                       POJO.setInicio(MinhaConexao.MostrarCampoDate("inicio")); 
                       conteudoBEAN conteudo_BEAN= new conteudoBEAN(POJO);
                       conteudos.add(conteudo_BEAN);
                  }                                                  
            }
            return conteudos;
        }    
        catch (Exception erro)
        {
            throw new Excecao(" erro ao buscar conteudo! ERRO: ");
        }
    }
    
     
    public Vector buscarPorsegmento(multisegmentosBEAN multisegmentos,int posicao,int numero_registros)throws Excecao{
    Vector registros=new Vector();
        try{
            String SQL=SELECt;
            String condicao="";
             if(multisegmentos.getIDStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_multisegmentos ="+multisegmentos.getIDStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_multisegmentos is null)";
            }
                 
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;  
            SQL+="limit "+((posicao-1)*numero_registros)+","+numero_registros;
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
    public Vector buscarPorMenuSegmentoCategoria(conteudoBEAN conteudo,int posicao,int numero_registros)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL=SELECt;
            String condicao="";
             if(conteudo.getid_menuStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_menu ="+conteudo.getid_menuStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_menu is null)";
            }
            if(conteudo.getid_multisegmentosStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_multisegmentos ="+conteudo.getid_multisegmentosStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_multisegmentos is null)";
            }
            if(conteudo.getid_categoriaStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_categoria ="+conteudo.getid_categoriaStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_categoria is null)";
            }  
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;  
            SQL+="limit "+((posicao-1)*numero_registros)+","+numero_registros;
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
    public int contarPorMenuSegmentoCategoria(conteudoBEAN conteudo,int numero_registros)throws Excecao{
        int contador=0;
        try{
            String SQL=SELECt;
            String condicao="";
             if(conteudo.getid_menuStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_menu ="+conteudo.getid_menuStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_menu is null)";
            }
            if(conteudo.getid_multisegmentosStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_multisegmentos ="+conteudo.getid_multisegmentosStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_multisegmentos is null)";
            }
            if(conteudo.getid_categoriaStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_categoria ="+conteudo.getid_categoriaStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_categoria is null)";
            }  
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;
            if(MinhaConexao.Busca(SQL)){
                 if(MinhaConexao.MoverProximo()){
                     contador=MinhaConexao.MostrarCampoInteger("contador").intValue();
                 }
             }
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar todos! ERRO:"+erro.getMessage());
        }
        contador=contador+numero_registros-(contador%numero_registros);
        try{contador=contador/numero_registros;}
        catch(Exception erroe){}   
        return contador;
    }
    public Vector buscarPorMenu(menuBEAN menu,int posicao,int numero_registros)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL=SELECt;
            String condicao="";
             if(menu.getIDStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_menu ="+menu.getIDStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_menu is null)";
            }
                 
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;  
            SQL+="limit "+((posicao-1)*numero_registros)+","+numero_registros;
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
    public Vector buscarPorCategoria(categoriaBEAN categoria,int posicao,int numero_registros)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL=SELECt;
            String condicao="";
             if(categoria.getIDStr()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_categoria ="+categoria.getIDStr()+")";
            }
            else{
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(conteudo.id_categoria is null)";
            }
                 
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;  
            SQL+="limit "+((posicao-1)*numero_registros)+","+numero_registros;
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
}