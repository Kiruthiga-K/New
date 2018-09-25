package com.example.RestApi;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import org.springframework.http.*;

@RestController
public class ControllerClass{
	static int count=0;
	static int avg=0;
	static float sum=0;
	static float min=9999,max=0;
	
	Statistics st=new Statistics();
	
	@RequestMapping(value = "/statisticsPost", method= RequestMethod.POST)
	public ResponseEntity<List<TransactionClass>> TransactionMethod(@RequestBody List<TransactionClass> tc) 
	{
		
		 
		 long time=System.currentTimeMillis();
		 
		 tc.stream().forEach(c -> { 
			 long userTime=c.getTimestamp();
			
			 if((time-userTime)<60000)
			 {
				 count=count+1;
				 sum=sum+c.getAmount();
				 if(min>c.getAmount())
						min=c.getAmount();
					if(max<c.getAmount())
						max=c.getAmount();
					
				}
			
			});
		if(count>0)
		{	
			
			avg=(int)sum/count;
		}	
		return new ResponseEntity<List<TransactionClass>>( HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value="/statistics", method =RequestMethod.GET )
	public ResponseEntity<Statistics> TransactiongetMethod(Statistics st)
	{
		st.setCount(count);
		st.setTotal((int)sum);
		st.setAvg(avg);
		st.setMax(max);
		st.setMin(min);
		return new ResponseEntity<Statistics>(st, HttpStatus.OK); 	 
	 }
		 
		
	}
	

