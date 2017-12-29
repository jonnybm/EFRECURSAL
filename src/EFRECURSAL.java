import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;

import javax.faces.bean.ManagedBean;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import com.sun.prism.paint.Color;
import com.sun.xml.internal.ws.util.StringUtils;
 


@ManagedBean
public class EFRECURSAL {
	
	private static GetSetCEF getSetCEF = new GetSetCEF();
	private static EficienciaFinanceiraRecursal EficienciaFinanceiraRecur = new EficienciaFinanceiraRecursal();
	private static String banco = "RECURSAL";  // BB ou CEF
	
//  	static String caminho = "/Volumes/HD/BackupJonny/Projetos/EficienciaFinanceira/PDFBB/";
//  	static String excelBB = "/Volumes/HD/BackupJonny/Projetos/EficienciaFinanceira/ExcelBB/Arcos31.07.2017.xlsx";

  	static String caminho = "";
  	static String excelBB = "";
  	static String ComboMes = "";
  	static String ComboAno = "";
	
	
  	static ArrayList<String> arrayPublicoCJExisteUnica = new ArrayList<String> ();
  	
  	
  	static File fo = new File(excelBB);

	//public static void main(String[] args) throws IOException {	
	public static void init() throws IOException, InterruptedException {
		fo = new File(excelBB);
		visualizarArquivos();
		
	}

	
	  public static void visualizarArquivos() throws IOException, InterruptedException {
		  


		  	String arquivoPDF = "";	
		  	String ret = "";
		  	String excluidos  = "";
		  
		  	File file = new File(caminho);
			File afile[] = file.listFiles();
			int i = 0;
			
			for (int j = afile.length; i < j; i++) {
				File arquivos = afile[i];
				arquivoPDF = caminho+arquivos.getName();
				//System.out.println(arquivos.getName());
				
				if(arquivos.getName().indexOf ("doc") >= 0) {
				
					//Recebe e Le Texto do PDF
					String texto = extraiTextoDoWORD(arquivoPDF);
					
					//Recebe o Conteudo do PDF e coloca em Array com quebra de Linha
					String linhas[] = texto.split("\n");
					
			        					        			
					//Trata o PDF lendo linha a linha do array
					//String textoret = trataPDF(linhas);
					 ret = trataDOC(linhas);
					
					 //System.out.println("PASSo 5 {"+ ret+"}");
					
					 //System.out.println("--> "+textoret);
										 
					  if(arquivoPDF.indexOf ("DS_Store") <= 0 ) //Se arquivo for diferente de arquivo de sistema que nao precisa ser analizado ou se o valor é zero mas nao igual ao mes de consulta
					  {
//						if(!ret.equals("ValorZeroNaoMesDeReferenciaContulta"))
//						{ 
							lerExcel("a");
						  
							getSetCEF.setPorcentagem((i*100)/afile.length);
							System.out.println(" STATUS: ["+getSetCEF.getPorcentagem()+" %]");
//						}
//						else
//						{
//							excluidos = caminho+"EXCLUIDOS";
//							
//							//VENDO SE O DIRETORIO EXISTE CASO NAO EXISTA CRIAR
//							diretorio(excluidos);
//							
//							Thread.sleep( 500 ); 
//							
//							excluidos = excluidos+"/"+arquivos.getName();
//							
//							System.out.println(" PDF ORIGINAL: ["+arquivoPDF+"]");
//							System.out.println(" PDF JOGAR: ["+excluidos+"]");
							
							
							//arquivos.renameTo(new File(excluidos));
							
							
							// -------- FAZER AQUI ------- ///
				
							// ----2 SE entrou neste if pod jogar o PDF (arquivoPDF) para a pasta EXCLUIDOS
							// --  3 
//						}	
					  }
				}
			
				

			}
			
			
			getSetCEF.setFimArquivo("FINALIZADO COM SUCESSO");
			System.out.println("FINALIZADO COM SUCESSO");
		}
	
  

