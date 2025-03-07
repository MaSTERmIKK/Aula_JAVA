// Classe Base Persona
class Persona {
    private String nome;
    private String cognome;

    public Persona(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void stampaRuolo() {
        System.out.println("Ruolo: Persona");
    }
}

// Classe derivata Medico
class Medico extends Persona {
    private String specializzazione;

    public Medico(String nome, String cognome, String specializzazione) {
        super(nome, cognome);
        this.specializzazione = specializzazione;
    }

    @Override
    public void stampaRuolo() {
        System.out.println("Ruolo: Medico");
        System.out.println("Specializzazione: " + specializzazione);
    }
}

// Classe derivata Paziente
class Paziente extends Persona {
    private int codicePaziente;

    public Paziente(String nome, String cognome, int codicePaziente) {
        super(nome, cognome);
        this.codicePaziente = codicePaziente;
    }

    @Override
    public void stampaRuolo() {
        System.out.println("Ruolo: Paziente");
        System.out.println("Codice Paziente: " + codicePaziente);
    }
}

// Classe principale Ospedale
public class Ospedale {

    public static void stampaInfoPersona(Persona p) {
        p.stampaRuolo();
        System.out.println("Nome: " + p.getNome());
        System.out.println("Cognome: " + p.getCognome());
        System.out.println();
    }

    public static void main(String[] args) {
        Medico medico = new Medico("Luca", "Bianchi", "Cardiologia");
        Paziente paziente = new Paziente("Marco", "Rossi", 12345);

        stampaInfoPersona(medico);
        stampaInfoPersona(paziente);
    }
}
