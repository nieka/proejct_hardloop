package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niek.project_hardloop.R;

import java.util.List;

import Entity.TrainingsSchema;

public class TrainingslistAdapter extends RecyclerView.Adapter<TrainingslistAdapter.ViewHolder> {

    private List<TrainingsSchema> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTNaam;
        public TextView mTLength;
        private ImageView mImageview;

        public ViewHolder(View v) {
            super(v);
            mTNaam = (TextView) v.findViewById(R.id.T_Naam);
            mTLength = (TextView) v.findViewById(R.id.T_lengthe);
            mImageview = (ImageView) v.findViewById(R.id.icon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrainingslistAdapter(List<TrainingsSchema> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrainingslistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trainingsitem, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = mDataset.get(position).getNaam();
        final String lengte = mDataset.get(position).getLengte() + mDataset.get(position).getLengteSoort();
        holder.mTNaam.setText(name);
        holder.mTLength.setText(lengte);
        switch (mDataset.get(position).getSoort()){
            case "Hardlopen" :
                holder.mImageview.setImageResource(R.drawable.ic_sports_1);
                break;
            case "Lopen" :
                holder.mImageview.setImageResource(R.drawable.ic_maps_directions_walk);
                break;
            case "Fietsen" :
                holder.mImageview.setImageResource(R.drawable.ic_maps_directions_bike);
                break;
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