	//EXTRAI INFORMACOES CAIXA RECURSAL EM WORD
		 public static String extraiTextoDoWORD(String caminho) throws IOException {
			 BufferedReader br = new BufferedReader(new FileReader(caminho)); 
			 String linha = "";
			 
			  // SE NAO FOR ARQUIVO PDF
			  if(caminho.indexOf ("doc") < 0) {
				  return "NAOeDOCVALIDO";
			  }
			 
			 
		     while (br.ready()) {
		          linha = linha + "\n" +br.readLine();
		          //System.out.println(linha);

		     }
		
		     String linhas[] = linha.split("\n");
			//System.out.println("CONTEUDO :>"+linha + " ]");

		     
		     
		     for (int i = 0; i < linhas.length; i++) 
			  {
				  
				  String ret = linhas[i];
				  
				//System.out.println("CONTEUDO :>"+ret + " ["+i+"]");
			  }
		     
		     
		     br.close();
			return linha;
		 }
		 
		 
			  
		  
		  //RECEBE O CONTEUDO DO EXCEL PARA TRATAMENTO DAS INFORMACOES
		  public static String trataDOC(String[] linhas) 
		  {			  
			  String ret = "";
			  
			  //System.out.println("PASSO 1");

				  if(banco.equals("RECURSAL")) // Word Caixa recursal em DOC
				  {
					  for (int i = 0; i < linhas.length; i++) 
					  {
						  
						  if(i == 4) //PEGANDO CONTA JUDICIAL
				          {
							  	ret = "";
							  	ret = linhas[i];
				        	  		

				        	  		//PEGANDO APENAS NUMERO INTEIRO DA STRING PARA RESGATAR O DODIGO DO EMPREDADO
				           		String numeros = ret;
				           		Pattern p = Pattern.compile("[0-9]+");
				           		Matcher m = p.matcher(numeros);

					           	StringBuilder nroExtraidos = new StringBuilder();
					           	while (m.find()) {
					           	    nroExtraidos.append(m.group().trim());
					           	}
					           	ret = nroExtraidos.toString();
					           	getSetCEF.setContaJuridica(ret); 
					           	//System.out.println("CODIGO EMPREGADO---------"+ret);

				          }
						  
				          if(i == 4) //PEGANDO O NOME
				          {
				        	  		ret = "";
				        	  		ret =  linhas[i]; 
				        	  		
				        	  		
				        	  		ret = ret.replaceAll("COD.EMPRG. :","");
				        	  		ret = ret.replaceAll(getSetCEF.getContaJuridica(),"");
				        	  		ret = ret.replaceAll("DEP","");
				        	  		ret = ret.trim();
				        	  		
				        	  		getSetCEF.setAutor(ret);
				        	  		
				        	  		//System.out.println("NOME EMPREGADO---------"+ret);
				          }
				          
				          
				          if(i == 3) //PEGANDO O REU
				          {
				        	  		ret = "";
				        	  		ret =  linhas[i]; 
				        	  		
				        	  		String lixo = "";
				        	  		lixo =  linhas[i];
				        	  		
				        	  		
				        	  		//PEGANDO APENAS NUMERO INTEIRO DA STRING PARA RESGATAR O DODIGO DO ETABELECIMENTO
					           	String numeros = lixo;
					           	Pattern p = Pattern.compile("[0-9]+");
					           	Matcher m = p.matcher(numeros);

						        StringBuilder nroExtraidos = new StringBuilder();
						        while (m.find()) {
						             nroExtraidos.append(m.group().trim());
						       	}
						        lixo = nroExtraidos.toString();
				        	  		
				        	  		
				        	  		ret = ret.replaceAll("COD.ESTAB. :","");
				        	  		ret = ret.replaceAll(lixo,"");
				        	  		ret = ret.replaceAll("DEP","");
				        	  		ret = ret.trim();
				        	  		
				        	  		getSetCEF.setReu(ret);
				        	  		
				        	  		//System.out.println("NOME REU---------"+ret);
				          }
				          
				          
				          if(i == 7) //PEGANDO O CNPJ
				          {
			        	  		String[] Valor = null;
			        	  		Valor =  linhas[i].split(" ");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha
			        	  		
			        	  		
			        	  		//VALOR DEPOSITO
			        	  		getSetCEF.setCNPJ(Valor[Valor.length -1]);
			        	  		//System.out.println("VALOR DO CNPJ---------"+Valor[Valor.length -1]);
			        	  		
				          }
				          
				          
				          if(i == 8) //PEGANDO NUMERO DO PROCESSO
				          {
			        	  		String[] Valor = null;
			        	  		
			        	  		ret = linhas[i];
			        	  		ret = ret.replace("PROC/VARA :               ", "");
			        	  		ret = ret.replace("PROC/VARA :              ", "");
			        	  		ret = ret.replace("PROC/VARA :             ", "");
			        	  		ret = ret.replace("PROC/VARA :            ", "");
			        	  		ret = ret.replace("PROC/VARA :           ", "");
			        	  		ret = ret.replace("PROC/VARA :          ", "");
			        	  		ret = ret.replace("PROC/VARA :         ", "");
			        	  		ret = ret.replace("PROC/VARA :        ", "");
			        	  		ret = ret.replace("PROC/VARA :       ", "");
			        	  		ret = ret.replace("PROC/VARA :      ", "");
			        	  		ret = ret.replace("PROC/VARA :     ", "");
			        	  		ret = ret.replace("PROC/VARA :    ", "");
			        	  		ret = ret.replace("PROC/VARA :   ", "");
			        	  		ret = ret.replace("PROC/VARA :  ", "");
			        	  		ret = ret.replace("PROC/VARA : ", "");
			        	  		ret = ret.replace("PROC/VARA :", "");
			        	  		
			        	  		
			        	  		//System.out.println("VALOR DO PROCESSO---------"+linhas[i]);
			        	  		
			        	  		
			        	  		
			        	  		Valor =  ret.split("/");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha

			        	  		
			        	  		
			        	  		//Valor =  linhas[i].split(" ");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha
			        	  		
			        	  		//System.out.println("VALOR DO PROCESSO---------"+Valor[0]);
			        	  		getSetCEF.setProcesso(Valor[0]);
			        	  		
			        	  		//VALOR DEPOSITO
			        	  		//getSetCEF.setProcesso(ret);
			        	  		//System.out.println("VALOR DO PROCESSO---------"+ret);
			        	  		
				          }
				          
				          
				          if(i == 33) //PEGANDO O VALOR DEPOSITO INICIAL E A DATA DEPOSITO INICIAL
				          {
			        	  		String[] Valor = null;
			        	  		Valor =  linhas[i].split(" ");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha
			        	  		
			        	  		
			        	  		//DATA DEPOSITO
			    		        try{ //Bloco try-catch utilizado pois leitura de string gera a exceção abaixo
			    		        		ret = "";
				        	  		ret = linhas[i].substring(0, 10);
				        	  		getSetCEF.setDataDeposito(ret.trim());
				        	  		//System.out.println("DATA DEPOSITO---------"+ret);
			    		        }
			    		        	catch(IndexOutOfBoundsException indexOutOfBoundsException){
			    		        		//SE DER ERRO E PQ NAO TEM DATA E TUDO QUE ESTIVER COMO 11/11/1111 O EXTRADO NAO TEM DATA
			    		        		getSetCEF.setDataDeposito("11/11/1111");
			    		        }
			        	  		
			        	  		
			        	  		
			        	  		//VALOR DEPOSITO
			        	  		getSetCEF.setValorOriginal(Valor[Valor.length -1]);
			        	  		//System.out.println("VALOR DO DEPOSITO---------"+Valor[Valor.length -1]);
			        	  		
				          }
				          
				          
				          //ULTIMO REGISTRO PEGANDO O SALDO
				          if(linhas.length-1 == i)//é o Ultimo  Registro?
				          {
				        	  		ret = "";
				        	  
				        	  		if(!ret.matches("[0-9]"))//contem apenas numero?
				        	        {
					        	  		
					        	  		ret =  linhas[i]; 

					        	  		ret = ret.replace("TOTAL SALDO DISPONIVEL", "");
					        	  		ret = ret.trim();

						  			
				        	  			//System.out.println("TOTAL SALDO DISPONIVEL :>>>>>>>>"+ret);

				        	        }
				        	  		getSetCEF.setValorAtualizado(ret.trim());				        	  		
				          }
				          
				          
				          
				          
						  
						  
						} 
				  }	  
			  return ret;
		  }
		  
		  
		  public static String trataSujeira(String str) {
			  
			//  System.out.println("TRATAR SUJEIRA :|"+str+"|");
			  
		        String ret = "";
		        
		        ret = str;
		        
		        
		        ret = ret.replaceAll(" / ", "");// TRATA SUJEIRA DA CONTA JURIDICA
		        ret = ret.replaceAll("-", "");// TRATA SUJEIRA DA CONTA JURIDICA
		        ret = ret.replaceAll("CRED", "");//TRA SUJEIRA DO SALDO
		        ret = ret.replaceAll("DEP.DINH.", "");
		        ret = ret.replaceAll("Saldo Anterior", "");
		        
		        ret = ret.replaceAll("A VARA DO TRABALHO", "");
		        
           		ret = ret.replaceAll("[^\\p{ASCII}]", "");
           		ret = ret.replaceAll("//", "");
           		ret = ret.replaceAll("/", "");
           		ret = ret.replaceAll("#", "");
           		ret = ret.replaceAll("Saldodoperodo", "");
	        		ret = ret.replaceAll("Saldodoperodo", "");
	        		ret = ret.replaceAll("Anterio", "");

	        		ret = ret.replaceAll("..-", "");
        	  		ret = ret.replaceAll("Anterio", "");
        	  		ret = ret.replaceAll("DEP.DINH", "");
        	  		ret = ret.replaceAll("DEB.AUTOR", "");
        	  		ret = ret.replace("Remunerao", "");
        	  		
        	  		ret = ret.replace("Autor          :", "");
        	  		ret = ret.replace("         Saldo do período             ", "");
				ret = ret.replace("          Saldo do período            ", "");
				ret = ret.replace("    Saldo do periodo    ", "");
				ret = ret.replace("/", "");
				ret = ret.replace("                ", "");
				ret = ret.replace("Reclamante", "");
				ret = ret.replace(":", "");
				ret = ret.replace("     ", "");
				ret = ret.replace("     Saldo do periodo ", "");
				ret = ret.replace("Autor", "");
				ret = ret.replace("     Saldo do periodo    ", "");
				ret = ret.replace("     Saldo do periodo    ", "");
				ret = ret.replace("Saldo do periodo", "");
		       	ret = ret.replaceAll("[^\\p{ASCII}]", "");
	         	ret = ret.replaceAll("//", "");
	      		ret = ret.replaceAll("/", "");
	      		ret = ret.replaceAll("#", "");
	      		ret = ret.replaceAll("Saldodoperodo", "");
	     		ret = ret.replaceAll("Saldo do período", "");
	     		ret = ret.replaceAll("Saldo do perodo", "");
	     		ret = ret.replaceAll("      ", "");
		     		ret = ret.replace("CONTA JUDICIAL", "");
	       		return ret;
		    }
		  
		  
		  private static boolean campoNumerico(String campo){           
		        return campo.matches("[0-9]+");   
		}
				  
		  		  
		  public static String toTitledCase(String nome){
			  
			  //System.out.println("STR ENTRADA= "+ nome);
			  
			  nome = " "+nome; 
			  	
			  String aux =""; // só é utilizada para facilitar 

		        try{ //Bloco try-catch utilizado pois leitura de string gera a exceção abaixo
		            for(int i = 0; i < nome.length(); ++i){
		                if( nome.substring(i, i+1).equals(" ") || nome.substring(i, i+1).equals("  "))
		                {
		                    aux += nome.substring(i+1, i+2).toUpperCase();
		                   // System.out.println("1= "+ aux);
		                }
		                else
		                {
		                    aux += nome.substring(i+1, i+2).toLowerCase();
		                    //System.out.println("2= "+ aux);
		                }
		        }
		        }catch(IndexOutOfBoundsException indexOutOfBoundsException){
		            //não faça nada. só pare tudo e saia do bloco de instrução try-catch
		        }
		        nome = aux;
		       // System.err.println(nome);

			  return nome;
			}  
		  
		  
		  public static String diretorio(String caminho) {
			
			  File diretorio = new File(caminho); // ajfilho é uma pasta!
			  if (!diretorio.exists()) {
			     diretorio.mkdir(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
			  } else {
			     System.out.println("Diretório já existente");
			  }

			  
			  return caminho;
			  
		  }
		  
		  public static String lerExcel(String str) throws IOException{

			  
			  
					int contAux = 0;// Controle para pegar o Numer da Conta Juridica
					int contadorPosicao = 11; //Para saber a linha que passou
					
					//Conta Juridica Existente:
					int posicaoExiste = 0; // Pega Guardar a posicao da Conta Existente
					int posicaoExisteUnica = 0; // Pega Guardar a posicao da Conta Existente
					
					boolean cjExiste = false;
					boolean cjExisteUnica = true;
					boolean cjNova = false;
					boolean barrarGravacaoContaJaExiste = false;
					
					long contaJudicial = 0;
					String valotAtualizado = "";
					String numeroParcela = "";
					boolean temValorParcela = false;
					String posicaoEvalor = "";
					
					String M = "M1";
					String L = "L1";
					String N = "N1";
					
					ArrayList<String> arrayConteudoExiste = new ArrayList<String> ();
					ArrayList<String> arrayConteudoNovoComValor = new ArrayList<String> ();
					ArrayList<String> arrayConteudoNovoSemValor = new ArrayList<String> ();
					ArrayList<String> arrayConteudoCJExisteNaoUnica = new ArrayList<String> ();
					
					ArrayList<String> arrayConteudoCJExisteNaoUnicaIncluirValor = new ArrayList<String> ();
					String ConteudoCJExisteNaoUnicaIncluir = "";
					
					ArrayList<String> arrayConteudoCJExisteNaoUnicaIncluirParcela = new ArrayList<String> ();
					String ConteudoExistenumeroParcela = "";
					String parcela = "";
					String CJXLX = "";
					String CJPDF = "";
			 
					try {

						ZipSecureFile.setMinInflateRatio(-1.0d);
			            XSSFWorkbook workbook = new XSSFWorkbook(excelBB);
			            XSSFSheet sheet = workbook.getSheetAt(0);
			            Row row = sheet.getRow(0);  
			            
			            getSetCEF.setCjExisteUnica(true);
			            getSetCEF.setCjExiste(false);
			            getSetCEF.setCjNova(true);
			            
			            CellReference cellReferencePAR = null;
			            Row rowLPAR = null;
			            Cell cellLPAR = null;
			            
			            CellReference cellReferenceVAL = null;
			            Row rowLVAL = null;
			            Cell cellLVAL = null;		           
			            
			            CellReference cellReferenceCJ = null;
			            Row rowLCJ = null;
			            Cell cellLCJ = null;
			            
			            
	
			            	for (int i = 11; i < 10000; i++) //Comeca do 11 para inicinar na linha 11
						{
			            	      try 
			            	      {
			            	    	  	//LENDO AS COLUNAS N1, N2 , N3  (VALOR ATUALIZADO)
//				            		 N = "N"+i;
//				            		 cellReferencePAR = new CellReference(N);   //Ferencia Coluna M usado na Conta Judicial
//				            		 rowLPAR = sheet.getRow(cellReferencePAR.getRow());	 //Ferencia Linha usado na Conta Judicial
//				            	     cellLPAR = rowLPAR.getCell(cellReferencePAR.getCol());
	
			            	    	  
			            	    	  
			            	    	  	//LENDO AS COLUNAS L1, L2 , L3  (VALOR ATUALIZADO)
				            		 L = "L"+i;
				            		 cellReferenceVAL = new CellReference(L);   //Ferencia Coluna M usado na Conta Judicial
				            		 rowLVAL = sheet.getRow(cellReferenceVAL.getRow());	 //Ferencia Linha usado na Conta Judicial
				            	     cellLVAL = rowLVAL.getCell(cellReferenceVAL.getCol());
	
			            	    	  
			            	    	  
			            	    	  	//LENDO AS COLUNAS M1, M2 , M3  (CONTA JUDICIAL)
				            		 M = "M"+i;			            		 
				            		 cellReferenceCJ = new CellReference(M);   //Ferencia Coluna M usado na Conta Judicial
				            		 rowLCJ = sheet.getRow(cellReferenceCJ.getRow());	 //Ferencia Linha usado na Conta Judicial
				            	     contaJudicial = 0;
		
			            	    	     cellLCJ = rowLCJ.getCell(cellReferenceCJ.getCol());  
				            	     
			            	    	     if(cellLCJ.CELL_TYPE_NUMERIC==cellLCJ.getCellType()) {
			                    		contaJudicial = (long) cellLCJ.getNumericCellValue();
				                    	contadorPosicao++;
				                    	getSetCEF.setContadorPosicao(contadorPosicao);
				            	     }
				            	     else if(cellLCJ.CELL_TYPE_STRING==cellLCJ.getCellType()) {
				            	    	 	CJXLX = cellLCJ.getStringCellValue();
				            	    	 	
				            	    	 	CJXLX = CJXLX.replace("//", "");
				            	    	 	CJXLX = CJXLX.replace("/", "");
				            	    	 	CJXLX = CJXLX.replace("-", "");
				            	    	 	CJXLX = CJXLX.replace("  ", "");
				            	    	 	CJXLX = CJXLX.trim();
				            	    	 	//System.out.println("CJ EXEL :>"+CJXLX);
				            	    	 	
				            	    	 	contaJudicial = Long.parseLong(CJXLX);
				                    	contadorPosicao++;
				                    	getSetCEF.setContadorPosicao(contadorPosicao);
				            	     }
			            	    	  
			            	    	     	CJPDF = getSetCEF.getContaJuridica();
				            	    	 	
			            	    	     	CJPDF = CJPDF.replace("//", "");
			            	    	     	CJPDF = CJPDF.replace("/", "");
			            	    	     	CJPDF = CJPDF.replace("-", "");
			            	    	     	CJPDF = CJPDF.replace("  ", "");
			            	    	     	CJPDF = CJPDF.trim();
				            	    	 	//System.out.println("CJ EXEL :>"+CJPDF);
			                    	//System.out.println("contadorPosicao: " + contadorPosicao);
				            	     

			            	    	     
			            	    	     
				            	      if(contaJudicial == Long.parseLong(CJPDF)) 
			                        	{
				            	    	  
				            	    	     System.out.println("contaJudicial EXEL :>"+contaJudicial);
				            	    	     System.out.println("contaJudicial PDF :>"+CJPDF);
				            	    	     System.out.println("POSICAO :>"+i);
				            	    	     
				            	    	     
				            	    	  		getSetCEF.setPosicaoExiste(i);	
			                        	 	getSetCEF.setCjNova(false);
	                  	 	
			                        	 
			                        	 	//---> VOU ADCIONANDO NO ARRAY ROSA QUE EXISTE PRA PINTAR DE UMA UNICA VEZ.
			                        	 	//arrayConteudoCJExisteNaoUnica.add(Integer.toString(getSetCEF.getPosicaoExiste()));
			                        	 	
			                        	 	
		                        	 		//Guarda o Valor atualizado para futura comparacao
			   			            	     if(cellLVAL.CELL_TYPE_NUMERIC==cellLVAL.getCellType()) {
			   			            	    	 	valotAtualizado = String.valueOf(cellLVAL.getNumericCellValue()) ;
						            	     }
			   			            	     if(cellLVAL.CELL_TYPE_STRING==cellLVAL.getCellType()) {
			   			            	    	 	valotAtualizado =	cellLVAL.getStringCellValue();
						            	     }
			   			            	     
//			   			            	     //Guardando a Parcela
//						            	     if(cellLPAR != null)
//						            	     {			            	    	 	
//						            	    	 	if(cellLPAR.CELL_TYPE_STRING==cellLPAR.getCellType() ) {			            	    	 		
//						            	    	 		numeroParcela = cellLPAR.getStringCellValue();
//						            	    	 	}
//						            	     }
			   			            	     
			   			            	     //---> ADIDIONA NESTE ARRAY PARA SABER SE JA EXISTE ANTES DE INCLUIR COM O MESMO VALOR E CONTA JUDICIAL
			   			            	  //	arrayConteudoCJExisteNaoUnicaIncluirValor.add(valotAtualizado);
			   			            	  //	arrayConteudoCJExisteNaoUnicaIncluirParcela.add(numeroParcela);
			   			            	     
			                        	 		
//			                        	 	if(getSetCEF.isCjExiste()) //ver se a conta existe e é unica ou nao
//			                        	 	{
//			                        	 		getSetCEF.setCjExisteUnica(false);
//			                        	 		//EscreverExistiNaoUnica();  // PINTA DE ROSA PARA AVIAR QUE TEM DUPLICIDADE E CONFERENCIA
//			                        	 		continue;// Se ja ter mais de uma para a verificacao		                        	 		
//			                        	 	}
		                        	 		
			                        	 	getSetCEF.setCjExiste(true); 
		                        	 		
			                        	}
	
							  }catch(Exception e){
								//  contadorPosicao ++;	
								  break;
						      }
						}
	
	
			            
			            if(getSetCEF.isCjNova()) 
		                {
				            	if(!getSetCEF.getValorAtualizado().equals("0,00"))// Se conter Valor
		                	 	{			                        	 		
		                	 		//System.out.println("3 - CONTA NAO EXISTE COM VALOR::" + getSetCEF.getValorAtualizado());
		                	 		EscreverContaNovaComValor();
		                	 	}
		                	 	else
		                	 	{
		                	 		//System.out.println("4 - CONTA NOVA VALOR ZERO:" + getSetCEF.getValorAtualizado());
		                	 		EscreverContaNovaValorZero();
		                	 	}
				            	
				            	//PAUSA PARA PODER GRAVAR O EXCEL
			  			     try {  
			  			        	//System.out.println(" PAUSA");
						        Thread.sleep( 100 );  
						     } 
			  			     catch (InterruptedException e) {  
						         e.printStackTrace();  
						     } 
		                }
			            
	      
			            
			            if(!getSetCEF.isCjNova() && getSetCEF.isCjExiste()) //Se existi é unica
	            	 		{
		            	 		//System.out.println("2-  CONTA EXISTE  É UNICA");
	
				            	if(!getSetCEF.getValorAtualizado().equals("0,00"))// Se conter Valor
		                	 	{			                        	 		
		                	 		//System.out.println("2.1 - VALOR ATUALIZADO: " + getSetCEF.getValorAtualizado());
		                	 		EscreverExistiUnicaComValor();   
		                	 	}
		                	 	else
		                	 	{
		                	 		//System.out.println("2.2 - VALOR ATUALIZADO ZERADO: " + getSetCEF.getValorAtualizado());
		                	 		EscreverExistiUnicaValorZero();  
		                	 	}
				            	
				            	
				            	//PAUSA PARA PODER GRAVAR O EXCEL
				            	try {  
						        Thread.sleep( 100 );  
						     } 
			  			     catch (InterruptedException e) {  
						         e.printStackTrace();  
						     } 
		            	 	}
//		            	 	else if(!getSetCEF.isCjExisteUnica()) 
//		            	 	{
//		            	 		String Valor = "";
//		            	 		String ValorApoio = "";
//		            	 		String valotVaiAtualizar = "";
//		            	 		
//		            	 		Valor  = getSetCEF.getValorAtualizado();
//		            	 		ValorApoio = getSetCEF.getValorAtualizado();
//		            	 		ValorApoio = ValorApoio.replace(".", "");
//		            	 		ValorApoio = ValorApoio.replace(",", "");
//	
//		            	 		
//		            	 		//COLOCO NO BEAN PARA QUANDO SABER AS LINHAS QUE PRECISA PINTAR DE ROSA QUE JA EXISTEM NAS LINHAS PASSADAS - EscreverExistiNaoUnica()
//		            	 		//getSetCEF.setArrayCJExisteNaoUnica(arrayConteudoCJExisteNaoUnica);
//		            	 		
//		            	 		//PINTA DE ROSA OS EXISTENTES  --- PINTAR ROS ESCURO sera os que nao conseguiu identificar
//		            	 		//EscreverExistiNaoUnica();
//	
//		            	 		//LIMPA O ARRAY PARA PODER PINTAR OS PROXIOS
//		            	 		//arrayConteudoCJExisteNaoUnica.clear();
//		            	 		
//		            	 		
//		            	 		
//		    			        for (int i = 0; i < arrayConteudoCJExisteNaoUnicaIncluirValor.size(); i++) {
//		    			        		
//		    			        		//Pegando Valor
//		    			        		valotVaiAtualizar = arrayConteudoCJExisteNaoUnicaIncluirValor.get(i);
//		    			        		valotVaiAtualizar = valotVaiAtualizar.replace(".", "");
//		    			        		valotVaiAtualizar = valotVaiAtualizar.replace(",", "");
//		    			        	
//		    			        		
//		    			        		//Pegando Parcela
//		    			        		ConteudoExistenumeroParcela = arrayConteudoCJExisteNaoUnicaIncluirParcela.get(i);
//		    			        		//ConteudoExistenumeroParcela = (ConteudoExistenumeroParcela.substring(ConteudoExistenumeroParcela.length() - 2).trim()).toUpperCase(); // Pega apenas os dois ultimos Digitos
//		    			        		
//		    			        		ConteudoExistenumeroParcela = (ConteudoExistenumeroParcela.toUpperCase()).trim();
//		    			        		ConteudoExistenumeroParcela = ConteudoExistenumeroParcela.replace("0", "");
//		    			        		
//		    			        		parcela = (getSetCEF.getParcela().toUpperCase()).trim();
//		    			        		parcela = parcela.replace("0", "");
//		    			        		
//		    			        		//Pegando a posicao e Valor para gravar caso a CJ existe e o numero da parcela tambem for igual
//		    			        		getSetCEF.setPosicaoExiste(Integer.parseInt(arrayConteudoCJExisteNaoUnica.get(i)));
//		    			        		
//		    			        		System.out.println("                                                           ");
//		    			        		System.out.println("Valor Excell         |" + valotVaiAtualizar+"|");	            	 		
//		    	            	 		System.out.println("Valor PDF            |" + ValorApoio +"|");
//		    	            	 		System.out.println("------------------------------------------------------------");
//		    	            	 		System.out.println("Parcela Excel        |" + ConteudoExistenumeroParcela +"|");	            	 		
//		    	            	 		System.out.println("Parcela PDF          |" + parcela +"|");	    			        		
//
//		    	            	 		System.out.println("                                                           ");
//			            	 		
//		    	            	 		
//		    	            	 		if(valotVaiAtualizar.equals(ValorApoio) || valotVaiAtualizar == ValorApoio) // Contas Juducuas ja sei que sao iguais a pergunra se o valor e diferente
//		    	                	 	{
//		    			            		System.out.println("VAI BARRAR -- VALORES IGUAIS NAO ATUALIZAR");
//		    			            		barrarGravacaoContaJaExiste = true;
//		    			            		break;
//		    	                	 	}
//			    	            	 	else if( ConteudoExistenumeroParcela.equals(parcela)   || ConteudoExistenumeroParcela == parcela) // Parcelas Iguais Atualiza Valor da Parcela
//			    	            	 	{
//			    	            	 		//SE ENCONTRAR A MESMA PARCELA
//			    	            	 		//PINTA DE ROSA E ATUALIZA O VALOR
//			    	            	 		
//			    	            	 		getSetCEF.setValorAtualizado(Valor);
//
//			    	            	 		System.out.println("CONTA IGUAL E PARCELA DIFERENTE-  VAI ATUALIZAR O VALOR");
//			    	            	 		EscreverExistiNaoUnicaComValor();
//			    	            	 		
//			    	            	 		barrarGravacaoContaJaExiste = true;
//			    	            	 		
//			    	            	 		break;
//			    	            	 	}
//			    	            	 	else
//			            	 				barrarGravacaoContaJaExiste = false;
//						        }
//			            	 		
//		
//			    			        if( !barrarGravacaoContaJaExiste)
//			    			        		EscreverExistiNaoUnicaNovo();
//	        	 				
//	        	 				
//			            	 	//PAUSA PARA PODER GRAVAR O EXCEL
//					        	try 
//					        	{  
//					        //System.out.println(" PAUSA");
//					        		Thread.sleep( 100 );  
//					        	} 
//					        	catch (InterruptedException e) {  
//							    e.printStackTrace();  
//							} 
//			            	 }	
		        } catch (IOException e) {
		            e.printStackTrace();
		        }				
				return "";
		  }
		  
		  
		  
		  public static void EscreverExistiUnicaComValor() throws IOException {
			  try{
				  	fo = new File(excelBB);
			        XSSFWorkbook a = new XSSFWorkbook(new FileInputStream(fo));
			        XSSFSheet my_sheet = a.getSheetAt(0);
			        
			        System.out.println("1-  EscreverExistiUnicaComValor GRAVAR NA LINHA :  " + getSetCEF.getPosicaoExiste());
			        

			        //Direita Cor Azul Claro
			        XSSFCellStyle style1 = a.createCellStyle();
			       // style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 201, 222)));
			        style1.setAlignment ( XSSFCellStyle.ALIGN_RIGHT ) ; 
//			        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			        style1.setBorderBottom(CellStyle.BORDER_THIN);
//			        style1.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderLeft(CellStyle.BORDER_THIN);
//			        style1.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderRight(CellStyle.BORDER_THIN);
//			        style1.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderTop(CellStyle.BORDER_THIN);
//			        style1.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        //Centro Azul Claro
			        XSSFCellStyle style2 = a.createCellStyle();
			       // style2.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 201, 222)));
			        style2.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
//			        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			        style2.setBorderBottom(CellStyle.BORDER_THIN);
//			        style2.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderLeft(CellStyle.BORDER_THIN);
//			        style2.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderRight(CellStyle.BORDER_THIN);
//			        style2.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderTop(CellStyle.BORDER_THIN);
//			        style2.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        CreationHelper createHelper = a.getCreationHelper();
			        XSSFCellStyle data = a.createCellStyle();
			        //data.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 201, 222)));
			        data.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
//			        data.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        data.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));
//			        data.setBorderLeft(CellStyle.BORDER_THIN);
//			        data.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        data.setBorderRight(CellStyle.BORDER_THIN);
//			        data.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        data.setBorderTop(CellStyle.BORDER_THIN);
//			        data.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        //Centro Azul Claro
			        XSSFCellStyle style3 = a.createCellStyle();
			        style3.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 201, 222)));
			        style3.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
			        style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        style3.setBorderBottom(CellStyle.BORDER_THIN);
			        style3.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderLeft(CellStyle.BORDER_THIN);
			        style3.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderRight(CellStyle.BORDER_THIN);
			        style3.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderTop(CellStyle.BORDER_THIN);
			        style3.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			       


//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(1).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(2).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(3).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(4).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(5).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(6).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(7).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(8).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(9).setCellStyle(data);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(10).setCellStyle(style1);
			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(11).setCellValue(getSetCEF.getValorAtualizado());			        
			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(11).setCellStyle(style1);
			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(11).setCellType(XSSFCell.CELL_TYPE_STRING);
			        
			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(12).setCellStyle(style3);
			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(12).setCellType(XSSFCell.CELL_TYPE_STRING);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(13).setCellStyle(style2);
			        
