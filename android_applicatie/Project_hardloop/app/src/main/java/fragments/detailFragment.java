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
        System.out.println("oncreate detail fragment");
        if(trainingsSchema != null){
            setText(trainingsSchema, view);
        }
        return view;
    }

    /*Set de detail fragment als die los word aangeroepen en de view al wel bestaat*/
    public void setText(TrainingsSchema trainingsSchema){
        System.out.println("training info set ");
        this.trainingsSchema = trainingsSchema;
        if(getView() != null){
            System.out.println("training info set ");
            String source;
            TextView naam =  (TextView) getView().findViewById(R.id.T_Naam);
            naam.setText(trainingsSchema.getNaam());
            TextView soort =  (TextView) getView().findViewById(R.id.T_soort);
            source =  "<b>" + getString(R.string.soort) + " </b>" + trainingsSchema.getSoort();
            soort.setText(Html.fromHtml(source));
            TextView afstand =  (TextView) getView().findViewById(R.id.T_afstand);
            source = "<b>" + getString(R.string.afstand) + "</b>" + " " + trainingsSchema.getLengte() + trainingsSchema.getLengteSoort();
            afstand.setText(Html.fromHtml(source));
            TextView title_omschrijving =  (TextView) getView().findViewById(R.id.T_title_omschrijving);
            title_omschrijving.setVisibility(View.VISIBLE);
            TextView omschrijving =  (TextView) getView().findViewById(R.id.T_omschrijving);
            omschrijving.setText(trainingsSchema.getOmschrijving());
        }

    }

    /*Set de detailfragment vanuit de oncreate methode*/
    public void setText(TrainingsSchema trainingsSchema, View view){
        this.trainingsSchema = trainingsSchema;
        System.out.println("training info set view methode");
        String source;
        TextView naam = (TextView) view.findViewById(R.id.T_Naam);
        naam.setText(trainingsSchema.getNaam());
        TextView soort =  (TextView) view.findViewById(R.id.T_soort);
        source =  "<b>" + getString(R.string.soort) + "</b>" + trainingsSchema.getSoort();
        soort.setText(Html.fromHtml(source));
        TextView afstand =  (TextView) view.findViewById(R.id.T_afstand);
        source = "<b>" + getString(R.string.afstand) + "</b>" + " " + trainingsSchema.getLengte() + trainingsSchema.getLengteSoort();
        afstand.setText(Html.fromHtml(source));
        TextView title_omschrijving =  (TextView) view.findViewById(R.id.T_title_omschrijving);
        title_omschrijving.setVisibility(View.VISIBLE);
        TextView omschrijving =  (TextView) view.findViewById(R.id.T_omschrijving);
        omschrijving.setText(trainingsSchema.getOmschrijving());

    }
}
