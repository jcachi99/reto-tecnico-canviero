package com.maintenance.entities.util;

public class ClientUtil {

    public static String getNameOrLastFormated(String name){
        return name!=""?name.replaceAll("\\s+", " ").replaceAll("\\d","").trim():"";
    }

    public static String getDocumentFormated(String document){
        if(document!=""){
            document = document.replaceAll("\\s+", "").trim();
            if(document.length()==8){
                return document.matches("[0-9]+")?document:document+"ERROR";
            }else{
                return document+"ERROR";
            }
        }else{
            return document+"ERROR";
        }
    }

    public static String getEmailFormated(String email){
        email = email.replaceAll("\\s+", "").trim();
        return email.toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.com")?email:email+"ERROR";
    }

    public static String getPhoneFormated(String phone){
        int index = phone.indexOf(")")+1;
        String prefix = phone.substring(0,index).replaceAll("\\s+", "").trim();
        if(prefix.matches("\\(+\\+[0-9]+\\)")){
            String number = phone.substring(index+1);
            number = number.replaceAll("\\s+", "").trim();
            if(number.length()==9 && number.matches("[0-9]+")){
                StringBuilder stringBuilder = new StringBuilder(number);
                stringBuilder.insert(3," ").insert(7," ");
                return prefix.concat(stringBuilder.toString());
            }else{
                return phone+"ERROR";
            }
        }else{
            return phone+"ERROR";
        }
    }

    public static boolean validateValue(String value){
        return value.contains("ERROR")?Boolean.FALSE:Boolean.TRUE;
    }

}
