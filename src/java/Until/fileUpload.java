package Until;


import BD.Conexao;
import BD.DadosConexao;
import com.onbarcode.barcode.QRCode;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Document;

public class fileUpload {
    public static final String path_upload = (new java.io.File("/home/alfa/webapps/ROOT/upload/")).getAbsolutePath();
    public static final boolean online=true;
    private String file;
    private byte dataBytes[];
    private String contentType;
    private HttpServletRequest resquest;
    public fileUpload(){
        this.file=null;
    }
    public static String getpath(){
        String acumulador=null;
        try{
            File arquivo = new File(".");
            acumulador=(arquivo).getAbsolutePath();
            if(acumulador.indexOf("/")==-1){
                arquivo = new File(acumulador+"/upload/");
            }
            else{
                arquivo = new File(acumulador+"\\upload\\");
            }
        }
        catch(Exception erro){
        }
        return acumulador;
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
                    String acumulador=this.file.substring(posicao);
                    posicao=acumulador.indexOf("\r\n-----------------------------");         
                    acumulador=acumulador.substring(0,posicao);
                    result=acumulador;
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
    public static boolean extensionsValidate(String Extensions[],String Extension){
        boolean result=false;
        
        if(Extensions!=null){
            for(int i=0;i<Extensions.length;i++){
                if((Extensions[i]!=null)&&(Extensions[i].toLowerCase().equals(Extension.toLowerCase()))){
                    result=true;
                }
            }
        }
        else if((Extension!=null)&&(Extension.length()!=0)){
            if(
                !(Extension.toLowerCase().equals("php"))
                &&
                !(Extension.toLowerCase().equals("jsp"))
                &&
                !(Extension.toLowerCase().equals("asp"))
                &&
                !(Extension.toLowerCase().equals("aspx"))
                &&
                !(Extension.toLowerCase().equals("cgi"))
            )
                result=true;
        }
        return result;
    }
    public  String upload(String parameter,String pasta,String Extensions[],long size){
        String nome_origem=null;
        String novo_nome=null;
        try{
            String barra="";
            if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                barra="\\";
            else
                barra="/";
            if(this.file!=null){
                parameter="Content-Disposition: form-data; name=\""+parameter+"\"; filename=\"";
                int posicao=this.file.indexOf(parameter);
                if (posicao>=0){
                    posicao+=parameter.length();
                    nome_origem = this.file.substring(posicao);
                    nome_origem = nome_origem.substring(0, nome_origem.indexOf("\""));
                    String Extension_file=getExtension(nome_origem);
                    if(extensionsValidate(Extensions,Extension_file)){
                        String ContentType=ExtensionToContentType(Extension_file);
                        parameter=parameter+nome_origem+"\"\r\nContent-Type: "+ContentType+"\r\n\r\n";
                        posicao=this.file.indexOf(parameter);
                        if (posicao>=0){
                            posicao+=parameter.length();
                            String acumulador=this.file.substring(posicao);
                            int total_bytes=acumulador.indexOf("\r\n-----------------------------");
                            if(total_bytes<=size){
                                //String conteudo=acumulador.substring(0,total_bytes);
                                Date tempo = new Date();
                                novo_nome=(int)tempo.getTime()+"."+getExtension(nome_origem);
                                File arquivo = new java.io.File(path_upload+barra+pasta+barra+nome_origem);
                                while (arquivo.exists()){
                                    tempo = new Date();
                                    novo_nome = (int)tempo.getTime()+"."+getExtension(nome_origem);
                                    arquivo = new java.io.File(path_upload+barra+pasta+barra+nome_origem);
                                }
                                FileOutputStream fileOut = new FileOutputStream(path_upload+barra+pasta+barra+novo_nome);
                                fileOut.write(this.dataBytes,posicao,(total_bytes));
                                fileOut.flush();
                                fileOut.close();
                            }
                        }
                        else
                            nome_origem=null;
                    }
                }
            }
        }
        catch(Exception erro){ 
            nome_origem=null;
            Exception exception = new Exception(erro.getMessage());
        }
        return novo_nome;
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
        try{
            File fileImgOriginal = new File(origem);  
            File fileImgRedimensionada = new File(destino);
            if (!fileImgOriginal.exists()) {
                throw new Exception("A imagem que você quer redimensionar não existe");
            }
            if (!fileImgOriginal.canRead()){
                throw new Exception("A imagem que você quer redimensionar não pode ser lida");
            }
            Image image_lida=ImageIO.read(fileImgOriginal);
            int altura_original = image_lida.getHeight(null); 
            int largura_original = image_lida.getWidth(null);
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

            if(
                (extensionsValidate(new String[]{"gif"},getExtension(origem)))
                    &&
                (extensionsValidate(new String[]{"gif"},getExtension(destino)))
            ){
                /*
                ImageReader gifOrigem = new GIFImageReader(new GIFImageReaderSpi());
                gifOrigem.setInput(ImageIO.createImageInputStream(fileImgOriginal));
                ArrayList<BufferedImage>quadros=getFrames(fileImgOriginal);
                ImageWriter wr=null;
                for(int i = 0; i < gifOrigem.getNumImages(true); i++){
                    //BufferedImage quadro = gifOrigem.getRawImageType(i).createBufferedImage(gifOrigem.getWidth(i), gifOrigem.getHeight(i));
                    BufferedImage quadro_origem = quadros.get(i);
                    
                    ImageTypeSpecifier spec = new ImageTypeSpecifier(quadro);
                    wr = ImageIO.getImageWriters(spec, "GIF").next();
                    wr.setOutput(image_lida);
                    ImageWriteParam param = wr.getDefaultWriteParam();
                    IIOMetadata meta = wr.getDefaultImageMetadata(spec, param);
                    wr.prepareWriteSequence(meta);
                    Image imagemDestino = image_lida.getScaledInstance(largura_redimencionado, altura_redimencionado,BufferedImage.SCALE_SMOOTH);
                    
                    BufferedImage quadro = new BufferedImage(largura, altura,BufferedImage.TYPE_INT_RGB);
                    quadro.createGraphics().drawImage(imagemDestino,diferenca_largura,diferenca_altura,null);
                    quadro= (BufferedImage) imagemDestino;
                    wr.writeToSequence(new IIOImage(quadro_origem,null,null),param);
                    ImageIO.write(quadro,getExtension(origem),fileImgRedimensionada);

                }
                */
                //wr.endWriteSequence();
                //imagemDestino.flush();
                //image_lida.flush();
                //quadro.flush();
            }
            else{
                Image imagemDestino = image_lida.getScaledInstance(largura_redimencionado, altura_redimencionado,BufferedImage.SCALE_SMOOTH);
                BufferedImage quadro = new BufferedImage(largura, altura,BufferedImage.TYPE_INT_RGB);
                quadro.createGraphics().drawImage(imagemDestino,diferenca_largura,diferenca_altura,null);
                ImageIO.write(quadro,getExtension(origem),fileImgRedimensionada);
                imagemDestino.flush();
                image_lida.flush();
                quadro.flush();
            }
        }
        catch(Exception erro){
        }
    }
    
   

    
   
    
    /*public static void saveAnimatedGIF(OutputStream out, List<GifFrame> frames, int loopCount) throws Exception
   {
      ImageWriter iw = ImageIO.getImageWritersByFormatName("gif").next();

      ImageOutputStream ios = ImageIO.createImageOutputStream(out);
      iw.setOutput(ios);
      iw.prepareWriteSequence(null);

      int p = 0;
      for (GifFrame frame : frames)
      {
         ImageWriteParam iwp = iw.getDefaultWriteParam();
         IIOMetadata metadata = iw.getDefaultImageMetadata(new ImageTypeSpecifier(frame.img), iwp);
         ImageUtil.configureGIFFrame(metadata, String.valueOf(frame.delay / 10L), p++, frame.disposalMethod, loopCount);
         IIOImage ii = new IIOImage(frame.img, null, metadata);
         iw.writeToSequence(ii, null);
      }

      iw.endWriteSequence();
      ios.close();
   }*/

    public void loadAnimation(String origem,String destino, int altura, int largura,boolean margem) {
        try {
            File fileImgOriginal = new File(origem);//carega arquivo
            File fileImgRedimensionada = new File(destino);            
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(new FileImageInputStream(fileImgOriginal));
            int lastImageIndex = 0;
            int totalImages = reader.getNumImages(true);
            boolean readingImage = true;
            int posicao_quadro_atual = 0;
            while(readingImage) {
                BufferedImage image = reader.read(posicao_quadro_atual);
                {
                    ImageTypeSpecifier spec = new ImageTypeSpecifier(image);
                    ImageWriter wr = ImageIO.getImageWriters(spec, "GIF").next();
                    
                    wr.setOutput(ImageIO.createImageOutputStream(fileImgRedimensionada));
                    ImageWriteParam param = wr.getDefaultWriteParam();
                    IIOMetadata meta = wr.getDefaultImageMetadata(spec, param);
                    wr.prepareWriteSequence(meta);
                    wr.writeToSequence(new IIOImage(image, null, null), param);
                }
                posicao_quadro_atual++;
                image.flush();
                if(posicao_quadro_atual == reader.getNumImages(true)){
                    readingImage = false;
                    reader.reset();
                    reader.dispose();
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String redimensionarImagem(String pasta,String imagem,String[]prefixos,Dimension[] dimensions){
        String novo_imagem = imagem;
        if(
                ((prefixos!=null)&&(prefixos.length!=0))
                &&
                ((dimensions!=null)&&(dimensions.length!=0))
        ){
            String barra="";
            if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                barra="\\";
            else
                barra="/";
            String Extension="jpg";
            extensionsValidate(new String[]{"png","jpg","gif"},getExtension(novo_imagem));
            Extension=getExtension(novo_imagem);
            if(imagem!=null){
                Date tempo = new Date();
                novo_imagem=(int)tempo.getTime()+"."+Extension;
                java.io.File arquivo = new java.io.File(functions.path_upload+barra+pasta+barra+prefixos[0]+novo_imagem);        
                while(arquivo.exists()) { 
                    tempo = new Date();
                    novo_imagem=(int)tempo.getTime()+"."+Extension;
                    arquivo = new java.io.File(functions.path_upload+barra+pasta+barra+prefixos[0]+novo_imagem);        
                }
                
                arquivo = new java.io.File(path_upload+barra+pasta+barra+imagem);
                if (arquivo.exists()){
                    try{                        
                        for(
                                int i=0;
                                i<((prefixos.length>dimensions.length)?dimensions.length:prefixos.length);
                                i++
                        )
                        {
                            String origem=path_upload+barra+pasta+barra+imagem;
                            String destino=path_upload+barra+pasta+barra+prefixos[i]+novo_imagem;
                            int largura=(int)dimensions[i].getWidth();
                            int altura=(int)dimensions[i].getHeight();
                            redimensionarImagem(origem, destino, altura, largura, false);
                        }
                        arquivo.delete();
                    }
                    catch(Exception erro){
                        novo_imagem=null;
                    }
                }
            }
        }
        return novo_imagem;
    }
    public static void deletaImagensRedimencionadas(String path_upload,String endereco){
        File arquivo = new java.io.File(path_upload+endereco); 
        String acumulador="";
        if ((path_upload!=null)&&(path_upload.indexOf("\\")!=-1))
            acumulador="\\";
        else
            acumulador="/";
        if (arquivo.exists()) {
            arquivo.delete();
        }
        arquivo = new java.io.File(path_upload+acumulador+"p_"+endereco); 
        if (arquivo.exists()) {
            arquivo.delete();
        }
        arquivo = new java.io.File(path_upload+acumulador+"m_"+endereco); 
        if (arquivo.exists()) {
            arquivo.delete();
        } 
        arquivo = new java.io.File(path_upload+acumulador+"g_"+endereco); 
        if (arquivo.exists()) {
            arquivo.delete();
        } 
        arquivo = new java.io.File(path_upload+acumulador+"h_"+endereco); 
        if (arquivo.exists()) {
            arquivo.delete();
        } 
    }
    public static String removenull(String dado){
        return (dado==null)?"":dado;
    }
    public static String ExtensionToContentType(String extension){
        String resultado="";
        String [][] ExtensionContentType=new String [][]
        {
            {"3dm","x-world/x-3dmf"},
            {"3dmf","x-world/x-3dmf"},
            {"a","application/octet-stream"},
            {"aab","application/x-authorware-bin"},
            {"aam","application/x-authorware-map"},
            {"aas","application/x-authorware-seg"},
            {"abc","text/vnd.abc"},
            {"acgi","text/html"},
            {"afl","video/animaflex"},
            {"ai","application/postscript"},
            {"aif","audio/aiff"},
            {"aif","audio/x-aiff"},
            {"aifc","audio/aiff"},
            {"aifc","audio/x-aiff"},
            {"aiff","audio/aiff"},
            {"aiff","audio/x-aiff"},
            {"aim","application/x-aim"},
            {"aip","text/x-audiosoft-intra"},
            {"ani","application/x-navi-animation"},
            {"aos","application/x-nokia-9000-communicator-add-on-software"},
            {"aps","application/mime"},
            {"arc","application/octet-stream"},
            {"arj","application/arj"},
            {"arj","application/octet-stream"},
            {"art","image/x-jg"},
            {"asf","video/x-ms-asf"},
            {"asm","text/x-asm"},
            {"asp","text/asp"},
            {"asx","application/x-mplayer2"},
            {"asx","video/x-ms-asf"},
            {"asx","video/x-ms-asf-plugin"},
            {"au","audio/basic"},
            {"au","audio/x-au"},
            {"avi","application/x-troff-msvideo"},
            {"avi","video/avi"},
            {"avi","video/msvideo"},
            {"avi","video/x-msvideo"},
            {"avs","video/avs-video"},
            {"bcpio","application/x-bcpio"},
            {"bin","application/mac-binary"},
            {"bin","application/macbinary"},
            {"bin","application/octet-stream"},
            {"bin","application/x-binary"},
            {"bin","application/x-macbinary"},
            {"bm","image/bmp"},
            {"bmp","image/bmp"},
            {"bmp","image/x-windows-bmp"},
            {"boo","application/book"},
            {"book","application/book"},
            {"boz","application/x-bzip2"},
            {"bsh","application/x-bsh"},
            {"bz","application/x-bzip"},
            {"bz2","application/x-bzip2"},
            {"c","text/plain"},
            {"c","text/x-c"},
            {"c++","text/plain"},
            {"cat","application/vnd.ms-pki.seccat"},
            {"cc","text/plain"},
            {"cc","text/x-c"},
            {"ccad","application/clariscad"},
            {"cco","application/x-cocoa"},
            {"cdf","application/cdf"},
            {"cdf","application/x-cdf"},
            {"cdf","application/x-netcdf"},
            {"cer","application/pkix-cert"},
            {"cer","application/x-x509-ca-cert"},
            {"cha","application/x-chat"},
            {"chat","application/x-chat"},
            {"class","application/java"},
            {"class","application/java-byte-code"},
            {"class","application/x-java-class"},
            {"com","application/octet-stream"},
            {"com","text/plain"},
            {"conf","text/plain"},
            {"cpio","application/x-cpio"},
            {"cpp","text/x-c"},
            {"cpt","application/mac-compactpro"},
            {"cpt","application/x-compactpro"},
            {"cpt","application/x-cpt"},
            {"crl","application/pkcs-crl"},
            {"crl","application/pkix-crl"},
            {"crt","application/pkix-cert"},
            {"crt","application/x-x509-ca-cert"},
            {"crt","application/x-x509-user-cert"},
            {"csh","application/x-csh"},
            {"csh","text/x-script.csh"},
            {"css","application/x-pointplus"},
            {"css","text/css"},
            {"cxx","text/plain"},
            {"dcr","application/x-director"},
            {"deepv","application/x-deepv"},
            {"def","text/plain"},
            {"der","application/x-x509-ca-cert"},
            {"dif","video/x-dv"},
            {"dir","application/x-director"},
            {"dl","video/dl"},
            {"dl","video/x-dl"},
            {"doc","application/msword"},
            {"dot","application/msword"},
            {"dp","application/commonground"},
            {"drw","application/drafting"},
            {"dump","application/octet-stream"},
            {"dv","video/x-dv"},
            {"dvi","application/x-dvi"},
            {"dwf","drawing/x-dwf (old)"},
            {"dwf","model/vnd.dwf"},
            {"dwg","application/acad"},
            {"dwg","image/vnd.dwg"},
            {"dwg","image/x-dwg"},
            {"dxf","application/dxf"},
            {"dxf","image/vnd.dwg"},
            {"dxf","image/x-dwg"},
            {"dxr","application/x-director"},
            {"el","text/x-script.elisp"},
            {"elc","application/x-bytecode.elisp (compiled elisp)"},
            {"elc","application/x-elc"},
            {"env","application/x-envoy"},
            {"eps","application/postscript"},
            {"es","application/x-esrehber"},
            {"etx","text/x-setext"},
            {"evy","application/envoy"},
            {"evy","application/x-envoy"},
            {"exe","application/octet-stream"},
            {"f","text/plain"},
            {"f","text/x-fortran"},
            {"f77","text/x-fortran"},
            {"f90","text/plain"},
            {"f90","text/x-fortran"},
            {"fdf","application/vnd.fdf"},
            {"fif","application/fractals"},
            {"fif","image/fif"},
            {"fli","video/fli"},
            {"fli","video/x-fli"},
            {"flo","image/florian"},
            {"flx","text/vnd.fmi.flexstor"},
            {"fmf","video/x-atomic3d-feature"},
            {"for","text/plain"},
            {"for","text/x-fortran"},
            {"fpx","image/vnd.fpx"},
            {"fpx","image/vnd.net-fpx"},
            {"frl","application/freeloader"},
            {"funk","audio/make"},
            {"g","text/plain"},
            {"g3","image/g3fax"},
            {"gif","image/gif"},
            {"gl","video/gl"},
            {"gl","video/x-gl"},
            {"gsd","audio/x-gsm"},
            {"gsm","audio/x-gsm"},
            {"gsp","application/x-gsp"},
            {"gss","application/x-gss"},
            {"gtar","application/x-gtar"},
            {"gz","application/x-compressed"},
            {"gz","application/x-gzip"},
            {"gzip","application/x-gzip"},
            {"gzip","multipart/x-gzip"},
            {"h","text/plain"},
            {"h","text/x-h"},
            {"hdf","application/x-hdf"},
            {"help","application/x-helpfile"},
            {"hgl","application/vnd.hp-hpgl"},
            {"hh","text/plain"},
            {"hh","text/x-h"},
            {"hlb","text/x-script"},
            {"hlp","application/hlp"},
            {"hlp","application/x-helpfile"},
            {"hlp","application/x-winhelp"},
            {"hpg","application/vnd.hp-hpgl"},
            {"hpgl","application/vnd.hp-hpgl"},
            {"hqx","application/binhex"},
            {"hqx","application/binhex4"},
            {"hqx","application/mac-binhex"},
            {"hqx","application/mac-binhex40"},
            {"hqx","application/x-binhex40"},
            {"hqx","application/x-mac-binhex40"},
            {"hta","application/hta"},
            {"htc","text/x-component"},
            {"htm","text/html"},
            {"html","text/html"},
            {"htmls","text/html"},
            {"htt","text/webviewhtml"},
            {"htx","text/html"},
            {"ice","x-conference/x-cooltalk"},
            {"ico","image/x-icon"},
            {"idc","text/plain"},
            {"ief","image/ief"},
            {"iefs","image/ief"},
            {"iges","application/iges"},
            {"iges","model/iges"},
            {"igs","application/iges"},
            {"igs","model/iges"},
            {"ima","application/x-ima"},
            {"imap","application/x-httpd-imap"},
            {"inf","application/inf"},
            {"ins","application/x-internett-signup"},
            {"ip","application/x-ip2"},
            {"isu","video/x-isvideo"},
            {"it","audio/it"},
            {"iv","application/x-inventor"},
            {"ivr","i-world/i-vrml"},
            {"ivy","application/x-livescreen"},
            {"jam","audio/x-jam"},
            {"jav","text/plain"},
            {"jav","text/x-java-source"},
            {"java","text/plain"},
            {"java","text/x-java-source"},
            {"jcm","application/x-java-commerce"},
            {"jfif","image/jpeg"},
            {"jfif","image/pjpeg"},
            {"jfif-tbnl","image/jpeg"},
            {"jpe","image/jpeg"},
            {"jpeg","image/jpeg"},
            {"jpg","image/jpeg"},
            {"jps","image/x-jps"},
            {"js","application/x-javascript"},
            {"jut","image/jutvision"},
            {"kar","audio/midi"},
            {"kar","music/x-karaoke"},
            {"ksh","application/x-ksh"},
            {"ksh","text/x-script.ksh"},
            {"la","audio/nspaudio"},
            {"la","audio/x-nspaudio"},
            {"lam","audio/x-liveaudio"},
            {"latex","application/x-latex"},
            {"lha","application/lha"},
            {"lha","application/octet-stream"},
            {"lha","application/x-lha"},
            {"lhx","application/octet-stream"},
            {"list","text/plain"},
            {"lma","audio/nspaudio"},
            {"lma","audio/x-nspaudio"},
            {"log","text/plain"},
            {"lsp","application/x-lisp"},
            {"lsp","text/x-script.lisp"},
            {"lst","text/plain"},
            {"lsx","text/x-la-asf"},
            {"ltx","application/x-latex"},
            {"lzh","application/octet-stream"},
            {"lzh","application/x-lzh"},
            {"lzx","application/lzx"},
            {"lzx","application/octet-stream"},
            {"lzx","application/x-lzx"},
            {"m","text/plain"},
            {"m","text/x-m"},
            {"m1v","video/mpeg"},
            {"m2a","audio/mpeg"},
            {"m2v","video/mpeg"},
            {"m3u","audio/x-mpequrl"},
            {"man","application/x-troff-man"},
            {"map","application/x-navimap"},
            {"mar","text/plain"},
            {"mbd","application/mbedlet"},
            {"mc$","application/x-magic-cap-package-1.0"},
            {"mcd","application/mcad"},
            {"mcd","application/x-mathcad"},
            {"mcf","image/vasa"},
            {"mcf","text/mcf"},
            {"mcp","application/netmc"},
            {"me","application/x-troff-me"},
            {"mht","message/rfc822"},
            {"mhtml","message/rfc822"},
            {"mid","application/x-midi"},
            {"mid","audio/midi"},
            {"mid","audio/x-mid"},
            {"mid","audio/x-midi"},
            {"mid","music/crescendo"},
            {"mid","x-music/x-midi"},
            {"midi","application/x-midi"},
            {"midi","audio/midi"},
            {"midi","audio/x-mid"},
            {"midi","audio/x-midi"},
            {"midi","music/crescendo"},
            {"midi","x-music/x-midi"},
            {"mif","application/x-frame"},
            {"mif","application/x-mif"},
            {"mime","message/rfc822"},
            {"mime","www/mime"},
            {"mjf","audio/x-vnd.audioexplosion.mjuicemediafile"},
            {"mjpg","video/x-motion-jpeg"},
            {"mm","application/base64"},
            {"mm","application/x-meme"},
            {"mme","application/base64"},
            {"mod","audio/mod"},
            {"mod","audio/x-mod"},
            {"moov","video/quicktime"},
            {"mov","video/quicktime"},
            {"movie","video/x-sgi-movie"},
            {"mp2","audio/mpeg"},
            {"mp2","audio/x-mpeg"},
            {"mp2","video/mpeg"},
            {"mp2","video/x-mpeg"},
            {"mp2","video/x-mpeq2a"},
            {"mp3","audio/mpeg3"},
            {"mp3","audio/x-mpeg-3"},
            {"mp3","video/mpeg"},
            {"mp3","video/x-mpeg"},
            {"mpa","audio/mpeg"},
            {"mpa","video/mpeg"},
            {"mpc","application/x-project"},
            {"mpe","video/mpeg"},
            {"mpeg","video/mpeg"},
            {"mpg","audio/mpeg"},
            {"mpg","video/mpeg"},
            {"mpga","audio/mpeg"},
            {"mpp","application/vnd.ms-project"},
            {"mpt","application/x-project"},
            {"mpv","application/x-project"},
            {"mpx","application/x-project"},
            {"mrc","application/marc"},
            {"ms","application/x-troff-ms"},
            {"mv","video/x-sgi-movie"},
            {"my","audio/make"},
            {"mzz","application/x-vnd.audioexplosion.mzz"},
            {"nap","image/naplps"},
            {"naplps","image/naplps"},
            {"nc","application/x-netcdf"},
            {"ncm","application/vnd.nokia.configuration-message"},
            {"nif","image/x-niff"},
            {"niff","image/x-niff"},
            {"nix","application/x-mix-transfer"},
            {"nsc","application/x-conference"},
            {"nvd","application/x-navidoc"},
            {"o","application/octet-stream"},
            {"oda","application/oda"},
            {"omc","application/x-omc"},
            {"omcd","application/x-omcdatamaker"},
            {"omcr","application/x-omcregerator"},
            {"p","text/x-pascal"},
            {"p10","application/pkcs10"},
            {"p10","application/x-pkcs10"},
            {"p12","application/pkcs-12"},
            {"p12","application/x-pkcs12"},
            {"p7a","application/x-pkcs7-signature"},
            {"p7c","application/pkcs7-mime"},
            {"p7c","application/x-pkcs7-mime"},
            {"p7m","application/pkcs7-mime"},
            {"p7m","application/x-pkcs7-mime"},
            {"p7r","application/x-pkcs7-certreqresp"},
            {"p7s","application/pkcs7-signature"},
            {"part","application/pro_eng"},
            {"pas","text/pascal"},
            {"pbm","image/x-portable-bitmap"},
            {"pcl","application/vnd.hp-pcl"},
            {"pcl","application/x-pcl"},
            {"pct","image/x-pict"},
            {"pcx","image/x-pcx"},
            {"pdb","chemical/x-pdb"},
            {"pdf","application/pdf"},
            {"pfunk","audio/make"},
            {"pfunk","audio/make.my.funk"},
            {"pgm","image/x-portable-graymap"},
            {"pgm","image/x-portable-greymap"},
            {"pic","image/pict"},
            {"pict","image/pict"},
            {"pkg","application/x-newton-compatible-pkg"},
            {"pko","application/vnd.ms-pki.pko"},
            {"pl","text/plain"},
            {"pl","text/x-script.perl"},
            {"plx","application/x-pixclscript"},
            {"pm","image/x-xpixmap"},
            {"pm","text/x-script.perl-module"},
            {"pm4","application/x-pagemaker"},
            {"pm5","application/x-pagemaker"},
            {"png","image/png"},
            {"pnm","application/x-portable-anymap"},
            {"pnm","image/x-portable-anymap"},
            {"pot","application/mspowerpoint"},
            {"pot","application/vnd.ms-powerpoint"},
            {"pov","model/x-pov"},
            {"ppa","application/vnd.ms-powerpoint"},
            {"ppm","image/x-portable-pixmap"},
            {"pps","application/mspowerpoint"},
            {"pps","application/vnd.ms-powerpoint"},
            {"ppt","application/mspowerpoint"},
            {"ppt","application/powerpoint"},
            {"ppt","application/vnd.ms-powerpoint"},
            {"ppt","application/x-mspowerpoint"},
            {"ppz","application/mspowerpoint"},
            {"pre","application/x-freelance"},
            {"prt","application/pro_eng"},
            {"ps","application/postscript"},
            {"psd","application/octet-stream"},
            {"pvu","paleovu/x-pv"},
            {"pwz","application/vnd.ms-powerpoint"},
            {"py","text/x-script.phyton"},
            {"pyc","applicaiton/x-bytecode.python"},
            {"qcp","audio/vnd.qcelp"},
            {"qd3","x-world/x-3dmf"},
            {"qd3d","x-world/x-3dmf"},
            {"qif","image/x-quicktime"},
            {"qt","video/quicktime"},
            {"qtc","video/x-qtc"},
            {"qti","image/x-quicktime"},
            {"qtif","image/x-quicktime"},
            {"ra","audio/x-pn-realaudio"},
            {"ra","audio/x-pn-realaudio-plugin"},
            {"ra","audio/x-realaudio"},
            {"ram","audio/x-pn-realaudio"},
            {"ras","application/x-cmu-raster"},
            {"ras","image/cmu-raster"},
            {"ras","image/x-cmu-raster"},
            {"rast","image/cmu-raster"},
            {"rexx","text/x-script.rexx"},
            {"rf","image/vnd.rn-realflash"},
            {"rgb","image/x-rgb"},
            {"rm","application/vnd.rn-realmedia"},
            {"rm","audio/x-pn-realaudio"},
            {"rmi","audio/mid"},
            {"rmm","audio/x-pn-realaudio"},
            {"rmp","audio/x-pn-realaudio"},
            {"rmp","audio/x-pn-realaudio-plugin"},
            {"rng","application/ringing-tones"},
            {"rng","application/vnd.nokia.ringing-tone"},
            {"rnx","application/vnd.rn-realplayer"},
            {"roff","application/x-troff"},
            {"rp","image/vnd.rn-realpix"},
            {"rpm","audio/x-pn-realaudio-plugin"},
            {"rt","text/richtext"},
            {"rt","text/vnd.rn-realtext"},
            {"rtf","application/rtf"},
            {"rtf","application/x-rtf"},
            {"rtf","text/richtext"},
            {"rtx","application/rtf"},
            {"rtx","text/richtext"},
            {"rv","video/vnd.rn-realvideo"},
            {"s","text/x-asm"},
            {"s3m","audio/s3m"},
            {"saveme","application/octet-stream"},
            {"sbk","application/x-tbook"},
            {"scm","application/x-lotusscreencam"},
            {"scm","text/x-script.guile"},
            {"scm","text/x-script.scheme"},
            {"scm","video/x-scm"},
            {"sdml","text/plain"},
            {"sdp","application/sdp"},
            {"sdp","application/x-sdp"},
            {"sdr","application/sounder"},
            {"sea","application/sea"},
            {"sea","application/x-sea"},
            {"set","application/set"},
            {"sgm","text/sgml"},
            {"sgm","text/x-sgml"},
            {"sgml","text/sgml"},
            {"sgml","text/x-sgml"},
            {"sh","application/x-bsh"},
            {"sh","application/x-sh"},
            {"sh","application/x-shar"},
            {"sh","text/x-script.sh"},
            {"shar","application/x-bsh"},
            {"shar","application/x-shar"},
            {"shtml","text/html"},
            {"shtml","text/x-server-parsed-html"},
            {"sid","audio/x-psid"},
            {"sit","application/x-sit"},
            {"sit","application/x-stuffit"},
            {"skd","application/x-koan"},
            {"skm","application/x-koan"},
            {"skp","application/x-koan"},
            {"skt","application/x-koan"},
            {"sl","application/x-seelogo"},
            {"smi","application/smil"},
            {"smil","application/smil"},
            {"snd","audio/basic"},
            {"snd","audio/x-adpcm"},
            {"sol","application/solids"},
            {"spc","application/x-pkcs7-certificates"},
            {"spc","text/x-speech"},
            {"spl","application/futuresplash"},
            {"spr","application/x-sprite"},
            {"sprite","application/x-sprite"},
            {"src","application/x-wais-source"},
            {"ssi","text/x-server-parsed-html"},
            {"ssm","application/streamingmedia"},
            {"sst","application/vnd.ms-pki.certstore"},
            {"step","application/step"},
            {"stl","application/sla"},
            {"stl","application/vnd.ms-pki.stl"},
            {"stl","application/x-navistyle"},
            {"stp","application/step"},
            {"sv4cpio","application/x-sv4cpio"},
            {"sv4crc","application/x-sv4crc"},
            {"svf","image/vnd.dwg"},
            {"svf","image/x-dwg"},
            {"svr","application/x-world"},
            {"svr","x-world/x-svr"},
            {"swf","application/x-shockwave-flash"},
            {"t","application/x-troff"},
            {"talk","text/x-speech"},
            {"tar","application/x-tar"},
            {"tbk","application/toolbook"},
            {"tbk","application/x-tbook"},
            {"tcl","application/x-tcl"},
            {"tcl","text/x-script.tcl"},
            {"tcsh","text/x-script.tcsh"},
            {"tex","application/x-tex"},
            {"texi","application/x-texinfo"},
            {"texinfo","application/x-texinfo"},
            {"text","application/plain"},
            {"text","text/plain"},
            {"tgz","application/gnutar"},
            {"tgz","application/x-compressed"},
            {"tif","image/tiff"},
            {"tif","image/x-tiff"},
            {"tiff","image/tiff"},
            {"tiff","image/x-tiff"},
            {"tr","application/x-troff"},
            {"tsi","audio/tsp-audio"},
            {"tsp","application/dsptype"},
            {"tsp","audio/tsplayer"},
            {"tsv","text/tab-separated-values"},
            {"turbot","image/florian"},
            {"txt","text/plain"},
            {"uil","text/x-uil"},
            {"uni","text/uri-list"},
            {"unis","text/uri-list"},
            {"unv","application/i-deas"},
            {"uri","text/uri-list"},
            {"uris","text/uri-list"},
            {"ustar","application/x-ustar"},
            {"ustar","multipart/x-ustar"},
            {"uu","application/octet-stream"},
            {"uu","text/x-uuencode"},
            {"uue","text/x-uuencode"},
            {"vcd","application/x-cdlink"},
            {"vcs","text/x-vcalendar"},
            {"vda","application/vda"},
            {"vdo","video/vdo"},
            {"vew","application/groupwise"},
            {"viv","video/vivo"},
            {"viv","video/vnd.vivo"},
            {"vivo","video/vivo"},
            {"vivo","video/vnd.vivo"},
            {"vmd","application/vocaltec-media-desc"},
            {"vmf","application/vocaltec-media-file"},
            {"voc","audio/voc"},
            {"voc","audio/x-voc"},
            {"vos","video/vosaic"},
            {"vox","audio/voxware"},
            {"vqe","audio/x-twinvq-plugin"},
            {"vqf","audio/x-twinvq"},
            {"vql","audio/x-twinvq-plugin"},
            {"vrml","application/x-vrml"},
            {"vrml","model/vrml"},
            {"vrml","x-world/x-vrml"},
            {"vrt","x-world/x-vrt"},
            {"vsd","application/x-visio"},
            {"vst","application/x-visio"},
            {"vsw","application/x-visio"},
            {"w60","application/wordperfect6.0"},
            {"w61","application/wordperfect6.1"},
            {"w6w","application/msword"},
            {"wav","audio/wav"},
            {"wav","audio/x-wav"},
            {"wb1","application/x-qpro"},
            {"wbmp","image/vnd.wap.wbmp"},
            {"web","application/vnd.xara"},
            {"wiz","application/msword"},
            {"wk1","application/x-123"},
            {"wmf","windows/metafile"},
            {"wml","text/vnd.wap.wml"},
            {"wmlc","application/vnd.wap.wmlc"},
            {"wmls","text/vnd.wap.wmlscript"},
            {"wmlsc","application/vnd.wap.wmlscriptc"},
            {"word","application/msword"},
            {"wp","application/wordperfect"},
            {"wp5","application/wordperfect"},
            {"wp5","application/wordperfect6.0"},
            {"wp6","application/wordperfect"},
            {"wpd","application/wordperfect"},
            {"wpd","application/x-wpwin"},
            {"wq1","application/x-lotus"},
            {"wri","application/mswrite"},
            {"wri","application/x-wri"},
            {"wrl","application/x-world"},
            {"wrl","model/vrml"},
            {"wrl","x-world/x-vrml"},
            {"wrz","model/vrml"},
            {"wrz","x-world/x-vrml"},
            {"wsc","text/scriplet"},
            {"wsrc","application/x-wais-source"},
            {"wtk","application/x-wintalk"},
            {"xbm","image/x-xbitmap"},
            {"xbm","image/x-xbm"},
            {"xbm","image/xbm"},
            {"xdr","video/x-amt-demorun"},
            {"xgz","xgl/drawing"},
            {"xif","image/vnd.xiff"},
            {"xl","application/excel"},
            {"xla","application/excel"},
            {"xla","application/x-excel"},
            {"xla","application/x-msexcel"},
            {"xlb","application/excel"},
            {"xlb","application/vnd.ms-excel"},
            {"xlb","application/x-excel"},
            {"xlc","application/excel"},
            {"xlc","application/vnd.ms-excel"},
            {"xlc","application/x-excel"},
            {"xld","application/excel"},
            {"xld","application/x-excel"},
            {"xlk","application/excel"},
            {"xlk","application/x-excel"},
            {"xll","application/excel"},
            {"xll","application/vnd.ms-excel"},
            {"xll","application/x-excel"},
            {"xlm","application/excel"},
            {"xlm","application/vnd.ms-excel"},
            {"xlm","application/x-excel"},
            {"xls","application/excel"},
            {"xls","application/vnd.ms-excel"},
            {"xls","application/x-excel"},
            {"xls","application/x-msexcel"},
            {"xlt","application/excel"},
            {"xlt","application/x-excel"},
            {"xlv","application/excel"},
            {"xlv","application/x-excel"},
            {"xlw","application/excel"},
            {"xlw","application/vnd.ms-excel"},
            {"xlw","application/x-excel"},
            {"xlw","application/x-msexcel"},
            {"xm","audio/xm"},
            {"xml","application/xml"},
            {"xml","text/xml"},
            {"xmz","xgl/movie"},
            {"xpix","application/x-vnd.ls-xpix"},
            {"xpm","image/x-xpixmap"},
            {"xpm","image/xpm"},
            {"x-png","image/png"},
            {"xsr","video/x-amt-showrun"},
            {"xwd","image/x-xwd"},
            {"xwd","image/x-xwindowdump"},
            {"xyz","chemical/x-pdb"},
            {"z","application/x-compress"},
            {"z","application/x-compressed"},
            {"zip","application/x-compressed"},
            {"zip","application/x-zip-compressed"},
            {"zip","application/zip"},
            {"zip","multipart/x-zip"},
            {"zoo","application/octet-stream"},
            {"zsh","text/x-script.zsh"}
        };
        for(int i=0;i<ExtensionContentType.length;i++){
            if(ExtensionContentType[i][0].toLowerCase().equals(extension.toLowerCase())){
                    resultado=ExtensionContentType[i][1];
            }
        }
        return resultado;
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