function analizzaNumeri(numeri: number[]): {
  sommaPositivi: number;
  sommaNegativi: number;
  contaPari: number;
  contaDispari: number;
} {
  let sommaPositivi = 0;
  let sommaNegativi = 0;
  let contaPari = 0;
  let contaDispari = 0;

  for (const num of numeri) {
    if (num >= 0) {
      sommaPositivi += num;
    } else {
      sommaNegativi += num;
    }

    if (num % 2 === 0) {
      contaPari++;
    } else {
      contaDispari++;
    }
  }

  return { sommaPositivi, sommaNegativi, contaPari, contaDispari };
}

// Esempio di array da analizzare
const arr = [10, -2, 3, -5, 0, 4, 1, -8, 2, -1];

// Chiamata della funzione e stampa dei risultati
const risultati = analizzaNumeri(arr);
console.log("Somma dei numeri positivi:", risultati.sommaPositivi);
console.log("Somma dei numeri negativi:", risultati.sommaNegativi);
console.log("Conteggio dei numeri pari:", risultati.contaPari);
console.log("Conteggio dei numeri dispari:", risultati.contaDispari);
