package com.james.mshop.util;

public class Bean {
private static String mAid;
private static String string_Adress;
private static String mString_Account;
private static String mString_Receive;
private static String mString_Telephone;
         

		public static void setAdressId(String string) {
		mAid=string;
		
		}
		
		public static   String	getAdressId() {
			return mAid;
		}

		public static void setAdressName(String string_adress) {
             string_Adress=string_adress;	
		}
		
		public static   String	getAdressName() {
			return string_Adress;
		}

		public static void setAccount(String mString_hk_rmb) {
			mString_Account=mString_hk_rmb;      			
		}
		
		public static   String	getAccount() {
			return mString_Account;
		}

		public static void setReceiver(String string) {
          mString_Receive=string;			
		}
		public static   String	getReceiver() {
			return mString_Receive;
		}

		public static void setTelephone(String string) {
			 mString_Telephone=string;
		}
		public static   String	  getTelephone() {
			return mString_Telephone;
		}

}
