package com.james.mshop.util;

public interface Content {
	//登陸
	
	String URL_LOGIN= "http://josie.i3.com.hk/mshop/json/login.php?";
	
	//註冊
    String URL_REGIT= "http://josie.i3.com.hk/mshop/json/reg.php?"; 
    
    //產品詳情頁面----尺寸列表
    String URL_PRODUCTSIZE="http://josie.i3.com.hk/mshop/json/productsize.php?id=";

    //產品詳情頁面----圖片列表
    String URL_PRODUCT_PIC="http://josie.i3.com.hk/mshop/json/productpic.php?id=";
    
  //產品詳情頁面----顏色列表
    String URL_PRODUCTCOLOR="http://josie.i3.com.hk/mshop/json/productcolor.php?id=";
    
    //一級分類列表
    String CATEGORY_ONE="http://josie.i3.com.hk/mshop/json/categoryonelist.php";
    
    //二級分類列表
    String CATEGORY_TWO="http://josie.i3.com.hk/mshop/json/categorytwolist.php?oid=";
    
    //三級分類列表
    String CATEGORY_THREE="http://josie.i3.com.hk/mshop/json/categorythreelist.php?tid=";
    
  //產品列表
    String CATEGORY_List="http://josie.i3.com.hk/mshop/json/productlist.php?";
  //搜索列表
    String CATEGORY_Search="http://josie.i3.com.hk/mshop/json/search.php?";
    
    //產品詳情頁面
      String PRODUCT_DETAIL="http://josie.i3.com.hk/mshop/json/productdetail.php?id=";
      
      //隨機顯示3條最新產品
      String RADOM_SECOND="http://josie.i3.com.hk/mshop/json/randlist.php?tid=";
      
    //地址列表
      String ADRESS_LIST="http://josie.i3.com.hk/mshop/json/m_addresslist.php?mid=";
      //刪除地址
      String ADRESS_DELETE="http://josie.i3.com.hk/mshop/json/m_addressdel.php?";
      //城市列表
      String CITY_LIST="http://josie.i3.com.hk/mshop/json/m_citylist.php";
      //地區列表
      String AREA_LIST="http://josie.i3.com.hk/mshop/json/m_arealist.php?cityid=";
      //添加地址
      String  ADD_ADRESS="http://josie.i3.com.hk/mshop/json/m_addressadd.php?";
      //修改地址
      String  EDIT_ADRESS="http://josie.i3.com.hk/mshop/json/m_addressedit.php?";
      //   收藏夾列表
      String  FAVORITELIST="http://josie.i3.com.hk/mshop/json/m_favoritelist.php?mid=";
      
      //   刪除收藏夾
      String  FAVORITE_DELETE="http://josie.i3.com.hk/mshop/json/m_favoritedel.php?";
      //   加入收藏夾
      String  FAVORITE_ADD=" http://josie.i3.com.hk/mshop/json/m_favoriteadd.php?";
      
      //加入購物車（非會員不能購物）
      String  SHOP_ADD=" http://josie.i3.com.hk/mshop/json/shop_add.php?mid=";
      
      //   購物車產品列表
      String  SHOP_LIST="http://josie.i3.com.hk/mshop/json/shop_list.php?mid=";
      
 //   刪除購物車某產品
      String  SHOP_DELETE="http://josie.i3.com.hk/mshop/json/shop_del.php?";
}
