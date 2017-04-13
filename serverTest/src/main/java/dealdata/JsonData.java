/**
 * @author lgy
 * 
 */
package dealdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 
 * @version 
 * 
 */

/*
 * 该方法需要依赖的包：json.lib+(ezmorph和commons的lang、logging、beanutils、collections)
 */
@SuppressWarnings("unused")
public class JsonData {
	
	public JsonData(){
		
	}
    
    @SuppressWarnings("finally")
    public Object ReadJsonFile(String filename){
        String path = filename;
        File file=new File(path);
        BufferedReader reader=null;
        String jsonContent="";
        List<JSONObject> jsonObjList=new ArrayList<JSONObject>();
        try {
            reader=new BufferedReader(new FileReader(file));
            String tempString=null;
            while((tempString=reader.readLine())!=null){
            	jsonContent += tempString;
//            	jsonObjList.add(JSONObject.fromObject(tempString));
//            	System.out.print()
            	}
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally {
            if(reader!=null){
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }

            return JSONObject.fromObject(jsonContent);
        }
        
    }

    public void WriteJson(JSONObject obj) {
//        JSONObject obj = new JSONObject();
//        obj.put("name", "jack");
//        obj.put("age", new Integer(20));
//        obj.put("balance", new Double(12.34));
//        obj.put("is_virgin", new Boolean(true));
//
//        JSONObject obj1 = new JSONObject();
//        obj1.put("name", "jack");
//        obj1.put("age", new Integer(20));
//        obj1.put("balance", new Double(12.34));
//        obj1.put("is_virgin", new Boolean(true));
//        obj1.put("hehe", obj);
//
//        System.out.println("obj1:" + obj1);
//        System.out.println("obj:" + obj);
    }

    public static void main(String[] args) {
//        WriteJson();
    	String filename = new String("/home/paasu/Documents/python/Paas/jobs.txt");
    	JsonData jsonD = new JsonData();
        System.out.print(jsonD.ReadJsonFile(filename));
    }
}

