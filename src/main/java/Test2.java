import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class Test2 {


    static HttpClient client = HttpClients.createDefault();
    
    static String uid = "10370";


    public static void main(String[] args) throws Exception {


//        String id = add();
//        System.out.println(id);
//        System.out.println(detail("12077265"));
//        System.out.println(delete("12077265"));


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
        String id = getId(response);

        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/question_infoexe.jsp?ischeck=0&id=" + uid + "&tsId=" + id);
        response = execute(request);


        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_result.jsp?id=" + uid + "&tsId=" + id + "&ischeck=0");
        response = execute(request);


        request = new HttpGet("http://www.gdou.com/entity/function/onlineexercise/homeworkpaper_resultexe.jsp?id=" + uid + "&totalScore=0.0&tsId=" + id + "&ischeck=0");
        response = execute(request);

        return id;

    }

    private static String execute(HttpUriRequest request) throws Exception{
        setCookie(request);
        HttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity());

    }

    private static void setCookie(HttpUriRequest request){
        request.setHeader("Cookie", "JSESSIONID=abd92DBiHsg2R6xCDiFKv; firstEnterUrlInSession=http://www.gdou.com/; VisitorCapacity=1");
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
