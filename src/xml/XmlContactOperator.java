package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

public class XmlContactOperator implements ContactOperator
{
	private File file;

	public XmlContactOperator(File file)
	{
		this.file = file;
		initialize(file);
	}

	private void initialize(File file)
	{
		if (!file.exists())
		{
			try
			{
				file.createNewFile();

				Document document = DocumentHelper.createDocument();
				document.addElement("contact-list");

				OutputStream output = new FileOutputStream(file);
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding(Charsets.UTF_8.name());
				XMLWriter writer = new XMLWriter(output, format);

				writer.write(document);
			}
			catch (IOException e)
			{
				throw new RuntimeException();
			}
		}
	}

	@Override
	public boolean addContact(Contact contact)
	{
		SAXReader reader = new SAXReader();
		try
		{
			Document document = reader.read(file);
			Element root = document.getRootElement();
			Element element = root.addElement("contact");
			element.addAttribute("id", contact.getId());

			element.addElement("name").setText(contact.getName());
			element.addElement("gender").setText(contact.getGender());
			element.addElement("phone").setText(contact.getPhone());
			element.addElement("address").setText(contact.getAddress());
			element.addElement("email").setText(contact.getEmail());

			OutputStream output = new FileOutputStream(file);
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(output, format);
			writer.write(document);
			return true;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean updateContact(Contact contact)
	{
		SAXReader reader = new SAXReader();
		try
		{
			Document document = reader.read(file);
			String xpath = "//contact[@id='" + contact.getId() + "']";
			Element element = (Element) document.selectSingleNode(xpath);
			if (element != null)
			{
				if (!element.elementText("name").equals(contact.getName()))
				{
					element.element("name").setText(contact.getName());
				}
				if (!element.elementText("gender")
						.equals(contact.getGender()))
				{
					element.element("gender").setText(contact.getGender());
				}
				if (!element.elementText("phone").equals(contact.getPhone()))
				{
					element.element("phone").setText(contact.getPhone());
				}
				if (!element.elementText("address")
						.equals(contact.getAddress()))
				{
					element.element("address").setText(contact.getAddress());
				}
				if (!element.elementText("email").equals(contact.getEmail()))
				{
					element.element("email").setText(contact.getEmail());
				}

				OutputStream output = new FileOutputStream(file);
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter writer = new XMLWriter(output, format);
				writer.write(document);
				System.out.println();
				return true;
			}
			throw new RuntimeException("Can't find the contact which id is ["
					+ contact.getId() + "]");
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean deleteContact(Contact contact)
	{
		SAXReader reader = new SAXReader();
		try
		{
			Document document = reader.read(file);
			String xpath = "//contact[@id='" + contact.getId() + "']";
			Element element = (Element) document.selectSingleNode(xpath);
			element.detach();

			OutputStream output = new FileOutputStream(file);
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(output, format);
			writer.write(document);
			return true;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	@Override
	public void listAllContact()
	{
		SAXReader reader = new SAXReader();
		List<Contact> contacts = Lists.newArrayList();
		try
		{
			Document document = reader.read(file);
			Element root = document.getRootElement();
			List<Element> contactList = root.elements("contact");
			for (Element eContact : contactList)
			{
				Contact contact = new Contact();
				contact.setId(eContact.attributeValue("id"));
				contact.setName(eContact.elementText("name"));
				contact.setGender(eContact.elementText("gender"));
				contact.setPhone(eContact.elementText("phone"));
				contact.setAddress(eContact.elementText("address"));
				contact.setEmail(eContact.elementText("email"));
				contacts.add(contact);
			}
			printTable(contacts);
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void printTable(List<Contact> contacts)
	{
		String header = String.format(
				"%s		%s		%s		%s		%s		%s", "ID", "NAME",
				"GENDER",
				"PHONE", "ADDRESS", "EMAIL");
		System.out.println(header);
		for (Contact contact : contacts)
		{
			String row = String.format(
					"%s		%s		%s		%s		%s		%s",
					contact.getId(),
					contact.getName(), contact.getGender(), contact.getPhone(),
					contact.getAddress(), contact.getEmail());
			System.out.println(row);
		}
	}
}
