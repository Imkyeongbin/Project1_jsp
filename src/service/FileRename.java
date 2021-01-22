package service;
import java.io.File;

public class FileRename {
	
	String Rename (String FileName, String uri){
		long counter = 1;
		String tempFileName ="";
		
		System.out.println("FileRename 부분 시작");
		
		String fullDrt=uri+FileName;
		
		System.out.println("fullDrt= "+fullDrt);
		try {
			  File file = new File(fullDrt); 
			  tempFileName =FileName;
				  while(file.exists()) {
					  System.out.println("while문 실행.");
					  tempFileName = "("+counter+")"+FileName;
					  System.out.println("tempFileName : "+tempFileName);
					  fullDrt=uri+tempFileName;
					  file = new File(fullDrt); 
					  counter++;
				  }
					 
				  System.out.println("최종 변경된 파일 이름="+tempFileName);

		} catch (Exception e) {
			System.out.println("FileRename 실패" + e.getMessage());
		}
		  return tempFileName;
	}

}