package joshuawink.howmanyout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button calculateButton = (Button) findViewById(R.id.Calculate);
        final CheckBox bleedCheckBox = (CheckBox) findViewById(R.id.Bleed);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                howManyOut();
            }

        };

        bleedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //get edit text fields
                EditText finalHeight = (EditText) findViewById(R.id.FinishedHeight);
                EditText finalWidth = (EditText) findViewById(R.id.FinishedWidth);

                //convert edit texts to Doubles
                Double dFinalHeight = Double.parseDouble(finalHeight.getText().toString());
                Double dFinalWidth = Double.parseDouble(finalWidth.getText().toString());

                //add bleed or remove bleed
                if(isChecked) {
                    dFinalHeight = addBleed(dFinalHeight);
                    dFinalWidth = addBleed(dFinalWidth);
                }else{
                    dFinalHeight = subtractBleed(dFinalHeight);
                    dFinalWidth = subtractBleed(dFinalWidth);
                }

                //set new bleed into text field
                finalHeight.setText(dFinalHeight + "");
                finalWidth.setText(dFinalWidth + "");
            }
        });

        calculateButton.setOnClickListener(listener);
}

    // Method to calculate how many smaller pieces you can get from a larger one.
    public void howManyOut(){

        // Initialize total
        int total = 0;

        //get final display point to update
        TextView totalOut = (TextView) findViewById(R.id.totalOut);
        //get the edit text fields
        EditText startingHeight = (EditText)findViewById(R.id.StartingHeight);
        EditText startingWidth = (EditText) findViewById(R.id.StartingWidth);
        EditText finalHeight = (EditText) findViewById(R.id.FinishedHeight);
        EditText finalWidth = (EditText) findViewById(R.id.FinishedWidth);

        //Convert Edit Text fields to Doubles
        Double dStartingHeight = Double.parseDouble(startingHeight.getText().toString());
        Double dStartingWidth = Double.parseDouble(startingWidth.getText().toString());
        Double dFinalHeight = Double.parseDouble(finalHeight.getText().toString());
        Double dFinalWidth = Double.parseDouble(finalWidth.getText().toString());

        // Height to Height outs
        Double heightToHeightOut = (dStartingHeight / dFinalHeight) - .5;
        Double widthToWidthOut = (dStartingWidth / dFinalWidth) - .5;

        // Height to Width outs
        Double  heightToWidthOut = (dStartingHeight / dFinalWidth) -.5;
        Double widthToHeightOut = (dStartingWidth / dFinalHeight) - .5;

        //Calculate total outs for laying out portrait vs landscape.
        int layout1 = (int)(Math.round(heightToHeightOut) * Math.round(widthToWidthOut));
        int layout2 = (int)(Math.round(heightToWidthOut) * Math.round(widthToHeightOut));

        // Compare which layout gives more out.
        if(layout1 >= layout2){
            total = layout1;
        }else{
            total = layout2;
        }

        //Set the answer on screen
        totalOut.setText(total + "");
    }

    public Double addBleed(Double startingDimension){
        startingDimension = startingDimension + .25;
        return startingDimension;
    }

    public Double subtractBleed(Double startingDimension){
        startingDimension = startingDimension - .25;
        return startingDimension;
    }

}
