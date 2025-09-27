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
        boolean nenhuma = true; // Inicialmente, Supõe-se que Nenhuma Variável foi Definida

        for(int i = 0; i < 26; i++){
            if(inicializada[i]){
                nenhuma = false; // Há Pelo menos uma Variável que foi Inicializada
            }
            System.out.printf("%c = %f\n", (char)('A' + i), valor[i]);
        }

        if(nenhuma){ // Se Nenhuma Variável foi Inicializada
            System.out.println("Nenhuma Variável Definida"); // Exibe a Mensagem ao Usuário
        }
    }
}