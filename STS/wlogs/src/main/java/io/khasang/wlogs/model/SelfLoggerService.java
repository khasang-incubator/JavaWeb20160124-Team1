package io.khasang.wlogs.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sergey Orlov
 */
public class SelfLoggerService{
    private  Logger logger;
    private String msg;

   public void setMsg(String msg){
       this.msg=msg;
   }

    public Logger getLog(){
        return LoggerFactory.getLogger(msg);
    }

    public String getTime(){
         Date currentDate = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:S");
         String time = sdf.format(currentDate);
        return time;
    }


}
