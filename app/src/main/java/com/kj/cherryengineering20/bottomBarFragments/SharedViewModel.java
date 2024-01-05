package com.kj.cherryengineering20.bottomBarFragments;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kj.cherryengineering20.product.InventoryDatabase;

public class SharedViewModel extends ViewModel {

    private int intValue;
    private double doubleValue;
    private Context context;
    private InventoryDatabase id;

    private MutableLiveData<CharSequence> text = new MutableLiveData<>();

    private String selectedItem;
    private String name;
    private String errorDialogString;

    private boolean inventoryAdding;
    private boolean commissionRateChangeStatus;

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setInventoryDatabase(InventoryDatabase id) {
        this.id = id;
    }

    public InventoryDatabase getInventoryDatabase() {
        return id;
    }

    public void setText(CharSequence input) {
        text.setValue(input);
    }

    public MutableLiveData<CharSequence> getText() {
        return text;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getViewModelContext() {
        return context;
    }

    public void setIntValue(int value) {
        intValue = value;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setDoubleValue(double value) {
        doubleValue = value;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setErrorDialogString(String text) {
        errorDialogString = text;
    }

    public String getErrorDialogString() {
        return errorDialogString;
    }

    public void setInventoryModificiationStatus(boolean status) {
        inventoryAdding = status;
    }

    public boolean getInventoryModificationStatus() {
        return inventoryAdding;
    }

    public void setCommissionRateChangeStatus(boolean value) {
        commissionRateChangeStatus = value;
    }

    public boolean getCommissionRateChangeStatus() {
        return commissionRateChangeStatus;
    }

}
