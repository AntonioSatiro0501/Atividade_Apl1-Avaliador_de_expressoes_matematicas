/*
-------------------------------
Nome: Aline Barbosa Vidal
RA: 10721348
-------------------------------
Nome: Antônio Costa Satiro de Souza
RA: 10723636
-------------------------------
Nome: Fabyani Tiva Yan
RA: 10431835
-------------------------------
*/

public class BibliotecaVariaveis{

    // Atributos
    private boolean[]inicializada;
    private double[]valor;

    // Construtor: Define os Vetores de Atributos com Tamanho 26 (A a Z)
    public BibliotecaVariaveis(){
        inicializada = new boolean[26]; 
        valor = new double[26];
    }

    // Define o Valor da Variável:
    public void setValor(char letra, double val){
        int indice = Character.toUpperCase(letra) - 'A'; // Encontra o Índice do Caractere no Alfabeto
        inicializada[indice] = true; // Variável Inicializada
        valor[indice] = val; // Variável tem Valor val
    }

    // Reinicia o Valor de Todas as Variáveis
    public void reset(){
        // Percorre Todo o Vetor de Alfabeto
        for(int i = 0; i < 26; i++){
            inicializada[i] = false; // Reinicia as Variáveis
            valor[i] = 0; // Define o Valor Padrão para 0
        }

    }
    // Verifica se a Variável foi Inicializada
    public boolean isInicializada(char letra){
        int indice = Character.toUpperCase(letra) - 'A'; // Obtém o Índice da Letra
        return inicializada[indice];
    }

    // Obtém o Valor Definido da Variável
    public double getValor(char letra){
        int indice = Character.toUpperCase(letra) - 'A'; // Obtém o Índice da Letra
        return valor[indice];
    }

    // Mostra Todas as Variáveis Definidas Para o Usuário
public void mostraVariaveis(){
    boolean nenhuma = true; // inicialmente, supõe-se que nenhuma variável foi definida

    for(int i = 0; i < 26; i++){
        if(inicializada[i]){
            nenhuma = false; // há pelo menos uma variável inicializada
            double v = valor[i];
            //imprime como inteiro se não houver parte fracionária
            if (v == Math.rint(v)) {
                System.out.printf("%c = %d%n", (char)('A' + i), (long)v);
            } else {
                System.out.printf("%c = %s%n", (char)('A' + i), String.valueOf(v));
            }
        }
    }

    if(nenhuma){ // se nenhuma variável foi inicializada
        System.out.println("Nenhuma variável definida.");
    }
}
}