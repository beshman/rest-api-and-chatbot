import org.jibble.pircbot.PircBot;

public class apibot extends PircBot {

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        StringBuilder buffer;
        //check if the message contains key phrases
        if (message.contains("Weather") || message.contains("How’s the weather in")) {
            buffer = new StringBuilder();
            //find the zip code in the message and save it
            for (int i = 0; i < message.length(); i++) {
                //check that the character is a digit
                if (message.charAt(i) >= '0' && message.charAt(i) <= '9') {
                    buffer.append(message.charAt(i));
                } else if (buffer.length() > 0) {
                    //stop the cycle if there are other characters after the digit
                    break;
                }
            }
            //send a response to the server
            sendMessage(channel, api.getWeather(Integer.parseInt(buffer.toString())));
        } else if (message.contains("Price in")) {
            buffer = new StringBuilder();
            for (int i = 0; i < message.length(); i++) {
                //check that the character is a digit
                if (message.charAt(i) >= '0' && message.charAt(i) <= '9') {
                    buffer.append(message.charAt(i));
                } else if (buffer.length() > 0) {
                    //stop the cycle if there are other characters after the digit
                    break;
                }
            }
            //send a response to the server
            sendMessage(channel, "EARLY BIRD KEY price: " + api.getPrice(Integer.parseInt(buffer.toString())));
        }
    }

    public static void main(String[] args) throws Exception {
        apibot bot = new apibot();
        bot.setName("WeatherBot");
        bot.connect("irc.freenode.net");
        //The channel which the bot will join.
        //Join the channel
        bot.joinChannel("#JavaWeatherRoom");
    }
}
