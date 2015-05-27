
public class Person {
	private String name = "";
	private String phone = "";
	private String email = "";
	private int age = 0;
	
	public Person(){}
	public Person(String name, String phone, String email, int age){
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
	}
	
	//accessor methods
	public void setName(String name){
		this.name = name;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setAge(int age){
		this.age = age;
	}
	
	public String toString(){
		String output = "Name: " + name + "\n" +
						"Phone: " + phone + "\n" + 
						"Email: " + email + "\n" +
						"Age: " + age;
		return output;
	}
}
