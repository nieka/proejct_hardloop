package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niek.project_hardloop.R;

import java.util.List;

import Adapters.RecyclerItemClickListener;
import Adapters.TrainingslistAdapter;
import Entity.TrainingsSchema;
public class TrainingList extends android.app.Fragment {

    public interface OnitemSelect {
        void onItemSelected(int position);
    }

    private OnitemSelect mCallback;
    private List<TrainingsSchema> trainingsSchemaList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_traininglist,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*recyleview met training lijst vullen met data*/
        if(trainingsSchemaList != null){
            RecyclerView traininglist = (RecyclerView) view.findViewById(R.id.traininglijst);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(traininglist.getContext());
            traininglist.setLayoutManager(mLayoutManager);
            traininglist.addOnItemTouchListener(
                    new RecyclerItemClickListener(this.getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            System.out.println("clicked");
                            mCallback.onItemSelected(position);
                        }
                    })
            );

            TrainingslistAdapter adapter = new TrainingslistAdapter(trainingsSchemaList);
            traininglist.setAdapter(adapter);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnitemSelect) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnitemSelect");
        }

    }


    public void setList(List<TrainingsSchema> trainingsSchemas ) {
        this.trainingsSchemaList = trainingsSchemas;
        if(getView() != null){
            RecyclerView traininglist = (RecyclerView) getView().findViewById(R.id.traininglijst);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
            traininglist.setLayoutManager(mLayoutManager);
            traininglist.addOnItemTouchListener(
                    new RecyclerItemClickListener(this.getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            mCallback.onItemSelected(position);
                        }
                    })
            );
            TrainingslistAdapter adapter = new TrainingslistAdapter(trainingsSchemaList);
            traininglist.setAdapter(adapter);
        }

    }

}
