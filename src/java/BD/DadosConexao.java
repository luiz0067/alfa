package BD;
import java.sql.*;
import javax.swing.JOptionPane;
        
public class DadosConexao 
{
    public 	 static final String MYSQL="MYSQL";
    public 	 static final String INTERBASE="INTERBASE";
    public 	 static final String ORACLE="ORACLE";
    public 	 static final String ACESS="ACESS";
    public 	 static final String SQL_SERVER="SQL_SERVER";
    public 	 static final String POSTGREE="POSTGREE";

    private  static String Usuario;
    private  static String Senha;
    private  static String Servidor;
    private  static String Banco;
    private  static String SGBD;
    
    public DadosConexao(){
	    Usuario="";
	    Senha="";
	    Servidor="";
	    Banco="";
	    SGBD="";
    }

    public DadosConexao(String Usuario,String Senha,String Servidor,String SGBD,String banco){
		this.setarUsuario(Usuario);
		this.setarSenha(Senha);
		this.setarServidor(Servidor);
		this.setarBanco(banco);
		this.setarSGBD(SGBD);
    }

	public void setarUsuario(String Usuario){
		this.Usuario=Usuario;
	}

	public void setarSenha(String Senha){
		this.Senha=Senha;
	}

	public void setarServidor(String Servidor){
		this.Servidor=Servidor;
	}

	public void setarBanco(String Banco){
		this.Banco=Banco;
	}
    
	public void setarSGBD(String SGBD){
		this.SGBD=SGBD;
	}


	public String mostrarUsuario(){
		return this.Usuario;
	}

	public String mostrarSenha(){
		return this.Senha;
	}

	public String mostrarServidor(){
		return this.Servidor;
	}

	public String mostrarBanco(){
		return this.Banco;
	}
    
	public String mostrarSGBD(){
		return this.SGBD=SGBD;
	}
}
