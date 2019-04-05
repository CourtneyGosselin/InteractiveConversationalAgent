import java.util.*;

import opennlp.tools.postag.*;
import opennlp.tools.stemmer.PorterStemmer;
import org.alicebot.ab.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
/**
 * YeBot is the main class where the conversation is held.
 */
public class YeBot {

	static Conversation conversation;			

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		PorterStemmer stemmer = new PorterStemmer();
		
		//initialize
		String dir = new File(".").getAbsolutePath();
		System.out.println(dir.substring(0,dir.length()-2));
		MagicBooleans.trace_mode = false;
		Bot yebot = new Bot("YeBot",dir.substring(0,dir.length()-2));
		yebot.writeAIMLFiles();
		String ans;
		
		Chat session = new Chat(yebot);
		conversation = new Conversation();

		String input = "test";
		String output= null;
		ArrayList<String> unknowns = new ArrayList<String>();
		unknowns.add("Wish I could help I dont know what that means");
		unknowns.add("You got good vibes but I dont know what to say to that");
		unknowns.add("Yo man you gotta slow down maybe try saything that a different way");
		unknowns.add("That aint something im knowledgable on maybe say it ina different way");
		unknowns.add("Could you say it in a different way I may just not know what youre on about");
		//String response;
		int i = 1;
			
		while(!conversation.isContained(input)){
			input = null;
			input = conversation.recieveInput();
		
			
			if (input!=""&&input!=null&&input.length()>1||i==1) {
				if(input==""||input==null||input.length()<1) {
					//start conversation
					output = conversation.response("Ye is in the BUILDING!");
					i=0;	
				}else if(conversation.isContained(input)) {
					//user calls for exiting the conversation
					try {
						Thread.sleep(500);
						output = conversation.response(input);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}else{
					//regular response
					if(unknowns.contains(session.multisentenceRespond(input))){
						//POS use to respond if we are given a default response and the input contains a noun
						try(InputStream modelIn = new FileInputStream("res/en-pos-maxent.bin")){
							POSModel model = new POSModel(modelIn);
							POSTaggerME tagger = new POSTaggerME(model);
							String sent[] =  input.split(" ");
							String tags[] = tagger.tag(sent);
							//System.out.println(tags[0]);
							for(int j = 0; j <= tags.length; j++){
								if(tags[j].equals("NN") || tags[j].equals("NNP")){
									output = conversation.response("I ain't know nothing bout " + sent[j]);
									break;
								}
							}
							
						}catch(NullPointerException Npointer){
							//System.out.println("POS issue: " + Npointer);
						}catch(Exception e){
							System.out.println("POS issue: " + e);
						}
					}else{
						output = conversation.response(session.multisentenceRespond(input));
					}
				}
			}	
		}
		System.exit(1); 	//This statement terminates the program	
		
	}
	
	
}
