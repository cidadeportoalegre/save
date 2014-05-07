package br.com.procempa.save.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

public class Util {
    
	/**
	 * Formata o String s com a máscara informada
	 * @param s
	 * @param mascara
	 * @return
	 */
	public static String formata(String s, String mascara) {
		MaskFormatter formatter;
		try {
			formatter = new MaskFormatter(mascara);
			formatter.setValueContainsLiteralCharacters(false);		
			return formatter.valueToString(s);
		} catch (ParseException e) {
			return s;
		}
	}	
	
	public static String formataCnpj(String s) {
		String entr = StringUtils.leftPad(s, 14, "0");
		return formata(entr, "##.###.###/####-##");
	}
	
	public static String formataCpf(String s) {
		String entr = StringUtils.leftPad(s, 11, "0");
		return formata(entr, "###.###.###-##");
	}
	
	public static String formataInscricao(String s) {
		String entr = StringUtils.leftPad(s, 8, "0");				
		return Util.formata(entr, "######-#-#");
		
	}
	
	/**
	 * Formata competência AAAAMM em MM/AAAA
	 * @param competencia
	 * @return
	 */
	public static String formataCompetencia(Integer competencia) {
		
		Calendar calAux = toCalendar(competencia);
 
		SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
		
		return sdf.format( calAux.getTime() );
	}

	public static String validaCpf(String cpf) {
		List<ValidationMessage> validationMessages;	
		
		if (cpf.length() != 11) {
			return "CPF inválido. Para CPF informe 11 dígitos";							
		}
		else{
			CPFValidator cpfValidator = new CPFValidator(false);
			validationMessages = cpfValidator.invalidMessagesFor(cpf);
			if ( ! validationMessages.isEmpty()) {
				return "CPF inválido";				
			}		
		}
		return "";
	}

	/**
	 * Valida o penúltimo dígito da inscrição e o DV
	 * @param inscricao
	 * @return
	 */
	public static String validaInscricao(String inscricao, char tipo) {
		if (inscricao.length() < 2) {
			return "Inscrição inválida";
		}
		
		String penultimo = penultimoDigito(inscricao);
		if ( ! "2".equals(penultimo) && ! "3".equals(penultimo)){
			return "Inscrição inválida";			
		}
		
		if ( "3".equals(penultimo) &&  tipo == 'P'){
			return "Inscrição inválida";			
		}
		
		if (! isDvMod11Ok(inscricao)) {
			return "Inscrição inválida";						
		}
		
		return "";
	}

	public static String penultimoDigito(String inscricao) {
		return StringUtils.substring(inscricao, inscricao.length() -2, inscricao.length() -1);
	}
	
    public static boolean isDvMod11Ok(String inscricao) {    	
		return isDvMod11Ok(StringUtils.substring(inscricao, 0, inscricao.length()-1), 
				StringUtils.substring(inscricao, inscricao.length()-1, inscricao.length()),
				1, 
				9);
	}

	/**

     * Calcula o dígito em Módulo 11 do código dado
     * Retorna o(s) NumDig Dígitos de Controle Módulo 11 do codigo, limitando
     * o valor de multiplicação em qtdLimiteMultiplicacao
     *
     * @param codigo Código cujo dígito se deseja calcular
     * @param qtdDigitosRetorno Quantidade de dígitos verificadores a retornar
     * @param qtdLimiteMultiplicacao Quantidade de dígitos a
     * @return
     */	
	public static String calculaDvMod11(String codigo, int qtdDigitosRetorno, int qtdLimiteMultiplicacao) {
		for(int n=1; n<=qtdDigitosRetorno; n++) {
			char[] digito = codigo.toCharArray();
			int soma = 0;
			int multiplicador = 2;

			for(int i=codigo.length()-1; i>=0; i--) {
				soma += (multiplicador * Integer.parseInt(String.valueOf(digito[i])));
				if(++multiplicador > qtdLimiteMultiplicacao) {
					multiplicador = 2;
				}
			}

			codigo += Integer.valueOf(((soma * 10) % 11) % 10).toString();
		}

		return codigo.substring(codigo.length()-qtdDigitosRetorno);

	}
	
