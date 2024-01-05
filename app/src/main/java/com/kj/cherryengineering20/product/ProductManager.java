package com.kj.cherryengineering20.product;

import android.content.Context;

import com.kj.cherryengineering20.CompletedCaseQuantityMenu.PointFiveWaterBottle;

import java.util.ArrayList;

public class ProductManager {

    private Context mContext;
    private ArrayList<Product> products;
    PointFiveWaterBottle pointFiveMwb;

    public ProductManager(Context context) {
        this.products = new ArrayList<>();
        mContext = context;
        products.add(pointFiveMwb);
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void removeProduct(Product p) {
        products.remove(p);
    }

    public ArrayList<String> getAllProductNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Product p: this.products) {
            names.add(p.getName());
        }

        return names;
        //for spinner in CompletedCaseQuantityMenu
    }

    private void resetProducts() {
        products.add(new PointFiveWaterBottle());
        products.add(new HydraJet32());
        products.add(new HydraJet64());
    }
}
