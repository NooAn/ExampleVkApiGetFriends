package com.andre1024.testworkforapprial;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by An on 29.10.2015.
 */
public class ItemFragment extends android.support.v4.app.Fragment implements SearchView.OnQueryTextListener {

    private static final String FRIENDS_LIST = "friends_list";
    private ModelListFiends mFriends;
    private ListView mListView;
    private MySimpleArrayAdapter mAdapter;

    public static ItemFragment newInstance(ModelListFiends friends) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(FRIENDS_LIST, friends);
        fragment.setArguments(args);
        return fragment;
    }

    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(FRIENDS_LIST)) {
            mFriends = (ModelListFiends) getArguments().getSerializable(FRIENDS_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        mAdapter = new MySimpleArrayAdapter(getActivity(), mFriends.getResponse());
        mListView.setAdapter(mAdapter);
        mListView.setTextFilterEnabled(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);
            searchView.setOnQueryTextListener(this);
            searchView.setSubmitButtonEnabled(true);
            searchView.setQueryHint(""+R.string.tab_searsh);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onQueryTextChange(String s) {
        mAdapter.filterData(s);
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        mAdapter.filterData(query);
        return false;
    }

}
