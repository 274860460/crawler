import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;


public class Test2 {


    static HttpClient client = HttpClients.createDefault();
    
    static String uid = "10370";

//    static String sessionId = "abc80h7kS9TH3eYxUsaLv";
    static String sessionId = "cda6OiRpI2Midgn2QMaLv";
//    static String sessionId = "bacSU62E8k4NE7L6qOaLv";


    public static void main(String[] args) throws Exception {

        File f = new File("F:/data");
        if (!f.exists()) {
            f.mkdir();
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("doing " + i + " ...");
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < 40; j++) {
                String content = add();
                sb.append("\r\n");
                sb.append(content);
            }
            File txt = new File(f.getAbsolutePath() + "/part" +  + i + ".txt");
            FileOutputStream out = new FileOutputStream(txt);
            out.write(sb.toString().getBytes());
            out.flush();
            out.close();
        }

    }


    public static String list() throws Exception{
        HttpUriRequest request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_list.jsp");
        return execute(request);
    }

    public static String delete(String id) throws Exception{
        HttpUriRequest request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkhistory_delexe.jsp?pageInt=1&tsId=" + id + "&url=homeworkpaper_list.jsp");
        return execute(request);
    }

    public static String detail(String id) throws Exception{
        HttpUriRequest request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkhistory_info.jsp?tsId=" + id);
        return execute(request);
    }

    public static String add() throws Exception{
        HttpUriRequest request = null;
        String response = null;
        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_add_bypolicy_random.jsp?paperId=" + uid + "&pageInt=1&tsId=null");
        response = execute(request);
        if (response.contains("登陆超时") ||response.contains("403")) {
            throw new RuntimeException("登陆超时");
        }

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_addexe_bypolicy_random.jsp?paperId=" + uid + "&tsId=null");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_pre.jsp?id=" + uid + "&tsId=null");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homework_frame.jsp?id=" + uid + "&pageInt=null&tsId=null&showType=null&isShow=null");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homework_top.jsp?id=" + uid + "&pageInt=null");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_left.jsp?id=" + uid + "&pageInt=null&tsId=null&showType=null&isShow=null");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_main.jsp");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/menu/user/menu.jsp?id=" + uid + "&tsId=null&showType=null&isShow=null");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/question_info.jsp?id=" + uid + "&tsId=null");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/question_infoexe.jsp?ischeck=-1&id=" + uid + "&tsId=null");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_result.jsp?id=" + uid + "&tsId=null&ischeck=-1");
        response = execute(request);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_resultexe.jsp?id=" + uid + "&totalScore=0.0&tsId=null&ischeck=-1");
        response = execute(request);
        //作业保存失败
        String id = getId(response);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/question_infoexe.jsp?ischeck=0&id=" + uid + "&tsId=" + id);
        response = execute(request);


        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_result.jsp?id=" + uid + "&tsId=" + id + "&ischeck=0");
        response = execute(request);

//        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_resultexe.jsp?id=" + uid + "&totalScore=0.0&tsId=" + id + "&ischeck=0");
//        response = execute(request);

        delete(id);
        return replace(response);

    }

    private static String replace(String content) throws IOException {
        int s = content.indexOf("<body");
        int e = content.indexOf("</table-->");
        content = content.substring(s, e)
                .replaceAll("<[a-z]+.+\">", "")
                .replaceAll("<[a-z]+.>", "")
                .replaceAll("</[a-z]+>", "")
                .replaceAll("<p>", "")
                .replaceAll("<body.+>", "")
                .replaceAll("<!--", "")
                .replaceAll("-->", "")
                .replaceAll("&.+;", "")
                .replaceAll("\t", "")
                .replaceAll(" ", "")
                .replaceAll("作业", "")
                .replaceAll("您的答案：", "")
                .replaceAll("此题得分：0.0", "")
                .replaceAll("试卷总得分：0.0", "")
                .replaceAll("[0-9]+．第[0-9]+题", "*")
                .replaceAll("题目内容：", "")
                .replaceAll("【答题要点】", "");
        BufferedReader reader = new BufferedReader(new StringReader(content));

        String str = null;
        StringBuilder sb = new StringBuilder();
        while ((str = reader.readLine()) != null) {
            if (str.length() > 0) {
                sb.append(str).append("\r\n");
            }
        }
        reader.close();
        return sb.toString();
    }

    private static String execute(HttpUriRequest request) throws Exception{
        setCookie(request);
        HttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity());

    }

    private static void setCookie(HttpUriRequest request){
        request.setHeader("Cookie", "JSESSIONID=" + sessionId);
    }

    private static String getId(String str){
        String start = "&tsId=";
        String end = "\"";
        int startIndex = str.indexOf(start) + start.length();
        str = str.substring(startIndex);
        int endIndex = str.indexOf(end);
        return str.substring(0, endIndex);
    }
}
