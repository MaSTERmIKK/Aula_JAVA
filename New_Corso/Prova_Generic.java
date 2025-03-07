// Classe generica Printer che può stampare qualsiasi tipo di valore
class Printer<T> {
    // Attributo generico che contiene il valore da stampare
    private T valoreDaStampare;

    // Costruttore che inizializza l'attributo con il valore fornito
    public Printer(T valoreDaStampare) {
        this.valoreDaStampare = valoreDaStampare;
    }

    // Metodo per stampare il valore contenuto nell'attributo
    public void print() {
        System.out.println("Valore da stampare: " + valoreDaStampare);
    }
}

// Classe principale con il metodo main
public class Prova_Generic {
    public static void main(String[] args) {
        // Istanza della classe Printer con un intero
        Printer<Integer> printerInt = new Printer<>(100);
        printerInt.print();

        // Istanza della classe Printer con una stringa
        Printer<String> printerString = new Printer<>("Ciao Generics!");
        printerString.print();

        // Istanza della classe Printer con un numero double
        Printer<Double> printerDouble = new Printer<>(50.5);
        printerDouble.print();
    }
}
