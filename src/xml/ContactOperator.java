package xml;

public interface ContactOperator
{
    public boolean addContact(Contact contact);
    
    public boolean updateContact(Contact contact);
    
    public boolean deleteContact(Contact contact);
    
    public void listAllContact();
}
