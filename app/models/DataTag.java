package models;

public class DataTag extends Object {

	private TagTyp tag_type;
	private String description;
	private String content = "";

	public DataTag(TagTyp tag_type, String description, String content) {
		this.setTag_type(tag_type);
		this.setDescription(description);
		this.setContent(content);
	}

	public DataTag(TagTyp tag_type, String description) {
		this.setTag_type(tag_type);
		this.setDescription(description);
	}

	public void setTag_type(TagTyp tag_type) {
		this.tag_type = tag_type;
	}

	public TagTyp getTag_type() {
		return tag_type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

//	public boolean equals(Object datatag) {
//		if ((this.content == (DataTag)datatag.getContent())
//				&& (this.tag_type == datatag.getTag_type()))
//			return true;
//		else
//			return false;
//	}
}
