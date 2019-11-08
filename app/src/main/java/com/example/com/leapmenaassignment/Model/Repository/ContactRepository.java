package com.example.com.leapmenaassignment.Model.Repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.com.leapmenaassignment.Model.Models.ContactDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ContactRepository {

    private static final String TAG = "ContactRepository";


    public static LiveData<ArrayList<ContactDetails>> getContactList(Context context) {
        Log.e(TAG, "****New contact fetch request from viewmodel: ****");
        Toast.makeText(context, "Phone Contact fetched", Toast.LENGTH_LONG).show();
        MutableLiveData<ArrayList<ContactDetails>> arrayListLiveData = new MutableLiveData<>();
        ArrayList<ContactDetails> contactDetailsList = new ArrayList<>();
        Set<String> setDetails = new HashSet<>();
        int count = 0;
        Cursor phones = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                null, "upper(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
        while (phones.moveToNext()) {

            String name = phones
                    .getString(phones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            String phoneNo1 = phones
                    .getString(phones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


            String email = phones
                    .getString(phones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            String imagUri = phones
                    .getString(phones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));

            String phoneNo = "";
            if (contactDetailsList.size() > 0) {

                phoneNo = phoneNo1.replace(" ", "");
                if (!setDetails.contains(phoneNo)) {
                    ContactDetails details = new ContactDetails();
                    details.setContactName(name);
                    details.setContactNumber(phoneNo);
                    contactDetailsList.add(details);
                    Log.i(TAG, "Count: " + count + " :Name: " + name);
                    Log.i(TAG, "Phone Number: " + phoneNo);
                    setDetails.add(phoneNo);
                }
            } else {
                setDetails.add(phoneNo);
                ContactDetails details = new ContactDetails();
                details.setContactName(name);
                details.setContactNumber(phoneNo);
                contactDetailsList.add(details);
                Log.i(TAG, "Count: " + count + " :Name: " + name);
                Log.i(TAG, "Phone Number: " + phoneNo);
            }


        }
        arrayListLiveData.setValue(contactDetailsList);
        if (phones != null) {
            phones.close();
        }
        return arrayListLiveData;
    }
}


