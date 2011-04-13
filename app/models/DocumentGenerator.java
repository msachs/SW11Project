package models;

import java.util.ArrayList;
import java.util.Iterator;

public class DocumentGenerator {
	
	public String template_content_;
	public String result_;
	public ArrayList<DataTag> tags_;
	
	private static String OPEN_TAG = "<mindshare:content>";
	private static String CLOSE_TAG = "</mindshare:content>";
	
	public DocumentGenerator(String template_content, ArrayList<DataTag> tags){
		
	  this.template_content_ = template_content;
	  this.tags_ = tags;
	  buildResult();
	}
	
	private void buildResult()
	{
	  Integer offset_start = 0;
	  Integer offset_end = 0;
	  String replace_label;
	  
	  this.result_ = this.template_content_;

	  offset_start = this.result_.indexOf(OPEN_TAG, offset_end);
	  offset_end = this.result_.indexOf(CLOSE_TAG, offset_start);
	  
	  while (offset_start != -1)
	  {
		  replace_label = result_.substring(offset_start + OPEN_TAG.length(), offset_end);
		  
		  if (this.tags_ != null)
		  {
		      Iterator<DataTag> iter = this.tags_.iterator();
		      while(iter.hasNext() )
		      {
		          DataTag tag = iter.next();
		          if ( replace_label.equals(tag.getDescription() ) )
		          {
		        	  this.result_ = this.result_.replaceAll(OPEN_TAG+replace_label+CLOSE_TAG, tag.getContent());
		        	  break;
		          }
		      }
		  }
		  else
		  {
			  this.result_ = "tags null";
		  }
		  offset_start = this.result_.indexOf(OPEN_TAG, offset_end);
		  offset_end = this.result_.indexOf(CLOSE_TAG, offset_start);
	  }
	}
	
	public String getResult(){
		
      
      return this.result_;
	  //return "aJuhu dshf";		
	}
	
	

}
