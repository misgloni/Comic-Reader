package work.touchstr.manhua.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import work.touchstr.manhua.Model.Base.ComicInfo;
import work.touchstr.manhua.R;


public class ComicInfoAdapter extends ArrayAdapter<ComicInfo> {

    int resource;
    public ComicInfoAdapter(@NonNull Context context, List<ComicInfo> objects) {
        super(context, R.layout.comic_info_item,objects);
        this.resource=R.layout.comic_info_item;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        ComicInfo comicInfo=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resource,null);
        TextView textView=(TextView)view.findViewById(R.id.comicInfoTextView);
        textView.setText(comicInfo.getComicName());
        return view;

    }
}
