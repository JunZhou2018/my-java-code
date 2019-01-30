package com.work.day01;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 将xml 报文转换成 map
 */
public class XmlToMapUtilsV2 {
    public static void main(String[] args) throws IOException, DocumentException {
        /*FileInputStream fis = new FileInputStream("d://demo.xml");
        byte[] b = new byte[fis.available()];
        fis.read(b);
        String str = new String(b,"UTF-8");*/
        Document doc = DocumentHelper.parseText(getString());
        System.out.println(doc.asXML());
        long beginTime = System.currentTimeMillis();
        Map<String, Object> map = Dom2Map(doc);
        System.out.println(map.toString());
        System.out.println("Use time:"+(System.currentTimeMillis()-beginTime));
    }


    @SuppressWarnings("unchecked")
    public static Map<String, Object> Dom2Map(Document doc){
        Map<String, Object> map = new HashMap<String, Object>();
        if(doc == null)
            return map;
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
            Element e = (Element) iterator.next();
            List list = e.elements();
            if(list.size() > 0){
                map.put(e.getName(), Dom2Map(e));
            }else
                map.put(e.getName(), e.getText());
        }
        return map;
    }
    @SuppressWarnings("unchecked")
    public static Map Dom2Map(Element e){
        Map map = new HashMap();
        List list = e.elements();
        if(list.size() > 0){
            for (int i = 0;i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if(iter.elements().size() > 0){
                    Map m = Dom2Map(iter);
                    if(map.get(iter.getName()) != null){
                        Object obj = map.get(iter.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if(obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    }else
                        map.put(iter.getName(), m);
                }else{
                    if(map.get(iter.getName()) != null){
                        Object obj = map.get(iter.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if(obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    }else
                        map.put(iter.getName(), iter.getText());
                }
            }
        }else
            map.put(e.getName(), e.getText());
        return map;
    }

    public static String getString(){
        String str = "<?xml version='1.0' encoding = 'UTF-8'?> ";
        str+= "<RES_OUT>";
        str+= "<BUSI_INFO>";
        str+="<TEMPLET_ID>20008</TEMPLET_ID>";
        str+="<TEMPLET_CODE>code</TEMPLET_CODE>";
        str+= "</BUSI_INFO>";
        str+="<PUB_INFO>";
        str+="<BUSI_TYPE>1</BUSI_TYPE>";
        str+="<USE_RANGE>2</USE_RANGE>";
        str+="<REGION_ID>3</REGION_ID>";
        str+="</PUB_INFO>";
        str+= "</RES_OUT>";
        return str;
    }
}
