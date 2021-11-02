package com.example.tourguide;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public  static final String userSession="userLoginSession";
    public  static final String remember_me="rememberMeSession";

    //userSession variables
    private static final String Is_Login="IsLoggedIn";
    public static final String key_fullName="fullName";
    public static final String key_userName="userName";
    public static final String key_email="email";
    public static final String key_phoneNo="phoneNo";
    public static final String key_password="password";

    //rememberMeSession variables
    private static final String  Is_remember="IsRememberMe";
    public static final String key_phoneNo_remember="phoneNo";
    public static final String key_password_remember="password";

   public SessionManager(Context _context,String sessionName){
        context =_context;
        sharedPreferences=_context.getSharedPreferences(sessionName,Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
    }

    //userSession function
    public  void creatingLoginSession(String fullName,String userName,String email,String phoneNo,String password){

       editor.putBoolean(Is_Login,true);

       editor.putString(key_fullName,fullName);
       editor.putString(key_userName,userName);
       editor.putString(key_email,email);
       editor.putString(key_phoneNo,phoneNo);
       editor.putString(key_password,password);

       editor.commit();
    }

    public HashMap<String,String> getUserDetailsFromSession(){
       HashMap<String,String> userData=new HashMap<String,String>();
       userData.put(key_fullName,sharedPreferences.getString(key_fullName,null));
       userData.put(key_userName,sharedPreferences.getString(key_userName,null));
       userData.put(key_email,sharedPreferences.getString(key_email,null));
       userData.put(key_phoneNo,sharedPreferences.getString(key_phoneNo,null));
       userData.put(key_password,sharedPreferences.getString(key_password,null));

       return userData;
    }

    public boolean checkLogin(){
       if(sharedPreferences.getBoolean(Is_Login,true)){
           return  true;
       }else
           return false;
    }
    public  void logoutSession(){
       editor.clear();
       editor.commit();
    }

    //rememberMe session

    public  void  rememberMeSession(String phoneNo,String password){
       editor.putBoolean(Is_remember,true);
       editor.putString(key_phoneNo_remember,phoneNo);
       editor.putString(key_password_remember,password);
       editor.commit();
    }

    public HashMap<String,String> getRememberDetails(){
        HashMap<String,String> userData=new HashMap<String, String>();

        userData.put(key_phoneNo_remember,sharedPreferences.getString(key_phoneNo_remember,null));
        userData.put(key_password_remember,sharedPreferences.getString(key_password_remember,null));

        return userData;
    }

    public boolean checkRememberMe(){
       if (sharedPreferences.getBoolean(Is_remember,false)){
           return true;
       }
       return false;
    }
}
