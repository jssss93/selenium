package parse_text;

public class split_html {
	public static void main(String args[]) {
		String prm_str="<p>test</p><p>test2</p><p>test3</p><p><br></p><p><img src=\"/upload/bb727feb-b34b-4dad-9ac3-3b3477fbfb7d.jpg\" title=\"\"></p><p><br style=\"clear:both;\">asdasd</p><p><br></p><p><img src=\"/upload/d25258d3-55a0-44fe-93a2-cf913cbe9cfd.png\" title=\"\"><br style=\"clear:both;\"><br></p>";
	    String[] strs=prm_str.split("<p>|</p>");
	    for(int i=0;i<strs.length;i++) {
	    	System.out.print(i+"==>>   ");
	    	System.out.println(strs[i]);
	    }
	    for(int j=1;j<strs.length;j=j+2) {
	    	System.out.print(j+"==>>   ");
	    	System.out.println(strs[j]);
	    	if(strs[j].substring(0, 4).equals("<img")) {
	    		System.out.println("업로드");
	    	}
	    }
	    
//	    String[] strs2=prm_str2.split("<p>|</p>");
	    
//	    for(int i=0;i<strs2.length;i=i++) {
//	    	System.out.println(strs2[i]);
//	    }
	}
}


/*

test

<br>

<img src="/upload/bb727feb-b34b-4dad-9ac3-3b3477fbfb7d.jpg" title="">

<br style="clear:both;">asdasd

<br>

<img src="/upload/d25258d3-55a0-44fe-93a2-cf913cbe9cfd.png" title=""><br style="clear:both;"><br>
*/