package xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ContactMain
{
	private static BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

	private static File file = new File("contact.xml");

	public static void main(String[] args) throws IOException
	{
		ContactOperator operator = new XmlContactOperator(file);

		while (true)
		{
			printTip();
			String choose = reader.readLine().trim();
			switch (choose)
			{
				case "a":
					Contact contact1 = addContactInfoFromConsole();
					if(operator.addContact(contact1))
					{
						System.out.println("Add successfully!");
					}
					else
					{
						System.out.println("Failed to add!");
					}
					System.out.println();
					break;
				case "u":
					Contact contact2 = updateContactInfoFromConsole();
					if (contact2 != null)
					{
						if(operator.updateContact(contact2))
						{
							System.out.println("Update successfully!");
						}
						else
						{
							System.out.println("Failed to update!");
						}
					}
					System.out.println();
					break;
				case "d":
					Contact contact3 = deleteContactInfoFromConsole();
					if (contact3 != null)
					{
						if(operator.deleteContact(contact3))
						{
							System.out.println("Delete successfully!");
						}
						else
						{
							System.out.println("Failed to delete!");
						}
					}
					System.out.println();
					break;
				case "l":
					operator.listAllContact();
					System.out.println();
					break;
				case "q":
					System.out.println("Good Bye!");
					System.exit(0);
				default:
					System.out.println("Illegal input,please re-enter");
			}
		}
	}

	/**
	 * get contact from console, this contact just set id.
	 */
	private static Contact deleteContactInfoFromConsole()
	{
		Contact contact = new Contact();

		try
		{
			String id = null;
			while (true)
			{
				System.out.print("The ID of the contact to be deleted([q] quit): ");
				id = reader.readLine().trim();
				if (id.equals("q"))
				{
					return null;
				}

				System.out.println();
				if (getContact(id) != null)
				{
					break;
				}
				System.out.println("The ID dose not exist!");
			}

			contact.setId(id);
			return contact;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Contact updateContactInfoFromConsole()
	{
		try
		{
			Contact contact = null;
			while (true)
			{
				System.out.print("The ID of contact to be updated([q] quit):");
				String id = reader.readLine().trim();

				if (id.equals("q"))
				{
					return null;
				}

				contact = getContact(id);
				if (contact != null)
				{
					break;
				}
				System.out.println("This ID does not exist!");
			}
			while (true)
			{
				printUpdateTip();
				String choose = reader.readLine().trim();
				switch (choose)
				{
					case "n":
						System.out.print("NAME: ");
						String name = reader.readLine().trim();
						contact.setName(name);
						return contact;
					case "g":
						System.out.print("GENDER: ");
						String gender = reader.readLine().trim();
						contact.setGender(gender);
						return contact;
					case "p":
						System.out.print("PHONE: ");
						String phone = reader.readLine().trim();
						contact.setPhone(phone);
						return contact;
					case "a":
						System.out.print("ADDRESS: ");
						String address = reader.readLine().trim();
						contact.setAddress(address);
						return contact;
					case "e":
						System.out.print("EMAIL: ");
						String email = reader.readLine().trim();
						contact.setEmail(email);
						return contact;
					default:
						System.out.println("Illegal input, please re-enter");
				}
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * print tip of menu.
	 */
	private static void printTip()
	{
		String tip = "**************************\n"
				+ "*          MENU          *\n"
				+ "**************************\n"
				+ "* [a] add a contact.     *\n"
				+ "* [u] update a contact.  *\n"
				+ "* [d] delete a contact.  *\n"
				+ "* [l] list all contacts. *\n"
				+ "* [q] quit.              *\n"
				+ "**************************";
		System.out.println(tip);
		System.out.print("Input: ");
	}

	private static void printUpdateTip()
	{
		String tip = "***************\n"
				+ "*    UPDATE   *\n"
				+ "***************\n"
				+ "* [n] name    *\n"
				+ "* [g] gender  *\n"
				+ "* [p] phone   *\n"
				+ "* [a] address *\n"
				+ "* [e] email   *\n"
				+ "***************";
		System.out.println(tip);
		System.out.print("Input: ");
	}

	private static Contact addContactInfoFromConsole()
	{
		try
		{
			System.out.print("ID: ");
			String id = reader.readLine().trim();
			System.out.print("NAME: ");
			String name = reader.readLine().trim();
			System.out.print("GENDER: ");
			String gender = reader.readLine().trim();
			System.out.print("PHONE: ");
			String phone = reader.readLine().trim();
			System.out.print("ADDRESS: ");
			String address = reader.readLine().trim();
			System.out.print("EMAIL:");
			String email = reader.readLine().trim();

			System.out.println();
			Contact contact = new Contact();
			contact.setId(id);
			contact.setName(name);
			contact.setGender(gender);
			contact.setPhone(phone);
			contact.setAddress(address);
			contact.setEmail(email);

			return contact;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Contact getContact(String id)
	{
		SAXReader reader = new SAXReader();
		try
		{
			Document document = reader.read(file);
			String xpath = "//contact[@id = '" + id + "']";
			Element element = (Element) document.selectSingleNode(xpath);
			if (element == null)
			{
				return null;
			}
			Contact contact = new Contact();
			contact.setId(element.attributeValue("id"));
			contact.setName(element.elementText("name"));
			contact.setGender(element.elementText("gender"));
			contact.setPhone(element.elementText("phone"));
			contact.setAddress(element.elementText("address"));
			contact.setEmail(element.elementText("email"));
			return contact;
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
	}
}
