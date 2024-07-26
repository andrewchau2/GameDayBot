package org.fightbattle;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.springframework.web.util.UriComponentsBuilder;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class Listener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        
        Message messages = event.getMessage();
        String content = messages.getContentRaw();
        if (content.equals("!test"))
        {
           try {
                URL url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port("8080")
                .path("games")
                .build().toUri().toURL();   

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                System.out.println("JSON data:" + inline);
                MessageChannel channel = event.getChannel();
                channel.sendMessage(inline).queue();
                //Close the scanner
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                // JSONParser parse = new JSONParser();
                // JSONObject data_obj = (JSONObject) parse.parse(inline);
                
                // //Get the required object from the above created object
                // JSONObject obj = (JSONObject) data_obj.get("Global");
                
                // //Get the required data using its key
                //  System.out.println(obj.get("TotalRecovered"));


            } catch (Exception e) {
                // TODO: handle exception
            }
            
        }

        if (content.equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }

    }
}
