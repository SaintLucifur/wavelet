import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String[] list = new String[50];
    int index = 0;

    public String handleRequest(URI url) {
        if(url.getPath().equals("/")) {
            return String.format("Try some commands!");
        }

        else if(url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("="); 
            for(int i = 0; i < list.length; i++) {
                if(list[i] == null) {
                    continue;
                }
                if(list[i].toLowerCase().contains(parameters[1].toLowerCase())) {
                    return list[i];
                }
                
            }
            return "no such item";
        }
        else {
            System.out.println("Path: " + url.getPath());
            if(url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if(parameters[0].equals("s")) {
                    list[index] = parameters[1];
                    index++;
                    return "Successfully added";
                }
            }
        }
        return "404 not found";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
