package utils;

import java.util.ArrayList;
import java.util.Random;

public class RandomGenerator {
	
	private static Random meuRandom = new Random();
	private static final int LIMITE = 1000;
	private static char[] minusculo = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	//private static char[] maiusculo = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	public static int gerarNumero(){		
		return meuRandom.nextInt(LIMITE);		
	}
	
	public static int gerarNumero(int limite){		
		return meuRandom.nextInt(limite);		
	}
	
	
	public static String gerarTelefone(){
		
		String telefone = "021";
		
		for(int contador = 0; contador < 10; contador++){
			telefone += gerarNumero(9);
		}
		
		return telefone;		
	}
	
	public static String gerarEmail(){
		String email = "";
		
		for(int contador = 0; contador <= 10; contador++){
			if(meuRandom.nextBoolean()){
				int num = gerarNumero(9);
				email += minusculo[num];
			}
			else{
				email += gerarNumero(9);
			}
		}
		email += "@teste.com.br";
		return email;
	}
	
	public static String gerarMatricula(){
		
		String matricula = "2018";
		int contador;
		for(contador = 0; contador < 10; contador++){
			matricula += gerarNumero(9);
		}
		
		return matricula;	
	}
	
	public static boolean jogarMoeda(){
		return meuRandom.nextBoolean();
	}
	
	public static ArrayList<Integer> gerarNumeros(int qnt){
		
		ArrayList<Integer> retorno = new ArrayList<Integer>();
		int contador = 0;
		
		for(contador = 0; contador < qnt; contador++){			
			retorno.add( meuRandom.nextInt(LIMITE)) ;			
		}		
		return retorno;	
	}
	
	

}
