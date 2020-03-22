/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Boite;
import beans.Contact;
import donnees.DContact;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 *
 * @author NGEREZA
 */
public class CContact implements Initializable {

    @FXML
    TableView<Contact> tableau;
    private TableColumn<Contact, Number> col_id;
    private TableColumn<Contact, String> col_num, col_nom;

    @FXML
    private TextField tid, tnum, tnom, tch;
    @FXML
    private TextArea tm;

    private DContact dc = new DContact();
    private Contact cont;

    public CContact() {
        cont = new Contact();
    }

    //créer un contact
    @FXML
    private void ajouter(ActionEvent ev) {

        //verification des champs
        if (Boite.verfier(tnum,tnom)) {

            cont = new Contact();
            cont.setId((int) (System.currentTimeMillis() / 100));
            cont.setNumero(tnum.getText());
            cont.setNom(tnom.getText());

            try {
                if (Boite.showConfirmation("Créer le contact?", "Ajout")) {
                    if (dc.create(cont)) {
                        effacer();
                        Boite.showInformation("Contact créer", "Ajout", Alert.AlertType.INFORMATION);
                    }
                }
            } catch (Exception ex) {
                Boite.showException(ex, "Ajout");
            }

        } 
    }

    //recherche
    @FXML
    private void chercher(Event ev) {

        try {

            if (!tch.getText().isBlank()) {
                cont = new Contact(0, "", tch.getText());
                tableau.setItems(dc.getList(cont));
            } else {
                effacer();
            }

        } catch (Exception ex) {
            Boite.showException(ex, "chercher");
        }

    }

    //selection dans le tableau
    @FXML
    private void tableau(Event ev) {
        if (tableau.getSelectionModel().getSelectedItem() != null) {
            cont = tableau.getSelectionModel().getSelectedItem();
            tid.setText(String.valueOf(cont.getId()));
            tnum.setText(cont.getNumero());
            tnom.setText(cont.getNom());
        }
    }

    //modifier un contact
    @FXML
    private void modifier(ActionEvent ev) {

        //verification des champs
        if (Boite.verfier(tid,tnum,tnom)) {

            cont = new Contact();
            cont.setId(Integer.valueOf(tid.getText()));
            cont.setNumero(tnum.getText());
            cont.setNom(tnom.getText());

            try {
                if (Boite.showConfirmation("Modifer le contact", "Modification")) {
                    //execution
                    if (dc.replace(cont)) {
                        effacer();
                        Boite.showInformation("Contact modifié", "Modification", Alert.AlertType.INFORMATION);
                    }
                }
            } catch (Exception ex) {
                Boite.showException(ex, "Modification");
            }

        } 

    }

    //supprimer un contact
    @FXML
    private void supprimer(ActionEvent ev) {

        if ((cont = tableau.getSelectionModel().getSelectedItem()) != null) {

            try {
                if (Boite.showConfirmation("Enlever le contact", "Suppression")) {
                    if (dc.delete(cont)) {
                        effacer();
                        Boite.showInformation("Contact enlevé", "Suppression", Alert.AlertType.INFORMATION);
                    }
                }
            } catch (Exception ex) {
                Boite.showException(ex, "Suppression");
            }

        } else {
            Boite.showInformation("Selectionner un contact", "Suppression", Alert.AlertType.WARNING);
        }

    }

    //envoyer un messgae
    @FXML
    private void envoyer(ActionEvent ev) {

        if (!tableau.getSelectionModel().isEmpty()) {

            if (Boite.verfier(tm)) {

                if (Boite.showConfirmation("Envoyer le message", "message")) {
                    Boite.executeTache(new Task() {
                        @Override
                        protected Object call() throws Exception {

                            CMessage cm = new CMessage();
                            try{
                                cm.sendMessage(tableau.getSelectionModel().getSelectedItems(),
                                    tm.getText());
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }

                            Platform.runLater(() -> {
                                try {
                                    effacer();
                                } catch (Exception ex) {
                                    Logger.getLogger(CContact.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            return null;
                        }
                    });
                }

            } else {
                Boite.showInformation("Ecrire un message", "message", Alert.AlertType.WARNING);
            }

        } else {
            Boite.showInformation("Selectionner au moins un contact", "message", Alert.AlertType.WARNING);
        }

    }

    @FXML
    private void rafraichir(ActionEvent ev) {
        try {
            effacer();
        } catch (Exception ex) {
            Boite.showException(ex, "Rafraichir");
        }
    }

    private void effacer() throws Exception {
        tid.clear();
        tnum.clear();
        tnom.clear();
        tm.clear();
        initTable();
    }

    //tableau
    private void initTable() {
        tableau.getColumns().clear();
        tableau.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //colone id
        col_id = new TableColumn<>("ID");
        col_id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Contact, Number> p) {
                return new SimpleObjectProperty<>(p.getValue().getId());
            }
        });
        tableau.getColumns().add(col_id);

        //colone numéro
        col_num = new TableColumn<>("Numéro");
        col_num.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> p) {
                return new SimpleObjectProperty<>(p.getValue().getNumero());
            }
        });
        tableau.getColumns().add(col_num);

        //colone nom
        col_nom = new TableColumn<>("Nom");
        col_nom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Contact, String> p) {
                return new SimpleObjectProperty<>(p.getValue().getNom());
            }
        });
        tableau.getColumns().add(col_nom);
        try {
            tableau.setItems(dc.getList());
        } catch (Exception ex) {
            Boite.showException(ex, "initTable");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
    }

}
