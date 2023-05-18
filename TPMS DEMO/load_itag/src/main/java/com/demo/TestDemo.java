package com.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.conduent.tpms.iag.constants.ItagConstants;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.utility.FormattedDateMatcher;

public class TestDemo {

	public static void main (String s[]) {
		String dirLocation = "D:\\srcDirectory";
		 
        try {
            List<File> files = Files.list(Paths.get(dirLocation))
                                    .filter(path -> path.toString().endsWith(".ITAG"))
                                     .map(Path::toFile)
                                    .collect(Collectors.toList());
            Path  result=null;
            Collections.sort(files, Comparator.comparingLong(File::lastModified));
                files.forEach(System.out::println);
                try (Stream<String> lines = Files.lines( files.get(0).toPath() )) 
                {
                	
              /* String dest ="D:\\file\\"+files.get(0).getName();
               System.out.println("destincation :"+dest);
                	result = Files.move(Paths.get(files.get(0).getAbsolutePath()), Paths.get(dest));
                	      
                	      if(result != null) {
                	         System.out.println("File moved successfully.");
                	      }else{
                	         System.out.println("File movement failed.");
                	      }
                	  */
                /*	Stream<String> forDetail =lines;
                	   
  								Optional<String> headerITAGLine = lines.findFirst();
  								System.out.println("Header line" +headerITAGLine);
  									List<String> detailLines = new ArrayList<>();*/
  								//	Stream<String> streamdetailLines = forDetail.skip(1);
  									//System.out.println("details line" +streamdetailLines.findFirst().get());
  						
  								List<String> list = lines.collect(Collectors.toList());
  								System.out.println("Header line" +list.get(0));
  								list.remove(0);
  								System.out.println("Detail line" +list.get(0));
                	      
                	      
                    //lines.forEach(System.out::println);
                    //reading header structure
                	System.out.println("starting reading");
                //	Stream<String> skip = lines.skip(1);
                	List<TagDeviceDetails> tags= new ArrayList<>();
                	
                	 Consumer<String> printTextInHexConsumer = (String x) -> {
                        TagDeviceDetails tag = new TagDeviceDetails();
                        tag.setDeviceNo(x.substring(0, 8));
                        tags.add(tag);
                        // System.out.print(String.format("%n%-10s:%s", x, sb.toString()));
                     };
                //	skip.forEach(printTextInHexConsumer);
                //	System.out.println(tags.get(0));
                	
                	
                	
                //	List<String> list = lines.collect(Collectors.toList());
                	//removed header line
                /*	String string = list.get(0);
                	System.out.println(string);
                	list.remove(0);*/
                	//start validating detail structure
                	
                	int i =0;
                	
                	/*for(String detail:list) {
                		if(i<10) {
                		System.out.println(detail);}else {break;} i++;
                	}
                	*/
                	
                //	Date date;
                	/*SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
                	f.setLenient(false); // <-- by default SimpleDateFormat allows 20010132
                	try {
                	   f.parse("20200713194418");
                	   System.out.println("good");
                	   // good
                	} catch (ParseException e) {
                	   // bad
                		 System.out.println("bad");
                	}
                	*/
                	DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                	
                	LocalDateTime fileDate=LocalDateTime.parse("20200713194418", formatter);
                	
                	/*if(fileDate.isAfter(LocalDateTime.now())) {
                		System.out.println("future date");
                	}else {
                		System.out.println("past date");
                	}*/
                	
                	
                	String fileName =files.get(0).getName();
            		//fileDetailsVO.setFileName(fileName);
                	System.out.println("file name {} :"+fileName);
            		String fromAgencyId= fileName.substring(0,3);
            		//fileDetailsVO.setProcessName(fromAgencyId);
            		System.out.println("file agency {} :"+fromAgencyId);
            		char underScore =fileName.charAt(3);
            		System.out.println("file underScore {} :"+underScore);
            		String fileDateTime=fileName.substring(4, fileName.length()-5);
            		System.out.println("file fileDateTime {} :"+fileDateTime);
            		if(files.get(0).toPath().toString().endsWith(ItagConstants.ITAG_FILE_EXTENSION) &&
            				ItagConstants.UNDER_SCORE_CHAR.equals(String.valueOf(underScore))) { 
            			//	FormattedDateMatcher.checkDateFormat(fileDateTime)==0) {
            			System.out.println("good file anme");
            		}
                	
                  } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            // Error while reading the directory
        }
	}
}
