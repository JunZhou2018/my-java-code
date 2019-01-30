package com.work.day01;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class XmlToMapUtilsV1 {
    public static void main(String[] args) throws IOException, DocumentException {
       /* FileInputStream fis = new FileInputStream("d://demo.xml");
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

    public static Map Dom2Map(Document doc){
        Map<String,Object> map = new HashMap<String, Object>();
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator();iterator.hasNext();){
            Element e = (Element) iterator.next();
            List list =  e.elements();
            if (list.size() > 0){
                map.put(e.getName(),Dom2Map(e));
            }else{
                map.put(e.getName(),e.getText());
            }
        }

        return map;
    }

    public static Map Dom2Map(Element e){
        Map<String,Object> map = new HashMap<String, Object>();
        List list = e.elements();
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Element element = (Element) list.get(i);

                List elementList = element.elements();
                if (elementList.size()>0){

                }else{
                    map.put(element.getName(),element.getText());
                }
            }
        }else {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    public static String getString(){
        String str = "<?xml version='1.0' encoding = 'UTF-8'?> ";
        str+= "<BUSI_INFO>  ";
        str+="	<TEMPLET_ID>20008</TEMPLET_ID>";
        str+="	<TEMPLET_CODE>code</TEMPLET_CODE>";
        str+= "</BUSI_INFO>";
        str+="<PUB_INFO>";
        str+=" <BUSI_TYPE>1</BUSI_TYPE>";
        str+=" <USE_RANGE>2</USE_RANGE>";
        str+=" <REGION_ID>3</REGION_ID>";
        str+="</PUB_INFO>";
        return str;
    }
}
