package work.touchstr.manhua.View.CustomComponent;

import android.content.Context;
import android.util.AttributeSet;

import work.touchstr.manhua.Model.Base.EpisodeInfo;

public class EpisodeButton extends android.support.v7.widget.AppCompatButton {
    EpisodeInfo episodeInfo;
    public EpisodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setEpisodeInfo(EpisodeInfo episodeInfo)
    {
        this.episodeInfo=episodeInfo;
    }
    public EpisodeInfo getEpisideInfo()
    {
        return episodeInfo;
    }
}
