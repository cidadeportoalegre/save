package br.com.procempa.save.enumerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bridi
 */
public enum Uf {
	 AC, AL, AM, AP, BA, CE, DF, ES, GO,   
	 MA, MG, MS, MT, PA, PB, PE, PI, PR,   
	 RJ, RN, RO, RR, RS, SC, SE, SP, TO, EX;
	  
	 public static Uf[] getValuesUF(){

		 List<Uf> valuesUF = new ArrayList<Uf>();

		 for (Uf uf : Uf.values() ) {
			 if(  uf != Uf.EX ){
				 valuesUF.add( uf );
			 }
		 }
		 
		 return  valuesUF.toArray(  new Uf[valuesUF.size()] );
	 }
}
