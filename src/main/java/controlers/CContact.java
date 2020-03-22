/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Boite;
import beans.Contact;
import donnees.DContact;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 *
 * @author NGEREZA
 */
public class CContact extends AnchorPane {

    @FXML
    private TableView<Contact> tableau;
    private TableColumn<Contact, CLigne> colone;

    @FXML
    private TextField tid, tnum, tnom, tch;
    @FXML
    private TextArea tm;
    @FXML
    private Hyperlink notif;

    private DContact dc = new DContact();
    private Contact cont = new Contact();
    private CMessage cm = new CMessage();

    public CContact() {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/vues/contact.fxml"));
        load.setRoot(this);
        load.setController(this);

        try {
            load.load();
            initTable();
            effacer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //cr�er un contact
    @FXML
    private void ajouter(ActionEvent ev) {

        //verification des champs
        if (Boite.verfier(tnum, tnom)) {

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

    /*
    *
    *
    *
    *
    *
    *
    *
    *
    *
    ***
    
    *
    ****
    
    
    *
    *
    *
    
     */
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
            tnom.setText(cont.getNom().get());
        }
    }

    /*
    *
    *
    *
    *
    *
    *
    *
    *
    *
    ***
    
    *
    ****
    
    
    *
    *
    *
    
     */
    //modifier un contact
    @FXML
    private void modifier(ActionEvent ev) {

        //verification des champs
        if (Boite.verfier(tid, tnum, tnom)) {

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

    /*
    *
    *
    *
    *
    *
    *
    *
    *
    *
    ***
    
    *
    ****
    
    
    *
    *
    *
    
     */
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

    /*
    *
    *
    *
    *
    *
    *
    *
    *
    *
    ***
    
    *
    ****
    
    
    *
    *
    *
    
     */
    //envoyer un messgae
    @FXML
    private void envoyer(ActionEvent ev) {

        if (!tableau.getSelectionModel().isEmpty()) {

            if (Boite.verfier(tm)) {

                if (Boite.showConfirmation("Envoyer le message", "message")) {
                    Boite.executeTache(new Task() {
                        @Override
                        protected Object call() throws Exception {

                            cm.sendMessage(tableau.getSelectionModel().getSelectedItems(),
                                    tm.getText());

                            Platform.runLater(() -> {
                                try {
                                    effacer();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
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

    //envoyer les brouillon
    @FXML
    private void envoiBrouilon(ActionEvent ev) {
        if (Boite.showConfirmation("Envoyer le(s) message(s) ?", "Brouillon")) {
            Boite.executeTache(new Task() {
                @Override
                protected Object call() throws Exception {

                    cm.envoiBrouillon();
                    Platform.runLater(() -> {
                        try {
                            effacer();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    return null;
                }
            });
        }
    }

    /*
    *
    *
    *
    *
    *
    *
    *
    *
    *
    ***
    
    *
    ****
    
    
    *
    *
    *
    
     */
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
        tableau.setItems(dc.getList());

        if (cm.brouillon() > 0) {
            notif.setText(cm.brouillon() + " Message(s) non envoyé(s)");
            notif.setVisible(true);
        } else {
            notif.setVisible(false);
        }

    }

    /*
    *
    *
    *
    *
    *
    *
    *
    *
    *
    ***
    
    *
    ****
    
    
    *
    *
    *
    
     */
    //tableau
    private void initTable() {
        tableau.getColumns().clear();
        tableau.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        colone = new TableColumn<>();
        colone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, CLigne>, ObservableValue<CLigne>>() {
            @Override
            public ObservableValue<CLigne> call(TableColumn.CellDataFeatures<Contact, CLigne> param) {
                return new SimpleObjectProperty<>(new CLigne(param.getValue(), colone.getWidth()));
            }
        });
        tableau.getColumns().add(colone);
    }

}
