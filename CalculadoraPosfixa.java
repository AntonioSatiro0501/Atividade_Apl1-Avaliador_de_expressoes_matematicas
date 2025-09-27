public class CalculadoraPosfixa {

// atributos
private BibliotecaVariaveis biblioteca;
private char[] operacoes;
private char[] variaveis;

// construtor
public CalculadoraPosfixa() {
    biblioteca = new BibliotecaVariaveis();
    operacoes = new char[]{'+', '-', '*', '^', '/'}; // somente operadores suportados
    variaveis = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
}

// transforma entrada infixa em posfixa
public String infixoEmPosfixo(String expressao) throws Exception{

    PilhaGenerica<Character> pilha = new PilhaGenerica<Character>();
    int tamanho = expressao.length();
    String saida = "";
    int varreExpressao = 0;

    if(isBalanceada(expressao)){
        while(varreExpressao < tamanho){

            char caracterAtual = expressao.charAt(varreExpressao);

            // caso caracterAtual seja uma operação
            if(estaPresente(caracterAtual, operacoes)){
                
                // caso pilha esteja vazia
                if(pilha.isEmpty()){
                    pilha.push(caracterAtual);
                }

                // caso topo da pilha seja uma operação
                else if(estaPresente(pilha.topo(), operacoes)){
                    // ^ é direita-associativo, desempilha apenas enquanto prioridade(top) > prioridade(novo)
                    boolean right = (caracterAtual == '^');
                    while(!pilha.isEmpty() && estaPresente(pilha.topo(), operacoes)){
                        char t = pilha.topo();
                        int pc = prioridade(caracterAtual), pt = prioridade(t);
                        boolean devePop = right ? (pt > pc) : (pt >= pc);
                        if(!devePop) break;
                        saida = saida + pilha.pop();
                    }
                    pilha.push(caracterAtual);
                }

                // adiciona operação caso topo da pilha igual inicio de parenteses
                else if(pilha.topo() == '('){
                    pilha.push(caracterAtual);
                }
            }

            // caso caracter atual seja uma variável
            if(estaPresente(Character.toUpperCase(caracterAtual), variaveis)){
                saida = saida + caracterAtual;
            }

            // caso caracterAtaul seja um espaço
            if(caracterAtual == ' '){
                varreExpressao += 1;
                continue;
            }

            // adiciona entrada de parenteses na pilha
            if(caracterAtual == '('){
                pilha.push(caracterAtual);
            }

            // adiciona à saída todas as opações dentro do parênteses
            if(caracterAtual == ')'){
                char procuraParenteses = ' ';
                while(procuraParenteses != '('){
                    char compara = pilha.pop();
                    if(compara != '('){
                        saida = saida + compara;
                    }
                    procuraParenteses = compara;
                } 

            }

            varreExpressao ++;
        }

        // esvazia a pilha; se sobrar '(', é erro
        while(!pilha.isEmpty()){
            char t = pilha.pop();
            if(t=='(') throw new Exception(erro());
            saida = saida + t;
        }

        return saida;
    }else{
        throw new Exception(erro());
    }
}

public boolean defineValor(String expressao){

    int tamanho = expressao.length();
    int varreExpressao = 0;
    
    while(varreExpressao < tamanho){

        char caracterAtual = expressao.charAt(varreExpressao);

        if(estaPresente(Character.toUpperCase(caracterAtual), variaveis) && !biblioteca.isInicializada(caracterAtual)){

            // procura caracter '='
            int procuraIgual = varreExpressao + 1;
            while(procuraIgual < tamanho){
                char caracterIgual = expressao.charAt(procuraIgual);

                // procura valor da variável
                if(caracterIgual == '='){
                    try{
                        String expressaoParaValor = expressao.replaceAll("[^0-9.]", "");
                        double caracterValor = Double.parseDouble(expressaoParaValor);
                        biblioteca.setValor(caracterAtual, caracterValor);
                        System.out.println(variavelDefinida(caracterAtual));
                        return true;
                    }
                    catch (NumberFormatException e){
                        return false;
                    } 

                }
                procuraIgual++;
            }
            return false;
        }
        varreExpressao++;
    }
    return false;
}

// checa se a expressão digitada tem balanceamento de parenteses
public static boolean isBalanceada(String exp) throws Exception{
    PilhaGenerica<Character> pilha2 = new PilhaGenerica<Character>();
    char c;

    for (int i = 0; i < exp.length(); i++){
        c = exp.charAt(i);
        if (c == '('){
            pilha2.push(c);
        }
        else if (c == ')'){
            if( pilha2.isEmpty()){
                return false;
            }
            pilha2.pop();
        }
    }
    return pilha2.isEmpty();
}

// retorna erro padrão
public String erro(){
    return "Erro: comando inválido";
}

// print de quando variável é definida
public String variavelDefinida(char letra){
    return (letra + " = " + biblioteca.getValor(letra));
}

public String variavelNaoDefinida(char letra){
    return "Erro: variável " + letra + " não definida";
}

// checa se o caracter esta presente na lista
public boolean estaPresente(char c, char[] lista){

    for(char varre : lista){
        if (varre == c) {
            return true;
        }
    }
    return false;
}

// retorna a prioridade da operação
public int prioridade(char c){
    switch (c){
        case '+': return 1;
        case '-': return 1;
        case '/': return 2;
        case '*': return 2;
        case '^': return 3;
        default: return 0;
    }
}

//lista variáveis usando a biblioteca
public void vars() {
    biblioteca.mostraVariaveis();
}

//reseta variáveis usando a biblioteca
public void reset() {
    biblioteca.reset();
}

//varre a string e imprime "erro: variável X não definida." para cada variável nao inicializada
public boolean reportaNaoDefinidas(String s){
    boolean faltou = false;
    boolean[] visto = new boolean[26]; // evita repetir mensagens
    for(int i=0;i<s.length();i++){
        char c = Character.toUpperCase(s.charAt(i));
        if(c>='A' && c<='Z'){
            int idx = c - 'A';
            if(!visto[idx]){
                visto[idx] = true;
                if(!biblioteca.isInicializada(c)){
                    System.out.println("Erro: variável " + c + " não definida.");
                    faltou = true; //retorna true se encontrou alguma não definida
                }
            }
        }
    }
    return faltou;
}

//avalia uma expressão ja em pós-fixa usando a sua pilha generica
private Double avaliarPosfixa(String pos) throws Exception{
    if(pos==null) return null;
    PilhaGenerica<Double> p = new PilhaGenerica<Double>();
    for(int i=0;i<pos.length();i++){
        char c = pos.charAt(i);
        if(c==' ') continue;
        char u = Character.toUpperCase(c);
        if(u>='A' && u<='Z'){
            if(!biblioteca.isInicializada(u)) return null;
            p.push(biblioteca.getValor(u));
        }else if(estaPresente(c, operacoes)){
            double b = p.pop();
            double a = p.pop();
            double r;
            switch(c){
                case '+': r=a+b; break;
                case '-': r=a-b; break;
                case '*': r=a*b; break;
                case '/': r=a/b; break;
                case '^': r=Math.pow(a,b); break;
                default: throw new Exception(erro());
            }
            p.push(r);
        }else{
            throw new Exception(erro());
        }
    }
    return p.pop();
}

//expoe calculo completo como infixa -> pós-fixa -> resultado
public Double calcular(String infixa) throws Exception{
    String pos = infixoEmPosfixo(infixa);
    return avaliarPosfixa(pos);
}

}
