package models;

import java.util.ArrayList;
import java.util.Iterator;

public class DocumentGenerator {
	
	public String template_content_;
	public String result_;
	public ArrayList<DataTag> tags_;
	
	private static String OPEN_TAG = "<mindshare:";
	private static String CLOSE_TAG = "</mindshare:";
	
	public DocumentGenerator(String template_content, ArrayList<DataTag> tags)
	{	
	  this.template_content_ = template_content;
	  this.tags_ = tags;
	  buildResult();
	}
	
	// fill empty template with data tags and save filled template in result_
	private void buildResult()
	{
	  Integer shrink_size = 0;
	  String replace_label;
	  
	  this.result_ = this.template_content_;
	  Integer result_length = result_.length();

	  Integer offset_open_start = this.result_.indexOf(OPEN_TAG, 0);
	  Integer offset_open_end = this.result_.indexOf(">", offset_open_start);// + 1;
	  Integer offset_close_start = this.result_.indexOf(CLOSE_TAG, offset_open_end);
	  Integer offset_close_end = this.result_.indexOf(">", offset_close_start);// + 1;
	  
	  // run over whole file
	  while (offset_open_start != -1 && (offset_close_start >= offset_open_end))
	  {
          String start_tag_type = this.result_.substring(offset_open_start+OPEN_TAG.length(), offset_open_end);
	      while(start_tag_type.length() <= 0)
		  {
			offset_open_start = this.result_.indexOf(OPEN_TAG, offset_open_end);
			offset_open_end = this.result_.indexOf(">", offset_open_start);
			start_tag_type = this.result_.substring(offset_open_start+OPEN_TAG.length(), offset_open_end);
		  }
		  String end_tag_type = this.result_.substring(offset_close_start+CLOSE_TAG.length(),offset_close_end);
		  
		  if (start_tag_type.equals(end_tag_type))
		  {  
			  replace_label = result_.substring(offset_open_end+1, offset_close_start);

			  if (this.tags_ != null)
			  {
			      Iterator<DataTag> iter = this.tags_.iterator();
			      while(iter.hasNext() )
			      {
			          DataTag tag = iter.next();
			          if ( replace_label.equals(tag.getDescription() ) )
			          {
			        	  this.result_ = this.result_.replaceAll(result_.substring(offset_open_start,offset_open_end+1)
			        			                                  +replace_label
			        			                                  +result_.substring(offset_close_start,offset_close_end+1)
			        			                                , tag.getContent()
			        			                                ); 
			        	  break;
			          }
			      }
			  }
			  else
			  {
				  this.result_ = "tags null";
			  }
	          
			  shrink_size = result_length - result_.length();

		  }
		  else
		  {
			  shrink_size = 0;
		  }

		  result_length = result_.length();
		  offset_open_start = this.result_.indexOf(OPEN_TAG, offset_close_end - shrink_size);
		  offset_open_end = this.result_.indexOf(">", offset_open_start);// + 1;
		  offset_close_start = this.result_.indexOf(CLOSE_TAG, offset_open_end);
		  offset_close_end = this.result_.indexOf(">", offset_close_start);// + 1;
	  }
	}
	
	public String getResult()
	{	
      return this.result_;	
	}
}
