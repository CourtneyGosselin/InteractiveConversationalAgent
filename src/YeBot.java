import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;

import edu.smu.tspell.wordnet.*;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.*;
//import net.didion.jwnl.JWNLException;
//import net.didion.jwnl.data.Synset;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.stemmer.PorterStemmer;
//import edu.mit.jwi.Dictionary;
/**
 * YeBot is the main class where the conversation is held.
 */
public class YeBot {

	static Conversation conversation;			

	public static void main(String[] args) throws FileNotFoundException, IOException{
	
	
		Bot yebot = null;
		ArrayList<String> unknowns;
		Chat session;
		
		WordNetDatabase database = null;
		
		//Note you need to have wordnet installed for this program to work with it
		try{
			System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict\\");
			database = WordNetDatabase.getFileInstance();
		}catch(Exception e){
			System.out.println("Download princetons word net to run the synonns properly. See Yebot.java ");
		}
		
		
		PorterStemmer stemmer = new PorterStemmer();
	
		//initialize
		String dir = new File(".").getAbsolutePath();
		System.out.println(dir);
		System.out.println(dir.substring(0,dir.length()-1));
		//dir = dir.substring(0,dir.length()-1)+"bots\\YeBot\\aiml";
		MagicBooleans.trace_mode = false;
		//yebot = new Bot("YeBot", dir);
		yebot = new Bot("YeBot", dir.substring(0,dir.length()-1));
		yebot.writeAIMLFiles();
		String ans;
				
		//Arraylist of default answers 
		unknowns = new ArrayList<String>();
		unknowns.add("Wish I could help I dont know what that means");
		unknowns.add("You got good vibes but I dont know what to say to that");
		unknowns.add("Yo man you gotta slow down maybe try saything that a different way");
		unknowns.add("That aint something im knowledgable on maybe say it ina different way");
		unknowns.add("Could you say it in a different way I may just not know what youre on about");
				
	
		session = new Chat(yebot);
		conversation = new Conversation();

		String input = "test";
		String output= null;
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
					branch:
					if(unknowns.contains(session.multisentenceRespond(input))){
						String sent[] =  input.split(" ");
						
						try{
							for(int k= 0; k <sent.length; k++){
								Synset[] syns = database.getSynsets(sent[k]);
								if(syns.length!=0){
									for(Synset syn : syns){
										String[] synonym = syn.getWordForms(); 
										for(String s : synonym){
											String newInput = input.replaceAll(sent[k]+" ", s+" ");
											System.out.println(newInput);
											output = session.multisentenceRespond(newInput);
											if(!unknowns.contains(output)){
												System.out.println("bentest");
												conversation.response(output);
												break branch;
												
											}
											
										}
										
									}
								}
							}
						}catch(Exception e){
							System.out.println("Wordnet issue "+ e);
						}
						
						try(InputStream inputStreamNameFinder = new FileInputStream("res/en-ner-person.bin"); ){
							
							TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
							NameFinderME nameFinder = new NameFinderME(model);
							String[] inputs = {input};
							Span nameSpans[] = nameFinder.find(inputs);
							
							for(Span s: nameSpans){
									output = conversation.response("I ain't heard of that" + s.toString().substring(s.toString().lastIndexOf(" ")));
								
							}
							break branch;
							
						}catch(NullPointerException Npointer){
							//System.out.println("POS issue: " + Npointer);
						}catch(Exception e){
							System.out.println("Name issue: " + e);
						}
						
						//POS use to respond if we are given a default response and the input contains a noun
						try(InputStream modelIn = new FileInputStream("res/en-pos-maxent.bin")){
							POSModel model = new POSModel(modelIn);
							POSTaggerME tagger = new POSTaggerME(model);
						
							String tags[] = tagger.tag(sent);
							//System.out.println(tags[0]);
							for(int j = 0; j < tags.length; j++){
								if(tags[j].equals("NN") || tags[j].equals("NNP")){
									output = conversation.response("I ain't know nothing bout " + sent[j]);
									break;
								}else{
									output = conversation.response(session.multisentenceRespond(input));
								}
							}
							break branch;
							
						}catch(NullPointerException Npointer){
							//System.out.println("POS issue: " + Npointer);
						}catch(Exception e){
							System.out.println("POS issue: " + e);
						}
					
						//output = conversation.response(output);
					}else{
						output = conversation.response(session.multisentenceRespond(input));
					}
				}
			}	
		}
		System.exit(1); 	//This statement terminates the program	
		
	}
	
	
}
