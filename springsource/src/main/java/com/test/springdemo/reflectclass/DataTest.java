package com.test.springdemo.reflectclass;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DataTest {

    @Test(dataProvider = "test1")
    @JsonFilePath("src/main/resources/data.json")
    public void testDataProvider1(StudentsEntity entity) {
     /*   logger.info("parameter========="+num);
        logger.info("parameter========="+entity.getName());
        logger.info("parameter========="+entity.getSex());
        logger.info("parameter========="+entity.getAge());
        logger.info("parameter========="+entity.getScore());
        logger.info("parameter========="+group);*/
        System.out.println("num = [" + entity.getName() + "], entity = [" + entity.getSex() + "], group = [" + entity.getAge() + "]");

    }

    @DataProvider(name = "test1")
    public Iterator<Object[]> testProvider(Method method) throws Exception {
        Class[] paramterTypes = method.getParameterTypes();//返回缩获取方法的所有入参
        JsonFilePath path = method.getAnnotation(JsonFilePath.class);
        String filePath = path.value();
        List<Object[]> list = new ArrayList<Object[]>();

        String input = FileUtils.readFileToString(new File(filePath), "UTF-8");
        Object jsonObject=JSON.parse(input);
        if(jsonObject instanceof JSONArray){
            JSONArray jsonA= (JSONArray) jsonObject;
            for (int i = 0; i < jsonA.size(); i++) {
                Object obj = jsonA.get(i);
                if (obj instanceof JSONArray) {  //json解析（判断是jsonArray）
                    Object[] objects = new Object[paramterTypes.length];
                    for (int j = 0; j < paramterTypes.length; j++) {
                        objects[j] = getObject(jsonA.getJSONArray(i), j, paramterTypes[j]);
                    }
                    list.add(objects);

                } else if (obj instanceof JSONObject) {   //json解析（判断是JSONObject）
                    list.add(new Object[]{getObject(jsonA.getJSONObject(i), paramterTypes[0])});
                } else {  //基本类型
                    list.add(new Object[]{jsonA.get(i)});
                }
            }
        }else if(jsonObject instanceof JSONObject){
            list.add(new Object[]{getObject((JSONObject) jsonObject, paramterTypes[0])});
        }


        return list.iterator();
    }

    private Object[] getObject(JSONArray jsonArray, Class c) throws Exception {
        Object[] objects = new Object[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            objects[i] = getObject(jsonArray, i, c);
        }

        return objects;
    }

    private Object getObject(JSONArray jsonArray, int i, Class c) throws Exception {
        //StudentsEntity stu = JSON.parseObject(obj.toString(), StudentsEntity.class);
        System.out.println("c=====" + c.getName());

        if (c.getName().equals("java.lang.Integer") || c.getName().equals("int")) {
            return jsonArray.getInteger(i);
        } else if (c.getName().equals("java.lang.String")) {
            return jsonArray.getString(i);
        } else if (c.getName().equals("java.lang.Long")) {
            return jsonArray.getLong(i);
        } else if (c.isArray()) {  //集合
            Type genericType = c.getGenericSuperclass();

            //如果是泛型参数的类型
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;

                //得到泛型里的class类型对象
                Class genericClass = (Class) pt.getActualTypeArguments()[0];

                return getObject(jsonArray.getJSONArray(i), genericClass);
            }
            return null;
        } else {  //非基本类型
            System.out.println("jsonArray=====" + jsonArray.toString());
            return getObject(jsonArray.getJSONObject(i), c);
        }

    }


    private Object getObject(JSONObject jo, Class c) throws Exception {
        System.out.println("c=====" + c.getName());
     /*   if(c==Long.class || c==long.class || c==String.class || c==Date.class || c==Double.class || c==Integer.class || c==int.class){
            return jo;
        }*/

        Field[] fs = c.getDeclaredFields();
        Object o = c.newInstance();

        for (Field f : fs) {
            f.setAccessible(true);
            if (f.getType() == Long.class || f.getType().getName().equals("long") || f.getType() == int.class) {
                f.set(o, jo.getInteger(f.getName()));
            } else if (f.getType() == String.class) {
                f.set(o, jo.getString(f.getName()));
            } else if (f.getType() == Date.class) {
                f.set(o, jo.getDate(f.getName()));
            } else if (f.getType() == Double.class || f.getType().getName().equals("double")) {
                f.set(o, jo.getDouble(f.getName()));
            }else if (f.getType() == Integer.class || f.getType().getName().equals("int")) {
                f.set(o, jo.getIntValue(f.getName()));
            }
            //TODO 其他基本类型
            else if (f.getType().isArray()) {//集合

                Type genericType = f.getGenericType();
                // 如果是泛型参数的类型
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    //得到泛型里的class类型对象
                    Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                    JSONArray ja = jo.getJSONArray(f.getName());
                    Object[] os = new Object[ja.size()];
                    for (int i = 0; i < ja.size(); i++) {
                        os[i] = getObject(ja.getJSONObject(i), genericClazz);
                    }
                    f.set(o, os);
                }
            } else {//其他类
                f.set(o, getObject(jo.getJSONObject(f.getName()), getClass()));
            }
        }
        return o;
    }
}
