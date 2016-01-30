package model; 

public class Server
{
	
	public int id;

	public Server(){}

	public String name;

	public Server(int id, String name ){
		this.id = id;
		this.name = name;
	}
	
	public String toString(){
		return "ID: " + id + ", NAME: " + name;
	}

	/**
	 * Added to simplify the integration tests
	 * @param o
	 * @return
     */
	public boolean equals(Object o){
		if (o instanceof Server){
			Server s = (Server)o;
			if(this.id == s.id && this.name.compareTo(s.name)==0)
				return true;
		}
		return false;
	}

}