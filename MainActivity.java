package health.calculator.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import health.calculator.bmicalculator.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SeekBar heightSeekBar;
    private Button okButton;
    private EditText WeightEditText, ageEditText;
    private TextView outputTextView;
    private TextView heighTtextView;
    private TextView textView;
    private RadioButton femaleRadioButton, maleRadioButton;
    private int heightValue=0 , WeightValue, desirableWeightValue,ageValue;
    private double bmiValue;
    private String weightStatusValue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightSeekBar = findViewById(R.id.heightSeekBar);
        okButton = findViewById(R.id.okButton);
        WeightEditText = findViewById(R.id.WeightEditText);
        ageEditText = findViewById(R.id.ageEditText);
        outputTextView = findViewById(R.id.outputTextView);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        heighTtextView = findViewById(R.id.heighTtextView);
        textView=findViewById(R.id.textView);
        okButton.setOnClickListener(this);


             heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minumum=140;
                heightValue = progress+minumum;
                heighTtextView.setText("גובה: " + heightValue + " ס''מ");
                outputTextView.setText("");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onClick(View v) {
        int length= ageEditText.length();

        outputTextView.setText(Integer.toString(length));

        try {
            if (heightValue != 0 && (maleRadioButton.isChecked() || femaleRadioButton.isChecked())) {
                WeightValue = Integer.parseInt(String.valueOf(WeightEditText.getText()));
                ageValue = Integer.parseInt(String.valueOf(ageEditText.getText()));
                if (ageValue < 19) {
                    Toast.makeText(this, "החישוב הוא מעל גיל 19 בלבד", Toast.LENGTH_SHORT).show();
                    outputTextView.setText("");
                } else if (ageValue > 120) {
                    Toast.makeText(this, "אל תנסה לעבוד עלי! הכנס את גילך האמיתי", Toast.LENGTH_SHORT).show();
                    outputTextView.setText("");
                } else if (WeightValue > 360) {
                    Toast.makeText(this, "המחשבון לא מיועד לפילים", Toast.LENGTH_SHORT).show();
                    outputTextView.setText("");
                } else {

                    if (maleRadioButton.isChecked())
                        desirableWeightValue = Functions.desirableWeightMale(heightValue);
                    else
                        desirableWeightValue = Functions.desirableWeightFemale(heightValue);

                    bmiValue = Functions.bmi(WeightValue, heightValue);
                    weightStatusValue = Functions.status(bmiValue);

                    outputTextView.setText("המשקל הרצוי: " + desirableWeightValue + " kg \n" + "המשקל שלך: " + WeightValue + " kg\n" + bmiValue + " :BMI\n" + "  סטטוס המשקל שלך: " + weightStatusValue + "  ");
                }
            } else
            {
                Toast.makeText(this, "נא להזין ערכים בכל השדות", Toast.LENGTH_SHORT).show();
           //    int k= ageEditText.getText().toString().length();
            //   outputTextView.setText(k);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "נא להזין ערכים בכל השדות", Toast.LENGTH_SHORT).show();
        }
    }
    public static class Functions {

        public static int desirableWeightMale(int h) {

            return (int) (48 + 1.1 * (h - 152));
        }

        public static int desirableWeightFemale(int h) {

            return (int) (45.4 + 0.9 * (h - 152));
        }

        public static double bmi(int weight, double height) {
            height /= 100;

            height = Math.pow(height, 2);

            double temp = weight / height;
            temp *= 100;
            temp = (int) temp / 100.0;

            return temp;
        }

        public static String status(double bmi) {
            String result = null;

            if (bmi < 15)
                result = "אנורקסי";
            else if (bmi >= 15 && bmi < 18.5)
                result = "תת משקל";
            else if (bmi > 18.5 && bmi < 25)
                result = "נורמלי";
            else if (bmi >= 25 && bmi < 30)
                result = "עודף משקל";
            else if (bmi >= 30 && bmi < 35)
                result = "שמן";
            else if (bmi > 35)
                result = "שמן מאוד";

            return result;
        }
    }
}