			        FileOutputStream outputStream = new FileOutputStream(new File(excelBB));
			        a.write(outputStream);
			        outputStream.close();//Close in finally if possible
			        outputStream = null;


  
			        
		        }catch(Exception e){
		        }
			}

		  
		  public static void EscreverExistiUnicaValorZero() throws IOException {
			  try{
				  	fo = new File(excelBB);
			        XSSFWorkbook a = new XSSFWorkbook(new FileInputStream(fo));
			        XSSFSheet my_sheet = a.getSheetAt(0);
			        
			        System.out.println("2 -  EscreverExistiUnicaValorZero GRAVAR NA LINHA :  " + getSetCEF.getPosicaoExiste());
			        

			        //Direita Cor Azul Claro
			        XSSFCellStyle style1 = a.createCellStyle();
			        //style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 251, 254)));
			        style1.setAlignment ( XSSFCellStyle.ALIGN_RIGHT ) ; 
//			        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			        style1.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderLeft(CellStyle.BORDER_THIN);
//			        style1.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderRight(CellStyle.BORDER_THIN);
//			        style1.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderTop(CellStyle.BORDER_THIN);
//			        style1.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        //System.out.println("Passo1");
			        
			        //Centro Azul Claro
			        XSSFCellStyle style2 = a.createCellStyle();
			       // style2.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 251, 254)));
			        style2.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
//			        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			        style2.setBorderBottom(CellStyle.BORDER_THIN);
//			        style2.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderLeft(CellStyle.BORDER_THIN);
//			        style2.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderRight(CellStyle.BORDER_THIN);
//			        style2.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderTop(CellStyle.BORDER_THIN);
//			        style2.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        //System.out.println("Passo2");
			        
			        
			        CreationHelper createHelper = a.getCreationHelper();
			        XSSFCellStyle data = a.createCellStyle();
			        //data.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 251, 254)));
			        data.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
//			        data.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        data.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));
//			        data.setBorderLeft(CellStyle.BORDER_THIN);
//			        data.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        data.setBorderRight(CellStyle.BORDER_THIN);
//			        data.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        data.setBorderTop(CellStyle.BORDER_THIN);
//			        data.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        //Centro Azul Claro
			        XSSFCellStyle style3 = a.createCellStyle();
			        style3.setFillForegroundColor(new XSSFColor(new java.awt.Color(183, 16, 46)));
			        style3.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
			        style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        style3.setBorderBottom(CellStyle.BORDER_THIN);
			        style3.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderLeft(CellStyle.BORDER_THIN);
			        style3.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderRight(CellStyle.BORDER_THIN);
			        style3.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderTop(CellStyle.BORDER_THIN);
			        style3.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));

			        
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(1).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(2).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(3).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(4).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(5).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(6).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(7).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(8).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(9).setCellStyle(data);			        
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(10).setCellStyle(style1);
			        
			        //Nao atualziar valor quando for zerado ja existe
			        //my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(11).setCellValue(getSetCEF.getValorAtualizado());			        
			        //my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(11).setCellStyle(style1);
			        //my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(11).setCellType(XSSFCell.CELL_TYPE_STRING);
			        
			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(12).setCellStyle(style3);
			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(12).setCellType(XSSFCell.CELL_TYPE_STRING);
