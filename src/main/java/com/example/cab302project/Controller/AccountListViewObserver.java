package com.example.cab302project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import com.example.cab302project.Observer;

public class AccountListViewObserver implements Observer {
    private ListView<String> accountListView;
    private ObservableList<String> accounts;


    public AccountListViewObserver(ListView<String> accountListView, ObservableList<String> accounts) {
        this.accountListView = accountListView;
        this.accounts = accounts;
    }


    @Override
    public void update(String searchTerm) {
        if (searchTerm.isEmpty()) {

            accountListView.setItems(FXCollections.observableArrayList());
        } else {

            accountListView.setItems(accounts.filtered(account -> account.toLowerCase().contains(searchTerm.toLowerCase())));
        }
    }
}
