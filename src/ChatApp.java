
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * This class defines the parameters for the configuration of the chat app and the resources for the appearance of text on the screen.
 * @author Gustavo
 *
 */
public class ChatApp extends Application {
	private boolean isServer = true; //if you want to open the client chat change "true" for "false" and vice versa
	private TextArea messages = new TextArea();
	private Network connection = isServer ? createServer() : createClient();
	
	private Parent createContent() {
		messages.setPrefHeight(550);
		TextField input = new TextField();
		input.setOnAction(event -> {
			String message = isServer ? "Server :" : "Client :";
			message += input.getText();
			input.clear();
			
			messages.appendText(message + "\n");
			try {
				connection.send(message);
			} catch (Exception e) {
				messages.appendText("Message fail" + "\n");
			}
			
		});
		VBox root = new VBox(20, messages, input);
		root.setPrefSize(600, 600);
		return root;
		
	}
/**
 * Method for initiate the connection with the Network.
 */
	public void init() throws Exception {
		connection.startConn();
		
	}
/**
 * Method for initiate the chat app.
 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ChatApp");
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();
		

	}
/**
 * Method for close the connection with the Network.
 */
	public void stop() throws Exception {
		connection.closeConn();
		
	}
/**
 * Creation of a new server with the port 
 * @return new Server
 */
	private Server createServer() {
		return new Server(55555, data -> {
			Platform.runLater(() ->{
				messages.appendText(data.toString() + "\n");				
			});
			
		});
	}
/**
 * Creation of a new client with the port and the ip.
 * @return new Client
 */
	private Client createClient() {
		return new Client("127.0.0.1", 55555, data ->{
			Platform.runLater(() -> {
				messages.appendText(data.toString() + "\n");
				
			});
		});
	}

	public static void main(String[] args) {
		launch(args);
		
	}

}
