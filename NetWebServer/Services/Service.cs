using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    [Rest.Service("contacts")]
    public class Service
    {
        private static ContactsRepository repository = new ContactsRepository();

        [Rest.Get]
        public static Contact[] Fetch()
        {
            return repository.Fetch();
        }

        [Rest.Post]
        public static Contact Add(Contact contact)
        {
            return repository.Add(contact);
        }

        [Rest.Put]
        public static void Change(Contact contact)
        {
            repository.Change(contact);
        }

        [Rest.Delete]
        public static void Remove(int id)
        {
            repository.Remove(id);
        }
    }
}
