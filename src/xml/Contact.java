package xml;

import com.google.common.base.MoreObjects;

public class Contact
{
	private String id;
	private String name;
	private String gender;
	private String phone;
	private String address;
	private String email;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("name", name)
				.add("gender", gender)
				.add("phone", phone)
				.add("address", address)
				.add("email", email).toString();
	}
}