    /**
     * Calcula o dígito em Módulo 10 do código dado
     * @param codigo codigo do qual se deseja obter o dígito verificador (sem dígito)
     * @return o dígito verificador
     */

	public static String calculaDvMod10(String codigo) {

		char[] digito = codigo.toCharArray();
		int multiplicador = 2;
		String stringAux = "";

		for (int i=codigo.length()-1; i>=0; i--) {

			stringAux = ( multiplicador * Integer.parseInt( String.valueOf(digito[i]) ) ) + stringAux;

			if (--multiplicador<1) {

				multiplicador = 2;

			}

		}

		int soma = 0;

		digito = stringAux.toCharArray();

		for (int i=0; i<stringAux.length(); i++) {

			soma = soma + Integer.parseInt(String.valueOf(digito[i]));

		}

		soma = soma % 10;

		if (soma != 0) {

			soma = 10 - soma;

		}
		return Integer.valueOf(soma).toString();
	}
	/**
	 * Verifica se o dígito é correto para o código fornecido
	 * @param codigo Código sem dígito
	 * @param digito Dígito que se deseja verificar
	 * @param qtdDigitosRetorno Quantidade de dígitos verificadores esperados
	 * @param qtdLimiteMultiplicacao Quantidade de dígitos para multiplicar
	 * @return true ou false
	 */	
	public static boolean isDvMod11Ok(String codigo, String digito, int qtdDigitosRetorno, int qtdLimiteMultiplicacao) {
		return calculaDvMod11(codigo, qtdDigitosRetorno, qtdLimiteMultiplicacao).equals(digito);
	}

	/**
	 * transforma Integer no formato YYYYMM em Calendar com 01 no dia
	 * @param competencia
	 * @return
	 */
	public static Calendar toCalendar(Integer competencia) {
		String cpt = competencia.toString();
		Calendar calendar = DateUtils.truncate(Calendar.getInstance(), Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, Integer.parseInt(StringUtils.substring(cpt, 4, 6)) -1);
		calendar.set(Calendar.YEAR, Integer.parseInt(StringUtils.substring(cpt, 0, 4)));
		
		return calendar;
	}

