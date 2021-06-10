package utils;

public class Response {
    public HTTPType type;
    public final String httpVersion = "HTTP/1.1 ";
    public String status;
    public String contentType;
    public byte[] body;
    public String contentLength(){
        try {
            return String.valueOf(body.length);
        }catch (NullPointerException n){
            return "0";
        }
    }

}