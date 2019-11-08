package com.example.com.leapmenaassignment.AppViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.com.leapmenaassignment.Model.Models.ContactDetails;
import com.example.com.leapmenaassignment.Model.Repository.ContactRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class ContactViewModel extends AndroidViewModel {
    private static final String TAG = "ContactViewModel";

    Context context;
    LiveData<ArrayList<ContactDetails>> arrayListLiveData;
    public LiveData<ArrayList<ContactDetails>> contactListLiveData;
    MutableLiveData<Integer> scrollPosition = new MutableLiveData<>();

    public ContactViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        Log.e(TAG, "ContactViewModel: Context:" + application.getApplicationContext());
        arrayListLiveData = ContactRepository.getContactList(context);
        contactListLiveData = Transformations.map(arrayListLiveData, data -> getContactListLiveData(data));
    }

    private ArrayList<ContactDetails> getContactListLiveData(ArrayList<ContactDetails> data) {
        return data;
    }

    public void updateScrollPosition(int position) {
        scrollPosition.setValue(position);
    }

    public int getScrollPosition() {

        return scrollPosition.getValue();
    }
}
