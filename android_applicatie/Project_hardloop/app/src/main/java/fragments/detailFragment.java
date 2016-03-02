package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niek.project_hardloop.R;

import Entity.TrainingsSchema;


public class DetailFragment extends Fragment {
    private TrainingsSchema trainingsSchema;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,
                container, false);

        setText(trainingsSchema, view);

        return view;
    }

    public void setText(TrainingsSchema trainingsSchema){
        System.out.println("set number");
        this.trainingsSchema = trainingsSchema;
        if(getView() != null){
            String source = "";
            TextView naam =  (TextView) getView().findViewById(R.id.T_Naam);
            naam.setText(trainingsSchema.getNaam());
            TextView soort =  (TextView) getView().findViewById(R.id.T_soort);
            source =  "<b>" + getString(R.string.soort) + "</b>" + trainingsSchema.getSoort();
            soort.setText(Html.fromHtml(source));
            TextView afstand =  (TextView) getView().findViewById(R.id.T_afstand);
            source = "<b>" + getString(R.string.afstand) + "</b>" + " " + trainingsSchema.getLengte() + trainingsSchema.getLengteSoort();
            afstand.setText(Html.fromHtml(source));
            TextView omschrijving =  (TextView) getView().findViewById(R.id.T_omschrijving);
            omschrijving.setText(trainingsSchema.getOmschrijving());
        }

    }

    public void setText(TrainingsSchema trainingsSchema, View view){
        System.out.println("set number with view");
        this.trainingsSchema = trainingsSchema;
        String source = "";
        TextView naam =  (TextView) view.findViewById(R.id.T_Naam);
        naam.setText(trainingsSchema.getNaam());
        TextView soort =  (TextView) view.findViewById(R.id.T_soort);
        source =  "<b>" + getString(R.string.soort) + "</b>" + trainingsSchema.getSoort();
        soort.setText(Html.fromHtml(source));
        TextView afstand =  (TextView) view.findViewById(R.id.T_afstand);
        source = "<b>" + getString(R.string.afstand) + "</b>" + " " + trainingsSchema.getLengte() + trainingsSchema.getLengteSoort();
        afstand.setText(Html.fromHtml(source));
        TextView omschrijving =  (TextView) view.findViewById(R.id.T_omschrijving);
        omschrijving.setText(trainingsSchema.getOmschrijving());

    }
}
