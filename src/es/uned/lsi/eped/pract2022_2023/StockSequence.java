package es.uned.lsi.eped.pract2022_2023;


import es.uned.lsi.eped.DataStructures.*;


public class StockSequence implements StockIF {

	protected SequenceIF<StockPair> stock;
	
	StockPair stockPair;
	
	
	
	/* Constructor de la clase */
	public StockSequence() {
		
		stock = new List<StockPair>();
		//StockPair elem=null;
	}


	/*
	 * 	Este casting puedes acceder a todos los metodos de List
		List<StockPair> listaStock = (List<StockPair>) stock;
		Este casting puedes acceder solo al metodo que estas casteando
		((List<StockPair>)stock).insert(1, stockPair);
	
	*/
	
	
	
	@Override
	public int retrieveStock(String p) {
		
		if(stock.isEmpty() || p.isEmpty()) {
			 
			return -1;
			
		}else {
			
			IteratorIF<StockPair> iterador = stock.iterator();
			
			while(iterador.hasNext()) {
				
				StockPair elemento = iterador.getNext();
				
				if(elemento.getProducto().equals(p)) {
				
					return elemento.getUnidades();		
				}	
			}
			
			return -1;
		}
	}

	
	
	
	@Override
	public void updateStock(String p, int u) {
			
		if(stock.isEmpty()) {
			//StockPair producto= new StockPair(p,u);
			((List<StockPair>) stock).insert(1, new StockPair(p,u));
			return;
		}
		
		IteratorIF<StockPair> iterador = stock.iterator();
		boolean encontrado= false;
		
		while(iterador.hasNext()) {
			StockPair elemento=iterador.getNext();
			
			if(elemento.getProducto().equals(p)) {
				//Si encontramos el producto en la lista, lo actualizamos
				elemento.setUnidades(u);
				encontrado= true;
				break;
			}
		}
		
		if(!encontrado) {
			StockPair nuevoElemento = new StockPair(p,u);
			//Busca la posicion en la lista del nuevo elemento
			int i = getIndice(nuevoElemento);
			
			((List<StockPair>) stock).insert(i,nuevoElemento);
		}
				
	}
	
	//BUSQUEDA BINARIA
	private int getIndice(StockPair elementoInsertar) {
		int indiceInferior = 1;
		int indiceSuperior = stock.size();

		while (indiceInferior <= indiceSuperior) {
			int indiceMedio = (indiceInferior + indiceSuperior) / 2;
			StockPair elementoComparar = ((List<StockPair>) stock).get(indiceMedio);

			int resultadoComparacion = elementoComparar.getProducto().compareTo(elementoInsertar.getProducto());
			if (resultadoComparacion == 0) {
				return indiceMedio;
			} else if (resultadoComparacion < 0) {
				indiceInferior = indiceMedio + 1;
			} else {
				indiceSuperior = indiceMedio - 1;
			}
		}

		return indiceInferior;
	}
	
	
	
	@Override
	public SequenceIF<StockPair> listStock(String prefix) {
		
	    ListIF<StockPair> prefijos = new List<StockPair>();
	    
	    if (prefix != null) {
	    	
	        StockPair elemento = null;
	        IteratorIF<StockPair> it = stock.iterator();
	        
	        while (it.hasNext()) {
	        	
	            elemento = it.getNext();

	            if (elemento.getProducto().startsWith(prefix)) {
	            	
	            	//Si la lista esta vacia lo inserta en la posicion 1
	                if (prefijos.isEmpty()) {
	                    prefijos.insert(1, elemento);
	                    
	                } else {
	                  
	                	prefijos.insert(prefijos.size() + 1, elemento);
	                    
	                }
	            }
	        }
	    }

	    return prefijos;
	}
	
	
}
