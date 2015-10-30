package com.andre1024.testworkforapprial;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by An on 29.10.2015.
 */
public class ModelListFiends implements Serializable {
    private ArrayList<ModelFriend> response;

    public ArrayList<ModelFriend> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<ModelFriend> response) {
        this.response = response;
    }
}
