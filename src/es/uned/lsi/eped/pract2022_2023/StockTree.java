package es.uned.lsi.eped.pract2022_2023;

import es.uned.lsi.eped.DataStructures.*;

public class StockTree implements StockIF {

	protected GTreeIF<Node> stock; /* El stock es un árbol general de nodos */
	//protected Node Root= new NodeRoot();
	
	/* Constructor de la clase */
	public StockTree() {
		super();
		this.stock=new GTree<Node>();	
	} 
	
	
	
	/*
	 * Seria mas eficiente ordenar la lista en listStock o mantener una lista ordenada???
	 * */
	
	
	/* Devuelve el valor indexado bajo el indice p.
     * @PRE: p != ""
     * Si no existe un valor indexado bajo el indice p,
     * devuelve el valor -1.
     */
	@Override
	public int retrieveStock(String p) {
		
		if(p==null || p.isEmpty()) {
			return -1;
		}
		//contador para recorrer el String
		int contadorString=0;
		
		return retrieveRecursivo(p,contadorString,stock);
	}
	
	
	private int retrieveRecursivo(String p, int contadorString, GTreeIF<Node> arbol) {
		//nuevoValor guarda el numero de unidades de p
		int nuevoValor=-1;
		//encontrado, encuentra o no el caracter buscado del String p
		boolean encontrado=false;
		
		GTreeIF<Node> arbolDeLista = arbol;
			
			if(contadorString<p.length()) {
				
				IteratorIF<GTreeIF<Node>> iterador = arbolDeLista.getChildren().iterator();
				
				while(iterador.hasNext()) {
					GTreeIF<Node> hijo = iterador.getNext();
					
					if(hijo.getRoot() instanceof NodeInner) {
						char c=p.charAt(contadorString);
						NodeInner nod= (NodeInner) hijo.getRoot();
						//hay coincidencia?

						if(nod.getLetter()==c) {
							contadorString++;
							encontrado=true;	
							
							nuevoValor=retrieveRecursivo(p,contadorString,hijo);
							break;
							
						}
					}			
				}
				
				if(!encontrado) {
					return -1;
				}
				
			}else { //Aqui hay que asegurar que sea un nodo hoja
				//return nodo hoja
				
				if(arbol.getChildren().isEmpty()) {
					return -1;
				}
				
				IteratorIF<GTreeIF<Node>> iterador = arbol.getChildren().iterator();
				
				while(iterador.hasNext()) {
				
					GTreeIF<Node> hijo = iterador.getNext();
					if(hijo.getRoot() instanceof NodeInfo) {
						
						//valor = ((NodeInfo) hijo.getRoot()).getUnidades();
						nuevoValor = ((NodeInfo) hijo.getRoot()).getUnidades(); // Asignar el nuevo valor a la variable local
						
						return nuevoValor;
						//return ((NodeInfo) hijo.getRoot()).getUnidades();
					}
				}
				
			}
			
		return nuevoValor;	
	}
	
	
	
	
	/*
	 * Ordena el stock en su inserccion
	 * */
	@Override
	public void updateStock(String p, int u) {
		// Si el producto no esta he de crearlo
		//recorre el String

		
		if(p == null || p.isEmpty() || u < 0) {
			return;
		}
		
		int contadorString=0;
		actualizaStock(p,u,contadorString,stock);
		//updateRecursivo(p,u,contadorString,stock);
				
	}
		
	
	private void actualizaStock(String p, int u, int contadorString, GTreeIF<Node> arbol) {

		//encuentra o no el caracter
		boolean encontrado= false;
		
		if(contadorString <  p.length()) {
			
			IteratorIF<GTreeIF<Node>> iterador = arbol.getChildren().iterator();
			
			while(iterador.hasNext()) {
				GTreeIF<Node> hijo = iterador.getNext();
				
				if(hijo.getRoot() instanceof NodeInner) {
					char c = p.charAt(contadorString);
					if(c == ((NodeInner) hijo.getRoot()).getLetter()) {
						encontrado= true;
						contadorString++;
						actualizaStock(p, u, contadorString, hijo);
						return;
					}
				}
			}
			if(!encontrado) {
				nuevoNodo(arbol, contadorString, p,u);
			}
		}else {
			
			IteratorIF<GTreeIF<Node>> iterador = arbol.getChildren().iterator();

			boolean existeHoja=false;
			while(iterador.hasNext()) {
				GTreeIF<Node> hijo = iterador.getNext();
				if(hijo.isLeaf()) {
					((NodeInfo) hijo.getRoot()).setUnidades(u);
					existeHoja=true;
					break;
				}
			}
			if(!existeHoja){
				
				nuevoNodo(arbol,contadorString, p,u);
			}
		}
	}
	

	
	
	
	/*
	 * arbol de lista es el arbol cuya lista no contiene el char
	 * contador es quien recorre el string y seguira recorriendo desde contador
	 * string palabra a buscar*/
	private void nuevoNodo(GTreeIF<Node> arbolDeLista, int contadorString, String p,int u) {
		GTreeIF<Node> arbolito=new GTree<>();
		
		
		if(p.length()>contadorString) {	
			char c=p.charAt(contadorString);
			
			contadorString=contadorString+1;

			arbolito.setRoot(new NodeInner(c));
			
			int indice = updateIndiceStock(c, arbolDeLista); //AQUI
			
			
			if(arbolDeLista == null || arbolDeLista.isEmpty()) {
				arbolDeLista.addChild(1, arbolito);
			}else {
				arbolDeLista.addChild(indice, arbolito);
			}
			nuevoNodo(arbolito,contadorString,p,u);
			return;
		}
		
			GTreeIF<Node> hijo = new GTree<>();
			hijo.setRoot(new NodeInfo(u));	
			
			if(arbolDeLista == null || arbolDeLista.isEmpty()) {
				arbolDeLista.addChild(1, hijo);
			}else {
				arbolDeLista.addChild(arbolDeLista.getChildren().size() + 1, hijo);
			}
	}
	
	
	
	
	
