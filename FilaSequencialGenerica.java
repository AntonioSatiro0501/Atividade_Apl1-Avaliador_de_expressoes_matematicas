public class FilaSequencialGenerica <T> {

    private static int Tamanho = 100; 
    private int inicio = 0, fim = 0;
    private T e[ ];

    public FilaSequencialGenerica(){
        e = (T[]) new Object[Tamanho];
    }

    public boolean qIsEmpty() {
		return (this.inicio == this.fim);	
	}

    public boolean qIsFull() {
    	return (this.fim == Tamanho);
    }

    public void enqueue(T e) throws Exception {
		if (! qIsFull( )){
			    this.e[this.fim++] = e;
		}
		else 
			throw new Exception("Oveflow - Estouro de Fila");	
	}
    
    public T dequeue() throws Exception {
    	  if (! qIsEmpty( )){
    		  return this.e[ this.inicio++];
    	  }else{
    		  throw new Exception("underflow - Esvaziamento de Fila");
    	  }
    }

    public T front() throws Exception {
		if (! qIsEmpty())
			return e[inicio];
		else{
			throw new Exception("underflow - Esvaziamento de Fila");
		}			
	}

    public T rear() throws Exception {
		if (! qIsEmpty()){
			  int pfinal = this.fim - 1;
			  return this.e[pfinal];
		}else{
			 throw new Exception("underflow - Esvaziamento de Fila");
		}			
	}

    public	int totalElementos(){
		if (!qIsEmpty()) return this.fim - this.inicio;
		else return 0;
	}
}