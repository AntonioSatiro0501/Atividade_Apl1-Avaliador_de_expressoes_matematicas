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

import java.util.Scanner;

public class Main {
    private static final int LIM = 10; //limite de comandos que podem ser gravados

    //executa uma linha que pode ser atribuicao, expressao, vars ou reset
    private static boolean executarLinha(String linha, CalculadoraPosfixa calc){
        linha = linha.trim();
        if(linha.isEmpty()) return true;

        //comando vars
     if (linha.equalsIgnoreCase("VARS")) { 
      calc.vars(); 
      return true; 
}

        //comando reset
        if (linha.equalsIgnoreCase("RESET")) { 
            calc.reset(); 
            System.out.println("Variáveis reiniciadas."); 
            return true; 
        }

        //atribuicao de variavel
        if (calc.defineValor(linha)) return true;

        //expressao aritmetica
        try{
            //validações extras
            if(!pareceExpressaoOuVariavel(linha)){
                System.out.println("Erro: comando inválido.");
                return true;
            }
            if(temBlocoDeLetras(linha) && !linha.matches("^\\s*[A-Za-z]\\s*$")){
                System.out.println("Erro: expressão inválida.");
                return true;
            }
            if(linha.indexOf('%')>=0){
                System.out.println("Erro: operador inválido.");
                return true;
            }
            if(!CalculadoraPosfixa.isBalanceada(linha)){
                System.out.println("Erro: expressão inválida.");
                return true;
            }
            // imprime todas as variáveis não definidas (se houver) e nao calcula
            if(calc.reportaNaoDefinidas(linha)){
                return true;
            }

            Double r = calc.calcular(linha);
            if(r!=null){
                
                if(r == Math.rint(r)) {
                     System.out.println(r.longValue()); // imprime sem casas decimais
                } else {
                System.out.println(r); // imprime como double normal
                }
            } else {
                System.out.println("Erro: expressão inválida.");
            }
            return true;
        }catch(Exception e){
            System.out.println("Erro: expressão inválida.");
            return false;
        }
    }

    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        CalculadoraPosfixa calc = new CalculadoraPosfixa();
        FilaSequencialGenerica<String> fila = new FilaSequencialGenerica<>();
        
        System.out.println();
        System.out.println("===========================================");
        System.out.println("          CALCULADORA PÓS-FIXA           ");
        System.out.println("===========================================");
        System.out.println("=============Instruções para uso===========");
        System.out.println(" 1. Use variáveis de A a Z e atribua valor");
        System.out.println(" 2. Faça expressões como (A + B) * A");
        System.out.println(" 3. Operadores suportados: +  -  *  /  ^ ( )");
        System.out.println("=============Comandos disponíveis==========");
        System.out.println("    VARS   -> lista variáveis definidas");
        System.out.println("    RESET  -> reinicia as variáveis");
        System.out.println("    REC    -> inicia gravação (max. 10)");
        System.out.println("    STOP   -> encerra gravação");
        System.out.println("    PLAY   -> executa gravação");
        System.out.println("    ERASE  -> apaga gravação");
        System.out.println("    EXIT   -> encerra o programa");
        System.out.println("==========================================");
        System.out.println();

        boolean gravando = false; //indica se está em modo rec
        int rec = 0;              //contador de comandos gravados

        while(true){
            System.out.print("> ");
            if(!in.hasNextLine()) break;
            String s = in.nextLine().trim();

            //se nao esta gravando
            if(!gravando){
                //comando exit
                if(s.equalsIgnoreCase("EXIT")) break;

                //comando rec
                if(s.equalsIgnoreCase("REC")){
                    gravando = true; rec = 0;
                    System.out.println("Iniciando gravação... (REC: 0/"+LIM+")");
                    continue;
                }

                //comando stop fora do rec nao faz nada
                if(s.equalsIgnoreCase("STOP")){ 
                    //sem saída específica
                    continue; 
                }

                //comando erase
                if(s.equalsIgnoreCase("ERASE")){
                    fila = new FilaSequencialGenerica<>();
                    System.out.println("Gravação apagada.");
                    continue;
                }

                //comando play
                if(s.equalsIgnoreCase("PLAY")){
                    if(fila.qIsEmpty()){ 
                        System.out.println("Não há gravação para ser reproduzida."); 
                        continue; 
                    }
                    System.out.println("Reproduzindo gravação...");
                    try{
                        while(!fila.qIsEmpty()){
                            String cmd = fila.dequeue();

                            boolean eAtrib = cmd.matches("^\\s*[A-Za-z]\\s*=.*$");
                            boolean eVars = cmd.equalsIgnoreCase("VARS");
                            boolean eReset = cmd.equalsIgnoreCase("RESET");
                            boolean eExprOuVar = pareceExpressaoOuVariavel(cmd);

                            //dentro do play, se for expressão nem atribuição/vars/reset, ecoa a linha antes do calculo
                            if(eExprOuVar && !eAtrib && !eVars && !eReset){
                                System.out.println(cmd);
                            }

                            //dentro do play, comandos proibidos são rejeitados
                            if(cmd.equalsIgnoreCase("REC")||cmd.equalsIgnoreCase("PLAY")||
                               cmd.equalsIgnoreCase("STOP")||cmd.equalsIgnoreCase("ERASE")||
                               cmd.equalsIgnoreCase("EXIT")){
                                System.out.println("Comando inválido em PLAY: "+cmd);
                                continue;
                            }

                            //executa atribuicao, expressao, vars ou reset
                            executarLinha(cmd, calc);
                        }
                        // exemplo não imprime conclusão específica
                    }catch(Exception e){
                        // mensagens específicas já saem dentro de executarLinha
                    }
                    continue;
                }

                //se não for comando especial, trata como atribuicao ou expressao
                executarLinha(s, calc);
            }else{
                //em modo rec
                if(s.equalsIgnoreCase("STOP")){
                    gravando = false;
                    System.out.println("Encerrando gravação... (REC: "+rec+"/"+LIM+")");
                    continue;
                }

                //comandos proibidos durante rec
                if(s.equalsIgnoreCase("REC")||s.equalsIgnoreCase("PLAY")||
                   s.equalsIgnoreCase("ERASE")||s.equalsIgnoreCase("EXIT")){
                    System.out.println("Erro: comando inválido para gravação.");
                    continue;
                }

                //só podem ser gravados: atribuicao, expressao, vars ou reset
                if(rec < LIM){
                    fila.enqueue(s);
                    rec++;
                    System.out.println("(REC: "+rec+"/"+LIM+") "+s);
                    if(rec==LIM){
                        gravando = false;
                        System.out.println("Encerrando gravação... (REC: "+rec+"/"+LIM+")");
                    }
                }else{
                    gravando = false;
                    System.out.println("Encerrando gravação... (REC: "+rec+"/"+LIM+")");
                }
            }
        }
    }

    //auxiliares de verificacao
    private static boolean pareceExpressaoOuVariavel(String s){
        if(s.matches("^\\s*[A-Za-z]\\s*$")) return true; //variavel simples
        return s.matches("^[A-Za-z\\s\\(\\)\\+\\-\\*/\\^]+$");
    }

    private static boolean temBlocoDeLetras(String s){
        return s.matches(".*[A-Za-z]{2,}.*");
    }
}
