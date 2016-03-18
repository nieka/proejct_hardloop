package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niek.project_hardloop.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import java.util.ArrayList;

import Entity.TrainingsSchema;

public class DetailFragment extends Fragment implements MapEventsReceiver {
    private TrainingsSchema trainingsSchema;
    private MapView mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,
                container, false);
        System.out.println("oncreate detail fragment");
        mMap = (MapView) view.findViewById(R.id.map);
        if(trainingsSchema != null){
            TextView textView = (TextView) view.findViewById(R.id.T_title_locatie);
            textView.setVisibility(View.VISIBLE);
            setText(view);
            setmMap();
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

            if(mMap != null){
                System.out.println("set location");
                setmMap();
            }
        }

    }

    /*Set de detailfragment vanuit de oncreate methode*/
    public void setText(View view){
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



    private void setmMap(){
        IMapController mapController = mMap.getController();
        CompassOverlay mCompassOverlay = new CompassOverlay(this.getActivity(), new InternalCompassOrientationProvider(this.getActivity()), mMap);
        mMap.setVisibility(View.VISIBLE);
        mMap.getOverlays().add(mCompassOverlay);
        mMap.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mMap.setBuiltInZoomControls(true);
        mMap.setMultiTouchControls(true);

        mapController.setZoom(17);
        GeoPoint startPoint;
        if(trainingsSchema.getLatitude() == 0){
            startPoint = new GeoPoint(51.687882, 5.286655);
        }else {
            startPoint = new GeoPoint(trainingsSchema.getLatitude(), trainingsSchema.getLongitude());
        }
        mapController.setCenter(startPoint);

        //your items
        ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem("Locatie", "Jouw trainings locatie", startPoint)); // Lat/Lon decimal degrees
        setMapOverlay(items);
    }
    private void setMapOverlay(ArrayList<OverlayItem> items){
        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                },new ResourceProxyImpl(this.getActivity().getApplicationContext()));
        mOverlay.setFocusItemsOnTap(true);

        mMap.getOverlays().add(mOverlay);

        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this.getActivity(), this);
        mMap.getOverlays().add(0, mapEventsOverlay);

        mMap.invalidate();
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
    }
}
