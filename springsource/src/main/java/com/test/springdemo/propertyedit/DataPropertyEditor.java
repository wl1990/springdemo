package com.test.springdemo.propertyedit;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 王大雷 on 2018/6/3.
 */
public class DataPropertyEditor extends PropertyEditorSupport {
    private String format="yyyy-mm-dd";
    public void setFormat(String format){
        this.format=format;
    }
    public void setAsText(String arg0) throws IllegalArgumentException{
        System.out.print("arg0:"+arg0);
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        try {
            Date d=sdf.parse(arg0);
            this.setValue(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
