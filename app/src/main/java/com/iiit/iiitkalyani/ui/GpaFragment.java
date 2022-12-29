package com.iiit.iiitkalyani.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.iiit.iiitkalyani.R;

public class GpaFragment extends Fragment {
    private TextView sub1;
    private TextView sub2;
    private TextView sub3;
    private TextView sub4;
    private TextView sub5;
    private TextView sub6;
    private TextView sub7;
    private TextView sub8;
    private EditText gpa1;
    private EditText gpa2;
    private EditText gpa3;
    private EditText gpa4;
    private EditText gpa5;
    private EditText gpa6;
    private EditText gpa7;
    private EditText gpa8;
    private Button btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_gpa, container, false);
        Spinner spinner = root.findViewById(R.id.spinner);
        sub1 = root.findViewById(R.id.sub1);
        sub2 = root.findViewById(R.id.sub2);
        sub3 = root.findViewById(R.id.sub3);
        sub4 = root.findViewById(R.id.sub4);
        sub5 = root.findViewById(R.id.sub5);
        sub6 = root.findViewById(R.id.sub6);
        sub7 = root.findViewById(R.id.sub7);
        sub8 = root.findViewById(R.id.sub8);
        gpa1 = root.findViewById(R.id.gpa1);
        gpa2 = root.findViewById(R.id.gpa2);
        gpa3 = root.findViewById(R.id.gpa3);
        gpa4 = root.findViewById(R.id.gpa4);
        gpa5 = root.findViewById(R.id.gpa5);
        gpa6 = root.findViewById(R.id.gpa6);
        gpa7 = root.findViewById(R.id.gpa7);
        gpa8 = root.findViewById(R.id.gpa8);
        btn = root.findViewById(R.id.button);
        String[] sem = new String[]{"Select Your Semester", "1", "2", "3", "4", "5", "6", "7", "8"};
        ArrayAdapter adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, sem);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(getContext(), "Select your semester ", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        sub1.setText("HU-101");
                        sub2.setText("HU-102");
                        sub3.setText("PH-101");
                        sub4.setText("MA-101");
                        sub5.setText("CS101");
                        sub6.setText("EC-101");
                        sub7.setText("CS-111");
                        sub8.setText("EC-111");
                        break;
                    case 2:
                        sub1.setText("HU-201");
                        sub2.setText("CS-201");
                        sub3.setText("CS-202");
                        sub4.setText("CS-203");
                        sub5.setText("EC-201");
                        sub6.setText("CS-211");
                        sub7.setText("CS-212");
                        sub8.setText("EC-211");
                        break;
                    case 3:
                        sub1.setText("");
                        sub2.setText("");
                        sub3.setText("");
                        sub4.setText("");
                        sub5.setText("");
                        sub6.setText("");
                        sub7.setText("");
                        sub8.setText("");
                        break;
                    case 4:
                        sub1.setText("");
                        sub2.setText("");
                        sub3.setText("");
                        sub4.setText("");
                        sub5.setText("");
                        sub6.setText("");
                        sub7.setText("");
                        sub8.setText("");
                        break;
                    case 5:
                        sub1.setText("");
                        sub2.setText("");
                        sub3.setText("");
                        sub4.setText("");
                        sub5.setText("");
                        sub6.setText("");
                        sub7.setText("");
                        sub8.setText("");
                        break;
                    case 6:
                        sub1.setText("");
                        sub2.setText("");
                        sub3.setText("");
                        sub4.setText("");
                        sub5.setText("");
                        sub6.setText("");
                        sub7.setText("");
                        sub8.setText("");
                        break;
                    case 7:
                        sub1.setText("");
                        sub2.setText("");
                        sub3.setText("");
                        sub4.setText("");
                        sub5.setText("");
                        sub6.setText("");
                        sub7.setText("");
                        sub8.setText("");
                        break;
                    case 8:
                        sub1.setText("");
                        sub2.setText("");
                        sub3.setText("");
                        sub4.setText("");
                        sub5.setText("");
                        sub6.setText("");
                        sub7.setText("");
                        sub8.setText("");
                        break;
                }
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        avg();
                        //Toast.makeText(getContext(),x,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void avg() {
                String gpaa1 = gpa1.getText().toString().toLowerCase();
                String gpaa2 = gpa2.getText().toString().toLowerCase();
                String gpaa3 = gpa3.getText().toString().toLowerCase();
                String gpaa4 = gpa4.getText().toString().toLowerCase();
                String gpaa5 = gpa5.getText().toString().toLowerCase();
                String gpaa6 = gpa6.getText().toString().toLowerCase();
                String gpaa7 = gpa7.getText().toString().toLowerCase();
                String gpaa8 = gpa8.getText().toString().toLowerCase();
                int[] gpa = new int[]{convert(gpaa1), convert(gpaa2), convert(gpaa3), convert(gpaa4), convert(gpaa5), convert(gpaa6), convert(gpaa7), convert(gpaa8)};
                double sum;
                sum = 0.0;
                for (int num : gpa) sum += num;
                String avg = String.valueOf(sum / 8);
                Toast.makeText(getContext(), avg, Toast.LENGTH_SHORT).show();
                TextView textView = root.findViewById(R.id.textView4);
                textView.setText(avg);
                //return avg;
            }

            private int convert(String gpaa) {
                switch (gpaa) {
                    case "ex":
                        return 10;
                    case "a":
                        return 9;
                    case "b":
                        return 8;
                    case "c":
                        return 7;
                    case "d":
                        return 6;
                    case "p":
                        return 5;
                    case "f":
                        return 0;
                    default:
                        Toast.makeText(getContext(), "Enter Valid Grade!", Toast.LENGTH_SHORT).show();
                }
                return Integer.parseInt("0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }
}