using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    internal class ContactsRepository
    {
        private IDictionary<int, Contact> contacts = new Dictionary<int, Contact>();
        private int id = 0;

        public Contact[] Fetch()
        {
            var list = new List<Contact>();
            list.AddRange(contacts.Values);
            return list.ToArray();
        }

        public Contact Add(Contact contact)
        {
            contact.Id = ++id;
            contacts[contact.Id] = contact;
            return contact;
        }

        public void Change(Contact contact)
        {
            if (contact != null && contacts.ContainsKey(contact.Id))
            {
                contacts[contact.Id] = contact;
            }
        }

        public void Remove(int id)
        {
            if (contacts.ContainsKey(id))
            {
                contacts.Remove(id);
            }
        }
    }
}
