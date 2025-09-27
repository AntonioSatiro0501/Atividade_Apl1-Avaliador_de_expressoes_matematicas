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

public class PilhaGenerica <T>{
    //atributos

    private final static int TAM_DEFAULT = 100;
    private int topoPilha;
    private T e[];

    //contrutores

    public PilhaGenerica( int tamanho){
        e = (T[]) new Object[tamanho];
        topoPilha = -1;
    }
    
    public PilhaGenerica() {
        this(TAM_DEFAULT);
    }

    public boolean isEmpty(){

        return topoPilha == -1;
    }
    
    public boolean isFull(){

        return topoPilha == e.length -1;
    }

    public void push(T e) throws Exception{

        if(!isFull()){
            this.e[++topoPilha] = e;

        }
        else{
            throw new Exception("Overflow - Estouro da Pilha");
        }
    }
    
    public T pop() throws Exception{
        if(!isEmpty()){
            return this.e[this.topoPilha--];
        }
        else{
            throw new Exception("Underflow - Esvaziamento da Pilha");
        }
    }  

    public T topo() throws Exception{

        if(!isEmpty()){
            return this.e[this.topoPilha];
        }
        else{
            throw new Exception("Underflow - Esvaziamento da Pilha");
        }
    }

    public int sizeElements() {
        return topoPilha+1;
    }
    
}
 