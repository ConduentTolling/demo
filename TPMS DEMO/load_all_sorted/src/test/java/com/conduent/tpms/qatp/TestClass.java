package com.conduent.tpms.qatp;

import ch.qos.logback.core.net.SyslogOutputStream;

public class TestClass {

	public static void main(String[] args) {
//		int primaryRebillPayType = 15;
//		if(primaryRebillPayType == (Constants.RPT_VISA  | Constants.RPT_MASTERCARD | Constants.RPT_AMEX | Constants.RPT_CASH | Constants.RPT_DISC | Constants.RPT_SACH | Constants.RPT_ACHSPPT | Constants.RPT_ACHCPPT))
//		{
//			System.out.println(primaryRebillPayType);
//		}
//		
//		int ar1[] = {0,0,0,0,0,0,0,1};
//		int ar2[] = {0,0,0,0,0,4,0,0};
//		int ar3[] = {0,0,0,0,0,0,2,0};
//		int[] summ = new int[8];
//		
//		
//		for (int i = summ.length-1; i >=0 ; i--) {
//			
//			summ[i] = ar1[i]+ar2[i];
//		}
//		
//		//System.out.println(Arrays.toString(summ));
//		
//		List collection = new ArrayList<String>();
//		collection.add("00000020");
//		collection.add("00000400");
//	//	int[] newarr = Arrays.(type[]) collection.toArray(new type[collection.size()]);
//		//System.out.println(Arrays.(int[])collection.toArray());
//		
//		
//		//String sum = add(00000001, 00000002); //sum:::00000003
//	//	String sum1 = add(00000001, 00000400); //sum:::00000257
////		int sum2 = AddInt(00000001, 00000400); //1257
//	//	System.out.println("sum:::"+00000001+00000400); 
//	//	System.out.println(sum2);
//		//System.out.println(Integer.toHexString(sum));
//	//	String s1 = String.format("%8s", Integer.toBinaryString(Integer.valueOf(sum1) & 0xFF)).replace(' ', '0');
//		//System.out.println(s1); // 10000001
//	}
//
//	static int Add(int x, int y)
//	{
//	  if (y == 0)
//	    return x;
//	  else
//	    return Add(x ^ y, (x & y) << 1);
//	}
//	static int AddInt(int x, int y)
//	{
//	  return Math.addExact(x, y);
//	}
//	
//	static String add(int x, int y)
//    {
//        int carry;
//        while(y!=0)
//        {
//            carry = x & y;
//            x = x ^ y;
//            y = carry << 1;
//        }
//        return String.format("%08d", x);
		
		String planstr="00020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		
		int ftag_plan_info=12;
		int planbit=(Integer.valueOf(planstr.substring((int) (Math.floor(105 / 10) * 4 ), 44))
				& (int) Math.pow(2, Math.floorMod(105, 10)));
		 if(   (Integer.valueOf(planstr.substring((int) (Math.floor(105 / 10) * 4 ), 44))
					& (int) Math.pow(2, Math.floorMod(105, 10))) > 0    )
		{
		     ftag_plan_info = ftag_plan_info + 1;
		 }
		System.out.println(ftag_plan_info);
		
		
		
		
    }
}
