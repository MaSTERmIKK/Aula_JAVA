// app.ts
const btn = document.getElementById("salutaBtn") as HTMLButtonElement;
const messaggio = document.getElementById("messaggio") as HTMLParagraphElement;

btn.addEventListener("click", () => {
  messaggio.textContent = "Ciao, benvenuto nel mondo di TypeScript e HTML!";
});