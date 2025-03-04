import java.util.Scanner;

// ricordati di cambaire il nome del file


public class Cioccolateria {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Chiediamo all'utente la quantità di cioccolatini da acquistare
            System.out.print("Inserisci il numero di cioccolatini da acquistare: ");
            String input = scanner.nextLine();

            // Proviamo a convertire la stringa in un intero
            int numeroCioccolatini = Integer.parseInt(input);

            // Verifichiamo che il numero non sia negativo
            if (numeroCioccolatini < 0) {
                throw new Exception("La quantità non può essere negativa!");
            }

            // Calcoliamo un prezzo indicativo, ad esempio 0.50 euro a cioccolatino
            double prezzoUnitario = 0.50;
            double costoTotale = numeroCioccolatini * prezzoUnitario;

            System.out.println("Hai ordinato " + numeroCioccolatini + " cioccolatini.");
            System.out.println("Costo totale: " + costoTotale + " euro");

        } catch (NumberFormatException e) {
            // Viene lanciata se la conversione in intero non riesce
            System.out.println("Errore: devi inserire un numero valido!");
        } catch (Exception e) {
            // Cattura altre eccezioni, come la quantità negativa
            System.out.println("Si è verificato un errore: " + e.getMessage());
        } finally {
            // Blocco opzionale che viene sempre eseguito, anche in caso di eccezioni
            scanner.close();
            System.out.println("Grazie per aver visitato la nostra cioccolateria!");
        }
    }
}
