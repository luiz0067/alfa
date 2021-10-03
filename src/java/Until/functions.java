package Until;
import BD.Conexao;
import BD.DadosConexao;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import com.onbarcode.barcode.QRCode; 

public class functions {
    public static final String path_upload = (new java.io.File("/home/alfa/webapps/ROOT/upload/")).getAbsolutePath();
    public static final boolean online=true;
    private String file;
    private byte dataBytes[];
    private String contentType;
    private HttpServletRequest resquest;
    public functions(){
        this.file=null;
    }
    public static String getpath(){
        String acc=null;
        try{
            File arquivo = new File(".");
            acc=(arquivo).getAbsolutePath();
            if(acc.indexOf("/")==-1){
                arquivo = new File(acc+"/upload/");
            }
            else{
                arquivo = new File(acc+"\\upload\\");
            }
        }
        catch(Exception erro){
        }
        return acc;
    }
    public void setRequest(HttpServletRequest request){
        this.resquest=request;
        try{ 
            this.contentType = request.getContentType();
            if ((this.contentType != null) && (this.contentType.indexOf("multipart/form-data") >= 0)) {
                DataInputStream in = new DataInputStream(request.getInputStream());
                int formDataLength = request.getContentLength();
                this.dataBytes = new byte[formDataLength];
                int byteRead = 0;
                int totalBytesRead = 0;
                while (totalBytesRead < formDataLength) {
                    byteRead = in.read(this.dataBytes, totalBytesRead, formDataLength);
                    totalBytesRead += byteRead;
                }
                this.file = new String(this.dataBytes);
            }
        }
        catch(Exception erro){this.file=null;}
    }
    public  String getParameter(String Parameter){
        String result=null;
        try{
            if(this.file!=null){               
                int posicao=0; 
                Parameter="Content-Disposition: form-data; name=\""+Parameter+"\"\r\n\r\n";
                posicao=this.file.indexOf(Parameter,posicao); 
                if (posicao>=0){
                    posicao+=Parameter.length();
                    String acc=this.file.substring(posicao);
                    posicao=acc.indexOf("\r\n-----------------------------");         
                    acc=acc.substring(0,posicao);
                    result=acc;
                }
            }
            else
                result=this.resquest.getParameter(Parameter);
        }
        catch(Exception erro1){
            new Exception(erro1.getMessage());
        } 
        return result;
    }    
    public  String uploadJPG(String parameter,String path_upload)throws Exception{
        String saveFile=upload(parameter,path_upload);
        if ((saveFile!=null)&&(!saveFile.toLowerCase().endsWith(".jpg"))) {
            //saveFile=null;
            File arquivo = new java.io.File(path_upload+saveFile); 
            if (arquivo.exists()) { 
                arquivo.delete();                
            } 
            throw new Exception("Erro: Somente arquivos jpg");
        }
        return saveFile;
    }
    public  String uploadJPGRedimenciona(String parameter,String path_upload)throws Exception{
        String saveFile=null;
        try{
            saveFile=uploadJPG(parameter,path_upload);
            saveFile=redimencionaImagens(path_upload, saveFile);
        }catch(Exception erro){new Exception(erro.getMessage());}
        return saveFile;
    } 
    public  String upload(String parameter,String path_upload){
        String saveFile=null;
        try{
            if(this.file!=null){
                parameter="Content-Disposition: form-data; name=\""+parameter+"\"; filename=\"";
                int posicao=this.file.indexOf(parameter);
                if (posicao>=0){
                    posicao+=parameter.length();
                    saveFile = this.file.substring(posicao );
                    saveFile = saveFile.substring(0, saveFile.indexOf("\""));
                    parameter=parameter+saveFile+"\"\r\nContent-Type: image/jpeg\r\n\r\n";
                    posicao=this.file.indexOf(parameter);
                    if (posicao>=0){
                        posicao+=parameter.length();
                        String acc=this.file.substring(posicao);
                        int posicao_fim=acc.indexOf("\r\n-----------------------------");
                        acc=acc.substring(0,posicao_fim);
                        String saveFileCompleto = path_upload + saveFile;
                        File arquivo = new java.io.File(path_upload+saveFile);
                        Date tempo = new Date();
                        saveFile = (int)tempo.getTime()+"."+getExtension(saveFile);
                        if ((path_upload!=null)&&(path_upload.indexOf("\\")!=-1))
                            saveFileCompleto = path_upload +"\\"+ saveFile;
                        else
                            saveFileCompleto = path_upload +"/"+ saveFile;
                        arquivo = new java.io.File(saveFileCompleto);
                        
                        while (arquivo.exists()) { 
                            tempo = new Date();
                            saveFile = (int)tempo.getTime()+"."+getExtension(saveFile);
                            saveFileCompleto = path_upload + saveFile;
                            arquivo = new java.io.File(path_upload+saveFile);
                        } 
                        FileOutputStream fileOut = new FileOutputStream(saveFileCompleto);
                        fileOut.write(this.dataBytes, posicao, (posicao_fim));
                        fileOut.flush();
                        fileOut.close();
                    }
                    else
                        saveFile=null;
                }
            }
        }
        catch(Exception erro){ 
            saveFile=null;
            Exception exception = new Exception(erro.getMessage());
        }
        return saveFile;
    }
    public  String upload(String parameter,String path_upload,String file){
        String saveFile=null;
        try{
            if(this.file!=null){
                parameter="Content-Disposition: form-data; name=\""+parameter+"\"; filename=\"";
                int posicao=this.file.indexOf(parameter);
                if (posicao>=0){
                    posicao+=parameter.length();
                    saveFile = this.file.substring(posicao );
                    saveFile = saveFile.substring(0, saveFile.indexOf("\""));
                    parameter=parameter+saveFile+"\"\r\nContent-Type: image/jpeg\r\n\r\n";
                    posicao=this.file.indexOf(parameter);
                    if (posicao>=0){
                        posicao+=parameter.length();
                        String acc=this.file.substring(posicao);
                        int posicao_fim=acc.indexOf("\r\n-----------------------------");
                        acc=acc.substring(0,posicao_fim);
                        String saveFileCompleto = path_upload + saveFile;
                        File arquivo = new java.io.File(path_upload+file);
                        if (arquivo.exists()){
                            arquivo.delete();
                        }
                        FileOutputStream fileOut = new FileOutputStream(path_upload+file);
                        fileOut.write(this.dataBytes, posicao, (posicao_fim));
                        fileOut.flush();
                        fileOut.close();
                        saveFile=file;
                    }
                    else
                        saveFile=null;
                }
            }
        }
        catch(Exception erro){ 
            saveFile=null;
            new Exception(erro.getMessage());
        }
        return saveFile;
    }
    public static String getExtension(String file) {
      String nomeArq = removenull(file);
      String ext = nomeArq.substring(nomeArq.lastIndexOf('.') + 1);
      return ext.toLowerCase();
    }
    public static String getFile(String file){
      String nomeArq = removenull(file);
      String ext = nomeArq.substring(nomeArq.lastIndexOf('/') + 1);
      if (ext.length()==0)
          ext = nomeArq.substring(nomeArq.lastIndexOf('\\') + 1);
      return ext;
    }
    public static void redimensionarImagem(String origem,String destino, int altura, int largura,boolean margem) throws Exception {
        File fileImgOriginal = new File(origem);  
        File fileImgRedimensionada = new File(destino);
        if (!fileImgOriginal.exists()) {
            throw new Exception("A imagem que você quer redimensionar não existe");
        }
        if (!fileImgOriginal.canRead()){
            throw new Exception("A imagem que você quer redimensionar não pode ser lida");
        }
        Image imagem = ImageIO.read(fileImgOriginal);
        int altura_original = imagem.getHeight(null); 
        int largura_original = imagem.getWidth(null);
        int altura_redimencionado = 0; 
        int largura_redimencionado = 0;
        float razao_altura=(((float)altura_original)/altura);
        float razao_largura=(((float)largura_original)/largura); 
        int diferenca_altura=0;
        int diferenca_largura=0;
        if((razao_altura/razao_largura)>1){
            altura_redimencionado=altura;
            largura_redimencionado=Math.round((largura_original/razao_altura));
            diferenca_largura=Math.round(((largura-largura_redimencionado)/2));
        }
        else if ((razao_altura/razao_largura)<1){
            altura_redimencionado=Math.round((altura_original/razao_largura));
            largura_redimencionado=largura;
            diferenca_altura=Math.round(((altura-altura_redimencionado)/2));
        }
        else if ((razao_altura/razao_largura)==1){
            altura_redimencionado=altura;
            largura_redimencionado=largura;
        }
        if(!margem){
            diferenca_largura=0;
            diferenca_altura=0;
            largura=largura_redimencionado;
            altura=altura_redimencionado;
        }
        Image thumbs = imagem.getScaledInstance(largura_redimencionado, altura_redimencionado,BufferedImage.SCALE_SMOOTH);
        BufferedImage buffer = new BufferedImage(largura, altura,BufferedImage.TYPE_INT_RGB);
        buffer.createGraphics().drawImage(thumbs, diferenca_largura, diferenca_altura, null);
        ImageIO.write(buffer, getExtension(origem),fileImgRedimensionada);
        buffer.flush();
    }
    public static void deletaImagensRedimencionadas(String path_upload,String endereco){
        File arquivo = new java.io.File(path_upload+endereco); 
        String acc="";
        if ((path_upload!=null)&&(path_upload.indexOf("\\")!=-1))
            acc="\\";
        else
            acc="/";
        if (arquivo.exists()) {
            arquivo.delete();
        }
        arquivo = new java.io.File(path_upload+acc+"p_"+endereco); 
        if (arquivo.exists()) {
            arquivo.delete();
        }
        arquivo = new java.io.File(path_upload+acc+"m_"+endereco); 
        if (arquivo.exists()) {
            arquivo.delete();
        } 
        arquivo = new java.io.File(path_upload+acc+"g_"+endereco); 
        if (arquivo.exists()) {
            arquivo.delete();
        } 
        arquivo = new java.io.File(path_upload+acc+"h_"+endereco); 
        if (arquivo.exists()) {
            arquivo.delete();
        } 
    }
    public static String redimencionaImagens(String path_upload,String endereco){
        String novo_endereco = endereco;
        if(endereco!=null){
            Date tempo = new Date();
            novo_endereco="p_"+(int)tempo.getTime()+"."+getExtension(novo_endereco);
            java.io.File arquivo = new java.io.File(path_upload +novo_endereco);        
            while(arquivo.exists()) { 
                tempo = new Date();
                novo_endereco = "p_"+(int)tempo.getTime()+"."+getExtension(novo_endereco);
                arquivo = new java.io.File(path_upload +novo_endereco);        
            }
            String acc="";
            if ((path_upload!=null)&&(path_upload.indexOf("\\")!=-1))
                acc="\\";
            else
                acc="/";
            arquivo = new java.io.File(path_upload+acc+endereco);
            if (arquivo.exists()){
                try{
                    novo_endereco=novo_endereco.substring(2,novo_endereco.length());
                    redimensionarImagem(path_upload+acc+endereco,path_upload +acc+"p_"+novo_endereco,140,400,false);
                    redimensionarImagem(path_upload+acc+endereco,path_upload +acc+"m_"+novo_endereco,200,240,true);
                    redimensionarImagem(path_upload+acc+endereco,path_upload +acc+"g_"+novo_endereco,480,640,true);
                    redimensionarImagem(path_upload+acc+endereco,path_upload +acc+"h_"+novo_endereco,600,800,false);
                    arquivo.delete();
                }
                catch(Exception erro){
                    novo_endereco=null;
                }
            }
        }
        return novo_endereco;
    }
    public static DadosConexao CreateDataConection(){
        String login="root";
        String senha="root";
        String banco="alfa";
        String servidor="localhost"
                + "";
        if (online){
            login="inoveticao";
            senha="midiamix";
            banco="inoveticao";
            servidor="mysql.inoveticao.com.br";
        }
        DadosConexao dados=new DadosConexao(login,senha,servidor,DadosConexao.MYSQL,banco);
        return dados;  
    }
    public static String DateToString(Date data,String formato){
        try{
            SimpleDateFormat formatador = new SimpleDateFormat(formato);
            return  formatador.format(data);
        }
        catch(Exception erro){
            return  null;
        }
    }
    public static String removenull(String dado){
        return (dado==null)?"":dado;
    }
    public static Date StringToDate(String data,String formato) throws Exception {
        try{
            SimpleDateFormat formatador = new SimpleDateFormat(formato); 
            return  formatador.parse(data);
        }
        catch(Exception erro){
            throw new Exception("Data Inválida");
        }
    }
    public static Date StringToDate(String data) throws Exception {
        try{
           return  StringToDate(data,"dd/MM/yyyy");
        }
        catch(Exception erro){
            try{
               return  StringToDate(data,"yyyy-MM-dd");
            }
            catch(Exception erro2){
                try{
                    return  StringToDate(data,"dd-MM-yy");
                }
                catch(Exception erro3){
                    throw new Exception("Data Inválida");
                }                
            }
        }
    }
    public static boolean equals(Object ob1,Object ob2){
        boolean resultado=false;
        if ((ob1!=null)&&(ob2!=null)){
            resultado=ob1.equals(ob2);
        }
        else if ((ob1==null)&&(ob2==null)){
            resultado=true;
        }
        return resultado;
    }
    public static Date PrimeiroDiaDaSemana(Date data)  
    {  
        GregorianCalendar calendar = new GregorianCalendar();  
        calendar.setTime(data);  
        calendar.set(calendar.DAY_OF_WEEK, calendar.MONDAY);  
        return calendar.getTime();  
    }  
    public static Date UltimoDiaDaSemana(Date data)  
    {  
        GregorianCalendar calendar = new GregorianCalendar();  
        calendar.setTime(data);  
        calendar.set(calendar.DAY_OF_WEEK, calendar.SUNDAY);  
        return calendar.getTime();  
    } 
    public static Date PrimeiroDiaDoMes(Date data)  
    {  
        GregorianCalendar calendar = new GregorianCalendar();  
        calendar.setTime(data);  
        int dia =calendar.getMinimum(calendar.DAY_OF_MONTH); 
        String texto = DateToString(data,"/MM/yyyy");
        String StrDia = String.format("%02d", dia);
        texto=StrDia+texto;
        try{
            data=StringToDate(texto,"dd/MM/yyyy");
        }
        catch(Exception erro){
            data=null;
        }
        return data;
    }  
    public static Date UltimoDiaDoMes(Date data)  
    {  
        GregorianCalendar calendar = new GregorianCalendar();  
        calendar.setTime(data);  
        int dia = calendar.getMaximum(calendar.DAY_OF_MONTH); 
        String texto = DateToString(data,"/MM/yyyy");
        String StrDia = String.format("%02d", dia);
        texto=StrDia+texto;
        try{
            data=StringToDate(texto,"dd/MM/yyyy");
        }
        catch(Exception erro){
            data=null;
        }
        return data;
    }  
    public static Date Ontem(){
        GregorianCalendar calendar = new GregorianCalendar(); 
        calendar.setTime(new Date());
        calendar.add(calendar.DAY_OF_WEEK, -1); 
        return calendar.getTime();
    }
    public static int getLarguraImagem(String origem) throws Exception {
        File fileImgOriginal = new File(origem);  
        if (!fileImgOriginal.exists()) {
            throw new Exception("A imagem que você quer redimensionar não existe");
        }
        if (!fileImgOriginal.canRead()){
            throw new Exception("A imagem que você quer redimensionar não pode ser lida");
        }
        Image imagem = ImageIO.read(fileImgOriginal);
        int largura_original = imagem.getWidth(null);
        return largura_original;
    }
    public static String pesquisarDiaSemana(Date data)  
    {  
        GregorianCalendar gc = new GregorianCalendar();  
        gc.setTime(data);  
        int _diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
        String diaSemana = null;  

        switch (_diaSemana)  
        {  

            case 1:  
            {  
              diaSemana = "Domingo";  
              break;  
            }  
            case 2:  
            {  
              diaSemana = "Segunda-Feira";  
              break;  
            }  
            case 3:  
            {  
              diaSemana = "Terça-Feira";  
              break;  
            }  
            case 4:  
            {  
              diaSemana = "Quarta-Feira";  
              break;  
            }  
            case 5:  
            {  
              diaSemana = "Quinta-Feira";  
              break;  
            }  
            case 6:  
            {  
              diaSemana = "Sexta-Feira";  
              break;  
            }  
            case 7:  
            {  
              diaSemana = "Sábado";  
              break;  
            } 
      }  
      return diaSemana;
    } 
    
    public static String LinkToImageQrCode(String link,String pasta){
        String novo_endereco=null;
        try{
            Date tempo = new Date();
            novo_endereco=(int)tempo.getTime()+".gif";
            String barra="";
            if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                barra="\\";
            else
                barra="/";
            File arquivo = new java.io.File(path_upload+barra+pasta+barra+novo_endereco);
            while(arquivo.exists()) { 
                tempo = new Date();
                novo_endereco=(int)tempo.getTime()+".gif";
                arquivo = new java.io.File(path_upload+barra+pasta+barra+novo_endereco);       
            }
            QRCode barcode = new QRCode(); 
            barcode.setData("Create-QR-Code-in-Java"); 
            barcode.drawBarcode("Java Graphics2D object"); 
            barcode.drawBarcode(path_upload+barra+pasta+barra+novo_endereco); 
        }
        catch(Exception erro){}
        return novo_endereco;
    }
    
}