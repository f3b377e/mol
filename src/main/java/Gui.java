import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import java.util.*;

/**
 * Class of graphic interface
 */
public class Gui {
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Pane generateInputPane;
    private HBox hBox;
    private StackPane stackPane;
    private TableView table;
    private TextField search;
    private Menu file, edit, view;
    private HashMap<KeyCombination, Boolean> keyPressed;
    private double prefWidth = 800.0;
    private double prefHeight = 600.0;
    private double prefMenuHeight=30;

    /**
     * builder that calls methods for configuring the interface
     */
    public Gui(){
        try {
            initKeyPressed();
            initGenerateInputPane();
            initMenu();
            initSearch();
            inithBox();
            initTable();
            initStackPane();
            initRootElement();
        }catch (Exception e){
            errorMessage("Something Wrong!","Error Message: "+e.getMessage());
        }
    }

    /**
     * init Keys structure, and set all keys to false.
     */
    private void initKeyPressed(){
        keyPressed = new HashMap<>();
        keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+M"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+G"), false);
    }

    /**
     * sets all keys to false except the input in true;
     * @param k input key
     */
    private void setKeyPressed(KeyCombination k){
        for(Map.Entry<KeyCombination, Boolean> entry : keyPressed.entrySet()) {
            KeyCombination key = entry.getKey();
            keyPressed.put(key,false);
        }
        keyPressed.put(k,true);
    }
    /**
     * set and init the Horizontal Box used to contain search text filed
     */
    private void inithBox(){
        hBox = new HBox();
        hBox.getChildren().add(search);
        hBox.setMaxHeight(25);
        hBox.setStyle("-fx-padding: 0 0 0 2; -fx-background-color: transparent;");
    }
    /**
     * set and init the stackPane. StackPane contain ScrollPane and consequently the Table.
     */
    private void initStackPane(){
        stackPane = new StackPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(table);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        stackPane.getChildren().add(scrollPane);
        stackPane.setAlignment(Pos.TOP_LEFT);
    }


    /**
     * Set and initialize Menu Item
     */
    private void initMenu(){
        menuBar = new MenuBar();
        initMenuFile();
        initMenuEdit();
        initMenuView();
        menuBar.getMenus().addAll(file, edit, view);
    }
    /**
     * set and initialize file section Menu
     */
    private void initMenuFile(){
        file = new Menu("File");
        MenuItem load = new MenuItem("Load");
        MenuItem save = new MenuItem("Save");
        MenuItem save_as = new MenuItem("Save as ");
        MenuItem quit = new MenuItem("Quit");
        load.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        save_as.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
        quit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));

        quit.setOnAction(e-> {
            if (confirmMessage("Exit Confirmation", "Are u sure u want to exit?"))
                System.exit(0);
        });

        file.getItems().addAll(load, save, save_as, quit);
    }
    /**
     * set and initialize edit section Menu
     */
    private void initMenuEdit(){
        edit = new Menu("Edit");
        MenuItem find = new MenuItem("Find");
        MenuItem delete = new MenuItem("Delete");
        MenuItem genInputMap = new MenuItem("Generate Input");
        delete.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
        genInputMap.setAccelerator(KeyCombination.keyCombination("Ctrl+G"));
        genInputMap.setOnAction(e->{
            if (!keyPressed.get(KeyCombination.keyCombination("Ctrl+G"))) {
                setKeyPressed(KeyCombination.keyCombination("Ctrl+G"));
                borderPane.setCenter(generateInputPane);
            }
        });

        find.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        find.setOnAction(e -> {
            if (keyPressed.get(KeyCombination.keyCombination("Ctrl+F"))) {
                stackPane.getChildren().remove(hBox);
                keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
            } else {
                stackPane.getChildren().add(hBox);
                search.requestFocus();
                keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), true);
            }
        });

        edit.getItems().addAll(genInputMap,delete, find);
    }
    private void initGenerateInputPane(){
        generateInputPane = new StackPane();
        GridPane container = new GridPane();
        List<Label> labels = new ArrayList<>();
        List<TextInputControl> textList = new ArrayList<>();
        HBox headerBox = new HBox();
        Button gen, cancel;
        gen = new Button("Generate");
        gen.setOnAction(e->{});
        cancel = new Button("Cancel");
        cancel.setOnAction(e-> textList.forEach(javafx.scene.control.TextInputControl::clear));
        Label headerLabel = new Label("Generate Input");
        headerLabel.setStyle("-fx-font-family: 'Goha-Tibeb Zemen'; -fx-font-size: 20px; -fx-text-fill: coral;");
        headerBox.setStyle("-fx-alignment: top-left; -fx-padding: 20 0 20 -20;");
        headerBox.getChildren().add(headerLabel);
        labels.add(new Label("Numero Nodi: "));
        labels.add(new Label("Dimensione Nodo: "));
        labels.add(new Label("Dimensione Raggio:"));
        labels.add(new Label("Superfice non amm.:"));
        labels.add(new Label("Colore Nodo: "));
        labels.add(new Label("Colore Arco: "));
        labels.add(new Label("Archi orientati: "));
        for (int i=0;i<labels.size(); i++){
            labels.get(i).setStyle("-fx-pref-height:25px; -fx-alignment: center-left; " +
                    "-fx-font-family: 'Goha-Tibeb Zemen'; -fx-font-size: 14px;");
            TextField t = new TextField();
            t.setStyle("-fx-max-width: 65px; -fx-alignment: center");
            textList.add(i, t);
            container.add(labels.get(i),0,i+1);
            container.add(t,1,i+1);
        }
        container.add(headerBox,0,0,2,1);
        container.add(gen,0, labels.size()+1);
        container.add(cancel,1,labels.size()+1);
        container.setStyle("-fx-hgap: 10px; -fx-vgap: 5px; -fx-padding: 0 0 0 40px; -fx-background-color: white;");
        generateInputPane.getChildren().add(container);
    }
    /**
     * set and initialize View section Menu
     */
    private void initMenuView(){
        view = new Menu("View");
        MenuItem Orders = new MenuItem("Orders");
        MenuItem Clients = new MenuItem("Clients");
        MenuItem Goods = new MenuItem("Goods");
        MenuItem Itineraries = new MenuItem("Itineraries");
        MenuItem Vehicles = new MenuItem("Vehicles");
        MenuItem Maps = new MenuItem("Maps");
        Orders.setAccelerator(KeyCombination.keyCombination("Alt+O"));
        Clients.setAccelerator(KeyCombination.keyCombination("Alt+C"));
        Goods.setAccelerator(KeyCombination.keyCombination("Alt+G"));
        Itineraries.setAccelerator(KeyCombination.keyCombination("Alt+I"));
        Vehicles.setAccelerator(KeyCombination.keyCombination("Alt+V"));
        Maps.setAccelerator(KeyCombination.keyCombination("Alt+M"));

        view.getItems().addAll(Orders, Clients, Goods, Vehicles, Itineraries, Maps);

        Maps.setOnAction(event -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+M"))) {
                setKeyPressed(KeyCombination.keyCombination("Alt+M"));
            }
        });
    }
    /**
     * Set and initialize table
     */
    private void initTable(){
        double offset = 0.003;
        table = new TableView();
        table.setEditable(false);
        table.setTableMenuButtonVisible(true);
        table.setMinSize(prefWidth-(prefWidth*offset),prefHeight-prefMenuHeight-(prefHeight*offset));
    }
    /**
     * Set and initialize search textView item
     */
    private void initSearch(){
        search = new TextField();
        search.setPromptText("Search");
        search.setMinWidth(25.0);
    }
    /**
     * Set and initialize Root Item
     */
    private void initRootElement(){
        borderPane = new BorderPane();
        borderPane.setPrefSize(prefWidth,prefHeight);
        borderPane.setCenter(stackPane);
        borderPane.setTop(menuBar);
    }
    /**
     * @return main panel
     */
    public Pane getRootElement(){
        return borderPane;
    }
    /**
     * Setup general alert window
     * @param alert alert to setup
     * @param title title of the alert
     * @param content body of the alert
     * @return alert's return value
     */
    private Optional<ButtonType> setupMessage(Alert alert, String title, String content) {
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        return alert.showAndWait();
    }
    /**
     * Error alert with the given title and header
     * @param title title of the error alert
     * @param content body of the error alert
     */
    private void errorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        setupMessage(alert, title, content);
    }
    /**
     * Confirm message with the given title and header
     * @param title title of the confirm alert
     * @param content body of the confirm alert
     * @return alert's return value
     */
    private boolean confirmMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        return setupMessage(alert, title, content).get() != ButtonType.CANCEL;
    }
}
