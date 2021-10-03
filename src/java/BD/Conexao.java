package BD;
import excecoes.Excecao;
import java.sql.*;
import javax.swing.JOptionPane;
        
public class Conexao 
{
    
    private  static Connection con;
    private  static Statement stm;
    private  static ResultSet rs=null;
    private  static DadosConexao dados;
    private  static boolean concetado=false;

    public static String sqlProtection(Object descricao) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Conexao(DadosConexao dados){
    	this.dados=dados;
        con=null;
        stm=null;
    }

    public  void Abrir() throws Exception  
    {
        try
        {
            String driver="", url="";
            if (dados.mostrarSGBD().equals(DadosConexao.MYSQL)){
              	url = "jdbc:mysql://"+dados.mostrarServidor()+"/"+dados.mostrarBanco();
                driver = "com.mysql.jdbc.Driver";
                Class.forName(driver);
                con=DriverManager.getConnection(url,dados.mostrarUsuario(),dados.mostrarSenha());
                 
            }           
            stm=con.createStatement();
            concetado=true;
           
        }
        catch (Exception erro){
            concetado=false;
            //return false;
            throw new Exception(erro.getMessage());
        }
    }
    public static String sqlProtection(String dado){
        try{
            byte[] acc=dado.getBytes();
            dado=new String(acc,"UTF-8");
        }
        catch(Exception erro){
        }
        return (dado==null)?"null":"'"+dado.replaceAll("'", "''")+"'";
    }
    public static boolean isConectado(){
        return concetado;
    }

    public static boolean Fechar() 
    {
        if (concetado){
            try 
            {
                con.close();
                concetado=false;
                return true;
            }
            catch(SQLException sqle) 
            {
                return false;
            }
            catch(Exception sqle) 
            {
                return false;
            }
        }
        else
            return false;
    }
    
    public  boolean Busca(String sql) {
        boolean resultado=false;
        rs=null;
        try{
            if (con==null){
                Abrir();           
            }
            rs=stm.executeQuery(sql);
            resultado = true;
        }
        catch (Exception e){
        }
        return resultado;
    }
    
    public  boolean Executa(String sql){
        boolean resultado=false;
        
        try{
            if (con==null){
                Abrir();
            }
            resultado=stm.execute(sql);
            resultado=true;
        }
        catch (Exception e){
        }
        return resultado;
    }
    
    public String MostrarCampoStr(String campo){
        String resultado=null;
        try{
            byte[] acc=rs.getString(campo).getBytes();
            resultado=new String(acc,"UTF-8");
            resultado=new String(acc);
        }
        catch(Exception e){
        }
        return resultado;
    }
    public String MostrarCampoStr(int campo){
        String resultado=null;
        try{
            byte[] acc=rs.getString(campo).getBytes();
            resultado=new String(acc,"UTF-8");
            //resultado=new String(acc);
        }
        catch(Exception e){
        }
        return resultado;
    }
    public Integer MostrarCampoInteger(String campo){
        Integer resultado=null;
        try{
            resultado=new Integer(rs.getInt(campo));
        }
        catch(Exception e){
        }
        return resultado;
    }
    public Boolean MostrarCampoBoolean(String campo){
        Boolean resultado=null;
        try{
            resultado=(rs.getBoolean(campo));
        }
        catch(Exception e){
        }
        return resultado;
    }    
    public Date MostrarCampoDate(String campo){
        Date resultado=null;
        try{            
            resultado=rs.getDate(campo);
        }
        catch(Exception e){
        }
        return resultado;
    }
    public int Contar(){
        int resultado=0;
        try{      
            rs.last();
            resultado=rs.getRow();
        }
        catch(Exception e){
        }
        return resultado;
    }
    public boolean MoverProximo(){
        try{
            return rs.next();
        }
        catch(Exception e){
            return false;
        }
    }
    
    public boolean MoverInicio(){
        try{
            return rs.first();
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean MoverFim(){
        try{
            return rs.last();
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean MoverVoltar(){
        try{
            return rs.previous();
        }
        catch(Exception e){
            return false;
        }
    }

}
