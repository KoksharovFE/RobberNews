package ru.astralight.koksharov.robbernews.LocalAdmin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider;
import ru.astralight.koksharov.robbernews.R;
import ru.astralight.koksharov.robbernews.tasks.DownloadImageTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateArticleFragment extends Fragment implements DBData {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateArticleFragment newInstance(String param1, String param2) {
        CreateArticleFragment fragment = new CreateArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_article, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TabHost tabHost = (TabHost)  getView().findViewById(R.id.create_article_tabhost);
//
        tabHost.setup();
//
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.create_article_main_tab);
        tabSpec.setIndicator(getString(R.string.Main));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.create_article_addional_tab);
        tabSpec.setIndicator(getString(R.string.addional_info));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.create_article_datetime_tab);
        tabSpec.setIndicator(getString(R.string.datetime));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);


        final Spinner accessSpinner = getView().findViewById(R.id.createArticleThemeSpinner);

        final ArrayAdapter<String> chooseAdapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_item, RobberNewsContentProvider.THEMES);//,R.layout.spinner_layout R.id.choose_spinner

        chooseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accessSpinner.setAdapter(chooseAdapter);
        // заголовок
        accessSpinner.setPrompt(getString(R.string.Table));
        // выделяем элемент
        accessSpinner.setSelection(0);
        accessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String changedText = RobberNewsContentProvider.THEMES[i];
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_THEME, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
//                choseFragment(spinnerData[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                choseFragment(spinnerData[0]);
            }
        });

        EditText title = (EditText) getView().findViewById(R.id.createArticleTitleEdit);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_TITLE, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText tag_cloud = (EditText) getView().findViewById(R.id.createArticleTagCloudEdit);
        tag_cloud.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_TAGS_CLOUD, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText author = (EditText) getView().findViewById(R.id.createAuthorEdit);
        author.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_AUTHOR_ID, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText preview = (EditText) getView().findViewById(R.id.createArticlePreviewEdit);
        preview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_PREVIEW, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText text = (EditText) getView().findViewById(R.id.createArticleTextEdit);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_TEXT, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText image = (EditText) getView().findViewById(R.id.createArticleImageEdit);
        final ImageView iv = getView().findViewById(R.id.createArticleImageView);
        image.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_IMAGE, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);

                new DownloadImageTask(iv).execute(changedText);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });


        DatePicker datePicker = getView().findViewById(R.id.createArticleDatePicker);

        Calendar calendar = Calendar.getInstance();

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_REGISTER_DATE, datePicker.getYear() + "." +
                        datePicker.getMonth() + "." + datePicker.getDayOfMonth());
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
            }
        });

        TimePicker timePicker = getView().findViewById(R.id.createArticleTimePicker);;
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                ;
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_DATE_TIME, timePicker.getCurrentHour() + "." +
                        timePicker.getCurrentMinute());
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
            }
        });

        final CheckBox isForumArticle = getView().findViewById(R.id.createArticleIsForumCheckBox);
        isForumArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<String, String>();
                if(isForumArticle.isChecked()){
                    map.put(RobberNewsContentProvider.COLUMN_IS_FORUM_ARTICLE, "1");
                } else {
                    map.put(RobberNewsContentProvider.COLUMN_IS_FORUM_ARTICLE, "0");
                }
                map.put("WRITE", RobberNewsContentProvider.TABLE_ARTICLE);
                mListener.onFragmentInteraction(map);
            }
        });

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Map<String, String> data) {
        if (mListener != null) {
            mListener.onFragmentInteraction(data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.wtf("Article create", "started");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Map<String, String> data);
    }


    @Override
    public HashMap<String, String> getFrarmentDataInHash() {
        return null;  // TODO:
    }
}
