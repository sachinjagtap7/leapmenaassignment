package com.example.com.leapmenaassignment.View.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.com.leapmenaassignment.AppViewModel.ContactViewModel;
import com.example.com.leapmenaassignment.Model.Models.ContactDetails;
import com.example.com.leapmenaassignment.R;
import com.example.com.leapmenaassignment.Utils.Adapters.ContactListAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContactFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ContactViewModel model;
    private static final String TAG = "ContactFragment";
    ArrayList<ContactDetails> contactDetailsList;
    int totalCount = 0;
    ContactListAdapter listAdapter;
    ArrayList<ContactDetails> temptDetailsList;

    boolean isLoading = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "fragment onCreate: called: ");
        contactDetailsList = new ArrayList<>();
        temptDetailsList = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        recyclerView = view.findViewById(R.id.rv_conatct_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: OnActivityCreated");
        model = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
        model.contactListLiveData.observe(getActivity(), new Observer<ArrayList<ContactDetails>>() {
            @Override
            public void onChanged(ArrayList<ContactDetails> contactDetails) {
                Log.e(TAG, "onChanged: of contact details:");

                temptDetailsList = contactDetails;
                Log.e(TAG, "tempArrayList size: " + temptDetailsList.size());
                if (temptDetailsList.size() > 0) {
                    int i = totalCount;
                    totalCount = totalCount + 10;
                    do {
                        contactDetailsList.add(temptDetailsList.get(i));
                        //  temptDetailsList.remove(i);
                        i++;
                    } while (i != totalCount);
                    initRecycerrView(contactDetailsList);
                } else {
                    Log.e(TAG, "onChanged: conatct list size is:0");
                }


            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initRecycerrView(ArrayList<ContactDetails> contactDetails) {

        layoutManager = new LinearLayoutManager(getActivity());
        listAdapter = new ContactListAdapter(getActivity(), contactDetails);
        Log.e(TAG, "initRecycerrView: ConatctDetailsList size:" + contactDetails.size());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addOnScrollListener(new PaginationListner());

        /*int scrollPosition = 0;
        try {
            scrollPosition = model.getScrollPosition();
            if (scrollPosition > 10) {
                recyclerView.smoothScrollToPosition(scrollPosition - 9);
            }
        } catch (Exception e) {


        }*/

    }

    class PaginationListner extends RecyclerView.OnScrollListener {
        Timer timer;

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int totalItemCount = layoutManager.getItemCount();
            int lastVisibleposition = layoutManager.findLastVisibleItemPosition();
            if (lastVisibleposition == totalCount - 1 && isLoading == false) {
                isLoading = true;
                ContactDetails details = new ContactDetails();
                details.setItemType(1);
                contactDetailsList.add(details);
                listAdapter.notifyItemChanged(totalItemCount + 1);
                startTimer(totalItemCount + 1);

            }
        }

        private void startTimer(int totalItemCount) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new TimerTask() {
                        @Override
                        public void run() {
                            contactDetailsList.remove(contactDetailsList.remove(totalItemCount - 1));
                            listAdapter.notifyItemChanged(totalItemCount - 1);
                            LoadContactListData();
                        }
                    });
                    stopTimer();


                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 500);
        }

        private void LoadContactListData() {


            int i = totalCount;
            Log.e(TAG, "tempDetailsList size:" + temptDetailsList.size() + "  contact details size: " + contactDetailsList.size());

            if (temptDetailsList.size() - totalCount >= 10) {
                totalCount = totalCount + 10;
                Log.e(TAG, "LoadContactListData: inside LoadContactListData() if");
                do {
                    Log.e(TAG, "Conatct: " + temptDetailsList.get(i).getContactName());
                    contactDetailsList.add(temptDetailsList.get(i));
                    //  temptDetailsList.remove(i);
                    i++;
                } while (i != totalCount);
                listAdapter.notifyDataSetChanged();
                // recyclerView.smoothScrollToPosition(totalCount - 10);

            } else {
                if (totalCount != temptDetailsList.size()) {
                    Log.e(TAG, "LoadContactListData: inside LoadContactListData() else");
                    totalCount = totalCount + (temptDetailsList.size() % 10);

                    do {
                        Log.e(TAG, "Conatct: " + temptDetailsList.get(i).getContactName());
                        contactDetailsList.add(temptDetailsList.get(i));
                        //temptDetailsList.remove(i);

                        i++;
                    } while (i != totalCount);
                    listAdapter.notifyDataSetChanged();
                    //  recyclerView.smoothScrollToPosition(totalCount - 10);
                } else {
                    Toast.makeText(getActivity(), "End of list", Toast.LENGTH_SHORT).show();
                }
            }

            model.updateScrollPosition(totalCount);
            isLoading = false;
        }

        private void stopTimer() {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
        }
    }
}
