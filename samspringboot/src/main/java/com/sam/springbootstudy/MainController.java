package com.sam.springbootstudy;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.websocket.OnMessage;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;


@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class MainController {
	
	
	@Autowired
	private UserMapper userMapper;

	String  foldername = "./kevin-computer-studylog/";

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @RequestMapping("/test")
  public String test(@RequestParam("callback") String jsonpcallback, ModelMap model) {
	  System.out.println("model:" + model.toString());
      return  jsonpcallback + "({'username':'jack','age':21,'gender':'male'})";
}

  
  @RequestMapping("/getsamdata")
  public String getsamdata() {
	  
	  
    return "catta get response!";
  }


  
  @OnMessage
  public void onMessage(String message, Session session) 
      throws IOException, InterruptedException {
      System.out.println("客户端说：" + message);
      
      while(true){
          session.getBasicRemote().sendText("world");
          Thread.sleep(2000);
      }
  }

    @RequestMapping("/appendstudentthinklog")
    public String appendstudentthinklog(String filename,String studentthink) {

      //  System.out.println(filename +"  " + studentthink);
        FileWriter writer =null;
        try {
            File file = new File(foldername,filename);
            writer = new FileWriter(file,true);

            writer.write("\r\n---------------------------------------\r\n" +df.format(new Date()) +"  kevin:" + studentthink);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{ writer.close();}catch (Exception ee){}
        }

        return "";
    }



    @RequestMapping("/saveclasslog")
    public String saveclasslog(String classtitle,String classcode) {

        saveclass2LocalFile(classtitle,classcode);

        return "";
    }


    void saveclass2LocalFile(String classtitle,String classcode)
    {

        classcode = classtitle +"#saveclass#" +"\r\n---------------------------------------"  + "\r\n" +classcode;

        String  filename = "class" + new Date().getTime();
        FileWriter writer =null;
        try {
            File file = new File(foldername,filename);
            writer = new FileWriter(file);
            writer.write(classcode);
            writer.flush();
            writer.close();

            } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{ writer.close();}catch (Exception ee){}
        }

    }

    @RequestMapping("/getclassloglist")
    public String getclassloglist() {

        String myRet = "";
        JSONObject retJsonObject = new JSONObject();
        ArrayList<JSONObject> alllist = new ArrayList<JSONObject>();

        try{
            File classlogDir = new File(foldername);
//            File classlogDir = new File("/Users/eviewlake/Documents/tianfangskill/kevin-computer/kevin-study-coding/samspringboot");

            if(classlogDir.isDirectory())//判断file是否是目录
            {
               // System.out.println("is dir");
                File [] classloglists = classlogDir.listFiles();
                Arrays.sort(classloglists, new MainController.CompratorByLastModified()); //事件排序

                if(classloglists!=null)
                {
                    for(int i=0;i<classloglists.length;i++)
                    {

                        if(classloglists[i].isDirectory()) continue;;
                        String filename = classloglists[i].getName();
                        String oneclass = readTxtFile(classloglists[i]);

                       // System.out.println(filename);
                        int save = oneclass.indexOf("#saveclass#");
                        if(save != -1)
                        {
                            String title = oneclass.substring(0, save);
                            String content = oneclass;

                            JSONObject temp = new  JSONObject();
                            temp.put("filename",filename);
                            temp.put("title",title);
                            temp.put("code",content);
                            alllist.add(temp);
                        }

                    }
                }
            }
        }catch (Exception ee) {ee.printStackTrace();}

        retJsonObject.put("classloglist",alllist);
        myRet = JSONObject.toJSONString(retJsonObject);

       // System.out.println("classloglist:" + myRet);
        return myRet;
    }



    ArrayList readclasslogfromlocaldir() throws Exception{

        ArrayList myRet = new ArrayList();


        return myRet;
    }



    String readTxtFile(File classFile)throws Exception {
        String result="";
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try{
            fileReader=new FileReader(classFile);
            bufferedReader=new BufferedReader(fileReader);
            try{
                String read=null;
                while((read=bufferedReader.readLine())!=null){
                    result=result+read+"\r\n";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileReader!=null){
                fileReader.close();
            }
        }
    //    System.out.println("读取出来的文件内容是"+result);
        return result;
    }


    private static class CompratorByLastModified implements Comparator<File> {
        public int compare(File f1, File f2) {
            long diff = f1.lastModified()-f2.lastModified();
            if(diff>0)
                return -1;
            else if(diff==0)
                return 0;
            else
                return 1;
        }
        public boolean equals(Object obj){
            return true;
        }
    }




    public  static void main(String args[])
    {

        MainController  a = new MainController();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(df.format(new Date()));

        String classlogfilenamelist = a.getclassloglist();

        System.out.println(classlogfilenamelist);

    }







}