//			        my_sheet.getRow(getSetCEF.getPosicaoExiste()-1).getCell(13).setCellStyle(style2);
			        
     
			        FileOutputStream outputStream = new FileOutputStream(new File(excelBB));
			        a.write(outputStream);
			        outputStream.close();//Close in finally if possible
			        
			        outputStream = null;
  
			        
		        }catch(Exception e){
		        }
			}
		  
		  
		  public static void EscreverContaNovaComValor() throws IOException {
			  try{
				  
				  
				  	
			        XSSFWorkbook a = new XSSFWorkbook(new FileInputStream(fo));
			        
			        XSSFSheet my_sheet = a.getSheetAt(0);
			        
			        
			        System.out.println("3 -  EscreverContaNovaComValor GRAVAR NA LINHA :  " + getSetCEF.getContadorPosicao());
			        

			        //Direita Cor Azul Claro
			        XSSFCellStyle style1 = a.createCellStyle();
			       // style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(89, 170, 8)));
			        style1.setAlignment ( XSSFCellStyle.ALIGN_RIGHT ) ; 
//			        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			        style1.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderLeft(CellStyle.BORDER_THIN);
//			        style1.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderRight(CellStyle.BORDER_THIN);
//			        style1.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderTop(CellStyle.BORDER_THIN);
//			        style1.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        //Centro Azul Claro
			        XSSFCellStyle style2 = a.createCellStyle();
			    //    style2.setFillForegroundColor(new XSSFColor(new java.awt.Color(89, 170, 8)));
			        style2.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
//			        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			        style2.setBorderBottom(CellStyle.BORDER_THIN);
//			        style2.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderLeft(CellStyle.BORDER_THIN);
//			        style2.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderRight(CellStyle.BORDER_THIN);
//			        style2.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderTop(CellStyle.BORDER_THIN);
//			        style2.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        
			        //Centro Com data Azul
			        CreationHelper createHelper = a.getCreationHelper();
			        XSSFCellStyle data = a.createCellStyle();
			        //data.setFillForegroundColor(new XSSFColor(new java.awt.Color(89, 179, 8)));
			        data.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
//			        data.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        data.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));
//			        data.setBorderLeft(CellStyle.BORDER_THIN);
//			        data.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        data.setBorderRight(CellStyle.BORDER_THIN);
//			        data.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        data.setBorderTop(CellStyle.BORDER_THIN);
//			        data.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        //Centro Azul Claro
			        XSSFCellStyle style3 = a.createCellStyle();
			        style3.setFillForegroundColor(new XSSFColor(new java.awt.Color(89, 179, 8)));
			        style3.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
			        style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        style3.setBorderBottom(CellStyle.BORDER_THIN);
			        style3.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderLeft(CellStyle.BORDER_THIN);
			        style3.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderRight(CellStyle.BORDER_THIN);
			        style3.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderTop(CellStyle.BORDER_THIN);
			        style3.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        


			        my_sheet.createRow(getSetCEF.getContadorPosicao()-1);

			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(1);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(1).setCellValue(getSetCEF.getAutor());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(1).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(2);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(2).setCellValue(getSetCEF.getReu());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(2).setCellStyle(style2);
		        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(3);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(3).setCellValue(getSetCEF.getCNPJ());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(3).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(4);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(4).setCellValue(getSetCEF.getProcesso());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(4).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(5);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(5).setCellValue(getSetCEF.getVara());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(5).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(6);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(6).setCellValue(getSetCEF.getComarca());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(6).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(7);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(7).setCellValue(getSetCEF.getEstado());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(7).setCellStyle(style2);

			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(8);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(8).setCellValue("Recursal");
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(8).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(9);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(9).setCellValue(getSetCEF.getDataDeposito());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(9).setCellStyle(data);

			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(10);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(10).setCellValue(getSetCEF.getValorOriginal());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(10).setCellStyle(style1);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(10).setCellType(XSSFCell.CELL_TYPE_STRING);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(11);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(11).setCellValue(getSetCEF.getValorAtualizado());			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(11).setCellStyle(style1);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(11).setCellType(XSSFCell.CELL_TYPE_STRING);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(12);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(12).setCellValue(getSetCEF.getContaJuridica());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(12).setCellStyle(style3);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(12).setCellType(XSSFCell.CELL_TYPE_STRING);
			        
//			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(13);
//			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(13).setCellValue(getSetCEF.getParcela());			        
//			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(13).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(13).setCellType(XSSFCell.CELL_TYPE_STRING);

			        FileOutputStream outputStream = new FileOutputStream(new File(excelBB));
			        a.write(outputStream);
			        outputStream.close();//Close in finally if possible
			        outputStream = null;
			        
		        }catch(Exception e){
		        		System.out.println("3 -  EscreverContaNovaComValor GRAVAR NA LINHA EROOO");
		        		System.out.println(" ERRO: " + e);
		        }
			}	
		  		 
		  
		  public static void EscreverContaNovaValorZero() throws IOException {
			  try{
				  	fo = new File(excelBB);
			        XSSFWorkbook a = new XSSFWorkbook(new FileInputStream(fo));
			        
			        XSSFSheet my_sheet = a.getSheetAt(0);
			        
			        
			        System.out.println("4 -  EscreverContaNovaValorZero GRAVAR NA LINHA :  " + getSetCEF.getContadorPosicao());
			        

			        //Direita Cor Azul Claro
			        XSSFCellStyle style1 = a.createCellStyle();
			        //style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(9, 232, 5)));
			        style1.setAlignment ( XSSFCellStyle.ALIGN_RIGHT ) ; 
//			        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			        style1.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderLeft(CellStyle.BORDER_THIN);
//			        style1.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderRight(CellStyle.BORDER_THIN);
//			        style1.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style1.setBorderTop(CellStyle.BORDER_THIN);
//			        style1.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        //Centro Azul Claro
			        XSSFCellStyle style2 = a.createCellStyle();
			       // style2.setFillForegroundColor(new XSSFColor(new java.awt.Color(9, 232, 5)));
			        style2.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
//			        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			        style2.setBorderBottom(CellStyle.BORDER_THIN);
//			        style2.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderLeft(CellStyle.BORDER_THIN);
//			        style2.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderRight(CellStyle.BORDER_THIN);
//			        style2.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        style2.setBorderTop(CellStyle.BORDER_THIN);
//			        style2.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        
			        //Centro Com data Azul
			        CreationHelper createHelper = a.getCreationHelper();
			        XSSFCellStyle data = a.createCellStyle();
			        //data.setFillForegroundColor(new XSSFColor(new java.awt.Color(9, 232, 5)));
			        data.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
//			        data.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        data.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));
//			        data.setBorderLeft(CellStyle.BORDER_THIN);
//			        data.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        data.setBorderRight(CellStyle.BORDER_THIN);
//			        data.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
//			        data.setBorderTop(CellStyle.BORDER_THIN);
//			        data.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			        
			        //Centro Azul Claro
			        XSSFCellStyle style3 = a.createCellStyle();
			        style3.setFillForegroundColor(new XSSFColor(new java.awt.Color(9, 232, 5)));
			        style3.setAlignment ( XSSFCellStyle.ALIGN_CENTER ) ; 
			        style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
			        style3.setBorderBottom(CellStyle.BORDER_THIN);
			        style3.setBottomBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderLeft(CellStyle.BORDER_THIN);
			        style3.setLeftBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderRight(CellStyle.BORDER_THIN);
			        style3.setRightBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        style3.setBorderTop(CellStyle.BORDER_THIN);
			        style3.setTopBorderColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			        
			       
			        my_sheet.createRow(getSetCEF.getContadorPosicao()-1);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(1);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(1).setCellValue(getSetCEF.getAutor());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(1).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(2);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(2).setCellValue(getSetCEF.getReu());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(2).setCellStyle(style2);
		        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(3);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(3).setCellValue(getSetCEF.getCNPJ());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(3).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(4);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(4).setCellValue(getSetCEF.getProcesso());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(4).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(5);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(5).setCellValue(getSetCEF.getVara());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(5).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(6);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(6).setCellValue(getSetCEF.getComarca());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(6).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(7);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(7).setCellValue(getSetCEF.getEstado());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(7).setCellStyle(style2);

			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(8);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(8).setCellValue("Recursal");
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(8).setCellStyle(style2);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(9);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(9).setCellValue(getSetCEF.getDataDeposito());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(9).setCellStyle(data);

			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(10);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(10).setCellValue(getSetCEF.getValorOriginal());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(10).setCellStyle(style1);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(10).setCellType(XSSFCell.CELL_TYPE_STRING);
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(11);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(11).setCellValue(getSetCEF.getValorAtualizado());			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(11).setCellStyle(style1);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(11).setCellType(XSSFCell.CELL_TYPE_STRING);
			        
			        
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(12);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(12).setCellValue(getSetCEF.getContaJuridica());
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(12).setCellStyle(style3);
			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(12).setCellType(XSSFCell.CELL_TYPE_STRING);
			        
//			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).createCell(13);
//			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(13).setCellValue(getSetCEF.getParcela());			        
//			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(13).setCellStyle(style2);
//			        my_sheet.getRow(getSetCEF.getContadorPosicao()-1).getCell(13).setCellType(XSSFCell.CELL_TYPE_STRING);

			        
			        FileOutputStream outputStream = new FileOutputStream(new File(excelBB));
			        a.write(outputStream);
			        outputStream.close();//Close in finally if possible
			        
			        outputStream = null;
  
			        
		        }catch(Exception e){
		        		System.out.println("4-  EscreverContaNovaComValor GRAVAR NA LINHA EROOO");
		        		System.out.println(" ERRO: " + e);
		        }
			}

	  
		  
}