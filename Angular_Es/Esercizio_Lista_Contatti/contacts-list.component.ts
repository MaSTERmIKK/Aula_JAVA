import { Component } from '@angular/core';

interface Contact {
  name: string;
  phone: string;
  email?: string;
}

@Component({
  selector: 'app-contacts-list',
  templateUrl: './contacts-list.component.html',
  styleUrls: ['./contacts-list.component.css']
})
export class ContactsListComponent {

  // Array di contatti iniziali di esempio
  contacts: Contact[] = [
    { name: 'Mario Rossi', phone: '1234567890', email: 'mario.rossi@example.com' },
    { name: 'Luca Bianchi', phone: '0987654321' }
  ];

  // Modello per un nuovo contatto
  newContact: Contact = {
    name: '',
    phone: '',
    email: ''
  };

  /**
   * Aggiunge un nuovo contatto all'array contacts
   * se i campi richiesti (name, phone) sono compilati.
   */
  addContact(): void {
    // Controllo semplice per evitare di aggiungere contatti vuoti
    if (this.newContact.name && this.newContact.phone) {
      this.contacts.push({
        name: this.newContact.name,
        phone: this.newContact.phone,
        email: this.newContact.email
      });

      // Resetta i campi del form
      this.newContact = { name: '', phone: '', email: '' };
    }
  }

  /**
   * Rimuove un contatto dall'array contacts in base all'indice.
   * @param index L'indice del contatto da rimuovere.
   */
  removeContact(index: number): void {
    this.contacts.splice(index, 1);
  }
}
