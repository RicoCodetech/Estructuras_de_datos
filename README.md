# Estructuras de datos
En esta práctica de estrategias programación y estructuras de datos, se nos ha encomendado la tarea de implementar la interfaz StockIF.java de dos maneras distintas. La primera consiste en crear una secuencia, que hemos llamado StockSequence.java, y la segunda implica la creación de un árbol general, que hemos nombrado StockTree.java. El material necesario para llevar a cabo esta tarea nos ha sido proporcionado por el equipo docente de la universidad, y hay una restricción importante: no podemos utilizar bibliotecas externas de Java en nuestro trabajo, debemos utilizar únicamente las estructuras que se nos han proporcionado. en la practica se realiza el estudio empirico de los costes con una bateria de pruebas. os presento los metodos a implementar segun la interfaz.

```java

   
   /* Devuelve el valor indexado bajo el indice p.
   * @PRE: p != ""
   * Si no existe un valor indexado bajo el indice p,
   * devuelve el valor -1.
   */
   public int retrieveStock(String p);


   /* Indexa el valor u bajo el indice p.
   * @PRE: p != "" y u >= 0
   * Si ya habia un valor bajo el mismo indice,
   * el nuevo valor substituye al anterior.
   */
   public void updateStock(String p, int u);


   /* Devuelve una secuencia de todos los pares <p,u>
   * presentes en el stock tales que:
   * -El valor indexado bajo el índice p es u
   * -El índice p comienza por la cadena prefix
   * Además, la secuencia deberá estar ordenada según
   * el orden alfabético de los productos
   */
   public SequenceIF<StockPair> listStock(String prefix);


```
Las clases que he desarrollado se encuentran dentro de la carpeta pract2022_2023 a continuacion imprimo el esquema de carpetas del proyecto.

```java
src/
└── es/
    └── uned/
        └── lsi/
            └── eped/
                ├── DataStructures/
                │   ├── BSTree.java
                │   ├── BSTreeIF.java
                │   ├── BTree.java
                │   ├── BTreeIF.java
                │   ├── Collection.java
                │   ├── CollectionIF.java
                │   ├── ContainerIF.java
                │   ├── GTree.java
                │   ├── GTreeIF.java
                │   ├── IteratorIF.java
                │   ├── List.java
                │   ├── ListIF.java
                │   ├── MultiSetIF.java
                │   ├── Queue.java
                │   ├── QueueIF.java
                │   ├── Sequence.java
                │   ├── SequenceIF.java
                │   ├── SetIF.java
                │   ├── Stack.java
                │   ├── StackIF.java
                │   ├── Test.java
                │   ├── Tree.java
                │   └── TreeIF.java
                └── pract2022_2023/
                    ├── Main.java
                    ├── Node.java
                    ├── NodeInfo.java
                    ├── NodeInner.java
                    ├── NodeRoot.java
                    ├── StockIF.java
                    ├── StockPair.java
                    ├── StockSequence.java
                    └── StockTree.java

```