	/**
	 * transforma String no formato YYYYMMDD em Calendar
	 * @param data
	 * @param lenient - levanta exceção caso a data seja inválida
	 * @return null caso string vazio
	 */
	public static Calendar stringToCalendar(String data, boolean lenient) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		Calendar calendar = DateUtils.truncate(Calendar.getInstance(), Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(StringUtils.substring(data, 6, 8)));
		calendar.set(Calendar.MONTH, Integer.parseInt(StringUtils.substring(data, 4, 6)) -1);
		calendar.set(Calendar.YEAR, Integer.parseInt(StringUtils.substring(data, 0, 4)));
		calendar.setLenient(lenient);
		return calendar;
	}
	
	/**
	 * transforma String no formato YYYYMMDD em Date
	 * @param data
	 * @param lenient - levanta exceção caso a data seja inválida
	 * @return null caso string vazio
	 */
	public static Date stringToDate(String data, boolean lenient) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		return stringToCalendar(data, lenient).getTime();
	}
	
	/**
	 * Calcula a competência imediatamente anterior
	 * @param competencia
	 * @return
	 */
	public static Integer calculaCompetenciaAnterior(Integer competencia) {
		String cpt = competencia.toString();
		String ano = StringUtils.substring(cpt, 0, 4);
		String mes = StringUtils.substring(cpt, 4, 6);
		if ("01".equals(mes)) {
			mes = "12";
			ano =  "" + (Integer.parseInt(ano) -1);
		} else {
			mes =  StringUtils.leftPad("" + (Integer.parseInt(mes) -1), 2, "0"); 
		}
		
		return Integer.valueOf(ano + mes);
	}

	/**
	 * Calcula a próxima competência
	 * @param competencia
	 * @return
	 */
	public static Integer calculaProximaCompetencia(Integer competencia) {
		String cpt = competencia.toString();
		String ano = StringUtils.substring(cpt, 0, 4);
		String mes = StringUtils.substring(cpt, 4, 6);
		if ("12".equals(mes)) {
			mes = "01";
			ano =  "" + (Integer.parseInt(ano) +1);
		} else {
			mes =  StringUtils.leftPad("" + (Integer.parseInt(mes) +1), 2, "0"); 
		}
		
		return Integer.valueOf(ano + mes);
	}	
	
	/**
	 * Verifica se a dataDocumento é anterior a competência informada
	 * @param dataDocumento
	 * @param competencia
	 * @return
	 */
	public static boolean isAntesCompetencia(Date dataDocumento, Integer competencia) {
		Date dataDoc = DateUtils.truncate(dataDocumento, Calendar.DAY_OF_MONTH);
		Calendar compet = toCalendar(competencia);
		return dataDoc.before(compet.getTime());
	}

	/**
	 * Verifica se a dataDocumento é posterior a competência informada
	 * @param dataDocumento
	 * @param competencia
	 * @return
	 */
	public static boolean isDepoisCompetencia(Date dataDocumento,
			Integer competencia) {
		Date dataDoc = DateUtils.setDays(dataDocumento,1);
		dataDoc = DateUtils.truncate(dataDoc, Calendar.DAY_OF_MONTH);
		Calendar compet = toCalendar(competencia);
		//compet.set(Calendar.MONTH, Calendar.JANUARY);
		
		return isData1MaiorIgualData2(dataDoc, compet.getTime() );
//		compet.add(Calendar.DAY_OF_MONTH, -1); //último dia do mês da competência
//		return dataDoc.after(compet.getTime());
	}

	/**
	 * Cria competência no formato AAAAMM
	 * @param ano
	 * @param mes
	 * @return
	 */
	public static Integer toCompetencia(String ano, String mes) {
		return Integer.valueOf((mes.length() == 1 ? ano + "0" + mes : ano + mes));
	}

	/**
	 * Cria competência no formato AAAAMM
	 * @param cal
	 * @return
	 */
	public static Integer toCompetencia(Calendar cal) {
		return Integer.valueOf(getYear(cal) * 100 + getMonth(cal));		
	}	
	
	/**
	 * Retorna valor com scale = 2 e RoundingMode.FLOOR
	 * @param valor
	 * @return
	 */
	public static BigDecimal scale2(BigDecimal valor) {
		if (null != valor)
			return valor.setScale(2, RoundingMode.FLOOR);
		else
			return valor;		
	}

	/**
	 * Retorna valor com scale = 4 e RoundingMode.FLOOR
	 * @param valor
	 * @return
	 */
	public static BigDecimal scale4(BigDecimal valor) {
		if (null != valor)
			return valor.setScale(4, RoundingMode.FLOOR);
		else
			return valor;		
	}

	/**
	 * @param calendar
	 * @return Calendar com o primeiro dia do mês para a data informada
	 */
	public static Calendar primeiroDiaMes(Calendar calendarOriginal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendarOriginal.getTime());
		calendar.setLenient(false);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}

	public static Integer dataToInteger(Date data ){
		if( null == data ) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.valueOf( sdf.format( data ) );
	}
	
	/**
	 * @param ano
	 * @param mes
	 * @return Data com primeiro dia do mês para ano/mês informado
	 */
	public static Date primeiroDiaMes(String ano, String mes) {
		int anoI = Integer.parseInt(ano);
		int mesI = Integer.parseInt(mes);
		return primeiroDiaMes(anoI, mesI);
	}

	/**
	 * @param ano
	 * @param mes
	 * @return Data com primeiro dia do mês para ano/mês informado
	 */
	public static Date primeiroDiaMes(int ano, int mes) {
		Calendar cal = DateUtils.truncate(Calendar.getInstance(), Calendar.DAY_OF_MONTH );
		cal.setLenient(false);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, mes - 1);
		cal.set(Calendar.YEAR, ano);
		return DateUtils.truncate( cal, Calendar.DAY_OF_MONTH).getTime();
	}

	/**
	 * @param calendar
	 * @return Calendar com o último dia do mês para a data informada
	 */
	public static Calendar ultimoDiaMes(Calendar calendarOriginal){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendarOriginal.getTime());
		calendar.setLenient(false);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar;
	}


	/**
	 * @param calendar
	 * @return Calendar com o primeiro dia do ano para a data informada
	 */
	public static Calendar primeiroDiaAno(Calendar calendarOriginal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendarOriginal.getTime());
		calendar.setLenient(false);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		return calendar;
	}

	/**
	 * @param calendar
	 * @return Caledar com o último dia do ano para a data informada
	 */
	public static Calendar ultimoDiaAno(Calendar calendarOriginal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendarOriginal.getTime());
		calendar.setLenient(false);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		return calendar;
	}
	
	

	/**
	 * @param calendar
	 * @return Caledar com o último dia do ano para a data informada
	 */
	public static Calendar ultimoDiaUtilDoAno() {
		Calendar calendar = Calendar.getInstance();
		//calendar.setTime(calendarOriginal.getTime());
		calendar.setLenient(false);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
				
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		if (dayOfWeek == Calendar.SATURDAY) {
			calendar.add(Calendar.DATE, -1);
		} else if (dayOfWeek == Calendar.SUNDAY) {
				calendar.add(Calendar.DATE, -2);
			} 
		
		return calendar;
	}
 
	/**
	 * @param calendar
	 * @return Calendar com o último dia do ano anterior
	 */
	public static Calendar ultimoDiaAnoAnterior(Calendar calendarOriginal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendarOriginal.getTime());
        calendar = ultimoDiaAno(calendar);
		calendar.add(Calendar.YEAR, -1);
		return calendar;
	}

	/** transforma string AAAAMMDD em Date
	 * @param aaaammdd
	 * @return Data a partir do string informado
	 */
	public static Date toDate(String aaaammdd) {
		if ("0".equals(aaaammdd) ||
            StringUtils.isEmpty(aaaammdd)){
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setLenient(false);

		int dia = Integer.parseInt( StringUtils.substring(aaaammdd, 6, 8 ) );
		int mes = Integer.parseInt( StringUtils.substring(aaaammdd, 4, 6 ) );
		int ano = Integer.parseInt( StringUtils.substring(aaaammdd, 0, 4 ) );

		cal.set(Calendar.DAY_OF_MONTH, dia);
		cal.set(Calendar.MONTH, mes - 1);
		cal.set(Calendar.YEAR, ano);
		return cal.getTime();
	}

	/**
	 * @param calendar
	 * @return int com o ano do Calendar passado
	 */
	public static int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}
 
	/**
	 * @param calendar
	 * @return int com o mês do Calendar passado
	 */
	public static int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONTH) + 1;
	}
 
	/**
	 * @param calendar
	 * @return int com o dia do Calendar passado
	 */
	public static int getDay(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
 
	/**
	 * @param calendar
	 * @return true caso seja final de semana (Sábado ou Domingo)
	 */
	public static boolean isFinalDeSemana(Calendar calendar) {
		 int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		 return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
	}

	/**
	 * @param data
	 * @return true caso seja final de semana (Sábado ou Domingo)
	 */
	public static boolean isFinalDeSemana(Date data) { 
		Calendar dataCal =Calendar.getInstance();
		dataCal.setTime(data);
		return isFinalDeSemana( dataCal );
	}

    /**
     * @param calendar
     * @return Long no formato AAAAMMDD
     */
    public static Long toLong(Calendar calendar) {
        String ano = "" + getYear(calendar);
        String mes = "" + getMonth(calendar);
        if (mes.length() == 1) {
            mes = "0" + mes;
        }
        String dia = "" + getDay(calendar);
        if (dia.length() == 1){
            dia = "0" + dia;
        }

        return Long.valueOf(ano + mes + dia);
    }

    /**
     * Valida uma data informada no formato DDMMAAAA
     * @param DDMMAAAA
     * @return
     */
    public static boolean ValidateDMA(String DDMMAAAA) {
    	Calendar data = Calendar.getInstance();
    	int dia = Integer.parseInt(DDMMAAAA.substring(0, 2));
    	int mes = Integer.parseInt(DDMMAAAA.substring(2, 4))-1;
    	int ano = Integer.parseInt(DDMMAAAA.substring(4));
    	data.set(ano, mes, dia);
    	return data.get(Calendar.DAY_OF_MONTH)==dia && data.get(Calendar.MONTH)== mes;
    }

    /**
     * Valida uma data informada no formato AAAAMMDD
     * @param AAAAMMDD
     * @return
     */
    public static boolean ValidateAMD(String AAAAMMDD) {
    	Calendar data = Calendar.getInstance();
    	int dia = Integer.parseInt(AAAAMMDD.substring(6));
    	int mes = Integer.parseInt(AAAAMMDD.substring(4, 6))-1;
    	int ano = Integer.parseInt(AAAAMMDD.substring(0, 4));
    	data.set(ano, mes, dia);
    	return data.get(Calendar.DAY_OF_MONTH)==dia && data.get(Calendar.MONTH)== mes;
    }

    /**
     * Verifica se a Data1 é posterior a Data2
     * @param data1 Data Inicial
     * @param data2 Data Final
     * @return
     */
    public static boolean isData1MaiorData2(Date data1, Date data2) {
    	if (data1 == null || data2 == null) {
    		return false;
    	}
    	Calendar cal1 = Calendar.getInstance();
    	cal1.clear();
    	cal1.setTime(data1);
    	Calendar cal2 = Calendar.getInstance();
    	cal2.clear();
    	cal2.setTime(data2);
    	return cal1.after(cal2);
    }

    /**
     * Verifica se a Data1 é maior ou igual a Data2
     * @param data1 Data Inicial
     * @param data2 Data Final
     * @return
     */
    public static boolean isData1MaiorIgualData2(Date data1, Date data2) {
    	if (data1 == null || data2 == null) {
    		return false;
    	}
    	Calendar cal1 = Calendar.getInstance();
    	cal1.clear();
    	cal1.setTime(data1);

    	Calendar cal2 = Calendar.getInstance();
    	cal2.clear();
    	cal2.setTime(data2);

    	return (toLong( cal1 ).compareTo(  toLong( cal2 ) ) > 0);
    }

	/**
	 * @param data
	 * @return int com o mês do Date passado
	 */
	public static int getMonth(Date data) {
		Calendar dataCal =Calendar.getInstance();
		dataCal.setTime(data);
		return dataCal.get(Calendar.MONTH)+1;
	}

	/**
	 * @param data
	 * @return int com o dia do Date passado
	 */
	public static int getDay(Date data) {
		Calendar dataCal =Calendar.getInstance();
		dataCal.setTime(data);
		return dataCal.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * @param data
	 * @return int com o ano do Date passado
	 */
	public static int getYear(Date data) {
		Calendar dataCal =Calendar.getInstance();
		dataCal.setTime(data);
		return dataCal.get(Calendar.YEAR);
	}
	
	/**
	 * Verifica se a competencia informada é válida para processamento
	 * 	O formato deve ser YYYYMM
	 * 	YYYY deve estar entre 2004 e o ano atual
	 * 	não pode ser menor que 200407
	 * 	MM deve ser entre 01 e 12
	 * @param competencia
	 * @return
	 */
	public static boolean isCompetenciaValidaParaProcessamento(Integer competencia) {
		if (competencia < 200407) {
			return false;
		}
		String cpt = competencia.toString();
		int ano = Integer.parseInt(StringUtils.left(cpt, 4));
		int mes = Integer.parseInt(StringUtils.right(cpt, 2));
		
		int anoAtual = Calendar.getInstance().get(Calendar.YEAR); 

		if (ano > anoAtual) {
			return false;
		}
		
		if (mes < 1 || mes > 12) {
			return false;
		}
		
		return true;
	}
	
	
	public static boolean isUltimoDiaMes(Date data) {
		boolean isUltimo;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		//pega ultimo dia do mes
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		if ( calendar.get(Calendar.DAY_OF_MONTH) == maxDay ){
			isUltimo = true;
		}else{
			isUltimo = false;
		}
		return isUltimo;
	}
	
	
	public static String gerarMD5 (String str) {  
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");  
			BigInteger hash;
			hash = new BigInteger(1, md.digest(str.getBytes("UTF-8")));
			String crypto = hash.toString(16);  
			if (crypto.length() %2 != 0)  
				crypto = "0" + crypto;
			crypto = crypto.toUpperCase();
			String str2 = " ";
			for (int i = 0; i < crypto.length(); i++) {
				str2 = str2 + crypto.charAt(i);
				if (i%2!=0){
					str2 = str2 + " ";
				}
			}
			return str2;  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