	private int updateIndiceStock(char letra, GTreeIF<Node> nodoActual) {
		
		IteratorIF<GTreeIF<Node>> iterador = nodoActual.getChildren().iterator();
		int poscicionHijo = 1;
		while(iterador.hasNext()) {
			GTreeIF<Node> hijo = iterador.getNext();
			if(hijo.getRoot() instanceof NodeInner) {
				char letraHijo = ((NodeInner) hijo.getRoot()).getLetter();
				
				if(letraHijo < letra) {
					poscicionHijo++;
				}
				else {
					break;
				}
			}else if(hijo.getRoot() instanceof NodeInfo) {
				poscicionHijo++;
			}
			
		}
		return poscicionHijo;
	}
	
	
	
	
	@Override
	public SequenceIF<StockPair> listStock(String prefix){
		ListIF<StockPair> lista = new List<>();
		GTreeIF<Node> nodoActual = stock;
		
		
		//Este for se puede meter dentro del while como un condicional IF y una variable
		//que aumente en cada iteracion.... NO creo que no...   
		for(int i = 0; i < prefix.length(); i++) {
			char letra = prefix.charAt(i);
			boolean encontrado = false;
			
			IteratorIF<GTreeIF<Node>> iterador = nodoActual.getChildren().iterator();
			
			while(iterador.hasNext()) {
				GTreeIF<Node> hijo = iterador.getNext();
				if(hijo.getRoot() instanceof NodeInner && ((NodeInner) hijo.getRoot()).getLetter() == letra) {
					nodoActual = hijo;
					encontrado = true;
					break;
				}
			}
			if(!encontrado) {
				return lista;
			}
		}
		
		/*if(nodoActual.getRoot() instanceof NodeInfo) {
			lista.insert(lista.size()+1, new StockPair(prefix, ((NodeInfo) nodoActual.getRoot()).getUnidades()));
		}*/
		
		//Hemos encontrado todos los caracteres de prefix
		return listaStockRecursivo(nodoActual,prefix, lista);
		
	}
	
	

	
	
	private ListIF<StockPair> listaStockRecursivo(GTreeIF<Node> nodoActual, String prefix, ListIF<StockPair> lista) {
		IteratorIF<GTreeIF<Node>> iterador = nodoActual.getChildren().iterator();
		
		IteratorIF<GTreeIF<Node>> iterador2 = nodoActual.getChildren().iterator();
		
		String prefix2=prefix;
		
		while(iterador2.hasNext()) {
			GTreeIF<Node> hijo2 = iterador2.getNext();
			
		
			if(hijo2.getRoot() instanceof NodeInfo) {
				//Aqui tendria que ordenar la lista.
				//StockPair nuevoElemento = new StockPair(prefix, ((NodeInfo) hijo2.getRoot()).getUnidades());
				//int indice = getIndice(nuevoElemento, lista);
				
				//lista.insert(indice, nuevoElemento);
				
				//Esta linea de abajo es la que funciona
				lista.insert(lista.size()+1, new StockPair(prefix, ((NodeInfo) hijo2.getRoot()).getUnidades()));
				break;
			}
		}
		
		
		while(iterador.hasNext()) {
			GTreeIF<Node> hijo = iterador.getNext();
			
			if(hijo.getRoot() instanceof NodeInner) {
				listaStockRecursivo(hijo, prefix2 + ((NodeInner) hijo.getRoot()).getLetter(), lista);
			}

		}
		return lista;
	}		

	
	//BUSQUEDA BINARIA
	private int getIndice(StockPair elementoInsertar, ListIF<StockPair> lista) {
		int indiceInferior = 1;
		int indiceSuperior = lista.size();
		//int indiceSuperior = stock.size();

		while (indiceInferior <= indiceSuperior) {
			int indiceMedio = (indiceInferior + indiceSuperior) / 2;
			StockPair elementoComparar = lista.get(indiceMedio);

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
	
	
	
	private int getIndice2(char c, ListIF<GTreeIF<Node>> listIF) {
		int indiceInferior = 1;
		int indiceSuperior = listIF.size();
		//int indiceSuperior = stock.size();

		while (indiceInferior <= indiceSuperior) {
			int indiceMedio = (indiceInferior + indiceSuperior) / 2;
		
			GTreeIF<Node> arbol = (GTreeIF<Node>) listIF.get(indiceMedio);
			
			if(((NodeInner) arbol.getRoot()).getLetter() == c) {
				return indiceMedio;
			}else if(((NodeInner) arbol.getRoot()).getLetter() < c) {
				indiceInferior = indiceMedio + 1;
			}else {
				indiceSuperior = indiceMedio - 1;
			}
		}

		return indiceInferior;
	}
	
	
	
	
}
