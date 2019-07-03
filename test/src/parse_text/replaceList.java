package parse_text;

import java.util.ArrayList;
import java.util.List;

public class replaceList {
	public static void main(String args[]) {
		
		List list=new ArrayList<>();
		
		list.add("1");
		list.add("2");
		list.add("3");
//		for(int i=0;i<list.size();i++) {
			System.out.println(list);
//		}
		
		list.remove("2");
		System.out.println(list);
	}
}
