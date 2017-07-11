package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.view;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.imageio.ImageIO;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller.ProcessamentoController;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Placa;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {

	private VBox vbox;
	private AnchorPane painel;
	private MenuBar mbBar;
	private Menu mnArquivo;
	private Menu mnConfigurar;
	private MenuItem mtAbrirFoto;
	private MenuItem mtSair;
	private ImageView imgFoto, imgPlaca, imgSeg1, imgSeg2, imgSeg3, imgSeg4, imgSeg5, imgSeg6, imgSeg7;
	private Label lblSeg1, lblSeg2, lblSeg3, lblSeg4, lblSeg5, lblSeg6, lblSeg7;
	private Button btProcessar;
	private TableView<ItensVeiculo> tbVeiculo;
	private TableColumn<ItensVeiculo, String> columnData;
	private TableColumn<ItensVeiculo, String> columnPlaca;
	private TableColumn<ItensVeiculo, String> columnSituacao;
	private TableColumn<ItensVeiculo, String> columnDtSituacao;
	//private static Placa placaFoto;
	private static ObservableList<ItensVeiculo> listItens = FXCollections.observableArrayList();
	private File file;
	private static Stage stage;
		
	public static Stage getStage() {
		return stage;
	}
	
	public static void setPlacaFoto(Placa pl) {
		//placaFoto = pl;
	}
	
	public static ObservableList<ItensVeiculo> getListItens() {
		return listItens;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		initComponents();
		initListeners();
		Scene scene = new Scene(new VBox(), 800, 570);
		((VBox) scene.getRoot()).getChildren().addAll(mbBar, painel);
		stage.setScene(scene);
		scene.getStylesheets().add("css/SILVeicular.css");
		stage.setResizable(false);
		stage.setTitle("Sistema de Identificação de Placa Veicular");
		stage.show();
		initLayout();
		TelaPrincipal.stage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void initComponents() {
		vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 10, 0, 10));
		painel = new AnchorPane();
		painel.setPrefSize(800, 570);
		
		mtAbrirFoto = new MenuItem("Abrir Foto");
		MenuItem mtAbrirDiretorio = new MenuItem("Abrir Diretorio");
		mtSair = new MenuItem("Sair");
		mnArquivo = new Menu("Arquivo");
		mnArquivo.getItems().addAll(mtAbrirFoto, mtAbrirDiretorio, mtSair);
		
		MenuItem mtConfCamera = new MenuItem("Camera");
		mnConfigurar = new Menu("Configurar");
		mnConfigurar.getItems().addAll(mtConfCamera);
		
		mbBar = new MenuBar();
		mbBar.getMenus().addAll(mnArquivo, mnConfigurar);
		
		//placaFoto = new Placa();
		imgFoto = new ImageView("image/ifrs.jpg");
		imgFoto.setFitWidth(320);
		imgFoto.setFitHeight(240);
		imgPlaca = new ImageView("image/ifrs.jpg");
		imgPlaca.setFitWidth(180);
		imgPlaca.setFitHeight(70);
		imgSeg1 = new ImageView("image/ifrs.jpg");
		imgSeg1.setFitWidth(25);
		imgSeg1.setFitHeight(40);
		imgSeg2 = new ImageView("image/ifrs.jpg");
		imgSeg2.setFitWidth(25);
		imgSeg2.setFitHeight(40);
		imgSeg3 = new ImageView("image/ifrs.jpg");
		imgSeg3.setFitWidth(25);
		imgSeg3.setFitHeight(40);
		imgSeg4 = new ImageView("image/ifrs.jpg");
		imgSeg4.setFitWidth(25);
		imgSeg4.setFitHeight(40);
		imgSeg5 = new ImageView("image/ifrs.jpg");
		imgSeg5.setFitWidth(25);
		imgSeg5.setFitHeight(40);
		imgSeg6 = new ImageView("image/ifrs.jpg");
		imgSeg6.setFitWidth(25);
		imgSeg6.setFitHeight(40);
		imgSeg7 = new ImageView("image/ifrs.jpg");
		imgSeg7.setFitWidth(25);
		imgSeg7.setFitHeight(40);
		lblSeg1 = new Label("T");
		lblSeg1.setPrefSize(12.0, 15.0);
		lblSeg2 = new Label("C");
		lblSeg2.setPrefSize(12.0, 15.0);
		lblSeg3 = new Label("C");
		lblSeg3.setPrefSize(12.0, 15.0);
		lblSeg4 = new Label("N");
		lblSeg4.setPrefSize(12.0, 15.0);
		lblSeg5 = new Label("E");
		lblSeg5.setPrefSize(12.0, 15.0);
		lblSeg6 = new Label("R");
		lblSeg6.setPrefSize(12.0, 15.0);
		lblSeg7 = new Label("Y");
		lblSeg7.setPrefSize(12.0, 15.0);
		btProcessar = new Button("Processar");
		btProcessar.setDisable(true);
		tbVeiculo = new TableView<ItensVeiculo>();
		tbVeiculo.setPrefSize(780.0, 230.0);
		columnData = new TableColumn<ItensVeiculo, String>();
		columnData.setText("Data:");
		columnData.setCellValueFactory(new PropertyValueFactory<ItensVeiculo, String>("data"));
		columnData.setPrefWidth(150);
		columnPlaca = new TableColumn<ItensVeiculo, String>();
		columnPlaca.setText("Placa:");
		columnPlaca.setCellValueFactory(new PropertyValueFactory<ItensVeiculo, String>("placa"));
		columnPlaca.setPrefWidth(100);
		columnSituacao = new TableColumn<ItensVeiculo, String>();
		columnSituacao.setText("Situação:");
		columnSituacao.setCellValueFactory(new PropertyValueFactory<ItensVeiculo, String>("situacao"));
		columnSituacao.setPrefWidth(150);
		columnDtSituacao = new TableColumn<ItensVeiculo, String>();
		columnDtSituacao.setText("Data da Verificação:");
		columnDtSituacao.setCellValueFactory(new PropertyValueFactory<ItensVeiculo, String>("dtSituacao"));
		columnDtSituacao.setPrefWidth(378);
		tbVeiculo.getColumns().add(columnData);
		tbVeiculo.getColumns().add(columnPlaca);
		tbVeiculo.getColumns().add(columnSituacao);
		tbVeiculo.getColumns().add(columnDtSituacao);
		listItens.add(new ItensVeiculo(Calendar.getInstance(), "TCCNERY", "Aprovado", "2017"));
		tbVeiculo.setItems(listItens);
		painel.getChildren().addAll(imgFoto, imgPlaca, imgSeg1, imgSeg2, imgSeg3, imgSeg4, imgSeg5, imgSeg6, imgSeg7,
				lblSeg1, lblSeg2, lblSeg3, lblSeg4, lblSeg5, lblSeg6, lblSeg7,
				btProcessar, tbVeiculo);
	}
	
	private void initLayout() {
		mnArquivo.getStyleClass().add("menu-active");
		imgFoto.setLayoutX(10);
		imgFoto.setLayoutY(10);
		imgPlaca.setLayoutX(350);
		imgPlaca.setLayoutY(10);
		imgPlaca.getStyleClass().add("image-border");
		imgSeg1.setLayoutX(350);
		imgSeg1.setLayoutY(90);
		imgSeg1.getStyleClass().add("image-border");
		imgSeg2.setLayoutX(376);
		imgSeg2.setLayoutY(90);
		imgSeg2.getStyleClass().add("image-border");
		imgSeg3.setLayoutX(402);
		imgSeg3.setLayoutY(90);
		imgSeg3.getStyleClass().add("image-border");
		imgSeg4.setLayoutX(428);
		imgSeg4.setLayoutY(90);
		imgSeg4.getStyleClass().add("image-border");
		imgSeg5.setLayoutX(454);
		imgSeg5.setLayoutY(90);
		imgSeg5.getStyleClass().add("image-border");
		imgSeg6.setLayoutX(480);
		imgSeg6.setLayoutY(90);
		imgSeg6.getStyleClass().add("image-border");
		imgSeg7.setLayoutX(506);
		imgSeg7.setLayoutY(90);
		imgSeg7.getStyleClass().add("image-border");
		lblSeg1.setLayoutX(357);
		lblSeg1.setLayoutY(130);
		lblSeg2.setLayoutX(383);
		lblSeg2.setLayoutY(130);
		lblSeg3.setLayoutX(409);
		lblSeg3.setLayoutY(130);
		lblSeg4.setLayoutX(435);
		lblSeg4.setLayoutY(130);
		lblSeg5.setLayoutX(461);
		lblSeg5.setLayoutY(130);
		lblSeg6.setLayoutX(487);
		lblSeg6.setLayoutY(130);
		lblSeg7.setLayoutX(513);
		lblSeg7.setLayoutY(130);
		btProcessar.setLayoutX(10);
		btProcessar.setLayoutY(260);
		tbVeiculo.setLayoutX((painel.getWidth() - tbVeiculo.getWidth()) / 2);
		tbVeiculo.setLayoutY(300.0);
	}
	
	private void initListeners() {
		btProcessar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				processarFoto();
			}
		});
		mtAbrirFoto.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				abrirFoto();
			}
		});
		mtSair.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fecharAplicacao();
			}
		});
	}
	
	private void fecharAplicacao() {
		System.exit(0);
	}
	
	private void abrirFoto() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selecionar foto");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.*", new String[]{"*.bmp", "*.BMP", "*.Bmp", "*.jpg", "*.JPG", "*.Jpg", "*.jpeg", "*.Jpeg", "*.JPEG", "*.png", "*.Png", "*.PNG"}),
				new FileChooser.ExtensionFilter("BMP", "*.bmp"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
		file = fileChooser.showOpenDialog(stage);
		
		if (file != null) {
			imgFoto.setImage(new Image("file:"+file.getPath()));
			btProcessar.setDisable(false);
		}
		
	}
	
	private void processarFoto() {
		ProcessamentoController processamento = new ProcessamentoController();
		try {
			btProcessar.setDisable(true);
			processamento.processarBufferedImage(ImageIO.read(file));
			carregarImgPlaca();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btProcessar.setDisable(false);
	}
	
	private void carregarImgPlaca() {
		lblSeg1.setText(tbVeiculo.getItems().get(0).getPlaca().substring(0, 1));
		lblSeg2.setText(tbVeiculo.getItems().get(0).getPlaca().substring(1, 2));
		lblSeg3.setText(tbVeiculo.getItems().get(0).getPlaca().substring(2, 3));
		lblSeg4.setText(tbVeiculo.getItems().get(0).getPlaca().substring(3, 4));
		lblSeg5.setText(tbVeiculo.getItems().get(0).getPlaca().substring(4, 5));
		lblSeg6.setText(tbVeiculo.getItems().get(0).getPlaca().substring(5, 6));
		lblSeg7.setText(tbVeiculo.getItems().get(0).getPlaca().substring(6, 7));
		imgPlaca.setImage(new Image("file:placa.jpg"));
		imgSeg1.setImage(new Image("file:s1.jpg"));
		imgSeg2.setImage(new Image("file:s2.jpg"));
		imgSeg3.setImage(new Image("file:s3.jpg"));
		imgSeg4.setImage(new Image("file:s4.jpg"));
		imgSeg5.setImage(new Image("file:s5.jpg"));
		imgSeg6.setImage(new Image("file:s6.jpg"));
		imgSeg7.setImage(new Image("file:s7.jpg"));
	}

}
