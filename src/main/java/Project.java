import lombok.Data;


@Data
public class Project {

	private String self;
	
	private String id;
	private String key;
	
	private String name;
	
	//private String[] avatarUrls;
	
	private class projectCategory {
		private String self;
		
		private String id;
		private String description;
		
		private String name;
	}
}
