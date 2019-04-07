package iut.flappy.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import iut.flappy.R;
import iut.flappy.metier.Player;

public class PlayerAdapter extends ArrayAdapter<Player> {

    private int resource;

    public PlayerAdapter(Context context, int resource, List<Player> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        ((TextView) rowView.findViewById(R.id.playerName)).setText(getItem(position).getName());

        ((TextView) rowView.findViewById(R.id.playerScore)).setText(String.valueOf(getItem(position).getScore()));

        return rowView;
    }
}
