package ru.astralight.koksharov.robbernews.LocalAdmin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider;
import ru.astralight.koksharov.robbernews.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateUserFragment extends Fragment implements DBData{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static String[] ACCESS_MODES;// = {ACCESS_MODE_ADMIN, ACCESS_MODE_MODERATOR, ACCESS_MODES_USER};
    int currentFragmentNumber = 3;

    public CreateUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateUserFragment newInstance(String param1, String param2) {
        CreateUserFragment fragment = new CreateUserFragment();
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
        return inflater.inflate(R.layout.fragment_create_user, container, false);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Spinner accessSpinner = getView().findViewById(R.id.createUserAccessModeSpinner);

        ArrayList<String> access_modes = new ArrayList<>();
        access_modes.add(RobberNewsContentProvider.ACCESS_MODE_ADMIN);
        access_modes.add(RobberNewsContentProvider.ACCESS_MODE_MODERATOR);
        for (String str : RobberNewsContentProvider.ACCESS_MODES_USER){
            access_modes.add(str);
        }
        ACCESS_MODES = access_modes.toArray(new String[access_modes.size()]);

        final ArrayAdapter<String> chooseAdapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_item, ACCESS_MODES);//,R.layout.spinner_layout R.id.choose_spinner

        chooseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accessSpinner.setAdapter(chooseAdapter);
        // заголовок
        accessSpinner.setPrompt(getString(R.string.Table));
        // выделяем элемент
        accessSpinner.setSelection(0);
        accessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String changedText = ACCESS_MODES[i];
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_ACCESS_MODE, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_USER);
                mListener.onFragmentInteraction(map);
//                choseFragment(spinnerData[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                choseFragment(spinnerData[0]);
            }
        });






        TabHost tabHost = (TabHost)  getView().findViewById(R.id.create_user_tabhost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.create_user_credetials_tab);
        tabSpec.setIndicator(getString(R.string.Credentials));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.create_user_addional_tab);
        tabSpec.setIndicator(getString(R.string.addional_info));
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.create_user_datetime_tab);
        tabSpec.setIndicator(getString(R.string.datetime));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        EditText name = (EditText) getView().findViewById(R.id.createUserNameEdit);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_NAME, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_USER);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText surname = (EditText) getView().findViewById(R.id.createUserSurnameEdit);
        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_SURNAME, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_USER);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText pass = (EditText) getView().findViewById(R.id.createUserPasswordEdit);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_PASSWORD, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_USER);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText avatar = (EditText) getView().findViewById(R.id.createUserAvatarEdit);
        avatar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_AVATAR, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_USER);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        EditText about = (EditText) getView().findViewById(R.id.createUserAboutEdit);
        about.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_ABOUT, changedText);
                map.put("WRITE", RobberNewsContentProvider.TABLE_USER);
                mListener.onFragmentInteraction(map);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable);
            }
        });

        DatePicker datePicker = getView().findViewById(R.id.createUserDatePicker);

        Calendar calendar = Calendar.getInstance();

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_REGISTER_DATE, datePicker.getYear() + "." +
                        datePicker.getMonth() + "." + datePicker.getDayOfMonth());
                map.put("WRITE", RobberNewsContentProvider.TABLE_USER);
                mListener.onFragmentInteraction(map);
            }
        });

        TimePicker timePicker = getView().findViewById(R.id.createUserTimePicker);;
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                ;
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(RobberNewsContentProvider.COLUMN_DATE_TIME, timePicker.getCurrentHour() + "." +
                        timePicker.getCurrentMinute());
                map.put("WRITE", RobberNewsContentProvider.TABLE_USER);
                mListener.onFragmentInteraction(map);
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Map<String, String> data) {
        if (mListener != null) {

            TextView name = (TextView)  getView().findViewById(R.id.createUserNameText);
            name.setText("LA PREESED FAB");
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
