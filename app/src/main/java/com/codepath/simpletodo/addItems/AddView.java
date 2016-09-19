package com.codepath.simpletodo.addItems;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.simpletodo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddView extends Fragment {

    @BindView(R.id.tbTodo) Toolbar mTbTodo;

    private Unbinder mUnbinder;

    public AddView() {
        // Required empty public constructor
    }

    public static AddView getNewInstance() {
        return new AddView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.add_view, container, false);

        mUnbinder = ButterKnife.bind(AddView.this, view);

        setupTodoToolbar();

        return view;
    }

    /** Setup tool bar */
    private void setupTodoToolbar() {
        final AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mTbTodo);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
