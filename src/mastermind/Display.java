
package mastermind;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;
import static javafx.scene.layout.AnchorPane.setBottomAnchor;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;




public  class Display{
    
    
     
    public int[] nums;
    int index;
    int guessNum;
    MasterMind game;
    int[] code;
    MediaPlayer mediaplayer;
    
    Display(){
        nums = new int[] {-1,-1,-1,-1};
        index = 13;
        game = new MasterMind();
        code = game.codeToInts(game.generateSecretCode());
        guessNum = 1;
     }
    public void createWelcomeScreen(){
        Media musicFile = new Media("file:///C:/Users/elev/Desktop/P3/MasterMind/src/bgmusic.wav");
        mediaplayer = new MediaPlayer(musicFile);
        mediaplayer.setAutoPlay(true);
        Image image = new Image("file:///C:/Users/elev/Desktop/P3/MasterMind/src/bg.png");
        ImageView bg = new ImageView();
        bg.setImage(image);
        bg.setFitWidth(520);
        bg.setFitHeight(600);
        Stage stage = new Stage();
        stage.setTitle("Welcome To Master Mind");
        AnchorPane welcomeScreen = new AnchorPane();
        Scene scene = new Scene(welcomeScreen, 520, 600);
        welcomeScreen.getChildren().add(bg);
        
        // create the start button 
        
        Button play = new Button("Start Game");
        setBottomAnchor(play,40.0);
        setLeftAnchor(play, 80.0);
        play.setPrefHeight(50.0);
        play.setPrefWidth(120.0);
        play.setOnAction(new EventHandler<ActionEvent>(){
            @Override
           public void handle(ActionEvent event){
            stage.close();
            createGameScreen();
         }
        });
               
        // create the quit button .
        Button quit = new Button("Quit");
        setBottomAnchor(quit,40.0);
        setLeftAnchor(quit, 330.0);
        quit.setPrefWidth(120);
        quit.setPrefHeight(50);
        quit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
           public void handle(ActionEvent event){
            stage.close();
            System.exit(0);
         }
        });
        welcomeScreen.getChildren().addAll(play,quit);
        stage.setScene(scene);
        stage.show();
        }
    public void createGameScreen() {
        Stage stage = new Stage();
        stage.setTitle("Master Mind");
        stage.setFullScreen(false);
        AnchorPane gameScreen = new AnchorPane();
        Scene scene = new Scene(gameScreen, 800, 650);
        
    
    HBox options = new HBox();
    options.setSpacing(30);
    
    for(int i=0; i<4; i++){
      int x = i;
      
    Circle c = new Circle(15.0);
    options.getChildren().add(c);
    c.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
           public void handle(MouseEvent event){
            toggleColor(c, x);
         }
        });
    }
    setBottomAnchor(options, 30.0);
    setLeftAnchor(options,254.0);
    
    Button submit = new Button("Submit");
    submit.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
        if (index > 3) {
           createGuessCircles(gameScreen, stage);
        } else {
           playAgainScreen(false, stage);
            for (int i = 0; i<4; i++) {
                System.out.print(code[i]);
                 }
            }
        }
    });
    submit.setPrefHeight(50);
    submit.setPrefWidth(120);
    setBottomAnchor(submit, 100.0);
    setLeftAnchor(submit, 50.0);
    
    Button exit = new Button("Exit");
    exit.setOnAction(new EventHandler<ActionEvent>(){
        @Override
                public void handle(ActionEvent event){
        System.exit(0);
        }
    });
    exit.setPrefHeight(50);
    exit.setPrefWidth(120);
    setBottomAnchor(exit, 25.0);
    setLeftAnchor(exit, 50.0);
    
    gameScreen.getChildren().addAll(options, submit, exit);
    stage.setScene(scene);
    stage.show();
    }
  
    public void toggleColor(Circle c, int index){

     if(nums[index]==5){
        nums[index]= 0;
        }else{
            nums[index]++;
        }
        changeColor(c, index);
}
    public void changeColor(Shape c, int index){
     switch(nums[index]){
         case 0:{
         c.setFill(Color.BLUE);break;
        }
        case 1:{
         c.setFill(Color.GREEN);break;
        }
        case 2:{
         c.setFill(Color.ORANGE);break;
        }
        case 3:{
         c.setFill(Color.BROWN);break;
        }
        case 4:{
         c.setFill(Color.RED);break;
        }
        case 5:{
         c.setFill(Color.YELLOW);break;
        }
    }   
}
    
public void createGuessCircles(AnchorPane a, Stage stage){
    HBox guess = new HBox();
    guess.setSpacing(30.0);
    Label whichGuess = new Label();
    whichGuess.setText(""+guessNum);
    guessNum++;
    guess.getChildren().add(whichGuess);
    
    for(int i=0; i<4; i++){
        Circle c = new Circle(15);
        changeColor(c, i);
        guess.getChildren().add(c);
    }
    Separator s = new Separator();
    s.setOrientation(Orientation.VERTICAL);
    guess.getChildren().add(s);
    
    int[] feedback = game.getFeedback(code, nums);
    
    if(feedback[0]==4){
        this.playAgainScreen(true, stage);
    }
    for(int i=0; i<4; i++){
        Circle cs = new Circle(15);
        guess.getChildren().add(cs);
        
        if(feedback[0]>0){
            cs.setFill(Color.BLACK);
            feedback[0]--;
        }
        else if (feedback[1]>0){
            cs.setFill(Color.WHITE);
            feedback[1]--;
        }
        else{
            cs.setFill(Color.CADETBLUE);
        }
       
    }
    if(guessNum!=11){
        setLeftAnchor(guess, 220.0);
    }
    else{
        setLeftAnchor(guess, 214.0);
    }
    index--;
    setTopAnchor(guess, index * 43.0);
    
    a.getChildren().add(guess);
}
    public void playAgainScreen(Boolean won, Stage s){
        Stage stage = new Stage();
        AnchorPane playAgain = new AnchorPane();
        Scene scene = new Scene(playAgain, 300, 100);
        
        Label message = new Label();
        setTopAnchor(message, 10.0);
        setLeftAnchor(message, 105.0);
        Label showCode = new Label();
        showCode.setVisible(false);
        if(won){
            stage.setTitle("Yon Win");
            message.setText("Congrats! Yo Win!");
        }
        else {
            stage.setTitle("You Lose!");
            message.setText("Sorry! You Lose!\n");
            showCode.setText("The code was"+ ' ' + game.codeForPlayAgain(code));
            showCode.setVisible(true);
            setTopAnchor(showCode, 28.0);
            setLeftAnchor(showCode, 40.0);
            
        }
        Button play = new Button("Play Again");
        setTopAnchor(play, 60.0);
        setLeftAnchor(play, 40.0);
        play.setPrefHeight(15);
        play.setPrefWidth(80);
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                    public void handle(ActionEvent event){
                        s.close();
                        stage.close();
                         (new Display()).createGameScreen();
                }
            
        });
        Button quit = new Button("Quit");
        setTopAnchor(quit, 60.0);
        setLeftAnchor(quit, 180.0);
        quit.setPrefHeight(15);
        quit.setPrefWidth(80);
        quit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
                    public void handle(ActionEvent event){
                       
                        System.exit(0);
            }
           
        });
        
        playAgain.getChildren().addAll(message, showCode, play, quit);
        stage.setScene(scene);
        stage.show();
    }
    
   
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
