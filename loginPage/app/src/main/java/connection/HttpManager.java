package connection;

import android.os.StrictMode;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import impl.Lesson;

public class HttpManager
{
    public static final int OK = 200;
    public static final int ERR = 301;

    public static final String URL = "http://10.0.0.42:9090";//"https://giladon.pythonanywhere.com";

    private int code;
    private Object data;

    public HttpManager(int code, Object data)
    {
        this.code = code;
        this.data = data;
    }

    public static HttpManager handleData(int code, String data)
    {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> map = gson.fromJson(data, type);

        if (code == ERR)
            return new HttpManager(code, map.get("error"));

        if (code == OK)
            return new HttpManager(code, map.get("result"));

        return null;
    }

    public HttpManager()
    {
        this(0, null);
    }

    public static HttpManager GetRequest(String urlStr) throws IOException {
        return GetRequest(urlStr, null, null);
    }

    public static HttpManager GetRequest(String urlStr, Map<String, String> prams) throws IOException {
        return GetRequest(urlStr, null, prams);
    }

    public static HttpManager GetRequest(String urlStr, ArrayList<String> routeAdd) throws IOException {
        return GetRequest(urlStr, routeAdd, null);
    }

    public static HttpManager GetRequest(String route, ArrayList<String> routeAdd, Map<String, String> prams) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        route = BuildURLWithParms(route, routeAdd, prams);
        URL url = new URL(route);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("Response code: " + responseCode);
        String data = getDataFromConnection(con);

        HttpManager httpData = handleData(responseCode, data);
        con.disconnect();
        return httpData;
    }

    public static HttpManager PostRequest(String urlStr, Object data) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        urlStr = URL + urlStr;

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        DataOutputStream out = new DataOutputStream(con.getOutputStream());

        Gson gson = new Gson();
        out.writeBytes(gson.toJson(data));
        out.flush();
        out.close();

        int responseCode = con.getResponseCode();
        String dataFromServer = getDataFromConnection(con);

        HttpManager httpData = new HttpManager(responseCode, dataFromServer);
        con.disconnect();
        return httpData;
    }

    private static String getDataFromConnection(HttpURLConnection con)
    {
        String dataFromServer = "";
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            dataFromServer = content.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataFromServer;
    }

    private static String BuildURLWithParms(String url, ArrayList<String> path, Map<String, String> parms)
    {
        url = URL + url;

        if (path != null)
        {
            for (String part: path)
            {
                if (part == null || part.isEmpty())
                    continue;

                url += "/" + encodeStringURL(part);
            }
        }

        if (parms == null || parms.isEmpty())
            return url;

        ArrayList<String> parmsList = new ArrayList<>();
        for (String k: parms.keySet())
        {
            if (k == null || k.isEmpty() || parms.get(k) == null)
                continue;

            String left = encodeStringURL(k);
            String right = encodeStringURL(parms.get(k));

            if (left == null || right == null)
                continue;

            parmsList.add(left + "=" + right);
        }
        if (parmsList.size() == 0)
            return url;
        return url + "?" + String.join("&", parmsList);
    }

    private static String encodeStringURL(String str)
    {
        try {
            return URLEncoder.encode(str, String.valueOf(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "HttpManager{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

//    public static void main(String[] args) {
//        String uid = "oXgq1ihTqmZWWezZER2MaoBw2V52";
//        String lid = "C++";
//
//        try {
//            HttpManager httpResp = HttpManager.GetRequest("/get_tutor_lesson", Map.of("UID", uid, "LID", lid));
//
//            System.out.println(Lesson.ObjectToLesson(httpResp.data));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Gson gson = new Gson();
//        String s = gson.toJson(new Lesson());
//        Object JS = gson.fromJson(s, Object.class);
//
//    }
}
