package application;

public class Data {
	private String id;
	private String name;
	private int num;
	private int price;
	private String date;
	private String type;
	private String note;
	
	public Data() {
		
	}

	public Data(String id, String name, int num, int price, String date, String type, String note) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
		this.price = price;
		this.date = date;
		this.type = type;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
}
