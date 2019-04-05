# InteractiveConversationalAgent
**Purpose:**
The Interactive Conversational Agent allows an individual to hold a conversation of at least 30 turns. The agent is a celebrity and the user can be anyone. The celebrity chosen is Kanye West. The conversation will be primarily question/answer based, but there may be certain specific responses for some non-question statements. The responses from the chat agent is a collection of tweets and song lyrics by Kanye West himself.

## Class Organization

**Breakdown of the classes:**
* BDialog: This class is responsible to create a Graphical User Interface for the conversation between the user and the Chatbot. 
* Conversation: This class is responsible to communicate and transfer user inputs and chatbot outputs. 
* YeBot: YeBot is the main class for the interaction between a user and the chatbot. 

## How to Compile and Run the Code
**Enter the following code into command line to run Yebot:**
* javac BDialog.java Conversation.java YeBot.javajava YeBot

**Or run the yebot.java file**

## Programmed Features
* GUI
   * The GUI was working in A2. However, improvements were made to this feature. This included aesthetic choices, ability to change the chat box size, as well as the removal of a tiny window that popped up along with the conversational window.
* Topics the agent covers:
   * The agenet responds to the user in several ways:
      * A sentence based on a former social media post for example twitter
      * A sentence based on song lyrics
      * A sentence based on interviews 
   * General Conversation
    * Himself
    * His family and pets
    * Personal Interests and hobbies
    * President of USA
    * His favourite colour etc.
* Reasonable responses for when the user enters something Yebot doesn't know
   * "Wish I could help    I dont know what that means"
   * "You got good vibes   but I dont know what to say to that"
   * "Yo man you gotta slow down  maybe try saying it a different way"
   * "That aint something im knowledgeable on maybe say it ina  different way"
   * "Could you say it in a different way I may just not know what youre on about"
* Spell checkb - Not Implemented Currently 
* Language toolkits
    * Synonym recognition -WordNet (you'll need a Java API to it)
    * POS tagging -Stanford toolkit
      * POS tagging was implemented to give responses when a noun is found and otherwise defualt answer would of been used
    * OpenNLP Named entity recognition -Stanford toolkit, 
    * OpenNLP Phrasal -Stanford toolkit
    * Coreference Resolution -Stanford toolkit, 
    * OpenNLP Sentiment analysis tools -Stanford toolkit
* Conversation with another agent in class
* Fixed Issues
  * Null response running every second in background removed

## Documentation
Found in the files folder and ReadMe File
* Level 0 DFD
* Level 1 DFD
* Submission of your GitHub repository
* Graph showing different features developed on seperate branh w commits amde to repo
* Include sample output of dialogue 30 turns showing the new features
* Document a list of limitation fo the program showing two short version of what it cannot handle
* 5+ Features that can be extracted from the code or design that can be shared with others as an API



## Built With

* [Java](https://www.java.com/) - Programming language 
* [AIML](https://www.tutorialspoint.com/aiml/) - AIML dialogue





