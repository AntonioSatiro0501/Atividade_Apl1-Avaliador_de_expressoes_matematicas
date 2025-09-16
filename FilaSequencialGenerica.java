public class FilaSequencialGenerica <T> {

    // atributos
    private static int Tamanho = 100; 
    private int inicio = 0, fim = 0;
    private T e[ ];

    // inicializador
    public FilaSequencialGenerica(){
        e = (T[]) new Object[Tamanho];
    }

    // verifica se a fila esta vazia
    public boolean qIsEmpty() {
		return (this.inicio == this.fim);	
	}

    // verifica se a fila esta cheia
    public boolean qIsFull() {
    	return (this.fim == Tamanho);
    }

    // adiciona valor a fila caso nao esteja cheia
    public void enqueue(T e) throws Exception {
		if (! qIsFull( )){
			    this.e[this.fim++] = e;
		}
		else 
			throw new Exception("Oveflow - Estouro de Fila");	
	}
    
    // remove primeiro valor da fila
    public T dequeue() throws Exception {
    	  if (! qIsEmpty( )){
    		  return this.e[ this.inicio++];
    	  }else{
    		  throw new Exception("underflow - Esvaziamento de Fila");
    	  }
    }

    // retorna valor na primeira posicao da fila
    public T front() throws Exception {
		if (! qIsEmpty())
			return e[inicio];
		else{
			throw new Exception("underflow - Esvaziamento de Fila");
		}			
	}

    // retorna ultimo valor da fila
    public T rear() throws Exception {
		if (! qIsEmpty()){
			  int pfinal = this.fim - 1;
			  return this.e[pfinal];
		}else{
			 throw new Exception("underflow - Esvaziamento de Fila");
		}			
	}

    // retorna total de elementos da fila
    public	int totalElementos(){
		if (!qIsEmpty()) return this.fim - this.inicio;
		else return 0;
	}
}