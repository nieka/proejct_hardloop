package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niek.project_hardloop.R;

import java.util.List;

import Adapters.RecyclerItemClickListener;
import Adapters.TrainingslistAdapter;
import Entity.TrainingsSchema;
public class TrainingList extends android.app.Fragment {

    public interface listFragmentCallback {
        void onItemSelected(int position);
        void deleteTraining(int position);

    }

    private listFragmentCallback mCallback;
    private List<TrainingsSchema> trainingsSchemaList;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_traininglist,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                System.out.println("swipe");
                mCallback.deleteTraining(viewHolder.getAdapterPosition());
            }
        };

        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);


        /*recyleview met training lijst vullen met data*/
        if(trainingsSchemaList != null){
            RecyclerView traininglist = (RecyclerView) view.findViewById(R.id.traininglijst);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(traininglist.getContext());
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
            itemTouchHelper.attachToRecyclerView(traininglist);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (listFragmentCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnitemSelect");
        }

    }


    public void setList(List<TrainingsSchema> trainingsSchemas) {
        this.trainingsSchemaList = trainingsSchemas;
        if (getView() != null) {
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
            itemTouchHelper.attachToRecyclerView(traininglist);
        }

    }

}
