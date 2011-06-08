package models;

public class WordPosRep {
	
	private int  position;
	private int length;
	private String replacer;
	
	public WordPosRep() {
		this.position = 0;
		this.length = 0;
		this.replacer = "";
	}
	
	public WordPosRep(int pos, int len, String rep) {
		this.position = pos;
		this.length = len;
		this.replacer = rep;
	}
	
	public int getPosition(){
		return this.position;
	}
	
	public int getLength(){
		return this.length;
	}

	public String getReplacer(){
		return this.replacer;
	}
	
	public void addToLenght(int offset){
		this.length += offset;
	}
	
	public void addToPosition(int offset){
		this.position += offset;
	}
	
	public void setPosition(int position){
		this.position = position;
	}
	
	public void setLength(int length){
		this.length = length;
	}
	
	public void setReplacer(String replacer){
		this.replacer = replacer;
	}
	
	public String toString(){
		return "[" + new Integer(this.position).toString() + 
				", " + new Integer(this.length).toString() + ", " + 
				this.replacer + "]"; 
	}
	
}
