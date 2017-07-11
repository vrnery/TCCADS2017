package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TelaInicial extends Application {

	private AnchorPane painel;
	private Label lblMensagem;
	private Button btEntrar;
	private static Stage stage;
	
	public static Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		initComponents();
		initListeners();
		Scene scene = new Scene(painel);
		stage.setScene(scene);
		scene.getStylesheets().add("css/SILVeicular.css");
		stage.setResizable(false);
		stage.setTitle("Sistema de Identificação de Placa Veicular");
		stage.show();
		initLayout();
		TelaInicial.stage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void initComponents() {
		painel = new AnchorPane();
		painel.setPrefSize(600, 250);
		//painel.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, blue 0%, silver 100%);");
		lblMensagem = new Label("Prototipo do Sistema de Identificação de Placa Veicular\n"
				+ "Apresentação de TCC.");
		btEntrar = new Button("Entrar...");
		painel.getChildren().addAll(lblMensagem, btEntrar);
	}
	
	private void initLayout() {
		lblMensagem.setLayoutX((painel.getWidth() - lblMensagem.getWidth()) / 2);
		lblMensagem.setLayoutY(50);
		lblMensagem.setTextAlignment(TextAlignment.CENTER);
		btEntrar.setLayoutX((painel.getWidth() - btEntrar.getWidth()) / 2);
		btEntrar.setLayoutY(painel.getHeight() - (btEntrar.getHeight() * 2));
	}
	
	private void initListeners() {
		btEntrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				entrar();
			}
		});
	}
	
	private void entrar() {
		try {
			new TelaPrincipal().start(new Stage());
			TelaInicial.getStage().close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
